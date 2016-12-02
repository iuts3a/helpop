(function() {
    'use strict';

    angular
        .module('helpopApp')
        .controller('SalesQuoteItemHelpopDialogController', SalesQuoteItemHelpopDialogController);

    SalesQuoteItemHelpopDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'SalesQuoteItem'];

    function SalesQuoteItemHelpopDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, SalesQuoteItem) {
        var vm = this;

        vm.salesQuoteItem = entity;
        vm.clear = clear;
        vm.datePickerOpenStatus = {};
        vm.openCalendar = openCalendar;
        vm.save = save;

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.salesQuoteItem.id !== null) {
                SalesQuoteItem.update(vm.salesQuoteItem, onSaveSuccess, onSaveError);
            } else {
                SalesQuoteItem.save(vm.salesQuoteItem, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('helpopApp:salesQuoteItemUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }

        vm.datePickerOpenStatus.createdAt = false;
        vm.datePickerOpenStatus.updatedAt = false;

        function openCalendar (date) {
            vm.datePickerOpenStatus[date] = true;
        }
    }
})();
