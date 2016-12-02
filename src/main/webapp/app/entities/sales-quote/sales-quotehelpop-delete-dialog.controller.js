(function() {
    'use strict';

    angular
        .module('helpopApp')
        .controller('SalesQuoteHelpopDeleteController',SalesQuoteHelpopDeleteController);

    SalesQuoteHelpopDeleteController.$inject = ['$uibModalInstance', 'entity', 'SalesQuote'];

    function SalesQuoteHelpopDeleteController($uibModalInstance, entity, SalesQuote) {
        var vm = this;

        vm.salesQuote = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            SalesQuote.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
