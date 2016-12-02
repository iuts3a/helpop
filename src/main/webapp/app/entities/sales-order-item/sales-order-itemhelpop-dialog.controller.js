(function() {
    'use strict';

    angular
        .module('helpopApp')
        .controller('SalesOrderItemHelpopDialogController', SalesOrderItemHelpopDialogController);

    SalesOrderItemHelpopDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'SalesOrderItem'];

    function SalesOrderItemHelpopDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, SalesOrderItem) {
        var vm = this;

        vm.salesOrderItem = entity;
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
            if (vm.salesOrderItem.id !== null) {
                SalesOrderItem.update(vm.salesOrderItem, onSaveSuccess, onSaveError);
            } else {
                SalesOrderItem.save(vm.salesOrderItem, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('helpopApp:salesOrderItemUpdate', result);
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
