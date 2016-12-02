(function() {
    'use strict';

    angular
        .module('helpopApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('sales-order-itemhelpop', {
            parent: 'entity',
            url: '/sales-order-itemhelpop',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'helpopApp.salesOrderItem.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/sales-order-item/sales-order-itemshelpop.html',
                    controller: 'SalesOrderItemHelpopController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('salesOrderItem');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('sales-order-itemhelpop-detail', {
            parent: 'entity',
            url: '/sales-order-itemhelpop/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'helpopApp.salesOrderItem.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/sales-order-item/sales-order-itemhelpop-detail.html',
                    controller: 'SalesOrderItemHelpopDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('salesOrderItem');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'SalesOrderItem', function($stateParams, SalesOrderItem) {
                    return SalesOrderItem.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'sales-order-itemhelpop',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('sales-order-itemhelpop-detail.edit', {
            parent: 'sales-order-itemhelpop-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/sales-order-item/sales-order-itemhelpop-dialog.html',
                    controller: 'SalesOrderItemHelpopDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['SalesOrderItem', function(SalesOrderItem) {
                            return SalesOrderItem.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('sales-order-itemhelpop.new', {
            parent: 'sales-order-itemhelpop',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/sales-order-item/sales-order-itemhelpop-dialog.html',
                    controller: 'SalesOrderItemHelpopDialogController',
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
                    $state.go('sales-order-itemhelpop', null, { reload: 'sales-order-itemhelpop' });
                }, function() {
                    $state.go('sales-order-itemhelpop');
                });
            }]
        })
        .state('sales-order-itemhelpop.edit', {
            parent: 'sales-order-itemhelpop',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/sales-order-item/sales-order-itemhelpop-dialog.html',
                    controller: 'SalesOrderItemHelpopDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['SalesOrderItem', function(SalesOrderItem) {
                            return SalesOrderItem.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('sales-order-itemhelpop', null, { reload: 'sales-order-itemhelpop' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('sales-order-itemhelpop.delete', {
            parent: 'sales-order-itemhelpop',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/sales-order-item/sales-order-itemhelpop-delete-dialog.html',
                    controller: 'SalesOrderItemHelpopDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['SalesOrderItem', function(SalesOrderItem) {
                            return SalesOrderItem.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('sales-order-itemhelpop', null, { reload: 'sales-order-itemhelpop' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
