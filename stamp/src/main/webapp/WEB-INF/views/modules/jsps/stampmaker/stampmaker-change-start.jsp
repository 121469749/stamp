<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp" %>
<!DOCTYPE html>
<html lang="zh">
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no"/>
    <meta name="renderer" content="webkit">
    <title>刻章点-变更申请</title>
    <link rel="stylesheet" href="${ctxHtml}/css/bootstrap.min.css">
    <link rel="stylesheet" href="${ctxHtml}/css/licence.css">
    <link rel="stylesheet" href="${ctxHtml}/css/font-awesome.min.css">
    <link rel="stylesheet" href="${ctxHtml}/css/myCss.css">
    <script language="JavaScript" src="${ctxHtml}/js/jquery-1.9.1.min.js"></script>
    <script src="${ctxHtml}/js/bootstrap.min.js"></script>
    <%--<script src="${ctxHtml}/js/formchecked.js"></script>--%>
    <script type="text/javascript" src="${ctxHtml}/js/jquery-form.js"></script>
    <script src="${ctxHtml}/js/stampengravechecked.js?id=${fns:getConfig('js.version')}"></script>
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
        .specialtext{
            width: 100%;
            height: 30px;
            border: 0;
            background: transparent;

        }
        input[type="text"],input[type="number"]{
            height: 30px;
            line-height: 30px;
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
        $(function() {

            $("#mainform").ajaxForm({
                dataType: 'json',
                type: "post",

                url: "${ctx}/makeStampAction/submit",
                success: function (result) {
                    //跳转
                    if (result.code == "200") {
                        alert("成功提交");
                        window.location= "${ctx}/makeStampAction/licenseForm?workType=OPEN";
                    }

                    else if (result.code == "500") {
                        //开放按钮


                        alert(result.message.toString());
                        document.getElementById("submitbtn2").removeAttribute('disabled');
                    }
                    else{
                        //开放按钮

                        alert(result.message.toString());
                        document.getElementById("submitbtn2").removeAttribute('disabled');
                    }

                },
                error: function () {

                    //显示错误信息
                    alert("出错了！请联系管理员！");
                    document.getElementById("submitbtn2").removeAttribute('disabled');

                }
            });
        });
    </script>
</head>
<body>
<div class="table-double">
    <!---右边部分-->
    <div class="table-right">
        <p> ${result == null ? "":  "上次变更申请审核结果为"} ${result == null ? "":  result.checkState.getValue()}
            <c:if test="${result != null}">
                <a href="${ctx}/makeStampPage/changefinish" target="_blank">查看上次申请的信息详情</a>
            </c:if>

        </p>
        <form:form id="mainform" modelAttribute="licenseApplyDTO" action="${ctx}/makeStampAction/submit" method="post" class="form-horizontal" enctype="multipart/form-data">
            <input  name="licence.workType" type="text" id="legalName" class="form-control hide" value="CHANGE"/>
            <div class="mod" style="display: none">开办流程说明</div>
            <div class="mod">
                <h3>变更申请</h3>
                <p>变更申请理由：</p>
                <form:textarea class="form-control" style="height:100px" path="licence.workReason"  type="text" id="reason" ></form:textarea>

                <table class="table maintable table-striped table-bordered">
                    <tbody>
                    <tr>
                        <td>企业名称</td>
                        <td>
                            <input value="${company.companyName}" id="enterprise_name" type="text" class="specialtext" readonly/>
                        </td>
                        <td>经济性质</td>
                        <td colspan="3">

                            <form:select path="licence.busType" class="form-control">
                                <form:options items="${fns:getDictList('busType')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
                            </form:select>
                            <span></span>
                        </td>
                    </tr>

                    <tr>
                        <td>法人姓名</td>
                        <td>
                            <form:input  path="licence.legalName" type="text" id="legalName2" class="form-control" placeholder="输入法人姓名"/>
                        </td>
                        <td>法人身份证号码</td>
                        <td>
                            <form:input  path="licence.legalCertCode" type="text" id="mainperson-id" class="form-control" placeholder="输入法人身份证号"/>
                        </td>
                        <td>法人联系电话</td>
                        <td>
                            <form:input  path="licence.legalPhone" type="number" id="mainperson-phone" class="form-control" placeholder="输入法人联系电话"/>
                        </td>
                    </tr>
                    <tr>
                        <td>治安负责人姓名</td>
                        <td>
                            <form:input  path="licence.policeName" type="text" id="securityperson" class="form-control" placeholder="输入治安负责人"/>
                        </td>
                        <td>负责人身份证号码</td>
                        <td>
                            <form:input  path="licence.policeIdCode" type="text" id="securityperson-id" class="form-control" placeholder="输入负责人身份证号"/>
                        </td>
                        <td>负责人联系电话</td>
                        <td>
                            <form:input  path="licence.policePhone" type="number" id="securityperson-phone" class="form-control" placeholder="输入治安负责人联系电话"/>
                        </td>
                    </tr>
                    <tr>
                        <td>企业总人数</td>
                        <td colspan="2">
                            <form:input  path="licence.headCount" type="number" id="allpeople" class="form-control" placeholder="输入企业总人数"/>
                        </td>
                        <td>其中特业从业人数</td>
                        <td colspan="2">
                            <form:input  path="licence.specialCount" type="number" id="specialpeople" class="form-control" placeholder="输入特业从业人数"/>
                        </td>
                    </tr>
                    <tr>
                        <td>企业统一码</td>
                        <td colspan="5">
                            <input value="${company.soleCode}" type="text" id="unifiedcode" class="specialtext" readonly/>
                        </td>
                    </tr>
                    </tbody>
                </table>

                <p class="prompt">
                    请上传以下附件，都是必须上传的，不然审批不通过
                </p>
                <!--文件上传及照片预览--->
                <table class="table table-striped table-bordered">
                    <tr>
                        <td rowspan="20" style="width: 10%;line-height:45px;text-align: center"><strong>附<br>件<br>材<br>料<br>上<br>传</strong></td>
                    </tr>
                    <c:forEach items="${dir}" var="dict">
                        <tr>
                            <td>${dict.label}</td>
                            <td style="width: 370px;">
                                <div class="box">
                                    <div class="clearfix" style="position:relative;">
                                        <input type="text" value="${dict.value}" name="fileType" style="display: none">
                                        <input type="button" class="btn" value="选择文件">
                                        <input type="text"  readonly style="max-width: 150px">
                                        <input class='upload' name="attachment" type='file' accept="image/jpeg,image/png,image/bmp">
                                        <input style="display: none" class="btn btn-primary showimg" data-toggle='modal' data-target='.bs-example-modal-lg' type="button" onclick="showimg(this)"  value="查看">
                                    </div>
                                    <input class="addhtml btn" type="button" style="margin-top: 2px" value="+">
                                </div>
                            </td>
                        </tr>
                    </c:forEach>
                </table>
                <button type="button"  id="submitbtn2"  class="btn btn-primary" style="width: 10%;margin-left: 40%">提交</button>
            </div>
            <div class="mod" style="display: none">网上咨询</div>
        </form:form>
    </div>
</div>
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
<div style="clear: both"></div>
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
    $(function(){
        <%--上传文件选择完文件后--%>
        $(document).on("change","input[type='file']",function(e){
            var files = $(e.target);
            var text = $(e.target).prev();
            var showimg = $(e.target).next();
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
                $(this).before("<div class='clearfix' style='position:relative;'><input type='text' name='fileType' value='"+ textval +"' style='display:none'><input type='button' class='btn' value='选择文件'><input type='text' style='margin-left: 4px;max-width: 150px;height: 30px;line-height: 30px' readonly> <input class='upload' name='attachment' type='file'> <input style='display: none' class='btn btn-primary showimg'data-toggle='modal' data-target='.bs-example-modal-lg' type='button' onclick='showimg(this)' value='查看'><input type='button' value='×' class='btn del'></div>")
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

<script>
    $(function () {
        $(".table-left ul li").click(function () {
            $(this).addClass("select").siblings().removeClass("select");
            var index = $(this).index();
            $(".mod").eq(index).show().siblings().hide();
        })
    })
</script>
</body>
</html>