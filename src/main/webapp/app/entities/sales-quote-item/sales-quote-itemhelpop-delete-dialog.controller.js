(function() {
    'use strict';

    angular
        .module('helpopApp')
        .controller('SalesQuoteItemHelpopDeleteController',SalesQuoteItemHelpopDeleteController);

    SalesQuoteItemHelpopDeleteController.$inject = ['$uibModalInstance', 'entity', 'SalesQuoteItem'];

    function SalesQuoteItemHelpopDeleteController($uibModalInstance, entity, SalesQuoteItem) {
        var vm = this;

        vm.salesQuoteItem = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            SalesQuoteItem.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
