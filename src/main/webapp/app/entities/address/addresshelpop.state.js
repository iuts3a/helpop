(function() {
    'use strict';

    angular
        .module('helpopApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('addresshelpop', {
            parent: 'entity',
            url: '/addresshelpop',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'helpopApp.address.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/address/addresseshelpop.html',
                    controller: 'AddressHelpopController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('address');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('addresshelpop-detail', {
            parent: 'entity',
            url: '/addresshelpop/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'helpopApp.address.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/address/addresshelpop-detail.html',
                    controller: 'AddressHelpopDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('address');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'Address', function($stateParams, Address) {
                    return Address.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'addresshelpop',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('addresshelpop-detail.edit', {
            parent: 'addresshelpop-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/address/addresshelpop-dialog.html',
                    controller: 'AddressHelpopDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Address', function(Address) {
                            return Address.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('addresshelpop.new', {
            parent: 'addresshelpop',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/address/addresshelpop-dialog.html',
                    controller: 'AddressHelpopDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                countryId: null,
                                city: null,
                                street1: null,
                                street2: null,
                                postCode: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('addresshelpop', null, { reload: 'addresshelpop' });
                }, function() {
                    $state.go('addresshelpop');
                });
            }]
        })
        .state('addresshelpop.edit', {
            parent: 'addresshelpop',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/address/addresshelpop-dialog.html',
                    controller: 'AddressHelpopDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Address', function(Address) {
                            return Address.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('addresshelpop', null, { reload: 'addresshelpop' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('addresshelpop.delete', {
            parent: 'addresshelpop',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/address/addresshelpop-delete-dialog.html',
                    controller: 'AddressHelpopDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Address', function(Address) {
                            return Address.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('addresshelpop', null, { reload: 'addresshelpop' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
