<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>数据保存成功管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		$(document).ready(function() {
			
		});
		function page(n,s){
			$("#pageNo").val(n);
			$("#pageSize").val(s);
			$("#searchForm").submit();
        	return false;
        }
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li class="active"><a href="${ctx}/countsetlog/tCountSetLog/">印章分配历史记录列表</a></li>
		<shiro:hasPermission name="countsetlog:tCountSetLog:edit"><li><a href="${ctx}/countsetlog/tCountSetLog/form">数据保存成功添加</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="tCountSetLog" action="${ctx}/countsetlog/tCountSetLog/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<input name="companyId" type="hidden" value="${tCountSetLog.companyId}">
		<ul class="ul-form">
			<li><label>分配时间：</label>

				<input name="beginUpdateDate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
					value="<fmt:formatDate value="${tCountSetLog.beginUpdateDate}" pattern="yyyy-MM-dd HH:mm:ss"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});"/> - 
				<input name="endUpdateDate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
					value="<fmt:formatDate value="${tCountSetLog.endUpdateDate}" pattern="yyyy-MM-dd HH:mm:ss"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});"/>
			</li>
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>分配时间</th>
				<th>单位名称</th>
				<th>本次分配电子印章数</th>
				<th>历史电子印章数</th>
				<th>本次分配物理印章数</th>
				<th>历史物理印章数</th>
				<shiro:hasPermission name="countsetlog:tCountSetLog:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="tCountSetLog">
			<tr>
				<td>
					<fmt:formatDate value="${tCountSetLog.updateDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<td><%--<a href="${ctx}/countsetlog/tCountSetLog/form?id=${tCountSetLog.id}">--%>
					${tCountSetLog.companyName}
				<%--</a>--%></td>
				<td>
					${tCountSetLog.eleCount}
				</td>
				<td>
					${tCountSetLog.eleSumcount}
				</td>
				<td>
					${tCountSetLog.phyCount}
				</td>
				<td>
					${tCountSetLog.phySumcount}
				</td>
				<td>
					<fmt:formatDate value="${tCountSetLog.updateDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<shiro:hasPermission name="countsetlog:tCountSetLog:edit"><td>
    				<a href="${ctx}/countsetlog/tCountSetLog/form?id=${tCountSetLog.id}">修改</a>
					<a href="${ctx}/countsetlog/tCountSetLog/delete?id=${tCountSetLog.id}" onclick="return confirmx('确认要删除该数据保存成功吗？', this.href)">删除</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
	<div style="text-align: center;margin-top: 10px">
		<button type="button" onclick="window.history.go(-1)" class="btn btn-primary">返 回</button>
	</div>
</body>
</html>