<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp" %>
<!DOCTYPE html>
<html lang="zh">
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no"/>
    <meta name="renderer" content="webkit">
    <title>印章管理-启停和转授权</title>
    <link rel="stylesheet" href="${ctxHtml}/css/bootstrap.min.css">
    <link rel="stylesheet" href="${ctxHtml}/css/licence.css">
    <script language="JavaScript" src="${ctxHtml}/js/jquery-1.9.1.min.js"></script>
    <script src="${ctxHtml}/js/bootstrap.min.js"></script>
    <script src="${ctxHtml}/js/My97DatePicker/WdatePicker.js"></script>
    <style>
        table input {
            border: none;
            background: transparent;
        }
    </style>
</head>
<body>
<div class="all-part">
    <h3>印章管理-启停和转授权</h3>
    <%--控件调用标签--%>
    <OBJECT CLASSID="CLSID:D2A20FFC-5207-434A-961E-79FCEA493433"
            ID="SealDraw" STYLE="LEFT:0;TOP:0" width="0" height="0">
    </OBJECT>
    <OBJECT CLASSID="CLSID:12D7ED82-1C9F-4134-A00C-1BE842488F03"
            ID="SealService" STYLE="LEFT:0;TOP:0" width="0" height="0">
    </OBJECT>
    <OBJECT CLASSID="CLSID:73E9563B-8074-4E63-A003-0A7BA6A10CFB"
            ID="SealServiceEx" STYLE="LEFT:0;TOP:0" width="0" height="0">
    </OBJECT>
    <OBJECT CLASSID="CLSID:BAFF8A96-6269-4410-BDBF-EC1E56035928"
            ID="Crpyto" STYLE="LEFT:0;TOP:0" width="0" height="0">
    </OBJECT>
    <c:choose>
        <c:when test="${!empty stampAuthVo.stamp.eleModel}">
            <img class="left-part" src="/img${stampAuthVo.stamp.electron.sealEleModel}" alt="">
        </c:when>
        <c:otherwise>
            <img src="${pageContext.request.contextPath}/static/images/making.png" width="200" height="100" alt=""
                 style="float: left;margin-left: 10px">
        </c:otherwise>
    </c:choose>
    <div class="right-part">
        <table class="table table-bordered" style="width: 80%;margin: 0 auto">
            <tr>
                <td>印章标题：</td>
                <td><input type="text" value="${stampAuthVo.stamp.stampName}" readonly></td>
            </tr>
            <tr>
                <td>允许使用次数：</td>
                <td><input type="text" value="${stampAuthVo.stamp.allowUseNum}" readonly></td>
            </tr>
            <tr>
                <td>有效开始时间：</td>
                <td><input type="text"
                           value="<fmt:formatDate value="${stampAuthVo.stamp.electron.validDateStart}" pattern="yyyy-MM-dd"/>"
                           readonly>

                </td>
            </tr>
            <tr>
                <td>有效结束时间：</td>
                <td><input type="text"
                           value="<fmt:formatDate value="${stampAuthVo.stamp.electron.validDateEnd}" pattern="yyyy-MM-dd"/>"
                           readonly>

                </td>
            </tr>
            <tr>
                <td>制作时间：</td>
                <td><input type="text"
                           value="<fmt:formatDate value="${stampAuthVo.stamp.makeDate}" pattern="yyyy-MM-dd"/>"
                           readonly>

                </td>
            </tr>
            <tr>
                <td>印章状态：</td>
                <td>
                    <input type="radio" name="state" value="启用">启用
                    <input type="radio" name="state" value="停用">停用
                </td>
            </tr>

        </table>
        <button class="btn btn-default" style="width:10%;margin-left: 45%;margin-top: 20px" onclick="changeState()">提交
        </button>
    </div>
    <div style="clear: both;"></div>
</div>
<div class="all-part">
    <div class="form-group">
        <label style="color: black">用户列表:</label>
        <button class="btn btn-default showimgs" data-toggle="modal" data-target=".bs-example-modal-lg"
                onclick="showimg(this)">新增用户
        </button>
    </div>
    <table class="table table-bordered">
        <tr>
            <th>工号</th>
            <th>登录名</th>
            <th>用户真实姓名</th>
            <th>授权状态</th>
            <th>授权时间</th>
            <th>取消授权时间</th>
            <th>操作</th>
        </tr>
        <c:forEach items="${stampAuthVo.page.list}" var="user">
            <tr>
                <td>${user.user.no}</td>
                <td>${user.user.loginName}</td>
                <td>${user.user.name}</td>
                <td>
                    <c:choose>
                        <c:when test="${user.stampAuthState == null || user.stampAuthState == ''}">
                            没有权限
                        </c:when>
                        <c:otherwise>
                            ${user.stampAuthState.value}
                        </c:otherwise>
                    </c:choose>
                </td>
                <td>
                    <fmt:formatDate value="${user.startTime}" pattern="yyyy-MM-dd HH:mm:ss"/>
                </td>
                <td>
                    <fmt:formatDate value="${user.endTime}" pattern="yyyy-MM-dd HH:mm:ss"/>
                </td>
                <td>
                    <c:choose>
                        <c:when test="${user.stampAuthState.value == '有权限'}">
                            <button class="btn btn-default"
                                    onclick="setAuthority('${user.user.id}', '0','${user.user.loginName}')">取消授权
                            </button>
                        </c:when>
                        <c:when test="${user.stampAuthState.value == '没有权限' || user.stampAuthState == null || user.stampAuthState == ''}">
                            <button class="btn btn-default" data-toggle="modal" data-target=".bs-example-modal-sm"
                                    onclick="setValue('${user.user.id}','${user.user.loginName}')">授权
                            </button>
                        </c:when>
                    </c:choose>
                    <button class="btn btn-default" onclick="delUser('${user.user.id}')">删除</button>
                </td>
            </tr>
        </c:forEach>
    </table>
    <button class="btn btn-default" onclick="history.go(-1)">返回</button>
</div>
<!--模态框-->
<div class="modal fade bs-example-modal-lg" tabindex="-1" role="dialog" aria-labelledby="myLargeModalLabel">
    <div class="modal-dialog modal-lg" role="document">
        <div class="modal-content">
            <div class="modal-header" style="border: 0;padding-right: 10%">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                    <span aria-hidden="true"><img src="${ctxHtml}/images/close.png"></span></button>
            </div>
            <c:choose>
                <c:when test="${fn:length(noAuthUser)>0}">
                <table class="table table-bordered table-hover" style="width: 90%;margin: 10px auto">
                    <tbody>
                    <tr>
                        <th>工号</th>
                        <th>姓名</th>
                        <th>操作</th>
                    </tr>
                    <c:forEach items="${noAuthUser}" var="user">
                        <tr>
                            <td>${user.no}</td>
                            <td>${user.name}</td>
                            <td>
                                <button onclick="addUser('${user.id}')">添加</button>
                            </td>
                        </tr>
                    </c:forEach>
                    </tbody>
                </table>
                </c:when>
                <c:when test="${fn:length(noAuthUser)==0}">
                  <div style="align-content: center">
                      <h2 style="color: red;align-content: center">目前没有新用户，请在“用户管理”添加新用户！</h2>
                  </div>
                </c:when>
            </c:choose>
        </div>
    </div>
</div>
<%--转授权窗口--%>
<div class="modal fade bs-example-modal-sm" tabindex="-1" role="dialog" aria-labelledby="myLargeModalLabel">
    <div class="modal-dialog modal-lg" role="document">
        <div class="modal-content">
            <div class="modal-header" style="border: 0;padding-right: 10%">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                    <span aria-hidden="true"><img src="${ctxHtml}/images/close.png"></span></button>
            </div>
            <input type="text" id="useId" style="display: none">
            <input type="text" id="userLogin" style="display: none">
            <table class="table table-bordered" style="width:80%;margin: 5px auto">
                <tr>
                    <td>使用次数：</td>
                    <td>
                        <input type="number" id="useTime" style="border: 1px solid #000;height: 30px" value="-1"/>(-1为无限次)
                    </td>
                </tr>
                <tr>
                    <td>使用开始时间：</td>
                    <td>
                        <input id="day1" style="width: 200px;height: 30px" class="Wdate" type="text"
                               onFocus="WdatePicker({startDate:'%y-%M-%D',maxDate:'#F{$dp.$D(\'day2\')}',dateFmt:'yyyy-MM-dd',minDate:'%y-%M-{%d}',alwaysUseStartDate:true})"/>
                    </td>
                </tr>
                <tr>
                    <td>使用结束时间：</td>
                    <td>
                        <input id="day2" style="width: 200px;height: 30px" class="Wdate" type="text"
                               onFocus="WdatePicker({startDate:'%y-%M-%D',minDate:'#F{$dp.$D(\'day1\')}',dateFmt:'yyyy-MM-dd'})"/>
                    </td>
                </tr>
                <tr>
                    <td colspan="2" style="text-align: center">
                        <button class="btn btn-info" onclick="setSubmit()">授权</button>
                    </td>
                </tr>
            </table>
        </div>
    </div>
</div>
</body>
</html>

<script>
    <%--转授权模态框方法--%>
    function setValue(useId, userLogin) {
        document.getElementById("userLogin").value = userLogin;
        document.getElementById("useId").value = useId;
    }
    //    转授权的ajax提交
    var objSeal = document.getElementById("SealServiceEx");
    function setSubmit() {
        //获取当前网址，如： http://localhost:8083/uimcardprj/share/meun.jsp
        var curWwwPath = window.document.location.href;
        //获取主机地址之后的目录，如： uimcardprj/share/meun.jsp
        var pathName = window.document.location.pathname;
        var pos = curWwwPath.indexOf(pathName);
        //获取主机地址，如： http://localhost:8083
        var localhostPaht = curWwwPath.substring(0, pos);
        var msg, pass;
        var name = "${loginUser.id}";
        //授权人名称和id
        var loginName = document.getElementById("userLogin").value;
        var userId = document.getElementById("useId").value;
        var allowUse = parseInt(document.getElementById("useTime").value);//允许使用次数
        var a = new Array();
        a = "<fmt:formatDate value="${stampAuthVo.stamp.electron.validDateStart}" pattern="yyyy-MM-dd"/>".split("-");
        var stampStar = new Date(a[0],a[1],a[2]);//印章本身开始日期
        a = "<fmt:formatDate value="${stampAuthVo.stamp.electron.validDateEnd}" pattern="yyyy-MM-dd"/>".split("-");
        var stampEnd = new Date(a[0],a[1],a[2]);//印章本身结束日期
        a = document.getElementById("day1").value.split("-");
        var userStar = new Date(a[0],a[1],a[2]);//被授权开始日期
        a = document.getElementById("day2").value.split("-");
        var userEnd = new Date(a[0],a[1],a[2]);//被授权结束日期
        <%--判断日期正确性--%>
        if (!allowUse && allowUse != -1) {
            alert("请输入正确的授权次数！");
            return false;
        }else if(userEnd.getTime() == userStar.getTime() || userStar.getTime() < stampStar.getTime() || userStar.getTime() > stampEnd.getTime()
                || userEnd.getTime() > stampEnd.getTime() || userEnd.getTime() < stampStar.getTime()){
            alert("日期有误！");
            return false;
        }
        msg = "确定要授权吗？";
        if (!confirm(msg)) {
            return false;
        }
        try {
            alert("授权证书登录名:"+name+"被授权证书名称(id):"+userId);
            console.log(localhostPaht+"/img${stampAuthVo.stamp.electron.sealPath}");
            alert("seal文件地址"+localhostPaht+"/img${stampAuthVo.stamp.electron.sealPath}");
            pass = prompt("输入用章管理员key密码");
            objSeal.SOF_Login(name, pass); // 用章管理员登录
        } catch (e) {
            alert("登录失败！请确认key是否插入，密码是否正确。");
            objSeal.SOF_Logout(name);
            return false;
        }
        try {
            //objSeal.OES_BeginMakeSeal("${stampAuthVo.stamp.id}");    // 在每刻制一个新章前必须调用，uuid由平台生成传给组件，作为印章唯一标识
            objSeal.OES_ReAuthorization(localhostPaht+"/img${stampAuthVo.stamp.electron.sealPath}", name, allowUse); // 此步之前，应已插入用户B的Key
        } catch (e) {
            alert("转授权失败！请确认转授权人key是否插入。");
            return false;
        } finally {
            //objSeal.OES_EndMakeSeal();// 完成一次刻制时必须调用，显式结束，否则会出bug
            objSeal.SOF_Logout(name);
        }
        $.ajax({
            type: "POST",
            url: "${ctx}/useCompanyStampAction/changAuthState",
            data: {
                "stamp.id": "${stampAuthVo.stamp.id}",
                "stamp.electron.allowUse": parseInt(allowUse),
                "startTime":document.getElementById("day1").value,
                "endTime":document.getElementById("day2").value,
                "user.id": userId.toString(),
                "state": '1'
            },
            dataType: "JSON",
            timeout: 5000,
            success: function (data) {
                if (data.code == 200) {
                    alert(data.message);
                    location.reload(true);
                } else {
                    alert(data.message);
                }
            },
            error: function (XMLHttpRequest, textStatus) {
                if (textStatus == "timeout") {
                    alert("请求超时");
                } else {
                    alert("请求错误!!!");
                }
            }
        });
    }
    function showimg(a) {
        var isIE = navigator.userAgent.match(/MSIE/) != null,
            isIE8 = navigator.userAgent.match(/MSIE 8.0/) != null;
        if (isIE8) {
            $(".modalBox").css("display", "block");
            document.getElementById("modalimg").style.filter = "progid:DXImageTransform.Microsoft.AlphaImageLoader(sizingMethod='scale',src=\"" + a.name + "\")";
            document.getElementById("modalimg").style.width = "90%";
        }
        else {
            $(".modalBox").css("display", "block");
            document.getElementById("modalimg").src = a.name;
        }
    }
    $(document).ready(function () {

        if (${stampAuthVo.stamp.useState.value == '启用'}) {
            $("input[name='state']").get(0).checked = true;
        } else {
            $("input[name='state']").get(1).checked = true;
        }
    });
    $(document).ready(function () {
        if (${stampAuthVo.stamp.useState.value == '启用'}) {
            $("input[name='state']").get(0).checked = true;
        } else {
            $("input[name='state']").get(1).checked = true;
        }
    });
    /*    //图片展示
     $(".showimgs").click(function (e) {
     $(".modalBox").css("display", "block");

     })
     $("#canbtn").click(function () {
     $(".modalBox").css("display", "none");

     })*/

    //点击模态框中的添加按钮
    function addUser(userId) {

        $.ajax({
            type: "POST",
            url: "${ctx}/useCompanyStampAction/addUserToAuth",
            data: {"stamp.id": "${stampAuthVo.stamp.id}", "user.id": userId.toString()},
            dataType: "JSON",
            timeout: 5000,
            success: function (data) {
                if (data.code == 200) {
                    alert(data.message);
                    $(".modalBox").css("display", "none");
                    location.reload(true);
                } else {
                    alert(data.message);
                }
            },
            error: function (XMLHttpRequest, textStatus) {
                if (textStatus == "timeout") {
                    alert("出错了！请联系管理员！");
                } else {
                    alert("出错了！请联系管理员！");
                }
            }
        });
    }

    function changeState() {
        var state;
        if ($('input:radio:checked').val() == '启用') {
            state = 1;
        } else {
            state = 0;
        }

        $.ajax({
            type: "POST",
            url: "${ctx}/useCompanyStampAction/changeStampState",
            data: {"id": "${stampAuthVo.stamp.id}", "state": state.toString()},
            dataType: "JSON",
            timeout: 5000,
            success: function (data) {

                if (data.code == 200) {
                    alert(data.message);
                    location.reload(true);
                } else {
                    alert(data.message);
                }
            },
            error: function (XMLHttpRequest, textStatus) {
                if (textStatus == "timeout") {
                    alert("请求超时");
                } else {
                    alert("请求错误!!!");
                }
            }
        });
    }
    function setAuthority(userId, authority, loginName) {
        msg = "确定要取消授权吗";
        if (!confirm(msg)) {
            return false;
        }
        $.ajax({
            type: "POST",
            url: "${ctx}/useCompanyStampAction/changAuthState",
            data: {"stamp.id": "${stampAuthVo.stamp.id}", "user.id": userId.toString(), "state": authority},
            dataType: "JSON",
            timeout: 5000,
            success: function (data) {
                if (data.code == 200) {
                    alert(data.message);
                    location.reload(true);
                } else {
                    alert(data.message);
                }
            },
            error: function (XMLHttpRequest, textStatus) {
                if (textStatus == "timeout") {
                    alert("请求超时");
                } else {
                    alert("请求错误!!!");
                }
            }
        });
    }
    <%--$.ajax({--%>
    <%--type: "POST",--%>
    <%--url: "${ctx}/useCompanyStampAction/changAuthState",--%>
    <%--data: {"stamp.id": "${stampAuthVo.stamp.id}","stamp.electron.allowUse": parseInt(allowUse), "user.id": userId.toString(), "state": authority},--%>
    <%--dataType: "JSON",--%>
    <%--timeout: 5000,--%>
    <%--success: function (data) {--%>
    <%--if (data.code == 200) {--%>
    <%--alert(data.message);--%>
    <%--location.reload(true);--%>
    <%--} else {--%>
    <%--alert(data.message);--%>
    <%--}--%>
    <%--},--%>
    <%--error: function (XMLHttpRequest, textStatus) {--%>
    <%--if (textStatus == "timeout") {--%>
    <%--alert("请求超时");--%>
    <%--} else {--%>
    <%--alert("请求错误!!!");--%>
    <%--}--%>
    <%--}--%>
    <%--});--%>


    function delUser(userId) {
        var msg = "确定要删除吗？";
        if (confirm(msg) == false) {
            return false;
        }
        $.ajax({
            type: "POST",
            url: "${ctx}/useCompanyStampAction/delUserFromAuth",
            data: {"user.id": userId.toString(), "stamp.id": "${stampAuthVo.stamp.id}"},
            dataType: "JSON",
            timeout: 5000,
            success: function (data) {

                if (data.code == 200) {
                    alert(data.message);
                    location.reload(true);
                } else {
                    alert(data.message);
                }
            },
            error: function (XMLHttpRequest, textStatus) {
                if (textStatus == "timeout") {
                    alert("请求超时");
                } else {
                    alert("请求错误!!!");
                }
            }
        });
    }
</script>