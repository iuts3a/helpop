(function() {
    'use strict';

    angular
        .module('helpopApp')
        .controller('SalesOrderItemHelpopDeleteController',SalesOrderItemHelpopDeleteController);

    SalesOrderItemHelpopDeleteController.$inject = ['$uibModalInstance', 'entity', 'SalesOrderItem'];

    function SalesOrderItemHelpopDeleteController($uibModalInstance, entity, SalesOrderItem) {
        var vm = this;

        vm.salesOrderItem = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            SalesOrderItem.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
