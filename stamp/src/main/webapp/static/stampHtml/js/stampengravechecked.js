/**
 * Created by 77426 on 2017/5/11.
 */
//表单验证
$(function () {
    //验证企业名称
    $("#companyname").blur(function () {

        var reg = /^([\u4e00-\u9fa5a-zA-Z]*)$/;
        if ($(this).val() == "") {
            if ($(this).next().length) {
            } else {
                $(this).after("<label>请输入单位名称</label>");
            }
        } else if ($(this).val().split(" ").length != 1) {
            $(this).next().remove();
            $(this).after("<label>输入格式不能含有空格！</label>");
        }
        // else if(!reg.test($(this).val())){
        //     $(this).next().remove();
        //     $(this).after("<label>公司名称格式错误！</label>");
        // }
        else if ($(this).val().length > 40) {
            $(this).next().remove();
            $(this).after("<label>公司名称过长！</label>");
        } else {
            $(this).next().remove();
        }

        //再次确认输入信息
        $('#unifiedcode').unbind("blur");

        $( "#dialog-message1 p").text($(this).val());
        $( "#dialog-message1").dialog({
            modal: true,
            buttons: {
                OK: function() {
                    $( this ).dialog( "close" );
                    findUseComp();
                }
            }
        });
        setTimeout(function(){
            $("#unifiedcode").bind('blur',unifiedcode_onblur);
        },1000)

    });
    //验证单位类别
    $("#companytype").blur(function () {
        if ($(this).val() == "") {
            if ($(this).next().length) {
            } else {
                $(this).after("<label>请输入单位类别</label>");
            }
        } else if ($(this).val().split(" ").length != 1) {
            $(this).next().remove();
            $(this).after("<label>输入格式不能含有空格！</label>");
        } else {
            $(this).next().remove();
        }
    });

    //页面初始化时绑定校验 方法
    $("#stampRecord").find("#unifiedcode").bind('blur',unifiedcode_onblur);

    //验证统一社会代号
    function unifiedcode_onblur () {

        //再次确认输入信息
        $("#dialog-message2 p").text($(this).val());
        $("#dialog-message2").dialog({
            modal: true,
            buttons: {
                OK: function() {
                    $( this ).dialog( "close" );
                }
            }
        });
        //增加判断是否为统一社会信用代码的值（1：统一代码；2：其他代码）,是统一码才继续判断下面的规则
        var codeCheck = $('#stampRecord input[name="stampRecord.isSoleCode"]:checked').val();
        if (codeCheck == 1) {
            if ($(this).val() == "") {
                if ($(this).next().length) {
                } else {
                    $(this).after("<label>请输入统一代码</label>");
                }
            } else if ($(this).val().split(" ").length != 1) {
                $(this).next().remove();
                $(this).after("<label>输入格式不能含有空格！</label>");
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
                if (RegEx.test($(this).val()) && $(this).val().length == 18) {
                    //将大写字母转换为对应的数字
                    for (var i = 0; i < 18; i++) {
                        switch ($(this).val().charAt(i)) {
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
                                numArray[i] = $(this).val().charAt(i);
                        }
                    }
                    //求和运算
                    for (i = 0; i < 17; i++) {
                        sum = Number(sum) + numArray[i] * WV[i];
                    }
                    //console.log(sum);

                    rem = sum % 31;
                    // alert("rem=="+rem);
                    //console.log(rem);
                    //计算最后一位的数字
                    dif = 31 - rem;

                    //特殊值更改（待定)
                    /* if(dif == 1){
                     dif = 30;
                     }
                     console.log(dif);
                     console.log(numArray[17]);

                     最后一位验证*/
                    //特殊值更改
                    if(dif==31){
                        dif=0;
                    }
                    // alert("dif==="+dif+"===numArray[17]==="+numArray[17]);

                    if (dif != numArray[17]) {
                        $(this).next().remove();
                        $(this).after("<label>企业统一信用代码校验不通过</label>");
                    } else {
                        $(this).next().remove();
                    }
                } else if (RegEx2.test($(this).val()) && $(this).val().length == 15) {
                    sum = Number($(this).val().charAt(0)) + 10;
                    rem = ((sum % 10) * 2) % 11;
                    for (var i = 1; i < 14; i++) {
                        sum = rem + Number($(this).val().charAt(i));
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
                    if ($(this).val().charAt(14) == sum) {
                        $(this).next().remove();
                    } else {
                        $(this).next().remove();
                        $(this).after("<label>企业统一信用代码校验不通过</label>");
                    }
                } else if (RegEx3.test($(this).val()) && $(this).val().length == 9) {
                    for (var i = 0; i < 9; i++) {
                        if ($(this).val().charAt(i) == "-") {
                            if ((/^([0-9])$/).test($(this).val().charAt(i + 1))) {
                                numArray[i] = $(this).val().charAt(i + 1);
                            } else {
                                numArray[i] = $(this).val().charAt(i + 1).charCodeAt() - 55;
                            }
                        } else {
                            if ((/^([0-9])$/).test($(this).val().charAt(i))) {
                                numArray[i] = $(this).val().charAt(i);
                            } else {
                                numArray[i] = $(this).val().charAt(i).charCodeAt() - 55;
                            }
                        }
                    }
                    for (var i = 0; i < 8; i++) {
                        sum = Number(sum) + numArray[i] * MV2[i];
                    }
                    rem = sum % 11;
                    dif = 11 - rem;
                    if (dif == numArray[8]) {
                        $(this).next().remove();
                    } else if (dif == 10 && numArray[8] == 33) {
                        $(this).next().remove();
                    } else if (dif == 11 && numArray[8] == 0) {
                        $(this).next().remove();
                    } else {
                        $(this).next().remove();
                        $(this).after("<label>企业统一信用代码校验不通过</label>");
                    }
                } else if (RegEx4.test($(this).val()) && $(this).val().length == 10) {
                    for (var i = 0; i < 9; i++) {
                        if ($(this).val().charAt(i) == "-") {
                            if ((/^([0-9])$/).test($(this).val().charAt(i + 1))) {
                                numArray[i] = $(this).val().charAt(i + 1);
                            } else {
                                numArray[i] = $(this).val().charAt(i + 1).charCodeAt() - 55;
                            }
                        } else {
                            if ((/^([0-9])$/).test($(this).val().charAt(i))) {
                                numArray[i] = $(this).val().charAt(i);
                            } else {
                                numArray[i] = $(this).val().charAt(i).charCodeAt() - 55;
                            }
                        }
                    }
                    for (var i = 0; i < 8; i++) {
                        sum = Number(sum) + numArray[i] * MV2[i];
                    }
                    rem = sum % 11;
                    dif = 11 - rem;
                    if (dif == numArray[8]) {
                        $(this).next().remove();
                    } else if (dif == 10 && numArray[8] == 33) {
                        $(this).next().remove();
                    } else if (dif == 11 && numArray[8] == 0) {
                        $(this).next().remove();
                    } else {
                        $(this).next().remove();
                        $(this).after("<label>企业统一信用代码校验不通过</label>");
                    }
                } else {
                    $(this).next().remove();
                    $(this).after("<label>企业统一信用代码格式错误</label>");
                }
            }
        } else {
            $(this).next().remove();
        }
    }

    //验证刻章单位地址
    $("#companyaddress").blur(function () {
        if ($(this).val() == "") {
            if ($(this).next().length) {
            } else {
                $(this).after("<label>请输入刻章单位地址</label>");
            }
        } else if ($(this).val().split(" ").length != 1) {
            $(this).next().remove();
            $(this).after("<label>输入格式不能含有空格！</label>");
        } else if ($(this).val().length > 100) {
            $(this).next().remove();
            $(this).after("<label>地址过长！</label>");
        } else {
            $(this).next().remove();
        }
    });
    //验证法人姓名
    $("#legalName").blur(function () {
        var reg = /^([\u4e00-\u9fa5]{1,20}|[a-zA-Z\.\s]{1,20})$/;
        if ($(this).val() == "") {
            if ($(this).next().length) {
            } else {
                $(this).after("<label>请输入法人姓名</label>");
            }
        } /*else if ($(this).val().split(" ").length != 1) {
            $(this).next().remove();
            $(this).after("<label>输入格式不能含有空格！</label>");
        } */else if (!reg.test($(this).val())) {
            $(this).next().remove();
            $(this).after("<label>输入姓名格式错误！</label>");
        } else {
            $(this).next().remove();
        }
    });
    //验证经办人姓名
    $("#personname").blur(function () {
        var reg = /^([\u4e00-\u9fa5]{1,20}|[a-zA-Z\.\s]{1,20})$/;
        if ($(this).val() == "") {
            if ($(this).next().length) {
            } else {
                $(this).after("<label>请输入经办人姓名</label>");
            }
        } /*else if ($(this).val().split(" ").length != 1) {
            $(this).next().remove();
            $(this).after("<label>输入格式不能含有空格！</label>");
        }*/ else if (!reg.test($(this).val())) {
            $(this).next().remove();
            $(this).after("<label>输入姓名格式错误！</label>");
        } else {
            $(this).next().remove();
        }
    });
    //验证法人证件
    $("#legalid").blur(function () {

        var a = $('#legalCertType option:selected').attr('value');

        if (a==1) {//身份证
            var regular = /^[1-9]\d{5}[1-9]\d{3}((0\d)|(1[0-2]))(([0|1|2]\d)|3[0-1])((\d{4})|\d{3}[A-Z])$/;
            if ($(this).val() == "") {
                $(this).next().remove();
                $(this).after("<label>请输入证件号码</label>");
            } else if ($(this).val().split(" ").length != 1) {
                $(this).next().remove();
                $(this).after("<label>输入格式不能含有空格！</label>");
            } else if (!regular.test($(this).val())) {
                $(this).next().remove();
                $(this).after("<label>输入身份证件格式错误</label>");
            } else {
                $(this).next().remove();
            }
        }else if(a==2) {//港澳通行证或者港澳身份证
            var regular = /^[HMChm]{1}([0-9]{10}|[0-9]{8})$/;
            var regular2 = /^((\s?[A-Za-z])|([A-Za-z]{2}))\d{6}(\([0-9aA]\)|[0-9aA])$/;//香港--身份证（本国人）
            var regular3 = /^[1|5|7][0-9]{6}\([0-9Aa]\)$/;//澳门--身份证（本国人）

            if ($(this).val() == "") {
                $(this).next().remove();
                $(this).after("<label>请输入证件号码</label>");
            } else if ($(this).val().split(" ").length != 1) {
                $(this).next().remove();
                $(this).after("<label>输入格式不能含有空格！</label>");
            } else if (!regular.test($(this).val()) && !regular2.test($(this).val()) && !regular3.test($(this).val())) {
                $(this).next().remove();
                $(this).after("<label>输入港澳通行证/港澳身份证(本国人)格式错误</label>");
            } else {
                $(this).next().remove();
            }
        }else if(a==3) {//台湾通行证或者台湾身份证
            var regular = /^[0-9]{8}$/;
            var regular2 = /^[0-9]{10}$/;
            var regular3 = /^[a-zA-Z][0-9]{9}$/;//台湾身份证
            if ($(this).val() == "") {
                $(this).next().remove();
                $(this).after("<label>请输入证件号码</label>");
            } else if ($(this).val().split(" ").length != 1) {
                $(this).next().remove();
                $(this).after("<label>输入格式不能含有空格！</label>");
            } else if (!regular.test($(this).val()) && !regular2.test($(this).val()) && !regular3.test($(this).val())) {
                $(this).next().remove();
                $(this).after("<label>输入台湾通行证/台湾身份证格式错误</label>");
            } else {
                $(this).next().remove();
            }
        }else if(a==4){// 护照验证
            var regular = /^[a-zA-Z]{5,17}$/;
            var regular2 = /^[a-zA-Z0-9]{5,17}$/;
            if ($(this).val() == "") {
                $(this).next().remove();
                $(this).after("<label>请输入证件号码</label>");
            } else if ($(this).val().split(" ").length != 1) {
                $(this).next().remove();
                $(this).after("<label>输入格式不能含有空格！</label>");
            } else if (!regular.test($(this).val()) && !regular2.test($(this).val())) {
                $(this).next().remove();
                $(this).after("<label>输入护照证件格式错误</label>");
            } else {
                $(this).next().remove();
            }
        }

    });
    //验证经办人证件
    $("#personid").blur(function () {

        var a = $('#persionType option:selected').attr('value');

        if (a==1) {//身份证
            var regular = /^[1-9]\d{5}[1-9]\d{3}((0\d)|(1[0-2]))(([0|1|2]\d)|3[0-1])((\d{4})|\d{3}[A-Z])$/;
            if ($(this).val() == "") {
                $(this).next().remove();
                $(this).after("<label>请输入证件号码</label>");
            } else if ($(this).val().split(" ").length != 1) {
                $(this).next().remove();
                $(this).after("<label>输入格式不能含有空格！</label>");
            } else if (!regular.test($(this).val())) {
                $(this).next().remove();
                $(this).after("<label>输入身份证件格式错误</label>");
            } else {
                $(this).next().remove();
            }
        }else if(a==2) {//港澳通行证
            var regular = /^[HMChm]{1}([0-9]{10}|[0-9]{8})$/;
            var regular2 = /^((\s?[A-Za-z])|([A-Za-z]{2}))\d{6}(\([0-9aA]\)|[0-9aA])$/;//香港--身份证（本国人）
            var regular3 = /^[1|5|7][0-9]{6}\([0-9Aa]\)$/;//澳门--身份证（本国人）
            if ($(this).val() == "") {
                $(this).next().remove();
                $(this).after("<label>请输入证件号码</label>");
            } else if ($(this).val().split(" ").length != 1) {
                $(this).next().remove();
                $(this).after("<label>输入格式不能含有空格！</label>");
            } else if (!regular.test($(this).val()) && !regular2.test($(this).val()) && !regular3.test($(this).val())) {
                $(this).next().remove();
                $(this).after("<label>输入港澳通行证/港澳身份证(本国人)格式错误</label>");
            } else {
                $(this).next().remove();
            }
        }else if(a==3) {//台湾通行证
            var regular = /^[0-9]{8}$/;
            var regular2 = /^[0-9]{10}$/;
            var regular3 = /^[a-zA-Z][0-9]{9}$/;//台湾身份证
            if ($(this).val() == "") {
                $(this).next().remove();
                $(this).after("<label>请输入证件号码</label>");
            } else if ($(this).val().split(" ").length != 1) {
                $(this).next().remove();
                $(this).after("<label>输入格式不能含有空格！</label>");
            } else if (!regular.test($(this).val()) && !regular2.test($(this).val()) && !regular3.test($(this).val())) {
                $(this).next().remove();
                $(this).after("<label>输入台湾通行证/台湾身份证格式错误</label>");
            } else {
                $(this).next().remove();
            }
        }else if(a==4){// 护照验证
            var regular = /^[a-zA-Z]{5,17}$/;
            var regular2 = /^[a-zA-Z0-9]{5,17}$/;
            if ($(this).val() == "") {
                $(this).next().remove();
                $(this).after("<label>请输入证件号码</label>");
            } else if ($(this).val().split(" ").length != 1) {
                $(this).next().remove();
                $(this).after("<label>输入格式不能含有空格！</label>");
            } else if (!regular.test($(this).val()) && !regular2.test($(this).val())) {
                $(this).next().remove();
                $(this).after("<label>输入护照证件格式错误</label>");
            } else {
                $(this).next().remove();
            }
        }
    });
    //法人电话
    $("#legalphone").blur(function () {
        var regTel1 = /^0?(13|14|15|16|17|18|19)[0-9]{9}$/;//手机号码
        var regTel2 = /^(([0\+]\d{2,3}-)?(0\d{2,3})-)(\d{7,8})(-(\d{3,}))?$/;//带区号的固定电话
        var regTel3 = /^(\d{7,8})(-(\d{3,}))?$/;//不带区号的固定电话
        if ($(this).val() == "") {
            $(this).next().remove();
            $(this).after("<label>请输入联系电话</label>");
        } else if ($(this).val().split(" ").length != 1) {
            $(this).next().remove();
            $(this).after("<label>输入格式不能含有空格</label>");
        } else if (!regTel1.test($(this).val())) {
            $(this).next().remove();
            $(this).after("<label>输入电话号码有误</label>");
        } else {
            $(this).next().remove();
        }
    });
    //单位电话
    /*$("#companyphone").blur(function () {
        var regTel1 = /^(13[0-9]{9})|(18[0-9]{9})|(14[0-9]{9})|(17[0-9]{9})|(15[0-9]{9})$/;//手机号码
        var regTel2 = /^(([0\+]\d{2,3}-)?(0\d{2,3})-)(\d{7,8})(-(\d{3,}))?$/;//带区号的固定电话
        var regTel3 = /^(\d{7,8})(-(\d{3,}))?$/;//不带区号的固定电话
        if ($(this).val() == "") {
            $(this).next().remove();
            $(this).after("<label>请输入联系电话</label>");
        } else if ($(this).val().split(" ").length != 1) {
            $(this).next().remove();
            $(this).after("<label>输入格式不能含有空格</label>");
        } else if (!regTel1.test($(this).val()) && !regTel2.test($(this).val()) && !regTel3.test($(this).val())) {
            $(this).next().remove();
            $(this).after("<label>输入电话号码有误</label>");
        } else {
            $(this).next().remove();
        }
    });*/
    //经办人电话
    $("#personphone").blur(function () {
        var regTel1 = /^0?(13|14|15|16|17|18|19)[0-9]{9}$/;//手机号码
        var regTel2 = /^(([0\+]\d{2,3}-)?(0\d{2,3})-)(\d{7,8})(-(\d{3,}))?$/;//带区号的固定电话
        var regTel3 = /^(\d{7,8})(-(\d{3,}))?$/;//不带区号的固定电话
        if ($(this).val() == "") {
            $(this).next().remove();
            $(this).after("<label>请输入联系电话</label>");
        } else if ($(this).val().split(" ").length != 1) {
            $(this).next().remove();
            $(this).after("<label>输入格式不能含有空格</label>");
        } else if (!regTel1.test($(this).val())) {
            $(this).next().remove();
            $(this).after("<label>输入电话号码有误</label>");
        } else {
            $(this).next().remove();
        }
    });

    //验证企业名称
    $("#enterprise_name").blur(function () {
        var reg = /^([\u4e00-\u9fa5a-zA-Z]*)$/;
        if ($(this).val() == "") {
            if ($(this).next().length) {
            } else {
                $(this).after("<label>请输入企业名称</label>");
            }
        } else if ($(this).val().split(" ").length != 1) {
            $(this).next().remove();
            $(this).after("<label>输入格式不能含有空格！</label>");
        } else if (!reg.test($(this).val())) {
            $(this).next().remove();
            $(this).after("<label>输入名称格式错误！</label>");
        } else if ($(this).val().length > 40) {
            $(this).next().remove();
            $(this).after("<label>输入名称过长！</label>");
        } else {
            $(this).next().remove();
        }
    });
    //验证企业地址
    $("#compAddress").blur(function () {
        if ($(this).val() == "") {
            if ($(this).next().length) {
            } else {
                $(this).after("<label>请输入企业地址</label>");
            }
        } else if ($(this).val().split(" ").length != 1) {
            $(this).next().remove();
            $(this).after("<label>输入格式不能含有空格！</label>");
        } else if ($(this).val().length > 100) {
            $(this).next().remove();
            $(this).after("<label>地址过长！</label>");
        } else {
            $(this).next().remove();
        }
    });
    //验证经营方式
    $("#busModel").blur(function () {
        if ($(this).val() == "") {
            if ($(this).next().length) {
            } else {
                $(this).after("<label>请输入经营方式</label>");
            }
        } else if ($(this).val().length > 100) {
            $(this).next().remove();
            $(this).after("<label>经营方式过长</label>");
        } else {
            $(this).next().remove();
        }
    });
    //验证法人姓名
    $("#legalName").blur(function () {
        var reg = /^([\u4e00-\u9fa5]{1,20}|[a-zA-Z\.\s]{1,20})$/;
        if ($(this).val() == "") {
            if ($(this).next().length) {
            } else {
                $(this).after("<label>请输入法人姓名</label>");
            }
        } /*else if ($(this).val().split(" ").length != 1) {
            $(this).next().remove();
            $(this).after("<label>输入格式不能含有空格！</label>");
        }*/ else if (!reg.test($(this).val())) {
            $(this).next().remove();
            $(this).after("<label>姓名格式错误!</label>");
        } else {
            $(this).next().remove();
        }
    });
    $("#legalName2").blur(function () {
        var reg = /^([\u4e00-\u9fa5]{1,20}|[a-zA-Z\.\s]{1,20})$/;
        if ($(this).val() == "") {
            if ($(this).next().length) {
            } else {
                $(this).after("<label>请输入法人姓名</label>");
            }
        } /*else if ($(this).val().split(" ").length != 1) {
            $(this).next().remove();
            $(this).after("<label>输入格式不能含有空格！</label>");
        } */else if (!reg.test($(this).val())) {
            $(this).next().remove();
            $(this).after("<label>姓名格式错误!</label>");
        } else {
            $(this).next().remove();
        }
    });
    //验证证件值
    $("#mainperson-id").blur(function () {
        var regular = /^[0-9a-zA-Z]{9,18}$/;
        if ($(this).val() == "") {
            $(this).next().remove();
            $(this).after("<label>请输入证件号码</label>");
        } else if ($(this).val().split(" ").length != 1) {
            $(this).next().remove();
            $(this).after("<label>输入格式不能含有空格！</label>");
        } else if (!regular.test($(this).val())) {
            $(this).next().remove();
            $(this).after("<label>输入证件格式错误</label>");
        } else {
            $(this).next().remove();
        }
    });
    //验证联系电话
    $("#mainperson-phone").blur(function () {
        var regTel1 = /^0?(13|14|15|16|17|18|19)[0-9]{9}$/;//手机号码
        var regTel2 = /^(([0\+]\d{2,3}-)?(0\d{2,3})-)(\d{7,8})(-(\d{3,}))?$/;//带区号的固定电话
        var regTel3 = /^(\d{7,8})(-(\d{3,}))?$/;//不带区号的固定电话
        if ($(this).val() == "") {
            $(this).next().remove();
            $(this).after("<label>请输入联系电话</label>");
        } else if ($(this).val().split(" ").length != 1) {
            $(this).next().remove();
            $(this).after("<label>输入格式不能含有空格</label>");
        } else if (!regTel1.test($(this).val()) && !regTel2.test($(this).val()) && !regTel3.test($(this).val())) {
            $(this).next().remove();
            $(this).after("<label>输入电话号码有误</label>");
        } else {
            $(this).next().remove();
        }
    });
    $("#securityperson").blur(function () {
        var reg = /^([\u4e00-\u9fa5]{1,20}|[a-zA-Z\.\s]{1,20})$/;
        if ($(this).val() == "") {
            if ($(this).next().length) {
            } else {
                $(this).after("<label>请输入法人姓名</label>");
            }
        } else if ($(this).val().split(" ").length != 1) {
            $(this).next().remove();
            $(this).after("<label>输入格式不能含有空格！</label>");
        } else if (!reg.test($(this).val())) {
            $(this).next().remove();
            $(this).after("<label>姓名格式错误!</label>");
        } else {
            $(this).next().remove();
        }
    });
    $("#securityperson-id").blur(function () {
        var regular = /^[0-9a-zA-Z]{9,18}$/;
        if ($(this).val() == "") {
            $(this).next().remove();
            $(this).after("<label>请输入证件号码</label>");
        } else if ($(this).val().split(" ").length != 1) {
            $(this).next().remove();
            $(this).after("<label>输入格式不能含有空格！</label>");
        } else if (!regular.test($(this).val())) {
            $(this).next().remove();
            $(this).after("<label>输入证件格式错误</label>");
        } else {
            $(this).next().remove();
        }
    });
    $("#securityperson-phone").blur(function () {
        var regTel1 = /^0?(13|14|15|16|17|18|19)[0-9]{9}$/;//手机号码
        var regTel2 = /^(([0\+]\d{2,3}-)?(0\d{2,3})-)(\d{7,8})(-(\d{3,}))?$/;//带区号的固定电话
        var regTel3 = /^(\d{7,8})(-(\d{3,}))?$/;//不带区号的固定电话
        if ($(this).val() == "") {
            $(this).next().remove();
            $(this).after("<label>请输入联系电话</label>");
        } else if ($(this).val().split(" ").length != 1) {
            $(this).next().remove();
            $(this).after("<label>输入格式不能含有空格</label>");
        } else if (!regTel1.test($(this).val()) && !regTel2.test($(this).val()) && !regTel3.test($(this).val())) {
            $(this).next().remove();
            $(this).after("<label>输入电话号码有误</label>");
        } else {
            $(this).next().remove();
        }
    });
    $("#allpeople").blur(function () {
        var reg = /^[0-9]{1,100}$/;
        if ($(this).val() == "") {
            if ($(this).next().length) {
            } else {
                $(this).after("<label>请输入企业总人数</label>");
            }
        } else if ($(this).val().split(" ").length != 1) {
            $(this).next().remove();
            $(this).after("<label>输入格式不能含有空格！</label>");
        } else if (!reg.test($(this).val())) {
            $(this).next().remove();
            $(this).after("<label>只能输入数字！</label>");
        } else if ($(this).val() <= 0) {
            $(this).next().remove();
            $(this).after("<label>人数应大于0</label>");
        } else if (parseInt($(this).val()) > parseInt($("#specialpeople").val())) {
            $(this).next().remove();
            $("#specialpeople").next().remove();
        } else {
            $(this).next().remove();
        }
    });
    $("#specialpeople").blur(function () {
        var reg = /^[0-9]{1,100}$/;
        if ($(this).val() == "") {
            if ($(this).next().length) {
            } else {
                $(this).after("<label>请输入特种从业人数</label>");
            }
        } else if ($(this).val().split(" ").length != 1) {
            $(this).next().remove();
            $(this).after("<label>输入格式不能含有空格！</label>");
        } else if (!reg.test($(this).val())) {
            $(this).next().remove();
            $(this).after("<label>只能输入数字！</label>");
        } else if ($(this).val() <= 0) {
            $(this).next().remove();
            $(this).after("<label>人数应大于0</label>");
        } else if (parseInt($(this).val()) > parseInt($("#allpeople").val())) {
            $(this).next().remove();
            $(this).after("<label>人数不能大于企业总人数</label>");
        } else {
            $(this).next().remove();
        }
    });

});

//按钮表单验证
$(function () {
    var reg3 = /^[0-9]{1,100}$/;
    $("#my-btn").click(function () {
        var reg = /^([\u4e00-\u9fa5a-zA-Z（）()]*)$/;
        var reg2 = /^([\u4e00-\u9fa5]{1,20}|[a-zA-Z\.\s]{1,20})$/;
        var regTel1 = /^0?(13|14|15|16|17|18|19)[0-9]{9}$/;//手机号码
        var regTel2 = /^(([0\+]\d{2,3}-)?(0\d{2,3})-)(\d{7,8})(-(\d{3,}))?$/;//带区号的固定电话
        var regTel3 = /^(\d{7,8})(-(\d{3,}))?$/;//不带区号的固定电话
        var regular = /^[0-9a-zA-Z]{9,18}$/;//证件识别
        var values = true;
        //计算文件总大小 ie下不适配
        var isIE = navigator.userAgent.match(/MSIE/) != null,
            isIE6 = navigator.userAgent.match(/MSIE 6.0/) != null;
        // if(!isIE){
        //     var s = 0;
        //     var x = document.getElementsByTagName('input');
        //     //遍历文件
        //     for(var i = 0;i < x.length;i++){
        //         if(x[i].getAttribute('type') === "file"){
        //             if(x[i].files.length > 0 ){
        //                 //获取文件大小
        //                 s = s + x[i].files[0].size;
        //             }
        //         }
        //     }
        //     //计算总共文件的大小M
        //     s = s / 1024 / 1024;
        //     if(s > 20){
        //         alert("上传文件总大小不能大于20M");
        //         return false;
        //     }
        // }

        //验证单位名称
        if ($("#companyname").val() == "") {
            values = false;
            if ($("#companyname").next().length) {
            } else {
                $("#companyname").after("<label>请输入单位名称</label>");
            }
        } else if ($("#companyname").val().split(" ").length != 1) {
            values = false;
            $("#companyname").next().remove();
            $("#companyname").after("<label>输入格式不能含有空格！</label>");
        }
        // else if (!reg.test($("#companyname").val())) {
        //     $("#companyname").next().remove();
        //     $("#companyname").after("<label>单位名称格式错误！</label>");
        // }
        else if ($("#companyname").val().length > 40) {
            $("#companyname").next().remove();
            $("#companyname").after("<label>单位名称过长！</label>");
        } else {
            $("#companyname").next().remove();
        }

        //增加判断是否为统一社会信用代码的值（1：统一代码；2：其他代码）,是统一码才继续判断下面的规则
        var codeCheck = $('#stampRecord input[name="stampRecord.isSoleCode"]:checked').val();

        if (codeCheck == 1) {
            //验证统一社会代号
            if ($("#unifiedcode").val() == "") {
                values = false;
                if ($("#unifiedcode").next().length) {
                } else {
                    $("#unifiedcode").after("<label>请输入统一代码</label>");
                }
            } else if ($("#unifiedcode").val().split(" ").length != 1) {
                values = false;
                $("#unifiedcode").next().remove();
                $("#unifiedcode").after("<label>输入格式不能含有空格！</label>");
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
                    /* if(dif == 1){
                     dif = 30;
                     }
                     console.log(dif);
                     console.log(numArray[17]);
                     最后一位验证*/


                    if(dif==31){
                        dif=0;
                    }

                    if (dif != numArray[17]) {
                        values = false;
                        $("#unifiedcode").next().remove();
                        $("#unifiedcode").after("<label>企业统一信用代码校验不通过</label>");
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
                        values = false;
                        $("#unifiedcode").next().remove();
                        $("#unifiedcode").after("<label>企业统一信用代码校验不通过</label>");
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
                        values = false;
                        $("#unifiedcode").next().remove();
                        $("#unifiedcode").after("<label>企业统一信用代码校验不通过</label>");
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
                        values = false;
                        $("#unifiedcode").next().remove();
                        $("#unifiedcode").after("<label>企业统一信用代码校验不通过</label>");
                    }
                } else {
                    values = false;
                    $("#unifiedcode").next().remove();
                    $("#unifiedcode").after("<label>企业统一信用代码格式错误</label>");
                }
            }
        }

        //验证刻章单位地址
        if ($("#companyaddress").val() == "") {
            values = false;
            if ($("#companyaddress").next().length) {
            } else {
                $("#companyaddress").after("<label>请输入刻章单位地址</label>");
            }
        } else if ($("#companyaddress").val().split(" ").length != 1) {
            values = false;
            $("#companyaddress").next().remove();
            $("#companyaddress").after("<label>输入格式不能含有空格！</label>");
        } else if ($("#companyaddress").val().length > 100) {
            values = false;
            $("#companyaddress").next().remove();
            $("#companyaddress").after("<label>地址过长！</label>");
        } else {
            $("#companyaddress").next().remove();
        }

        //验证法人姓名
        if ($("#legalName").val() == "") {
            values = false;
            if ($("#legalName").next().length) {
            } else {
                $("#legalName").after("<label>请输入法人姓名</label>");
            }
        } /*else if ($("#legalName").val().split(" ").length != 1) {
            values = false;
            $("#legalName").next().remove();
            $("#legalName").after("<label>输入格式不能含有空格！</label>");
        }*/ else if (!reg2.test($("#legalName").val())) {
            values = false;
            $("#legalName").next().remove();
            $("#legalName").after("<label>输入姓名格式错误！</label>");
        } else {
            $("#legalName").next().remove();
        }
        //验证经办人姓名
        if ($("#personname").val() == "") {
            values = false;
            if ($("#personname").next().length) {
            } else {

                $("#personname").after("<label>请输入经办人姓名</label>");
            }
        } /*else if ($("#personname").val().split(" ").length != 1) {
            values = false;
            $("#personname").next().remove();
            $("#personname").after("<label>输入格式不能含有空格！</label>");
        }*/ else if (!reg2.test($("#personname").val())) {
            values = false;
            $("#personname").next().remove();
            $("#personname").after("<label>输入姓名格式错误！</label>");
        } else {
            $("#personname").next().remove();
        }
        //验证法人证件
        // var typeValue = $('#persionType option:selected').attr('value');
        var typeValue = $("#legalCertType").find("option:selected").val();


        if (typeValue==1) {//身份证
            var regular = /^[1-9]\d{5}[1-9]\d{3}((0\d)|(1[0-2]))(([0|1|2]\d)|3[0-1])((\d{4})|\d{3}[A-Z])$/;
            if ($('#legalid').val() == "") {
                values = false;
                $('#legalid').next().remove();
                $('#legalid').after("<label>请输入证件号码</label>");
            } else if ($('#legalid').val().split(" ").length != 1) {
                values = false;
                $('#legalid').next().remove();
                $('#legalid').after("<label>输入格式不能含有空格！</label>");
            } else if (!regular.test($('#legalid').val())) {
                values = false;
                $('#legalid').next().remove();
                $('#legalid').after("<label>输入身份证件格式错误</label>");
            } else {
                $('#legalid').next().remove();
            }
        }else if(typeValue==2) {//港澳通行证
            var regular = /^[HMChm]{1}([0-9]{10}|[0-9]{8})$/;
            var regular2 = /^((\s?[A-Za-z])|([A-Za-z]{2}))\d{6}(\([0-9aA]\)|[0-9aA])$/;//香港--身份证（本国人）
            var regular3 = /^[1|5|7][0-9]{6}\([0-9Aa]\)$/;//澳门--身份证（本国人）
            if ($('#legalid').val() == "") {
                values = false;
                $('#legalid').next().remove();
                $('#legalid').after("<label>请输入证件号码</label>");
            } else if ($('#legalid').val().split(" ").length != 1) {
                values = false;
                $('#legalid').next().remove();
                $('#legalid').after("<label>输入格式不能含有空格！</label>");
            } else if (!regular.test($('#legalid').val()) && !regular2.test($('#legalid').val()) && !regular3.test($('#legalid').val())) {
                values = false;
                $('#legalid').next().remove();
                $('#legalid').after("<label>输入港澳通行证/港澳身份证(本国人)格式错误</label>");
            } else {
                $('#legalid').next().remove();
            }
        }else if(typeValue==3) {//台湾通行证
            var regular = /^[0-9]{8}$/;
            var regular2 = /^[0-9]{10}$/;
            var regular3 = /^[a-zA-Z][0-9]{9}$/;//台湾身份证
            if ($('#legalid').val() == "") {
                values = false;
                $('#legalid').next().remove();
                $('#legalid').after("<label>请输入证件号码</label>");
            } else if ($('#legalid').val().split(" ").length != 1) {
                values = false;
                $('#legalid').next().remove();
                $('#legalid').after("<label>输入格式不能含有空格！</label>");
            } else if (!regular.test($('#legalid').val()) && !regular2.test($('#legalid').val()) && !regular3.test($('#legalid').val())) {
                values = false;
                $('#legalid').next().remove();
                $('#legalid').after("<label>输入台湾通行证/台湾身份证格式错误</label>");
            } else {
                $('#legalid').next().remove();
            }
        }else if(typeValue==4){// 护照验证
            var regular = /^[a-zA-Z]{5,17}$/;
            var regular2 = /^[a-zA-Z0-9]{5,17}$/;
            if ($('#legalid').val() == "") {
                values = false;
                $('#legalid').next().remove();
                $('#legalid').after("<label>请输入证件号码</label>");
            } else if ($('#legalid').val().split(" ").length != 1) {
                values = false;
                $('#legalid').next().remove();
                $('#legalid').after("<label>输入格式不能含有空格！</label>");
            } else if (!regular.test($('#legalid').val()) && !regular2.test($('#legalid').val())) {
                values = false;
                $('#legalid').next().remove();
                $('#legalid').after("<label>输入护照证件格式错误</label>");
            } else {
                $('#legalid').next().remove();
            }
        }
        //验证经办人证件
        var a = $('#persionType option:selected').attr('value');

        if (a==1) {//身份证
            var regular = /^[0-9a-zA-Z]{9,18}$/;
            if ($("#personid").val() == "") {values = false;
                $("#personid").next().remove();
                $("#personid").after("<label>请输入证件号码</label>");
            } else if ($("#personid").val().split(" ").length != 1) {
                values = false;
                $("#personid").next().remove();
                $("#personid").after("<label>输入格式不能含有空格！</label>");
            } else if (!regular.test($("#personid").val())) {
                values = false;
                $("#personid").next().remove();
                $("#personid").after("<label>输入身份证件格式错误</label>");
            } else {
                $("#personid").next().remove();
            }
        }else if(a==2) {//港澳通行证
            var regular = /^[HMChm]{1}([0-9]{10}|[0-9]{8})$/;
            var regular2 = /^((\s?[A-Za-z])|([A-Za-z]{2}))\d{6}(\([0-9aA]\)|[0-9aA])$/;//香港--身份证（本国人）
            var regular3 = /^[1|5|7][0-9]{6}\([0-9Aa]\)$/;//澳门--身份证（本国人）
            if ($("#personid").val() == "") {
                values = false;
                $("#personid").next().remove();
                $("#personid").after("<label>请输入证件号码</label>");
            } else if ($("#personid").val().split(" ").length != 1) {
                values = false;
                $("#personid").next().remove();
                $("#personid").after("<label>输入格式不能含有空格！</label>");
            } else if (!regular.test($("#personid").val()) && !regular2.test($("#personid").val()) && !regular3.test($("#personid").val())) {
                values = false;
                $("#personid").next().remove();
                $("#personid").after("<label>输入港澳通行证/港澳身份证(本国人)格式错误</label>");
            } else {
                $("#personid").next().remove();
            }
        }else if(a==3) {//台湾通行证
            var regular = /^[0-9]{8}$/;
            var regular2 = /^[0-9]{10}$/;
            var regular3 = /^[a-zA-Z][0-9]{9}$/;//台湾身份证
            if ($("#personid").val() == "") {
                values = false;
                $("#personid").next().remove();
                $("#personid").after("<label>请输入证件号码</label>");
            } else if ($("#personid").val().split(" ").length != 1) {
                values = false;
                $("#personid").next().remove();
                $("#personid").after("<label>输入格式不能含有空格！</label>");
            } else if (!regular.test($("#personid").val()) && !regular2.test($("#personid").val()) && !regular3.test($("#personid").val())) {
                values = false;
                $("#personid").next().remove();
                $("#personid").after("<label>输入台湾通行证/台湾身份证格式错误</label>");
            } else {
                $("#personid").next().remove();
            }
        }else if(a==4){// 护照验证
            var regular = /^[a-zA-Z]{5,17}$/;
            var regular2 = /^[a-zA-Z0-9]{5,17}$/;
            if ($("#personid").val() == "") {
                values = false;
                $("#personid").next().remove();
                $("#personid").after("<label>请输入证件号码</label>");
            } else if ($("#personid").val().split(" ").length != 1) {
                values = false;
                $("#personid").next().remove();
                $("#personid").after("<label>输入格式不能含有空格！</label>");
            } else if (!regular.test($("#personid").val()) && !regular2.test($("#personid").val())) {
                values = false;
                $("#personid").next().remove();
                $("#personid").after("<label>输入护照证件格式错误</label>");
            } else {
                $("#personid").next().remove();
            }
        }
        //法人电话
        if ($("#legalphone").val() == "") {
            values = false;
            $("#legalphone").next().remove();
            $("#legalphone").after("<label>请输入联系电话</label>");
        } else if ($("#legalphone").val().split(" ").length != 1) {
            values = false;
            $("#legalphone").next().remove();
            $("#legalphone").after("<label>输入格式不能含有空格</label>");
        } else if (!regTel1.test($("#legalphone").val())) {
            values = false;
            $("#legalphone").next().remove();
            $("#legalphone").after("<label>输入电话号码有误</label>");
        } else {
            $("#legalphone").next().remove();
        }
        //单位电话
        /*if ($("#companyphone").val() == "") {
            values = false;
            $("#companyphone").next().remove();
            $("#companyphone").after("<label>请输入联系电话</label>");
        } else if ($("#companyphone").val().split(" ").length != 1) {
            values = false;
            $("#companyphone").next().remove();
            $("#companyphone").after("<label>输入格式不能含有空格</label>");
        } else if (!regTel1.test($("#companyphone").val())&& !regTel2.test($(this).val()) && !regTel3.test($(this).val())) {
            values = false;
            $("#companyphone").next().remove();
            $("#companyphone").after("<label>输入电话号码有误</label>");
        } else {
            $("#companyphone").next().remove();
        }*/
        //经办人电话
        if ($("#personphone").val() == "") {
            values = false;
            $("#personphone").next().remove();
            $("#personphone").after("<label>请输入联系电话</label>");
        } else if ($("#personphone").val().split(" ").length != 1) {
            values = false;
            $("#personphone").next().remove();
            $("#personphone").after("<label>输入格式不能含有空格</label>");
        } else if (!regTel1.test($("#personphone").val())) {
            values = false;
            $("#personphone").next().remove();
            $("#personphone").after("<label>输入电话号码有误</label>");
        } else {
            $("#personphone").next().remove();
        }

        if (values) {
            $("#my-btn").attr("disabled", "disabled");
            $("#stampRecord").submit();
        } else {
            var sc = $(window).scrollTop();
            $('body,html').animate({scrollTop: 0}, 500);
        }
    });
    $("#submitbtn").click(function () {
        var reg = /^([\u4e00-\u9fa5a-zA-Z]*)$/;
        var reg2 = /^([\u4e00-\u9fa5]{1,20}|[a-zA-Z\.\s]{1,20})$/;
        var regTel1 = /^0?(13|14|15|16|17|18|19)[0-9]{9}$/;//手机号码
        var regTel2 = /^(([0\+]\d{2,3}-)?(0\d{2,3})-)(\d{7,8})(-(\d{3,}))?$/;//带区号的固定电话
        var regTel3 = /^(\d{7,8})(-(\d{3,}))?$/;//不带区号的固定电话
        var regular = /^[0-9a-zA-Z]{9,18}$/;
        var values = true;
        //计算文件总大小 ie下不适配
        var isIE = navigator.userAgent.match(/MSIE/) != null,
            isIE6 = navigator.userAgent.match(/MSIE 6.0/) != null;
        // if(!isIE){
        //     var s = 0;
        //     var x = document.getElementsByTagName('input');
        //     //遍历文件
        //     for(var i = 0;i < x.length;i++){
        //         if(x[i].getAttribute('type') === "file"){
        //             if(x[i].files.length > 0 ){
        //                 //获取文件大小
        //                 s = s + x[i].files[0].size;
        //             }
        //         }
        //     }
        //     //计算总共文件的大小M
        //     s = s / 1024 / 1024;
        //     if(s > 20){
        //         alert("上传文件总大小不能大于20M");
        //         return false;
        //     }
        // }
        //验证统一社会代号
        //增加判断是否为统一社会信用代码的值（1：统一代码；2：其他代码）,是统一码才继续判断下面的规则
        var codeCheck = $('#stampRecord input[name="stampRecord.isSoleCode"]:checked').val();
        if (codeCheck == 1) {
            if ($("#unifiedcode").val() == "") {
                values = false;
                if ($("#unifiedcode").next().length) {
                } else {
                    $("#unifiedcode").after("<label>请输入统一代码</label>");
                }
            } else if ($("#unifiedcode").val().split(" ").length != 1) {
                values = false;
                $("#unifiedcode").next().remove();
                $("#unifiedcode").after("<label>输入格式不能有空格</label>");
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
                    /* if(dif == 1){
                     dif = 30;
                     }
                     console.log(dif);
                     console.log(numArray[17]);
                     最后一位验证*/
                    if (dif != numArray[17]) {
                        values = false;
                        $("#unifiedcode").next().remove();
                        $("#unifiedcode").after("<label>企业统一信用代码校验不通过</label>");
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
                        values = false;
                        $("#unifiedcode").next().remove();
                        $("#unifiedcode").after("<label>企业统一信用代码校验不通过</label>");
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
                        values = false;
                        $("#unifiedcode").next().remove();
                        $("#unifiedcode").after("<label>企业统一信用代码校验不通过</label>");
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
                        values = false;
                        $("#unifiedcode").next().remove();
                        $("#unifiedcode").after("<label>企业统一信用代码校验不通过</label>");
                    }
                } else {
                    values = false;
                    $("#unifiedcode").next().remove();
                    $("#unifiedcode").after("<label>企业统一信用代码格式错误</label>");
                }
            }
        }else {
            $("#unifiedcode").next().remove();
        }

        if ($("#enterprise_name").val() == "") {
            values = false;
            if ($("#enterprise_name").next().length) {
            } else {
                $("#enterprise_name").after("<label>请输入企业名称</label>");
            }
        } else if ($("#enterprise_name").val().split(" ").length != 1) {
            values = false;
            $("#enterprise_name").next().remove();
            $("#enterprise_name").after("<label>输入格式不能有空格</label>");
        } else if (!reg.test($("#enterprise_name").val())) {
            values = false;
            $("#enterprise_name").next().remove();
            $("#enterprise_name").after("<label>单位名称格式错误！</label>");
        } else if ($("#enterprise_name").val().length > 40) {
            values = false;
            $("#enterprise_name").next().remove();
            $("#enterprise_name").after("<label>单位名称过长！</label>");
        } else {
            $("#enterprise_name").next().remove();
        }
        if ($("#compAddress").val() == "") {
            values = false;
            if ($("#compAddress").next().length) {
            } else {
                $("#compAddress").after("<label>请输入企业地址</label>");
            }
        } else if ($("#compAddress").val().split(" ").length != 1) {
            values = false;
            $("#compAddress").next().remove();
            $("#compAddress").after("<label>输入格式不能有空格</label>");
        } else if ($("#compAddress").val().length > 100) {
            values = false;
            $("#compAddress").next().remove();
            $("#compAddress").after("<label>地址过长！</label>");
        } else {
            $("#compAddress").next().remove();
        }
        if ($("#busModel").val() == "") {
            values = false;
            if ($("#busModel").next().length) {
            } else {
                $("#busModel").after("<label>请输入经营方式</label>");
            }
        } else if ($("#busModel").val().split(" ").length != 1) {
            values = false;
            $("#busModel").next().remove();
            $("#busModel").after("<label>输入格式不能有空格</label>");
        } else if ($("#busModel").val().length > 100) {
            values = false;
            $("#busModel").next().remove();
            $("#busModel").after("<label>经营方式过长</label>");
        } else {
            $("#busModel").next().remove();
        }
        if ($("#legalName").val() == "") {
            values = false;
            if ($("#legalName").next().length) {
            } else {
                $("#legalName").after("<label>请输入法人姓名</label>");
            }
        } /*else if ($("#legalName").val().split(" ").length != 1) {
            values = false;
            $("#legalName").next().remove();
            $("#legalName").after("<label>输入格式不能有空格</label>");
        } */else if (!reg2.test($("#legalName").val())) {
            values = false;
            $("#legalName").next().remove();
            $("#legalName").after("<label>输入姓名格式错误！</label>");
        } else {
            $("#legalName").next().remove();
        }
        if ($("#mainperson-id").val() == "") {
            values = false;
            $("#mainperson-id").next().remove();
            $("#mainperson-id").after("<label>请输入法人证件号码</label>");
        } else if ($("#mainperson-id").val().split(" ").length != 1) {
            values = false;
            $("#mainperson-id").next().remove();
            $("#mainperson-id").after("<label>输入格式不能有空格</label>");
        } else if (!regular.test($("#mainperson-id").val())) {
            values = false;
            $("#mainperson-id").next().remove();
            $("#mainperson-id").after("<label>输入证件格式错误</label>");
        } else {
            $("#mainperson-id").next().remove();
        }
        if ($("#mainperson-phone").val() == "") {
            values = false;
            $("#mainperson-phone").next().remove();
            $("#mainperson-phone").after("<label>请输入法人联系电话</label>");
        } else if ($("#mainperson-phone").val().split(" ").length != 1) {
            values = false;
            $("#mainperson-phone").next().remove();
            $("#mainperson-phone").after("<label>输入格式不能有空格</label>");
        } else if (!regTel1.test($("#mainperson-phone").val()) && !regTel2.test($("#mainperson-phone").val()) && !regTel3.test($("#mainperson-phone").val())) {
            values = false;
            $("#mainperson-phone").next().remove();
            $("#mainperson-phone").after("<label>输入电话格式错误</label>");
        } else {
            $("#mainperson-phone").next().remove();
        }
        if ($("#securityperson").val() == "") {
            values = false;
            if ($("#securityperson").next().length) {
            } else {
                $("#securityperson").after("<label>请输入治安负责人姓名</label>");
            }
        } else if ($("#securityperson").val().split(" ").length != 1) {
            values = false;
            $("#securityperson").next().remove();
            $("#securityperson").after("<label>输入格式不能有空格</label>");
        } else if (!reg2.test($("#securityperson").val())) {
            values = false;
            $("#securityperson").next().remove();
            $("#securityperson").after("<label>输入姓名格式错误！</label>");
        } else {
            $("#securityperson").next().remove();
        }
        if ($("#securityperson-id").val() == "") {
            values = false;
            $("#securityperson-id").next().remove();
            $("#securityperson-id").after("<label>请输入治安负责人证件号码</label>");
        } else if ($("#securityperson-id").val().split(" ").length != 1) {
            values = false;
            $("#securityperson-id").next().remove();
            $("#securityperson-id").after("<label>输入格式不能有空格</label>");
        } else if (!regular.test($("#securityperson-id").val())) {
            values = false;
            $("#securityperson-id").next().remove();
            $("#securityperson-id").after("<label>输入证件格式错误</label>");
        } else {
            $("#securityperson-id").next().remove();
        }
        if ($("#securityperson-phone").val() == "") {
            values = false;
            $("#securityperson-phone").next().remove();
            $("#securityperson-phone").after("<label>请输入负责人联系电话</label>");
        } else if ($("#securityperson-phone").val().split(" ").length != 1) {
            values = false;
            $("#securityperson-phone").next().remove();
            $("#securityperson-phone").after("<label>输入格式不能有空格</label>");
        } else if (!regTel1.test($("#securityperson-phone").val()) && !regTel2.test($("#securityperson-phone").val()) && !regTel3.test($("#securityperson-phone").val())) {
            values = false;
            $("#securityperson-phone").next().remove();
            $("#securityperson-phone").after("<label>输入电话格式错误</label>");
        } else {
            $("#securityperson-phone").next().remove();
        }
        if ($("#allpeople").val() == "") {
            values = false;
            if ($("#allpeople").next().length) {
            } else {
                $("#allpeople").after("<label>请输入企业总人数</label>");
            }
        } else if ($("#allpeople").val().split(" ").length != 1) {
            values = false;
            $("#allpeople").next().remove();
            $("#allpeople").after("<label>输入格式不能有空格</label>");
        } else if (!reg3.test($("#allpeople").val())) {
            values = false;
            $("#allpeople").next().remove();
            $("#allpeople").after("<label>只能输入数字</label>");
        } else if ($("#allpeople").val() <= 0) {
            values = false;
            $("#allpeople").next().remove();
            $("#allpeople").after("<label>人数应大于0</label>");
        } else {
            $("#allpeople").next().remove();
        }
        if ($("#specialpeople").val() == "") {
            values = false;
            if ($("#specialpeople").next().length) {
            } else {
                $("#specialpeople").after("<label>请输入特种从业人数</label>");
            }
        } else if ($("#specialpeople").val().split(" ").length != 1) {
            values = false;
            $("#specialpeople").next().remove();
            $("#specialpeople").after("<label>输入格式不能有空格</label>");
        } else if (!reg3.test($("#specialpeople").val())) {
            values = false;
            $("#specialpeople").next().remove();
            $("#specialpeople").after("<label>只能输入数字</label>");
        } else if ($("#specialpeople").val() <= 0) {
            values = false;
            $("#specialpeople").next().remove();
            $("#specialpeople").after("<label>人数应大于0</label>");
        } else if (parseInt($("#specialpeople").val()) > parseInt($("#allpeople").val())) {
            values = false;
            $("#specialpeople").next().remove();
            $("#specialpeople").after("<label>人数不能大于企业总人数</label>");
        } else {
            $("#specialpeople").next().remove();
        }
        if (values) {
            $("#submitbtn").attr("disabled", "disabled");
            $("#mainform").submit();
        } else {
            var sc = $(window).scrollTop();
            $('body,html').animate({scrollTop: 0}, 500);
        }
    });
    //submitbtn2 的点击事件
    $("#submitbtn2").click(function () {
        var reg = /^([\u4e00-\u9fa5a-zA-Z]*)$/;
        var reg2 = /^([\u4e00-\u9fa5]{1,20}|[a-zA-Z\.\s]{1,20})$/;
        var regTel1 = /^0?(13|14|15|16|17|18|19)[0-9]{9}$/;//手机号码
        var regTel2 = /^(([0\+]\d{2,3}-)?(0\d{2,3})-)(\d{7,8})(-(\d{3,}))?$/;//带区号的固定电话
        var regTel3 = /^(\d{7,8})(-(\d{3,}))?$/;//不带区号的固定电话
        var regular = /^[0-9a-zA-Z]{9,18}$/;
        var values = true;
        //计算文件总大小 ie下不适配
        var isIE = navigator.userAgent.match(/MSIE/) != null,
            isIE6 = navigator.userAgent.match(/MSIE 6.0/) != null;
        // if(!isIE){
        //     var s = 0;
        //     var x = document.getElementsByTagName('input');
        //     //遍历文件
        //     for(var i = 0;i < x.length;i++){
        //         if(x[i].getAttribute('type') === "file"){
        //             if(x[i].files.length > 0 ){
        //                 //获取文件大小
        //                 s = s + x[i].files[0].size;
        //             }
        //         }
        //     }
        //     //计算总共文件的大小M
        //     s = s / 1024 / 1024;
        //     if(s > 20){
        //         alert("上传文件总大小不能大于20M");
        //         return false;
        //     }
        // }
        if ($("#legalName2").val() == "") {
            values = false;
            if ($("#legalName2").next().length) {
            } else {
                $("#legalName2").after("<label>请输入法人姓名</label>");
            }
        } /*else if ($("#legalName2").val().split(" ").length != 1) {
            values = false;
            $("#legalName2").next().remove();
            $("#legalName2").after("<label>输入格式不能有空格</label>");
        }*/ else if (!reg2.test($("#legalName2").val())) {
            values = false;
            $("#legalName2").next().remove();
            $("#legalName2").after("<label>输入姓名格式错误！</label>");
        } else {
            $("#legalName2").next().remove();
        }
        if ($("#mainperson-id").val() == "") {
            values = false;
            $("#mainperson-id").next().remove();
            $("#mainperson-id").after("<label>请输入法人证件号码</label>");
        } else if ($("#mainperson-id").val().split(" ").length != 1) {
            values = false;
            $("#mainperson-id").next().remove();
            $("#mainperson-id").after("<label>输入格式不能有空格</label>");
        } else if (!regular.test($("#mainperson-id").val())) {
            values = false;
            $("#mainperson-id").next().remove();
            $("#mainperson-id").after("<label>输入证件格式错误</label>");
        } else {
            $("#mainperson-id").next().remove();
        }
        if ($("#mainperson-phone").val() == "") {
            values = false;
            $("#mainperson-phone").next().remove();
            $("#mainperson-phone").after("<label>请输入法人联系电话</label>");
        } else if ($("#mainperson-phone").val().split(" ").length != 1) {
            values = false;
            $("#mainperson-phone").next().remove();
            $("#mainperson-phone").after("<label>输入格式不能有空格</label>");
        } else if (!regTel1.test($("#mainperson-phone").val()) && !regTel2.test($("#mainperson-phone").val()) && !regTel3.test($("#mainperson-phone").val())) {
            values = false;
            $("#mainperson-phone").next().remove();
            $("#mainperson-phone").after("<label>输入电话格式错误</label>");
        } else {
            $("#mainperson-phone").next().remove();
        }
        if ($("#securityperson").val() == "") {
            values = false;
            if ($("#securityperson").next().length) {
            } else {
                $("#securityperson").after("<label>请输入治安负责人姓名</label>");
            }
        } else if ($("#securityperson").val().split(" ").length != 1) {
            values = false;
            $("#securityperson").next().remove();
            $("#securityperson").after("<label>输入格式不能有空格</label>");
        } else if (!reg2.test($("#securityperson").val())) {
            values = false;
            $("#securityperson").next().remove();
            $("#securityperson").after("<label>输入姓名格式错误！</label>");
        } else {
            $("#securityperson").next().remove();
        }
        if ($("#securityperson-id").val() == "") {
            values = false;
            $("#securityperson-id").next().remove();
            $("#securityperson-id").after("<label>请输入治安负责人证件号码</label>");
        } else if ($("#securityperson-id").val().split(" ").length != 1) {
            values = false;
            $("#securityperson-id").next().remove();
            $("#securityperson-id").after("<label>输入格式不能有空格</label>");
        } else if (!regular.test($("#securityperson-id").val())) {
            values = false;
            $("#securityperson-id").next().remove();
            $("#securityperson-id").after("<label>输入证件格式错误</label>");
        } else {
            $("#securityperson-id").next().remove();
        }
        if ($("#securityperson-phone").val() == "") {
            values = false;
            $("#securityperson-phone").next().remove();
            $("#securityperson-phone").after("<label>请输入负责人联系电话</label>");
        } else if ($("#securityperson-phone").val().split(" ").length != 1) {
            values = false;
            $("#securityperson-phone").next().remove();
            $("#securityperson-phone").after("<label>输入格式不能有空格</label>");
        } else if (!regTel1.test($("#securityperson-phone").val()) && !regTel2.test($("#securityperson-phone").val()) && !regTel3.test($("#securityperson-phone").val())) {
            values = false;
            $("#securityperson-phone").next().remove();
            $("#securityperson-phone").after("<label>输入电话格式错误</label>");
        } else {
            $("#securityperson-phone").next().remove();
        }
        if ($("#allpeople").val() == "") {
            values = false;
            if ($("#allpeople").next().length) {
            } else {
                $("#allpeople").after("<label>请输入企业总人数</label>");
            }
        } else if ($("#allpeople").val() <= 0) {
            values = false;
            $("#allpeople").next().remove();
            $("#allpeople").after("<label>人数应大于0</label>");
        } else {
            $("#allpeople").next().remove();
        }
        if ($("#specialpeople").val() == "") {
            values = false;
            if ($("#specialpeople").next().length) {
            } else {
                $("#specialpeople").after("<label>请输入特种从业人数</label>");
            }
        } else if ($("#specialpeople").val() <= 0) {
            values = false;
            $("#specialpeople").next().remove();
            $("#specialpeople").after("<label>人数应大于0</label>");
        } else if (parseInt($("#specialpeople").val()) > parseInt($("#allpeople").val())) {
            values = false;
            $("#specialpeople").next().remove();
            $("#specialpeople").after("<label>人数不能大于企业总人数</label>");
        } else {
            $("#specialpeople").next().remove();
        }
        if (values) {
            $("#submitbtn").attr("disabled", "disabled");
            $("#mainform").submit();
        } else {
            var sc = $(window).scrollTop();
            $('body,html').animate({scrollTop: 0}, 500);
        }
    });
    //my-btn2点击事件
    $("#my-btn2").click(function () {
        var reg = /^([\u4e00-\u9fa5a-zA-Z]*)$/;
        var reg2 = /^([\u4e00-\u9fa5]{1,20}|[a-zA-Z\.\s]{1,20})$/;
        var regTel1 = /^0?(13|14|15|16|17|18|19)[0-9]{9}$/;//手机号码
        var regTel2 = /^(([0\+]\d{2,3}-)?(0\d{2,3})-)(\d{7,8})(-(\d{3,}))?$/;//带区号的固定电话
        var regTel3 = /^(\d{7,8})(-(\d{3,}))?$/;//不带区号的固定电话
        var regular = /^[0-9a-zA-Z]{9,18}$/;
        var values = true;
        //计算文件总大小 ie下不适配
        var isIE = navigator.userAgent.match(/MSIE/) != null,
            isIE6 = navigator.userAgent.match(/MSIE 6.0/) != null;
        // if(!isIE){
        //     var s = 0;
        //     var x = document.getElementsByTagName('input');
        //     //遍历文件
        //     for(var i = 0;i < x.length;i++){
        //         if(x[i].getAttribute('type') === "file"){
        //             if(x[i].files.length > 0 ){
        //                 //获取文件大小
        //                 s = s + x[i].files[0].size;
        //             }
        //         }
        //     }
        //     //计算总共文件的大小M
        //     s = s / 1024 / 1024;
        //     if(s > 20){
        //         alert("上传文件总大小不能大于20M");
        //         return false;
        //     }
        // }
        //验证经办人姓名
        if ($("#personname").val() == "") {
            values = false;
            if ($("#personname").next().length) {
            } else {

                $("#personname").after("<label>请输入经办人姓名</label>");
            }
        } /*else if ($("#personname").val().split(" ").length != 1) {
            values = false;
            $("#personname").next().remove();
            $("#personname").after("<label>输入格式不能含有空格！</label>");
        }*/ else if (!reg2.test($("#personname").val())) {
            values = false;
            $("#personname").next().remove();
            $("#personname").after("<label>输入姓名格式错误！</label>");
        } else {
            $("#personname").next().remove();
        }

        //验证经办人证件
        var a = $('#persionType option:selected').attr('value');

        if (a==1) {//身份证
            var regular = /^[0-9a-zA-Z]{9,18}$/;
            if ($("#personid").val() == "") {values = false;
                $("#personid").next().remove();
                $("#personid").after("<label>请输入证件号码</label>");
            } else if ($("#personid").val().split(" ").length != 1) {
                values = false;
                $("#personid").next().remove();
                $("#personid").after("<label>输入格式不能含有空格！</label>");
            } else if (!regular.test($("#personid").val())) {
                values = false;
                $("#personid").next().remove();
                $("#personid").after("<label>输入身份证件格式错误</label>");
            } else {
                $("#personid").next().remove();
            }
        }else if(a==2) {//港澳通行证
            var regular = /^[HMChm]{1}([0-9]{10}|[0-9]{8})$/;
            var regular2 = /^((\s?[A-Za-z])|([A-Za-z]{2}))\d{6}(\([0-9aA]\)|[0-9aA])$/;//香港--身份证（本国人）
            var regular3 = /^[1|5|7][0-9]{6}\([0-9Aa]\)$/;//澳门--身份证（本国人）
            if ($("#personid").val() == "") {
                values = false;
                $("#personid").next().remove();
                $("#personid").after("<label>请输入证件号码</label>");
            } else if ($("#personid").val().split(" ").length != 1) {
                values = false;
                $("#personid").next().remove();
                $("#personid").after("<label>输入格式不能含有空格！</label>");
            } else if (!regular.test($("#personid").val()) && !regular2.test($("#personid").val()) && !regular3.test($("#personid").val())) {
                values = false;
                $("#personid").next().remove();
                $("#personid").after("<label>输入港澳通行证/港澳身份证(本国人)格式错误</label>");
            } else {
                $("#personid").next().remove();
            }
        }else if(a==3) {//台湾通行证
            var regular = /^[0-9]{8}$/;
            var regular2 = /^[0-9]{10}$/;
            var regular3 = /^[a-zA-Z][0-9]{9}$/;//台湾身份证
            if ($("#personid").val() == "") {
                values = false;
                $("#personid").next().remove();
                $("#personid").after("<label>请输入证件号码</label>");
            } else if ($("#personid").val().split(" ").length != 1) {
                values = false;
                $("#personid").next().remove();
                $("#personid").after("<label>输入格式不能含有空格！</label>");
            } else if (!regular.test($("#personid").val()) && !regular2.test($("#personid").val()) && !regular3.test($("#personid").val())) {
                values = false;
                $("#personid").next().remove();
                $("#personid").after("<label>输入台湾通行证/台湾身份证格式错误</label>");
            } else {
                $("#personid").next().remove();
            }
        }else if(a==4){// 护照验证
            var regular = /^[a-zA-Z]{5,17}$/;
            var regular2 = /^[a-zA-Z0-9]{5,17}$/;
            if ($("#personid").val() == "") {
                values = false;
                $("#personid").next().remove();
                $("#personid").after("<label>请输入证件号码</label>");
            } else if ($("#personid").val().split(" ").length != 1) {
                values = false;
                $("#personid").next().remove();
                $("#personid").after("<label>输入格式不能含有空格！</label>");
            } else if (!regular.test($("#personid").val()) && !regular2.test($("#personid").val())) {
                values = false;
                $("#personid").next().remove();
                $("#personid").after("<label>输入护照证件格式错误</label>");
            } else {
                $("#personid").next().remove();
            }
        }

        //经办人电话
        if ($("#personphone").val() == "") {
            values = false;
            $("#personphone").next().remove();
            $("#personphone").after("<label>请输入联系电话</label>");
        } else if ($("#personphone").val().split(" ").length != 1) {
            values = false;
            $("#personphone").next().remove();
            $("#personphone").after("<label>输入格式不能含有空格</label>");
        } else if (!regTel1.test($("#personphone").val())) {
            values = false;
            $("#personphone").next().remove();
            $("#personphone").after("<label>输入电话号码有误</label>");
        } else {
            $("#personphone").next().remove();
        }

        if (values) {
            $("#my-btn2").attr("disabled", "disabled");
            $("#disposeForm").submit();
        } else {
            var sc = $(window).scrollTop();
            $('body,html').animate({scrollTop: 0}, 500);
        }
    });
    //my-btn3点击事件
    $("#my-btn3").click(function () {
        var reg = /^([\u4e00-\u9fa5a-zA-Z]*)$/;
        var reg2 = /^([\u4e00-\u9fa5]{1,20}|[a-zA-Z\.\s]{1,20})$/;
        var regTel1 = /^0?(13|14|15|16|17|18|19)[0-9]{9}$/;//手机号码
        var regTel2 = /^(([0\+]\d{2,3}-)?(0\d{2,3})-)(\d{7,8})(-(\d{3,}))?$/;//带区号的固定电话
        var regTel3 = /^(\d{7,8})(-(\d{3,}))?$/;//不带区号的固定电话
        var regular = /^[0-9a-zA-Z]{9,18}$/;
        var values = true;
        //计算文件总大小 ie下不适配
        var isIE = navigator.userAgent.match(/MSIE/) != null,
            isIE6 = navigator.userAgent.match(/MSIE 6.0/) != null;
        // if(!isIE){
        //     var s = 0;
        //     var x = document.getElementsByTagName('input');
        //     //遍历文件
        //     for(var i = 0;i < x.length;i++){
        //         if(x[i].getAttribute('type') === "file"){
        //             if(x[i].files.length > 0 ){
        //                 //获取文件大小
        //                 s = s + x[i].files[0].size;
        //             }
        //         }
        //     }
        //     //计算总共文件的大小M
        //     s = s / 1024 / 1024;
        //     if(s > 20){
        //         alert("上传文件总大小不能大于20M");
        //         return false;
        //     }
        // }
        //验证经办人姓名
        if ($("#personname").val() == "") {
            values = false;
            if ($("#personname").next().length) {
            } else {

                $("#personname").after("<label>请输入经办人姓名</label>");
            }
        } /*else if ($("#personname").val().split(" ").length != 1) {
            values = false;
            $("#personname").next().remove();
            $("#personname").after("<label>输入格式不能含有空格！</label>");
        }*/ else if (!reg2.test($("#personname").val())) {
            values = false;
            $("#personname").next().remove();
            $("#personname").after("<label>输入姓名格式错误！</label>");
        } else {
            $("#personname").next().remove();
        }

        //验证经办人证件
        var a = $('#persionType option:selected').attr('value');

        if (a==1) {//身份证
            var regular = /^[0-9a-zA-Z]{9,18}$/;
            if ($("#personid").val() == "") {values = false;
                $("#personid").next().remove();
                $("#personid").after("<label>请输入证件号码</label>");
            } else if ($("#personid").val().split(" ").length != 1) {
                values = false;
                $("#personid").next().remove();
                $("#personid").after("<label>输入格式不能含有空格！</label>");
            } else if (!regular.test($("#personid").val())) {
                values = false;
                $("#personid").next().remove();
                $("#personid").after("<label>输入身份证件格式错误</label>");
            } else {
                $("#personid").next().remove();
            }
        }else if(a==2) {//港澳通行证
            var regular = /^[HMChm]{1}([0-9]{10}|[0-9]{8})$/;
            var regular2 = /^((\s?[A-Za-z])|([A-Za-z]{2}))\d{6}(\([0-9aA]\)|[0-9aA])$/;//香港--身份证（本国人）
            var regular3 = /^[1|5|7][0-9]{6}\([0-9Aa]\)$/;//澳门--身份证（本国人）
            if ($("#personid").val() == "") {
                values = false;
                $("#personid").next().remove();
                $("#personid").after("<label>请输入证件号码</label>");
            } else if ($("#personid").val().split(" ").length != 1) {
                values = false;
                $("#personid").next().remove();
                $("#personid").after("<label>输入格式不能含有空格！</label>");
            } else if (!regular.test($("#personid").val()) && !regular2.test($("#personid").val()) && !regular3.test($("#personid").val())) {
                values = false;
                $("#personid").next().remove();
                $("#personid").after("<label>输入港澳通行证/港澳身份证(本国人)格式错误</label>");
            } else {
                $("#personid").next().remove();
            }
        }else if(a==3) {//台湾通行证
            var regular = /^[0-9]{8}$/;
            var regular2 = /^[0-9]{10}$/;
            var regular3 = /^[a-zA-Z][0-9]{9}$/;//台湾身份证
            if ($("#personid").val() == "") {
                values = false;
                $("#personid").next().remove();
                $("#personid").after("<label>请输入证件号码</label>");
            } else if ($("#personid").val().split(" ").length != 1) {
                values = false;
                $("#personid").next().remove();
                $("#personid").after("<label>输入格式不能含有空格！</label>");
            } else if (!regular.test($("#personid").val()) && !regular2.test($("#personid").val()) && !regular3.test($("#personid").val())) {
                values = false;
                $("#personid").next().remove();
                $("#personid").after("<label>输入台湾通行证/台湾身份证格式错误</label>");
            } else {
                $("#personid").next().remove();
            }
        }else if(a==4){// 护照验证
            var regular = /^[a-zA-Z]{5,17}$/;
            var regular2 = /^[a-zA-Z0-9]{5,17}$/;
            if ($("#personid").val() == "") {
                values = false;
                $("#personid").next().remove();
                $("#personid").after("<label>请输入证件号码</label>");
            } else if ($("#personid").val().split(" ").length != 1) {
                values = false;
                $("#personid").next().remove();
                $("#personid").after("<label>输入格式不能含有空格！</label>");
            } else if (!regular.test($("#personid").val()) && !regular2.test($("#personid").val())) {
                values = false;
                $("#personid").next().remove();
                $("#personid").after("<label>输入护照证件格式错误</label>");
            } else {
                $("#personid").next().remove();
            }
        }

        //经办人电话
        if ($("#personphone").val() == "") {
            values = false;
            $("#personphone").next().remove();
            $("#personphone").after("<label>请输入联系电话</label>");
        } else if ($("#personphone").val().split(" ").length != 1) {
            values = false;
            $("#personphone").next().remove();
            $("#personphone").after("<label>输入格式不能含有空格</label>");
        } else if (!regTel1.test($("#personphone").val())) {
            values = false;
            $("#personphone").next().remove();
            $("#personphone").after("<label>输入电话号码有误</label>");
        } else {
            $("#personphone").next().remove();
        }

        if (values) {
            $("#my-btn3").attr("disabled", "disabled");
            $("#stampReportForm").submit();
        } else {
            var sc = $(window).scrollTop();
            $('body,html').animate({scrollTop: 0}, 500);
        }
    });
    //my-btn4点击事件
    $("#my-btn4").click(function () {
        var reg2 = /^([\u4e00-\u9fa5]{1,20}|[a-zA-Z\.\s]{1,20})$/;
        var regTel1 = /^0?(13|14|15|16|17|18|19)[0-9]{9}$/;//手机号码
        var regTel2 = /^(([0\+]\d{2,3}-)?(0\d{2,3})-)(\d{7,8})(-(\d{3,}))?$/;//带区号的固定电话
        var regTel3 = /^(\d{7,8})(-(\d{3,}))?$/;//不带区号的固定电话
        var regular = /^[0-9a-zA-Z]{9,18}$/;
        var values = true;

        //计算文件总大小 ie下无法操作
        var isIE = navigator.userAgent.match(/MSIE/) != null,
            isIE6 = navigator.userAgent.match(/MSIE 6.0/) != null;
        // if(!isIE){
        //     var s = 0;
        //     var x = document.getElementsByTagName('input');
        //     //遍历文件
        //     for(var i = 0;i < x.length;i++){
        //         if(x[i].getAttribute('type') === "file"){
        //             if(x[i].files.length > 0 ){
        //     //获取文件大小
        //                 s = s + x[i].files[0].size;
        //             }
        //         }
        //     }
        //     //计算总共文件的大小M
        //     s = s / 1024 / 1024;
        //     if(s > 20){
        //         alert("上传文件总大小不能大于20M");
        //         return false;
        //     }
        // }
        //验证经办人姓名
        if ($("#personname").val() == "") {
            values = false;
            if ($("#personname").next().length) {
            } else {

                $("#personname").after("<label>请输入经办人姓名</label>");
            }
        } /*else if ($("#personname").val().split(" ").length != 1) {
            values = false;
            $("#personname").next().remove();
            $("#personname").after("<label>输入格式不能含有空格！</label>");
        }*/ else if (!reg2.test($("#personname").val())) {
            values = false;
            $("#personname").next().remove();
            $("#personname").after("<label>输入姓名格式错误！</label>");
        } else {
            $("#personname").next().remove();
        }

        //验证经办人证件
        var a = $('#persionType option:selected').attr('value');

        if (a==1) {//身份证
            var regular = /^[0-9a-zA-Z]{9,18}$/;
            if ($("#personid").val() == "") {values = false;
                $("#personid").next().remove();
                $("#personid").after("<label>请输入证件号码</label>");
            } else if ($("#personid").val().split(" ").length != 1) {
                values = false;
                $("#personid").next().remove();
                $("#personid").after("<label>输入格式不能含有空格！</label>");
            } else if (!regular.test($("#personid").val())) {
                values = false;
                $("#personid").next().remove();
                $("#personid").after("<label>输入身份证件格式错误</label>");
            } else {
                $("#personid").next().remove();
            }
        }else if(a==2) {//港澳通行证
            var regular = /^[HMChm]{1}([0-9]{10}|[0-9]{8})$/;
            var regular2 = /^((\s?[A-Za-z])|([A-Za-z]{2}))\d{6}(\([0-9aA]\)|[0-9aA])$/;//香港--身份证（本国人）
            var regular3 = /^[1|5|7][0-9]{6}\([0-9Aa]\)$/;//澳门--身份证（本国人）
            if ($("#personid").val() == "") {
                values = false;
                $("#personid").next().remove();
                $("#personid").after("<label>请输入证件号码</label>");
            } else if ($("#personid").val().split(" ").length != 1) {
                values = false;
                $("#personid").next().remove();
                $("#personid").after("<label>输入格式不能含有空格！</label>");
            } else if (!regular.test($("#personid").val()) && !regular2.test($("#personid").val()) && !regular3.test($("#personid").val())) {
                values = false;
                $("#personid").next().remove();
                $("#personid").after("<label>输入港澳通行证/港澳身份证(本国人)格式错误</label>");
            } else {
                $("#personid").next().remove();
            }
        }else if(a==3) {//台湾通行证
            var regular = /^[0-9]{8}$/;
            var regular2 = /^[0-9]{10}$/;
            var regular3 = /^[a-zA-Z][0-9]{9}$/;//台湾身份证
            if ($("#personid").val() == "") {
                values = false;
                $("#personid").next().remove();
                $("#personid").after("<label>请输入证件号码</label>");
            } else if ($("#personid").val().split(" ").length != 1) {
                values = false;
                $("#personid").next().remove();
                $("#personid").after("<label>输入格式不能含有空格！</label>");
            } else if (!regular.test($("#personid").val()) && !regular2.test($("#personid").val()) && !regular3.test($("#personid").val())) {
                values = false;
                $("#personid").next().remove();
                $("#personid").after("<label>输入台湾通行证/台湾身份证格式错误</label>");
            } else {
                $("#personid").next().remove();
            }
        }else if(a==4){// 护照验证
            var regular = /^[a-zA-Z]{5,17}$/;
            var regular2 = /^[a-zA-Z0-9]{5,17}$/;
            if ($("#personid").val() == "") {
                values = false;
                $("#personid").next().remove();
                $("#personid").after("<label>请输入证件号码</label>");
            } else if ($("#personid").val().split(" ").length != 1) {
                values = false;
                $("#personid").next().remove();
                $("#personid").after("<label>输入格式不能含有空格！</label>");
            } else if (!regular.test($("#personid").val()) && !regular2.test($("#personid").val())) {
                values = false;
                $("#personid").next().remove();
                $("#personid").after("<label>输入护照证件格式错误</label>");
            } else {
                $("#personid").next().remove();
            }
        }
        //经办人电话
        if ($("#personphone").val() == "") {
            values = false;
            $("#personphone").next().remove();
            $("#personphone").after("<label>请输入联系电话</label>");
        } else if ($("#personphone").val().split(" ").length != 1) {
            values = false;
            $("#personphone").next().remove();
            $("#personphone").after("<label>输入格式不能含有空格</label>");
        } else if (!regTel1.test($("#personphone").val())) {
            values = false;
            $("#personphone").next().remove();
            $("#personphone").after("<label>输入电话号码有误</label>");
        } else {
            $("#personphone").next().remove();
        }

        if (values) {
            $("#my-btn4").attr("disabled", "disabled");
            $("#repairForm").submit();
        } else {
            var sc = $(window).scrollTop();
            $('body,html').animate({scrollTop: 0}, 500);
        }
    });
});



