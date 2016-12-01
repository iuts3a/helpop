(function() {
    'use strict';

    angular
        .module('helpopApp')
        .controller('SalesQuoteItemHelpopController', SalesQuoteItemHelpopController);

    SalesQuoteItemHelpopController.$inject = ['$scope', '$state', 'SalesQuoteItem'];

    function SalesQuoteItemHelpopController ($scope, $state, SalesQuoteItem) {
        var vm = this;

        vm.salesQuoteItems = [];

        loadAll();

        function loadAll() {
            SalesQuoteItem.query(function(result) {
                vm.salesQuoteItems = result;
                vm.searchQuery = null;
            });
        }
    }
})();
