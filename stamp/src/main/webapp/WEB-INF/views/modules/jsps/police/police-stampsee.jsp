<%--
  Created by IntelliJ IDEA.
  User: 77426
  Date: 2017/5/20
  Time: 15:57
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp" %>
<!DOCTYPE html>
<html lang="zh">
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no"/>
    <meta name="renderer" content="webkit">
    <title>公安部门-印章查看</title>
    <link rel="stylesheet" href="${ctxHtml}/css/bootstrap.min.css">
    <link rel="stylesheet" href="${ctxHtml}/css/paging.css?id=${fns:getConfig('css.version')}">
    <script language="JavaScript" src="${ctxHtml}/js/jquery-1.9.1.min.js"></script>
    <script src="${ctxHtml}/js/bootstrap.min.js"></script>
    <style>
        table input{
            border: none;
            background: transparent;
        }
        label{
            color: red;
        }
    </style>
    <script type="text/javascript">
        $(document).ready(function () {

        });
        $(function () {

            $(document).on("click",".showcod",function () {
//            alert($(this).parents("td").prev().prev().prev().prev().prev().text())
                var a = $(this).parents("tr").children("td:first").text();
                var id = $(this).parents("tr").children("td:first").next().next().text();
                var danwei = $(this).parents("tr").children("td:eq(6)").next().text();
//            alert(a);
                $("#stampId").val(id);
                $("#ss").val(a);
                $("#danwei").val(danwei);
                //保存改变类型
                var type_id = this.id;
                $("#type_id").val(type_id);
                //alert(type_id);

                var type = $(this).text();
                $("#type").val(type);
                $("#policename").val("");
                $("#policename").next().remove();
                $("#policeid").val("");
                $("#policeid").next().remove();
                $("#hidden").val($(this).parents("tr").children(".hide:eq(0)").text());
                $("#ss").val($(this).parents("tr").children("td:eq(4)").text())
                $("#hidess").val($(this).parents("tr").children(".hide:eq(1)").text())
            });
            $("#policename").blur(function(){
                var reg = /^([\u4e00-\u9fa5]{1,20}|[a-zA-Z\.\s]{1,20})$/;
                if($(this).val() == ""){
                    if($(this).next().length){
                    }else{
                        $(this).after("<label>请输入经办人姓名</label>");
                    }
                }else if($(this).val().split(" ").length != 1) {
                    $(this).next().remove();
                    $(this).after("<label>输入格式不能有空格</label>");
                }else if(!reg.test($(this).val())){
                    $(this).next().remove();
                    $(this).after("<label>输入姓名格式错误！</label>");
                }else{
                    $(this).next().remove();
                }
            });
            $("#policeid").blur(function(){
                var regular =  /(^\d{18}$)|(^\d{17}(\d|X|x)$)/;
                if($(this).val().length != 18){
                    if($(this).next().length){
                    }else{
                        $(this).after("<label>请输入18位二代身份证号码</label>");
                    }
                }else if($(this).val().split(" ").length != 1) {
                    $(this).next().remove();
                    $(this).after("<label>输入格式不能有空格</label>");
                }else if(!regular.test($(this).val())){
                    $(this).next().remove();
                    $(this).after("<label>输入证件格式错误</label>");
                }else{
                    $(this).next().remove();
                }
            });
//表单验证
            $("#submitbtn").click(function () {
                var message = "";
                var value = true;
                var reg = /^([\u4e00-\u9fa5]{1,20}|[a-zA-Z\.\s]{1,20})$/;
                var regular =  /(^\d{18}$)|(^\d{17}(\d|X|x)$)/;
                if($("#policename").val() != null && $("#policeid").val() != null){
                    if($("#policename").val() == ""){
                        value = false;
                        message += "请输入经办人姓名\n";
                    }else if($("#policename").val().split(" ").length != 1) {
                        value = false;
                        message += "输入格式不能有空格\n";
                    }else if(!reg.test($("#policename").val())){
                        value = false;
                        message += "输入姓名格式错误\n";
                    }
                    if($("#policeid").val().length != 18){
                        value = false;
                        message += "请输入18位二代身份证号码\n";
                    }else if($("#policeid").val().split(" ").length != 1) {
                        value = false;
                        message += "输入格式不能有空格\n";
                    }else if(!regular.test($("#policeid").val())){
                        value = false;
                        message += "输入证件格式错误\n";
                    }
                }else {
                    value = false;
                }

                if(value){
                    $(this).attr("disabled","disabled");
                    $("#permissionForm").submit();
                }else{
                    alert(message);
                }
            })


        })

        function page(n, s){
            $("#pageNo").val(n);
            $("#pageSize").val(s);
            $("#inputForm").submit();

            return false;
        }
    </script>
</head>
<body style="width: 90%;margin: 10px auto">
<h3 >公安部门-印章查看</h3>
<div class="form-group" >

    <form:form id="inputForm" modelAttribute="stamp" action="${ctx}/policeStampPage/stampView" method="post" class="form-horizontal">
        <input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
        <input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
        <table style="min-width: 780px">
            <tr>
                    <%--<td>--%>
                    <%--<label for="">印章编码：</label>--%>
                    <%--<form:input path="id" cssClass="form-control" cssStyle="width: 150px;display: inline" htmlEscape="false" maxlength="50" />--%>
                    <%--</td>--%>
                <td>&nbsp;</td>
                <td>
                    <label for="">用章单位名称：</label>
                    <form:input path="lastRecord.companyName" cssClass="form-control" cssStyle="width: 150px;display: inline" htmlEscape="false" maxlength="50" />
                </td>
                <td>&nbsp;</td>
                <td>
                    <label>印章形式：</label>
                    <form:select multiple="single" path="stampShape" cssClass="form-control" cssStyle="width: 150px;display: inline">
                        <form:option value="1" label="物理印章"/>
                        <form:option value="2" label="电子印章"/>
                    </form:select>
                </td>
                <td>&nbsp;</td>
                <td><label>印章类型：</label>
                    <form:select multiple="single" path="stampType" cssClass="form-control" cssStyle="width: 150px;display: inline">
                        <form:option value=""></form:option>
                        <form:options items="${fns:getDictList('stampType')}" itemLabel="label" itemValue="value"
                                      htmlEscape="false"></form:options>
                    </form:select></td>
                <td>&nbsp;</td>
                <td><label>印章使用状态：</label>
                    <form:select multiple="single" path="useState" cssClass="form-control" cssStyle="width: 150px;display: inline">
                        <form:option value=""></form:option>
                        <form:options items="${useStates}" itemLabel="value"
                                      htmlEscape="false"></form:options>
                    </form:select></td>
                <td>&nbsp;</td>
                <td>
                    <input id="btnSubmit" class="btn  btn-default btn-sm" type="submit" value="搜索" style="margin-bottom: 2"/>
                </td>
                </td>
            </tr>
        </table>

    </form:form>
</div>
<table class="table table-striped table-bordered">

    <tr>
        <th>印章编码</th>
        <th>用章单位名称</th>
        <%--<th>印章名称</th>--%>
        <th>印章类型</th>
        <th>印章形式</th>
        <th>使用状态</th>
        <th style="width: 10%">交付时间</th>
        <th>详情</th>

    </tr>
    <c:forEach items="${page.list}" var="stamp">
        <tr>
            <td class="hide">${stamp.id}</td>
            <td class="hide">${stamp.stampShape}</td>
            <td>${stamp.stampCode}</td>
            <td>${stamp.lastRecord.companyName}</td>
            <%--<td>${stamp.stampName}</td>--%>
            <td>${fns:getDictLabel(stamp.stampType,"stampType",null)}</td>
            <td>${fns:getDictLabel(stamp.stampShape,'stampShape' ,null )}</td>
            <td>${stamp.useState.value}</td>
            <td>    <fmt:formatDate value="${stamp.deliveryDate}"
                                    pattern="yyyy-MM-dd " /></td>
            <td>
                <c:choose>
                <c:when test="${stamp.useState.key == '1' || stamp.useState.key == '2'}">
                    <a class="btn btn-default" href="${ctx}/policeStampPage/stampDetail?id=${stamp.id}&stampShape=${stamp.stampShape}&useState1=${fns:urlEncode(stamp.useState.key)}">查看</a>
                    <a id="change" class="btn btn-default showcod" data-toggle="modal" data-target=".bs-example-modal-lg" >变更</a>
                    <a id="reset" class="btn btn-default showcod" data-toggle="modal" data-target=".bs-example-modal-lg" >补刻</a>
                    <a id="cancal" class="btn btn-default showcod" data-toggle="modal" data-target=".bs-example-modal-lg" >缴销</a>
                    <a id="miss" class="btn btn-default showcod" data-toggle="modal" data-target=".bs-example-modal-lg" >挂失</a>
                </c:when>
                <c:otherwise>
                    <a class="btn btn-default" href="${ctx}/policeStampPage/stampDetail?id=${stamp.id}&stampShape=${stamp.stampShape}&useState1=${fns:urlEncode(stamp.useState.key)}">查看</a>
                </c:otherwise>
                </c:choose>

            </td>
        </tr>
    </c:forEach>
</table>


<%--模态框--%>
<div class="modal fade bs-example-modal-lg" tabindex="-1" role="dialog" aria-labelledby="myLargeModalLabel">
    <div class="modal-dialog modal-lg" role="document">
        <div class="modal-content">
            <div class="modal-header" style="border: 0;padding-right: 10%">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                    <span aria-hidden="true"><img src="${ctxHtml}/images/close.png"></span></button>
            </div>
            <form id="permissionForm" action="${ctx}/policeStampPage/permission" method="post">
                <table class="table table-bordered table-hover" style="width: 90%;margin: 10px auto">
                    <tbody>
                    <tr>
                        <input id="hidden" name="id" type="hidden" value="">
                        <input id="type_id" name="type_id" type="hidden" value="">
                        <td>业务类型</td>
                        <td >
                            <input type="text" id="type" name="type" class="form-control" readonly>
                        </td></tr>
                    <tr>
                        <td>印章名称</td>
                        <td><input type="text"  class="form-control" id="stampId" readonly></td>
                    </tr>
                    <tr >
                        <td>印章形式</td>
                        <td ><input type="hidden" name="stampShape" id="hidess" value=""><input type="text"  class="form-control" id="ss" value="" readonly></td>
                    </tr>
                    <tr>
                        <td>所属单位</td>
                        <td ><input type="text" class="form-control" id="danwei" readonly></td>
                    </tr>
                    <tr>
                        <td>经办人姓名：</td>
                        <td><input type="text" name="agentName" class="form-control" id="policename" placeholder="请输入经办人姓名"></td>
                    </tr>
                    <tr>
                        <td>经办人身份证：</td>
                        <td><input type="text" class="form-control" name="agentId" id="policeid" placeholder="请输入经办人身份证"></td>
                    </tr>
                    <tr>
                        <td colspan="4" style="text-align: center">
                            <button type="button" id="submitbtn" class="btn btn-primary">确定</button>
                        </td>
                    </tr>
                    </tbody>
                </table>
            </form>
        </div>
    </div>
</div>
<div>
<div class="pagination" style="display: block;text-align: center">
    ${page}
</div>
</div>
</body>
</html>
