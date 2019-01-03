<%--
  Created by IntelliJ IDEA.
  User: 77426
  Date: 2017/5/20
  Time: 15:54
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
    <meta name="decorator" content="default"/>
    <title>刻章点印章数据统计</title>
    <link rel="stylesheet" href="${ctxHtml}/css/bootstrap.min.css">
    <link rel="stylesheet" href="${ctxHtml}/css/paging.css?id=${fns:getConfig('css.version')}">
    <script language="JavaScript" src="${ctxHtml}/js/jquery-1.9.1.min.js"></script>
    <script src="${ctxHtml}/js/bootstrap.min.js"></script>
    <style>
        table input {
            width: 100%;
            border: none;
            background: transparent;
        }
        input{
            height: 30px!important;
            margin-bottom: 0!important;
        }
        .search-btn-div{
            width:100%;
            text-align: right;
        }
    </style>
    <script type="text/javascript">
        function page(n, s) {
            $("#pageNo").val(n);
            $("#pageSize").val(s);
            $("#searchForm").submit();
            return false;
        }

        function exportExcel() {
            //加载中...
//            $(".bg-model").fadeTo(300, 1);
//            //隐藏窗体的滚动条
//            $("body").css({"overflow": "hidden"});

            <%--$.ajax({--%>
                <%--type:"post",--%>
                <%--url:"${ctx}/makeCompany/count/stamp/exportExcel",--%>
                <%--dataType:"json",--%>
                <%--success:function (result) {--%>
                   <%--console.log(result.code);--%>
                    <%--if(result.code=="200"){--%>

                        <%--$(".bg-model").hide();--%>

                        <%--显示窗体的滚动条--%>
                        <%--$("body").css({"overflow": "visible"});--%>

                        <%--$("#searchForm").attr("action", "${ctx}/makeCompany/count/stamp/list");--%>
                    <%--}--%>
                <%--}--%>
            <%--});--%>

            $("#searchForm").attr("action", "${ctx}/makeCompany/count/stamp/exportExcel");
            $("#searchForm").submit();

            $("#searchForm").attr("action", "${ctx}/makeCompany/count/stamp/list");

//            $(".bg-model").hide();
//            显示窗体的滚动条
//            $("body").css({"overflow": "visible"});



        }

    </script>
</head>
<body>
<div class="panel-head clearfix" style="margin: 10px 40px">
    <h3 style="display: inline">印章数据统计</h3>
    <span style="float: right;margin-right: 10px">
        查询结果共有【<span>${page.count}</span>】条数据
    </span>
</div>
<form:form id="searchForm" modelAttribute="dto" action="${ctx}/makeCompany/count/stamp/list"
           method="post" cssStyle="width: 93%;margin: 10px auto;" class="breadcrumb form-search">
    <%--页码记录--%>
    <input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
    <input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>

    <table class="table table-bordered">
        <tr>
            <td class="col-md-1 column">
            <label>企业名称：</label>
            </td>
            <td class="col-md-2 column">
                <form:input path="companyName" cssStyle="width: 255px"></form:input>
            </td>
            <td class="col-md-1 column">
            <label>印章状态：</label>
            </td>
            <td class="col-md-2 column">
                <form:select path="stampState" cssStyle="width: 255px">
                    <form:option value="" label="全部"></form:option>
                    <form:options items="${stampStates}" itemLabel="value"></form:options>
                </form:select>
            </td>
            <td class="col-md-1 column">
            <label>印章形式：</label>
            </td>
            <td class="col-md-2 column">
                <form:select path="stampShape" cssStyle="width: 255px">
                    <form:option value="" label="全部"></form:option>
                    <form:option value="1" label="物理印章"></form:option>
                    <form:option value="2" label="电子印章"></form:option>
                </form:select>
            </td>
        </tr>
        <tr>
            <td class="col-md-1 column">
                <label>制作日期范围：</label>
            </td>
            <td class="col-md-2 column">
                <input id="beginMakeDate" name="beginMakeDate" type="text" readonly="readonly" maxlength="20" class="input-mini Wdate" style="width: 110px"
                       value="<fmt:formatDate value="${dto.beginMakeDate}" pattern="yyyy-MM-dd"/>" onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:true});"/>
                <label>至&nbsp;&nbsp;</label>
                <input id="endMakeDate" name="endMakeDate" type="text" readonly="readonly" maxlength="20" class="input-mini Wdate" style="width: 110px"
                       value="<fmt:formatDate value="${dto.endMakeDate}" pattern="yyyy-MM-dd"/>" onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:true});"/>
            </td>

            <td class="col-md-1 column"><label>印章类型:</label></td>
            <td class="col-md-2 column">
                <form:select path="stampType" cssStyle="width: 255px">
                    <form:option value="" label="全部"></form:option>
                    <form:options items="${fns:getDictList('stampType')}" itemLabel="label" itemValue="value" htmlEscape="false" />
                </form:select>
            </td>


            <td class="col-md-1 column">
                <label>交付日期范围：</label>
            </td>

            <td class="col-md-2 column">
                <input id="beginDeliveryDate" name="beginDeliveryDate" type="text" readonly="readonly" maxlength="20" class="input-mini Wdate" style="width: 110px"
                       value="<fmt:formatDate value="${dto.beginDeliveryDate}" pattern="yyyy-MM-dd"/>" onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:true});"/>
                <label>至&nbsp;&nbsp;</label>
                <input id="endDeliveryDate" name="endDeliveryDate" type="text" readonly="readonly" maxlength="20" class="input-mini Wdate" style="width: 110px"
                       value="<fmt:formatDate value="${dto.endDeliveryDate}" pattern="yyyy-MM-dd"/>" onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:true});"/>
            </td>
        </tr>

    </table>
   <div class="search-btn-div">
       <button type="submit" class="btn btn-info">搜索</button>
       <button class="btn btn-info" onclick="exportExcel()"  type="button">导出到Excel</button>
   </div>
</form:form>
<table class="table table-striped table-bordered" style="width: 93%;margin: 10px auto">
    <tr>
        <th>企业名称</th>
        <th>印章编码</th>
        <th>印章类型</th>
        <th>印章状态</th>
        <th>物理/电子</th>
        <th>备案日期</th>
        <th>制作日期</th>
        <th>交付日期</th>
    </tr>
    <c:forEach items="${page.list}" var="stamp">
        <tr>
            <td>${stamp.useComp.companyName}</td>
            <td>${stamp.stampCode}</td>
            <td>${fns:getDictLabel(stamp.stampType,'stampType' ,null )}</td>
            <td>${stamp.stampState.value}</td>
            <td>
                <c:if test="${stamp.stampShape == '1'}">
                    物理印章
                </c:if>
                <c:if test="${stamp.stampShape == '2'}">
                    电子印章
                </c:if>
            </td>
            <td>
                <fmt:formatDate value="${stamp.recordDate}" pattern="yyyy-MM-dd"/>
            </td>
            <td>
                <fmt:formatDate value="${stamp.makeDate}" pattern="yyyy-MM-dd"/>
            </td>
            <td>
                <fmt:formatDate value="${stamp.deliveryDate}" pattern="yyyy-MM-dd"/>
            </td>
        </tr>
    </c:forEach>
</table>
<div class="pagination" style="margin-left: 20%">
    ${page}
</div>

<div id="fade" class="black_overlay"></div>
<%--<div id="MyDiv" class="load_img">--%>
    <%--<img src="${ctxStatic}/sign/img/loading.gif">--%>
<%--</div>--%>

<div class="bg-model"
     style="position:absolute;top:0;left:0;display:none;background:rgba(0,0,0,0.3);width:100%;height:100%;position:fixed;z-index:9999">
    <div class='content'
         style="position: absolute;left: 35%;top: 25%;border-radius: 8px;width: 30%;height: 40%;text-align: center">
        <div id="MyDiv" class="load_img">
            <img src="${ctxStatic}/sign/img/export.gif">
        </div>
    </div>
</div>

</body>
</html>
