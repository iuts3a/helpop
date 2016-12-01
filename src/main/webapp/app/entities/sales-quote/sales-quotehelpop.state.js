(function() {
    'use strict';

    angular
        .module('helpopApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('sales-quotehelpop', {
            parent: 'entity',
            url: '/sales-quotehelpop',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'helpopApp.salesQuote.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/sales-quote/sales-quoteshelpop.html',
                    controller: 'SalesQuoteHelpopController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('salesQuote');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('sales-quotehelpop-detail', {
            parent: 'entity',
            url: '/sales-quotehelpop/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'helpopApp.salesQuote.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/sales-quote/sales-quotehelpop-detail.html',
                    controller: 'SalesQuoteHelpopDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('salesQuote');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'SalesQuote', function($stateParams, SalesQuote) {
                    return SalesQuote.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'sales-quotehelpop',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('sales-quotehelpop-detail.edit', {
            parent: 'sales-quotehelpop-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/sales-quote/sales-quotehelpop-dialog.html',
                    controller: 'SalesQuoteHelpopDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['SalesQuote', function(SalesQuote) {
                            return SalesQuote.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('sales-quotehelpop.new', {
            parent: 'sales-quotehelpop',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/sales-quote/sales-quotehelpop-dialog.html',
                    controller: 'SalesQuoteHelpopDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                quotedAt: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('sales-quotehelpop', null, { reload: 'sales-quotehelpop' });
                }, function() {
                    $state.go('sales-quotehelpop');
                });
            }]
        })
        .state('sales-quotehelpop.edit', {
            parent: 'sales-quotehelpop',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/sales-quote/sales-quotehelpop-dialog.html',
                    controller: 'SalesQuoteHelpopDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['SalesQuote', function(SalesQuote) {
                            return SalesQuote.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('sales-quotehelpop', null, { reload: 'sales-quotehelpop' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('sales-quotehelpop.delete', {
            parent: 'sales-quotehelpop',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/sales-quote/sales-quotehelpop-delete-dialog.html',
                    controller: 'SalesQuoteHelpopDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['SalesQuote', function(SalesQuote) {
                            return SalesQuote.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('sales-quotehelpop', null, { reload: 'sales-quotehelpop' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
