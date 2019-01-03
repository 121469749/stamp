<%--
  Created by IntelliJ IDEA.
  User: hjw-pc
  Date: 2017/5/14
  Time: 20:16
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
    <meta name="decorator" content="default"/>
    <title>Title</title>
</head>
<body>
<ul class="nav nav-tabs">
    <li>测试公安账户生成</li>
</ul><br/>
<form:form id="inputForm" modelAttribute="company" action="${ctx}/policeMakeComAction/saveMakeCompany" method="post" class="form-horizontal">
    <form:hidden path="id"/>
    <sys:message content="${message}"/>
    <div class="control-group">
    <label class="control-label">所属区域：</label>
    <div class="controls">
    <sys:treeselect id="area" name="area.id" value="${company.area.id}" labelName="area.name" labelValue="${company.area.name}"
                    title="区域" url="/sys/area/treeData" cssClass="" allowClear="true" notAllowSelectParent="true"/>
    </div>
    </div>
    <div class="control-group">
    <label class="control-label">企业名称：</label>
    <div class="controls">
    <form:input path="comapnyName" htmlEscape="false" maxlength="64" class="input-xlarge "/>
    </div>
    </div>
    <div class="control-group">
        <label class="control-label">单位唯一编码：</label>
        <div class="controls">
            <form:input path="soleCode" htmlEscape="false" maxlength="200" class="input-xlarge required"/>
            <span class="help-inline"><font color="red">*</font> </span>
        </div>
    </div>
    <div class="control-group">
        <label class="control-label">法定代表人：</label>
        <div class="controls">
            <form:input path="legalName" htmlEscape="false" maxlength="64" class="input-xlarge required"/>
            <span class="help-inline"><font color="red">*</font> </span>
        </div>
    </div>
    <div class="control-group">
        <label class="control-label">法人手机：</label>
        <div class="controls">
            <form:input path="legalPhone" htmlEscape="false" maxlength="64" class="input-xlarge required"/>
            <span class="help-inline"><font color="red">*</font> </span>
        </div>
    </div>
    <div class="control-group">
        <label class="control-label">法人证件类型：</label>
        <div class="controls">
            <form:input path="legalCertType" htmlEscape="false" maxlength="10" class="input-xlarge required"/>
            <span class="help-inline"><font color="red">*</font> </span>
        </div>
    </div>
    <div class="control-group">
        <label class="control-label">法人证件号码：</label>
        <div class="controls">
            <form:input path="legalCertCode" htmlEscape="false" maxlength="64" class="input-xlarge required"/>
            <span class="help-inline"><font color="red">*</font> </span>
        </div>
    </div>
    <div class="control-group">
        <label class="control-label">治安负责人：</label>
        <div class="controls">
            <form:input path="policeName" htmlEscape="false" maxlength="64" class="input-xlarge required"/>
            <span class="help-inline"><font color="red">*</font> </span>
        </div>
    </div>
    <div class="control-group">
        <label class="control-label">治安负责人身份证号码：</label>
        <div class="controls">
            <form:input path="policeIdCode" htmlEscape="false" maxlength="64" class="input-xlarge required"/>
            <span class="help-inline"><font color="red">*</font> </span>
        </div>
    </div>
    <div class="control-group">
        <label class="control-label">治安负责人联系电话：</label>
        <div class="controls">
            <form:input path="policePhone" htmlEscape="false" maxlength="64" class="input-xlarge required"/>
            <span class="help-inline"><font color="red">*</font> </span>
        </div>
    </div>
    <div class="control-group">
        <label class="control-label">经营方式：</label>
        <div class="controls">
            <form:input path="busModel" htmlEscape="false" maxlength="64" class="input-xlarge "/>
        </div>
    </div>
    <div class="control-group">
        <label class="control-label">经济性质：</label>
        <div class="controls">
            <form:input path="busType" htmlEscape="false" maxlength="10" class="input-xlarge "/>
        </div>
    </div>
    <div class="control-group">
        <label class="control-label">单位总人数：</label>
        <div class="controls">
            <form:input path="headCount" htmlEscape="false" maxlength="11" class="input-xlarge "/>
        </div>
    </div>
    <div class="control-group">
        <label class="control-label">单位特业从业人员：</label>
        <div class="controls">
            <form:input path="specialCount" htmlEscape="false" maxlength="11" class="input-xlarge "/>
        </div>
    </div>
    <div class="control-group">
        <label class="control-label">单位地址：</label>
        <div class="controls">
            <form:input path="compAddress" htmlEscape="false" maxlength="300" class="input-xlarge required"/>
            <span class="help-inline"><font color="red">*</font> </span>
        </div>
    </div>
    <div class="control-group">
        <label class="control-label">单位电话：</label>
        <div class="controls">
            <form:input path="compPhone" htmlEscape="false" maxlength="64" class="input-xlarge required"/>
            <span class="help-inline"><font color="red">*</font> </span>
        </div>
    </div>
    <div class="control-group">
        <label class="control-label">邮编：</label>
        <div class="controls">
            <form:input path="postcode" htmlEscape="false" maxlength="20" class="input-xlarge "/>
        </div>
    </div>
    <div class="control-group">
        <label class="control-label">企业营业执照号：</label>
        <div class="controls">
            <form:input path="busLicnum" htmlEscape="false" maxlength="64" class="input-xlarge required"/>
            <span class="help-inline"><font color="red">*</font> </span>
        </div>
    </div>
    <div class="control-group">
        <label class="control-label">企业税务登记证号：</label>
        <div class="controls">
            <form:input path="busTagnum" htmlEscape="false" maxlength="64" class="input-xlarge required"/>
            <span class="help-inline"><font color="red">*</font> </span>
        </div>
    </div>
    <div class="control-group">
        <label class="control-label">企业状态：</label>
        <div class="controls">
            <form:input path="compState" htmlEscape="false" maxlength="10" class="input-xlarge required"/>
            <span class="help-inline"><font color="red">*</font> </span>
        </div>
    </div>
    <div class="form-actions">
        <input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>
        <input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
    </div>
</form:form>
</body>
</html>
