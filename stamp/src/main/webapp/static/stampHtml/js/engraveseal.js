
// var province = ["公章", "财务专用章", "发票专用章", "合同专用章", "业务专用章", "法定代表人名章"];
//
// var gongz = ["38公章","40公章","42公章"];
// var caiwu = ["35公章","38公章","40公章","42公章"];
// var fapiao = ["42*30发票章"];
// var hetong = ["38公章","40公章","42公章","45公章"];
// var yewu = ["38公章","40公章","42公章"];
// var fading = ["二字法人名章","三字法人名章","四字法人名章"];
//
// $(function () {
// //设置省份数据
//     setProvince();
//
// //设置背景颜色
//     setBgColor();
// });
//
//
// //设置省份数据
// function setProvince() {
// //给省份下拉列表赋值
//     var option, modelVal;
//     var $sel = $("#selProvince");
//
// //获取对应省份城市
//     for (var i = 0, len = province.length; i < len; i++) {
//         modelVal = province[i];
//         option = "<option value='" + modelVal + "'>" + modelVal + "</option>";
//
// //添加到 select 元素中
//         $sel.append(option);
//     }
//
// //调用change事件，初始城市信息
//     provinceChange();
// }
//
// //根据选中的省份获取对应的城市
//     function setCity(provinec) {
        var city = $("#stampType").value;
        var obj = document.getElementById("SealDraw");
        switch (city) {
            case "01":
                // proCity = gongz;
                obj.SealType = 1;
                $("ul li:gt(0)").show();
                $("#nofading").show();
                $("#fading").hide();
                $("#angle").show();
                break;
            case "02":
                // proCity = caiwu;
                obj.SealType = 2;
                $("ul li:gt(0)").show();
                $("#nofading").show();
                $("#fading").hide();
                $("#angle").show();
                break;
            case "03":
                // proCity = fapiao;
                obj.SealType = 3;
                $("ul li:gt(0)").show();
                $("#nofading").show();
                $("#fading").hide();
                $("#angle").hide();
                break;
            case "04":
                // proCity = hetong;
                obj.SealType = 4;
                $("ul li:gt(0)").show();
                $("#nofading").show();
                $("#fading").hide();
                $("#angle").show();
                break;
            case "99":
                // proCity = yewu;
                obj.SealType = 6;
                $("ul li:gt(0)").show();
                $("#nofading").show();
                $("#fading").hide();
                $("#angle").show();
                break;
            case "05":
                // proCity = fading;
                obj.SealType = 5;
                $("ul li:gt(0)").hide();
                $("#nofading").hide();
                $("#fading").show();
                break;
        }

// //先清空之前绑定的值
//         $city.empty();
//
// //设置对应省份的城市
//         for (var i = 0, len = proCity.length; i < len; i++) {
//             modelVal = proCity[i];
//             option = "<option value='" + modelVal + "'>" + modelVal + "</option>";
//
// //添加
//             $city.append(option);
//         }
// //设置背景
//         setBgColor();
//     }
// //省份选中事件
//         function provinceChange() {
//             var $pro = $("#selProvince");
//             setCity($pro.val());
//         }
// //设置下拉列表间隔背景样色
// function setBgColor() {
//     var $option = $("select option:odd");
//     $option.css({ "background-color": "#DEDEDE" });
// }
