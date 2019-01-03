<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>区域对应办事附件管理</title>
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
		<li class="active"><a href="${ctx}/areaattachment/areaAttachment/">区域对应办事附件列表</a></li>
		<li><a href="${ctx}/areaattachment/areaAttachment/form">区域对应办事附件添加</a></li>
	</ul>
	<form:form id="searchForm" modelAttribute="areaAttachment" action="${ctx}/areaattachment/areaAttachment/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>区域：</label>
				<sys:treeselect id="area" name="area.id" value="${areaAttachment.area.id}" labelName="area.name" labelValue="${areaAttachment.area.name}"
					title="区域" url="/sys/area/treeData" cssClass="input-small" allowClear="true" notAllowSelectParent="true"/>
			</li>
			<li><label>业务类型：</label>
				<form:select path="type" class="input-medium">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('service_type')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
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
				<th>区域</th>
				<th>业务类型</th>
				<th>更新时间</th>
				<th>备注</th>
				<th>操作</th>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="areaAttachment">
			<tr>
				<td>
					${areaAttachment.area.name}
				</td>
				<td>
					${fns:getDictLabel(areaAttachment.type,"service_type" ,null )}
				</td>
				<td>
					<fmt:formatDate value="${areaAttachment.updateDate}" pattern="yyyy-MM-dd"></fmt:formatDate>

				</td>
				<td>
					${areaAttachment.remarks}
				</td>
				<td>
    				<a href="${ctx}/areaattachment/areaAttachment/get?id=${areaAttachment.id}">修改</a>
					<a href="${ctx}/areaattachment/areaAttachment/delete?id=${areaAttachment.id}" onclick="return confirmx('确认要删除该用户吗？', this.href)">删除</a>
				</td>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>