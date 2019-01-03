<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>许可证打印</title>
    <script language="javascript" src="${ctxHtml}/js/jquery-1.9.1.min.js"></script>
    <script language="javascript">
        function printf()
        {
            <%--调用浏览器执行打印事件--%>
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
<div class="article">
    <div>
        <h1 class="text-center">印章${stampPermissionVO.type}-回执单</h1>
    </div>
    <div class="content">

        <p>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
            <front style="text-decoration:underline">${stampPermissionVO.lastRecord.companyName}</front>&nbsp;&nbsp;的&nbsp;&nbsp;${stampPermissionVO.agentName}&nbsp;&nbsp;(身份证号：${stampPermissionVO.agentId})&nbsp;&nbsp;于&nbsp;&nbsp;
            <fmt:formatDate value="${stampPermissionVO.applyDate}" type="date" dateStyle="long"/>
            &nbsp;&nbsp;在&nbsp;&nbsp;${stampPermissionVO.userName}&nbsp;&nbsp;${stampPermissionVO.type}&nbsp;&nbsp;${fns:getDictLabel(stampPermissionVO.stampTexture,"stampTexture",null)}-${stampPermissionVO.stampName}。</p>
        <br/>
        <c:if test="${stampPermissionVO.makeCompany.compState == 'USING'}">
            <%--<p>凭此证明到&nbsp;&nbsp;<front style="text-decoration:underline">${stampPermissionVO.makeCompany.companyName}</front>&nbsp;&nbsp;刻章点；刻章点输入回执验证码后进行后续操作。</p>--%>
            <p>凭此证明到公安指定刻章点，刻章点输入回执验证码后进行后续操作。</p>

            <%--<p>地址：${stampPermissionVO.makeCompany.compAddress}</p>--%>
            <%--<p>刻章点联系电话：${stampPermissionVO.makeCompany.compPhone}</p>--%>

        </c:if>
        <c:if test="${stampPermissionVO.makeCompany.compState == 'STOP'||stampPermissionVO.makeCompany.compState == 'LOGOUT'}">
        <p>凭此证明到&nbsp;&nbsp;${stampPermissionVO.makeCompany.area.name}&nbsp;&nbsp;任意刻章点进行${stampPermissionVO.type}</p>
        </c:if>
        <p>
        <h3> 回执验证码为：${stampPermissionVO.stampId}</h3>
        </p>
        <p style="text-align: right" > ${stampPermissionVO.userName}</p>

        <p  style="text-align: right">
            <fmt:formatDate value="${stampPermissionVO.applyDate}" type="date" dateStyle="long"/>
        </p>
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