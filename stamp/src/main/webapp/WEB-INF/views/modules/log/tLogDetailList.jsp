<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>操作日志明细管理</title>
	<meta name="decorator" content="default"/>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
	<meta http-equiv="X-UA-Compatible" content="IE=edge">
	<meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no"/>
	<meta name="renderer" content="webkit">
	<link rel="stylesheet" href="${ctxHtml}/css/bootstrap.min.css">
	<%--<link rel="stylesheet" href="${ctxHtml}/css/font-awesome.min.css">--%>
	<%--<link rel="stylesheet" href="${ctxHtml}/css/myCss.css">--%>
	<%--<link rel="stylesheet" href="${ctxHtml}/css/licence.css">--%>
	<script src="${ctxHtml}/js/jquery-1.9.1.min.js"></script>
	<script src="${ctxHtml}/js/bootstrap.min.js"></script>
	<script type="text/javascript" src="${ctxHtml}/js/myScript.js?id=${fns:getConfig('js.version')}"></script>
	<script type="text/javascript" src="${ctxHtml}/js/jquery-form.js"></script>
	<script src="${ctxHtml}/js/stampengravechecked.js?id=${fns:getConfig('js.version')}"></script>

	<SCRIPT TYPE="text/javascript">
        function getURLParameter(name) {
            var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)", "i");
            var r = window.location.search.substr(1).match(reg);
            if (r != null) return unescape(r[2]); return null;
        }

	</SCRIPT>
	<style type="text/css">

		.modal-content{
			background: rgba(0,0,0,0);
			border: none;
			box-shadow: none;
			text-align: center;
		}
		table input {
			width: 100%;
			border: none;
			background: transparent;
		}
		input{
			height: 30px!important;
			margin-bottom: 0!important;
		}
		label{
			color: #000810;
		}
		.modal{
			margin-left: auto;
			margin-right: auto;
			position: fixed;
			/*background-color:gray;*/
			top:10px;
			z-index: 1050;
			display: none;
			overflow: hidden;
			-webkit-overflow-scrolling: touch;
			outline: 0;
		}

		.modal-dialog{
			position: relative;
			width: auto;
		}
	 .table th {
			white-space: nowrap;
			background-color: #E5E5E5;
			background-image: -moz-linear-gradient(top, #EAEAEA, #E5E5E5);
			background-image: -ms-linear-gradient(top,#EAEAEA,#E5E5E5);
			background-image: -webkit-gradient(linear, 0 0, 0 100%, from(#EAEAEA), to(#E5E5E5));
			background-image: -webkit-linear-gradient(top,#EAEAEA,#E5E5E5);
			background-image: -o-linear-gradient(top,#EAEAEA,#E5E5E5);
			background-image: linear-gradient(top,#EAEAEA,#E5E5E5);
			background-repeat: repeat-x;
		}

		.search-btn-div{
			width:100%;
			text-align: center;
		}

	</style>

	<script type="text/javascript">
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
		<li class="active"><a href="${ctx}/log/tLogDetail/">操作日志明细列表</a></li>
		<%--<shiro:hasPermission name="log:tLogDetail:edit"><li><a href="${ctx}/log/tLogDetail/form">操作日志明细添加</a></li></shiro:hasPermission>--%>
	</ul>
	<form:form id="searchForm" modelAttribute="tLogDetail" action="${ctx}/log/tLogDetail/" method="post" cssStyle="width: 97%;margin: 10px auto;" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<input type="hidden" name="sysUserType" id="sysUserType" value="${sysUserType}">



		<table class="table table-bordered">
			<tr>
				<td class="col-md-1 column"><label>字段名：</label></td>
				<td class="col-md-2 column"><form:input path="columnText" htmlEscape="false" class="input-medium"/></td>

				<td class="col-md-1 column"><label>操作类型：</label></td>
				<td class="col-md-2 column">
					<form:input path="operationType" htmlEscape="false" class="input-medium"/>
				</td>

				<td class="col-md-1 column"><label>企业名称：</label></td>
				<td class="col-md-2 column">
					<form:input path="companyName" htmlEscape="false" class="input-medium"/>
				</td>
			</tr>
			<tr>
				<td class="col-md-1 column"><label>刻章点：</label></td>
				<td class="col-md-2 column">
					<form:input path="makeCom" htmlEscape="false" class="input-medium"/>
				</td>

				<td class="col-md-1 column"><label>创建者：</label></td>
				<td class="col-md-2 column">
					<form:input path="createBy.id" htmlEscape="false" class="input-medium"/>
				</td>

				<td class="col-md-1 column"><label>创建时间：</label></td>
				<td class="col-md-2 column">
					<input name="beginCreateDate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
						   value="<fmt:formatDate value="${tLogDetail.beginCreateDate}" pattern="yyyy-MM-dd HH:mm:ss"/>"
						   onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});"/>
					<label>至&nbsp;</label>
					<input name="endCreateDate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
						   value="<fmt:formatDate value="${tLogDetail.endCreateDate}" pattern="yyyy-MM-dd HH:mm:ss"/>"
						   onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});"/>
				</td>
			</tr>

		</table>


		<div class="search-btn-div">
			<%--<button type="submit" class="btn btn-info">搜索</button>--%>
			<input id="btnSubmit" class="btn btn-info" type="submit"  value="查询"/>
			<%--<button class="btn btn-info" onclick="exportExcel()"  type="button">导出到Excel</button>--%>
		</div>




	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered" style="width: 97%;margin: 2px auto">
		<thead>
			<tr>
				<th>表名</th>
				<%--<th>字段</th>--%>
				<th>操作对象(业务名称)</th>
				<th>字段名</th>
				<th>操作类型</th>
				<th>企业名称</th>
				<th>修改前</th>
				<th>修改后</th>
				<th>刻章点</th>
				<th>创建者</th>
				<th>创建时间</th>
				<%--<shiro:hasPermission name="log:tLogDetail:edit"><th>操作</th></shiro:hasPermission>--%>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="tLogDetail">
			<tr>
				<td>
					${tLogDetail.tableName}
				</td>
				<%--<td>--%>
					<%--${tLogDetail.columnName}--%>
				<%--</td>--%>

				<td>
					${tLogDetail.businessName}
				</td>
				<td>
					${tLogDetail.columnText}
				</td>
				<td>
					${tLogDetail.operationType}
				</td>
				<td>
					${tLogDetail.companyName}
				</td>
				<td>
					<c:choose>
						<c:when test="${tLogDetail.operationType == '删除'||tLogDetail.operationType == '添加' || tLogDetail.operationType == '修改附件'}">
							<c:if test="${tLogDetail.oldValue !=null}">
								<a href=""  name="/img${tLogDetail.oldValue}" data-toggle="modal"
								   data-target=".bs-example-modal-lg" onclick="showimg(this)" value="查看">查看</a>
							</c:if>
						</c:when>
						<c:otherwise>
							${tLogDetail.oldValue}
						</c:otherwise>
					</c:choose>
				</td>
				<td>
					<c:choose>
						<c:when test="${tLogDetail.operationType == '删除'||tLogDetail.operationType == '添加' || tLogDetail.operationType == '修改附件'}">
							<c:forEach items="${tLogDetail.newValue.split(',')}" var="newValue">
								<a href=""  name="/img${newValue}" data-toggle="modal" data-target=".bs-example-modal-lg" onclick="showimg(this);"  value="查看">查看</a>
							</c:forEach>
						</c:when>
						<c:otherwise>
							${tLogDetail.newValue}
						</c:otherwise>
				    </c:choose>
				</td>
				<td>
					${tLogDetail.makeCom}
				</td>
				<td>
					${tLogDetail.createBy.name}
				</td>
				<td>
					<fmt:formatDate value="${tLogDetail.createDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<%--<shiro:hasPermission name="log:tLogDetail:edit"><td>--%>
    				<%--<a href="${ctx}/log/tLogDetail/form?id=${tLogDetail.id}">修改</a>--%>
					<%--<a href="${ctx}/log/tLogDetail/delete?id=${tLogDetail.id}" onclick="return confirmx('确认要删除该操作日志明细吗？', this.href)">删除</a>--%>
				<%--</td></shiro:hasPermission>--%>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination" style="display: block;margin-left: 27%">
		${page}
	</div>


<!--模态框-->
<div class="modal fade bs-example-modal-lg" tabindex="-1" role="dialog" aria-labelledby="myLargeModalLabel" data-backdrop="static">
	<div class="modal-dialog modal-lg" role="document">
		<div class="modal-content">
			<div class="modal-header" style="border: 0;padding-right: 10%">
				<button type="button" class="close" data-dismiss="modal" aria-label="Close">
					<span aria-hidden="true"><img src="${ctxHtml}/images/close.png"></span></button>
			</div>
			<%--<div style="height:600px;overflow:auto; ">--%>
				<img style="width: 100%" id="modalimg">
			<%--</div>--%>

		</div>
	</div>
</div>



<script type="text/javascript">
    function showimg(a){
        console.log("a.name:"+a.name);
        if(IEVersion()<10){
            $(".modalBox").css("display", "block");
            document.getElementById("modalimg").style.filter = "progid:DXImageTransform.Microsoft.AlphaImageLoader(sizingMethod='scale',src=\"" + a.name + "\")";
            document.getElementById("modalimg").style.width = "90%";
        }
        else{
            $(".modalBox").css("display", "block");
            document.getElementById("modalimg").src = a.name;
        }
    }

    //判断是否是IE浏览器，包括Edge浏览器
    function IEVersion() {

        var userAgent = navigator.userAgent; //取得浏览器的userAgent字符串
        var isOpera = userAgent.indexOf("Opera") > -1; //判断是否Opera浏览器
        var isIE = userAgent.indexOf("compatible") > -1 && userAgent.indexOf("MSIE") > -1 && !isOpera; //判断是否IE浏览器
        var isEdge = userAgent.indexOf("Windows NT 6.1; Trident/7.0;") > -1 && !isIE; //判断是否IE的Edge浏览器
        if (isIE) {
            var reIE = new RegExp("MSIE (\\d+\\.\\d+);");
            reIE.test(userAgent);
            var fIEVersion = parseFloat(RegExp["$1"]);
            return fIEVersion;
        }
//        else if (isEdge) {
//            return "Edge";
//        }
        else {
            return 100;//非IE
        }
    }


</script>
</body>
</html>