(function() {
    'use strict';

    angular
        .module('helpopApp')
        .controller('SalesOrderItemHelpopController', SalesOrderItemHelpopController);

    SalesOrderItemHelpopController.$inject = ['$scope', '$state', 'SalesOrderItem'];

    function SalesOrderItemHelpopController ($scope, $state, SalesOrderItem) {
        var vm = this;

        vm.salesOrderItems = [];

        loadAll();

        function loadAll() {
            SalesOrderItem.query(function(result) {
                vm.salesOrderItems = result;
                vm.searchQuery = null;
            });
        }
    }
})();
