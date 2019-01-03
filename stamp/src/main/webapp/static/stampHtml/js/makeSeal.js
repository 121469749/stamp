/**
 * Created by NieLei on 2017/1/13.
 */
$(document).ready(function(){
   $("#tag li").click(function(){
       $(this).addClass('active').siblings().removeClass('active');

   });
});

function showSealFace(){
    $(".sealFace").css("display","block");
    $(".sealFace").siblings("div").css("display","none");
}

function showAnnularFont(){
    $(".setAnnularFont").css("display","block");
    $(".setAnnularFont").siblings("div").css("display","none");
}

function showHorizontalFont(){
    $(".setHorizontalFont").css("display","block");
    $(".setHorizontalFont").siblings("div").css("display","none");
}