module.exports = {
  parser: '@typescript-eslint/parser',
  env: {
    jest: true,
    browser: true,
    'jest/globals': true
  },
  parserOptions: {
    jsx: true,
    useJSXTextNode: true
  },
  plugins: [
    '@typescript-eslint/eslint-plugin',
    'react-hooks',
    'jest',
    'prettier',
    'jsx-a11y'
  ],
  extends: [
    'airbnb',
    'plugin:@typescript-eslint/recommended',
    'prettier',
    'prettier/@typescript-eslint'
  ],
  ignorePatterns: ['src/setupTests.ts'],
  rules: {
    'no-shadow': 'off',
    '@typescript-eslint/no-shadow': ['error'],
    'no-underscore-dangle': 'off',
    // '@typescript-eslint/no-use-before-define': ['error'],
    'jest/no-disabled-tests': 'warn',
    'jest/no-focused-tests': 'error',
    'jest/no-duplicate-hooks': 'warn',
    'jest/prefer-hooks-on-top': 'warn',
    'jest/require-top-level-describe': 'error',
    'jest/valid-title': 'warn',
    'jest/valid-expect': 'error',
    'jest/no-identical-title': 'error',
    'jest/prefer-to-have-length': 'warn',
    'jest/no-export': 'error',
    'jest/expect-expect': 'error',
    'jest/lowercase-name': 'warn',
    'jest/no-commented-out-tests': 'warn',
    /**
     * @bug https://github.com/benmosher/eslint-plugin-import/issues/1282
     * "import/named" temporary disable.
     */
    'import/named': 'off',
    'import/export': 'off',
    'import/no-unresolved': 'off',
    'import/prefer-default-export': 'off', // Allow single Named-export
    'import/extensions': 'off', // fixes Missing File Extension for ".. error
    'no-unused-expressions': [
      'warn',
      {
        allowShortCircuit: true,
        allowTernary: true
      }
    ], // https://eslint.org/docs/rules/no-unused-expressions
    '@typescript-eslint/no-unused-vars': [
      'warn',
      {
        ignoreRestSiblings: true
      }
    ], // https://eslint.org/docs/rules/no-unused-vars
    'no-plusplus': 'off',

    /**
     * @description rules of @typescript-eslint
     */
    '@typescript-eslint/prefer-interface': 'off', // also want to use "type"
    '@typescript-eslint/explicit-function-return-type': 'off', // annoying to force return type
    '@typescript-eslint/no-use-before-define': 0, // so we can place the useStlyes under the component
    '@typescript-eslint/no-explicit-any': ['error', { ignoreRestArgs: true }],
    /**
     * @description rules of eslint-plugin-react
     */
    'react/jsx-filename-extension': [
      'warn',
      {
        extensions: ['.jsx', '.tsx']
      }
    ],

    'react/jsx-indent': 'off', // conflicting with prettier styles
    'react/jsx-indent-props': 'off', // fixes conflict with prettier rule that tries to indent props
    'react/jsx-closing-tag-location': 'off', // conflicting with prettier styles
    'react/prop-types': 'off',
    'react/jsx-props-no-spreading': 'off',
    'react/jsx-one-expression-per-line': 'off',
    'react/jsx-curly-newline': 'off', // conflicting with prettier styles
    'react/jsx-wrap-multilines': 'off', // conflicting with prettier styles
    /**
     * @description rules of eslint-plugin-react-hooks
     */
    'react-hooks/rules-of-hooks': 'error',
    'react-hooks/exhaustive-deps': 'warn',

    /**
     * @description rules of eslint-plugin-prettier
     */
    'prettier/prettier': [
      'error',
      {
        singleQuote: true,
        semi: true,
        tabWidth: 2,
        'newline-before-return': true,
        'no-duplicate-variable': [true, 'check-parameters'],
        'no-var-keyword': true
      }
    ],
    /**
     * https://github.com/evcohen/eslint-plugin-jsx-a11y/blob/master/docs/rules/label-has-associated-control.md
     * Labels for custom inputs.
     */
    'jsx-a11y/label-has-associated-control': [
      2,
      {
        controlComponents: ['PaginationInput']
      }
    ],
    // note you must disable the base rule as it can report incorrect errors
    'no-use-before-define': 'off'
  }
};
