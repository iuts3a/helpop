(function() {
    'use strict';

    angular
        .module('helpopApp')
        .controller('SalesQuoteHelpopController', SalesQuoteHelpopController);

    SalesQuoteHelpopController.$inject = ['$scope', '$state', 'SalesQuote'];

    function SalesQuoteHelpopController ($scope, $state, SalesQuote) {
        var vm = this;

        vm.salesQuotes = [];

        loadAll();

        function loadAll() {
            SalesQuote.query(function(result) {
                vm.salesQuotes = result;
                vm.searchQuery = null;
            });
        }
    }
})();
