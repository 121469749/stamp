<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp" %>
<!DOCTYPE html>
<html>
<head lang="zh">
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no"/>
    <title>用章单位-信息更新</title>
    <link rel="stylesheet" href="${ctxHtml}/css/bootstrap.min.css"/>
    <script language="JavaScript" src="${ctxHtml}/js/jquery-1.9.1.min.js"></script>
    <script type="text/javascript" src="${ctxHtml}/js/jquery-form.js"></script>
</head>
<body>
    <div style="width: 80%;margin: 20px auto">
        <h3>用章单位-信息更新</h3>
        <table class="table table-bordered ">
            <tbody>
            <tr>
                <td>单位名称</td>
                <td colspan="2"><input type="text" class="form-control" value="${company.companyName}" readonly></td>
                <td>单位类别</td>
                <c:set var="type" value="${company.type1}"/>
                <td colspan="2"><input type="text" class="form-control" value="${fns:getDictLabel(type, "unitType", null)}" readonly></td>
            </tr>
            <tr>
                <td>单位统一社会信用代码</td>
                <td colspan="2"><input type="text" class="form-control" value="${company.soleCode}" readonly></td>
                <td>单位联系电话</td>
                <td colspan="2"><input type="text" class="form-control" value="${company.compPhone}" readonly></td>
            </tr>
            <tr>
                <td>单位地址</td>
                <td colspan="3"><input type="text" class="form-control" value="${company.compAddress}" readonly></td>
                <td>法人姓名</td>
                <td><input type="text" class="form-control" value="${company.legalName}" readonly></td>

            </tr>
            <tr>
                <td>法人联系电话</td>
                <td><input type="text" class="form-control" value="${company.legalPhone}" readonly></td>
                <td>法人证件号码</td>
                <td><input type="text" class="form-control" value="${company.legalCertCode}" readonly></td>
                <td>法人证件类型</td>
                <c:set var="legalCertType" value="${company.legalCertType}"/>
                <td><input type="text" class="form-control" value="${fns:getDictLabel(legalCertType, "certificateType", null)}" readonly></td>
            </tr>

            <tr>
                <td>经济性质</td>
                <c:set var="busType" value="${company.busType}"/>
                <td colspan="2"><input type="text" class="form-control" value="${fns:getDictLabel(busType, "busType", null)}" readonly></td>
                <td>企业营业执照</td>
                <td colspan="2"><input type="text" class="form-control" value="${company.busLicnum}" readonly></td>
            </tr>
            <tr>

                <td>企业税务登记号</td>
                <td><input type="text" class="form-control" value="${company.busTagnum}" readonly></td>
                <td>企业状态</td>
                <td><input type="text" class="form-control" value="${company.compState.value}" readonly></td>
                <td>成立日期</td>
                <td><input type="text" class="form-control" value="${company.compCreatDate}" readonly></td>
            </tr>
            <tr>
                <td>治安负责人</td>
                <td colspan="2">
                    <input name="policeName" id="policeName" type="text" class="form-control" value="${company.policeName}" readonly>
                </td>
                <td>治安负责人身份证号码</td>
                <td colspan="2">
                    <input name="policeIdCode" id="policeIdCode" type="text" class="form-control" value="${company.policeIdCode}" readonly>
                </td>
            </tr>
            <tr>
                <td>治安负责人联系电话</td>
                <td>
                    <input name="policePhone" id="policePhone" type="text" class="form-control" value="${company.policePhone}" readonly>
                </td>
                <td>单位总人数</td>
                <td>
                    <input name="headCount" id="headCount" type="text" class="form-control" value="${company.headCount}" readonly>
                </td>
                <td>单位特业从业人员</td>
                <td>
                    <input name="specialCount" id="specialCount" type="text" class="form-control" value="${company.specialCount}" readonly>
                </td>
            </tr>
            <tr>
                <td>邮编</td>
                <td>
                    <input name="postcode" id="postcode" type="text" class="form-control" value="${company.postcode}" readonly>
                </td>
                <td>经营方式</td>
                <td colspan="3"><input name="busModel" id="busModel" type="text" class="form-control" value="${company.busModel}" readonly></td>
            </tr>
            <tr>
                <td>单位电话</td>
                <td><input name="compPhone" id="compPhone" type="text" class="form-control" value="${company.compPhone}" readonly></td>
                <td>登记机关</td>
                <td><input name="recordUnit" id="recordUnit" type="text" class="form-control" value="${company.recordUnit}" readonly></td>
                <td>经营范围</td>
                <td><input name="busScope" id="busScope" type="text" class="form-control" value="${company.busScope}" readonly></td>
            </tr>
            <tr>
                <td>注册资本</td>
                <td><input name="regCap" id="regCap" type="text" class="form-control" value="${company.regCap}" readonly></td>

                <td>营业期限起始时间</td>
                <td><input name="startDate" id="startDate" type="text" class="form-control" value="<fmt:formatDate value="${company.busStartDate}" pattern="yyyy-MM-dd"/>" readonly></td>

                <td>营业期限截止时间</td>
                <td><input name="endDate" id="endDate" type="text" class="form-control" value="<fmt:formatDate value="${company.busEndDate}" pattern="yyyy-MM-dd"/>" readonly></td>

            </tr>

            </tbody>
        </table>
        <shiro:hasPermission name="useCompany:edit"><a href="${ctx}/useCompanyInfoPage/showComInfo" style="width: 10%;margin-left: 45%" class="btn btn-default">更新</a></shiro:hasPermission>

    </div>
</body>
</html>