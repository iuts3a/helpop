(function() {
    'use strict';

    angular
        .module('helpopApp')
        .controller('CustomerHelpopDialogController', CustomerHelpopDialogController);

    CustomerHelpopDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', '$q', 'entity', 'Customer', 'SalesQuote', 'Address', 'SalesOrder'];

    function CustomerHelpopDialogController ($timeout, $scope, $stateParams, $uibModalInstance, $q, entity, Customer, SalesQuote, Address, SalesOrder) {
        var vm = this;

        vm.customer = entity;
        vm.clear = clear;
        vm.save = save;
        vm.salesquotes = SalesQuote.query({filter: 'customer-is-null'});
        $q.all([vm.customer.$promise, vm.salesquotes.$promise]).then(function() {
            if (!vm.customer.salesQuoteId) {
                return $q.reject();
            }
            return SalesQuote.get({id : vm.customer.salesQuoteId}).$promise;
        }).then(function(salesQuote) {
            vm.salesquotes.push(salesQuote);
        });
        vm.addresses = Address.query({filter: 'customer-is-null'});
        $q.all([vm.customer.$promise, vm.addresses.$promise]).then(function() {
            if (!vm.customer.addressId) {
                return $q.reject();
            }
            return Address.get({id : vm.customer.addressId}).$promise;
        }).then(function(address) {
            vm.addresses.push(address);
        });
        vm.salesorders = SalesOrder.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.customer.id !== null) {
                Customer.update(vm.customer, onSaveSuccess, onSaveError);
            } else {
                Customer.save(vm.customer, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('helpopApp:customerUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
