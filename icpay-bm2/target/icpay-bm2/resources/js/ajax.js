var $ = {};
$.ajax2 = function (options) {
    //1.獲取參數
    var type = options.type.toUpperCase() || 'GET';
    var resDataType = options.resDataType || 'string';
    var reqDataType = options.reqDataType || 'string';
    var url = options.url;
    var data = options.data;
    var success = options.success;
    var fail = options.fail;
    var progress = options.progress;
    var imgType = options.imgType || 'jpg';

    //2.獲取xhr對象
    var xhr = $.getXhr();

    //3.創建連接
    xhr.open(type, url);
    /*指定返回數據的格式需要在發送請求之前*/
    if (resDataType === 'blob') {
        xhr.responseType = 'blob';
    }

    //4.發送請求
    if (type === 'GET') {
        xhr.send(null)
    } else if (type === 'POST') {
        if (progress) {
            xhr.upload.onprogress = progress;
        }
        if (reqDataType === 'json') {
            xhr.setRequestHeader('Content-Type', 'application/json;charset=UTF-8');
            data = JSON.stringify(data); //只能發送字符串格式的json,不能直接發送json
        }
        if (reqDataType === 'string') {
            xhr.setRequestHeader('Content-Type', 'application/x-www-form-urlencoded');
        }
        xhr.send(data);
    }

    //5.接收數據
    xhr.onreadystatechange = function () {
        if (this.readyState === 4 && (this.status >= 200 && this.status < 300)) {
            var res;
            if (resDataType === 'json') {
                res = JSON.parse(this.responseText);
                success.call(this, res, this.responseXML)
            }
            if (resDataType === 'blob') {
                res = new Blob([this.response], {
                    type: 'image/' + imgType
                });
                success.call(this, res)
            }

        }
    };
};