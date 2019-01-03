<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp" %>
<!DOCTYPE html>
<html lang="zh">
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no"/>
    <meta name="renderer" content="webkit">
    <title>印章管理-盖章日志</title>
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
        .chosestamp{
            width: 120px!important;
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
    <h3>印章管理-盖章日志</h3>
    <form:form id="searchForm" modelAttribute="stampOperation" action="${ctx}/useCompanyLogPage/showUseLog" method="post">
        <div class="form-group" style="min-width: 800px">
            <input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}">
            <input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}">
            <label for="" style="color: black">操作时间：</label>
            <input id="d5221" name="begin" style="height: 35px;width: 175px" class="Wdate searchtext form-control"  value="<fmt:formatDate value="${stampOperation.beginDate}" pattern="yyyy-MM-dd HH:mm:ss"/>"  type="text" onFocus="var d5222=$dp.$('d5222');WdatePicker	({onpicked:function(){d5222.focus();},maxDate:'#F{$dp.$D(\'d5222\')}',maxDate:'%y-%M-%d',dateFmt:'yyyy-MM-dd HH:mm:ss'})"/>
            至
            <input id="d5222" name="end" style="height: 35px;width: 175px" class="Wdate searchtext form-control" value="<fmt:formatDate value="${stampOperation.endDate}" pattern="yyyy-MM-dd HH:mm:ss"/>" type="text" onFocus="WdatePicker({minDate:'#F{$dp.$D(\'d5221\')}',maxDate:'%y-%M-%d',dateFmt:'yyyy-MM-dd HH:mm:ss'})"/>
            <label for="" style="color: black">印章形式：</label>
            <form:select path="stamp.stampShape" id="stampShape" class="form-control chosestamp" cssStyle="height: 30px">
                <form:options items="${fns:getDictList('stampShape')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
            </form:select>
            <label for="" style="color: black">印章名字：</label>
            <form:input path="stamp.stampName" type="text" class="form-control searchtext" htmlEscape="false"/>
            <button type="submit" class="btn btn-default " style="margin-bottom: 4px">搜索</button>
        </div>
    </form:form>
    <table class="table table-bordered" style="width: 100%;margin: 0 auto">
        <tr>
            <th>使用者</th>
            <th>印章名称</th>
            <th>使用时间</th>
            <th>用途</th>
        </tr>
        <c:forEach items="${page.list}" var="log">
            <tr>
                <td><input type="text" style="width: 100%"  value="${log.user.name}" readonly></td>

                <td><input type="text" style="width: 100%"  value="${log.stamp.stampName}" readonly></td>

                <td><input type="text" style="width: 100%"  value="<fmt:formatDate value="${log.createDate}" pattern="yyyy-MM-dd HH:mm:ss"/>" readonly></td>

                <td><input type="text" style="width: 100%"   value="${log.remarks}" readonly></td>
            </tr>
        </c:forEach>
    </table>
    <div class="pagination" style="display: block;text-align: center">
        ${page}
    </div>
</div>
</body>
</html>