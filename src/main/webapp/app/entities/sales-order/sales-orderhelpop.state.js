(function() {
    'use strict';

    angular
        .module('helpopApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('sales-orderhelpop', {
            parent: 'entity',
            url: '/sales-orderhelpop',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'helpopApp.salesOrder.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/sales-order/sales-ordershelpop.html',
                    controller: 'SalesOrderHelpopController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('salesOrder');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('sales-orderhelpop-detail', {
            parent: 'entity',
            url: '/sales-orderhelpop/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'helpopApp.salesOrder.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/sales-order/sales-orderhelpop-detail.html',
                    controller: 'SalesOrderHelpopDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('salesOrder');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'SalesOrder', function($stateParams, SalesOrder) {
                    return SalesOrder.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'sales-orderhelpop',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('sales-orderhelpop-detail.edit', {
            parent: 'sales-orderhelpop-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/sales-order/sales-orderhelpop-dialog.html',
                    controller: 'SalesOrderHelpopDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['SalesOrder', function(SalesOrder) {
                            return SalesOrder.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('sales-orderhelpop.new', {
            parent: 'sales-orderhelpop',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/sales-order/sales-orderhelpop-dialog.html',
                    controller: 'SalesOrderHelpopDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                orderedAt: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('sales-orderhelpop', null, { reload: 'sales-orderhelpop' });
                }, function() {
                    $state.go('sales-orderhelpop');
                });
            }]
        })
        .state('sales-orderhelpop.edit', {
            parent: 'sales-orderhelpop',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/sales-order/sales-orderhelpop-dialog.html',
                    controller: 'SalesOrderHelpopDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['SalesOrder', function(SalesOrder) {
                            return SalesOrder.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('sales-orderhelpop', null, { reload: 'sales-orderhelpop' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('sales-orderhelpop.delete', {
            parent: 'sales-orderhelpop',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/sales-order/sales-orderhelpop-delete-dialog.html',
                    controller: 'SalesOrderHelpopDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['SalesOrder', function(SalesOrder) {
                            return SalesOrder.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('sales-orderhelpop', null, { reload: 'sales-orderhelpop' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
