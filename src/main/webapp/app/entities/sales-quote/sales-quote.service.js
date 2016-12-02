(function() {
    'use strict';
    angular
        .module('helpopApp')
        .factory('SalesQuote', SalesQuote);

    SalesQuote.$inject = ['$resource', 'DateUtils'];

    function SalesQuote ($resource, DateUtils) {
        var resourceUrl =  'api/sales-quotes/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    if (data) {
                        data = angular.fromJson(data);
                        data.quotedAt = DateUtils.convertDateTimeFromServer(data.quotedAt);
                    }
                    return data;
                }
            },
            'update': { method:'PUT' }
        });
    }
})();
