<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp" %>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>补刻印章详情</title>
    <link href="${ctxHtml}/css/bootstrap.min.css" rel="stylesheet">
    <link href="${ctxHtml}/css/engraveseal.css" rel="stylesheet">
    <script src="${ctxHtml}/js/jquery-1.9.1.min.js"></script>
    <script type="text/javascript" src="${ctxHtml}/js/jquery-form.js"></script>
    <script src="${ctxHtml}/js/bootstrap.min.js"></script>

    <script >

        $(document).ready(function(){
            //TODO
            $('#submitForm').ajaxForm({
                dataType: 'json',
                type:"post",
                url: "${ctx}/stampMakeAction/makeRepairStamp",
                success: function (result) {
                    alert(result.message);
                    if(result.code == 200){
                        //alert("resultUrl:"+result.url);
                        window.location=result.url;
                    }
                },
                error: function () {
                    alert("出错了！请联系管理员！");
                }
            });

        });

    </script>
<body>
<form class="border-box" id="submitForm"  action="${ctx}/stampMakeAction/makeRepairStamp" method="post">
    <h3>补刻印章</h3>
    <input name="id" hidden value="${stamp.id}">
    <!--表单上方的印章单位基本信息-->
    <div>
        <div class="form-group each-input">
            <label>印章单位：</label>
            <input path="useComp.companyName" value="${stamp.useComp.companyName}" id="comname" onfocus="this.blur()">
        </div>
        <div class="form-group each-input">
            <label>印章名称：</label>
            <input type="text" value="${stamp.stampName}" id="sname"  onfocus="this.blur()">
        </div>
        <div class="form-group each-input">
            <label>印章类别：</label>
            <input type="text" value="${fns:getDictLabel(stamp.stampType,"stampType" ,null )}" id="type"  onfocus="this.blur()">
        </div>
        <div class="form-group each-input">
            <label>印章材质：</label>
            <input type="text" value="${fns:getDictLabel(stamp.stampTexture,"stampTexture" ,null )}"   onfocus="this.blur()">
        </div>
    </div>
    <p class="essentialinformation clo-md-12">印模图片</p>
    <!--印章刻制部分-->
    <div class="picsborder">
        <div class="form-group picshow">
            <label>电子印章</label><br>
            <div class="imgborder" >
                <img src="${ctxOther}/img${stamp.eleModel}" width="100%"  style="border: none" alt="">
            </div>
            <a href="${ctxOther}/img${stamp.eleModel}" class="btn btn-default downloadbtn" download="${stamp.useComp.companyName}_${stamp.stampName}_电子印模">下载电子印模</a>
        </div>
        <div class="form-group picshow">
            <label>物理印章</label><br>
            <div class="imgborder" >
                <img src="${ctxOther}/img${stamp.phyModel}" width="100%"  style="border: none" alt="">
            </div>
            <a href="${ctxOther}/img${stamp.phyModel}" class="btn btn-default downloadbtn" download="${stamp.useComp.companyName}_${stamp.stampName}_物理印模">下载物理印模</a>
        </div>
        <div style="clear:  both;"></div>
        <button class="btn btn-primary" type="submit">补刻完成</button>
    </div>
</form>
</body>
</html>