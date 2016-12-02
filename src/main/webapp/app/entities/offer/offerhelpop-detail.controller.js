(function() {
    'use strict';

    angular
        .module('helpopApp')
        .controller('OfferHelpopDetailController', OfferHelpopDetailController);

    OfferHelpopDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Offer', 'Customer'];

    function OfferHelpopDetailController($scope, $rootScope, $stateParams, previousState, entity, Offer, Customer) {
        var vm = this;

        vm.offer = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('helpopApp:offerUpdate', function(event, result) {
            vm.offer = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
