module.exports = {
  root: true,
  parserOptions: {
    ecmaVersion: 2018,
    sourceType: 'module',
  },
  plugins: [
    'ember',
  ],
  extends: [
    'eslint:recommended',
    'plugin:ember/recommended',
  ],
  env: {
    browser: true,
  },
  rules: {
    'comma-dangle': ['error', 'always-multiline'],
    'no-var': 'error',
    'sort-imports': 'error',
    'object-shorthand': ['error', 'methods'],
    'no-duplicate-imports': 'error',
    'func-call-spacing': ['error', 'never'],
    'keyword-spacing': ['error', { 'before': true, 'after': true, 'overrides': {
      'if': { 'after': false },
      'for': { 'after': false },
      'while': { 'after': false },
      'catch': { 'after': false },
      'switch': { 'after': false },
    }}],
    'no-trailing-spaces': 'error',
  },
  globals: {
    'CommonValidations': true,
  },
  overrides: [
    // node files
    {
      files: [
        '.eslintrc.js',
        '.template-lintrc.js',
        'ember-cli-build.js',
        'testem.js',
        'blueprints/*/index.js',
        'config/**/*.js',
        'lib/*/index.js',
        'server/**/*.js',
      ],
      parserOptions: {
        sourceType: 'script',
      },
      env: {
        browser: false,
        node: true,
      },
      plugins: ['node'],
      rules: Object.assign({}, require('eslint-plugin-node').configs.recommended.rules, {
        // add your custom rules and overrides for node files here

        // this can be removed once the following is fixed
        // https://github.com/mysticatea/eslint-plugin-node/issues/77
        'node/no-unpublished-require': 'off',
      }),
    },
  ],
};
