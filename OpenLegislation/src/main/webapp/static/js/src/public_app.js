var openPublicApp = angular.module('open-public', ['ngMaterial']);

openPublicApp.controller('PublicHomeCtrl', ['$scope', '$http', '$window', '$location', '$sce',
                                    function($scope, $http, $window, $location, $sce) {
    var docsPath = ctxPath + '/docs';

    $scope.getDocsPath = function(page) {
        return $sce.trustAsResourceUrl(ctxPath + "/static/docs/html/" + page);
    };

    $scope.currDocsPath = $scope.getDocsPath("index.html");
    $scope.showLogin = false;

    $scope.dataWeProvide = [
        { type: 'New York State Bills and Resolutions', blurb: 'Discover current and prior legislation that impacts New York State.',
            icon: 'icon-documents', bgclass: 'blue3-bg', docsPage: 'bills.html'},
        { type: 'New York State Laws', blurb: 'Search through the current laws of NYS.',
            icon: 'icon-bookmarks', bgclass: 'green3-bg', docsPage: 'laws.html'},
        { type: 'Senate Session/Hearing Transcripts', blurb: 'Records of Senate session floor discussion since 1993.',
            icon: 'icon-text', bgclass: 'blue4-bg', docsPage: 'transcripts_floor.html'},
        { type: 'Senate Committee Agendas', blurb: 'Committee meetings to discuss bills and the votes to move them to the floor.',
            icon: 'icon-megaphone', bgclass: 'green2-bg', docsPage: 'agendas.html'},
        { type: 'Senate Floor Calendars', blurb: 'Listings of bills that are scheduled for discussion and voting on the senate floor.',
            icon: 'icon-calendar', bgclass: 'blue5-bg', docsPage: 'calendars.html'},
        { type: 'Senate/Assembly Membership', blurb: 'Senate and Assembly members for each session since 2009.',
            icon: 'icon-users', bgclass: 'green1-bg', docsPage: 'members.html'}
    ];

    $scope.subscriptionsAvailable = [
        { title: 'Breaking Changes', enumVal:'BREAKING_CHANGES', checked: true,
            desc:"Sign up for emails regarding breaking changes to the API."},
        { title: 'New Features', enumVal:'NEW_FEATURES', checked: false,
            desc:"Sign up to hear about new features added to the API."}
    ];

    $scope.goToDocsPage = function(page) {
        $scope.currDocsPath = $scope.getDocsPath(page);
        location.hash = 'docs';
    };

    /** Api Key Registration */
    $scope.signedup = false;
    $scope.email = '';
    $scope.subscriptions = [];
    $scope.signup = function() {
        $scope.errmsg = '';
        if (!$scope.email || $scope.email.indexOf('@') === -1) {
            $scope.errmsg = 'Please enter a valid email!';
        }
        else if (!$scope.name || $scope.name.length === 0) {
            $scope.errmsg = 'Please enter a name!';
        }
        else {
            $scope.processing = true;
            /* Check for the subscriptions that are checked and send them in request */
            var subs = [];
            $scope.subscriptionsAvailable.forEach(function(sub){
               if(sub.checked) {
                   subs.push(sub.enumVal);
               }
            });
            $scope.subscriptions = subs;

            $http.post(ctxPath + "/register/signup", {name:$scope.name, email:$scope.email,
                                                                    subscriptions:$scope.subscriptions})
            .success(function(data, status, headers, config) {
                if (data.success === false) {
                    $scope.errmsg = data.message;
                }
                else {
                    $scope.signedup = true;
                }
                $scope.processing = false;
            })
            .error(function(data, status, headers, config) {
                $scope.processing = false;
                $scope.errmsg = 'Sorry, there was an error while processing your request.';
            });
        }
    };

    /** Api Key Login */
    $scope.apiKey = null;
    $scope.loginErrMsg = null;
    $scope.loginWithAPIKey = function() {
        $scope.loginErrMsg = null;
        if (!$scope.apiKey) {
            $scope.loginErrMsg = 'Please enter a valid api key, or sign up for one below.';
        }
        else {
            $scope.apiKey = $scope.apiKey.trim();
            $http.post(ctxPath + "/loginapikey", {apiKey : $scope.apiKey})
            .success(function(data, status, headers, config) {
                if (data.success == false) {
                    $scope.loginErrMsg = data.message;
                }
                else {
                    $window.location.href = ctxPath + '/';
                }
            })
            .error(function(data, status, headers, config) {
                $scope.loginErrMsg = 'There was a problem logging. Please try again later.';
            });
        }
    };
}]);
