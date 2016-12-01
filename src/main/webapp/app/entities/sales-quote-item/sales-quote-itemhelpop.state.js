(function() {
    'use strict';

    angular
        .module('helpopApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('sales-quote-itemhelpop', {
            parent: 'entity',
            url: '/sales-quote-itemhelpop',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'helpopApp.salesQuoteItem.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/sales-quote-item/sales-quote-itemshelpop.html',
                    controller: 'SalesQuoteItemHelpopController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('salesQuoteItem');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('sales-quote-itemhelpop-detail', {
            parent: 'entity',
            url: '/sales-quote-itemhelpop/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'helpopApp.salesQuoteItem.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/sales-quote-item/sales-quote-itemhelpop-detail.html',
                    controller: 'SalesQuoteItemHelpopDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('salesQuoteItem');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'SalesQuoteItem', function($stateParams, SalesQuoteItem) {
                    return SalesQuoteItem.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'sales-quote-itemhelpop',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('sales-quote-itemhelpop-detail.edit', {
            parent: 'sales-quote-itemhelpop-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/sales-quote-item/sales-quote-itemhelpop-dialog.html',
                    controller: 'SalesQuoteItemHelpopDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['SalesQuoteItem', function(SalesQuoteItem) {
                            return SalesQuoteItem.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('sales-quote-itemhelpop.new', {
            parent: 'sales-quote-itemhelpop',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/sales-quote-item/sales-quote-itemhelpop-dialog.html',
                    controller: 'SalesQuoteItemHelpopDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                createdAt: null,
                                updatedAt: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('sales-quote-itemhelpop', null, { reload: 'sales-quote-itemhelpop' });
                }, function() {
                    $state.go('sales-quote-itemhelpop');
                });
            }]
        })
        .state('sales-quote-itemhelpop.edit', {
            parent: 'sales-quote-itemhelpop',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/sales-quote-item/sales-quote-itemhelpop-dialog.html',
                    controller: 'SalesQuoteItemHelpopDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['SalesQuoteItem', function(SalesQuoteItem) {
                            return SalesQuoteItem.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('sales-quote-itemhelpop', null, { reload: 'sales-quote-itemhelpop' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('sales-quote-itemhelpop.delete', {
            parent: 'sales-quote-itemhelpop',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/sales-quote-item/sales-quote-itemhelpop-delete-dialog.html',
                    controller: 'SalesQuoteItemHelpopDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['SalesQuoteItem', function(SalesQuoteItem) {
                            return SalesQuoteItem.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('sales-quote-itemhelpop', null, { reload: 'sales-quote-itemhelpop' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
