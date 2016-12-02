(function() {
    'use strict';

    angular
        .module('helpopApp')
        .controller('SalesOrderHelpopController', SalesOrderHelpopController);

    SalesOrderHelpopController.$inject = ['$scope', '$state', 'SalesOrder'];

    function SalesOrderHelpopController ($scope, $state, SalesOrder) {
        var vm = this;

        vm.salesOrders = [];

        loadAll();

        function loadAll() {
            SalesOrder.query(function(result) {
                vm.salesOrders = result;
                vm.searchQuery = null;
            });
        }
    }
})();
