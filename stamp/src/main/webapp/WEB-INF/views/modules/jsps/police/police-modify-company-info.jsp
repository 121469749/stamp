<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp" %>
<html lang="zh-cn">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no"/>
    <meta name="renderer" content="webkit">
    <title>印章刻制-变更</title>
    <link rel="stylesheet" href="${ctxHtml}/css/bootstrap.min.css">
    <link rel="stylesheet" href="${ctxHtml}/css/font-awesome.min.css">
    <link rel="stylesheet" href="${ctxHtml}/css/myCss.css">
    <link rel="stylesheet" href="${ctxHtml}/css/licence.css">
    <link rel="stylesheet" href="${ctxHtml}/css/engraveseal.css">
    <script src="${ctxHtml}/js/bootstrap.min.js"></script>
    <script type="text/javascript" src="${ctxHtml}/js/jquery-1.9.1.min.js"></script>
    <script type="text/javascript" src="${ctxHtml}/js/jquery-form.js"></script>
    <script type="text/javascript" src="${ctxHtml}/js/webuploader.html5only.min.js"></script>
    <script type="text/javascript" src="${ctxHtml}/js/police-modify-company-info.js?id=3"></script>
    <style type="text/css">
        body {
            padding: 0 2%;
            font-family: "微软雅黑";
        }

        /*隐藏滚动条*/
        body::-webkit-scrollbar {
            display: none
        }

        th {
            text-align: center;
            border: 1px solid #d1d1d1;
        }

        th img {
            width: 22px;
            height: 22px;
            display: none;
        }
        .modal-content{
            background: rgba(0,0,0,0);
            border: none;
            box-shadow: none;
            text-align: center;
        }
        .box {
            margin: 0;
            width: 100%;
        }

        .del {
            padding: 1px 5px;
            font-size: 18px;
            color: white;
            margin-left: 5px;
            background-color: #c9302c!important;
            border-color: #ac2925!important;
        }
        .del:hover{
            background-color:#333!important;
            color: white;
        }
        .fileBoxUl li {
            float: left;
            margin-right: 2px;
        }

        .upload {
            width: 24%;
            margin: 0;
        }

        .addhtml {
            width: 70%;
            margin: 0;
            cursor: pointer;
        }
        #my-btn:disabled{
            opacity: 0.5;
        }
        input[type="text"]{
            height: 30px;
            line-height: 30px;
            width: 65%;
        }

        input[type="file"]{
            position: absolute;
            top: 0;
            left: 0;
            right: 0;
            opacity: 0;
            height: 30px;
            filter:progid:DXImageTransform.Microsoft.Alpha(opacity=0);
        }
    </style>
    <script>
        var ctx = "${ctx}";
        $(document).ready(function(){
            $('#stampRecord').ajaxForm({
                dataType: 'json',
                type:"post",
                url: "${ctx}/stampMakeAction/changeStamp",
                success: function (result) {
                    alert(result.message);
                    if(result.code == 200){
                        window.location="${ctx}/stampMakePage/stampView";
                    }else {
                        $("#my-btn").removeAttr("disabled");
                    }
                },
                error: function () {
                    alert("未知错误???");
                    $("#my-btn").removeAttr("disabled");
                }
            });
        });

    </script>
</head>
<body>
<div>
    <div class="panel-head">
        <h3>印章管理-企业信息变更</h3>
        <%--<b style="font-size: 25px;font-weight: 300"><span class="fa fa-address-book-o"></span>
            &nbsp;印章刻制-缴销</b>--%>
        <span style="float: right">
            登记时间：<span id="cur-time" style="margin-right: 50px"><fmt:formatDate value="${currentTime}" pattern="yyyy-MM-dd" /></span>
            业务流水号：<span>${serialNum}</span>
        </span>
    </div>
    <form:form id="frmModifyCompanyInfo" modelAttribute="modifyCompanyInfo" action="" method="post" enctype="multipart/form-data" >
    <form:hidden path="serialNum" value="${serialNum}"></form:hidden>
    <form:hidden path="company.id" value="${modifyCompanyInfo.company.id}"></form:hidden>
    <div>
        <div class="form-group">
            <label for="modifyReason" style="color: #3c3c3c">变更原因：<p style="color: red"></p></label>
            <form:textarea id="modifyReason" path="modifyReason"  rows="10" cssStyle="overflow: scroll;overflow-x: hidden;width: 100%"></form:textarea>
        </div>
    </div>
    <table class="table table-striped table-bordered">
        <tr>
            <th>用章单位名称</th>
            <td colspan="3">
                <form:input path="company.companyName" id="companyName" class="form-control" value="${company.companyName}" cssStyle="width: 535px;" readonly="true"/>
            </td>
        </tr>
        <tr>
            <th>企业统一社会信用代码</th>
            <td>
                <form:input path="company.soleCode" id="unifiedcode" class="form-control" value="${company.soleCode}" cssStyle="width:280px;" readonly="true"/>
            </td>
            <th>单位类别</th>
            <td>
                <form:select class="form-control showtext" id="comCertType"  path="company.type1" cssStyle="width:280px;" readonly="true" disabled="true">
                    <form:option value="${company.type1}">${fns:getDictLabel(company.type1,"unitType" ,null )}</form:option>
                    <form:options items="${fns:getDictList('unitType')}" itemLabel="label" itemValue="value"
                                  htmlEscape="false"/>
                </form:select>
            </td>
                <%--<th>用章单位联系电话</th>--%>
                <%--<td>--%>
                <%--<form:input path="useCompany.compPhone" id="companyphone" class="form-control" value="${busniessHandler.useCompany.compPhone}"/>--%>
                <%--</td>--%>
        </tr>
        <tr>
            <th>用章单位地址</th>
            <td colspan="3">
                <form:input path="company.compAddress" id="companyAddress" class="form-control" value="${company.compAddress}" cssStyle="width: 785px;"/>
            </td>
        </tr>
        <tr>
            <th>法人姓名</th>
            <td>
                <form:input path="company.legalName" id="legalName" class="form-control" value="${company.legalName}" cssStyle="width:280px;"/>

            </td>
            <th>法人联系电话</th>
            <td>
                <form:input path="company.legalPhone" id="legalPhone" class="form-control" value="${company.legalPhone}" cssStyle="width:280px;"/>
            </td>
        </tr>
        <tr>
            <th>法人证件类型</th>
            <td>

                <form:select class="form-control showtext" id="legalCertType" path="company.legalCertType" cssStyle="width:280px;" >
                    <form:options items="${fns:getDictList('certificateType')}" itemLabel="label" itemValue="value"
                                  htmlEscape="false"/>
                </form:select>
            </td>
            <th>法人证件号码</th>
            <td>
                <form:input path="company.legalCertCode" id="legalCertCode" class="form-control" value="${company.legalCertCode}" style="width:280px;" />
            </td>
        </tr>
        <tr>
            <th>经办人姓名</th>
            <td>
                <form:input class="form-control" id="agentName" path="agentName" type="text" cssStyle="width:280px;" />
            </td>
            <th>经办人联系电话</th>
            <td>
                <form:input class="form-control" id="agentPhone" path="agentPhone" type="text" cssStyle="width:280px;" />
            </td>
        </tr>
        <tr>
            <th>经办人证件类型</th>
            <td>
                <form:select class="form-control" path="agentCertType" id="agentCertType" cssStyle="width:280px;">
                    <form:options items="${fns:getDictList('certificateType')}" itemLabel="label" itemValue="value"
                                  htmlEscape="false"/>
                </form:select>
            </td>
            <th>经办人证件号码</th>
            <td>
                <form:input class="form-control" id="agentCertCode" path="agentCertCode" type="text"  cssStyle="width:280px;"/>
            </td>
        </tr>
    </table>
    <table class="table table-striped table-bordered">
        <tr>
            <td rowspan="20" style="width: 10%;line-height:45px;text-align: center"><strong>附<br>件<br>材<br>料<br>上<br>传</strong></td>
        </tr>
        <c:forEach items="${dir}" var="dictMap">
            <tr>
                <td>
                    <c:if test="${dictMap.key!='0'}">
                        <font color="red">&nbsp;*&nbsp;</font>
                    </c:if>
                        ${dictMap.dict.label}
                    <input type="text" value="${dictMap.key}" id="requiredList" name="requiredList" style="display: none">
                </td>
                <td style="width: 370px;">
                    <div class="box">
                        <div class="clearfix" style="position:relative;">
                            <input type="text" value="${dictMap.dict.value}" id="temp" name="temp" style="display: none">
                            <input type="button" class="btn" value="选择文件">
                            <input type="text" readonly style="max-width: 150px">
                            <input class='upload' name="file" type='file' id="${dictMap.dict.value}">
                            <input type='text' value='-1' id='fileType' name='fileType' style='display: none'>
                            <input style="display: none" class="btn btn-primary showimg" data-toggle="modal"
                                   data-target=".bs-example-modal-lg" onclick="showimg(this)" type="button"
                                   value="查看">
                        </div>
                        <input class="addhtml btn" type="button" style="margin-top: 2px;width: 80px;" value="+" width="80">
                    </div>
                </td>
            </tr>
        </c:forEach>
    </table>
    <center>
        <input type="button"  class="btn-lg bg-primary" id="my-btn" value="变更" onclick="valid_form_data('${ctx}/policeStampPage/modifyCompanyInfo')"/>
    </center>

</div>
</form:form>
<!--模态框-->
<div class="modal fade bs-example-modal-lg" tabindex="-1" role="dialog" aria-labelledby="myLargeModalLabel">
    <div class="modal-dialog modal-lg" role="document">
        <div class="modal-content">
            <div class="modal-header" style="border: 0;padding-right: 10%">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                    <span aria-hidden="true"><img src="${ctxHtml}/images/close.png"></span></button>
            </div>
            <img style="width: 100%" id="modalimg">
        </div>
    </div>
</div>

<div class="my-mask"></div>
<script src="${ctxHtml}/js/bootstrap.min.js"></script>
<script type="text/javascript">
    <%--点击查看按钮触发事件--%>
    function showimg(a){
        var isIE = navigator.userAgent.match(/MSIE/)!= null,
            isIE8 = navigator.userAgent.match(/MSIE 8.0/)!= null;
        <%--如果是IE8版本显示图片方式--%>
        if(isIE8){
            $(".modalBox").css("display", "block");
            document.getElementById("modalimg").style.filter = "progid:DXImageTransform.Microsoft.AlphaImageLoader(sizingMethod='scale',src=\"" + a.name + "\")";
            document.getElementById("modalimg").style.width = "90%";
        }
        else{
            <%--非IE8版本显示方式--%>
            $(".modalBox").css("display", "block");
            document.getElementById("modalimg").src = a.name;
        }
    }

    //全局变量
    var temptt = -1;

    $(function(){
        <%--上传文件选择完文件后--%>
        $(document).on("change","input[type='file']",function(e){
            var files = $(e.target);
            var text = $(e.target).prev();
            var showimg = $(e.target).next().next();
            var determine = new Array();
            var a = false;
            <%--设置图片格式数组，用于验证文件格式--%>
            var arr = ["jpg","JPG","png","PNG","jpeg","JPEG","bmp","BMP","gif","GIF"];
            var textname = new Array();
            <%--分割文件路径和格式--%>
            textname = files.val().split("\\");
            determine = files.val().split(".");
            text.val(files.val());
            for(var i = 0;i < arr.length;i++){
                if(determine[1] == arr[i]){
                    a = true;
                }
            }
            <%--如果没有上传文件或文件是文件格式，不显示查看按钮--%>
            if(files.val() == ""){
                showimg.hide();
            }else if(!a){
                text.val(textname[textname.length-1]);
//                    text.show();
                showimg.hide();
            }else{
                text.val(textname[textname.length-1]);
//                    text.hide();
                showimg.show();
            }
            <%--识别是否上传了重复的文件--%>
            <%--遍历同级下所有文件的文件名--%>
            var temp = $(files.parents("td").find("input[type='file']"));
            var index = $(files.parents("div")).index();
            for(var i = 0 ; i < temp.length;i++){
                if(files.val() == temp.eq(i).val() && index != i){
                    alert("上传文件重复！");
                    files.val("");
                    text.val("");
                    showimg.css("display","none");
                }
            }
            <%--以下为用于点击查看按钮时显示对应图片的代码--%>
            var fil = this.files;
            var file = $(this);
            /*var pic = $("#modalimg");*/
            /*var pic = file.next("img");*/
            var isIE = navigator.userAgent.match(/MSIE/)!= null,
                isIE6 = navigator.userAgent.match(/MSIE 6.0/)!= null;
            <%--如果是IE保存的文件路径为虚拟路径--%>
            if(isIE) {
                file.select();
                var reallocalpath = document.selection.createRange().text;
                showimg.attr('name',reallocalpath);
            }else {
                <%--如果不是IE版本，保存文件流--%>
                var reader = new FileReader();
                reader.readAsDataURL(fil[0]);
                reader.onload = function () {
                    showimg.attr('name', reader.result);
                }
            }

            var aftertt = $(e.target).next().val();
            temptt = $(e.target).prev().prev().prev().val();
            if(aftertt!=$(e.target).prev().prev().prev().val()) {
                $(e.target).next().remove()
                $(this).after("<input type='text' value='" + temptt + "' id='fileType' name='fileType' style='display: none'>");
            }else{
                $(e.target).next().remove()
                $(this).after("<input type='text' value='" + temptt + "' id='fileType' name='fileType' style='display: none'>");
            }

        });

        $(document).on("click",".modalBox",function(){
            $(this).css("display", "none");
        });
        <%--图片上传，添加新的一栏的按钮的点击事件--%>
        $(".addhtml").click(function(){
            <%--获得前一项的上传框的值--%>
            var hasfile = $(this).prev("div").children("input[type='file']").val();
            var textval = $(this).prev("div").children("input[type='text']").val();
            var checked = $(this).parents("td");
            var a = checked.find("input[type = 'text']");
            var x = true;
            <%--如果上一项为空，将x值变为false--%>
            for(var i = 0 ; i < a.length;i++){
                if(a.eq(i).val() == "" || a.eq(i).val() == null){
                    x = false;
                }
            }
            <%--若上一项不为空，则在后面添加新的一栏上传文件div块--%>
            if(x){
                $(this).before("<div class='clearfix' style='position:relative;'><input type='text' value='"+temptt+"' id='temp' name='temp' style='display: none'><input type='button' class='btn' value='选择文件'><input type='text' style='margin-left: 4px;max-width: 150px;height: 30px;line-height: 30px' readonly> <input class='upload' name='file' type='file'><input type='text' value='-1' id='fileType' name='fileType' style='display: none'> <input style='display: none' class='btn btn-primary showimg'data-toggle='modal' data-target='.bs-example-modal-lg' type='button' onclick='showimg(this)' value='查看'><input type='button' value='×' class='btn del'></div>")
            }else{
                alert("上一项未填！")
            }
        });
        <%--点击删除X按钮时，删除整个上传文件的div块--%>
        $(document).on("click",".del",function(){
            var a = $(this).parent();
            a.remove();
        });

    });
</script>
</body>
</html>
