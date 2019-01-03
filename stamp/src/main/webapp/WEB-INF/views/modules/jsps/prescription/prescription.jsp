<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2017/11/19
  Time: 14:19
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp" %>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="decorator" content="default"/>
    <!-- ie渲染-->
    <meta http-equiv="X-UA-Compatible" content="IE=Edge，chrome=1">
    <link rel="stylesheet" href="${ctxHtml}/css/bootstrap.min.css">
    <link rel="stylesheet" href="${ctxHtml}/css/portalWeb_style.css?id=1"/>
    <script src="${ctxHtml}/js/jquery-1.9.1.min.js"></script>
    <script src="${ctxHtml}/js/bootstrap.min.js"></script>
    <script src="${ctxHtml}/js/My97DatePicker/WdatePicker.js"></script>
    <title>润成时效控制</title>
    <script>
        function page(n, s) {
            $("#pageNo").val(n);
            $("#pageSize").val(s);
            $("#searchForm").submit();
            return false;
        }
    </script>
    <style>
        .pages{
            display: block;
            text-align: center;
        }
        .pages input{
            width: 55px;
            border: 0;
        }
        .table th,.table td{
            text-align: center;
            vertical-align: middle;
            !important;
        }
    </style>
</head>
<body>

<div class="center clearfix" style="padding-top: 2%;width: 90%;min-width: 1060px">
    <form:form id="searchForm" action="${ctx}/control/stamp/page" method="post" modelAttribute="dto">
        <input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
        <input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
        <div class="form-group" style="text-align: center">
            <label ><font size="5px">公司名称：</font></label>
            <form:input path="companyName" type="text" class="input-medium" cssStyle="height: 35px;width: 200px;"/>
            <label ><font size="5px">印章名称：</font></label>
            <form:input path="stampName" type="text" class="input-medium" cssStyle="height: 35px;width: 100px;" />
            <label ><font size="5px">印章编码：</font></label>
            <form:input path="stampCode" type="text" class="input-medium" cssStyle="height: 35px;width: 200px;" />
                <%-- <label style="color: black">印模类型：</label>
                 <form:select path="stampShape" cssStyle="padding: 3px 5px">
                     <form:option value=""></form:option>
                     <form:option value="1">物理印模</form:option>
                     <form:option value="2">电子印模</form:option>
                 </form:select>--%>
            <button type="submit" class="search-btn btn-info " style="margin-bottom: 4px">搜索</button>
        </div>
    </form:form>
    <table class="table table-hover table-striped table-bordered">
        <thead>
        <tr>
            <th>印模图片</th>
            <th>印章类型</th>
            <th>印章编码</th>
            <th>印模类型</th>
            <th>有效开始时间</th>
            <th>有效结束时间</th>
            <%--<th>润城控制时间</th>--%>
            <th>公司名称</th>
            <th>法人姓名</th>
            <th>操作</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${page.list}" var="stamp">
            <tr>
                <td>
                    <c:if test="${stamp.stampShape == '1'}">
                        <img src="/img${stamp.phyModel}" width="140px" alt="">
                    </c:if>
                    <c:if test="${stamp.stampShape == '2'}">
                        <img src="/img${stamp.eleModel}" width="140px" alt="">
                    </c:if>
                </td>
                <td>${stamp.stampName}</td>
                <td>${stamp.stampCode}</td>
                <td>
                    <c:if test="${stamp.stampShape == '1'}">
                        物理印模
                    </c:if>
                    <c:if test="${stamp.stampShape == '2'}">
                        电子印模
                    </c:if>
                </td>
                <td><fmt:formatDate value="${stamp.electron.validDateStart}" pattern="yyyy-MM-dd"/></td>
                <td><fmt:formatDate value="${stamp.electron.validDateEnd}" pattern="yyyy-MM-dd"/></td>
                    <%--<td><fmt:formatDate value="${stamp.electron.rcDate}" pattern="yyyy-MM-dd"/></td>--%>
                <td>${stamp.useComp.companyName}</td>
                <td>${stamp.useComp.legalName}</td>
                <td>
                        <%-- <c:if test="${stamp.stampShape == '2'}">
                             <button class="search-btn btn-info" data-toggle="modal" data-target=".bs-example-modal-lg" onclick="chang('${stamp.electron.id}')">时效控制</button>
                         </c:if>--%>
                    <c:if test="${stamp.electron.sysOprState.key == 1}">
                        <a id="check" class="search-btn btn-info" href="${ctx}/control/stamp/systemOpration/stop?companyId=${stamp.electron.id}" style="background-color: coral" onclick="return confirmx('确认要停用该印章吗？', this.href)">管控停用</a>
                    </c:if>
                    <c:if test="${stamp.electron.sysOprState.key == 2}" >
                        <a id="check" class="search-btn btn-info" href="${ctx}/control/stamp/systemOpration/start?companyId=${stamp.electron.id}" onclick="return confirmx('确认要启用该印章吗？', this.href)">管控启用</a>
                    </c:if>
                </td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
    <div class="pages" style="margin: 0 auto">
        ${page}
    </div>
</div>
<div  class="modal fade bs-example-modal-lg" tabindex="-1" role="dialog" aria-labelledby="myLargeModalLabel">
    <div class="modal-dialog modal-lg" role="document">
        <div class="modal-content" style="width: 768px">
            <div class="modal-header" style="border: 0;padding-right: 10%">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                    <span aria-hidden="true"><img src="${ctxHtml}/images/close.png"></span></button>
            </div>
            <table class="table table-bordered" style="width:80%;margin: 5px auto">
                <tr>
                    <td>印章时效：</td>
                    <td><input type="text" class="form-control" id="useTime" onFocus="WdatePicker({startDate:'%y-%M-%D 00:00:00',dateFmt:'yyyy-MM-dd HH:mm:ss',minDate:'%y-%M-{%d}',alwaysUseStartDate:true})"></td>
                </tr>
            </table>
            <input type="text" id="stampId" style="display: none">
            <div style="text-align: center;margin-bottom: 20px">
                <button class="search-btn btn-primary" onclick="sub()">提交</button>
            </div>
        </div>
    </div>
</div>
<script>
    <%--点击更改时效时填入对应的印模id--%>
    function chang(i){
        document.getElementById("stampId").value = i;
    }
    <%--点击提交，获取值传给后台--%>
    function sub(){
        var i = document.getElementById("stampId").value;
        var t = document.getElementById("useTime").value;
        $.ajax({
            type:"post",
            dateType:"json",
            url:"${ctx}/control/stamp/control",
            data:{"id":i,"rcDate":t},
            success:function(result){
                result = JSON.parse(result);
                alert(result.message);
                if(result.code == 200){
                    location.reload();
                }
            },
            error:function(){
                alert("出错了!");
            }
        });
    }
</script>
</body>
</html>
