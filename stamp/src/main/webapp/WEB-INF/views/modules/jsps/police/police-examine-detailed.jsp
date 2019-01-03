<%--
  Created by IntelliJ IDEA.
  User: 77426
  Date: 2017/5/20
  Time: 15:38
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
    <title>公安部门-审批查看详情</title>
    <link rel="stylesheet" href="${ctxHtml}/css/bootstrap.min.css">
    <link rel="stylesheet" href="${ctxHtml}/css/licence.css?id=${fns:getConfig('css.version')}">
    <script language="JavaScript" src="${ctxHtml}/js/jquery-1.9.1.min.js"></script>
    <script type="text/javascript" src="${ctxStatic}/jqueryform/jquery.form.js"></script>
    <script src="${ctxHtml}/js/bootstrap.min.js"></script>
    <style type="text/css">
        .modal-content {
            background: rgba(0, 0, 0, 0);
            border: none;
            box-shadow: none;
            text-align: center;
        }

        table input {
            width: 100%;
            height: 20px;
            border: none;
            background: transparent;
        }
    </style>
</head>
<body>
<div class="table-double">
    <h3>公安部门-审批查看详情</h3>
    <form id="mainform">
        <div class="mod">
            <p>申请类型: ${vo.licence.workType.getValue()}</p>

            <p> ${empty vo.licence.workReason ? "":  "申请原因： "} ${empty vo.licence.workReason  ? "": vo.licence.workReason }</p>
            <table class="table maintable table-striped table-bordered">
                <tbody>
                <tr>
                    <td>企业名称</td>
                    <td><input type="text" value="${vo.company.companyName}" readonly></td>
                    <td>经济性质</td>
                    <td colspan="3">
                        <input type="text" value="${fns:getDictLabel(vo.company.busType, 'busType', '无')}" readonly>
                    </td>
                </tr>
                <tr>
                    <td>企业地址</td>
                    <td>
                        <input type="text" value="${vo.company.compAddress}" readonly>
                    </td>
                    <td>经营方式</td>
                    <td colspan="3">
                        <input type="text" value="${vo.company.busModel}" readonly>
                    </td>
                </tr>
                <tr>
                    <td>主要负责人</td>
                    <td>
                        ${vo.licence.legalName}
                    </td>
                    <td>身份证号码</td>
                    <td>
                        ${vo.licence.legalCertCode}
                    </td>
                    <td>联系电话</td>
                    <td>
                        ${vo.licence.legalPhone}
                    </td>
                </tr>
                <tr>
                    <td>治安负责人</td>
                    <td>
                        ${vo.licence.policeName}
                    </td>
                    <td>身份证号码</td>
                    <td>
                        ${vo.licence.policeIdCode}
                    </td>
                    <td>联系电话</td>
                    <td>
                        ${vo.licence.policePhone}
                    </td>
                </tr>
                <tr>
                    <td>企业总人数</td>
                    <td colspan="2">
                        ${vo.licence.headCount}
                    </td>
                    <td>其中特业从业人数</td>
                    <td colspan="2">
                        ${vo.licence.specialCount}
                    </td>
                </tr>
                <tr>
                    <td>企业统一码</td>
                    <td colspan="5">
                        ${vo.company.soleCode}
                    </td>
                </tr>
                </tbody>
            </table>


            <!--文件上传及照片预览--->
            <table class="table table-striped table-bordered">
                <tr>
                    <td rowspan="11" style="width: 10%;line-height:45px;text-align: center">
                        <strong>附<br>件<br>材<br>料</strong>
                    </td>
                </tr>
                <c:forEach items="${dir}" var="dict">
                    <tr>
                        <td>${dict.label}</td>
                        <td>
                            <button type="button" onclick="seeimg('${dict.value}','${dict.label}')"
                                    class="btn btn-primary seeimg" data-toggle="modal"
                                    data-target=".bs-example-modal-lg">查看
                            </button>
                        </td>
                    </tr>
                </c:forEach>

            </table>
        </div>
    </form>
    <shiro:hasPermission name="police:edit">
        <form:form id="submitForm" modelAttribute="licence" action="${ctx}/policeLicenseAction/updateCheckState"
                   method="post" class="form-horizontal">
            <div class="form-group" style="width: 80%;margin: 5px auto">
                <p style="font-size: 20px">评审意见</p>
                <form:textarea path="checkReason" id="checkReason" class="form-control phone-check-input"
                               style="width: 60%;height: 100px;max-width: 500px"></form:textarea>
                    <%--<label for="checkReason" class="phone-check-label">评审意见:</label>--%>
                    <%--<form:input path="checkReason" id="checkReason" type="text" class="form-control phone-check-input" />--%>
                <form:input path="id" id="licenceId" type="text" class="form-control phone-check-input hide"/>
                <form:input path="workType" type="text" class="form-control phone-check-input hide"/>
                <form:input path="checkState" id="checkState" type="text" class="form-control phone-check-input hide"/>
            </div>
            <div class="form-group" style="width: 40%;text-align: right">

                <button id="pass" class="btn btn-default submitFlag" style="width: 25%;min-width: 70px">通过</button>
                <button id="notpass" class="btn btn-danger submitFlag" style="width: 25%;min-width: 70px">不通过</button>
            </div>
        </form:form>
    </shiro:hasPermission>


    <!--图片显示模态框-->
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

<div style="clear: both"></div>

<script>
    <%--点击查看按钮事件--%>
    function seeimg(v, n) {
        var judge = /^(jpg|bmp|gif|ico|pcx|jpeg|tif|png|raw|tga|JPG|BMP|GIF|ICO|PCX|JPEG|TIF|PNG|RAW|TGA)$/;
        var show = false;
        var i = 0;
        $(".modal-content").children("img").remove();
        $(".modal-content").children("a").remove();
        <c:forEach items="${vo.attachmentList}" var="item" varStatus="status" >
        <%--如果图片对应，则显示到模态框--%>
        if ("${item.attachType}" == v) {
            show = true;
            if (!judge.test("${item.fileSuffix}")) {
                $(".modal-content").append("<a class='btn btn-success btn-lg active' href='/img" + "${item.attachPath}" + "' download='" + $("#unit-name").val() + "-" + n + i++ + "'>文档" + i + "附件下载</a>");
            } else {
                $(".modal-content").append("<img src='/img" + "${item.attachPath}" + "' style='margin-top:10px;width: 100%;height: auto'>");
            }
        }
        </c:forEach>
        <%--如果没有图片，则提示没有上传图片--%>
        if (!show) {
            alert("该资料没有上传图片");
        }
    }

    $(function () {
        <%--表单提交事件--%>
        $("#submitForm").ajaxForm({
            dataType: 'json',
            type: "post",
            url: $("#submitForm").attr("action"),
            success: function (condition) {
                alert(condition.message.toString());
                //跳转
                if (condition.code == "200") {
                    window.location = "${ctx}/policeMakeComPage/showCheckingCompany";
                }

            },
            error: function () {
                //显示错误信息
                alert("出错了！请联系管理员！");

            }
        });
    });


    $(function () {
        <%--点击通过或不通过时的事件--%>
        $(".submitFlag").click(function (e) {
            var id = '${vo.licence.id}';
            $("#licenceId").val(id);
            <%--如果通过，则在表单存入通过这个值，反之存入不通过--%>
            if (e.target.id == "pass") {
                $('#checkState').val("CHECKSUCCESS");
            }
            else if (e.target.id == "notpass") {
                $('#checkState').val("CHECKFAIL");
            }
        });
        $(".modalBox").click(function () {
            $(this).css("display", "none");
        });
    })
</script>
</body>
</html>
