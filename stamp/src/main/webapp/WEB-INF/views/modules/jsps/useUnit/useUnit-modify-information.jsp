<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp" %>
<!DOCTYPE html>
<html>
<head lang="zh">
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no"/>
    <title>用章单位-信息修改</title>
    <link rel="stylesheet" href="${ctxHtml}/css/bootstrap.min.css"/>
    <link rel="stylesheet" href="${ctxHtml}/css/useUnit_style.css"/>
    <script language="JavaScript" src="${ctxHtml}/js/jquery-1.9.1.min.js"></script>
    <script type="text/javascript" src="${ctxHtml}/js/jquery-form.js"></script>
    <script src="${ctxHtml}/js/My97DatePicker/WdatePicker.js"></script>
    <style>
        label{
            color: red;
        }
    </style>
</head>
<body style="padding-left: 3%;padding-top: 3%">
<div style="width: 90%;margin: 20px auto">
    <h3>单位信息修改</h3>
    <table class="table table-bordered">
        <tr>
            <td>单位唯一编码</td>
            <td colspan="2"><input type="text" class="form-control" value="${company.soleCode}" readonly></td>
            <td>单位名称</td>
            <td colspan="2"><input type="text" class="form-control" value="${company.companyName}" readonly></td>
        </tr>
        <tr>
            <c:set var="unitType1" value="${company.type1}"/>
            <td>单位类别</td>
            <td colspan="2"><input type="text" class="form-control" value="${fns:getDictLabel(unitType1, "unitType", null)}" readonly></td>
            <td>单位统一社会信用代码</td>
            <td colspan="2"><input type="text" class="form-control" value="${company.soleCode}" readonly></td>
        </tr>
        <tr>
            <td>法定代表人</td>
            <td colspan="2"><input type="text" class="form-control" value="${company.legalName}" readonly></td>
            <td>法人手机</td>
            <td colspan="2"><input type="text" class="form-control" value="${company.legalPhone}" readonly></td>
        </tr>
        <tr>
            <td>法人证件类型</td>
            <c:set var="legalCertType" value="${company.legalCertType}"/>
            <td colspan="2"><input type="text" class="form-control" value="${fns:getDictLabel(legalCertType, "certificateType", null)}" readonly></td>
            <td>法人证件号码</td>
            <td colspan="2"><input type="text" class="form-control" value="${company.legalCertCode}" readonly></td>
        </tr>
        <tr>
            <td>经济性质</td>
            <c:set var="busType" value="${company.busType}"/>
            <td colspan="3"><input type="text" class="form-control" value="${fns:getDictLabel(busType, "busType", null)}" readonly></td>
            <td>企业营业执照</td>
            <td><input type="text" class="form-control" value="${company.busLicnum}" readonly></td>
        </tr>
        <tr>
            <td>企业税务登记号</td>
            <td><input type="text" class="form-control" value="${company.busTagnum}" readonly></td>
            <td>企业状态</td>
            <td><input type="text" class="form-control" value="${company.compState.value}" readonly></td>
            <td>成立日期</td>
            <td><input type="text" class="form-control" value="${company.compCreatDate}" readonly></td>
        </tr>

    </table>
    <form:form id="mainForm" modelAttribute="company">
        <table class="table table-striped table-hover table-bordered">
            <tbody>
            <tr>
                <form:hidden path="id" value="${company.id}"/>
                <td>治安负责人</td>
                <td colspan="2">
                    <form:input path="policeName" id="policeName" type="text" class="form-control" value="${company.policeName}"/>
                </td>
                <td>治安负责人身份证号码</td>
                <td colspan="2">
                    <form:input path="policeIdCode" id="policeIdCode" type="text" class="form-control" value="${company.policeIdCode}"/>
                </td>
            </tr>
            <tr>
                <td>治安负责人联系电话</td>
                <td colspan="2">
                    <form:input path="policePhone" id="policePhone" type="text" class="form-control" value="${company.policePhone}"/>
                </td>
                <td>经营方式</td>
                <td colspan="2"><form:input path="busModel" id="busModel" type="text" class="form-control" value="${company.busModel}"/></td>
            </tr>
            <tr>
                <td>单位总人数</td>
                <td>
                    <form:input path="headCount" id="headCount" type="number" class="form-control" value="${company.headCount}"/>
                </td>
                <td>单位特业从业人员</td>
                <td>
                    <form:input path="specialCount" id="specialCount" type="number" class="form-control" value="${company.specialCount}"/>
                </td>
                <td>邮编</td>
                <td>
                    <form:input path="postcode" id="postcodes" type="text" class="form-control" value="${company.postcode}"/>
                </td>
            </tr>

            <tr>
                <td>单位地址</td>
                <td colspan="3"><form:input path="compAddress" id="compAddress" type="text" class="form-control" value="${company.compAddress}"/></td>
                <td>单位电话</td>
                <td><form:input path="compPhone" id="compPhone" type="text" class="form-control" value="${company.compPhone}"/></td>
            </tr>

            <tr>
                <td>登记机关</td>
                <td><form:input path="recordUnit" id="recordUnit" type="text" class="form-control" value="${company.recordUnit}"/></td>
                <td>经营范围</td>
                <td><form:input path="busScope" id="busScope" type="text" class="form-control" value="${company.busScope}"/></td>
                <td>注册资本</td>
                <td><form:input path="regCap" id="regCap" type="text" class="form-control" value="${company.regCap}"/></td>
            </tr>
            <tr>
                <td>营业期限起始时间</td>
                <td><input name="startDate" id="startDate" type="text" class="form-control" value="<fmt:formatDate value="${company.busStartDate}" pattern="yyyy-MM-dd"/>" onfocus="WdatePicker({skin:'whyGreen',maxDate:'%y-%M-%d'})"/></td>

                <td>营业期限截止时间</td>
                <td><input name="endDate" id="endDate" type="text" class="form-control" value="<fmt:formatDate value="${company.busEndDate}" pattern="yyyy-MM-dd"/>" onfocus="WdatePicker({skin:'whyGreen',minDate:'%y-%M-%d'})"/></td>
                <td></td>
                <td></td>
            </tr>
            <tr>
                <td colspan="6" style="text-align: center">
                    <button type="button" id="modify" class="btn btn-primary">修改</button>
                </td>

            </tr>
            </tbody>
        </table>
    </form:form>
</div>
<script>
//    表单验证
    $(function () {
        $("#policeName").blur(function () {
            var reg = /^([\u4e00-\u9fa5]{1,20}|[a-zA-Z\.\s]{1,20})$/;
            if ($(this).val() == "") {
                if ($(this).next().length) {
                } else {
                    $(this).after("<label>请输入治安负责人</label>");
                }
            } else  if($(this).val().split(" ").length != 1) {
                $(this).next().remove();
                $(this).after("<label>输入格式不能含有空格！</label>");
            }else if(!reg.test($(this).val())){
                $(this).next().remove();
                $(this).after("<label>姓名格式错误!</label>");
            }else {
                $(this).next().remove();
            }
        });
        $("#policeIdCode").blur(function () {
            var regular = /^[0-9a-zA-Z]{18}$/;
            if($(this).val().length != 18){
                $(this).next().remove();
                $(this).after("<label>请输入18位有效的治安负责人身份证号码</label>");
            }else if($(this).val().split(" ").length != 1) {
                $(this).next().remove();
                $(this).after("<label>输入格式不能含有空格！</label>");
            }else if(!regular.test($(this).val())){
                $(this).next().remove();
                $(this).after("<label>输入证件格式错误</label>");
            }else{
                $(this).next().remove();
            }
        });
        $("#policePhone").blur(function () {
            var regTel1 = /^(((13[0-9]{1})|(15[0-9]{1})|(18[0-9]{1}))+\d{8})$/;//手机号码
            var regTel2 = /^(([0\+]\d{2,3}-)?(0\d{2,3})-)(\d{7,8})(-(\d{3,}))?$/;//带区号的固定电话
            var regTel3 = /^(\d{7,8})(-(\d{3,}))?$/;//不带区号的固定电话
            if ($(this).val() == "") {
                if ($(this).next().length) {
                }else{
                    $(this).after("<label>请输入负责人联系电话</label>");
                }
            }else if(!regTel1.test($(this).val()) && !regTel2.test($(this).val()) && !regTel3.test($(this).val())){
                $(this).next().remove();
                $(this).after("<label>输入负责人联系电话有误！</label>");
            }else{
                $(this).next().remove();
            }
        });
        $("#busModel").blur(function () {
            if ($(this).val() == "") {
                if ($(this).next().length) {
                } else {
                    $(this).after("<label>请输入经营方式</label>");
                }
            }else if($(this).val().split(" ").length != 1) {
                $(this).next().remove();
                $(this).after("<label>输入格式不能含有空格</label>");
            }else if($(this).val().length > 100){
                $(this).next().remove();
                $(this).after("<label>经营方式过长</label>");
            }else{
                $(this).next().remove();
            }
        });
        $("#headCount").blur(function () {
            var reg = /^[0-9]{1,100}$/;
            if ($(this).val() == "") {
                if ($(this).next().length) {
                } else {
                    $(this).after("<label>请输入单位总人数</label>");
                }
            }else if($(this).val().split(" ").length != 1) {
                $(this).next().remove();
                $(this).after("<label>输入格式不能含有空格！</label>");
            }else if(parseInt($(this).val()) <= 0){
                $(this).next().remove();
                $(this).after("<label>人数应大于0</label>");
            }else if(!reg.test($(this).val())){
                $(this).next().remove();
                $(this).after("<label>只能输入数字</label>");
            }else if(parseInt($(this).val()) > parseInt($("#specialCount").val())){
                $(this).next().remove();
                $("#specialCount").next().remove();
            }else{
                $(this).next().remove();
            }
        });
        $("#specialCount").blur(function () {
            var reg = /^[0-9]{1,100}$/;
            if ($(this).val() == "") {
                if ($(this).next().length) {
                } else {
                    $(this).after("<label>请输入单位特业从业人员</label>");
                }
            }else if($(this).val().split(" ").length != 1) {
                $(this).next().remove();
                $(this).after("<label>输入格式不能含有空格！</label>");
            }else if(parseInt($(this).val()) <= 0){
                $(this).next().remove();
                $(this).after("<label>人数应大于0</label>");
            }else if(!reg.test($(this).val())){
                $(this).next().remove();
                $(this).after("<label>只能输入数字</label>");
            }else if(parseInt($(this).val()) > parseInt($("#headCount").val())){
                $(this).next().remove();
                $(this).after("<label>人数不能大于单位总人数</label>");
            }else{
                $(this).next().remove();
            }
        });
        $("#postcodes").blur(function () {
            var reg = /^[1-9][0-9]{5}$/;
            if (!reg.test($(this).val())) {
                if ($(this).next().length) {
                } else {
                    $(this).after("<label>请输入6位有效的邮编</label>");
                }
            } else {
                $(this).next().remove();
            }
        });
        $("#compAddress").blur(function () {
            if ($(this).val() == "") {
                if ($(this).next().length) {
                } else {
                    $(this).after("<label>请输入单位地址</label>");
                }
            }else if($(this).val().split(" ").length != 1) {
                $(this).next().remove();
                $(this).after("<label>输入格式不能含有空格！</label>");
            }else {
                $(this).next().remove();
            }
        });
        $("#compPhone").blur(function () {
            var regTel1 = /^(((13[0-9]{1})|(15[0-9]{1})|(18[0-9]{1}))+\d{8})$/;//手机号码
            var regTel2 = /^(([0\+]\d{2,3}-)?(0\d{2,3})-)(\d{7,8})(-(\d{3,}))?$/;//带区号的固定电话
            var regTel3 = /^(\d{7,8})(-(\d{3,}))?$/;//不带区号的固定电话
            if ($(this).val() == "") {
                if ($(this).next().length) {
                } else {
                    $(this).after("<label>请输入单位电话</label>");
                }
            }else if(!regTel1.test($(this).val()) && !regTel2.test($(this).val()) && !regTel3.test($(this).val())){
                $(this).next().remove();
                $(this).after("<label>输入单位电话有误！</label>");
            }else{
                $(this).next().remove();
            }
        });

        var message = "";

        $("#modify").click(function () {
            message =  "";
            if(checked()){
                $("#modify").attr("disabled","disabled");
                $("#mainForm").submit();
            }else {
                alert(message);
            }
        });
        //提交表单数据
        $('#mainForm').ajaxForm({
            dataType: "json",
            type: "post",
            url: "${ctx}/useCompanyInfoAction/updateComInfo",
            success: function (data) {
                if (data.code == 200) {
                    alert(data.message);
                    location.href = "${ctx}" + data.url.toString();

                } else {
                    alert(data.message);
                    $("#modify").attr("disabled",false);
                }
            },
            error: function () {
                alert("出错了！请联系管理员！");
                $("#modify").attr("disabled",false);
            }
        });
        function checked() {

            var values = true;
            var reg = /^([\u4e00-\u9fa5]{1,20}|[a-zA-Z\.\s]{1,20})$/;
            var regular = /^[0-9a-zA-Z]{18}$/;
            var regTel1 = /^(((13[0-9]{1})|(15[0-9]{1})|(18[0-9]{1}))+\d{8})$/;//手机号码
            var regTel2 = /^(([0\+]\d{2,3}-)?(0\d{2,3})-)(\d{7,8})(-(\d{3,}))?$/;//带区号的固定电话
            var regTel3 = /^(\d{7,8})(-(\d{3,}))?$/;//不带区号的固定电话
            if ($("#postcode").val() != "" && $("#postcode").val() != "" && $("#postcode").val() != "" && $("#postcode").val() != "" && $("#postcode").val() != "" && $("#postcode").val() != "" && $("#postcode").val() != "") {

                var values = true;
                if ($("#policeName").val() == "") {
                    values = false;
                    message += "输入治安负责人\n";
                }else if($("#policeName").val().split(" ").length != 1){
                    values = false;
                    message += "输入治安负责人不能有空格\n";
                }else if(!reg.test($("#policeName").val())){
                    values = false;
                    message += "输入治安负责人格式错误\n";
                }
                if ($("#policeIdCode").val().length != 18) {
                    values = false;
                    message += "输入治安负责人身份证号码\n"
                }else if($("#policeIdCode").val().split(" ").length != 1){
                    values = false;
                    message += "输入治安负责人身份证号码不能有空格\n";
                }else if(!regular.test($("#policeIdCode").val())){
                    values = false;
                    message += "输入治安负责人身份证号码格式错误\n";
                }
                if ($("#policePhone").val() == "") {
                    values = false;
                    message += "输入治安负责人联系电话\n";
                }else if($("#policePhone").val().split(" ").length != 1){
                    values = false;
                    message += "输入治安负责人联系电话不能有空格\n";
                }else if(!regTel1.test($("#policePhone").val()) && !regTel2.test($("#policePhone").val()) && !regTel3.test($("#policePhone").val())){
                    values = false;
                    message += "输入治安负责人联系电话号码有误\n";
                }
                if ($("#busModel").val() == "") {
                    values = false;
                    message += "输入经营方式\n";
                }else if($("#busModel").val().split(" ").length != 1){
                    values = false;
                    message += "输入经营方式不能有空格\n";
                }else if($("#busModel").val().length > 100){
                    values = false;
                    message += "输入经营方式过长\n";
                }
                if ($("#headCount").val() == "") {
                    values = false;
                    message += "输入单位总人数\n";
                }else if($("#headCount").val().split(" ").length != 1){
                    values = false;
                    message += "输入单位总人数不能有空格\n";
                }else if(parseInt($("#headCount").val()) < 0){
                    values = false;
                    message += "输入单位总人数应大于0\n";
                }
                if ($("#specialCount").val() == "") {
                    values = false;
                    message += "输入特种从业总人数\n";
                }else if(parseInt($("#specialCount").val()) > (parseInt($("#headCount").val()))){
                    values = false;
                    message += "特种从业总人数不能大于单位总人数\n";
                }else if($("#specialCount").val().split(" ").length != 1){
                    values = false;
                    message += "输入特种从业总人数不能有空格\n";
                }else if(parseInt($("#specialCount").val()) < 0){
                    values = false;
                    message += "输入特种从业总人数应大于0\n";
                }

                if ($("#compAddress").val() == "") {
                    values = false;
                    message += "输入单位地址\n";
                }else if($("#compAddress").val().split(" ").length != 1){
                    values = false;
                    message += "输入单位地址不能有空格\n";
                }

                if ($("#compPhone").val() == "") {
                    values = false;
                    message += "输入单位电话\n";
                }else if($("#compPhone").val().split(" ").length != 1){
                    values = false;
                    message += "输入单位电话不能有空格\n";
                }else if(!regTel1.test($("#compPhone").val()) && !regTel2.test($("#compPhone").val()) && !regTel3.test($("#compPhone").val())){
                    values = false;
                    message += "输入单位电话有误\n";
                }

                var reg = /^[1-9][0-9]{5}$/;
                if (!reg.test($("#postcodes").val())) {
                    values = false;
                    message += "请输入6位有效的邮编\n";
                }else if($("#postcodes").val().split(" ").length != 1){
                    values = false;
                    message += "输入邮编不能有空格\n";
                }

                if(values) {
                    return true;
                }else{
                    return false;
                }
            }else{
                return false;
            }
        }


/*       function submitForm() {
            if(checked()){
                //提交表单数据
                $('#mainForm').ajaxForm({
                    dataType: "json",
                    type: "post",
                    url: "${ctx}/useCompanyInfoAction/updateComInfo",
                    success: function (data) {
                        if (data.code == 200) {
                            alert(data.message);
                            location.href = "${ctx}" + data.url.toString();

                        } else {
                            alert(data.message);
                            $("#modify").attr("disabled",false);
                        }
                    },
                    error: function () {
                        alert("系统繁忙");
                        $("#modify").attr("disabled",false);
                    }
                });
            }else {
                alert(message);
            }
        }*/
    })
</script>
</body>
</html>

