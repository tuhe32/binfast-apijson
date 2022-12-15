function deleteEmptyProperty(object){
    for (var i in object) {
        var value = object[i];
        if (typeof value === 'object') {
            if (Array.isArray(value)) {
                if (value.length == 0) {
                    delete object[i];
                    continue;
                }
            }
            this.deleteEmptyProperty(value);
            if (this.isEmpty(value)) {
                delete object[i];
            }
        } else {
            if (value === '' || value === null || value === undefined) {
                delete object[i];
            } else {
            }
        }
    }
}


function isEmpty(object) {
    for (var name in object) {
        return false;
    }
    return true;
}
var $ = layui.$
function fetch(url, paras, callback) {
    $.ajax({
        url: url,
        type: "POST",
        contentType: "application/json; charset=utf-8",
        dataType : "json",
        data: JSON.stringify(paras),
        success:function(resp){
            if(resp.code !== 200){
                layer.alert(resp.msg, { title: '保存失败', icon: 2 })
            }else{
                if (callback) {
                    callback(resp)
                } else {
                    layer.alert(resp.msg, { title: '保存成功', icon: 1 })
                }
            }
        },
        error:function(resp){
            layer.alert(resp.msg, { title: '保存失败', icon: 2 })
        }
    })
}


function tableOption (listName) {
    return {
        elem: '#currentTableId',
        url: '/get',
        method: 'post',
        contentType: 'application/json',
        where: search,
        parseData: function(res){ //res 即为原始返回的数据
            return {
                "code": res.code, //解析接口状态
                "msg": res.msg, //解析提示文本
                "total": res.total, //解析数据长度
                "data": res[listName] //解析数据列表
            };
        },
        response: {
            statusCode: 200, //规定成功的状态码，默认：0
            countName: 'total' //规定数据总数的字段名称，默认：count
        },
        before: function (options) {
            search[listName]["page"] = (options.page.curr || options.index) - 1
            search[listName]["count"] = (options.page.limit || options.limit)
            options.where = search
        },
        done: function (res, curr, count) {
            console.log(res)
            if (res.code === 401) {
                window.location.replace("/showLogin")
            }
        },
        toolbar: '#toolbarDemo',
        defaultToolbar: ['filter', 'exports', 'print', {
            title: '提示',
            layEvent: 'LAYTABLE_TIPS',
            icon: 'layui-icon-tips'
        }],
        cols: [],
        limits: [10, 15, 20, 25, 50, 100],
        limit: 15,
        page: true,
        skin: 'line'
    }
}