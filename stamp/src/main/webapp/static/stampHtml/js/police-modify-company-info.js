/**
 * Created by Administrator on 2018-08-24.
 */

function valid_form_data(url) {
    var reg = /^([\u4e00-\u9fa5a-zA-Z（）()]*)$/;
    var reg2 = /^([\u4e00-\u9fa5]{1,20}|[a-zA-Z\.\s]{1,20})$/;
    var regTel1 = /^(13[0-9]|14[579]|15[0-3,5-9]|16[6]|17[0135678]|18[0-9]|19[89])\d{8}$/;//手机号码
    var regTel2 = /^(([0\+]\d{2,3}-)?(0\d{2,3})-)(\d{7,8})(-(\d{3,}))?$/;//带区号的固定电话
    var regTel3 = /^(\d{7,8})(-(\d{3,}))?$/;//不带区号的固定电话
    var regular = /^[0-9a-zA-Z]{9,18}$/;//证件识别
    var values = true;
    //计算文件总大小 ie下不适配
    var isIE = navigator.userAgent.match(/MSIE/) != null,
        isIE6 = navigator.userAgent.match(/MSIE 6.0/) != null;
    //验证单位名称
    if ($("#companyName").val() == "") {
        values = false;
        if ($("#companyName").next().length) {
        } else {
            $("#companyName").after("<label>请输入单位名称</label>");
        }
    } else if ($("#companyName").val().split(" ").length != 1) {
        values = false;
        $("#companyName").next().remove();
        $("#companyName").after("<label>输入格式不能含有空格！</label>");
    }

    else if ($("#companyName").val().length > 40) {
        $("#companyName").next().remove();
        $("#companyName").after("<label>单位名称过长！</label>");
    } else {
        $("#companyName").next().remove();
    }

    //增加判断是否为统一社会信用代码的值（1：统一代码；2：其他代码）,是统一码才继续判断下面的规则
    var codeCheck = $('#stampRecord input[name="stampRecord.isSoleCode"]:checked').val();

    if (codeCheck == 1) {
        //验证统一社会代号
        if ($("#unifiedcode").val() == "") {
            if ($("#unifiedcode").next().length) {
            } else {
                $("#unifiedcode").after("<label>请输入统一代码</label>");
            }
            return false;
        } else if ($("#unifiedcode").val().split(" ").length != 1) {
            $("#unifiedcode").next().remove();
            $("#unifiedcode").after("<label>输入格式不能含有空格！</label>");
            return false;
        } else {
            //建立正则表达式
            var RegEx = /^([0-9ABCDEFGHJKLMNPQRTUWXY]{2})(\d{6})([0-9ABCDEFGHJKLMNPQRTUWXY]{9})([0-9ABCDEFGHJKLMNPQRTUWXY])$/;
            var RegEx2 = /^(\d{15})$/;
            var RegEx3 = /^([0-9A-Z]{9})$/;
            var RegEx4 = /^([0-9A-Z]{8})([\-]{1})([0-9A-Z]{1})$/;
            //创建数字数组以及定义权值
            var numArray = new Array();
            var WV = [1, 3, 9, 27, 19, 26, 16, 17, 20, 29, 25, 13, 8, 24, 10, 30, 28];
            var MV2 = [3, 7, 9, 10, 5, 8, 4, 2];
            //求和值，取余值，差
            var sum = 0, rem = 0, dif = 0;
            //正则表达式验证
            if (RegEx.test($("#unifiedcode").val()) && $("#unifiedcode").val().length == 18) {
                //将大写字母转换为对应的数字
                for (var i = 0; i < 18; i++) {
                    switch ($("#unifiedcode").val().charAt(i)) {
                        case '0':
                            numArray[i] = 0;
                            break;
                        case '1':
                            numArray[i] = 1;
                            break;
                        case '2':
                            numArray[i] = 2;
                            break;
                        case '3':
                            numArray[i] = 3;
                            break;
                        case '4':
                            numArray[i] = 4;
                            break;
                        case '5':
                            numArray[i] = 5;
                            break;
                        case '6':
                            numArray[i] = 6;
                            break;
                        case '7':
                            numArray[i] = 7;
                            break;
                        case '8':
                            numArray[i] = 8;
                            break;
                        case '9':
                            numArray[i] = 9;
                            break;
                        case 'A':
                            numArray[i] = 10;
                            break;
                        case 'B':
                            numArray[i] = 11;
                            break;
                        case 'C':
                            numArray[i] = 12;
                            break;
                        case 'D':
                            numArray[i] = 13;
                            break;
                        case 'E':
                            numArray[i] = 14;
                            break;
                        case 'F':
                            numArray[i] = 15;
                            break;
                        case 'G':
                            numArray[i] = 16;
                            break;
                        case 'H':
                            numArray[i] = 17;
                            break;
                        case 'J':
                            numArray[i] = 18;
                            break;
                        case 'K':
                            numArray[i] = 19;
                            break;
                        case 'L':
                            numArray[i] = 20;
                            break;
                        case 'M':
                            numArray[i] = 21;
                            break;
                        case 'N':
                            numArray[i] = 22;
                            break;
                        case 'P':
                            numArray[i] = 23;
                            break;
                        case 'Q':
                            numArray[i] = 24;
                            break;
                        case 'R':
                            numArray[i] = 25;
                            break;
                        case 'T':
                            numArray[i] = 26;
                            break;
                        case 'U':
                            numArray[i] = 27;
                            break;
                        case 'W':
                            numArray[i] = 28;
                            break;
                        case 'X':
                            numArray[i] = 29;
                            break;
                        case 'Y':
                            numArray[i] = 30;
                            break;
                        default:
                            numArray[i] = $("#unifiedcode").val().charAt(i);
                    }
                }
                //求和运算
                for (i = 0; i < 17; i++) {
                    sum = Number(sum) + numArray[i] * WV[i];
                }
                //console.log(sum);
                rem = sum % 31;

                //console.log(rem);
                //计算最后一位的数字
                dif = 31 - rem;
                //特殊值更改（待定)


                if(dif==31){
                    dif=0;
                }

                if (dif != numArray[17]) {
                    $("#unifiedcode").next().remove();
                    $("#unifiedcode").after("<label>企业统一信用代码校验不通过</label>");
                    return false;
                } else {
                    $("#unifiedcode").next().remove();
                }
            } else if (RegEx2.test($("#unifiedcode").val()) && $("#unifiedcode").val().length == 15) {
                sum = Number($("#unifiedcode").val().charAt(0)) + 10;
                rem = ((sum % 10) * 2) % 11;
                for (var i = 1; i < 14; i++) {
                    sum = rem + Number($("#unifiedcode").val().charAt(i));
                    if ((sum % 10) == 0) {
                        rem = (10 * 2) % 11;
                    } else {
                        rem = ((sum % 10) * 2) % 11;
                    }
                }
                for (var j = 0; j < 20; j++) {
                    sum = j;
                    if (((rem + sum) % 10) == 1)
                        break;
                }
                if ($("#unifiedcode").val().charAt(14) == sum) {
                    $("#unifiedcode").next().remove();
                } else {
                    $("#unifiedcode").next().remove();
                    $("#unifiedcode").after("<label>企业统一信用代码校验不通过</label>");
                    return false;
                }
            } else if (RegEx3.test($("#unifiedcode").val()) && $("#unifiedcode").val().length == 9) {
                for (var i = 0; i < 9; i++) {
                    if ($("#unifiedcode").val().charAt(i) == "-") {
                        if ((/^([0-9])$/).test($("#unifiedcode").val().charAt(i + 1))) {
                            numArray[i] = $("#unifiedcode").val().charAt(i + 1);
                        } else {
                            numArray[i] = $("#unifiedcode").val().charAt(i + 1).charCodeAt() - 55;
                        }
                    } else {
                        if ((/^([0-9])$/).test($("#unifiedcode").val().charAt(i))) {
                            numArray[i] = $("#unifiedcode").val().charAt(i);
                        } else {
                            numArray[i] = $("#unifiedcode").val().charAt(i).charCodeAt() - 55;
                        }
                    }
                }
                for (var i = 0; i < 8; i++) {
                    sum = Number(sum) + numArray[i] * MV2[i];
                }
                rem = sum % 11;
                dif = 11 - rem;
                if (dif == numArray[8]) {
                    $("#unifiedcode").next().remove();
                } else if (dif == 10 && numArray[8] == 33) {
                    $("#unifiedcode").next().remove();
                } else if (dif == 11 && numArray[8] == 0) {
                    $("#unifiedcode").next().remove();
                } else {
                    $("#unifiedcode").next().remove();
                    $("#unifiedcode").after("<label>企业统一信用代码校验不通过</label>");
                    return false;
                }
            } else if (RegEx4.test($("#unifiedcode").val()) && $("#unifiedcode").val().length == 10) {
                for (var i = 0; i < 9; i++) {
                    if ($("#unifiedcode").val().charAt(i) == "-") {
                        if ((/^([0-9])$/).test($("#unifiedcode").val().charAt(i + 1))) {
                            numArray[i] = $("#unifiedcode").val().charAt(i + 1);
                        } else {
                            numArray[i] = $("#unifiedcode").val().charAt(i + 1).charCodeAt() - 55;
                        }
                    } else {
                        if ((/^([0-9])$/).test($("#unifiedcode").val().charAt(i))) {
                            numArray[i] = $("#unifiedcode").val().charAt(i);
                        } else {
                            numArray[i] = $("#unifiedcode").val().charAt(i).charCodeAt() - 55;
                        }
                    }
                }
                for (var i = 0; i < 8; i++) {
                    sum = Number(sum) + numArray[i] * MV2[i];
                }
                rem = sum % 11;
                dif = 11 - rem;
                if (dif == numArray[8]) {
                    $("#unifiedcode").next().remove();
                } else if (dif == 10 && numArray[8] == 33) {
                    $("#unifiedcode").next().remove();
                } else if (dif == 11 && numArray[8] == 0) {
                    $("#unifiedcode").next().remove();
                } else {
                    $("#unifiedcode").next().remove();
                    $("#unifiedcode").after("<label>企业统一信用代码校验不通过</label>");
                    return false;
                }
            } else {
                $("#unifiedcode").next().remove();
                $("#unifiedcode").after("<label>企业统一信用代码格式错误</label>");
                return false;
            }
        }
    }

    //验证刻章单位地址
    if ($("#companyAddress").val() == "") {
        if ($("#companyAddress").next().length) {
        } else {
            $("#companyAddress").after("<label>请输入刻章单位地址</label>");
        }
        return false;
    } else if ($("#companyAddress").val().split(" ").length != 1) {
        $("#companyAddress").next().remove();
        $("#companyAddress").after("<label>输入格式不能含有空格！</label>");
        return false;
    } else if ($("#companyAddress").val().length > 100) {
        $("#companyAddress").next().remove();
        $("#companyAddress").after("<label>地址过长！</label>");
        return false;
    } else {
        $("#companyAddress").next().remove();
    }

    //验证法人姓名
    if ($("#legalName").val() == "") {
        if ($("#legalName").next().length) {
        } else {
            $("#legalName").after("<label>请输入法人姓名</label>");
        }
        return false;
    } else if (!reg2.test($("#legalName").val())) {
        $("#legalName").next().remove();
        $("#legalName").after("<label>输入姓名格式错误！</label>");
        return false;
    } else {
        $("#legalName").next().remove();
    }
    //验证经办人证件姓名
    if ($("#agentName").val() == "") {
        if ($("#agentName").next().length) {
        } else {
            $("#agentName").after("<label>请输入经办人名字</label>");
        }
        return false;
    } else if (!reg2.test($("#agentName").val())) {
        $("#agentName").next().remove();
        $("#agentName").after("<label>输入经办人名字号码格式错误！</label>");
        return false;
    } else {
        $("#agentName").next().remove();
    }
    //验证经办人证件号码
    if ($("#agentCertCode").val() == "") {
        if ($("#agentCertCode").next().length) {
        } else {
            $("#agentCertCode").after("<label>请输入经办人证件号码</label>");
        }
        return false;
    } else if (!regular.test($("#agentCertCode").val())) {
        $("#agentCertCode").next().remove();
        $("#agentCertCode").after("<label>输入经办人证件号码格式错误！</label>");
        return false;
    } else {
        $("#agentCertCode").next().remove();
    }
    //验证法人证件
    var typeValue = $("#legalCertType").find("option:selected").val();
    if (typeValue==1) {//身份证
        var regular = /^[1-9]\d{5}[1-9]\d{3}((0\d)|(1[0-2]))(([0|1|2]\d)|3[0-1])((\d{4})|\d{3}[A-Z])$/;
        if ($('#legalCertCode').val() == "") {
            $('#legalCertCode').next().remove();
            $('#legalCertCode').after("<label>请输入证件号码</label>");
            return false;
        } else if ($('#legalCertCode').val().split(" ").length != 1) {
            $('#legalCertCode').next().remove();
            $('#legalCertCode').after("<label>输入格式不能含有空格！</label>");
            return false;
        } else if (!regular.test($('#legalCertCode').val())) {
            $('#legalCertCode').next().remove();
            $('#legalCertCode').after("<label>输入身份证件格式错误</label>");
            return false;
        } else {
            $('#legalCertCode').next().remove();
        }
    }else if(typeValue==2) {//港澳通行证
        var regular = /^[HMChm]{1}([0-9]{10}|[0-9]{8})$/;
        var regular2 = /^((\s?[A-Za-z])|([A-Za-z]{2}))\d{6}(\([0-9aA]\)|[0-9aA])$/;//香港--身份证（本国人）
        var regular3 = /^[1|5|7][0-9]{6}\([0-9Aa]\)$/;//澳门--身份证（本国人）
        if ($('#legalCertCode').val() == "") {
            $('#legalCertCode').next().remove();
            $('#legalCertCode').after("<label>请输入证件号码</label>");
            return false;
        } else if ($('#legalCertCode').val().split(" ").length != 1) {
            $('#legalCertCode').next().remove();
            $('#legalCertCode').after("<label>输入格式不能含有空格！</label>");
            return false;
        } else if (!regular.test($('#legalCertCode').val()) && !regular2.test($('#legalCertCode').val()) && !regular3.test($('#legalCertCode').val())) {
            $('#legalCertCode').next().remove();
            $('#legalCertCode').after("<label>输入港澳通行证/港澳身份证(本国人)格式错误</label>");
            return false;
        } else {
            $('#legalCertCode').next().remove();
        }
    }else if(typeValue==3) {//台湾通行证
        var regular = /^[0-9]{8}$/;
        var regular2 = /^[0-9]{10}$/;
        var regular3 = /^[a-zA-Z][0-9]{9}$/;//台湾身份证
        if ($('#legalCertCode').val() == "") {
            $('#legalCertCode').next().remove();
            $('#legalCertCode').after("<label>请输入证件号码</label>");
            return false;
        } else if ($('#legalCertCode').val().split(" ").length != 1) {
            $('#legalCertCode').next().remove();
            $('#legalCertCode').after("<label>输入格式不能含有空格！</label>");
            return false;
        } else if (!regular.test($('#legalCertCode').val()) && !regular2.test($('#legalCertCode').val()) && !regular3.test($('#legalCertCode').val())) {
            $('#legalCertCode').next().remove();
            $('#legalCertCode').after("<label>输入台湾通行证/台湾身份证格式错误</label>");
            return false;
        } else {
            $('#legalCertCode').next().remove();
        }
    }else if(typeValue==4){// 护照验证
        var regular = /^[a-zA-Z]{5,17}$/;
        var regular2 = /^[a-zA-Z0-9]{5,17}$/;
        if ($('#legalCertCode').val() == "") {
            $('#legalCertCode').next().remove();
            $('#legalCertCode').after("<label>请输入证件号码</label>");
            return false;
        } else if ($('#legalCertCode').val().split(" ").length != 1) {
            $('#legalCertCode').next().remove();
            $('#legalCertCode').after("<label>输入格式不能含有空格！</label>");
            return false;
        } else if (!regular.test($('#legalCertCode').val()) && !regular2.test($('#legalCertCode').val())) {
            $('#legalCertCode').next().remove();
            $('#legalCertCode').after("<label>输入护照证件格式错误</label>");
            return false;
        } else {
            $('#legalCertCode').next().remove();
        }
    }
    //验证经办人证件
    var agentCertType = $('#agentCertType option:selected').attr('value');

    if (agentCertType==1) {//身份证
        var regular = /^[0-9a-zA-Z]{9,18}$/;
        if ($("#agentCertCode").val() == "") {values = false;
            $("#agentCertCode").next().remove();
            $("#agentCertCode").after("<label>请输入证件号码</label>");
        } else if ($("#agentCertCode").val().split(" ").length != 1) {
            $("#agentCertCode").next().remove();
            $("#agentCertCode").after("<label>输入格式不能含有空格！</label>");
            return false;
        } else if (!regular.test($("#agentCertCode").val())) {
            $("#agentCertCode").next().remove();
            $("#agentCertCode").after("<label>输入身份证件格式错误</label>");
            return false;
        } else {
            $("#agentCertCode").next().remove();
        }
    }else if(agentCertType==2) {//港澳通行证
        var regular = /^[HMChm]{1}([0-9]{10}|[0-9]{8})$/;
        var regular2 = /^((\s?[A-Za-z])|([A-Za-z]{2}))\d{6}(\([0-9aA]\)|[0-9aA])$/;//香港--身份证（本国人）
        var regular3 = /^[1|5|7][0-9]{6}\([0-9Aa]\)$/;//澳门--身份证（本国人）
        if ($("#agentCertCode").val() == "") {
            $("#agentCertCode").next().remove();
            $("#agentCertCode").after("<label>请输入证件号码</label>");
            return false;
        } else if ($("#agentCertCode").val().split(" ").length != 1) {
            $("#agentCertCode").next().remove();
            $("#agentCertCode").after("<label>输入格式不能含有空格！</label>");
            return false;
        } else if (!regular.test($("#agentCertCode").val()) && !regular2.test($("#agentCertCode").val()) && !regular3.test($("#agentCertCode").val())) {
            $("#agentCertCode").next().remove();
            $("#agentCertCode").after("<label>输入港澳通行证/港澳身份证(本国人)格式错误</label>");
            return false;
        } else {
            $("#agentCertCode").next().remove();
        }
    }else if(agentCertType==3) {//台湾通行证
        var regular = /^[0-9]{8}$/;
        var regular2 = /^[0-9]{10}$/;
        var regular3 = /^[a-zA-Z][0-9]{9}$/;//台湾身份证
        if ($("#agentCertCode").val() == "") {
            $("#agentCertCode").next().remove();
            $("#agentCertCode").after("<label>请输入证件号码</label>");
            return false;
        } else if ($("#agentCertCode").val().split(" ").length != 1) {
            $("#agentCertCode").next().remove();
            $("#agentCertCode").after("<label>输入格式不能含有空格！</label>");
            return false;
        } else if (!regular.test($("#agentCertCode").val()) && !regular2.test($("#agentCertCode").val()) && !regular3.test($("#agentCertCode").val())) {
            $("#agentCertCode").next().remove();
            $("#agentCertCode").after("<label>输入台湾通行证/台湾身份证格式错误</label>");
            return false;
        } else {
            $("#agentCertCode").next().remove();
        }
    }else if(agentCertType==4){// 护照验证
        var regular = /^[a-zA-Z]{5,17}$/;
        var regular2 = /^[a-zA-Z0-9]{5,17}$/;
        if ($("#agentCertCode").val() == "") {
            $("#agentCertCode").next().remove();
            $("#agentCertCode").after("<label>请输入证件号码</label>");
            return false;
        } else if ($("#agentCertCode").val().split(" ").length != 1) {
            $("#agentCertCode").next().remove();
            $("#agentCertCode").after("<label>输入格式不能含有空格！</label>");
            return false;
        } else if (!regular.test($("#agentCertCode").val()) && !regular2.test($("#agentCertCode").val())) {
            $("#agentCertCode").next().remove();
            $("#agentCertCode").after("<label>输入护照证件格式错误</label>");
            return false;
        } else {
            $("#agentCertCode").next().remove();
        }
    }
    //法人电话
    if ($("#legalPhone").val() == "") {
        $("#legalPhone").next().remove();
        $("#legalPhone").after("<label>请输入联系电话</label>");
        return false;
    } else if ($("#legalPhone").val().split(" ").length != 1) {
        $("#legalPhone").next().remove();
        $("#legalPhone").after("<label>输入格式不能含有空格</label>");
        return false;
    } else if (!regTel1.test($("#legalPhone").val())) {
        $("#legalPhone").next().remove();
        $("#legalPhone").after("<label>输入电话号码有误</label>");
        return false;
    } else {
        $("#legalPhone").next().remove();
    }
    //经办人电话
    if ($("#agentPhone").val() == "") {
        $("#agentPhone").next().remove();
        $("#agentPhone").after("<label>请输入联系电话</label>");
        return false;
    } else if ($("#agentPhone").val().split(" ").length != 1) {
        $("#agentPhone").next().remove();
        $("#agentPhone").after("<label>输入格式不能含有空格</label>");
        return false;
    } else if (!regTel1.test($("#agentPhone").val())) {
        $("#agentPhone").next().remove();
        $("#agentPhone").after("<label>输入电话号码有误</label>");
        return false;
    } else {
        $("#agentPhone").next().remove();
    }
    debugger
    // 变更原因
    if ($("#modifyReason").val() == "" || $("#modifyReason").val() == null) {
        $("#modifyReason").next().remove();
        $("#modifyReason").after("<label>请输入变更原因</label>");
        return false;
    } else {
        $("#modifyReason").next().remove();
    }
    var formData = new FormData($("#frmModifyCompanyInfo" )[0]);
    $.ajax({
        url: url,
        type: 'POST',
        data: formData,
        async: false,
        cache: false,
        contentType: false,
        processData: false,
        success:function (data) {
            debugger
            if(data == "no_attachments"){
                alert("请上传完整附件")
            }
            if(data.indexOf("success") != -1){
                alert("变更成功");
                var id = data.substr(data.indexOf("=")+1,data.length)
                window.location.href = ctx +"/policeStampPage/printNewCompanyInfo?id=" + id;
            }
        },
        error:function (data) {
            alert("出错了！请联系管理员！");
        }
    });
}



