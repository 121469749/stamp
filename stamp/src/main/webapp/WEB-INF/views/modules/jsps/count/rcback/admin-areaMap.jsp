<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2018\11\19 0019
  Time: 15:51
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp" %>
<!DOCTYPE html>
<html lang="zh">
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no"/>
    <meta name="renderer" content="webkit">
    <title>管理员-数据分析图形</title>
    <meta name="decorator" content="default"/>
    <link href="${ctxHtml}/dataAnalysis/css/data-analysis.css?id=11" rel="stylesheet">
    <link href="${ctxHtml}/dataAnalysis/css/font-awesome.min.css" rel="stylesheet">
    <link href="${ctxHtml}/dataAnalysis/css/main.css" rel="stylesheet">
    <link href="${ctxHtml}/css/bootstrap.min.css" rel="stylesheet">
    <link href="${pageContext.request.contextPath}/static/stampHtml/css/bootstrap-multiselect.css" rel="stylesheet">
    <script type="text/javascript" src="${ctxHtml}/js/countUp.js"></script>

    <script type="text/javascript" src="${ctxHtml}/js/jquery.min.js"></script>
    <script type="text/javascript" src="${ctxHtml}/dataAnalysis/js/echarts.min.js"></script>
    <script type="text/javascript" src="${ctxHtml}/dataAnalysis/js/rcback/china.js"></script>

    <link rel="stylesheet" type="text/css" href="${ctxHtml}/dataAnalysis/js/rcback/areaMap/static/css/main.css">
    <script type="text/javascript" src="${ctxHtml}/dataAnalysis/js/rcback/areaMap/static/js/echarts.min.js"></script>
    <!-- 全国344个市、区、州对应的数字编号 -->
    <script type="text/javascript" src="${ctxHtml}/dataAnalysis/js/rcback/areaMap/static/js/citymap.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/static/stampHtml/js/bootstrap-multiselect.js"></script>

    <script type="text/javascript">
        var ctx = "${ctx}";
        var ctxJson = "${ctxHtml}/dataAnalysis/js/rcback/areaMap/";
    </script>


</head>
<body>
<table style="width: 100%;">
    <tr>
        <td style="width:1000px;">
            <div id="main" style="height: 800px;width:1000px;background:white;margin:5px 0 0;"></div>
        </td>
        <td>
            <div >
                <div class="row margin-top margin-right">
                    <table style="width: 100%;">
                        <tr>
                            <td>
                                <div class="row" style="margin-right: 20px;margin-left: 1%">
                                    <div class="col-md-6">
                                        <div class="ibox float-e-margins card-panel ">
                                            <div class="card-panel-wrapper icon-apply">
                                                <img src="${pageContext.request.contextPath}/static/stampHtml/images/seal.png">
                                            </div>
                                            <div class="card-panel-description">
                                                <div class="layui-card-header">
                                                    该辖区已申请印章
                                                    <div style="float: right;">
                                                        <li class="layui-badge layui-bg-blue layuiadmin-badge">总数</li>
                                                    </div>
                                                </div>
                                                <div>
                                                    <h1 id="totalApplyCount">0</h1>
                                                </div>
                                            </div>
                                        </div>
                                    </div>

                                    <div class="col-md-6">
                                        <div class="ibox float-e-margins card-panel ">
                                            <div class="card-panel-wrapper icon-make">
                                                <img src="${pageContext.request.contextPath}/static/stampHtml/images/stamp-engrave.png">
                                            </div>
                                            <div class="card-panel-description">
                                                <div class="layui-card-header">
                                                    该辖区已制作印章
                                                    <div style="float: right;">
                                                        <li class="layui-badge layui-bg-blue layuiadmin-badge">总数</li>
                                                    </div>
                                                </div>
                                                <div>
                                                    <h1 id="totalEngraveCount">0</h1>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </td>
                        </tr>

                        <tr>
                            <td>
                                <div class="row" style="margin-right: 20px;margin-left: 1%">
                                    <div class="col-md-6">
                                        <div class="ibox float-e-margins card-panel ">
                                            <div class="card-panel-wrapper icon-delivery">
                                                <img src="${pageContext.request.contextPath}/static/stampHtml/images/stamp-delivery.png">
                                            </div>
                                            <div class="card-panel-description">
                                                <div class="layui-card-header">
                                                    该辖区已交付印章
                                                    <div style="float: right;">
                                                        <li class="layui-badge layui-bg-blue layuiadmin-badge">总数</li>
                                                    </div>
                                                </div>
                                                <div>
                                                    <h1 id="totalDeliveryCount">0</h1>
                                                </div>
                                            </div>
                                        </div>
                                    </div>

                                    <div class="col-md-6">
                                        <div class="ibox float-e-margins card-panel ">
                                            <div class="card-panel-wrapper icon-use">
                                                <img src="${pageContext.request.contextPath}/static/stampHtml/images/company.png">
                                            </div>
                                            <div class="card-panel-description">
                                                <div class="layui-card-header">
                                                    该辖区已备案企业
                                                    <div style="float: right;">
                                                        <li class="layui-badge layui-bg-blue layuiadmin-badge">总数</li>
                                                    </div>
                                                </div>
                                                <div>
                                                    <h1 id="totalUseComCount">0</h1>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </td>
                        </tr>
                        <tr style="width: 100%;">
                            <td>
                                <div id="selectDiv" class="btn-group open" style="width: 250px;float: left;margin-top: 10px;">
                                    <select class="multiselect" style="color: #00aa00;" id="mySelect" multiple="multiple" >
                                    </select>
                                </div>
                                <div>
                                    <div id="show-line" style="width: 100%;height:450px;margin-top: 50px;"></div>
                                </div>
                             </td>
                        </tr>

                    </table>
                </div>
            </div>

        </td>
    </tr>
</table>

<script type="text/javascript" src="${ctxHtml}/dataAnalysis/js/rcback/admin-show-line.js?id=2"></script>
<script type="text/javascript" src="${ctxHtml}/dataAnalysis/js/rcback/areaMap/static/js/app.js"></script>
<script type="text/javascript">
    var selectValueStr =new Array();
    $("#mySelect").change(function () {
        selectValueStr = [];
        $("#mySelect").each(function () {
            selectValueStr.push($(this).val());

        });
//        console.log("selectValueStr[].length:"+selectValueStr[0]);
        //开始全部隐藏
        for(var j = 0;j<tempArray.length;j++){

            option2.legend.selected[tempArray[j]] = false;
        }
        myChart.setOption(option2);
        //根据所勾选的显示
        if(selectValueStr[0] != null){
            for(var i = 0 ;i< selectValueStr[0].length;i++){
                option2.legend.selected[selectValueStr[0][i]] = true;
            }
        }
        myChart.setOption(option2);
    })

    function getToatalData(areaName) {
        var  idArrayForTotal = new  Array('totalApplyCount','totalEngraveCount','totalDeliveryCount','totalUseComCount');
            $.ajax({
                type:'POST',
                contentType :'application/json',
                url :'${ctx}/adminCount/getTotalApplyCount?areaName='+areaName,
                dataType: 'json',
                success:function (data) {

                    dynamicCount(idArrayForTotal,data);

                },
                error:function (e) {
                    console.log("错误："+e);
                    alert("请联系管理员！");
                }
            });
    }

        //数据动态显示
        function dynamicCount(idArray,data) {
            /*
             target = 目标元素的 ID；
             startVal = 开始值；
             endVal = 结束值；
             decimals = 小数位数，默认值是0；
             duration = 动画延迟秒数，默认值是2；
             */
            var options = {
                useEasing: true,
                useGrouping: true,
                separator: ',',
                decimal: '.',
            };
            for(var i=0;i<idArray.length;i++){
                var demo = new CountUp(idArray[i], 0, data[i], 0, 1.0, options);
                if (!demo.error) {
                    demo.start();
                } else {
                    console.error(demo.error);
                }
            }

        }

</script>
</body>
</html>

