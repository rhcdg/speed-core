// eslint-disable-next-line @typescript-eslint/no-var-requires,import/no-extraneous-dependencies
const webpack = require('webpack');

module.exports = {
  webpack: function override(config, _) {
    return {
      ...config,
      plugins: [
        ...config.plugins,
        new webpack.NormalModuleReplacementPlugin(
          /src\/environments\/environment\.ts/,
          'environment.docker-nginx.ts'
        )
      ]
    };
  }
};
