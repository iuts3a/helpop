(function() {
    'use strict';

    angular
        .module('helpopApp')
        .controller('OfferHelpopDialogController', OfferHelpopDialogController);

    OfferHelpopDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'Offer', 'Customer'];

    function OfferHelpopDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, Offer, Customer) {
        var vm = this;

        vm.offer = entity;
        vm.clear = clear;
        vm.datePickerOpenStatus = {};
        vm.openCalendar = openCalendar;
        vm.save = save;
        vm.customers = Customer.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.offer.id !== null) {
                Offer.update(vm.offer, onSaveSuccess, onSaveError);
            } else {
                Offer.save(vm.offer, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('helpopApp:offerUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }

        vm.datePickerOpenStatus.createdAt = false;
        vm.datePickerOpenStatus.limitAt = false;

        function openCalendar (date) {
            vm.datePickerOpenStatus[date] = true;
        }
    }
})();
