// The file contents for the current environment will overwrite these during build.
// The build system defaults to the dev environment which uses `environment.ts`, but if you do
// `ng build --env=prod` then `environment.prod.ts` will be used instead.
// The list of which env maps to which file can be found in `.angular-cli.json`.

export const environment = {
  production: false,
  userServiceURL: 'http://localhost:9080/users/api/data',
  timeServiceURL: 'http://localhost:9080/service2/api/data',
  projectServiceURL: 'http://localhost:9080/service1/api/data',
  userAddEditBaseURL: 'http://localhost:9080/users/api/persons',
  searchServiceURL: 'http://localhost:9080/service1',
  casesURL: 'http://localhost:9080/service1',
  agencyServiceURL: 'http://localhost:9080/service1',
  analytics: {
    measurement_id: `${process.env.REACT_APP_ANALYTICS_MEASUREMENT_ID}`
  },
  cognito: {
    authority:
      'https://cognito-idp.us-east-2.amazonaws.com/us-east-2_CDRh3fkc0',
    client_id: `${process.env.REACT_APP_COGNITO_CLIENT_ID}`,
    response_type: 'code',
    metadata: {
      issuer: 'https://cognito-idp.us-east-2.amazonaws.com/us-east-2_CDRh3fkc0',
      authorization_endpoint:
        'https://practice-4.auth.us-east-2.amazoncognito.com/oauth2/authorize',
      userinfo_endpoint:
        'https://practice-4.auth.us-east-2.amazoncognito.com/oauth2/userInfo',
      end_session_endpoint:
        'https://practice-4.auth.us-east-2.amazoncognito.com/logout',
      token_endpoint:
        'https://practice-4.auth.us-east-2.amazoncognito.com/oauth2/token',
      jwks_uri:
        'https://cognito-idp.us-east-2.amazonaws.com/us-east-2_CDRh3fkc0/.well-known/jwks.json'
    },
    signingKeys: [
      {
        alg: 'RS256',
        e: 'AQAB',
        kid: `${process.env.REACT_APP_COGNITO_SIGNING_KEY_KID_1}`,
        kty: 'RSA',
        n: `${process.env.REACT_APP_COGNITO_SIGNING_KEY_N_1}`,
        use: 'sig'
      },
      {
        alg: 'RS256',
        e: 'AQAB',
        kid: `${process.env.REACT_APP_COGNITO_SIGNING_KEY_KID_2}`,
        kty: 'RSA',
        n: `${process.env.REACT_APP_COGNITO_SIGNING_KEY_N_2}`,
        use: 'sig'
      }
    ],
    jwtAuthorityKey: 'cognito:groups'
  }
};
