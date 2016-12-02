(function() {
    'use strict';
    angular
        .module('helpopApp')
        .factory('SalesOrder', SalesOrder);

    SalesOrder.$inject = ['$resource', 'DateUtils'];

    function SalesOrder ($resource, DateUtils) {
        var resourceUrl =  'api/sales-orders/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    if (data) {
                        data = angular.fromJson(data);
                        data.orderedAt = DateUtils.convertDateTimeFromServer(data.orderedAt);
                    }
                    return data;
                }
            },
            'update': { method:'PUT' }
        });
    }
})();
