<%--
  Created by IntelliJ IDEA.
  User: 77426
  Date: 2017/5/20
  Time: 15:49
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp" %>
<!DOCTYPE html>
<html lang="zh">
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no"/>
    <meta name="renderer" content="webkit">
    <title>刻章单位-详情</title>
    <link rel="stylesheet" href="${ctxHtml}/css/bootstrap.min.css">
    <link rel="stylesheet" href="${ctxHtml}/css/licence.css?id=${fns:getConfig('css.version')}">
    <script src="${ctxHtml}/js/jquery-1.9.1.min.js"></script>
    <script src="${ctxHtml}/js/bootstrap.min.js"></script>
    <style type="text/css">
        .modal-content{
            background: rgba(0,0,0,0);
            border: none;
            box-shadow: none;
            text-align: center;
        }
    </style>
    <script>
        function seeimg(v,n){
            <%--var judge = /^(jpg|bmp|gif|ico|pcx|jpeg|tif|png|raw|tga|JPG|BMP|GIF|ICO|PCX|JPEG|TIF|PNG|RAW|TGA)$/;--%>
            <%--$.ajax({--%>
            <%--type:"post",--%>
            <%--url:"${ctx}/attachment/getAttachment",--%>
            <%--data:{"id":$("#recordId").val(),"fileType":v},--%>
            <%--dataType:"json",--%>
            <%--success:function (result) {--%>
            <%--$(".modal-content").children("img").remove();--%>
            <%--$(".modal-content").children("a").remove();--%>
            <%--for(var i = 0;i < result.length;i++){--%>
            <%--if(!judge.test(result[i].fileSuffix)){--%>
            <%--$(".modal-content").append("<a class='btn btn-success btn-lg active' href='/img"+ result[i].attachPath +"' download='"+ $("#unit-name").val()+ "-" + n  + i +"'>文档"+i+"附件下载</a>");--%>
            <%--}else{--%>
            <%--$(".modal-content").append("<img src='/img"+ result[i].attachPath +"' style='margin-top:10px;width: 100%;height: auto'>");--%>
            <%--}--%>
            <%--}--%>
            <%--},--%>
            <%--error:function(){--%>
            <%--alert("出错啦！");--%>
            <%--}--%>
            <%--})--%>

            var judge = /^(jpg|bmp|gif|ico|pcx|jpeg|tif|png|raw|tga|JPG|BMP|GIF|ICO|PCX|JPEG|TIF|PNG|RAW|TGA)$/;
            var show = false;
            var isFirst = false;
            var i = 0;
            $(".modal-content").children("img").remove();
            $(".modal-content").children("a").remove();
            <c:forEach items="${vo.attachmentList}" var="item" varStatus="status" >
            if("${item.attachType}" == v){
                show = true;
                if(!judge.test("${item.fileSuffix}")){
                    $(".modal-content").append("<a class='btn btn-success btn-lg active' href='${ctx}/stampMakeInfo/download/file?id="+ "${item.id}" +"'>文档"+i+"附件下载</a>");
                }else{
                    $(".modal-content").append("<img src='/img"+ "${item.attachPath}" +"' style='margin-top:10px;width: 100%;height: auto'>");
                }
            }
            </c:forEach>
            if(!show){
                alert("该资料没有上传图片");
            }
        }
    </script>
</head>
<body>
<div class="table-double">
    <h3>刻章单位</h3>
        <input id="licenseID" value="${company.licence.id}" hidden>
        <div class="mod">
            <table class="table maintable table-striped table-bordered">
                <tbody>
                <tr>
                    <td>企业名称</td>
                    <td>
                        ${vo.company.companyName}
                    </td>
                    <td>经济性质</td>
                    <td colspan="3">
                        ${fns:getDictLabel(vo.company.busType, 'busType', '无')}
                    </td>
                </tr>
                <tr>
                    <td>企业地址</td>
                    <td colspan="3">
                        <input type="text" value="${vo.company.compAddress}" style="width: 100%;border: none" readonly>
                    </td>
                    <td>经营方式</td>
                    <td>
                        ${vo.company.busModel}
                    </td>
                </tr>
                <tr>
                    <td>主要负责人</td>
                    <td>
                        ${vo.company.legalName}
                    </td>
                    <td>身份证号码</td>
                    <td>
                       ${vo.company.legalCertCode}
                    </td>
                    <td>联系电话</td>
                    <td>
                        ${vo.company.legalPhone}
                    </td>
                </tr>
                <tr>
                    <td>治安负责人</td>
                    <td>
                       ${vo.company.policeName}
                    </td>
                    <td>身份证号码</td>
                    <td>
                       ${vo.company.policeIdCode}
                    </td>
                    <td>联系电话</td>
                    <td>
                       ${vo.company.policePhone}
                    </td>
                </tr>
                <tr>
                    <td>企业总人数</td>
                    <td colspan="2">
                        ${vo.company.headCount}
                    </td>
                    <td>其中特业从业人数</td>
                    <td colspan="2">
                       ${vo.company.specialCount}
                    </td>
                </tr>
                </tbody>
            </table>
            <%--<div class="licence-apply-address">--%>
                <%--<span>企业所在区域:</span>--%>
                <%--<sys:treeselect id="area" name="area.id" value="${vo.company.area.id}" labelName="area.name" labelValue="${vo.company.area.name}"--%>
                                <%--title="区域" url="/sys/area/treeData" cssClass="required"/>--%>
            <%--</div>--%>
            <h4>该刻章点最近一次许可证申请信息</h4>
            <div>

            </div>
            <label style="color: #000">办理类型:</label>
            <input value="${vo.licence.workType.value}" style="border: 0;line-height: 20px;height: 20px" readonly/>
            <c:if test="${vo.licence.workType.key == 1}">
                <label style="color: #000">办理类型:</label>
                <input value="${vo.licence.checkReason}" style="border: 0;line-height: 20px;height: 20px" readonly/>
            </c:if>
                <label style="color: #000">审核状态:</label>
                <input value="${vo.licence.checkState.value}" style="border: 0;line-height: 20px;height: 20px" readonly/>
            <!--文件上传及照片预览--->
            <h4>申请附件如下:</h4>
            <table class="table table-striped table-bordered">
                <tr>
                    <td rowspan="11" style="width: 10%;line-height:45px;text-align: center"><strong>附<br>件<br>材<br>料</strong>
                    </td>
                </tr>
                <c:forEach items="${dir}" var="dict">
                    <tr>
                        <td>${dict.label}</td>
                        <td><button type="button" onclick="seeimg('${dict.value}','${dict.label}')" class="btn btn-primary seeimg" data-toggle="modal" data-target=".bs-example-modal-lg">查看</button></td>
                    </tr>
                </c:forEach>
            </table>
        </div>


    <!--模态框-->
    <div class="modal fade bs-example-modal-lg" tabindex="-1" role="dialog" aria-labelledby="myLargeModalLabel">
        <div class="modal-dialog modal-lg" role="document">
            <div class="modal-content">
                <div class="modal-header" style="border: 0;padding-right: 10%">
                    <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                        <span aria-hidden="true"><img src="${ctxHtml}/images/close.png"></span></button>
                </div>
                <img src="" id="firstimg" style="width: 100%;height: auto">
            </div>
        </div>
    </div>
    <div class="my-mask"></div>
</div>
<div style="clear: both"></div>
</body>
</html>
