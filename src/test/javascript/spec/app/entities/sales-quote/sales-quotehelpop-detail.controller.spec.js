'use strict';

describe('Controller Tests', function() {

    describe('SalesQuote Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockPreviousState, MockSalesQuote, MockSalesQuoteItem;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockPreviousState = jasmine.createSpy('MockPreviousState');
            MockSalesQuote = jasmine.createSpy('MockSalesQuote');
            MockSalesQuoteItem = jasmine.createSpy('MockSalesQuoteItem');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity,
                'previousState': MockPreviousState,
                'SalesQuote': MockSalesQuote,
                'SalesQuoteItem': MockSalesQuoteItem
            };
            createController = function() {
                $injector.get('$controller')("SalesQuoteHelpopDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'helpopApp:salesQuoteUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
