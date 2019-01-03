<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2017/5/15
  Time: 10:02
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp" %>
<html>
<head>
    <title>Title</title>
    <script type="text/javascript" src="${ctxStatic}/jquery/jquery-1.8.3.js"></script>
    <script type="text/javascript">

        function saveAttachment() {
            var formdata = new FormData($("#Attachment")[0]);
            $.ajax({
                type: "post",
                data: formdata,
                dataType: "json",
                url: "${ctx}/policeLicenseAction/testSave",
                contentType: false,
                cache: false,
                processData: false,
                success: function (result) {
                    alert(result);
                },
                error: function () {
                    alert("fail!");
                }
            })

        }
    </script>
</head>
<body>
    <form id="Attachment" action="" method="post" >
        文件:<input type="file" name="attachment" value="">
        文件名:<input type="text" name="attachName">
        文件类型:<input type="text" name="attachType">
        <input type="button" value="提交" onclick="saveAttachment()">
    </form>
</body>
</html>
