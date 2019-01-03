<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>备案回执打印</title>
    <script language="javascript" src="${ctxHtml}/js/jquery-1.9.1.min.js"></script>
    <script language="javascript">

        function printf()
        {
            window.print();
        }
    </script>
    <style>
        @media print {
            .curroute{
                display: none;
            }
        }
        body{
            width:100%;
            margin:auto;
            font-family: "Helvetica Neue", Helvetica, Tahoma, Arial, 'Microsoft Yahei', 'PingFang SC', 'Hiragino Sans GB', 'WenQuanYi Micro Hei', sans-serif;
            font-size: 13px;
            line-height: 1.53846154;
            color: #353535;
            background-color: #fff;
        }
        .article{
            margin:2% auto;
            padding: 20px;
            border:solid 1px black;
            width:80%;
        }
        .article div h1{
            margin-bottom: 20px;
            line-height: 1.5;
            font-size: 26px;
        }
        button{
            margin-top:20px;
        }
        .curroute{
            width: 40%;
            min-width: 350px;
            margin: 20px auto;

        }
        .content{
            padding: 20px 0;
            font-size: 14px;
            line-height: 1.78571429;
        }
        .text-center{
            text-align: center;
        }
        @media print {
            .noprint{
                display: none;
            }
        }
    </style>
</head>
<body>
<p class="curroute noprint" style="font-size: 18px">
    <span><strong>当前路径：</strong></span>
    <span >首页</span>
    <span>\</span>
    <span >印章备案</span>
    <span>\</span>
    <span >印章备案回执</span>
</p>
<div class="article" style="width: 70%">
    <div>
        <h1 class="text-center">回执</h1>

    </div>
    <div class="content" >
        <p>${receiptInfo.useCompany.companyName} 的 ${receiptInfo.stampRecord.agentName} 于 <fmt:formatDate value="${receiptInfo.stampRecord.createDate}"  pattern="yyyy-MM-dd"/>&nbsp;申请刻制
            <br />
            <strong>一、物理印章：</strong><br/>
            <c:forEach items="${receiptInfo.stamps}" var="stamp1" varStatus="status1">
                <c:if test="${stamp1.stampShape == 1}">
                    &nbsp;&nbsp;(${status1.index + 1})&nbsp;${fns:getDictLabel(stamp1.stampType, "stampType",null )} 1个，<br />
                </c:if>
            </c:forEach>
            <strong>二、电子印章：</strong><br/>
            <c:forEach items="${receiptInfo.stamps}" var="stamp2" varStatus="status2">
                <c:if test="${stamp2.stampShape == 2}">
                    &nbsp;&nbsp;(${status2.index + 1})&nbsp;${fns:getDictLabel(stamp2.stampType, "stampType",null )} 1个，<br />
                </c:if>
                <%--${fns:getDictLabel(stamp.stampType, "stampType",null )}-${fns:getDictLabel(stamp.stampShape, "stampShape",null )} 1个，--%>
            </c:forEach>
            <br />
            凭此回执到刻章点领取，特此证明。</p>
        <p style="text-align: right" >${receiptInfo.makeCompany.companyName}</p>
        <p  style="text-align: right"><fmt:formatDate value="${receiptInfo.stampRecord.createDate}"  pattern="yyyy-MM-dd"/> </p>
    </div>

</div>
<div id="btn" class="noprint" style="text-align: center">
    <input class='btn' type='button' name='button_export' title='打印' onclick='printf()' value='打印'>
</div>

<script>
    window.onload=function(){
        printf();
    }
</script>
</body>
</html>