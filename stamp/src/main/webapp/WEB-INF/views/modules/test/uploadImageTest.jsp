<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2018\8\21 0021
  Time: 10:37
  To change this template use File | Settings | File Templates.
--%>
<%--
一、目标效果:
1.前端，页面上传多张图片时，POST到服务器端，页面能立即显示图片效果，并且不刷新页面
2.后台,页面POST过来的图片被重命名并保存到服务器

二、思路：
1.封装一个js函数uploadimg(imgid,fileid,hiddenid),里面调用ajaxfileupload.js的函数$.ajaxFileUpload()将标签input type=’file’ 里的文件ajax方式上传到服务器，上传成功后接收返回json数据——图片在服务器端的路径data.path，将该值赋给img标签和对应的隐藏域
2.在服务器端有个专门处理ajax上传图片的php文件，首先判断文件类型和大小是否合法，然后将文件重命名并且移动一个文件暂存区，然后json格式返回图片路径给前端页面
3.等到页面真正提交表单信息到后台保存时，后台根据隐藏域中的图片路径值，将暂存区中的图片移动到真正永久保存图片的文件夹中
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html lang="en-US">
<head>
    <meta charset="UTF-8"/>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <%--<link rel='stylesheet' type="text/css" href='/assets/css/own/img.css' />--%>
    <%--<script src="/assets/js/jquery-1.4.2.min.js"></script>--%>
    <%--<script src="/assets/js/ajaxfileupload.js"></script>--%>
    <script type="text/javascript" src="${ctxHtml}/js/jquery-1.9.1.min.js"></script>
    <script src="${ctxHtml}/js/ajaxfileupload.js?id=${fns:getConfig('js.version')}"></script>
    <%--<script src="${ctxHtml}/js/uploadimg.js?id=${fns:getConfig('js.version')}"></script>--%>
    <title>upload image</title>
</head>
<body>
<form id="w0" action="imgfile.php" name="form1" method="post" enctype="multipart/form-data" >
    <div>
        <input type="file" name="fileImg" id="fileImg" onchange="uploadimg('img1','fileImg','hidden1')" accept="image/jpeg,image/png,image/gif"><br />
        <input type="hidden" name="hidden1" id="hidden1" value="" />
        <img class="load0" name="img1" id="img1" src="" />
    </div>
    <br /><br />
    <div>
        <input type="file" name="fileImg2" id="fileImg2" onchange="uploadimg('img2','fileImg2','hidden2')" accept="image/jpeg,image/png,image/gif"><br />
        <input type="hidden" name="hidden2" id="hidden2" value="" />
        <img class="load0" name="img2" id="img2" src="" />
    </div>
    <br /><br />
    <input type="submit" value="ok">
</form>

<script type="text/javascript">
    function uploadimg(imgid,fileid,hiddenid) {
        //imgid指<img />标签id,fileid表示<input type='file' />文件上传标签的id,hiddenid指隐藏域标签id
        $("#"+imgid).attr("src","${ctxStatic}/images/loading.gif").removeClass("load0");//加载loading图片
        $.ajaxFileUpload
        (
            {
                type:"POST",
                url: '${ctx}/test/test/startUploadImage',
                secureuri: false,
                fileElementId: fileid,
                dataType: 'json',
                success: function (data, status)
                {   //data的内容都是在后台php代码中自定义的,json格式返回后在这里以对象的方式的访问
                    if(data.code != 200){//上传文件出错
                        console.log("data.code:"+data.code);
                        var error_msg="";
                        switch(data.code)
                        {
                            case 101: error_msg="文件类型错误";
                                break;
                            case 102: error_msg="文件过大";
                                break;
                            case 400: error_msg="非法上传";
                                break;
                            case 404: error_msg="文件为空";
                                break;
                            default :error_msg="服务器不稳定";
                        }
                        console.log(error_msg);
                        alert(error_msg);
                    }else{
                        $("#"+imgid).attr("src",data.message).addClass("load1");//加载返回的图片路径并附加上样式
                        $("#"+hiddenid).val(data.message);//给对应的隐藏域赋值，以便提交时给后台
                    }
                },
                error: function (data, status, e)
                {
                    console.log("错误："+e);
                    alert(e);
                }
            }
        )
    }
</script>

</body>
</html>
