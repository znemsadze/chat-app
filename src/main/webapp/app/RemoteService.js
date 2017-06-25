/**
 * Created by zviad on 6/25/17.
 * main service for remote http requests
 */
chatApp.service("RemoteService", function ($http) {
    var instance = this;
    this.postObj = function (url, obj, successcb, errorcb, fincb) {
        var req = {
            method: 'POST',
            url: url,
            headers: {
                'Content-Type': 'application/json'
            },
            data: JSON.stringify(obj)
        };
        $http(req).then(function (data) {
            if (successcb != null && successcb != undefined) {
                successcb(data);
            }
        }, function (data) {
            if (errorcb != null && errorcb != undefined) {
                errorcb(data);
            }

        }).finally(function () {
            fincb();
        });
    }

    this.requestGet = function (url, params, successcb, errorcb, fincb) {
        instance.requestBase(url, "GET", params, successcb, errorcb, fincb);
    }
    this.requestPost = function (url, params, successcb, errorcb, fincb) {
        instance.requestBase(url, "POST", params, successcb, errorcb, fincb);
    }

    this.requestBase = function (url, methValue, params, successcb, errorcb, fincb) {
        var req = {
            method: methValue,
            url: url,
            headers: {
                'Content-Type': 'application/json'
            }
        };
        if (params != null) {
            req.params = params;
        }
        ;

        $http(req).then(function (data) {
            if (successcb != null && successcb != undefined) {
                successcb(data);
            }
        }, function (data) {
            if (errorcb != null && errorcb != undefined) {
                errorcb(data);
            }
        }).finally(function () {
            if (fincb != null && fincb != undefined) {
                fincb();
            }

        });
    }


});