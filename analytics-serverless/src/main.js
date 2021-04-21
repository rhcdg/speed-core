'use strict';

const { auth } = require('google-auth-library');

exports.handler = async (event, context, callback) => {
  const client = auth.fromJSON(JSON.parse(process.env.SERVICE_ACCOUNT_JSON));
  client.scopes = ['https://www.googleapis.com/auth/analytics.readonly'];

  const accessToken = (await client.getAccessToken()).token;

  callback(null, {
    statusCode: 200,
    headers: {
      'Content-Type': 'application/json',
    },
    body: JSON.stringify({
      accessToken: accessToken
    })
  });
};