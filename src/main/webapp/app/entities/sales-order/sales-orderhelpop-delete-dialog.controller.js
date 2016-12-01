(function() {
    'use strict';

    angular
        .module('helpopApp')
        .controller('SalesOrderHelpopDeleteController',SalesOrderHelpopDeleteController);

    SalesOrderHelpopDeleteController.$inject = ['$uibModalInstance', 'entity', 'SalesOrder'];

    function SalesOrderHelpopDeleteController($uibModalInstance, entity, SalesOrder) {
        var vm = this;

        vm.salesOrder = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            SalesOrder.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
