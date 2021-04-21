'use strict'
const https = require('https');

const CIS_HOST = process.env.CIS_DATA_API_HOST;
const STATUS_FAVORABLE = 'FAVORABLE'
const STATUS_UNFAVORABLE = 'UNFAVORABLE'
const STATUS_INCOMPLETE = 'INCOMPLETE'
const STATUS_PENDING = 'PENDING'
const TYPE_INITIAL = 'A_NUMBER'
const TYPE_FULL = 'FIRST_LAST_NAME'
const RESPONSE_TYPE_401 = 'Unauthorized.'

function createResponse(respCode, body) {
  return {
    statusCode: respCode,
    headers: {
      'Content-Type': 'application/json',
    },
    body: JSON.stringify(body)
  }
}

function performSecurityChecks(cisBio, checkValue) {
  let checks = [];
  // first simple check if A Number matches
  if (cisBio.a_number === checkValue.aNumber) {
    checks.push(createResponseModel(STATUS_FAVORABLE, cisBio.a_number, TYPE_INITIAL))
  } else {
    checks.push(createResponseModel(STATUS_UNFAVORABLE, cisBio.a_number, TYPE_INITIAL))
  }
  // second check is for optional First and/or Last Name
  // if either of them do not match the cisBio then fail
  // if both are provides, both must match, if one provided it must match
  if (checkValue.firstName && (checkValue.firstName !== cisBio.first_name) ||
      (checkValue.lastName && (checkValue.lastName !== cisBio.last_name))) {
    // fail
    checks.push(createResponseModel(STATUS_UNFAVORABLE, cisBio.a_number, TYPE_FULL))
  } else if (checkValue.firstName || checkValue.lastName) {
    // pass
    checks.push(createResponseModel(STATUS_FAVORABLE, cisBio.a_number, TYPE_FULL))
  }
  return checks;
}

function createResponseModel(status, aNumber, type) {
  return {
    'createdBy': 'speed-security-checks',
    'createdDate': new Date().toISOString(),
    'aNumber': aNumber,
    'securityCheckStatus': status,
    'securityCheckType': type
  }
}

function retrieveCheckValue(event) {
  let checkValue = {}
  if (event && event.queryStringParameters) {
    // regex for aNum = ^A[0-9]{8,9}$
    if (event.queryStringParameters.a_number) {
      checkValue.aNumber = event.queryStringParameters.a_number;
    }
    if (event.queryStringParameters.first_name) {
      checkValue.firstName = event.queryStringParameters.first_name;
    }
    if (event.queryStringParameters.last_name) {
      checkValue.lastName = event.queryStringParameters.last_name;
    } 
  }
  return checkValue;
}

exports.handler = function (event, context, callback) {

  let authToken;
  if (event && event.headers && event.headers.Authorization) {
    authToken = event.headers.Authorization;
  } else {
    const response = createResponse(401, { error: RESPONSE_TYPE_401 } )
    callback(null, response)
    return
  }

  let checkValue = retrieveCheckValue(event);
  if (!checkValue.aNumber) {
    const response = createResponse(500, { error: 'Missing require parameter: \'a_number\'.'} )
    callback(null, response)
    return
  }
  console.log(`Performing check on:`);
  console.log(checkValue)

  const options = {
    hostname: CIS_HOST,
    port: 443,
    path: `/cis-data/cis_cases/${checkValue.aNumber}`,
    method: 'GET',
    headers: {
      'Authorization': authToken,
      'Content-Type': 'application/json'
    }
  }
  console.log('options....')
  console.log(options)

  https.get(options, function (res) {
    let json = '';
    res.on('data', function (chunk) {
      json += chunk;
    });
    res.on('end', function () {
      if (res.statusCode === 200) {
        try {
          const data = JSON.parse(json);
          // data is available here:
          console.log('cis-data.....');
          console.log(data);
          const body = performSecurityChecks(data, checkValue)
          const response = createResponse(200, body)
          callback(null, response)
      
        } catch (e) {
          console.log('Error parsing JSON!');
          console.log(e);
          // cis-data-api returns text `NOT FOUND` instead of JSON
          let checks = []
          checks.push(createResponseModel(STATUS_UNFAVORABLE, checkValue.aNumber, TYPE_INITIAL));
          const response = createResponse(200, checks)
          callback(null, response)
        }
      } else {
        console.log('Status:', res.statusCode);
        const response = createResponse(res.statusCode, { error: RESPONSE_TYPE_401 } )
        callback(null, response)
      }
    });
  }).on('error', function (err) {
    console.log('Error:', err);
    const response = createResponse(500, { error: err.message } )
    callback(null, response)
  });
}