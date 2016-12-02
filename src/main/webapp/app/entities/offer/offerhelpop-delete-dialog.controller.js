(function() {
    'use strict';

    angular
        .module('helpopApp')
        .controller('OfferHelpopDeleteController',OfferHelpopDeleteController);

    OfferHelpopDeleteController.$inject = ['$uibModalInstance', 'entity', 'Offer'];

    function OfferHelpopDeleteController($uibModalInstance, entity, Offer) {
        var vm = this;

        vm.offer = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            Offer.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
