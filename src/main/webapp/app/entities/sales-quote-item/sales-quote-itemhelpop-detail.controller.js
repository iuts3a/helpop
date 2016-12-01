(function() {
    'use strict';

    angular
        .module('helpopApp')
        .controller('SalesQuoteItemHelpopDetailController', SalesQuoteItemHelpopDetailController);

    SalesQuoteItemHelpopDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'SalesQuoteItem'];

    function SalesQuoteItemHelpopDetailController($scope, $rootScope, $stateParams, previousState, entity, SalesQuoteItem) {
        var vm = this;

        vm.salesQuoteItem = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('helpopApp:salesQuoteItemUpdate', function(event, result) {
            vm.salesQuoteItem = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
