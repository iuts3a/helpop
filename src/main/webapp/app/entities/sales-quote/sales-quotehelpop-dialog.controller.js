(function() {
    'use strict';

    angular
        .module('helpopApp')
        .controller('SalesQuoteHelpopDialogController', SalesQuoteHelpopDialogController);

    SalesQuoteHelpopDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'SalesQuote', 'SalesQuoteItem'];

    function SalesQuoteHelpopDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, SalesQuote, SalesQuoteItem) {
        var vm = this;

        vm.salesQuote = entity;
        vm.clear = clear;
        vm.datePickerOpenStatus = {};
        vm.openCalendar = openCalendar;
        vm.save = save;
        vm.salesquoteitems = SalesQuoteItem.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.salesQuote.id !== null) {
                SalesQuote.update(vm.salesQuote, onSaveSuccess, onSaveError);
            } else {
                SalesQuote.save(vm.salesQuote, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('helpopApp:salesQuoteUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }

        vm.datePickerOpenStatus.quotedAt = false;

        function openCalendar (date) {
            vm.datePickerOpenStatus[date] = true;
        }
    }
})();
