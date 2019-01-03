<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp" %>
<!DOCTYPE html>
<html lang="zh">
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no"/>
    <meta name="renderer" content="webkit">
    <title>水印录入</title>
    <link rel="stylesheet" href="${ctxHtml}/css/bootstrap.min.css">
    <link rel="stylesheet" href="${ctxHtml}/css/licence.css">
    <script language="JavaScript" src="${ctxHtml}/js/jquery-1.9.1.min.js"></script>
    <script src="${ctxHtml}/js/bootstrap.min.js"></script>
    <script type="text/javascript" src="${ctxHtml}/js/jquery-form.js"></script>
    <style>
        .table > tbody > tr > td, .table > tbody > tr > th, .table > tfoot > tr > td, .table > tfoot > tr > th, .table > thead > tr > td, .table > thead > tr > th {
            border: none;
        }

        td {
            text-align: right;
        }
        .box {
            margin: 0;
            width: 100%;
        }
        .clearfix {
            margin-bottom: 10px;
            min-width: 300px;
        }
        input[type="text"], input[type="number"] {
            height: 30px;
            line-height: 30px;
            width: 65%;
        }
        .upload {
            width: 24%;
            margin: 0;
        }
        input[type="file"]{
            position: absolute;
            top: 0;
            left: 0;
            right: 0;
            opacity: 0;
            height: 30px;
            filter:progid:DXImageTransform.Microsoft.Alpha(opacity=0);
        }
    </style>
</head>
<body>
    <ul class="nav nav-tabs">
        <li><a href="${ctx}/water/page">水印管理</a></li>
        <li class="active"><a href="${ctx}/water/form?id=${water.id}">水印${not empty water.id?'修改':'添加'}</a></li>
    </ul><br/>
<div style="width: 600px;margin: 20px 20%">
    <h3 style="text-align: center">水印录入</h3>
    <form:form modelAttribute="water" id="form-water" action="${ctx}/water/save" enctype="multipart/form-data">
        <form:hidden path="id"/>
        <table class="table">
            <tr>
                <td>水印名称：</td>
                <td style="text-align: left">
                    <form:input path="name" id="name" type="text" class="form-control"/>
                </td>
            </tr>
            <tr>
                <td>水印图片：</td>
                <td style="text-align: left">
                    <c:if test="${not empty water.id}">
                        <form:input type="hidden" path="filePath" />
                        <img src="/img${water.filePath}" alt="" style="max-width: 200px">
                    </c:if>
                    <div class="box">
                        <div class="clearfix" style="position:relative;">
                            <input type="button" class="btn" value="选择文件">
                            <input type="text" id="text" readonly style="max-width: 150px">
                            <input class='upload' name="file" type='file'
                                   accept="image/jpeg,image/png,image/bmp,.doc,.docx,.tiff,.gif">
                            <input style="display: none" class="btn btn-primary showimg" data-toggle="modal"
                                   data-target=".bs-example-modal-lg" onclick="showimg(this)" type="button" value="查看">
                        </div>
                    </div>
                    <c:if test="${not empty water.id}">
                        *若不修改请不要选择文件
                    </c:if>
                </td>
            </tr>
            <tr>
                <td>备注：</td>
                <td>
                    <form:textarea path="remarks" class="form-control" rows="5"></form:textarea>
                </td>
            </tr>
        </table>
    </form:form>
    <c:if test="${not empty water.id}">
        <button id="submit" class="btn btn-default" style="width: 10%;margin-left: 45%" onclick="submit()">修改</button>
    </c:if>
    <c:if test="${empty water.id}">
        <button id="submit" class="btn btn-default" style="width: 10%;margin-left: 45%" onclick="submit()">新增</button>
    </c:if>
</div>
<!--模态框-->
<div class="modal fade bs-example-modal-lg" tabindex="-1" role="dialog" aria-labelledby="myLargeModalLabel">
    <div class="modal-dialog modal-lg" role="document">
        <div class="modal-content">
            <div class="modal-header" style="border: 0;padding-right: 10%">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                    <span aria-hidden="true"><img src="${ctxHtml}/images/close.png"></span></button>
            </div>
            <img style="width: 100%" id="modalimg">
        </div>
    </div>
</div>
</body>
</html>
<script type="text/javascript">
    function showimg(a){
        var isIE = navigator.userAgent.match(/MSIE/)!= null,
            isIE8 = navigator.userAgent.match(/MSIE 8.0/)!= null;
        if(isIE8){
            $(".modalBox").css("display", "block");
            document.getElementById("modalimg").style.filter = "progid:DXImageTransform.Microsoft.AlphaImageLoader(sizingMethod='scale',src=\"" + a.name + "\")";
            document.getElementById("modalimg").style.width = "90%";
        }
        else{
            $(".modalBox").css("display", "block");
            document.getElementById("modalimg").src = a.name;
        }
    }
    $(function(){
        $(document).on("change","input[type='file']",function(e){
            var files = $(e.target);
            var text = $(e.target).prev();
            var showimg = $(e.target).next();
            var determine = new Array();
            var a = false;
            var arr = ["jpg","JPG","png","PNG","jpeg","JPEG","bmp","BMP","gif","GIF"];
            var textname = new Array();
            textname = files.val().split("\\");
            determine = files.val().split(".");
            text.val(files.val());
            for(var i = 0;i < arr.length;i++){
                if(determine[1] == arr[i]){
                    a = true;
                }
            }
            if(files.val() == ""){
                showimg.hide();
            }else if(!a){
                text.val(textname[textname.length-1]);
//                    text.show();
                showimg.hide();
            }else{
                text.val(textname[textname.length-1]);
//                    text.hide();
                showimg.show();
            }

            var temp = $(files.parents("td").find("input[type='file']"));
            var index = $(files.parents("div")).index();
            for(var i = 0 ; i < temp.length;i++){
                if(files.val() == temp.eq(i).val() && index != i){
                    alert("上传文件重复！");
                    files.val("");
                    text.val("");
                    showimg.css("display","none");
                }
            }

            var fil = this.files;
            var file = $(this);
            /*var pic = $("#modalimg");*/
            /*var pic = file.next("img");*/
            var isIE = navigator.userAgent.match(/MSIE/)!= null,
                isIE6 = navigator.userAgent.match(/MSIE 6.0/)!= null;
            if(isIE) {
                file.select();
                var reallocalpath = document.selection.createRange().text;
                showimg.attr('name',reallocalpath);
            }else {
                var reader = new FileReader();
                reader.readAsDataURL(fil[0]);
                reader.onload = function () {
                    showimg.attr('name', reader.result);
                }
            }

        });

        $(document).on("click",".modalBox",function(){
            $(this).css("display", "none");
        });
        //图片上传添加按钮
        $(".addhtml").click(function(){
            var hasfile = $(this).prev("div").children("input[type='file']").val();
            var textval = $(this).prev("div").children("input[type='text']").val();
            var checked = $(this).parents("td");
            var a = checked.find("input[type = 'text']");
            if((a.length/2) == 5){
                alert("最多只能上传五个文件。");
                return false;
            }
            var x = true;
            for(var i = 0 ; i < a.length;i++){
                if(a.eq(i).val() == "" || a.eq(i).val() == null){
                    x = false;
                }
            }
            if(x){
                $(this).before("<div class='clearfix' style='position:relative;'><input type='text' name='fileType' value='"+ textval +"' style='display:none'><input type='button' class='btn' value='选择文件'><input type='text' style='margin-left: 4px;max-width: 150px;height: 30px;line-height: 30px' readonly> <input class='upload' name='file' type='file'> <input style='display: none' class='btn btn-primary showimg'data-toggle='modal' data-target='.bs-example-modal-lg' type='button' onclick='showimg(this)' value='查看'><input type='button' value='×' class='btn del'></div>")
            }else{
                alert("上一项未填！")
            }
        });
        $(document).on("click",".del",function(){
            var a = $(this).parent();
            a.remove();
        });

    });
</script>
<script>
    $(function () {
        <%--水印上传异步提交--%>
        $("#form-water").ajaxForm({
            dataType: 'json',
            type:"post",
            url: "${ctx}/water/save",
            success: function (result) {
                alert(result.message);
                if (result.code == 200) {
                    $(".nav-list",window.parent.document).children("li").removeClass("active");
                    $(".nav-list",window.parent.document).children("li:eq(2)").addClass("active");
                    location.href = "${ctx}/water/page";
                }else{
                    $('#submit').removeAttr("disabled");
                }
            },
            error: function () {
                $('#submit').removeAttr("disabled");
                alert("出错了，请重试!");
            }
        });
    });
    <%--水印上传表单验证--%>
    function submit() {
        $('#submit').attr("disabled", "disabled");
        if($("#name").val() == ""){
            alert("水印名称不能为空!");
            $('#submit').removeAttr("disabled");
            return false;
        }
        <c:if test="${empty water.id}">
        if($("#text").val() == ""){
            alert("请上传水印!");
            $('#submit').removeAttr("disabled");
            return false;
        }
        </c:if>
        $("#form-water").submit();
        <%--var formdata = new FormData($('#form-water')[0]);--%>
        <%--$.ajax({--%>
            <%--type: "POST",--%>
            <%--url: "${ctx}/water/save",--%>
            <%--data: formdata,--%>
            <%--dataType: "JSON",--%>
            <%--success: function (result) {--%>
                <%--result = JSON.parse(result);--%>
                <%--alert(result.message);--%>
                <%--if (result.code == 200) {--%>
                    <%--location.href = "${ctx}/water/page";--%>
                <%--}--%>
            <%--},--%>
            <%--error: function () {--%>
                <%--alert("出错了，请重试!");--%>
            <%--}--%>
        <%--});--%>
    }
</script>