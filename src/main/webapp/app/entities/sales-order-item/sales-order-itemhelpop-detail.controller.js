(function() {
    'use strict';

    angular
        .module('helpopApp')
        .controller('SalesOrderItemHelpopDetailController', SalesOrderItemHelpopDetailController);

    SalesOrderItemHelpopDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'SalesOrderItem'];

    function SalesOrderItemHelpopDetailController($scope, $rootScope, $stateParams, previousState, entity, SalesOrderItem) {
        var vm = this;

        vm.salesOrderItem = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('helpopApp:salesOrderItemUpdate', function(event, result) {
            vm.salesOrderItem = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
