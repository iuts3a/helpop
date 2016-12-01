(function() {
    'use strict';

    angular
        .module('helpopApp')
        .controller('AddressHelpopDeleteController',AddressHelpopDeleteController);

    AddressHelpopDeleteController.$inject = ['$uibModalInstance', 'entity', 'Address'];

    function AddressHelpopDeleteController($uibModalInstance, entity, Address) {
        var vm = this;

        vm.address = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            Address.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
