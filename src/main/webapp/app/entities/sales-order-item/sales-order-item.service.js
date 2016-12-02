(function() {
    'use strict';
    angular
        .module('helpopApp')
        .factory('SalesOrderItem', SalesOrderItem);

    SalesOrderItem.$inject = ['$resource', 'DateUtils'];

    function SalesOrderItem ($resource, DateUtils) {
        var resourceUrl =  'api/sales-order-items/:id';

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
