(function() {
    'use strict';
    angular
        .module('helpopApp')
        .factory('Offer', Offer);

    Offer.$inject = ['$resource', 'DateUtils'];

    function Offer ($resource, DateUtils) {
        var resourceUrl =  'api/offers/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    if (data) {
                        data = angular.fromJson(data);
                        data.createdAt = DateUtils.convertDateTimeFromServer(data.createdAt);
                        data.limitAt = DateUtils.convertDateTimeFromServer(data.limitAt);
                    }
                    return data;
                }
            },
            'update': { method:'PUT' }
        });
    }
})();
