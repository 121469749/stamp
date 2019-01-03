<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp" %>
<!DOCTYPE html>
<html lang="zh">
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no"/>
    <meta name="renderer" content="webkit">
    <title>公安部门-系统操作日志</title>
    <link rel="stylesheet" href="${ctxHtml}/css/bootstrap.min.css">
    <link rel="stylesheet" href="${ctxHtml}/css/licence.css?id=${fns:getConfig('css.version')}">
    <link rel="stylesheet" href="${ctxHtml}/css/paging.css?id=${fns:getConfig('css.version')}"/>
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
        $(document).ready(function () {

        });
        function page(n, s) {
            $("#pageNo").val(n);
            $("#pageSize").val(s);
            $("#searchForm").submit();
            return false;
        }
    </script>
</head>
<body>
<div style="width: 80%;margin: 20px auto;min-width: 800px">
    <h3>公安部门-系统操作日志</h3>
    <form:form id="searchForm" modelAttribute="policeLog" action="${ctx}/policeLogPage/showSysLog" method="post">
        <input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}">
        <input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}">
        <div class="form-group">
            <label for="">操作时间：</label>
            <input id="d5221" name="beginDate" style="width: 180px;height: 22px" type="text" class="Wdate searchtext form-control" value="<fmt:formatDate value="${policeLog.beginDate}" pattern="yyyy-MM-dd"/>"  onFocus="var d5222=$dp.$('d5222');WdatePicker	({onpicked:function(){d5222.focus();},maxDate:'#F{$dp.$D(\'d5222\')}',maxDate:'%y-%M-%d',dateFmt:'yyyy-MM-dd'})"/>
            至
            <input id="d5222"  name="endDate" style="width: 180px;height: 22px" type="text" class="Wdate searchtext form-control" value="<fmt:formatDate value="${policeLog.endDate}" pattern="yyyy-MM-dd"/>"  onFocus="WdatePicker({minDate:'#F{$dp.$D(\'d5221\')}',maxDate:'%y-%M-%d',dateFmt:'yyyy-MM-dd'})"/>
            <div class="form-group" style="float: right">
                <label for="">刻章点名称：</label>
                 <form:input path="title" type="text" class="form-control searchtext" htmlEscape="false"/>
                <button type="submit" class="btn btn-default " style="margin-bottom: 2px">搜索</button>
            </div>

        </div>
    </form:form>
    <table class="table table-bordered">
        <tr>
            <th >刻章点名称</th>
            <th >操作内容</th>
            <th>操作时间</th>
            <th>操作人</th>
        </tr>

        <c:forEach var="log" items="${page.list}">
            <tr>
                <td>${log.title}</td>
                <td>${log.content}</td>
                <td><fmt:formatDate value="${log.createDate}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
                <td>${log.operator.name}</td>
            </tr>
        </c:forEach>
    </table>
    <div class="pagination" style="display: block;text-align: center">
        ${page}
    </div>
</div>
</body>
</html>