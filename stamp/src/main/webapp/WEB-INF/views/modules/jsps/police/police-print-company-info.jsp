<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp" %>
<html>
<head>
    <title>已刻印章详情</title>
</head>
<link rel="stylesheet" href="${ctxHtml}/css/bootstrap.min.css">
<link rel="stylesheet" href="${ctxHtml}/css/licence.css">
<script type="text/javascript" src="${ctxHtml}/js/jquery-1.9.1.min.js"></script>
<script type="text/javascript" src="${ctxStatic}/stamp-utils/stamp-utils.js"></script>

<style type="text/css">
    tr {
        line-height: 50px;
        height: 60px;
    }
    th {
        text-align: center;
    }
</style>
<script type="text/javascript">

    var ctx = "${ctx}"; 

    // 根据下拉框选择不同印章查找对应印章信息,并加载到页面,实现页面局部刷新
    function getStampInfoById(){
        var stamp_type_id = $("#frmPrintCompanyInfo").find("#stamp_type_id").val();
        if(stamp_type_id == ""){ // 选中了请选择,清空内容
            $("#frmPrintCompanyInfo").find("#td_stamp_texture").html(null);
            $("#frmPrintCompanyInfo").find("#td_stamp_shape").html(null);
            $("#frmPrintCompanyInfo").find("#td_stamp_model").html(null);
            return;
        }
        $.ajax({
            url: "${ctx}/policeStampPage/findStampInfoById",
            type: "GET",
            data: {stampId:stamp_type_id},
            dataType:"json",
            async:false,
            success:function(stampInfo){
                debugger
                var stamp_texture = getDictLabel(stampInfo.stampTexture,"stampTexture");
                debugger
                $("#frmPrintCompanyInfo").find("#td_stamp_texture").html(stamp_texture);
                if(stampInfo.stampShape == 1){
                    $("#frmPrintCompanyInfo").find("#td_stamp_shape").html("物理印章");
                    $("#frmPrintCompanyInfo").find("#td_stamp_model").html("<img src =/img"+stampInfo.phyModel+ " />");
                }else{
                    $("#frmPrintCompanyInfo").find("#td_stamp_shape").html("UKEY");
                    $("#frmPrintCompanyInfo").find("#td_stamp_model").html("<img src =/img"+ stampInfo.eleModel + " style='width:159px;height:159px;' />");
                }
                $("#print_company_info_btn").css("display","block");
                $("#frmPrintCompanyInfo").find("#stamp_type_id").css("appearance","");
                $("#frmPrintCompanyInfo").find("#stamp_type_id").css("-moz-appearance","");
                $("#frmPrintCompanyInfo").find("#stamp_type_id").css("webkit-appearance","");
            },
            error:function () {
                alert("网络繁忙,请重试!");
            }
        });
    }

    // 打印公司表单信息<备案登记表>
    function print_company_info(){
        debugger
        var stamp_type_id = $("#frmPrintCompanyInfo").find("#stamp_type_id").val();
        var td_stamp_shape = $("#frmPrintCompanyInfo").find("#td_stamp_shape").html();
        if((stamp_type_id == null || stamp_type_id.trim() == "") && (td_stamp_shape == null || td_stamp_shape.trim() == "")){
            alert("请选择印章名称");
            return;
        }
        debugger
        if(stamp_type_id != null && stamp_type_id.trim() != ""){
            $("#frmPrintCompanyInfo").find("#stamp_type_id").css("background","transparent");
            $("#frmPrintCompanyInfo").find("#stamp_type_id").css("overflow","hidden");
            $("#frmPrintCompanyInfo").find("#stamp_type_id").css("resize","none");
            $("#frmPrintCompanyInfo").find("#stamp_type_id").css("border","0px");
            $("#frmPrintCompanyInfo").find("#stamp_type_id").css("appearance","none");
            $("#frmPrintCompanyInfo").find("#stamp_type_id").css("-moz-appearance","none");
            $("#frmPrintCompanyInfo").find("#stamp_type_id").css("webkit-appearance","none");
        }
        $("#print_company_info_btn").css("display","none");
        window.print();
    }
</script>
<body>
<div class="panel-head">
    <div style="text-align: center;" id="ph3"><h3>印章备案登记表</h3></div>
    <span style="float: right;margin-bottom: 15px;margin-top: 15px;">
            登记时间：<span id="cur-time" style="margin-right: 50px"><fmt:formatDate value="${mca.createDate}"
                                                                                pattern="yyyy-MM-dd"/></span>
            业务流水号：<span id="ht1">${serialNum}</span>
        </span>
</div>



<form:form action="" method="post" modelAttribute="stamp" id="frmPrintCompanyInfo">

    <table class="table table-striped table-bordered" style="table-layout:fixed;word-wrap:break-word;">
        <tr>
            <th>企业名称</th>
            <td colspan="3">
                <textarea id="companyname" value="${mca.company.companyName}" readonly="readonly" disabled="disabled"
                          style="background:transparent;overflow:hidden;resize: none;border: 0px;" cols="80"
                          rows="1">${mca.company.companyName}</textarea>
            </td>
        </tr>
        <tr>
            <th>企业统一社会信用代码
            </th>
            <td>
                ${mca.company.soleCode}
            </td>
            <th>企业类型</th>
            <td>
                <input type="text" id="temp" value="${fns:getDictLabel(mca.company.type1,"unitType" ,null )}"
                       style="background:transparent;overflow:hidden;resize: none;border: 0px;"/>
            </td>
        </tr>
        <tr>
            <th>企业住所</th>
            <td colspan="3"><input class="input" id="compAddress" name="compAddress" value="${mca.company.compAddress}"
                                   type="text" readonly="readonly"
                                   style="background:transparent;overflow:hidden;resize: none;border: 0px;width: 700px;"/>
            </td>
        </tr>
        <tr>
            <th>法人姓名</th>
            <td><input class="input" id="legalName" name="legalName" value="${mca.company.legalName}" type="text"
                       readonly="readonly" style="background:transparent;overflow:hidden;resize: none;border: 0px;"
                       cols="80" rows="1"/></td>
            <th>法人联系电话</th>
            <td><input class="input" id="legalphone" name="legalPhone" value="${mca.company.legalPhone}" type="text"
                       readonly="readonly" style="background:transparent;overflow:hidden;resize: none;border: 0px;"/>
            </td>
        </tr>
        <tr>
            <th>法人证件类型</th>
            <td>
                <input type="text" id="tempLegalCertType"
                       value="${fns:getDictLabel(mca.company.legalCertType,"certificateType" ,null )}"
                       style="background:transparent;overflow:hidden;resize: none;border: 0px;"/>
            </td>
            <th>法人证件号码</th>
            <td><input class="input" id="legalid" name="legalCertCode" value="${mca.company.legalCertCode}" type="text"
                       disabled="disabled" style="background:transparent;overflow:hidden;resize: none;border: 0px;"/>
            </td>
        </tr>
        <tr>
            <th>经办人姓名</th>
            <td><input class="input" id="personname" name="agentName" value="${mca.agentName}" type="text"
                       readonly="readonly" style="background:transparent;overflow:hidden;resize: none;border: 0px;"/>
            </td>
            <th>经办人联系电话</th>
            <td><input class="input" id="personphone" name="agentPhone" value="${mca.agentPhone}" type="text"
                       readonly="readonly" style="background:transparent;overflow:hidden;resize: none;border: 0px;"/>
            </td>
        </tr>
        <tr>
            <th>经办人证件类型</th>
            <td>
                <input type="text" id="tempAgentCertType"
                       value="${fns:getDictLabel(mca.agentCertType,"certificateType" ,null )}"
                       style="background:transparent;overflow:hidden;resize: none;border: 0px;"/>
            </td>
            <th>经办人证件号码</th>
            <td><input class="input" id="personid" name="agentCertCode" value="${mca.agentCertCode}" type="text"
                       disabled="disabled" style="background:transparent;overflow:hidden;resize: none;border: 0px;"/>
            </td>
        </tr>
        <tr>
            <th>企业信息是否经过变更</th>
            <td> 是 </td>
            <th>变更时间</th>
            <td><input class="input input-medium Wdate"  value="<fmt:formatDate value="${mca.createDate}" pattern="yyyy-MM-dd HH:mm:ss"/>" type="text"
                       disabled="disabled" style="background:transparent;overflow:hidden;resize: none;border: 0px;" pattern="yyyy-MM-dd HH:mm:ss"/>

            </td>
        </tr>
        <tr>
            <th>变更原因</th>
            <td colspan="3">
                <textarea  disabled="disabled" rows="4" style="width: 95%;background:transparent;overflow:hidden;resize: none;border: 0px;">${mca.modifyReason}</textarea>
            </td>
        </tr>
    </table>
<table class="table table-striped table-bordered subbody" style="text-align: center;vertical-align: middle;">
    <thead>
    <tr>
        <th width="25%">印章名称</th>
        <th width="20%">章型</th>
        <th width="25%">章材</th>
        <th>印模</th>
    </tr>
    </thead>
    <tbody id="tbody" height="190px">
    <tr>
        <td align="center"  style="text-align: center;vertical-align: middle;" id="td_stamp_name">
            <c:if test="${stampList.size() == 2}">
                    ${stampList.get(1).stampName}
            </c:if>
            <c:if test="${stampList.size() > 2}">
                <form:select path="id" items="${stampList}" id="stamp_type_id" itemLabel="stampName" itemValue="id" cssStyle="width: 120px; height: 30px;" onchange="getStampInfoById()" class="input-medium">
                    <form:option value="">请选择</form:option>
                </form:select>
            </c:if>
        </>
        <td align="center"  style="text-align: center;vertical-align: middle;" id="td_stamp_shape">
            <c:if test="${stampList.size() == 2}">
                <c:if test="${stampList.get(1).stampShape ==1}">
                    物理印章
                </c:if>
                <c:if test="${stampList.get(1).stampShape ==2}">
                    UKEY
                </c:if>
            </c:if>
        </td>
        <td align="center" style="text-align: center;vertical-align: middle;" id="td_stamp_texture">
            <c:if test="${stampList.size() == 2}">
                ${fns:getDictLabel(stampList.get(1).stampTexture,"stampTexture",null)}
            </c:if>
        </td>
        <td align="center" id="td_stamp_model">
            <c:if test="${stampList.size() == 2}">
                <%--物理的加载物理路径--%>
                <c:if test="${stampList.get(1).stampShape ==1}">
                    <img src="/img${stampList.get(1).phyModel}" width="159" height="159"></img>
                </c:if>
                <%--电子的加载电子路径--%>
                <c:if test="${stampList.get(1).stampShape ==2}">
                    <img src="/img${stampList.get(1).eleModel}"></img>
                </c:if>
            </c:if>
        </td>
    </tr>
    </tbody>

    <table  class="table table-striped table-bordered subbody" style="text-align: center;vertical-align: middle;">
        <thead>
        <tr>
            <th width="30%">公安盖章</th>
            <th width="30%">企业盖章</th>
            <th width="30%">经办人签名</th>
        </tr>
        </thead>
        <tr  style="height: 170px;">
            <td></td>
            <td></td>
            <td align="center"></td>
        </tr>
    </table>
</table>
</form:form>

    <div class="search-btn-div" style="text-align: center" id="print_company_info_btn">
        <input id="btn_print" class="btn btn-info" type="button"  value="打印" onclick="print_company_info()"/>
    </div>
</body>
</html>
