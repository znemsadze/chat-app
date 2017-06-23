chatApp.service("CommonServices", function ($http,$modal, $location) {

    this.startLoading = function (scopeInstance) {
        scopeInstance.modalInstance = $modal.open({
            templateUrl: 'LoadingModal.html',
            backdrop: 'static',
            keyboard: false,
            windowClass: 'center-modal'

        });
    };

    this.stopLoading = function (scopeInstance) {
        scopeInstance.modalInstance.close();
    }

});