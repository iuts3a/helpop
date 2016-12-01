(function() {
    'use strict';

    angular
        .module('helpopApp')
        .controller('AddressHelpopController', AddressHelpopController);

    AddressHelpopController.$inject = ['$scope', '$state', 'Address'];

    function AddressHelpopController ($scope, $state, Address) {
        var vm = this;

        vm.addresses = [];

        loadAll();

        function loadAll() {
            Address.query(function(result) {
                vm.addresses = result;
                vm.searchQuery = null;
            });
        }
    }
})();
