(function() {
    'use strict';
    angular
        .module('helpopApp')
        .factory('SalesQuoteItem', SalesQuoteItem);

    SalesQuoteItem.$inject = ['$resource', 'DateUtils'];

    function SalesQuoteItem ($resource, DateUtils) {
        var resourceUrl =  'api/sales-quote-items/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    if (data) {
                        data = angular.fromJson(data);
                        data.createdAt = DateUtils.convertDateTimeFromServer(data.createdAt);
                        data.updatedAt = DateUtils.convertDateTimeFromServer(data.updatedAt);
                    }
                    return data;
                }
            },
            'update': { method:'PUT' }
        });
    }
})();
