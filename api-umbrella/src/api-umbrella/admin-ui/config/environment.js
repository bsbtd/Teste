'use strict';

module.exports = function(environment) {
  let ENV = {
    modulePrefix: 'api-umbrella-admin-ui',
    environment,
    rootURL: '/admin/',
    locationType: 'hash',
    EmberENV: {
      FEATURES: {
        // Here you can enable experimental features on an ember canary build
        // e.g. EMBER_NATIVE_DECORATOR_SUPPORT: true
      },
      EXTEND_PROTOTYPES: {
        // Prevent Ember Data from overriding Date.parse.
        Date: false,
      },
    },

    APP: {
      // Here you can pass flags/options to your application instance
      // when it is created
    },

    fontawesome: {
      icons: {
        'free-solid-svg-icons': [
          'arrow-down',
          'arrow-right',
          'bars',
          'calendar',
          'caret-down',
          'cog',
          'lock',
          'map-marker-alt',
          'pencil-alt',
          'plus',
          'plus-circle',
          'question-circle',
          'signal',
          'sitemap',
          'sort',
          'sort-down',
          'sort-up',
          'sync-alt',
          'times',
          'upload',
          'user',
          'users',
        ],
        'free-regular-svg-icons': [
          'file',
          'folder',
        ],
      },
    },
  };

  if(environment === 'development') {
    // ENV.APP.LOG_RESOLVER = true;
    // ENV.APP.LOG_ACTIVE_GENERATION = true;
    // ENV.APP.LOG_TRANSITIONS = true;
    // ENV.APP.LOG_TRANSITIONS_INTERNAL = true;
    // ENV.APP.LOG_VIEW_LOOKUPS = true;
  }

  if(environment === 'test') {
    // Testem prefers this...
    ENV.locationType = 'none';

    // keep test console output quieter
    ENV.APP.LOG_ACTIVE_GENERATION = false;
    ENV.APP.LOG_VIEW_LOOKUPS = false;

    ENV.APP.rootElement = '#ember-testing';
    ENV.APP.autoboot = false;
  }

  if(environment === 'production') {
    // here you can enable a production-specific feature
  }

  return ENV;
};
