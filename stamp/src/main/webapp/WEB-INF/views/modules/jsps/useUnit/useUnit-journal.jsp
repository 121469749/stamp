<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp" %>
<!DOCTYPE html>
<html>
<head lang="zh">
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no"/>
    <title>用章单位-印章使用日志查看</title>
    <link rel="stylesheet" href="${ctxHtml}/css/bootstrap.min.css"/>
    <link rel="stylesheet" href="${ctxHtml}/css/useUnit_style.css"/>
    <link rel="stylesheet" href="${ctxHtml}/css/licence.css">
    <link rel="stylesheet" href="${ctxHtml}/css/paging.css"/>
    <script language="JavaScript" src="${ctxHtml}/js/jquery-1.9.1.min.js"></script>
    <script src="${ctxHtml}/js/bootstrap.min.js"></script>
    <script src="${ctxHtml}/js/My97DatePicker/WdatePicker.js"></script>
    <script type="text/javascript">
        $(document).ready(function () {

        });
        function page(n, s) {
            $("#pageNo").val(n);
            $("#pageSize").val(s);
            $("#searchForm").submit();
            return false;
        }
    </script>
    <style>
        input[type="text"]{
            border: none;
            width: 100%;
        }
    </style>
</head>
<body style="padding-left: 3%;padding-top: 3%">
<h3>用章单位-印章使用日志查看</h3>
<div style="margin-bottom: 20px">
    <form:form id="searchForm" action="${ctx}/useCompanyLogPage/showStampLog" method="post">
        <input name="stamp.id" type="hidden" value="${useLog.stamp.id}">
        <input id="pageNo" name="pageNo" type="hidden" value="${useLog.page.pageNo}">
        <input id="pageSize" name="pageSize" type="hidden" value="${useLog.page.pageSize}">
    </form:form>
    <c:choose>
        <c:when test="${!empty useLog.stamp.phyModel || !empty useLog.stamp.eleModel}">
            <img class="left-part" src="/img${useLog.stamp.eleModel}" alt="" style="float: left">
        </c:when>
        <c:otherwise>
            <img src="${pageContext.request.contextPath}/static/images/making.png" width="200" height="100" alt="" style="float: left;margin-left: 10px">
        </c:otherwise>
    </c:choose>
    <div style="width: 60%;float: left">
        <table class="table table-bordered" style="width: 80%;margin: 0 auto">
            <tr>
                <td>印章名称：</td>
                <td>${useLog.stamp.stampName}</td>
            </tr>
            <tr>
                <td>印章类型：</td>
                <c:set var="stampShape" value="${useLog.stamp.stampShape}"/>
                <td>${fns:getDictLabel(stampShape, "stampShape", null)}</td>
            </tr>
            <c:if test="${useLog.page.list[0].stamp.stampShape == '1'}">
                <tr>
                    <td>印章材质</td>
                    <c:set var="stampTexture" value="${useLog.stamp.stampTexture}"/>
                    <td>${fns:getDictLabel(stampTexture, "stampTexture", null)}</td>
                </tr>
            </c:if>
            <tr>
                <td>印章种类</td>
                <c:set var="stampType" value="${useLog.stamp.stampType}"/>
                <td>${fns:getDictLabel(stampType, "stampType", null)}</td>
            </tr>
        </table>

    </div>
    <div style="clear: both;"></div>
</div>
<div>
    <div class="form-group" style="width: 90%">
        <label style="font-size: 25px;color: black">印章使用历史</label>
        <c:if test="${useLog.stamp.stampShape == '1'}">
            <c:if test="${useLog.stamp.useState.key == '1'}">
                <button class="btn btn-default showimgs" data-toggle="modal" data-target=".bs-example-modal-lg" onclick="showimg(this)">印章使用录入</button>
            </c:if>
        </c:if>
    </div>
    <div style="clear: both"></div>
    <div id="using">
        <table class="table-bordered table" style="width: 80%">
            <tbody>
            <tr>
                <th>操作者</th>
                <th>使用者</th>
                <th>使用时间</th>
                <th>用途</th>
            </tr>
            <c:forEach items="${useLog.page.list}" var="data">
                <tr>
                    <td>${data.user.name}</td>
                    <td>${data.applyUsername}</td>
                    <td><fmt:formatDate value="${data.useDate}" pattern="yyyy-MM-dd"/></td>
                    <c:choose>
                        <c:when test="${data.remarks != null}">
                            <td>${data.remarks}</td>
                        </c:when>
                        <c:otherwise>
                            <td></td>
                        </c:otherwise>
                    </c:choose>
                </tr>
            </c:forEach>
            </tbody>
        </table>
        <div class="pagination" style="display: block">
            ${useLog.page}
        </div>
        <button  class="btn btn-default" onclick="history.go(-1)">返回</button>
    </div>

</div>
<!--模态框-->
<div  class="modal fade bs-example-modal-lg" tabindex="-1" role="dialog" aria-labelledby="myLargeModalLabel">
    <div class="modal-dialog modal-lg" role="document">
        <div class="modal-content">
            <div class="modal-header" style="border: 0;padding-right: 10%">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                    <span aria-hidden="true"><img src="${ctxHtml}/images/close.png"></span></button>
            </div>
            <table class="table table-bordered" style="width:80%;margin: 5px auto">
                <tr>
                    <td>操作者：</td>
                    <td><input type="text" class="form-control" id="username" disabled></td>
                    <input type="hidden" class="form-control" id="userId">
                    <input type="hidden" class="form-control" id="companyId">
                    <%--<td>--%>
                        <%--<select id="user" class="form-control">--%>
                        <%--</select>--%>
                    <%--</td>--%>
                </tr>
                <tr>
                    <td>使用者：</td>
                    <td><input type="text" class="form-control" id="applyUsername"></td>
                </tr>
                <tr>
                    <td>使用时间：</td>
                    <td><input type="text" class="form-control" id="useTime" onfocus="WdatePicker({skin:'whyGreen',maxDate:'%y-%M-%d'})"></td>
                </tr>
                <tr>
                    <td>用途：</td>
                    <td><input type="text" class="form-control" id="remarks"></td>
                </tr>
                <tr>
                    <td colspan="2" style="text-align: center">
                        <button class="btn btn-default" onclick="submit()">添加</button>
                    </td>
                </tr>
            </table>
        </div>
    </div>
</div><%--<div class="modalBox">
    <div class="overLay" style="background-color: black"></div>
    <div class="alertContent" style="overflow:auto;">
        <table class="table table-bordered" style="width:80%;margin: 5px auto">
            <tr>
                <td>使用用户名：</td>
                <td>
                    <select id="user" class="form-control">
                    </select>
                </td>
            </tr>
            <tr>
                <td>使用时间：</td>
                <td><input type="text" class="form-control" id="useTime" onfocus="WdatePicker({skin:'whyGreen',maxDate:'%y-%M-%d'})"></td>
            </tr>
            <tr>
                <td>用途：</td>
                <td><input type="text" class="form-control" id="remarks"></td>
            </tr>
            <tr>
                <td colspan="2" style="text-align: center">
                    <button class="btn btn-default" onclick="submit()">添加</button>
                    <button id="canbtn" class="btn btn-default">返回</button>
                </td>
            </tr>
        </table>
    </div>
</div>--%>
<script>
    function showimg(a){
        var isIE = navigator.userAgent.match(/MSIE/)!= null,//判断ie8
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
    //图片展示
    $(".showimgs").click(function (e) {

        var userSelect = document.getElementById("user");
        // userSelect.options.length = 0;

        $.ajax({
            type: "POST",
            url: "${ctx}/useCompanyStampAction/showUser",
            dataType: "JSON",
            success: function (data) {
                if (data.code == 200) {
                    if (data.entity.userList.length != 0) {
                        // for (var i = 0; i < data.entity.userList.length; i++) {
                        //     userSelect.options.add(new Option(data.entity.userList[i].name, data.entity.userList[i].id));
                        // }
                        $("#username").val(data.entity.userList[0].name);
                        $("#applyUsername").val(data.entity.userList[0].name);
                        $("#userId").val(data.entity.userList[0].id);
                        $("#companyId").val(data.entity.stampList[0].useComp.id);
                    }
                } else {
                    alert("出错了！请联系管理员！");
                }
            },
            error: function () {
                alert("出错了！请联系管理员！");
            }
        });

    })

</script>
</body>
</html>

<script>
    function submit() {
        if ($('#useTime').val() == null || $('#useTime').val() == '') {
            alert("使用日期不能为空!!!")

        } else {
            $.ajax({
                type: "POST",
                url: "${ctx}/useCompanyLogAction/insertOperation",
                data: {"stampId": "${useLog.stamp.id}", "useDate": $('#useTime').val(), "remarks": $('#remarks').val(), "userId": $('#userId').val(),
                        "applyUsername": $('#applyUsername').val(), "companyId": $('#companyId').val(), "username": $('#username').val()},
                dataType: "JSON",
                timeout: 5000,
                success: function (data) {

                    if (data.code == 200) {
                        alert(data.message);
                        $(".modalBox").css("display", "none");
                        location.reload(true);
                    } else {
                        alert(data.message);
                    }
                },
                error: function (XMLHttpRequest, textStatus) {
                    if (textStatus == "timeout") {
                        alert("请求超时");
                    } else {
                        alert("错误!!!");
                    }
                }
            });
        }
    }
</script>