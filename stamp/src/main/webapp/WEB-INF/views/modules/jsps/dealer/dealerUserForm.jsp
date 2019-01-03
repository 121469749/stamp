<%--
  Created by IntelliJ IDEA.
  User: hjw-pc
  Date: 2017/6/16
  Time: 14:23
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
    <title>用户管理</title>
    <meta name="decorator" content="default"/>
    <script type="text/javascript">
        $(document).ready(function() {
            $("#no").focus();
            $("#inputForm").validate({
                rules: {
                    loginName: {remote: "${ctx}/dealer/user/checkLoginName?oldLoginName=" + encodeURIComponent('${user.loginName}')}
                },
                messages: {
                    loginName: {remote: "用户登录名已存在"},
                    confirmNewPassword: {equalTo: "输入与上面相同的密码"}
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
    </script>
</head>
<body>
<ul class="nav nav-tabs">
    <li><a href="${ctx}/dealer/user/list">用户列表</a></li>
    <li class="active"><a href="${ctx}/dealer/user/form?id=${user.id}">经销商<shiro:hasPermission name="dealer:runcheng:user:edit">${not empty user.id?'修改':'添加'}</shiro:hasPermission><shiro:lacksPermission name="dealer:runcheng:user:edit">查看</shiro:lacksPermission></a></li>
</ul><br/>

<form:form id="inputForm" modelAttribute="user" action="${ctx}/dealer/user/save" method="post" class="form-horizontal">
    <form:hidden path="id"/>
    <form:hidden path="userType"></form:hidden>
    <sys:message content="${message}"/>
    <%--<div class="control-group">--%>
        <%--<label class="control-label">头像:</label>--%>
        <%--<div class="controls">--%>
            <%--<form:hidden id="nameImage" path="photo" htmlEscape="false" maxlength="255" class="input-xlarge"/>--%>
            <%--<sys:ckfinder input="nameImage" type="images" uploadPath="/photo" selectMultiple="false" maxWidth="100" maxHeight="100"/>--%>
        <%--</div>--%>
    <%--</div>--%>
    <%--<div class="control-group">--%>
        <%--<label class="control-label">归属公司:</label>--%>
        <%--<div class="controls">--%>
            <%--<input id="company"  value="${user.company.name}"--%>
                   <%--readonly     class="readonly"       title="公司" />--%>
        <%--</div>--%>
    <%--</div>--%>
    <%--<div class="control-group">--%>
        <%--<label class="control-label">归属部门:</label>--%>
        <%--<div class="controls">--%>
            <%--<input id="office"  value="${user.office.name}"--%>
                     <%--readonly   class="readonly"          title="部门" />--%>
        <%--</div>--%>
    <%--</div>--%>
    <form:hidden path="companyInfo.id"></form:hidden>
    <div class="control-group">
        <label class="control-label">经销商统一码:</label>
        <div class="controls">
            <form:input path="companyInfo.soleCode" htmlEscape="false" maxlength="50" id="num" class="required"/>
            <span class="help-inline"><font color="red">*</font> </span>
        </div>
    </div>
    <div class="control-group">
        <label class="control-label">经销商公司名:</label>
        <div class="controls">
            <form:input path="companyInfo.companyName" htmlEscape="false" maxlength="50" id="comname" class="required"/>
            <span class="help-inline"><font color="red">*</font> </span>
        </div>
    </div>

    <div class="control-group">
        <label class="control-label">经销商公司联系电话:</label>
        <div class="controls">
            <form:input path="companyInfo.compPhone" id="compPhone" htmlEscape="false" maxlength="50" class="required"/>
            <span class="help-inline"><font color="red">*</font> </span>
        </div>
    </div>

    <div class="control-group">
        <label class="control-label">经销商公司地址:</label>
        <div class="controls">
            <form:input path="companyInfo.compAddress" id="compAddress" htmlEscape="false" maxlength="50" class="required"/>
            <span class="help-inline"><font color="red">*</font> </span>
        </div>
    </div>

    <div class="control-group">
        <label class="control-label">法人姓名:</label>
        <div class="controls">
            <form:input path="companyInfo.legalName" id="legalName" htmlEscape="false" maxlength="50" class="required"/>
            <span class="help-inline"><font color="red">*</font> </span>
        </div>
    </div>
    <div class="control-group">
        <label class="control-label">法人证件类型:</label>
        <div class="controls">
            <form:select path="companyInfo.legalCertType">
                <form:options items="${fns:getDictList('certificateType')}" itemLabel="label" itemValue="value"
                              htmlEscape="false"/>
            </form:select>
            <span class="help-inline"><font color="red">*</font> </span>
        </div>
    </div>
    <div class="control-group">
        <label class="control-label">法人证件号:</label>
        <div class="controls">
            <form:input path="companyInfo.legalCertCode" id="legalCertCode" htmlEscape="false" maxlength="50" class="required"/>
            <span class="help-inline"><font color="red">*</font> </span>
        </div>
    </div>

    <div class="control-group">
        <label class="control-label">法人电话:</label>
        <div class="controls">
            <form:input path="companyInfo.legalPhone" id="legalPhone" htmlEscape="false" maxlength="50" class="required"/>
            <span class="help-inline"><font color="red">*</font> </span>
        </div>
    </div>

    <div class="control-group">
        <label class="control-label">管辖区域:</label>
        <div class="controls">
            <sys:treeselect id="area" name="companyInfo.area.id" value="${user.companyInfo.area.id}" labelName="companyInfo.area.name" labelValue="${user.companyInfo.area.name}"
                            title="区域" url="/sys/area/treeData" cssClass="required" />
            <span class="help-inline"><font color="red">*</font> </span>
        </div>
    </div>
    <%--<div class="control-group">--%>
        <%--<label class="control-label">工号:</label>--%>
        <%--<div class="controls">--%>
            <%--<form:input path="no" htmlEscape="false" maxlength="50" class="required"/>--%>
            <%--<span class="help-inline"><font color="red">*</font> </span>--%>
        <%--</div>--%>
    <%--</div>--%>
    <div class="control-group">
        <label class="control-label">姓名:</label>
        <div class="controls">
            <form:input path="name" htmlEscape="false" maxlength="50" class="required"/>
            <span class="help-inline"><font color="red">*</font> </span>
        </div>
    </div>
    <div class="control-group">
        <label class="control-label">登录名:</label>
        <div class="controls">
            <input id="oldLoginName" name="oldLoginName" type="hidden" value="${user.loginName}">
            <form:input path="loginName" htmlEscape="false" maxlength="50" class="required userName"/>
            <span class="help-inline"><font color="red">*</font></span>
        </div>
    </div>
    <div class="control-group">
        <label class="control-label">密码:</label>
        <div class="controls">
            <input id="newPassword" name="newPassword" type="password" value="" maxlength="50" minlength="3" class="${empty user.id?'required':''}"/>
            <c:if test="${empty user.id}"><span class="help-inline"><font color="red">*</font> </span></c:if>
            <c:if test="${not empty user.id}"><span class="help-inline">若不修改密码，请留空。</span></c:if>
        </div>
    </div>
    <div class="control-group">
        <label class="control-label">确认密码:</label>
        <div class="controls">
            <input id="confirmNewPassword" name="confirmNewPassword" type="password" value="" maxlength="50" minlength="3" equalTo="#newPassword"/>
            <c:if test="${empty user.id}"><span class="help-inline"><font color="red">*</font> </span></c:if>
        </div>
    </div>
    <div class="control-group">
        <label class="control-label">邮箱:</label>
        <div class="controls">
            <form:input path="email" htmlEscape="false" maxlength="100" class="email"/>
        </div>
    </div>
    <div class="control-group">
        <label class="control-label">电话:</label>
        <div class="controls">
            <form:input path="phone" htmlEscape="false" maxlength="100"/>
        </div>
    </div>
    <div class="control-group">
        <label class="control-label">手机:</label>
        <div class="controls">
            <form:input path="mobile" htmlEscape="false" maxlength="100"/>
        </div>
    </div>
    <div class="control-group">
        <label class="control-label">是否允许登录:</label>
        <div class="controls">
            <form:select path="loginFlag">
                <form:options items="${fns:getDictList('yes_no')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
            </form:select>
            <span class="help-inline"><font color="red">*</font> “是”代表此账号允许登录，“否”则表示此账号不允许登录</span>
        </div>
    </div>

    <div class="control-group">
        <label class="control-label">用户角色:</label>
        <div class="controls">
            <form:radiobuttons path="roleIdList" items="${allRoles}" itemLabel="name" itemValue="id" htmlEscape="false" class="required"/>
            <span class="help-inline"><font color="red">*</font> </span>
        </div>
    </div>
    <div class="control-group">
        <label class="control-label">备注:</label>
        <div class="controls">
            <form:textarea path="remarks" htmlEscape="false" rows="3" maxlength="200" class="input-xlarge"/>
        </div>
    </div>
    <c:if test="${not empty user.id}">
        <div class="control-group">
            <label class="control-label">创建时间:</label>
            <div class="controls">
                <label class="lbl"><fmt:formatDate value="${user.createDate}" type="both" dateStyle="full"/></label>
            </div>
        </div>
        <div class="control-group">
            <label class="control-label">最后登陆:</label>
            <div class="controls">
                <label class="lbl">IP: ${user.loginIp}&nbsp;&nbsp;&nbsp;&nbsp;时间：<fmt:formatDate value="${user.loginDate}" type="both" dateStyle="full"/></label>
            </div>
        </div>
    </c:if>
    <div class="form-actions">
        <shiro:hasPermission name="dealer:runcheng:user:edit"><input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp</shiro:hasPermission>
        <input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
    </div>
</form:form>
<script>
    $(function(){
        var val1 = true;
        var val2 = true;
        var val3 = true;
        var val4 = true;
        var val5 = true;
        var val6 = true;
        var val7 = true;
        var val8 = true;
        var val9 = true;
        var val10 = true;
        var val11 = true;
        var val12 = true;
        $("#num").blur(function () {
            var reg = /^[0-9a-zA-Z]*$/;
            if($(this).val().split(" ").length != 1){
                val1 = false;
                $(this).next().remove();
                $(this).after("<label style='color: red'>输入格式不能含有空格</label>");

            }else if(!reg.test($(this).val())){
                val1 = false;
                $(this).next().remove();
                $(this).after("<label style='color: red'>统一码格式错误</label>");
            }else if($(this).val() == ""){
                val1 = true;
                $(this).next().remove();
            }else{
                val1 = true;
                $(this).next().remove();
            }
            if(val1 && val2 && val3 && val4 && val5 && val6 && val7 && val8 && val9 && val10 && val11 && val12){
                $("#btnSubmit").attr("disabled",false);
            }else{
                $("#btnSubmit").attr("disabled",true);
            }
        });
        $("#comname").blur(function () {
            var reg = /^([\u4e00-\u9fa5a-zA-Z]{1,40})$/;
            if(!reg.test($(this).val())){
                val2 = false;
                $(this).next().remove();
                $(this).after("<label style='color: red'>公司名称格式错误</label>");
            }else if($(this).val().split(" ").length != 1){
                val2 = false;
                $(this).next().remove();
                $(this).after("<label style='color: red'>输入格式不能含有空格</label>");
            }else if($(this).val() == ""){
                val2 = true;
                $(this).next().remove();
            }else{
                val2 = true;
                $(this).next().remove();
            }
            if(val1 && val2 && val3 && val4 && val5 && val6 && val7 && val8 && val9 && val10 && val11 && val12){
                $("#btnSubmit").attr("disabled",false);
            }else{
                $("#btnSubmit").attr("disabled",true);
            }
        });
        $("#compPhone").blur(function () {
            var regTel1 = /^(((13[0-9]{1})|(15[0-9]{1})|(18[0-9]{1}))+\d{8})$/;//手机号码
            var regTel2 = /^(([0\+]\d{2,3}-)?(0\d{2,3})-)(\d{7,8})(-(\d{3,}))?$/;//带区号的固定电话
            var regTel4 = /^(([0\+]\d{2,3}-)?(0\d{2,3}))(\d{7,8})(-(\d{3,}))?$/;//带区号的固定电话
            var regTel3 = /^(\d{7,8})(-(\d{3,}))?$/;//不带区号的固定电话
            if(!regTel1.test($(this).val()) && !regTel2.test($(this).val()) && !regTel3.test($(this).val())&& !regTel4.test($(this).val())){
                val3 = false;
                $(this).next().remove();
                $(this).after("<label style='color: red'>输入电话号码有误</label>");
            }else if($(this).val().split(" ").length != 1){
                val3 = false;
                $(this).next().remove();
                $(this).after("<label style='color: red'>输入格式不能含有空格</label>");
            }else if($(this).val() == ""){
                val3 = true;
                $(this).next().remove();
            }else{
                val3 = true;
                $(this).next().remove();
            }
            if(val1 && val2 && val3 && val4 && val5 && val6 && val7 && val8 && val9 && val10 && val11 && val12){
                $("#btnSubmit").attr("disabled",false);
            }else{
                $("#btnSubmit").attr("disabled",true);
            }
        });
        $("#compAddress").blur(function () {
            if($(this).val().split(" ").length != 1){
                val4 = false;
                $(this).next().remove();
                $(this).after("<label style='color: red'>输入格式不能含有空格</label>");
            }else if($(this).val() == ""){
                val4 = true;
                $(this).next().remove();
            }else{
                val4 = true;
                $(this).next().remove();
            }
            if(val1 && val2 && val3 && val4 && val5 && val6 && val7 && val8 && val9 && val10 && val11 && val12){
                $("#btnSubmit").attr("disabled",false);
            }else{
                $("#btnSubmit").attr("disabled",true);
            }
        });
        $("#legalName").blur(function () {
            var reg = /^([\u4e00-\u9fa5]{1,20}|[a-zA-Z\.\s]{1,20})$/;
            if(!reg.test($(this).val())){
                val5 = false;
                $(this).next().remove();
                $(this).after("<label style='color: red'>姓名格式错误</label>");
            }else if($(this).val().split(" ").length != 1){
                val5 = false;
                $(this).next().remove();
                $(this).after("<label style='color: red'>输入格式不能含有空格</label>");
            }else if($(this).val() == ""){
                val5 = true;
                $(this).next().remove();
            }else{
                val5 = true;
                $(this).next().remove();
            }
            if(val1 && val2 && val3 && val4 && val5 && val6 && val7 && val8 && val9 && val10 && val11 && val12){
                $("#btnSubmit").attr("disabled",false);
            }else{
                $("#btnSubmit").attr("disabled",true);
            }
        });
        $("#legalCertCode").blur(function () {
            var regular = /^[0-9a-zA-Z]{9,18}$/;
            if($(this).val().split(" ").length != 1) {
                val6 = false;
                $(this).next().remove();
                $(this).after("<label>输入格式不能含有空格！</label>");
            }else if(!regular.test($(this).val())){
                val6 = false;
                $(this).next().remove();
                $(this).after("<label>输入证件格式错误</label>");
            }else if($(this).val() == ""){
                val6 = true;
                $(this).next().remove();
            }else{
                val6 = true;
                $(this).next().remove();
            }
            if(val1 && val2 && val3 && val4 && val5 && val6 && val7 && val8 && val9 && val10 && val11 && val12){
                $("#btnSubmit").attr("disabled",false);
            }else{
                $("#btnSubmit").attr("disabled",true);
            }
        });
        $("#legalPhone").blur(function () {
            var regTel1 = /^(((13[0-9]{1})|(15[0-9]{1})|(18[0-9]{1}))+\d{8})$/;//手机号码
            var regTel2 = /^(([0\+]\d{2,3}-)?(0\d{2,3})-)(\d{7,8})(-(\d{3,}))?$/;//带区号的固定电话
            var regTel3 = /^(\d{7,8})(-(\d{3,}))?$/;//不带区号的固定电话
            if(!regTel1.test($(this).val()) && !regTel2.test($(this).val()) && !regTel3.test($(this).val())){
                val7 = false;
                $(this).next().remove();
                $(this).after("<label style='color: red'>输入电话号码有误</label>");
            }else if($(this).val().split(" ").length != 1){
                val7 = false;
                $(this).next().remove();
                $(this).after("<label style='color: red'>输入格式不能含有空格</label>");
            }else if($(this).val() == ""){
                val7 = true;
                $(this).next().remove();
            }else{
                val7 = true;
                $(this).next().remove();
            }
            if(val1 && val2 && val3 && val4 && val5 && val6 && val7 && val8 && val9 && val10 && val11 && val12){
                $("#btnSubmit").attr("disabled",false);
            }else{
                $("#btnSubmit").attr("disabled",true);
            }
        });
        $("#name").blur(function () {
            var reg = /^([\u4e00-\u9fa5]{1,20}|[a-zA-Z\.\s]{1,20})$/;
            if(!reg.test($(this).val())){
                val8 = false;
                $(this).next().remove();
                $(this).after("<label style='color: red'>姓名格式错误</label>");
            }else if($(this).val().split(" ").length != 1){
                val8 = false;
                $(this).next().remove();
                $(this).after("<label style='color: red'>输入格式不能含有空格</label>");
            }else if($(this).val() == ""){
                val8 = true;
                $(this).next().remove();
            }else{
                val8 = true;
                $(this).next().remove();
            }
            if(val1 && val2 && val3 && val4 && val5 && val6 && val7 && val8 && val9 && val10 && val11 && val12){
                $("#btnSubmit").attr("disabled",false);
            }else{
                $("#btnSubmit").attr("disabled",true);
            }
        });
        $("#no").blur(function () {
            var reg = /^[0-9a-zA-Z]*$/;
            if($(this).val().split(" ").length != 1){
                val9 = false;
                $(this).next().remove();
                $(this).after("<label style='color: red'>输入格式不能含有空格</label>");

            }else if(!reg.test($(this).val())){
                val9 = false;
                $(this).next().remove();
                $(this).after("<label style='color: red'>工号格式错误</label>");
            }else if($(this).val() == ""){
                val9 = true;
                $(this).next().remove();
            }else{
                val9 = true;
                $(this).next().remove();
            }
            if(val1 && val2 && val3 && val4 && val5 && val6 && val7 && val8 && val9 && val10 && val11 && val12){
                $("#btnSubmit").attr("disabled",false);
            }else{
                $("#btnSubmit").attr("disabled",true);
            }
        });
        $("#phone").blur(function () {
            var regTel1 = /^(((13[0-9]{1})|(15[0-9]{1})|(18[0-9]{1}))+\d{8})$/;//手机号码
            var regTel2 = /^(([0\+]\d{2,3}-)?(0\d{2,3})-)(\d{7,8})(-(\d{3,}))?$/;//带区号的固定电话
            var regTel3 = /^(\d{7,8})(-(\d{3,}))?$/;//不带区号的固定电话
            if(!regTel1.test($(this).val()) && !regTel2.test($(this).val()) && !regTel3.test($(this).val())){
                val11 = false;
                $(this).next().remove();
                $(this).after("<label style='color: red'>输入电话号码有误</label>");
            }else if($(this).val().split(" ").length != 1){
                val11 = false;
                $(this).next().remove();
                $(this).after("<label style='color: red'>输入格式不能含有空格</label>");
            }else{
                val11 = true;
                $(this).next().remove();
            }
            if($(this).val() == ""){
                val11 = true;
                $(this).next().remove();
            }
            if(val1 && val2 && val3 && val4 && val5 && val6 && val7 && val8 && val9 && val10 && val11 && val12){
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
                val12 = false;
                $(this).next().remove();
                $(this).after("<label style='color: red'>输入电话号码有误</label>");
            }else if($(this).val().split(" ").length != 1){
                val12 = false;
                $(this).next().remove();
                $(this).after("<label style='color: red'>输入格式不能含有空格</label>");
            }else{
                val12 = true;
                $(this).next().remove();
            }
            if($(this).val() == ""){
                val12 = true;
                $(this).next().remove();
            }
            if(val1 && val2 && val3 && val4 && val5 && val6 && val7 && val8 && val9 && val10 && val11 && val12){
                $("#btnSubmit").attr("disabled",false);
            }else{
                $("#btnSubmit").attr("disabled",true);
            }
        });
    })
</script>
</body>
</html>
