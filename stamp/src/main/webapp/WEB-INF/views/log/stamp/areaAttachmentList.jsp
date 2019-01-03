<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>xxx管理</title>
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
		<li class="active"><a href="${ctx}/stamp/areaAttachment/">xxx列表</a></li>
		<shiro:hasPermission name="stamp:areaAttachment:edit"><li><a href="${ctx}/stamp/areaAttachment/form">xxx添加</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="areaAttachment" action="${ctx}/stamp/areaAttachment/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>区域：</label>
				<sys:treeselect id="area" name="area.id" value="${areaAttachment.area.id}" labelName="area.name" labelValue="${areaAttachment.area.name}"
					title="区域" url="/sys/area/treeData" cssClass="input-small" allowClear="true" notAllowSelectParent="true"/>
			</li>
			<li><label>业务办理类型：</label>
				<form:select path="type" class="input-medium">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</li>
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>更新时间</th>
				<th>备注</th>
				<shiro:hasPermission name="stamp:areaAttachment:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="areaAttachment">
			<tr>
				<td><a href="${ctx}/stamp/areaAttachment/form?id=${areaAttachment.id}">
					<fmt:formatDate value="${areaAttachment.updateDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</a></td>
				<td>
					${areaAttachment.remarks}
				</td>
				<shiro:hasPermission name="stamp:areaAttachment:edit"><td>
    				<a href="${ctx}/stamp/areaAttachment/form?id=${areaAttachment.id}">修改</a>
					<a href="${ctx}/stamp/areaAttachment/delete?id=${areaAttachment.id}" onclick="return confirmx('确认要删除该xxx吗？', this.href)">删除</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>