/**
 * Created by 77426 on 2017/5/11.
 */
//表单验证
$(function(){
    var values = [0,0,0,0,0,0,0,0,0,0,0,0,0,0];
    //验证公司名称
    $("#enterprise_name").blur(function(){
        if($(this).val() == ""){
            if($(this).next().length){
            }else{
                $(this).after("<label>请输入企业名称</label>");
            }
        }else{
            $(this).next().remove();
            values[0] = "1";
            console.log(values[0]);
        }
    });
    //验证企业地址
    $("#compAddress").blur(function(){
        if($(this).val() == ""){
            if($(this).next().length){
            }else{
                $(this).after("<label>请输入企业地址</label>");
            }
        }else{
            $(this).next().remove();
            values[1] = 1;
        }
    });
    //验证经营方式
    $("#busModel").blur(function(){
        if($(this).val() == ""){
            if($(this).next().length){
            }else{
                $(this).after("<label>请输入经营方式</label>");
            }
        }else{
            $(this).next().remove();
            values[2] = 1;
        }
    });
    //验证主要负责人，治安负责人
    $("#securityperson").blur(function(){
        if($(this).val() == ""){
            if($(this).next().length){
            }else{
                $(this).after("<label>请输入负责人姓名</label>");
            }
        }else{
            $(this).next().remove();
            values[3] = 1;
        }
    });
    $("#legalName").blur(function(){
        if($(this).val() == ""){
            if($(this).next().length){
            }else{
                $(this).after("<label>请输入负责人姓名</label>");
            }
        }else{
            $(this).next().remove();
            values[4] = 1;
        }
    });
    //验证负责人的身份证
    $("#securityperson-id").blur(function(){
        if($(this).val().length != 18){
            if($(this).next().length){
            }else{
                $(this).after("<label>请输入18位二代身份证号码</label>");
            }
        }else{
            $(this).next().remove();
            values[5] = 1;
        }
    });
    $("#mainperson-id").blur(function(){
        if($(this).val().length != 18){
            if($(this).next().length){
            }else{
                $(this).after("<label>请输入18位二代身份证号码</label>");
            }
        }else{
            $(this).next().remove();
            values[6] = 1;
            //验证负责人的联系电话
        }
    });
    $("#mainperson-phone").blur(function(){
        if($(this).val().length != 11){
            if($(this).next().length){
            }else{
                $(this).after("<label>请输入11位联系电话</label>");
            }
        }else{
            $(this).next().remove();
            values[7] = 1;
        }
    });
    $("#securityperson-phone").blur(function(){
        if($(this).val().length != 11){
            if($(this).next().length){
            }else{
                $(this).after("<label>请输入11位联系电话</label>");
            }
        }else{
            $(this).next().remove();
            values[8] = 1;
        }
    });
    //验证刻章点法人手机号
    $("#phonenumber").blur(function(){
        if($(this).val().length != 11){
            if($(this).next().length){
            }else{
                $(this).after("<label>请输入11位联系电话</label>");
            }
        }else{
            $(this).next().remove();
            values[12] = 1;
        }
    });
    //验证刻章点法人手机验证码
    $("#checknumber").blur(function(){
        if($(this).val() == ""){
            if($(this).next("label").length){
            }else{
                $(this).after("<label>输入验证码</label>");
            }
        }else{
            $(this).next().remove();
            values[13] = 1;
        }
    });
    //验证企业总人数
    $("#allpeople").blur(function(){
        if($(this).val() == ""){
            if($(this).next().length){
            }else{
                $(this).after("<label>请输入企业总人数</label>");
            }
        }else{
            $(this).next().remove();
            values[9] = 1;
        }
    });
    //验证特业从业人数
    $("#specialpeople").blur(function(){
        if($(this).val() == ""){
            if($(this).next().length){
            }else{
                $(this).after("<label>请输入特业从业人数</label>");
            }
        }else{
            $(this).next().remove();
            values[10] = 1;
        }
    });
    //验证企业统一码
    $("#unifiedcode").blur(function(){
        if($(this).val() == ""){
            if($(this).next().length){
            }else{
                $(this).after("<label>企业统一码</label>");
            }
        }else{
            $(this).next().remove();
            values[11] = 1;
        }
    });
/*    //重新提交申请提交
    $("#resubmit").click(function () {
        var count = 0;
        var i = 0;
        var imgs = true;
        $("#filetable tr").each(function () {
            if ($(this).find('input').val() != "") {

            } else {
                imgs = false;//循环有一个input不为空则将status改为false
            }
        });
        for( i = 0 ; i < 15 ; i ++){
            if(values[i] == 1){
                count++;
            }
        }
        if(count >= 12 && imgs) {
            $(this).attr("disabled","disabled");
        }else{

            alert("请填写完整信息！！")
        }
    });
    //理由提交提交
    $("#reasonsubmit").click(function () {
        var count = 0;
        var i = 0;
        var imgs = true;
        $("#filetable tr").each(function () {
            if ($(this).find('input').val() != "") {

            } else {
                imgs = false;//循环有一个input不为空则将status改为false
            }
        });
        for( i = 0 ; i < 15 ; i ++){
            if(values[i] == 1){
                count++;
            }
        }
        if(count >= 13 && imgs) {
            $(this).attr("disabled","disabled");
        }else{
            alert("请填写完整信息！！")
        }
    });*/
    $("#submitbtn").click(function () {
        var count = 0;
        var i = 0;
        var imgs = true;
        $("#filetable tr").each(function () {
            if ($(this).find('input[type="text"]').val() != "") {
            } else {
                imgs = false;//循环有一个input不为空则将status改为false
            }
        });
        for( i = 0 ; i < 14 ; i ++){
            if(values[i] == 1){
                count++;
            }
        }
        if(count >= 9 && imgs) {
            $(this).attr("disabled","disabled");
            submitForm();

        }else{
            alert("请填写完整信息！！");
        }


    });
})
function submitForm() {


    var formdata = new FormData($("#fileForm")[0]);
    var poData = $("#mainform").serializeArray();
    for (var i = 0; i < poData.length; i++)
        formdata.append(poData[i].name, poData[i].value);

    $.ajax({
        type: "post",
        data: formdata,
        dataType: "json",
        cache: false,
        contentType: false,
        processData: false,
        url: $("#mainform").attr("action"),
        success: function (condition) {
            //跳转
            if (condition.code == "200") {
                location.href = condition.message.toString();
            }

            else if (condition.code == "500") {
                //开放按钮

                document.getElementById("submitbtn").removeAttribute('disabled');
             //    $(".error").text(condition.message.toString());
                alert(condition.message.toString());

            }

        },
        error: function (condition) {
            //显示错误信息


        }
    });
}