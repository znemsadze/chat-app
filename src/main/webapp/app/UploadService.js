chatApp.service("UploadService",function ($http ) {

    this.uploadFile = function(files,$scope,successcb,errorcb ) {
        var fd = new FormData();
        //Take the first selected file
        fd.append("file", files[0]);
        var uploadUrl="upload-file"
        $http.post(uploadUrl, fd, {
            withCredentials: true,
            headers: {'Content-Type': undefined },
            transformRequest: angular.identity
        }).success(function(data){
             console.log(data);
             successcb(data);

        }).error( function (data) {
            errorcb(data);
        }).finally(function(){
            $scope.actionDisabled=false;
            $scope.saveButtonClass="";
        });
    };






})