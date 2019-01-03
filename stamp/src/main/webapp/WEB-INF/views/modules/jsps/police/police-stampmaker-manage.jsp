<%--
  Created by IntelliJ IDEA.
  User: 77426
  Date: 2017/5/20
  Time: 15:54
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
    <title>刻章点管理</title>
    <link rel="stylesheet" href="${ctxHtml}/css/bootstrap.min.css">
    <link rel="stylesheet" href="${ctxHtml}/css/paging.css?id=${fns:getConfig('css.version')}">
    <script language="JavaScript" src="${ctxHtml}/js/jquery-1.9.1.min.js"></script>
    <script src="${ctxHtml}/js/bootstrap.min.js"></script>
    <style>
        table input{
            height: 20px;
            width: 100%;
            border: none;
            background: transparent;
        }
    </style>
    <script type="text/javascript">
        function page(n, s) {
            $("#pageNo").val(n);
            $("#pageSize").val(s);
            $("#searchForm").submit();
            return false;
        }
    </script>
</head>
<body style="width: 93%;margin: 10px auto">

<h3 >刻章点管理</h3>
<form:form id="searchForm" modelAttribute="company" action="${ctx}/policeMakeComPage/showMakeCompany/" method="post" class="breadcrumb form-search ">
    <input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
    <input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
    <label>公司名称:</label>
    <form:input path="companyName"></form:input>
    <input id="btnSubmit" class="btn  btn-default btn-sm" type="submit" value="搜索" style="margin-bottom: 2px"/>
</form:form>
<table class="table table-striped table-bordered" >
    <tr>
        <th>单位名称</th>
        <th>单位地址</th>
        <th style="width: 10%">年审状态</th>
        <th style="width: 10%">公司状态</th>
        <th width="200px">操作</th>
    </tr>
    <c:forEach items="${page.list}" var="company">
        <tr>
            <td class ="hide" id="companyId"><input type="text" value="${company.id}" readonly></td>
            <td><input type="text" value="${company.companyName}" readonly></td>
            <td><input type="text" value="${company.compAddress}" readonly></td>
            <c:if test="${company.delFlag == 0}">
            <c:choose>
                <c:when test="${company.busEndDate gt company.busStartDate}">
                    <td><input type="text" value="未过期" readonly></td>
                </c:when>
                <c:when test="${company.busStartDate == null}">
                    <td><input type="text" value="待审核" readonly></td>
                </c:when>
                <c:otherwise>
                    <td><input type="text" value="过期" readonly></td>
                </c:otherwise>

            </c:choose>
            </c:if>
            <c:if test="${company.delFlag == 1}">
                <td><input type="text" value="已撤销" readonly></td>
            </c:if>
            <td><input type="text" value="${company.compState.value}" readonly ></td>
            <td width="200px">
                <div style="width: 200px!important;">
                <a id="check" class="btn btn-block btn-default" href="${ctx}/policeMakeComPage/companyDetail?id=${company.id}" style="display: inline">查看</a>
                <c:if test="${company.delFlag == 0}">
                <c:choose>
                    <c:when test="${company.compState.value == '审核中'}">


                        <shiro:hasPermission name="police:edit"> <a id="start" class='stop btn btn-block btn-default' href="${ctx}/policeMakeComAction/updateComState?keyId=2&id=${company.id}"  style='display: inline'>许可</a>
                            <a id="delete" class='delete btn btn-block btn-default'  href="${ctx}/policeMakeComAction/deleteMakeCompany" style='display: inline'>撤销</a> </shiro:hasPermission>

                    </c:when>
                    <c:when test="${company.compState.value == '已许可'}">

                        <shiro:hasPermission name="police:edit">
                            <a id="stop" class='stop btn btn-block btn-default' href="${ctx}/policeMakeComAction/updateComState?keyId=3&id=${company.id}"  style='display: inline'>暂停</a>
                            <a id="delete" class='delete btn btn-block btn-default'  href="${ctx}/policeMakeComAction/deleteMakeCompany" style='display: inline'>撤销</a>
                        </shiro:hasPermission>

                    </c:when>
                    <c:when test="${company.compState.value == '暂停'}">

                        <shiro:hasPermission name="police:edit">
                            <a id="start" class='start btn btn-block btn-default' href="${ctx}/policeMakeComAction/updateComState?keyId=2&id=${company.id}" style='display: inline'>启用</a>
                            <a id="delete" class='delete btn btn-block btn-default'  href="${ctx}/policeMakeComAction/deleteMakeCompany" style='display: inline'>撤销</a>
                        </shiro:hasPermission>
                    </c:when>



                </c:choose>
                </c:if>
                </div>
            </td>
        </tr>
    </c:forEach>
</table>
<div class="pagination">${page}</div>
<script>
//    $(function() {
//        $("table").click(function (event) {
//            if(event.target.id == "check"){
//                return true;
//            }
//            var param1, param2, url;
//            url = $(event.target).attr("href");
//            if (event.target.id == "start") {
//                param1 = $(event.target).parents('tr').children("td:first-child").children("input:first-child").val();
//                param2 = "USING";
//                Asyn(url, param1, param2);
//            }
//            else if (event.target.id == "stop") {
//                param1 = $(event.target).parents('tr').children("td:first-child").children("input:first-child").val();
//                param2 = "STOP";
//                Asyn(url, param1, param2);
//            }
//            else if (event.target.id == "delete") {
//                param1 = $(event.target).parents('tr').children("td:first-child").children("input:first-child").val();
//                Asyn(url, param1, param2);
//            }
//          return false;
//        });
//        function Asyn(url, param1, param2) {
//            $.ajax({
//                type: "POST",
//                url: url,
//                data: {"id": param1, "compState": param2},
//                timeout: 5000,
//                success: function (msg) {
//                    alert("success");
//                    window.location.reload();
//                },
//                error: function (XMLHttpRequest, textStatus, errorThrown) {
//                    // $("#state").append("[state: " + textStatus + ", error: " + errorThrown + " ]<br/>");
//                    if (textStatus == "timeout") { // 请求超时
//                        alert("出错了！请联系管理员！"); // 递归调用
//                        // 其他错误，如网络错误等
//                    } else {
//                        alert("出错了！请联系管理员！");
//                        // alert(errorThrown);
//                    }
//                }
//            });
//        }
//    }
//    )
</script>
</body>
</html>
