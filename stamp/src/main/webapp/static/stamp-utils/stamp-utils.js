

// form表单自动填充  for example $("#frmApplyInfo").jsonData(data);
// 使用方法, #后填写要自动填充的表单, data为后台请求返回的json数据
$.fn.jsonData = function(data){
    return this.each(function(){
        var formElem, name;
        if(data == null){this.reset(); return; }
        for(var i = 0; i < this.length; i++){
            formElem = this.elements[i];
            name = (formElem.type == "checkbox")? formElem.name.replace(/(.+)\[\]$/, "$1") : formElem.name;
            if(data[name] == undefined) continue;
            switch(formElem.type){
                case "checkbox":
                    if(data[name] == ""){
                        formElem.checked = false;
                    }else{
                        if(data[name].indexOf(formElem.value) > -1){
                            formElem.checked = true;
                        }else{
                            formElem.checked = false;
                        }
                    }
                    break; 
                case "radio":
                    if(data[name] == ""){
                        formElem.checked = false;
                    }else if(formElem.value == data[name]){
                        formElem.checked = true;
                    }
                    break;
                case "button": break;
                default: formElem.value = data[name];
            }
        }
    });
};

//根据value和type查找label
function getDictLabel(value ,type){
    var label = null;



    $.ajax({
        url: ctx + "/stampUtil/getDictLabel",
        type: "GET",
        data: {"value":value,"type":type},
        dataType:"json",
        async:false,
        success:function(dirInfo){
            label = dirInfo.label
        },
        error:function (result) {
            alert("网络繁忙,请重试!error");
        }
    });
    return label;
};

// 根据数据字典的type获取整个列表对象
function getDictList(type){
 var dictList = null;
    $.ajax({
        url: "/stampUtil/getDictList",
        type: "GET",
        data: {"type":type},
        dataType:"json",
        async:false,
        success:function(dirList){
            dictList =  dirList;
        },
        error:function () {
        alert("网络繁忙,请重试!error");
    },
});
return dictList;
};