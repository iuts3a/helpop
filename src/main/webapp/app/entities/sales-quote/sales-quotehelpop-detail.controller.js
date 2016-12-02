(function() {
    'use strict';

    angular
        .module('helpopApp')
        .controller('SalesQuoteHelpopDetailController', SalesQuoteHelpopDetailController);

    SalesQuoteHelpopDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'SalesQuote', 'SalesQuoteItem'];

    function SalesQuoteHelpopDetailController($scope, $rootScope, $stateParams, previousState, entity, SalesQuote, SalesQuoteItem) {
        var vm = this;

        vm.salesQuote = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('helpopApp:salesQuoteUpdate', function(event, result) {
            vm.salesQuote = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
