<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no"/>
    <title>Ukey管理</title>
    <link href="https://cdnjs.cloudflare.com/ajax/libs/twitter-bootstrap/3.3.7/css/bootstrap.min.css" rel="stylesheet">
    <script src="${ctxHtml}/js/jquery-1.9.1.min.js"></script>
</head>
<body>
    <c:if test="${type eq 'ukey_logout'}">
        <div class="panel-head" style="margin-left:20px;color: rgb(49, 126, 172);font-weight: bold;font-family: Telex, sans-serif;">
            <h4><strong>当前路径：Ukey管理 \ 注销</strong></h4>
            <button id="revokeES" type="button" class="btn btn-danger bottom-btn col-md-4"
                    style="text-shadow: black 4px 30px 30px;"
                    title="点击注销印章">
                <span class="glyphicon glyphicon-repeat revokeES"></span>
                <img class="revokeESLoad" src="${ctxStatic}/sign/pdfjs/web/images/loading-icon.gif" style="display: none;">
                注销(谨慎操作)
            </button>
        </div>
    </c:if>
    <c:if test="${type eq 'ukey_changePIN'}">
        <div class="panel-head" style="margin-left:20px;color: rgb(49, 126, 172);font-weight: bold;font-family: Telex, sans-serif;">
            <h4><strong>当前路径：Ukey管理 \ 修改PIN码</strong></h4>
            <button id="changePIN" type="button" class="btn btn-warning bottom-btn col-md-4"
                    style="text-shadow: black 4px 30px 30px;"
                    title="点击修改用户PIN码">
                <span class="glyphicon glyphicon-edit changePIN"></span>
                <img class="changePINLoad" src="${ctxStatic}/sign/pdfjs/web/images/loading-icon.gif" style="display: none;">
                修改用户PIN码
            </button>
        </div>
    </c:if>



    <script>
        // 修改PIN码
        $("#changePIN").click(function () {
            $(".changePIN").hide();
            $(".changePINLoad").show();

            var returnStr = window.external.ChangePin();
            alert(returnStr);

            $(".changePINLoad").hide();
            $(".changePIN").show();
        });

        // 注销
        $("#revokeES").click(function () {
            var msg = "此操作会将Ukey(包括PIN码)整个初始化，是否确认注销？";
            if(confirm(msg)==true){
                $(".revokeES").hide();
                $(".revokeESLoad").show();

                var returnStr = window.external.ProcRevoke();
                if (returnStr.indexOf("success") != -1) {
                    returnStr = "注销成功";
                }
                alert(returnStr);

                $(".revokeESLoad").hide();
                $(".revokeES").show();

            }else{
                $(".revokeESLoad").hide();
                $(".revokeES").show();
                return false;
            }
        });
    </script>
</body>
</html>
