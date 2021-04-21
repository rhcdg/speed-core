// The file contents for the current environment will overwrite these during build.
// The build system defaults to the dev environment which uses `environment.ts`, but if you do
// `ng build --env=prod` then `environment.prod.ts` will be used instead.
// The list of which env maps to which file can be found in `.angular-cli.json`.

export const environment = {
  production: false,
  userServiceURL: '/users/api/data',
  timeServiceURL: '/service2/api/data',
  projectServiceURL: '/service1/api/data',
  userAddEditBaseURL: '/users/api/persons',
  service1URL: '/service1/api/data/service1Sas',
  analyticsServiceURL: '/analytics',
  analytics: {
    measurement_id: `${process.env.REACT_APP_ANALYTICS_MEASUREMENT_ID}`
  },
  cognito: {
    authority:
      'https://cognito-idp.us-east-2.amazonaws.com/us-east-2_CDRh3fkc0',
    client_id: '274gmvg18iu03cdlifii4udnf2',
    redirect_uri:
      'https://speed-adjudicator-web.speedc2c24.steampunkfoundry.net/callback',
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
        kid: 'Ks6kqhffCRJq1ac6PGNfNeNYDnw5MvzJHYJ5OSiFJD0=',
        kty: 'RSA',
        n:
          'x07uKQ8TJ84NPa3zvz_y2GOTwwV6FUoQOLTO3pwQYo3BlKO3B9RfG7cNqM9yufx_FUZ4p9izBj-J8mM3weRdeTPRXLXCNuAzbrxn0t0bl-tOE5lXkXEHhJouOQSOjxhHrrzFxVpfn9EhCgkxwQ2jEmPpYtfhVOIhQ_AdjHIkaZZLSnHq48zWCnDsCfaI3w8ITxMnTjgiI7UFldfX-UQUhhlhIi2vxybW_sKJ2YzNlTYm2AgqDGrGk0DRI_uRPthsnqK-gg7dChlDSbS7mKst1amOubrDbEfChGAPwTei7UxUuafJ1vt_G978rao0WJgtjumkeKYz9k111dg2XPgTFQ',
        use: 'sig'
      },
      {
        alg: 'RS256',
        e: 'AQAB',
        kid: 'e1SV8Os8ZnRVOzXlfQ7Vk3lY5Xih9ozqFQyFODwptP0=',
        kty: 'RSA',
        n:
          'nV5d_P9wR9W4WOAnz8hkUUZ9zLaPNyj8GlqUxPR8HS6wV4o5pNMrlSxjWc5TgfXw-lM6OBbRuYf7IVbA_GFfbWyGQfU_10EczVWd4FhQzptCLYnzFVEYMueC3SN66TYBdDiI1ACsS7Q5sHFKwi01QokrV9J_qjSnzko_gEKveOjaw4RpGlQRoGKTrwmWtaG6b3CxnWvLp3OZ77Aff1QdKkynKRgbL_LEmr3M8GmMCEjbBH1jxCSXQGcuDKLC1vESBy7F8rHUFjdPWnDuJZ_4sklUYCQ1dESGmSl2wEmHZL6tp3XedQmdDvpHALFs3FJLwW11_Mp_xLqbMRoSTW9vwQ',
        use: 'sig'
      }
    ],
    jwtAuthorityKey: 'cognito:groups'
  }
};
