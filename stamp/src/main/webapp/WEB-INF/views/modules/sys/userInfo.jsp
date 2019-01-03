<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>个人信息</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		$(document).ready(function() {
			$("#inputForm").validate({
				submitHandler: function(form){
					loading('正在提交，请稍等...');
					form.submit();
                    closeLoading();
				},
				errorContainer: "#messageBox",
				errorPlacement: function(error, element) {
					$("#messageBox").text("输入有误，请先更正。");
					if (element.is(":checkbox")||element.is(":radio")||element.parent().is(".input-append")){
						error.appendTo(element.parent().parent());
					} else {
						error.insertAfter(element);
					}
				}
			});
		});
		function updateCert() {
		    $("#updateCert").attr("disabled","disabled");
			$.ajax({
                type:"post",
                dataType:"json",
                url:"${ctx}/sys/user/updateCert",
				success:function (result) {
					if(result.code == 200){
					    alert("更新证书成功！请重新进行证书申请");
                        location.reload();
                    }else{
					    alert("更新证书失败！");
                    }
                },
				error:function (e) {
					alert(e);
                }
			})
        }
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li class="active"><a href="${ctx}/sys/user/info">个人信息</a></li>
		<li><a href="${ctx}/sys/user/modifyPwd">修改密码</a></li>
	</ul><br/>
	<form:form id="inputForm" modelAttribute="user" action="${ctx}/sys/user/info" method="post" class="form-horizontal">
		<%--<a href="${ctx}/test/test/testUpdate">测试更新数据库</a>--%>
		<%--<br>--%>
		<%--<a href="${ctx}/test/test/updateCertificate">测试：更新证书</a>--%>
		<div class="control-group">
			<label class="control-label">姓名:</label>
			<div class="controls">
				<form:input path="name" htmlEscape="false" maxlength="50" class="required" readonly="true"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">邮箱:</label>
			<div class="controls">
				<form:input path="email" htmlEscape="false" maxlength="50" class="email"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">电话:</label>
			<div class="controls">
				<form:input path="phone" htmlEscape="false" maxlength="50"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">手机:</label>
			<div class="controls">
				<form:input path="mobile" htmlEscape="false" maxlength="50"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">备注:</label>
			<div class="controls">
				<form:textarea path="remarks" htmlEscape="false" rows="3" maxlength="200" class="input-xlarge"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">用户类型:</label>
			<div class="controls">
				<label class="lbl">${user.userType.value}</label>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">用户角色:</label>
			<div class="controls">
				<label class="lbl">${user.roleNames}</label>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">上次登录:</label>
			<div class="controls">
				<label class="lbl">IP: ${user.oldLoginIp}&nbsp;&nbsp;&nbsp;&nbsp;时间：<fmt:formatDate value="${user.oldLoginDate}" type="both" dateStyle="full"/></label>
			</div>
		</div>
		<c:if test="${user.userType.key == '3'  ||  user.userType.key == '4'}">
			<c:if test="${hasCert == true}">
				<div class="control-group">
					<label class="control-label">证书:</label>
					<div class="controls">
						<a href="${ctx}/sys/user/downloadCert" class="btn btn-success" style="filter: none">下载证书</a>
						<a class="btn btn-primary" id="updateCert" onclick="updateCert()" style="filter: none">更新证书</a>
					</div>
				</div>
			</c:if>
			<c:if test="${hasCert == false}">
				<div class="control-group">
					<label class="control-label">证书:</label>
					<div class="controls">
						<a id="applyCert" class="btn btn-primary" style="filter: none">申请证书</a>
					</div>
				</div>
			</c:if>
		</c:if>

		<div class="form-actions">
			<input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>
		</div>
	</form:form>
	<script>
		$(function(){
		    $("#applyCert").click(function () {
                $("#applyCert").attr("disabled","disabled");
				$.ajax({
					type:"post",
					dataType:"json",
					url:"${ctx}/sys/user/applyCert",
					success:function (result) {
						if(result.code == 200){
						    alert("申请证书成功!");
                            location.reload();
                        }else{
                            $("#applyCert").removeAttr("disabled");
						    alert("申请证书失败!");
                        }
                    },
					error:function(e){
                        $("#applyCert").removeAttr("disabled");
					    alert(e);
                    }
				})
            });
		    var val1 = true;
            var val2 = true;
            var val3 = true;
			$("#phone").blur(function () {
                var regTel1 = /^(((13[0-9]{1})|(15[0-9]{1})|(18[0-9]{1}))+\d{8})$/;//手机号码
                var regTel2 = /^(([0\+]\d{2,3}-)?(0\d{2,3})-)(\d{7,8})(-(\d{3,}))?$/;//带区号的固定电话
                var regTel3 = /^(\d{7,8})(-(\d{3,}))?$/;//不带区号的固定电话
                if(!regTel1.test($(this).val()) && !regTel2.test($(this).val()) && !regTel3.test($(this).val())){
                    val2 = false;
                    $(this).next().remove();
                    $(this).after("<label style='color: red'>输入电话号码有误</label>");
                }else if($(this).val().split(" ").length != 1){
					val2 = false;
                    $(this).next().remove();
                    $(this).after("<label style='color: red'>输入格式不能含有空格</label>");
				}else{
                    val2 = true;
                    $(this).next().remove();
				}
                if($(this).val() == ""){
                    val2 = true;
                    $(this).next().remove();
                }
                if(val1 && val2 && val3){
                    $("#btnSubmit").attr("disabled",false);
                }else{
                    $("#btnSubmit").attr("disabled",true);
                }
            });
            $("#mobile").blur(function () {
                var regTel1 = /^(((13[0-9]{1})|(15[0-9]{1})|(18[0-9]{1}))+\d{8})$/;//手机号码
                var regTel2 = /^(([0\+]\d{2,3}-)?(0\d{2,3})-)(\d{7,8})(-(\d{3,}))?$/;//带区号的固定电话
                var regTel3 = /^(\d{7,8})(-(\d{3,}))?$/;//不带区号的固定电话
                if(!regTel1.test($(this).val()) && !regTel2.test($(this).val()) && !regTel3.test($(this).val())){
                    val3 = false;
                    $(this).next().remove();
                    $(this).after("<label style='color: red'>输入电话号码有误</label>");
                }else if($(this).val().split(" ").length != 1){
                    val3 = false;
                    $(this).next().remove();
                    $(this).after("<label style='color: red'>输入格式不能含有空格</label>");
                }else{
                    val3 = true;
                    $(this).next().remove();
                }
                if($(this).val() == ""){
                    val3 = true;
                    $(this).next().remove();
                }
                if(val1 && val2 && val3){
                    $("#btnSubmit").attr("disabled",false);
                }else{
                    $("#btnSubmit").attr("disabled",true);
                }
            })
        });

	</script>
</body>
</html>