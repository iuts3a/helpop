(function() {
    'use strict';

    angular
        .module('helpopApp')
        .controller('SalesOrderHelpopDetailController', SalesOrderHelpopDetailController);

    SalesOrderHelpopDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'SalesOrder', 'SalesOrderItem'];

    function SalesOrderHelpopDetailController($scope, $rootScope, $stateParams, previousState, entity, SalesOrder, SalesOrderItem) {
        var vm = this;

        vm.salesOrder = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('helpopApp:salesOrderUpdate', function(event, result) {
            vm.salesOrder = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
