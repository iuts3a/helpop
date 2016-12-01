(function() {
    'use strict';

    angular
        .module('helpopApp')
        .controller('SalesOrderHelpopDialogController', SalesOrderHelpopDialogController);

    SalesOrderHelpopDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'SalesOrder', 'SalesOrderItem'];

    function SalesOrderHelpopDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, SalesOrder, SalesOrderItem) {
        var vm = this;

        vm.salesOrder = entity;
        vm.clear = clear;
        vm.datePickerOpenStatus = {};
        vm.openCalendar = openCalendar;
        vm.save = save;
        vm.salesorderitems = SalesOrderItem.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.salesOrder.id !== null) {
                SalesOrder.update(vm.salesOrder, onSaveSuccess, onSaveError);
            } else {
                SalesOrder.save(vm.salesOrder, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('helpopApp:salesOrderUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }

        vm.datePickerOpenStatus.orderedAt = false;

        function openCalendar (date) {
            vm.datePickerOpenStatus[date] = true;
        }
    }
})();
