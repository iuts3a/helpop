(function() {
    'use strict';

    angular
        .module('helpopApp')
        .controller('CustomerHelpopController', CustomerHelpopController);

    CustomerHelpopController.$inject = ['$scope', '$state', 'Customer'];

    function CustomerHelpopController ($scope, $state, Customer) {
        var vm = this;

        vm.customers = [];

        loadAll();

        function loadAll() {
            Customer.query(function(result) {
                vm.customers = result;
                vm.searchQuery = null;
            });
        }
    }
})();
