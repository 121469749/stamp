$(function () {
    /*右上菜单*/
    $(".menu-btn").click(function () {
        $(".menu").slideToggle("slow");
    });

    /*上传文件-选择文件按钮*/
    $("#btn-choose").click(function () {
        $("#uploadFile").click();
    });
    /*验章-选择文件按钮*/
    $("#btn-check").click(function () {
        $("#checkSealFile").click();
    });
});
/*上传文件*/
function uploadModal() {
    $("#uploadFileModal").fadeIn();
}
function filepath(fp) {
    var filename = $(fp).val().split("\\");
    filename = filename[filename.length - 1];
    $(fp).prev().text(filename);
}
function closeModal() {
    $("#uploadFileModal").fadeOut();
}

/*验章*/
function checkSealModal() {
    $("#checkSealModal").fadeIn();
}
function closeCheckSealModal() {
    $("#checkSealModal").fadeOut();
}
function checkSeal(ctx) {
    $("#btn-checkSeal").attr("disabled", "true"); //设置变灰按钮
    setTimeout("$('#btn-checkSeal').removeAttr('disabled')", 5000); //设置五秒后提交按钮显示
    var formdata = new FormData($("#checkSealForm")[0]);
    $.ajax({
        url: ctx+"/a/check/document",
        type: "POST",
        data: formdata,
        dataType: "json",
        cache: false,
        processData: false,
        contentType: false,
        success: function (result) {
            alert(result.message);
            if(result.code == 200){
                var date = changeDate(result.entity.sinatrueDate);
                var undoneUrl = ctx + "/document/info/undone?document=" + result.entity.document.id;
                var doneUrl = ctx + "/document/info/done?document=" + result.entity.document.id;
                var s ='<div><img src="/file' +result.entity.seal.sealPath + '" style="height: 140px"></div>'+
                    '<div><span>文件名：' + result.entity.document.fileName + '</span></div>'+
                    '<div><span>备注：' + result.entity.document.remarks + '</span></div>'+
                    '<div><span>签章日期：' + date + '</span></div>'+
                    '<div style="padding-top: 20px">'+
                    '<button class="btn-choose" type="button" onclick="' + undoneUrl +'">查看原文件</button>'+
                    '<button class="btn-choose" type="button" onclick="' + doneUrl +'">查看已签章文件</button></div>';
                $("#checkSealForm").html(s);
            }
        },
        error: function () {
            alert("error")
        }
    })
}
function changeDate(date) {
    var jsondate="/Date(" + date + ")/";
    var formatdate=eval(jsondate.replace(/\/Date\((\d+)\)\//gi, "new Date($1)"));
    return formatdate.toLocaleDateString();
}

/*点击底部文档签章*/
function toFileList(url) {
    alert("请先选择文件再进行签章！");
    location.href = url;
}