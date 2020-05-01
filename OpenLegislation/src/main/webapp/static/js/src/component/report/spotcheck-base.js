(function () {
    /**
     * Defines utilities that are used throughout the spotcheck pages
     */
    angular.module('open.spotcheck', ['open.core', 'smart-table', 'diff-match-patch'])
        .factory('SpotcheckMismatchIgnoreAPI', ['$resource', mismatchIgnoreApi])
        .factory('SpotcheckMismatchTrackingAPI', ['$resource', mismatchTrackingApi])
        .factory('SpotcheckMismatchDeleteAllAPI', ['$resource', mismatchDeleteAllApi])
        .filter('contentType', contentTypeLabelFilter)
        .filter('mismatchType', mismatchTypeFilter)
        .filter('dataSourceRef', dataSourceRefFilter)
        .filter('dataSourceData', dataSourceDataFilter)
        .filter('contentUrl', contentUrlFilter)
        .filter('referenceUrl', referenceUrlFilter)
        .filter('isOLRef', isOLRefFilter)
        .directive('mismatchDiff', ['$timeout', mismatchDiffDirective])
    ;

    /* --- Constants --- */

    /**
     * Maps datasource to relevant labels
     * todo integrate into spotcheck-report-page.js
     * todo add labels to datasource java enum and pull from there
     */
    var dataSourceMap = {
        LBDC: {
            comparisonLabel: 'LBDC - OpenLegislation',
            refLabel: 'LBDC',
            dataLabel: 'OpenLeg'
        },
        NYSENATE: {
            comparisonLabel: 'OpenLegislation - NYSenate.gov',
            refLabel: 'OpenLeg',
            dataLabel: 'NYSenate.gov',
            olRef: true
        },
        OPENLEG: {
            comparisonLabel: 'Openlegislation Ref - Openlegislaton Source',
            refLabel: 'Open Legislation Ref',
            dataLabel: 'Open Legislation Source'
        }
    };

    /* --- Api Methods --- */

    function mismatchIgnoreApi($resource) {
        return $resource(adminApiPath + "/spotcheck/mismatches/:mismatchId/ignore", {
            mismatchId: '@mismatchId',
            ignoreLevel: '@ignoreLevel'
        });
    }

    function mismatchTrackingApi($resource) {
        return $resource(adminApiPath + "/spotcheck/mismatches/:mismatchId/issue/:issueId", {
            mismatchId: '@mismatchId',
            issueId: '@issueId'
        });
    }

    // Delete all issues corresponding to the given mismatch
    function mismatchDeleteAllApi($resource) {
        return $resource(adminApiPath + "/spotcheck/mismatch/:mismatchId/delete", {
            mismatchId: '@mismatchId'
        });
    }

    /* --- Filters --- */

    function contentTypeLabelFilter() {
        const contentTypeMap = {
            AGENDA: "Agenda",
            AGENDA_WEEK: "Agenda",
            BILL: "Bill",
            BILL_AMENDMENT: "Bill Amendment",
            CALENDAR: "Calendar",
            LAW: "Law",
        };
        return function(reportType) {
            if (contentTypeMap.hasOwnProperty(reportType)) {
                return contentTypeMap[reportType];
            }
            return "Content";
        };
    }

    function mismatchTypeFilter() {
        var mismatchTypeMap = angular.copy(window.mismatchMap);

        // Map of mismatch type to boolean indicating whether the mismatch is reference missing.
        var missingTypes = {
            'REFERENCE_DATA_MISSING': true,
            'OBSERVE_DATA_MISSING': false
        };

        return function (mismatchType, dataSource) {
            if (!mismatchTypeMap.hasOwnProperty(mismatchType)) {
                console.error('Unknown mismatch type:', mismatchType);
                return "Unknown MM Type!";
            }

            // If mismatch is for data missing, display the data source from which it was missing
            if (dataSource && missingTypes.hasOwnProperty(mismatchType)) {
                var refMissing = missingTypes[mismatchType];
                var dataSourceInfo = dataSourceMap[dataSource];
                // If openleg is the reference, reference/observed is flipped in the display
                if (dataSourceInfo.olRef) {
                    refMissing = !refMissing;
                }
                var label = refMissing ? dataSourceInfo.refLabel : dataSourceInfo.dataLabel;
                return "Missing: " + label;
            }

            return mismatchTypeMap[mismatchType];
        }
    }

    function dataSourceRefFilter() {
        return function (dataSource) {
            if (!dataSourceMap.hasOwnProperty(dataSource)) {
                return "Unknown DataSource"
            }
            return dataSourceMap[dataSource].refLabel;
        }
    }

    function dataSourceDataFilter() {
        return function (dataSource) {
            if (!dataSourceMap.hasOwnProperty(dataSource)) {
                return "Unknown DataSource"
            }
            return dataSourceMap[dataSource].dataLabel;
        }
    }

    function contentUrlFilter() {
        var contentTypeUrlFns = {
            LBDC: openlegLocalUrlFns,
            NYSENATE: senateSiteUrlFns,
            OPENLEG: openlegLocalUrlFns
        };

        return function (key, dataSource, contentType) {
            var contentUrlFn = (contentTypeUrlFns[dataSource] || {})[contentType];
            if (contentUrlFn) {
                return contentUrlFn(key);
            }
            return null;
        };
    }

    function referenceUrlFilter() {
        // multi-map of url generating functions by datasource and content type
        var refUrlFns = {
            LBDC: lbdcUrlFns,
            NYSENATE: openlegLocalUrlFns,
            OPENLEG: openlegRefUrlFns
        };

        return function (key, datasource, contentType) {
            // Get a url function for the given datasource and content type, if it exists
            var refUrlFn = (refUrlFns[datasource] || {})[contentType];
            if (refUrlFn) {
                return refUrlFn(key);
            }
            return null;
        };
    }

    function isOLRefFilter() {
        return function (dataSource) {
            return dataSourceMap.hasOwnProperty(dataSource) &&
                (dataSourceMap[dataSource].olRef === true);
        }
    }


    /* --- Directives --- */

    function mismatchDiffDirective($timeout) {
        return {
            restrict: 'E',
            scope: {
                left: '=',
                right: '='
            },
            template:
            "<span ng-class='{preformatted: pre, \"word-wrap\": !pre}' style='line-height: {{lineHeight}}px'>" +
            "<line-numbers ng-if='pre && showLines' line-end='lines'></line-numbers>" +
            "<semantic-diff left-obj='left' right-obj='right'></semantic-diff>" +
            "</span></span>"
            ,
            link: function ($scope, $element, $attrs) {
                $scope.pre = !$attrs.hasOwnProperty('noPre');
                $scope.wrap = !$attrs.hasOwnProperty('noWrap');
                $scope.showLines = $attrs.showLines !== "false";
                $scope.lines = 1;
                $scope.lineHeight = 20;
                $scope.adjustLineCount = function () {
                    if (!$scope.pre && !$attrs.hasOwnProperty('noPre')) {
                        $scope.pre = $scope.left.split(/\n/).length > 1 || $scope.right.split(/\n/).length > 1;
                    }
                    if ($scope.pre && $scope.showLines) {
                        $timeout(function () {
                            var childElement = $element.children()[0];
                            var elementHeight = childElement.offsetHeight;
                            $scope.lines = Math.ceil(elementHeight / $scope.lineHeight);
                            $scope.pre = $scope.lines > 1;
                        }, 100);
                    }
                };
                $scope.$watchGroup(['left', 'right'], $scope.adjustLineCount);
            }
        };
    }

    /* --- Mismatch Url Methods --- */


    var openlegLocalUrlFns = {
        'AGENDA': getLocalAgendaUrl,
        'AGENDA_WEEK': getLocalAgendaUrl,
        'BILL': getLocalBillUrl,
        'BILL_AMENDMENT': getLocalBillUrl,
        'CALENDAR': getLocalCalendarUrl,
        'LAW': getOpenlegLawUrl
    };

    var lbdcUrlFns = {
        BILL: getLrsBillUrl
    };

    var senateSiteUrlFns = {
        AGENDA: getSenSiteAgendaUrl,
        BILL_AMENDMENT: getSenSiteBillUrl,
        CALENDAR: getSenSiteCalendarUrl,
        LAW: getSenSiteLawUrl
    };

    var openlegRefUrlFns = {
        AGENDA: getOpenlegRefAgendaUrl,
        BILL: getOpenlegRefBillUrl,
        CALENDAR: getOpenlegRefCalendarUrl
    };

    function getOpenlegLawUrl(key) {
        var url = ctxPath + "/laws";
        var obsType = key.obsType;
        if (obsType === 'TREE' || obsType === 'DOCUMENT') {
            url += "/" + key.lawChapter;
            if (obsType === 'DOCUMENT') {
                url += "?location=" + key.locationId;
            }
        }
        return url;
    }

    function getLocalAgendaUrl(key) {
        var url = ctxPath + "/agendas/";
        if (key.agendaId) {
            url += key.agendaId.year + "/" + key.agendaId.number
        } else if (key.weekOf) {
            url += key.weekOf;
        } else {
            return "";
        }
        if (key.committeeId) {
            url += "?comm=" + key.committeeId.name;
        }
        return url;
    }

    function getLocalBillUrl(key) {
        var url = ctxPath + "/bills/" + key.session.year + "/" + key.basePrintNo;
        if (key.hasOwnProperty('version')) {
            url += '?version=' + key.version;
        }
        return url;
    }

    function getLocalCalendarUrl(key) {
        var url = ctxPath + "/calendars/" + key.year + "/" + key.calNo;
        if (key.hasOwnProperty('type')) {
            switch (key.type) {
                case 'ACTIVE_LIST':
                    url += '?view=active-list';
                    break;
                case 'FLOOR_CALENDAR':
                case 'SUPPLEMENTAL_CALENDAR':
                    url += '?view=floor';
                    break;
            }
        }
        return url;
    }

    function getLrsBillUrl(key) {
        return "http://public.leginfo.state.ny.us/navigate.cgi" +
            "?NVDTO:=&QUERYTYPE=BILLNO&CBTEXT=Y&CBSPONMEMO=Y" +
            "&SESSYR=" + key.session.year +
            "&QUERYDATA=" + key.printNo;
    }

    function getSenSiteAgendaUrl(key) {
        // TODO Need meeting date time to create link.
        // Example: https://www.nysenate.gov/calendar/meetings/codes/january-23-2017/codes-meeting
        // return senSitePath + "/calendar/meetings/" + key.committeeId.name + "/";
        return null;
    }

    function getSenSiteBillUrl(key) {
        var billType = "bills";
        if (!/^[SA]/i.test(key.printNo)) {
            billType = "resolutions";
        }
        var amendment = key.version === "DEFAULT"
            ? "original" : key.version;
        return senSitePath + "/legislation/" + billType + "/" +
            key.session.year + "/" + key.basePrintNo + "/amendment/" + amendment;
    }

    function getSenSiteCalendarUrl(key) {
        // TODO Need session date time to create link.
        // Example: https://www.nysenate.gov/calendar/sessions/june-05-2017/session-6-5-17
        return null;
    }

    function getSenSiteLawUrl(key) {
        var url = senSitePath + "/legislation/laws";;
        var obsType = key.obsType;
        if (obsType === 'TREE' || obsType === 'DOCUMENT') {
            url += "/" + key.lawChapter;
            if (obsType === 'DOCUMENT') {
                url += "/" + key.locationId;
            }
        } else {
            url += "/all";
        }
        return url;
    }

    function getOpenlegRefBillUrl(key) {
        var url = openlegRefPath + "/bills/" + key.session.year + "/" + key.basePrintNo;
        if (key.hasOwnProperty('version')) {
            url += '?version=' + key.version;
        }
        return url;
    }

    function getOpenlegRefCalendarUrl(key) {
        var url = openlegRefPath + "/calendars/" + key.year + "/" + key.calNo;
        if (key.hasOwnProperty('type')) {
            switch (key.type) {
                case 'ACTIVE_LIST':
                    url += '?view=active-list';
                    break;
                case 'FLOOR_CALENDAR':
                case 'SUPPLEMENTAL_CALENDAR':
                    url += '?view=floor';
                    break;
            }
        }
        return url;
    }

    function getOpenlegRefAgendaUrl(key) {
        if (key.agendaId.year > 0) {
            return openlegRefPath + "/agendas/" + key.agendaId.year + "/" + key.agendaId.number +
                "?comm=" + key.committeeId.name;
        }
        return "";
    }

})();
