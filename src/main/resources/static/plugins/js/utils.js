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