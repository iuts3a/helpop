(function() {
    'use strict';

    angular
        .module('helpopApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('customerhelpop', {
            parent: 'entity',
            url: '/customerhelpop',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'helpopApp.customer.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/customer/customershelpop.html',
                    controller: 'CustomerHelpopController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('customer');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('customerhelpop-detail', {
            parent: 'entity',
            url: '/customerhelpop/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'helpopApp.customer.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/customer/customerhelpop-detail.html',
                    controller: 'CustomerHelpopDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('customer');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'Customer', function($stateParams, Customer) {
                    return Customer.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'customerhelpop',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('customerhelpop-detail.edit', {
            parent: 'customerhelpop-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/customer/customerhelpop-dialog.html',
                    controller: 'CustomerHelpopDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Customer', function(Customer) {
                            return Customer.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('customerhelpop.new', {
            parent: 'customerhelpop',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/customer/customerhelpop-dialog.html',
                    controller: 'CustomerHelpopDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                firstName: null,
                                lastName: null,
                                password: null,
                                email: null,
                                consummerGrade: null,
                                offererGrade: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('customerhelpop', null, { reload: 'customerhelpop' });
                }, function() {
                    $state.go('customerhelpop');
                });
            }]
        })
        .state('customerhelpop.edit', {
            parent: 'customerhelpop',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/customer/customerhelpop-dialog.html',
                    controller: 'CustomerHelpopDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Customer', function(Customer) {
                            return Customer.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('customerhelpop', null, { reload: 'customerhelpop' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('customerhelpop.delete', {
            parent: 'customerhelpop',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/customer/customerhelpop-delete-dialog.html',
                    controller: 'CustomerHelpopDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Customer', function(Customer) {
                            return Customer.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('customerhelpop', null, { reload: 'customerhelpop' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
