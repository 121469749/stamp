<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>修改密码</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		$(document).ready(function() {
			$("#oldPassword").focus();
            $.validator.addMethod("regex", function(value, element, regexpr) {
                return regexpr.test(value);
            }, "Please enter a valid password.");   //增加regex正则表达式验证

            $("#inputForm").validate({
				rules: {
                    newPassword: {
                        regex: /^(?![^a-zA-Z]+$)(?!\D+$)/      //密码正则表达式字母和数字的组合
                    }
				},
				messages: {
					confirmNewPassword: {equalTo: "输入与上面相同的密码"},
                    newPassword: {
                        regex : '密码必须是数字和字母组合,可加特殊符号'
                    }
				},
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

        function pwStrength(pwd) {
            O_color = "#eeeeee";
            L_color = "#FF0000";
            M_color = "#FF9900";
            H_color = "#33CC00";
            var level = 0, strength = "O";
            if (pwd == null || pwd == '') {
                strength = "O";
                Lcolor = Mcolor = Hcolor = O_color;
            }
            else {
                var mode = 0;
                if (pwd.length <= 4)
                    mode = 0;
                else {
                    for (i = 0; i < pwd.length; i++) {
                        var charMode, charCode;
                        charCode = pwd.charCodeAt(i);
                        // 判断输入密码的类型
                        if (charCode >= 48 && charCode <= 57) //数字
                            charMode = 1;
                        else if (charCode >= 65 && charCode <= 90) //大写
                            charMode = 2;
                        else if (charCode >= 97 && charCode <= 122) //小写
                            charMode = 4;
                        else
                            charMode = 8;
                        mode |= charMode;
                    }
                    // 计算密码模式
                    level = 0;
                    for (i = 0; i < 4; i++) {
                        if (mode & 1)
                            level++;
                        mode >>>= 1;
                    }
                }
                switch (level) {
                    case 0:
                        strength = "O";
                        Lcolor = Mcolor = Hcolor = O_color;
                        break;
                    case 1:
                        strength = "L";
                        Lcolor = L_color;
                        Mcolor = Hcolor = O_color;
                        break;
                    case 2:
                        strength = "M";
                        Lcolor = Mcolor = M_color;
                        Hcolor = O_color;
                        break;
                    default:
                        strength = "H";
                        Lcolor = Mcolor = Hcolor = H_color;
                        break;
                }
            }
            document.getElementById("strength_L").style.background = Lcolor;
            document.getElementById("strength_M").style.background = Mcolor;
            document.getElementById("strength_H").style.background = Hcolor;

            return strength;
        }
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li><a href="${ctx}/sys/user/info">个人信息</a></li>
		<li class="active"><a href="${ctx}/sys/user/modifyPwd">修改密码</a></li>
	</ul><br/>
	<form:form id="inputForm" modelAttribute="user" action="${ctx}/sys/user/modifyPwd" method="post" class="form-horizontal">
		<form:hidden path="id"/>

		<sys:message content="${message}"/>
		<div class="control-group">
			<label class="control-label">旧密码:</label>
			<div class="controls">
				<input id="oldPassword" name="oldPassword" type="password" value="" maxlength="50" minlength="6" class="required"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">新密码:</label>
			<div class="controls">
				<input id="newPassword" name="newPassword" type="password" value="" maxlength="50" minlength="8" class="required"  onkeyup="pwStrength(this.value);" onblur="pwStrength(this.value);"/>
				<%--<span class="help-inline"><font color="red">*</font> </span>--%>
				<br>
				<span class="help-inline"><font color="red">密码强度:</font> </span>
				   <table width="220px" border="1" cellspacing="0" cellpadding="1" bordercolor="#eeeeee" height="22px">
				       <tr align="center" bgcolor="#f5f5f5">
				           <td width="33%" id="strength_L">弱</td>
				           <td width="33%" id="strength_M">中</td>
				           <td width="33%" id="strength_H">强</td>
					   </tr>
				   </table>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">确认新密码:</label>
			<div class="controls">
				<input id="confirmNewPassword" name="confirmNewPassword" type="password" value="" maxlength="50" minlength="8" class="required" equalTo="#newPassword"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="form-actions">
			<input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>
		</div>
	</form:form>
</body>
</html>