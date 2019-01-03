<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>区域对应办事附件管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript" src="${ctxHtml}/js/jquery-1.9.1.min.js"></script>
	<script type="text/javascript" src="${ctxHtml}/js/jquery-form.js"></script>
	<script type="text/javascript">
		$(document).ready(function() {

			//$("#name").focus();
//			$("#inputForm").validate({
//				submitHandler: function(form){
//					loading('正在提交，请稍等...');
//					form.submit();
//				},
//				errorContainer: "#messageBox",
//				errorPlacement: function(error, element) {
//					$("#messageBox").text("输入有误，请先更正。");
//					if (element.is(":checkbox")||element.is(":radio")||element.parent().is(".input-append")){
//						error.appendTo(element.parent().parent());
//					} else {
//						error.insertAfter(element);
//					}
//				}
//			});
			$("#btnSubmit").click(function () {
				$("#btnSubmit").attr("disabled","disabled");
                $("#inputForm").submit();
            });
            $("#inputForm").ajaxForm({
                type: "post",
                dataType: "json",
				url:"${ctx}/areaattachment/areaAttachment/save",
				success:function (result) {
					alert(result.message);
//					console.log(result);
					if(result.code == 200){
                        history.go(-1);
                    }
                    $("#btnSubmit").removeAttr("disabled");
                },
				error:function () {
					alert("出错了！请联系管理员！");
                    $("#btnSubmit").removeAttr("disabled");
                }

			})
        })

        var aaa = $("#r1").prop("checked");
        if(aaa){
            alert("选中");
        };

	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li><a href="${ctx}/areaattachment/areaAttachment/">区域对应办事附件列表</a></li>
		<li class="active"><a href="${ctx}/areaattachment/areaAttachment/form?id=${areaAttachmentDTO.areaAttachment.id}">区域对应办事附件</a></li>
	</ul><br/>
	<form:form id="inputForm" modelAttribute="areaAttachmentDTO" action="${ctx}/areaattachment/areaAttachment/save" method="post" class="form-horizontal">
		<form:hidden path="areaAttachment.id"/>
		<div class="control-group">
			<label class="control-label">区域：</label>
			<div class="controls">
				<sys:treeselect id="area" name="areaAttachment.area.id" value="${areaAttachmentDTO.areaAttachment.area.id}" labelName="areaAttachment.area.name" labelValue="${areaAttachmentDTO.areaAttachment.area.name}"
					title="区域" url="/sys/area/treeData" cssClass="required" allowClear="true" notAllowSelectParent="true"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">业务办理类型：</label>
			<div class="controls">
				<form:select path="areaAttachment.type" class="input-xlarge required">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('service_type')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">附件类型列表：</label>
			<table class="table table-hover table-striped table-bordered" style="padding-left: 10.5%">
			<div class="controls">
				<ul style="list-style: none">
				<c:forEach items="${fns:getDictList('file_type')}" var="dict">
					<tr>
						<td width="400px;">
							<label><input type="checkbox" name="attachList" value="${dict.value}" id="${dict.value}"
							<c:if test="${areaAttachmentDTO.attachList !=null}">
							<c:if test="${areaAttachmentDTO.attachList.contains(dict.value)}">
										  checked
							</c:if>
							</c:if>
							>${dict.label}</label>
						</td>
						<td>
							 <%--<form:radiobuttons path="requiredList" items="${fns:getDictList('required_null')}" itemLabel="label" itemValue="value" htmlEscape="false" class="required"/>--%>
								 <label><input type="checkbox" name="requiredList" value="${dict.value}" id="r${dict.value}"
								 <c:if test="${areaAttachmentDTO.requiredList !=null}">
								 <c:if test="${areaAttachmentDTO.requiredList.contains(dict.value)}">
											   checked
								 </c:if>
								 </c:if>
								 >必填</label>
							<span class="help-inline">&nbsp;&nbsp;默认为<font color="red">非必填</font></span>
						</td>

					</tr>
				</c:forEach>
				</ul>
			</div>
			</table>
		</div>

		<div class="control-group">
			<label class="control-label">备注：</label>
			<div class="controls">
				<form:textarea path="areaAttachment.remarks" htmlEscape="false" rows="4" maxlength="255" class="input-xxlarge "/>
			</div>
		</div>
		<div class="form-actions">
			<input id="btnSubmit" class="btn btn-primary" type="button" value="保 存"/>&nbsp;
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
	</form:form>
</body>
</html>