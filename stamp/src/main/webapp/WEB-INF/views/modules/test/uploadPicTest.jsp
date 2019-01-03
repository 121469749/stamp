<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2018\8\23 0023
  Time: 14:00
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
    <meta name="viewport" id="viewport" content="width=device-width, initial-scale=1,minimum-scale=1.0, maximum-scale=1.0, user-scalable=no" />

    <script type="text/javascript" src="${ctxHtml}/js/jquery-1.9.1.min.js"></script>
    <script>
        function uploadPic() {
            var form = document.getElementById('upload'),
                formData = new FormData(form);
            $.ajax({
                url:"http://localhost:8080/a/test/test/startUploadImage",
                type:"post",
                data:formData,
                processData:false,
                contentType:false,
                success:function(res){
                    if(res){
                        alert("上传成功！");
                    }
                    console.log(res);
                    $("#pic").val("");
                    $(".showUrl").html(res);
                    $(".showPic").attr("src",res);
                },
                error:function(err){
                    alert("网络连接失败,稍后重试",err);
                }

            })

        }
        //创建一个XMLHttpRequest对象
        function createXHR() {
            if (typeof XMLHttpRequest != "undefined") {
                return new XMLHttpRequest(); //IE7+和其他浏览器版本创建方式
            }
            else {
                //IE7以下的浏览器版本
                return  new ActiveXObject('Microsoft.XMLHTTP'); // window.XMLHttpRequest ? new XMLHttpRequest() :
            }
        }

        function uploadimage(){


            var file=document.getElementById('file').files[0];
            var formData = new FormData();

            formData.append("file", file); // document.getElementById('fileToUpload').files[0]);


            $.ajax({
                url:"http://localhost:8080/a/test/test/startUploadImage",
                type:"post",
                data:formData,
                processData:false,
                contentType:false,
                success:function(res){
                    if(res){
                        alert("上传成功！");
                    }
                    console.log(res);
                    $("#pic").val("");
                    $(".showUrl").html(res);
                    $(".showPic").attr("src",res);
                },
                error:function(err){
                    alert("网络连接失败,稍后重试",err);
                }
            })
        }
    </script>
</head>
<form id="upload"  enctype="multipart/form-data"  method="post">
    <input type="text" name="name"><br>
    <input type="file" name="file" id="file" />
    <input type="button" value="提交" onclick="uploadPic();"/>
    <span class="showUrl"></span>
    <img src="" class="showPic" alt="">
</form>
<input type="button" value="提交2" onclick="uploadimage();"/>

<h1>Upload single file with fields</h1>

<form action="http://127.0.0.1:9900/zf/imgtobpp" method="post" enctype="multipart/form-data">

    Files: <input type="file" name="file"><br><br>
    <input type="submit" value="Submit">
</form>
<div>
</html>
