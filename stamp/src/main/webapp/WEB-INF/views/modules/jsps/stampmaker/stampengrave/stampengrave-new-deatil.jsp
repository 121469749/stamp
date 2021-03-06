<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp" %>
<!DOCTYPE html>
<html lang="zh-cn">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no"/>
    <meta name="renderer" content="webkit">
    <title>已交付印章详情</title>
    <link rel="stylesheet" href="${ctxHtml}/css/bootstrap.min.css">
    <link rel="stylesheet" href="${ctxHtml}/css/font-awesome.min.css">
    <link rel="stylesheet" href="${ctxHtml}/css/myCss.css">
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
        }

        #tbody tr td input {
            text-align: center;
        }

        input {
            width: 100%;
            height: 100%;
            border: none;
            outline: none;
            background: rgba(0, 0, 0, 0);
        }

        th img {
            width: 22px;
            height: 22px;
            display: none;
        }

        .modal-content {
            background: rgba(0, 0, 0, 0);
            border: none;
            box-shadow: none;
            text-align: center;
        }
        .modal-content2 {
            background: rgba(0, 0, 0, 0);
            border: none;
            box-shadow: none;
            text-align: center;
        }
        input[type="text"],input[type="number"]{
            height: 30px;
            line-height: 30px;
            width:65%;
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
        table tr td input{
            border: none;
            background:transparent;
        }
        .baPDF{
            width:880px;
            margin-left:-450px;
        }
        @media (min-width: 992px) {
            .modal-lg {
                width: 900px;
            }
        }
    </style>
    <script>
        //窗口类型(备案登记表PDF)
        var iframeType = 1;

        function downloadimg() {
            $.ajax({
                type: "post",
                url: "${ctx}/stampMakeInfo/download/file?id=" + document.getElementById("simg").value,
                dataType: "json",
                success: function (result) {
                    //图片下载
                    var form = $("<form>");//定义一个form表单
                    form.attr("style", "display:none");
                    form.attr("target", "");
                    form.attr("method", "post");
                    form.attr("action", "${ctx}/stampMakeInfo/download/file");
                    var input1 = $("<input>");
                    input1.attr("type", "hidden");
                    input1.attr("name", "id");
                    input1.attr("value", $("#simg").val());
                    $("body").append(form);//将表单放置在web中
                    form.append(input1);
                    form.submit();//表单提交
                    <%--document.getElementById("download").href = "${ctx}/stampMakeInfo/download/EleModelFile?id="+document.getElementById("simg").value;--%>
//                alert(document.getElementById("download").href);
//                document.getElementById("download").click();
                },
                error: function () {
                    alert("图片不存在");
                }
            })
        }
        <%--点击查看按钮事件--%>
        function seeimg(v, n) {
            <%--正则表达式验证文件格式是否为图片--%>
            var judge = /^(jpg|bmp|gif|ico|pcx|jpeg|tif|png|raw|tga|JPG|BMP|GIF|ICO|PCX|JPEG|TIF|PNG|RAW|TGA)$/;
            var show = false;
            var isFirst = false;
            var i = 0;
            $(".modal-content2").children("img").remove();
            $(".modal-content2").children("a").remove();
            $(".seeimg").attr({
                'data-toggle':"",
                'data-target':""
            })

            <c:forEach items="${attachments}" var="item" varStatus="status" >
            <%--如果文件对应，则显示到模态框--%>
            if ("${item.attachType}" == v) {

                $(".seeimg").attr({
                    'data-toggle':"modal",
                    'data-target':".bs-example-modal-lg"
                })
                show = true;
                if (!judge.test("${item.fileSuffix}")) {
                    $(".modal-content2").append("<a class='btn btn-success btn-lg active' href='${ctx}/stampMakeInfo/download/file?id=" + "${item.id}" + "'>文档" + i + "附件下载</a>");
                } else {
                    $(".modal-content2").append("<img src='/img" + "${item.attachPath}" + "' style='margin-top:10px;width: 100%;height: auto'>");
                }
            }
            </c:forEach>
            <%--如果没有图片，则提示没有上传图片--%>
            if (!show) {
                alert("该资料没有上传图片");
            }

        }
        function showimg(a){
            var isIE = navigator.userAgent.match(/MSIE/)!= null,
                isIE8 = navigator.userAgent.match(/MSIE 8.0/)!= null;
            if(isIE8){
                $(".modalBox").css("display", "block");
                document.getElementById("tabAttachmentImg").style.filter = "progid:DXImageTransform.Microsoft.AlphaImageLoader(sizingMethod='scale',src=\"" + a.name + "\")";
                document.getElementById("tabAttachmentImg").style.width = "90%";
            }
            else{
                $(".modalBox").css("display", "block");
                document.getElementById("tabAttachmentImg").src = a.name;
            }
        }
    </script>
</head>
<body>
<div>
    <div class="panel-head">
        <input id="recordId" value="${stampDeliveryVo.stamp.lastRecord.id}" hidden>
        当前路径：首页 \ 已交付印章 \ 已交付印章详情<font color="red" size="+1">(已进行过企业信息变更)</font></h4>
        <span style="float: right">
            登记时间：<span id="cur-time" style="margin-right: 50px"><fmt:formatDate
                value="${stampDeliveryVo.stamp.createDate}" pattern="yyyy-MM-dd"/></span>
            业务流水号：<span>${stampDeliveryVo.stamp.lastRecord.serialNum}</span>
        </span>
    </div>
    <button class="btn btn-success" onclick="history.go(-1)">返回上一页</button>
    <table class="table table-striped table-bordered">
        <tr>
            <th>企业名称</th>
            <td>
                <textarea  id="companyname"  value="${company.companyName}" readonly style="background:transparent;overflow:hidden;resize: none;border: 0px;" cols="80" rows="1">${company.companyName}</textarea>
            </td>
            <th>企业类型</th>
            <td>${fns:getDictLabel(company.type1,"unitType" ,null )}</td>
        </tr>
        <tr>
            <th>企业统一社会信用代码
            </th>
            <td>${company.soleCode}</td>
            <th>
                企业联系电话
            </th>
            <td>${company.compPhone}</td>
        </tr>
        <tr>
            <th>企业住所</th>
            <td colspan="3">${company.compAddress}</td>
        </tr>
        <tr>
            <th>法人姓名</th>
            <td>${company.legalName}</td>
            <th>法人联系电话</th>
            <td>${company.legalPhone}</td>
        </tr>
        <tr>
            <th>法人证件类型</th>
            <td>
                ${fns:getDictLabel(company.legalCertType,"certificateType" ,null )}
            </td>
            <th>法人证件号码</th>
            <td>${company.legalCertCode}</td>
        </tr>
        <tr>
            <th>经办人姓名</th>
            <td>${mca.agentName}</td>
            <th>经办人联系电话</th>
            <td>${mca.agentPhone}</td>
        </tr>
        <tr>
            <th>经办人证件类型</th>
            <td>
                ${fns:getDictLabel(mca.agentCertType,"certificateType" ,null )}
            </td>
            <th>经办人证件号码</th>
            <td>${mca.agentCertCode}</td>
        </tr>
    </table>
    <table class="table table-striped table-bordered">
        <thead>
        <tr>
            <th>序号</th>
            <th>印章名称</th>
            <th>章型</th>
            <th>章材</th>
            <th>印模</th>
        </tr>
        </thead>
        <tbody id="tbody">
        <tr>
            <td align="center">1</td>
            <td align="center">${stamp.lastRecord.companyName}${fns:getDictLabel(stampDeliveryVo.stamp.stampType, "stampType",null )}
            </td>
            <td align="center">
                ${fns:getDictLabel(stamp.stampShape,"stampShape" ,null )}
            </td>
            <td align="center">${fns:getDictLabel(stamp.stampTexture,"stampTexture",null)}
                <c:if test="${stamp.stampShape == 2}">
                    Ukey
                </c:if>
            </td>
            <td align="center">
                <img src="/img${stamp.eleModel}" alt="">
            </td>
        </tr>
        </tbody>
    </table>
    <table class="table table-striped table-bordered">
        <tr>
            <td rowspan="20" style="width: 10%;line-height:45px;text-align: center"><strong>附<br>件<br>材<br>料</strong>
            </td>
        </tr>
        <%--<c:forEach items="${dir}" var="dict">
            <tr>
                <td>${dict.label}</td>
                <td>
                    <button type="button" onclick="seeimg('${dict.value}','${dict.label}',this)"
                            class="btn btn-primary seeimg" data-toggle="modal" data-target=".bs-example-modal-lg">查看
                    </button>
                </td>
            </tr>
        </c:forEach>--%>
        <c:forEach items="${dir}" var="dict">
            <tr>
                <td>${dict.label}</td>
                <td style="width: 370px;">
                    <div class="box" id="${dict.value}">
                        <c:choose>
                            <c:when test="${stamp.lastRecord.sblsh eq null}">
                                <c:set var="flag" value="0" />
                                <c:forEach items="${attachments}" var="item" varStatus="status">
                                        <c:if test="${item.attachType == dict.value}">
                                            <c:set var="flag" value="1" />
                                            <div class="clearfix" style="position:relative;">
                                                <input type="file" style="display: none">
                                                <button class="btn btn-primary" name="/img${item.attachPath}" data-toggle="modal" data-target=".bs-example-modal-lg" onclick="showimg(this)" type="button">查看</button>
                                                <input style="display: none" type='button' value='×' class='btn delFile' name="${item.id}">
                                            </div>
                                        </c:if>
                                </c:forEach>
                                <c:if test="${flag != '1'}">
                                    <div>（未上传）</div>
                                </c:if>
                            </c:when>
                            <c:otherwise>
                                <div>（多证合一数据）</div>
                            </c:otherwise>
                        </c:choose>
                    </div>
                </td>
            </tr>
        </c:forEach>
    </table>
    <table class="table table-striped table-bordered">
        <tr>
            <td rowspan="20" style="width: 10%;line-height:45px;text-align: center"><strong>交<br>付<br>材<br>料</strong></td>
        </tr>
        <c:if test="${empty stamp.recordPhoto}">
            <tr>
                <td><strong>经办人身份证正面</strong></td>
                <td>
                    <button type="button" class="btn btn-primary" onclick="checkTabAttachment(1)"
                            data-toggle="modal" data-target=".bs-example-modal-lg">查看</button>
                </td>
            </tr>
            <tr>
                <td><strong>经办人身份证反面</strong></td>
                <td>
                    <button type="button" class="btn btn-primary" onclick="checkTabAttachment(2)"
                            data-toggle="modal" data-target=".bs-example-modal-lg">查看</button>
                </td>
            </tr>
            <tr>
                <td><strong>经办人照片</strong></td>
                <td>
                    <button type="button" class="btn btn-primary" onclick="checkTabAttachment(3)"
                            data-toggle="modal" data-target=".bs-example-modal-lg">查看</button>
                </td>
            </tr>
            <tr>
                <td><strong>经办人签名</strong></td>
                <td>
                    <button type="button" class="btn btn-primary" onclick="checkTabAttachment(10)"
                            data-toggle="modal" data-target=".bs-example-modal-lg">查看</button>
                </td>
            </tr>
           <c:if test="${stamp.stampShape eq 1}">
               <tr>
                   <td><strong>备案登记表</strong></td>
                   <td>
                       <button type="button" class="btn btn-primary" onclick="checkTabAttachment(4)"
                               data-toggle="modal" data-target=".bs-example-modal-lg">查看
                       </button>
                   </td>
               </tr>
           </c:if>
            <c:if test="${stamp.stampShape eq 2}">
                <tr>
                    <td><strong>备案登记表PDF</strong></td>
                    <td>
                        <button type="button" class="btn btn-primary" onclick="checkTabAttachmentPDF('${stamp.recordPDF}')"
                                data-toggle="modal">查看
                        </button>
                    </td>
                </tr>
            </c:if>
        </c:if>
        <c:if test="${not empty stamp.recordPhoto}">
            <tr>
                <td>经办人照片</td>
                <td><button type="button" class="btn btn-primary seeimg" data-toggle="modal" data-target=".here">查看</button></td>
            </tr>
            <tr>
                <td>备案登记表</td>
                <td id="btn"><button type="button"  class="btn btn-primary seeimg" data-toggle="modal" data-target=".doc"
                                     onclick="lookImg('${stamp.recordPhoto}')">查看</button></td>
            </tr>
        </c:if>
    </table>
</div>
<%--模态框--%>
<div class="modal fade here" tabindex="-1" role="dialog" aria-labelledby="myLargeModalLabel">
    <div class="modal-dialog modal-lg" role="document">
        <div class="modal-content">
            <div class="modal-header" style="border: 0;padding-right: 10%">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                    <span aria-hidden="true"><img src="${ctxHtml}/images/close.png"></span></button>
            </div>
            <c:forEach items="${attachments}" var="attr">
                <img src="/img${attr.attachPath}" style="width: 90%;height: auto">
            </c:forEach>
        </div>
    </div>
</div>
<div class="modal fade doc" tabindex="-1" role="dialog" aria-labelledby="myLargeModalLabel">
    <div class="modal-dialog modal-lg" role="document">
        <div class="modal-content">
            <div class="modal-header" style="border: 0;padding-right: 10%">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                    <span aria-hidden="true"><img src="${ctxHtml}/images/close.png"></span></button>
            </div>
            <img id="doc"  style="width: 90%;height: auto">
        </div>
    </div>
</div>
<!--模态框-->
<!--TODO-->
<%--    <div class="modal fade bs-example-modal-lg" tabindex="-1" role="dialog" aria-labelledby="myLargeModalLabel">
        <div class="modal-dialog modal-lg" role="document">
            <div class="modal-content2">
                <div class="modal-header" style="border: 0;padding-right: 10%">
                    <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                        <span aria-hidden="true"><img src="${ctxHtml}/images/close.png"></span></button>
                </div>
            </div>
        </div>
    </div>--%>
<!--浏览交付材料-->
<div class="modal fade bs-example-modal-lg" tabindex="-1" role="dialog" aria-labelledby="myLargeModalLabel">
    <div class="modal-dialog modal-lg" role="document">
        <div class="modal-content">
            <div class="modal-header" style="border: 0;padding-right: 10%">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                    <span aria-hidden="true"><img src="${ctxHtml}/images/close.png"></span></button>
            </div>
            <img style="width: 80%" id="tabAttachmentImg" onerror="this.src='${ctxHtml}/images/no-photo.jpg'">
        </div>
    </div>
</div>
<!--浏览备案登记表PDF-->
<div id="btn_browse" class="modal fade baPDF" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true"
     style="width: 100%;height: 100%;left: 450px; top:0px;">
    <div class="modal-header" style="background-color: rgb(0,0,0); filter: alpha(opacity=10);">
        <h3 id="myModalLabel" style="color: white;margin:0px 0px 0px -10px;">备案登记表
            <button type="button" class="close" data-dismiss="modal" style="color:white;opacity: 1.0;">x</button>
        </h3>
    </div>
    <div class="modal-body" style="width: 100%;max-height: 800px; padding: 0px;">
        <iframe id="displayPdfIframe" width="100%" height="780px"></iframe>
    </div>
</div>
<div class="my-mask"></div>
<script src="${ctxHtml}/js/jquery-1.9.1.min.js"></script>
<script src="${ctxHtml}/js/bootstrap.min.js"></script>
<script>
    <%--打印方法--%>
    function printf() {
        $("#p").hide();
        $("#d1").hide();
        $("#d2").hide();
        $("#d3").hide();
        $("#my-btn").hide();
        $("#print").show();
        window.print();
        $("#p").show();
        $("#d1").show();
        $("#d2").show();
        $("#d3").show();
        $("#my-btn").show();
        $("#print").hide();
    }

    /*查询交付材料*/
    function checkTabAttachment(type) {
        switch (type){
            case 1:
            $("#tabAttachmentImg").attr({"src": "https://stamp.ieseals.cn/img/zf/img/${map['tabAttachment1']}"});
                break;
            case 2:
            $("#tabAttachmentImg").attr({"src": "https://stamp.ieseals.cn/img/zf/img/${map['tabAttachment2']}"});
                break;
            case 3:
            $("#tabAttachmentImg").attr({"src": "https://stamp.ieseals.cn/img/zf/img/${map['tabAttachment3']}"});
                break;
            case 4:
            $("#tabAttachmentImg").attr({"src": "https://stamp.ieseals.cn/img/zf/img/${map['tabAttachment4']}"});
                break;
            case 10:
            $("#tabAttachmentImg").attr({"src": "https://stamp.ieseals.cn/img/zf/img/${map['tabAttachment10']}"
                ,"style": "background-color: white;width: 50%"});
                break;
        }
    }
    /*查询交付材料-备案登记表PDF*/
    function checkTabAttachmentPDF(s) {
        var rand = Math.random();
        path = s;

        if(path!=""&&path!=null){
            $('#btn_browse').modal({});
            url1= "/img" + path+"?"+rand;
            url2=encodeURI(url1);
            $('#displayPdfIframe').attr("src",'${ctxStatic}/pdfJs/web/viewer.html?file='+url2);
        }else {
            alert("材料未上传！");
        }
    }

    function lookImg(path) {
//        alert(encodeURI(path+"?"+Math.random()));
        $('#doc').attr("src",'/img'+encodeURI(path+"?"+Math.random()));
    }

    $(function () {
        var arr = new Array();
        var arr = $("#doc").attr("src").split(".");
        var a = arr[1];
        var judge = /^(jpg|bmp|gif|ico|pcx|jpeg|tif|png|raw|tga|JPG|BMP|GIF|ICO|PCX|JPEG|TIF|PNG|RAW|TGA)$/;
        if (!judge.test(a)) {
            $("#btn").empty();
            $("#btn").append("<a class='btn btn-success active' href='" + $("#doc").attr("src") + "' download='备案文档'>文档附件下载</a>");
        }
    })
</script>
</body>
</html>
