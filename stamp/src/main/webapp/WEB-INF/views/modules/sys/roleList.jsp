]<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>角色管理</title>
	<meta name="decorator" content="default"/>
</head>
<body>
	<ul class="nav nav-tabs">
		<li class="active"><a href="${ctx}/sys/role/">角色列表</a></li>
		<shiro:hasPermission name="sys:role:edit">
			<li><a href="${ctx}/sys/role/toCreate">角色添加</a></li>
		</shiro:hasPermission>
	</ul>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<tr><th>角色名称</th>
			<th>英文名称</th>
			<th>用户类型组</th>
			<shiro:hasPermission name="sys:role:edit">
				<th>操作</th>
			</shiro:hasPermission>
		</tr>
		<c:forEach items="${list}" var="role">
			<tr>
				<td><a href="form?id=${role.id}">${role.name}</a></td>
				<td><a href="form?id=${role.id}">${role.enname}</a></td>
				<td>${fns:getDictLabel(role.deptType,"sys_role_type" ,null )}</td>
				<shiro:hasPermission name="sys:role:edit"><td>
					<%--<a href="${ctx}/sys/role/assign?id=${role.id}">分配</a>--%>
					<%--<c:if test="${(role.sysData eq fns:getDictValue('是', 'yes_no', '1') && fns:getUser().admin)||!(role.sysData eq fns:getDictValue('是', 'yes_no', '1'))}">--%>
						<a href="${ctx}/sys/role/form?id=${role.id}">修改</a>
					<%--</c:if>--%>
						<a href="#" onclick="del('${role.id}')">删除</a>
					<%--<a href="${ctx}/sys/role/delete?id=${role.id}" onclick="return confirmx('确认要删除该角色吗？', this.href)">删除</a>--%>
				</td></shiro:hasPermission>	
			</tr>
		</c:forEach>
	</table>
	<script>
		function del(h) {
			var msg = "确认要删除该角色吗？";
			if(confirm(msg) == true){
				$.ajax({
					type:"post",
					url:"${ctx}/sys/role/checkDelete",
					data:{'id':h},
					dataType:"json",
					success:function (result) {
						if(result.code == 200){
						    window.location = "${ctx}/sys/role/delete?id=" + h;
                        }else{
						    alert("删除失败.");
                        }
                    },
					error:function(){
						alert("出错了!");
                    }
				})
			}else{
			    return false;
			}
        }
	</script>
</body>
</html>