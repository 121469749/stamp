<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp" %>
<!DOCTYPE html>
<html lang="zh">
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no"/>
    <meta name="renderer" content="webkit">
    <title>刻章点-注销申请-详情</title>
    <link rel="stylesheet" href="${ctxHtml}/css/bootstrap.min.css">
    <link rel="stylesheet" href="${ctxHtml}/css/licence.css">
    <link rel="stylesheet" href="${ctxHtml}/css/font-awesome.min.css">
    <link rel="stylesheet" href="${ctxHtml}/css/myCss.css">
    <script language="JavaScript" src="${ctxHtml}/js/jquery-1.9.1.min.js"></script>
    <script src="${ctxHtml}/js/bootstrap.min.js"></script>
    <style>
        input[type="text"]{
            height: 30px;
            line-height: 30px;
        }

        input[type="file"]{
            position: absolute;
            top: 0;
            left: 9px;
            right: 0;
            opacity: 0;
            height: 30px;
            filter:progid:DXImageTransform.Microsoft.Alpha(opacity=0);
        }
    </style>

</head>
<body>
<div class="table-double">
    <!--左边导航-->
    <div class="table-left">
        <ul>
            <li><a href="#">开办流程</a></li>
            <li class="select"><a href="#">开办申请</a></li>
            <li><a href="#">网上咨询</a></li>
        </ul>
    </div>
    <!---右边部分-->
    <div class="table-right">
        <form id="mainform" >
            <div class="mod" style="display: none">开办流程说明</div>
            <div class="mod">
                <div class="panel-head" style="font-size: 14px">
                    <b><span class="fa fa-address-book-o"></span>
                        &nbsp;注销申请</b>

                </div>
                <table class="table maintable table-striped table-bordered">
                    <tbody>
                    <tr>
                        <td>企业名称</td>
                        <td>
                            <input name="enterprise_name" id="enterprise_name" type="text" class="form-control readonly"
                                   readonly value="${vo.licence.compName}">
                        </td>
                        <td>经济性质</td>
                        <td colspan="3">
                            <input name="enterprise_name" id="enterprise_nature" type="text" class="form-control readonly"
                                   readonly value="${vo.licence.busType}">
                        </td>
                    </tr>

                    <tr>
                        <td>主要负责人</td>
                        <td>
                            <input type="text" id="mainperson" class="form-control readonly" value="${vo.licence.legalName}" readonly>
                        </td>
                        <td>身份证号码</td>
                        <td>
                            <input type="text" id="mainperson-id" class="form-control" value="${vo.licence.legalCertCode}" readonly>
                        </td>
                        <td>联系电话</td>
                        <td>
                            <input type="number" id="mainperson-phone" class="form-control readonly" value="${vo.licence.legalPhone}"
                                   readonly>
                        </td>
                    </tr>
                    <tr>
                        <td>治安负责人</td>
                        <td>
                            <input type="text" id="securityperson" class="form-control readonly" value="${vo.licence.policeName}" readonly>
                        </td>
                        <td>身份证号码</td>
                        <td>
                            <input type="text" id="securityperson-id" class="form-control readonly" value="${vo.licence.policeIdCode}"
                                   readonly>
                        </td>
                        <td>联系电话</td>
                        <td>
                            <input type="text" id="securityperson-phone" class="form-control readonly" value="${vo.licence.policePhone}"
                                   readonly>
                        </td>
                    </tr>
                    <tr>
                        <td>企业总人数</td>
                        <td colspan="2">
                            <input type="text" id="allpeople" class="form-control readonly" value="${vo.licence.headCount}" readonly>
                        </td>
                        <td>其中特业从业人数</td>
                        <td colspan="2">
                            <input type="text" id="specialpeople" class="form-control readonly" value="${vo.licence.specialCount}" readonly>
                        </td>
                    </tr>
                    </tbody>
                </table>

                <!--文件上传及照片预览--->
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
            <div class="mod" style="display: none">网上咨询</div>
        </form>
        <!--模态框-->
        <div class="modal fade bs-example-modal-lg" tabindex="-1" role="dialog" aria-labelledby="myLargeModalLabel">
            <div class="modal-dialog modal-lg" role="document">
                <div class="modal-content">
                    <div class="modal-header" style="border: 0;padding-right: 10%">
                        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                            <span aria-hidden="true"><img src="${ctxHtml}/images/close.png"></span></button>
                    </div>
                    <img src="images/tmp.png" id="firstimg" style="width: 100%;height: auto">
                </div>
            </div>
        </div>
        <div class="my-mask"></div>
    </div>
</div>

<div style="clear: both"></div>
<script type="text/javascript">
    <%--点击查看按钮事件--%>
    function seeimg(v,n){
        var judge = /^(jpg|bmp|gif|ico|pcx|jpeg|tif|png|raw|tga|JPG|BMP|GIF|ICO|PCX|JPEG|TIF|PNG|RAW|TGA)$/;
        var show = false;
        var isFirst = false;
        var i = 0;
        $(".modal-content").children("img").remove();
        $(".modal-content").children("a").remove();
        <c:forEach items="${vo.attachmentList}" var="item" varStatus="status" >
        <%--如果图片对应，则显示到模态框--%>
        if("${item.attachType}" == v){
            show = true;
            if(!judge.test("${item.fileSuffix}")){
                $(".modal-content").append("<a class='btn btn-success btn-lg active' href='/img"+ "${item.attachPath}" +"' download='"+ $("#unit-name").val()+ "-" + n + i++ +"'>文档"+i+"附件下载</a>");
            }else{
                $(".modal-content").append("<img src='/img"+ "${item.attachPath}" +"' style='margin-top:10px;width: 100%;height: auto'>");
            }
        }
        </c:forEach>
        <%--如果没有图片，则提示没有上传图片--%>
        if(!show){
            alert("该资料没有上传图片");
        }
    }
</script>

</body>
</html>