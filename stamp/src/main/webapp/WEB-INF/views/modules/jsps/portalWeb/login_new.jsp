<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp" %>
<!DOCTYPE html>
<html>
<head lang="zh">
    <meta charset="UTF-8">
    <!-- ie渲染-->
    <meta http-equiv="X-UA-Compatible" content="IE=Edge，chrome=1">
    <!-- 包含头部信息用于适应不同设备 -->
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <!-- 包含 bootstrap 样式表 -->
    <!--<link rel="stylesheet" href="https://apps.bdimg.com/libs/bootstrap/3.2.0/css/bootstrap.min.css">-->
    <%--<link rel="stylesheet" type="text/css" href="${ctxHtml}/css/iview.css">--%>
    <link rel="stylesheet" type="text/css" href="${ctxHtml}/css/login_new.css?id=14">
    <script src="${ctxHtml}/js/jquery-1.9.1.min.js"></script>
    <!--<script src="http://cdn.static.runoob.com/libs/bootstrap/3.3.7/js/bootstrap.min.js"></script>-->
    <script src="${ctxHtml}/js/page_new.js?id=4"></script>
    <script src="${ctxStatic}/jquery-validation/1.11.1/jquery.validate.js"></script>
</head>
<body>
<div id="Layer1" style="position:absolute; left:0px; top:0px; width:100%; height:100%;position:fixed">
    <img src="${ctxHtml}/images/loginbg_new.png" width="100%" height="100%"/>
</div>
<div class="background">
    <div id="mainContent" class="mainContent">
        <div class="sealSystem">
            <div id="sealProblem" class="sealProblem">
                <div class="sealTitleTable">
                    <div class="sealTitleAndLogo">
                        <img class="logo" src="${ctxHtml}/images/logoAndTitle_new.png"/>
                    </div>
                </div>
                <div id="sealProblemContent" class="sealProblemContent">
                    <div id="sealProblemContentDiv" class="sealProblemContentDiv">
                        <div class="sealProblemList" id="sealProblemList">
                            <ul id="listContent" class="listContent">
                            </ul>
                        </div>
                        <div class="sealProblemLineOut">
                            <div class="line"></div>
                        </div>
                        <div class="sealProblemExplain">
                            <div id="sealExplainContent" class="sealExplainContent">
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <div class="login">
            <div class="loginTitleTable">
                <div class="loginTitle">
                    <p id="loginTitleText" class="loginTitleText"></p>
                </div>
            </div>
            <div id="errorLoginDiv" class="errorLoginDiv" style="display:${empty message ? 'none' : 'block'}">
                <div id="errorLogin" class="errorLogin">${message}</div>
            </div>

            <form id="loginForm" method="post">
                <div class="loginInputArea">
                    <div class="loginInputBorder">
                        <label for="username" id="usernameInputLabel" class="inputLabel usernameLabel">用户名</label>
                        <input id="username" type="text" name="username" class="LoginInput"
                               placeholder="用户名" maxlength="20"/>
                        <div class="inputSplitLine"></div>
                        <label for="password" id="passwordInputLabel" class="inputLabel passwordLabel">密码</label>
                        <input id="password" type="password" name="password" class="LoginInput"
                               placeholder="密码" maxlength="20" autocomplete="off"/>
                        <input id='chosenrole' class="hideRole" type="radio" checked="checked" name="chosenrole"/>
                    </div>
                    <div class="verificationCodeClass" style="display: ${isValidateCodeLogin?'inline-block':'none'}">
                        <label for="verification" id="verificationInputLabel"
                               class="inputLabel verificationLabel">请输入验证码</label>
                        <input id="verification" type="text" name="validateCode" class="verificationCodeInput"
                               placeholder="验证码" maxlength="4" required/>
                        <img id="verificationCodeImg" class="verificationCodeImg"
                             src="${pageContext.request.contextPath}/servlet/validateCodeServlet">
                    </div>
                    <div class="rememberMe">
                        <input name="rememberMe" id="rememberMeCheckbox" type="checkbox"
                               class="rememberMeCheckbox changeHand"/>
                        <label for="rememberMeCheckbox" id="rememberMeLabel" title="记住密码"
                               class="rememberMeLabelFalse changeHand">记住我(公共场合慎用)</label>
                    </div>
                    <div id="loginButton">
                        <button id="passwordLogin" class="passwordLogin changeHand" type="submit">密码登录</button>
                        <button id="certificateLogin" class="certificateLogin changeHand" type="submit">证书登录</button>
                    </div>
                </div>
            </form>

            <%--增加用章单位的忘记密码的显示框--%>
            <div id="forgetPasswordForm" style="display: none;z-index: 1">
                <div class="loginInputArea">
                    <div class="loginInputBorder">
                        <div id="forgetPasswordPage" >
                            <label class="inputLabel usernameLabel">请输入用户名</label>
                            <input class="LoginInput" id="phoneNumber" type="text" placeholder="请输入用户名"/><br/>
                            <div class="inputSplitLine"></div>
                            <div class="forgetCodeDiv">
                            <label class="inputLabel usernameLabel">请输入验证码</label>
                            <input id="vertifyNumber" class="forgetInput" type="text" placeholder="请输入验证码"/>
                            <button class="forgetPasswordBtn changeHand" onclick="sentMessage()">点击获取验证码</button>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="reset">
                    <button onclick="checkVertifyNumber(this)" class="forgetPasswordBtn changeHand resetBtn">提交重置</button>
                </div>
            </div>

            <%--增加用章单位的忘记密码按钮--%>
            <div id="forgetPassword" class="forgetPassword changeHand" style="display: none;text-align: center">
                <p onclick="changePage()">忘记密码</p>
            </div>
            <div id="goLogin" class="forgetPassword changeHand" style="display: none;text-align: center">
                <p onclick="goLogin()">重新登录</p>
            </div>
            <div class="loginBottom changeHand">
                <p id="ShowSearch">查询印章</p>
                <p style="cursor: text;color: black">Copyright © 2014-2018 - Powered By RC</p>
            </div>
        </div>
    </div>
    <div class="contactUs">
        <div class="QRCode changeHand" id="QRCode" title="联系微信">
            <img src="${ctxHtml}/images/QRCode_new.png" class="contactUsSingle"/>
        </div>
        <div class="QQ changeHand" id="QQ" title="联系QQ">
            <img src="${ctxHtml}/images/QQ_new.png" class="contactUsSingle" onclick="connectToQQ()"/>
        </div>
    </div>
    <div class="hideQRCode" id="QRCodeInfo">
        <div class="WX">
            <span>微信</span>
        </div>
        <img class="WXQRCode" src="${ctxHtml}/images/rcQRcode.jpg"/>
    </div>
    <div id="Search" class="Search">
        <div class="SearchCenter">
            <div class="SearchDiv">
                <div class="SearchContent">
                    <div class="SearchCondition">
                        <form class="SearchForm" id="searchForm"  method="post"
                              onsubmit="return FormSearch()">
                            <%--<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>--%>
                            <%--<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>--%>
                            <label class="SearchLabel">公司名称:</label>
                            <input id="companyName" name="companyName" class="SearchInput"/>
                            <label class="SearchLabel">印章名称:</label>
                            <input name="stampName" class="SearchInput"/>
                            <label class="SearchLabel">印章编码:</label>
                            <input name="stampCode" class="SearchInput"/>
                            <label class="SearchLabel">印章类型:</label>
                            <select id="SearchSelect" name="stampShape" class="SearchSelect">
                                <option value=""></option>
                                <option value="1">物理印章</option>
                                <option value="2">电子印章</option>
                            </select>
                            <button type="submit" class="SearchButton">搜索</button>
                        </form>
                    </div>
                    <div class="SearchSplitLine">
                    </div>
                    <div id="SearchResult" class="SearchResult">
                    </div>
                </div>
                <div id="SearchCancel" class="SearchCancel">
                    <img class="SearchCancelImg" src="${ctxHtml}/images/close-white_new.png">
                </div>
            </div>

        </div>
    </div>
</div>
</body>
<script>
/*忘记密码*/
    /*跳转页面*/
    function changePage() {
        document.getElementById("phoneNumber").value = "";
        document.getElementById("vertifyNumber").value = "";
        $("#loginForm").css("display","none");
//        $("#forgetPasswordPage").css("display","");
        $("#forgetPasswordForm").css("display","");
        $("#goLogin").css("display","");
        $("#forgetPassword").css("display","none");
    }
    /*跳回登录界面*/
    function goLogin() {
        $("#forgetPassword").css("display","");
        $("#loginForm").css("display","");
        $("#forgetPasswordForm").css("display","none");
        $("#goLogin").css("display","none");
    }
    /*发送信息*/
    function sentMessage() {
        if($("#phoneNumber").val() == ''){
            alert("请输入手机号码");
        }
        var phoneNumber = $("#phoneNumber").val();
        <%--window.location = "${ctxFront}/forgetPassword";--%>
        $.ajax({
            type: "POST",
            url: "/sentMessage?phoneNumber=" + phoneNumber,
            dataType: "JSON",
            success: function(result) {
                alert(result.message);
            },
            error: function(result){
                alert("出现错误，请联系管理员，错误代码为,erro:" + result.message);
            }
        });
    }
    /*检查验证码*/
    function checkVertifyNumber(el) {
        //alert($("#vertifyNumber").val());
        var phoneNumber = $("#phoneNumber").val();
        var vertifyNumber = $("#vertifyNumber").val();
        $.ajax({
            type: "POST",
            url: "/vertify?vertifyNumber=" + vertifyNumber + "&phoneNumber=" + phoneNumber,
            dataType: "JSON",
            success: function(result) {
                alert(result.message);
                if(result.code == 200) {
                    goLogin();
                }
            },
            error: function(result){
                alert("出现错误，请联系管理员，错误代码为,erro:" + result.message);
            }
        });
    }


    var browser = navigator.appName
    var b_version = navigator.appVersion
    var version = b_version.split(";");
    /*解决火狐undefined问题*/
    var trim_Version = ""
    if (version[1]) {
        trim_Version = version[1].replace(/[ ]/g, "");
    }
    if (browser == "Microsoft Internet Explorer" && (trim_Version == "MSIE6.0" || trim_Version == "MSIE7.0" || trim_Version == "MSIE8.0")) {

        $('#mainContent').removeClass().addClass('mainContentIe8')

        $('#sealProblem').removeClass().addClass('sealProblemIe8')

        $('#sealProblemContent').removeClass().addClass('sealProblemContentIe8')

        $('#sealProblemContentDiv').removeClass().addClass('sealProblemContentDivIe8')

        $('#certificateLogin').removeClass().addClass('certificateLoginIe8')


        $('#usernameInputLabel').css('display', 'block')
        $('#passwordInputLabel').css('display', 'block')
        $('#verificationInputLabel').css('display', 'block')

        $('#username').bind("input propertychange", function () {
            if ($(this).val() != '') {
                $('#usernameInputLabel').css('display', 'none')
            } else {
                $('#usernameInputLabel').css('display', 'block')
            }
        });

        $('#password').bind("input propertychange", function () {
            if ($(this).val() != '') {
                $('#passwordInputLabel').css('display', 'none')
            } else {
                $('#passwordInputLabel').css('display', 'block')
            }
        });

        $('#verification').bind("input propertychange", function () {
            if ($(this).val() != '') {
                $('#verificationInputLabel').css('display', 'none')
            } else {
                $('#verificationInputLabel').css('display', 'block')
            }
        });


        $('#Search').removeClass().addClass('SearchIe8')
        $('#SearchCancel').removeClass().addClass('SearchCancelIe8')
        $('#SearchSelect').removeClass().addClass('SearchSelectIe8')

    } else if (browser == "Microsoft Internet Explorer" && (trim_Version == "MSIE9.0")) {

        $('#usernameInputLabel').css('display', 'block')
        $('#passwordInputLabel').css('display', 'block')
        $('#verificationInputLabel').css('display', 'block')

        $('#username').bind("input onchange", function () {
            if ($(this).val() != '') {
                $('#usernameInputLabel').css('display', 'none')
            } else {
                $('#usernameInputLabel').css('display', 'block')
            }
        });

        $('#password').bind("input propertychange", function () {
            if ($(this).val() != '') {
                $('#passwordInputLabel').css('display', 'none')
            } else {
                $('#passwordInputLabel').css('display', 'block')
            }
        });

        $('#verification').bind("input propertychange", function () {
            if ($(this).val() != '') {
                $('#verificationInputLabel').css('display', 'none')
            } else {
                $('#verificationInputLabel').css('display', 'block')
            }
        });

    }



    /**
     * 获取url末尾(修改登录From的action)
     */
    var urlAction = window.location.pathname
    urlAction = urlAction.substring(1, urlAction.length)
    var urlFirstLength = urlAction.search(/\//)
    if (urlFirstLength != -1) {
        urlAction = urlAction.substring(urlFirstLength + 1, urlAction.length)
    }
    var urlEffectLength = urlAction.search(/\W/)
    if (urlEffectLength != -1) {
        urlAction = urlAction.substring(0, urlEffectLength)
    }

    $('#loginForm').attr('action', urlAction)

    /**
     * 需要修改的登录角色value(可以根据url判断)
     */
    var titleName
    var roleValue
    switch (urlAction) {
        case 'makecomlogin':
            titleName = '刻章点登录'
            roleValue = '3'
            break;
        case 'policelogin':
            titleName = '公安部门登录'
            roleValue = '5'
            break;
        case 'usecomlogin':
            titleName = '企业单位登录'
            roleValue = '4'
            $("#forgetPassword").css("display","");
            break;
        case 'agenylogin':
            titleName = '经销商登录'
            roleValue = '2'
            break;
//        case 'login':
//            titleName = '后台管理员登录'
//            roleValue = '1'
//            break;
    }

    $("#chosenrole").attr('value', roleValue)
    $("#loginTitleText").text(titleName)

    var li = ['备案登记指南', '制章单位注册流程', '制章单位年审流程', '制章单位管理办法']

    var liText = "<h4>一、\t办事流程</h4>" +
        "        <p>1、\t申请人携带刻章所需资料到刻章单位填写印章备案登记表；</p>" +
        "        <p>2、\t刻章单位工作人员审核申请人所填写的印章备案登记表及刻章所需资料，确认无误后，将印章备案登记表录入系统，扫描并上传刻章所需资料；</p>" +
        "        <p>3、\t备案登记成功后申请人领取登记回执；</p>" +
        "        <p>4、\t印章制作完成后刻章单位工作人员联系申请人前往刻章单位领取印章；</p>" +
        "        <p>5、\t申请人携带登记回执前往刻章单位领取印章，并在交付回执上签名完成印章交付。</p>" +
        "        <h4>二、\t申请刻章单位需提供的统一资料</h4>" +
        "        <p>1、\t委托书：单位法人手签名并在名字上盖右手食指印模，写上时间；</p>" +
        "        <p>2、\t工商营业执照副本原件和复印件一份（法人在复印件上注明“此复印件与原件一致”，并在手签名处，盖右手食指印模、写时间）。</p>" +
        "        <p>3、\t法人或负责人身份证原件和复印件一份（法人在复印件上注明“此复印件与原件一致”、并在手签名处，盖右手食指印模、写时间）。</p>" +
        "        <p>4、\t经办人身份证原件及复印件：单位法人委托他人到公安机关办理公章刻制核准的，应出具单位法人签名的委托书,并提供经办人身份证的原件及复印件(经办人在身份证复印件上注明“此复印件与原件一致”、并在手签名处，盖右手食指印模、写时间）。</p>"

    var li2 = [
        {liName: '备案登记指南', value: liText},
        {liName: '制章单位注册流程', value: '待补充'},
        {liName: '制章单位年审流程', value: '待补充'},
        {liName: '制章单位管理办法', value: '待补充'}
    ]

    addLiHtml("#listContent", li2)

    /**
     *  默认加载第一个li
     */
    $("#li0").children().removeClass().addClass("listContentSingleChoose")
    replaceSonHtml("#sealExplainContent", li2[0].value)

    /**
     * li 的居中
     */
    centerHtml("#sealProblemList", "#listContent")
    /**
     * 放大缩小重新设置li
     */
    window.onresize = function () {
        centerHtml("#sealProblemList", "#listContent")
    }
    /**
     *  li点击事件
     */
    $(".listContentSingleText").click(function () {
        //回归默认值
        $(".listContentSingleText").children().removeClass().addClass("listContentSingleUnChoose")
        //改变选中值
        $(this).children().removeClass().addClass("listContentSingleChoose")
        //获取id
//        alert($(this).attr("id"))
        var liId = $(this).attr("id")
        //改变右侧
        liId = liId.substring(2, liId.length)

        replaceSonHtml("#sealExplainContent", li2[liId].value)
    })

    /**
     * 修改记住密码文字
     */
    $("#rememberMeCheckbox").change(function () {
        if ($(this).is(":checked")) {
            $("#rememberMeLabel").removeClass("rememberMeLabelFalse").addClass("rememberMeLabelTure");
        } else {
            $("#rememberMeLabel").removeClass("rememberMeLabelTure").addClass("rememberMeLabelFalse");
        }
    })


    /**
     *  修改验证码图片
     */
    $("#verificationCodeImg").click(function () {
        $(this).attr('src', '${pageContext.request.contextPath}/servlet/validateCodeServlet?' + new Date().getTime());
    })


    /**
     * 联系的显示
     */
    $("#QRCode").mouseover(function () {
        $("#QRCodeInfo").removeClass().addClass("showQRCode")
    }).mouseout(function () {
        $("#QRCodeInfo").removeClass().addClass("hideQRCode")
    })


    /**
     * 搜索
     **/
    $("#ShowSearch").click(function () {
        $('#Search').css({
            'display': 'table'
        })
    })

    $("#SearchCancel").mouseover(function () {
        $(this).animate({
            height: '+=5px',
            width: '+=5px'
        })
    }).mouseout(function () {
        $(this).animate({
            height: '-=5px',
            width: '-=5px'
        })
    }).click(function () {
        $('#Search').css({
            'display': 'none'
        })
    })

    /**
     *  搜索
     * @returns {boolean} 返回false为表单不处理
     * @constructor
     */
    function FormSearch() {
        var dataArray = $('#searchForm').serializeArray()
        var data = {}
        for (var i = 0; i < dataArray.length; i++) {
            data[dataArray[i].name] = dataArray[i].value
        }
        if (dataArray[0].value == '' && dataArray[1].value == '' && dataArray[2].value == '') {
            alert('请输入搜索条件')
            return false
        }
        $.post(
            "${ctxOther}/check/page/search",
            data,
            function (result) {
                if (result.list) {
                    addSearchResultListHtml("#SearchResult", result.list)
                }
            }
        )
        //addSearchResultListHtml("#SearchResult", [1, 2, 3, 4])
        return false
    }

    ////TODO 表单验证

    $("#loginForm").validate({
        rules: {
            validateCode: {remote: "${pageContext.request.contextPath}/servlet/validateCodeServlet"}
        },
        messages: {
            validateCode: {remote: "验证码不正确", required: "请填写验证码"}
        },
        errorElement: 'div',
        errorLabelContainer: "#errorLoginDiv",
        errorContainer: "#errorLoginDiv",
        showErrors: function (errorMap, errorList) {
            if (errorMap.validateCode) {
                $('#errorLoginDiv').css({
                    'display': 'inline-block'
                })
                errorLoginHtml($('#errorLoginDiv'), errorMap.validateCode)
            } else {
                if ($('#verification').val()) {
                    $('#errorLoginDiv').css({
                        'display': 'none'
                    })
                }
            }
        }
    });

    function connectToQQ() {
        window.open("http://wpa.qq.com/msgrd?v=3&uin=${fns:getConfig("connect.qq")}&site=qq&menu=yes");
    }

</script>
</html>
