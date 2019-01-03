<%--
  Created by IntelliJ IDEA.
  User: 77426
  Date: 2017/7/14
  Time: 14:32
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp" %>
<!DOCTYPE html>
<head lang="zh">
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no"/>
    <title>图片相似度查询</title>
    <link rel="stylesheet" href="${ctxHtml}/css/bootstrap.min.css"/>
    <script type="text/javascript" src="${ctxHtml}/js/jquery-1.9.1.min.js"></script>
    <script type="text/javascript" src="${ctxHtml}/js/jquery-form.js"></script>
</head>

<script >
    $(document).ready(function(){
        //TODO
        $("#form1").ajaxForm({
            url: "${ctx}/picture/comparePicture1",
            type:"post",
            dataType: "json",
            success: function (result) {
                $("#btn-first").removeAttr("disabled");
                $("#text1").text(result.message);

            },
            error: function () {
                $("#btn-first").removeAttr("disabled");
                $("#text1").text("出错了！请联系管理员！");
            }
        });
        $("#btn-first").click(function () {
            $(this).attr("disabled","disabled");
            $("#form1").submit();
        });
        $("#form2").ajaxForm({
            dataType: "json",
            type:"post",
            url: "${ctx}/picture/comparePicture2",
            success: function (result) {
                $("#btn-second").removeAttr("disabled");
                $("#text2").text(result.message);
            },
            error: function () {
                $("#btn-second").removeAttr("disabled");
                $("#text2").text("出错了！请联系管理员！");

            }
        });
        $("#btn-second").click(function () {
            $(this).attr("disabled","disabled");
            $("#form2").submit();
        });

    });

</script>
<body style="width: 80%;margin: 25px auto">
<h4>相似度查询</h4>
<table class="table-bordered table" style="width: 100%">
    <form enctype="multipart/form-data" action="${ctx}/picture/comparePicture1" method="post" id="form1">
        <tr>
            <th>图片一</th>
            <th>图片二</th>
        </tr>
        <tr>
            <td><input type="file" onchange="changeimg1(this)" name="picture"/></td>
            <td><input type="file" onchange="changeimg2(this)" name="picture"/></td>
        </tr>
        <tr>
            <td><img width="300px" id="img1" src="" alt=""/></td>
            <td><img width="300px" id="img2" src="" alt=""/></td>
        </tr>
        <tr style="text-align: center">
            <td colspan="2">
                <button class="btn btn-primary" type="button" id="btn-first">相似度查询</button>
            </td>
        </tr>
        <tr>
            <td id="text1" style="color: red;font-size: 20px" colspan="2">
                请选择两张图片!
            </td>
        </tr>
    </form>
</table>
<table class="table-bordered table" style="width: 100%">
    <form enctype="multipart/form-data" action="${ctx}/picture/comparePicture2" method="post" id="form2">
        <tr>
            <th>图片一</th>
            <th>图片二</th>
        </tr>
        <tr>
            <td><input type="file" onchange="changeimg3(this)" name="picture"/></td>
            <td><input type="file" onchange="changeimg4(this)" name="picture"/></td>
        </tr>
        <tr>
            <td><img width="300px" id="img3" src="" alt=""/></td>
            <td><img width="300px" id="img4" src="" alt=""/></td>
        </tr>
        <tr style="text-align: center">
            <td colspan="2">
                <button class="btn btn-primary" type="button" id="btn-second">相似度查询</button>
            </td>
        </tr>
        <tr>
            <td id="text2" style="color: red;font-size: 20px" colspan="2">
                请选择两张图片!
            </td>
        </tr>
    </form>
</table>
<script>
    function changeimg1(e) {
        // var files = $(e.target);
        //var text = $(e.target).prev();
        // textname = files.val().split("\\");
        // determine = files.val().split(".");
        //text.val(files.val());
        // text.val(textname[textname.length-1]);
        var fil = e.files;
        var file = e;
        var isIE = navigator.userAgent.match(/MSIE/) != null,
            isIE6 = navigator.userAgent.match(/MSIE 6.0/) != null;
        if (isIE) {
            file.select();
            var reallocalpath = document.selection.createRange().text;
            $("#img1").attr('src', reallocalpath);
            document.getElementById("img1").style.filter = "progid:DXImageTransform.Microsoft.AlphaImageLoader(sizingMethod='scale',src=\"" + reallocalpath + "\")";
        } else {
            var reader = new FileReader();
            reader.readAsDataURL(fil[0]);
            reader.onload = function () {
                document.getElementById("img1").src = reader.result;
            }
        }
    }
    function changeimg2(e) {
        // var files = $(e.target);
        //var text = $(e.target).prev();
        // textname = files.val().split("\\");
        // determine = files.val().split(".");
        //text.val(files.val());
        // text.val(textname[textname.length-1]);
        var fil = e.files;
        var file = e;
        var isIE = navigator.userAgent.match(/MSIE/) != null,
            isIE6 = navigator.userAgent.match(/MSIE 6.0/) != null;
        if (isIE) {
            file.select();
            var reallocalpath = document.selection.createRange().text;
            $("#img2").attr('src', reallocalpath);
            document.getElementById("img2").style.filter = "progid:DXImageTransform.Microsoft.AlphaImageLoader(sizingMethod='scale',src=\"" + reallocalpath + "\")";
        } else {
            var reader = new FileReader();
            reader.readAsDataURL(fil[0]);
            reader.onload = function () {
                document.getElementById("img2").src = reader.result;
                document.getElementById("img2").style.filter = "progid:DXImageTransform.Microsoft.AlphaImageLoader(sizingMethod='scale',src=\"" + reader.result  + "\")";

            }
        }
    }
    function changeimg3(e) {
        // var files = $(e.target);
        //var text = $(e.target).prev();
        // textname = files.val().split("\\");
        // determine = files.val().split(".");
        //text.val(files.val());
        // text.val(textname[textname.length-1]);
        var fil = e.files;
        var file = e;
        var isIE = navigator.userAgent.match(/MSIE/) != null,
            isIE6 = navigator.userAgent.match(/MSIE 6.0/) != null;
        if (isIE) {
            file.select();
            var reallocalpath = document.selection.createRange().text;
            $("#img3").attr('src', reallocalpath);
        } else {
            var reader = new FileReader();
            reader.readAsDataURL(fil[0]);
            reader.onload = function () {
                document.getElementById("img3").src = reader.result;
            }
        }
    }
    function changeimg4(e) {
        // var files = $(e.target);
        //var text = $(e.target).prev();
        // textname = files.val().split("\\");
        // determine = files.val().split(".");
        //text.val(files.val());
        // text.val(textname[textname.length-1]);
        var fil = e.files;
        var file = e;
        var isIE = navigator.userAgent.match(/MSIE/) != null,
            isIE6 = navigator.userAgent.match(/MSIE 6.0/) != null;
        if (isIE) {
            file.select();
            var reallocalpath = document.selection.createRange().text;
            $("#img4").attr('src', reallocalpath);
        } else {
            var reader = new FileReader();
            reader.readAsDataURL(fil[0]);
            reader.onload = function () {
                document.getElementById("img4").src = reader.result;
            }
        }
    }
</script>
</body>
</html>
