<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>缴销回执</title>
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
    <span >缴销</span>
    <span>\</span>
    <span >缴销回执</span>
</p>
<div class="article">
    <div>
        <h1 class="text-center">回执</h1>

    </div>
    <div class="content">
        <p><fmt:formatDate value="${stampRecord.createDate}"  pattern="yyyy-MM-dd"/>收到${useCompanyName}公司的缴销申请,缴销
            <c:forEach items="${stamps}" var="stamp">
                ${fns:getDictLabel(stamp.stampType, "stampType",null )} 1个，
            </c:forEach>
            请缴销完成后拿该回执进行领取。</p>
        <p style="text-align: right" >${makeCompanyName}公司</p>
        <p  style="text-align: right"><fmt:formatDate value="${stampRecord.createDate}"  pattern="yyyy-MM-dd"/> </p>
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