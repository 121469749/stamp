<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp" %>
<!DOCTYPE html>
<html lang="zh">
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no"/>
    <meta name="renderer" content="webkit">
    <title>印章管理-操作日志</title>
    <link rel="stylesheet" href="${ctxHtml}/css/bootstrap.min.css">
    <link rel="stylesheet" href="${ctxHtml}/css/licence.css">
    <link rel="stylesheet" href="${ctxHtml}/css/paging.css"/>
    <script language="JavaScript" src="${ctxHtml}/js/jquery-1.9.1.min.js"></script>
    <script src="${ctxHtml}/js/bootstrap.min.js"></script>
    <script src="${ctxHtml}/js/My97DatePicker/WdatePicker.js"></script>
    <style>
        input[type="text"]{
            border: none;
        }
        .searchtext{
            width: 150px;

            border: 1px solid #afaaaa!important;
        }
    </style>
    <script type="text/javascript">
        function page(n,s) {
            $("#pageNo").val(n);
            $("#pageSize").val(s);
            $("#searchForm").submit();
            return false;
        }
    </script>
</head>
<body>
<div style="width: 60%;margin: 20px auto">
    <h3>印章管理-操作日志</h3>
    <form:form id="searchForm" modelAttribute="stampLog" action="${ctx}/useCompanyLogPage/showSysLog" method="post">
        <input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}">
        <input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}">
        <div class="form-group" style="min-width: 550px">
            <label for="" style="color: black">操作时间：</label>
            <input id="d5221" name="begin" style="height: 35px;width: 175px" class="Wdate searchtext form-control" value="<fmt:formatDate value="${stampLog.beginDate}" pattern="yyyy-MM-dd HH:mm:ss"/>" type="text" onFocus="var d5222=$dp.$('d5222');WdatePicker	({onpicked:function(){d5222.focus();},maxDate:'#F{$dp.$D(\'d5222\')}',maxDate:'%y-%M-%d',dateFmt:'yyyy-MM-dd HH:mm:ss'})"/>
            至
            <input id="d5222" name="end" style="height: 35px;width: 175px" class="Wdate searchtext form-control" value="<fmt:formatDate value="${stampLog.endDate}" pattern="yyyy-MM-dd HH:mm:ss"/>" type="text" onFocus="WdatePicker({minDate:'#F{$dp.$D(\'d5221\')}',maxDate:'%y-%M-%d',dateFmt:'yyyy-MM-dd HH:mm:ss'})"/>
            <label for="" style="color: black">操作备注：</label>
            <form:input path="title" type="text" class="form-control searchtext" htmlEscape="false"/>
            <button type="submit" class="btn btn-default " style="margin-bottom: 4px">搜索</button>
        </div>
    </form:form>
    <table class="table table-bordered" style="min-width: 700px">
        <tr>
            <th>时间</th>
            <th style="width: 450px">操作备注</th>
            <th>操作人</th>
        </tr>

        <c:forEach var="log" items="${page.list}">
            <tr>
                <td><input type="text" style="width: 100%"  value="<fmt:formatDate value="${log.createDate}" pattern="yyyy-MM-dd HH:mm:ss"/>" readonly></td>

                <td><input type="text" style="width: 100%"   value="${log.title}" readonly></td>

                <td><input type="text" style="width: 100%"  value="${log.createBy.name}" readonly></td>
            </tr>
        </c:forEach>
    </table>
    <div class="pagination" style="display: block;text-align: center">
        ${page}
    </div>
</div>
</body>
</html>