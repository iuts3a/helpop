(function() {
    'use strict';

    angular
        .module('helpopApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('offerhelpop', {
            parent: 'entity',
            url: '/offerhelpop',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'helpopApp.offer.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/offer/offershelpop.html',
                    controller: 'OfferHelpopController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('offer');
                    $translatePartialLoader.addPart('offerTimeType');
                    $translatePartialLoader.addPart('offerType');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('offerhelpop-detail', {
            parent: 'entity',
            url: '/offerhelpop/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'helpopApp.offer.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/offer/offerhelpop-detail.html',
                    controller: 'OfferHelpopDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('offer');
                    $translatePartialLoader.addPart('offerTimeType');
                    $translatePartialLoader.addPart('offerType');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'Offer', function($stateParams, Offer) {
                    return Offer.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'offerhelpop',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('offerhelpop-detail.edit', {
            parent: 'offerhelpop-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/offer/offerhelpop-dialog.html',
                    controller: 'OfferHelpopDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Offer', function(Offer) {
                            return Offer.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('offerhelpop.new', {
            parent: 'offerhelpop',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/offer/offerhelpop-dialog.html',
                    controller: 'OfferHelpopDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                name: null,
                                priceUSD: null,
                                discountPercent: null,
                                createdAt: null,
                                limitAt: null,
                                grade: null,
                                timeType: null,
                                offerType: null,
                                description: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('offerhelpop', null, { reload: 'offerhelpop' });
                }, function() {
                    $state.go('offerhelpop');
                });
            }]
        })
        .state('offerhelpop.edit', {
            parent: 'offerhelpop',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/offer/offerhelpop-dialog.html',
                    controller: 'OfferHelpopDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Offer', function(Offer) {
                            return Offer.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('offerhelpop', null, { reload: 'offerhelpop' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('offerhelpop.delete', {
            parent: 'offerhelpop',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/offer/offerhelpop-delete-dialog.html',
                    controller: 'OfferHelpopDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Offer', function(Offer) {
                            return Offer.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('offerhelpop', null, { reload: 'offerhelpop' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
