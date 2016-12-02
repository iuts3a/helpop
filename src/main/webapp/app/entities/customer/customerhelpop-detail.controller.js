(function() {
    'use strict';

    angular
        .module('helpopApp')
        .controller('CustomerHelpopDetailController', CustomerHelpopDetailController);

    CustomerHelpopDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Customer', 'SalesQuote', 'Address', 'SalesOrder'];

    function CustomerHelpopDetailController($scope, $rootScope, $stateParams, previousState, entity, Customer, SalesQuote, Address, SalesOrder) {
        var vm = this;

        vm.customer = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('helpopApp:customerUpdate', function(event, result) {
            vm.customer = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
