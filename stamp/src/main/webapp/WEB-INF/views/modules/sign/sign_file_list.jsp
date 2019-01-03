<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>文件管理</title>
	<meta name="decorator" content="default"/>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
	<script type="text/javascript" src="${ctxStatic}/sign/js/jquery-form.js"></script>

	<link href="https://cdnjs.cloudflare.com/ajax/libs/jqueryui/1.12.1/jquery-ui.min.css" rel="stylesheet">
	<script src="https://cdnjs.cloudflare.com/ajax/libs/jqueryui/1.12.1/jquery-ui.min.js"></script>

	<script type="text/javascript">

        //窗口类型(印章)
        var iframeType = 1;

        //签章人id
        var userId = "${user.id}"

        $(document).ready(function() {
            $("#uploadModal").draggable();//让模态框可拖拽
        });
        function page(n,s){
            $("#pageNo").val(n);
            $("#pageSize").val(s);
            $("#searchForm").submit();
            return false;
        }
        function closeModal() {
            $('#signModal').modal('hide');
		}
        function signPDF(s) {
            path = s;

            if(path!=""&&path!=null){
                $('#signModal').modal({});
                $('#signPdfIframe').attr("src", path);
            }else {
                alert("文件出错！");
            }
        }
        function checkPDF(s) {
            var rand = Math.random();
            path = s;

            if(path!=""&&path!=null){
                $('#checkModal').modal({});
                url1= "/img/sign" + path+"?"+rand;
                url2= encodeURI(url1);
                $('#checkPdfIframe').attr("src",'${ctxStatic}/pdfJs/web/viewer.html?file='+url2);
            }else {
                alert("文件出错！");
            }
        }
        function filepath(fp) {
            var filename = $(fp).val().split("\\");
            filename = filename[filename.length - 1];
            $('#filepath').text(filename);
        }
        $(function () {
			/*上传文件*/
            $("#uploadForm").ajaxForm({
                dataType: 'json',
                type:"post",
                url: "${ctx}/document/info/upload",
                success: function (result) {
                    if(result.code == 200){
                        alert("上传成功");
                        window.location = "${ctx}/document/info/index";
                    }else{
                        alert(result.message);
                    }
                },
                error:function () {
                    alert("error")
                }
            });
        });

	</script>
	<style type="text/css">
		.modal {
			width:900px;
			margin-left:-450px;
		}
		@media (min-width: 992px) {
			.modal-lg {
				width: 900px;
			}
		}
		.table tr th {
			text-align: center;
		}
		.table tr td {
			text-align: center;
		}
		.uploadBtn {
			margin-left: 200px;
		}
		.fileinput-button {
			position: relative;
			display: inline-block;
			overflow: hidden;
		}
		.fileinput-button input[type="file"]{
			position: absolute;
			right: 0px;
			top: 0px;
			opacity: 0;
			-ms-filter: 'alpha(opacity=0)';
		}
		#filepath {
			margin-left: 5px;
			margin-top: 5px;
			font-size: 20px;
		}
	</style>
</head>
<body>
<ul class="nav nav-tabs">
	<li class="active"><a href="${ctx}/license/businessLicense/">用户文件列表</a></li>
</ul>
<form action="${ctx}/document/info/index" id="searchForm" class="breadcrumb form-search">
	<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
	<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
	<ul class="ul-form">
		<li><label>文件名称：</label>
			<input name="fileName" type="text" class="input-medium" value="${document.fileName}" placeholder="输入文件名">
		</li>
		<li class="btns"><input id="btnSubmit" class="btn btn-primary input-medium" type="submit" value="搜索"/></li>
		<li class="uploadBtn"><button class="btn btn-warning btn-lg" data-toggle="modal" data-target="#uploadModal">上传文件</button></li>
		<li class="clearfix"></li>
	</ul>
</form>
<sys:message content="${message}"/>
<table id="contentTable" class="table table-striped table-bordered table-condensed">
	<thead>
	<tr>
		<th>文件类型</th>
		<th>文件名称</th>
		<th>上传日期</th>
		<th>(最后)签署日期</th>
		<th>使用者</th>
		<th>操作</th>
	</tr>
	</thead>
	<tbody>
	<c:forEach items="${page.list}" var="item">
		<tr>
			<td>
				<img src="${ctxStatic}/images/pdf.png">
			</td>
			<td>
				${item.fileName}
			</td>
			<td>
				<fmt:formatDate value="${item.createDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
			</td>
			<td>
				<c:if test="${item.singalDate ne '' && item.singalDate ne null}">
				<fmt:formatDate value="${item.singalDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</c:if>
				<c:if test="${item.singalDate eq '' || item.singalDate eq null}">
				----
				</c:if>
			</td>
			<td>
				${user.name}
			</td>
			<%--<shiro:hasPermission name="license:businessLicense:edit">--%>
			<td>
				<a data-toggle="modal" onclick="signPDF('${ctx}/document/info/singture/show?id=${item.id}')" class="btn btn-primary">签章</a>
				&nbsp;&nbsp;
				<a data-toggle="modal" onclick="checkPDF(${item.fileSinaturePath eq null}?'${item.fileOrgPath}':'${item.fileSinaturePath}')" class="btn btn-primary">查看(验章)</a>
				&nbsp;&nbsp;
				<c:if test="${item.fileSinaturePath eq null}">
					<a href="${ctx}/download/document/org?id=${item.id}" class="btn btn-success">下载</a>
				</c:if>
				<c:if test="${item.fileSinaturePath ne null}">
				<a href="${ctx}/download/document/sinature?id=${item.id}" class="btn btn-success">下载</a>
				</c:if>
				&nbsp;&nbsp;
				<a href="${ctx}/document/info/delete?id=${item.id}" onclick="return confirmx('确认要删除该文件吗？', this.href)" class="btn btn-danger">删除</a>
			</td>
			<%--</shiro:hasPermission>--%>
		</tr>
	</c:forEach>
	</tbody>
</table>
<div style="text-align: center;margin:20px auto;">
	<div class="pagination" style="display: block;">${page}</div>
</div>

<!-- 模态框（文件上传） -->
<form id="uploadForm">
<div class="modal fade" id="uploadModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
	<div class="modal-dialog">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal" aria-hidden="true">
					&times;
				</button>
				<h4 class="modal-title" id="uploadModalLabel">
					请选择文件上传<h4 style="color: red;">（*请上传格式为PDF的文件）</h4>
				</h4>
			</div>
			<div class="modal-body">
				<span class="fileinput-button btn btn-success" type="button">
				<span>选择文件</span>
				<input name="file" type="file" onchange="filepath(this)">
				</span>
				<span id="filepath"></span>
			</div>
			<div class="modal-footer">
				<button type="submit" class="btn btn-primary">
					确认上传
				</button>
				<button type="button" class="btn btn-default" data-dismiss="modal">
					取消
				</button>
			</div>
		</div><!-- /.modal-content -->
	</div><!-- /.modal -->
</div>
</form>

<!-- 模态框（签章）-->
<div id="signModal" class="modal hide fade" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true"
	 style="width: 100%;height: 100%;left: 450px; top:0px;">
	<div class="modal-header" style="background-color: rgb(0,0,0); filter: alpha(opacity=10);">
		<button type="button" class="close" data-dismiss="modal" aria-hidden="true" style="color:white;opacity: 1.0;">x</button>
		<h3 id="signModalLabel" style="color: white;margin:0px 0px 0px -10px;">文件签署</h3>
	</div>
	<div class="modal-body" style="width: 100%;height:100%;max-height: 100%; padding: 0px;">
		<iframe id="signPdfIframe" width="100%" height="100%"></iframe>
	</div>
</div>

<!-- 模态框（验章）-->
<div id="checkModal" class="modal hide fade" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true"
	 style="width: 100%;height: 100%;left: 450px; top:0px;">
	<div class="modal-header" style="background-color: rgb(0,0,0); filter: alpha(opacity=10);">
		<button type="button" class="close" data-dismiss="modal" aria-hidden="true" style="color:white;opacity: 1.0;">x</button>
		<h3 id="checkModalLabel" style="color: white;margin:0px 0px 0px -10px;">文件验证</h3>
	</div>
	<div class="modal-body" style="width: 100%;max-height: 800px; padding: 0px;">
		<iframe id="checkPdfIframe" width="100%" height="780px"></iframe>
	</div>
</div>



</body>

</html>