<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2017/7/22
  Time: 10:32
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp" %>
<!DOCTYPE html>
<html lang="zh">
<head>
    <meta charset="UTF-8">
    <meta name="decorator" content="default"/>
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no"/>
    <title>水印列表</title>
    <meta name="decorator" content="default"/>
    <style>
        table input {
            width: 100%;
            border: none;
            background: transparent;
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
        function exportExcel() {

            $("#searchForm").attr("action","${ctx}/makeCompany/count/exportExcel");
            $("#searchForm").submit();
            $("#searchForm").attr("action","${ctx}/makeCompany/count/list");
        }

    </script>
</head>
<body>
    <ul class="nav nav-tabs">
        <li class="active"><a href="${ctx}/water/page">水印列表</a></li>
        <li><a href="${ctx}/water/form">水印添加</a></li>
    </ul><br/>
<h3 style="width: 80%;margin: 10px auto">水印管理</h3>
<%--<form:form id="searchForm" modelAttribute="dto" action="${ctx}/makeCompany/count/list"--%>
           <%--method="post"--%>
           <%--cssStyle="text-align: center">--%>
    <%--<div style="width: 1300px">--%>
        <%--<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>--%>
        <%--<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>--%>
        <%--<label for="companyName">水印名称:</label>--%>
        <%--<form:input id="companyName" path="company.companyName"/>--%>
        <%--<input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/>--%>
    <%--</div>--%>
<%--</form:form>--%>
<table class="table table-striped table-bordered" style="width: 93%;margin: 10px auto">
    <tr>
        <%--以下th标签为列表的头--%>
        <th>水印图片</th>
        <th>水印名称</th>
        <th>更新时间</th>
        <th>备注</th>
        <th>操作</th>
    </tr>
    <c:forEach items="${page.list}" var="w">
        <tr>
            <td><img src="/img${w.filePath}" alt="" style="max-width: 150px"></td>
            <td>${w.name}</td>
            <td><fmt:formatDate value="${w.createDate}" pattern="yyyy-MM-dd"/></td>
            <td>${w.remarks}</td>
            <td width="200px">
                <div style="width: 200px!important;">
                    <a href="#" onclick="del('${w.id}')">删除</a>|
                    <a href="${ctx}/water/form?id=${w.id}">修改</a>
                </div>
            </td>
        </tr>
    </c:forEach>
</table>
<div class="pagination" style="margin-left: 20%">
    ${page}
</div>
    <script>
        function del(i){
            var msg = "确定删除吗？";
            if(confirm(msg)){
                $.ajax({
                    type:"post",
                    dateTypt:"json",
                    url:"${ctx}/water/delete",
                    data:{"id":i},
                    success:function (result) {
                        result = JSON.parse(result);
                        alert(result.message);
                        if(result.code == 200){
                            location.reload();
                        }
                    },
                    error:function(){
                        alert("出错了!");
                    }
                })
            }
        }
    </script>
</body>
</html>
