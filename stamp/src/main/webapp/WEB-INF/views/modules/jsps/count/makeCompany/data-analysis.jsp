<%--
  Created by IntelliJ IDEA.
  User: bb
  Date: 2018-08-02
  Time: 18:17
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
    <title>刻章点-数据分析图形</title>
    <meta name="decorator" content="default"/>
    <link href="${pageContext.request.contextPath}/static/stampHtml/dataAnalysis/css/data-analysis.css?id=11"
          rel="stylesheet">
    <link href="${pageContext.request.contextPath}/static/stampHtml/dataAnalysis/css/font-awesome.min.css"
          rel="stylesheet">
    <link href="${pageContext.request.contextPath}/static/stampHtml/css/bootstrap-multiselect.css" rel="stylesheet">
    <link href="${pageContext.request.contextPath}/static/stampHtml/css/bootstrap.min.css" rel="stylesheet">
    <link href="${pageContext.request.contextPath}/static/stampHtml/dataAnalysis/css/main.css" rel="stylesheet">

    <script type="text/javascript" src="${pageContext.request.contextPath}/static/stampHtml/js/jquery.min.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/static/stampHtml/js/countUp.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/static/stampHtml/js/bootstrap-multiselect.js"></script>

    <script type="text/javascript"
            src="${pageContext.request.contextPath}/static/stampHtml/dataAnalysis/js/echarts.min.js"></script>
    <%--<script src="https://cdnjs.cloudflare.com/ajax/libs/echarts/4.1.0.rc2/echarts.min.js"></script>--%>

    <script type="text/javascript">
        var ctx = "${ctx}";
    </script>

</head>
<body>
<div class="alert alert-danger alert-dismissable" style="font-size: 15px;">
    <%--<marquee direction="right" behavior="alternate" scrollamount="5" scrolldelay="0" loop="-1" width="100%" height="" bgcolor="" hspace="" vspace="">
    </marquee>--%>
    <button type="button" class="close" data-dismiss="alert">&times;</button>
    <strong>警告!</strong> 有多个章未正常交付，请注意！
</div>
<div class="counter-title">
    <div class="row margin-top margin-right">
        <table style="width: 100%;">
            <tr>
                <td style="width: 7%; text-align: right;">
                    <div class="btn-group btn-group-vertical" role="group">
                        <input type="button" class="btn btn-default layui-bg-green" style="color: #fff;" id="today"
                               value="今日">
                        <input type="button" class="btn btn-default layui-bg-blue" style="color: #fff;" id="thisWeek"
                               value="本周">
                        <input type="button" class="btn btn-default layui-bg-orange" style="color: #fff;" id="thisMonth"
                               value="本月">
                        <input type="button" class="btn btn-default layui-bg-cyan" style="color: #fff;" id="thisYear"
                               value="今年">
                    </div>
                </td>
                <td>
                    <div class="row" style="margin-right: 10%;margin-left: 1%">
                        <div class="col-md-3">
                            <div class="ibox float-e-margins card-panel ">
                                <div class="card-panel-wrapper icon-apply">
                                    <img src="${pageContext.request.contextPath}/static/stampHtml/images/seal.png">
                                </div>
                                <div class="card-panel-description">
                                    <div class="layui-card-header">
                                        申请备案数量
                                        <div style="float: right;">
                                            <span class="layui-badge layui-bg-green layuiadmin-badge"
                                                  id="applyText">今日</span>
                                        </div>
                                    </div>
                                    <div>
                                        <h1 id="tdApplyCount">0</h1>
                                    </div>
                                </div>
                            </div>
                        </div>

                        <div class="col-md-3">
                            <div class="ibox float-e-margins card-panel ">
                                <div class="card-panel-wrapper icon-make">
                                    <img src="${pageContext.request.contextPath}/static/stampHtml/images/stamp-engrave.png">
                                </div>
                                <div class="card-panel-description">
                                    <div class="layui-card-header">
                                        制作印章数量
                                        <div style="float: right;">
                                            <span class="layui-badge layui-bg-green layuiadmin-badge" id="engraveText">今日</span>
                                        </div>
                                    </div>
                                    <div>
                                        <h1 id="tdEngraveCount">0</h1>
                                    </div>
                                </div>
                            </div>
                        </div>

                        <div class="col-md-3">
                            <div class="ibox float-e-margins card-panel ">
                                <div class="card-panel-wrapper icon-delivery">
                                    <img src="${pageContext.request.contextPath}/static/stampHtml/images/stamp-delivery.png">
                                </div>
                                <div class="card-panel-description">
                                    <div class="layui-card-header">
                                        交付印章数量
                                        <div style="float: right;">
                                            <span class="layui-badge layui-bg-green layuiadmin-badge" id="deliveryText">今日</span>
                                        </div>
                                    </div>
                                    <div>
                                        <h1 id="tdDeliveryCount">0</h1>
                                    </div>
                                </div>
                            </div>
                        </div>

                        <div class="col-md-3">
                            <div class="ibox float-e-margins card-panel ">
                                <div class="card-panel-wrapper icon-use">
                                    <img src="${pageContext.request.contextPath}/static/stampHtml/images/company.png">
                                </div>
                                <div class="card-panel-description">
                                    <div class="layui-card-header">
                                        备案企业数量
                                        <div style="float: right;">
                                            <span class="layui-badge layui-bg-green layuiadmin-badge"
                                                  id="useComText">今日</span>
                                        </div>
                                    </div>
                                    <div>
                                        <h1 id="tdUseComCount">0</h1>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>

                    <div class="row" style="margin-right: 10%;margin-left: 1%">
                        <div class="col-md-3">
                            <div class="ibox float-e-margins card-panel ">
                                <div class="card-panel-wrapper icon-apply">
                                    <img src="${pageContext.request.contextPath}/static/stampHtml/images/seal.png">
                                </div>
                                <div class="card-panel-description">
                                    <div class="layui-card-header">
                                        已申请印章
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

                        <div class="col-md-3">
                            <div class="ibox float-e-margins card-panel ">
                                <div class="card-panel-wrapper icon-make">
                                    <img src="${pageContext.request.contextPath}/static/stampHtml/images/stamp-engrave.png">
                                </div>
                                <div class="card-panel-description">
                                    <div class="layui-card-header">
                                        已制作印章
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

                        <div class="col-md-3">
                            <div class="ibox float-e-margins card-panel ">
                                <div class="card-panel-wrapper icon-delivery">
                                    <img src="${pageContext.request.contextPath}/static/stampHtml/images/stamp-delivery.png">
                                </div>
                                <div class="card-panel-description">
                                    <div class="layui-card-header">
                                        已交付印章
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

                        <div class="col-md-3">
                            <div class="ibox float-e-margins card-panel ">
                                <div class="card-panel-wrapper icon-use">
                                    <img src="${pageContext.request.contextPath}/static/stampHtml/images/company.png">
                                </div>
                                <div class="card-panel-description">
                                    <div class="layui-card-header">
                                        已备案企业
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
        </table>
    </div>

</div>

<!-- 为 ECharts 准备一个具备大小（宽高）的 DOM -->
<div class="row margin-top margin-left">
    <div class="col-lg-6">
        <div id="selectDiv" class="btn-group open" style="width: 250px;float: left;">
            <select class="multiselect" style="color: #00aa00;" id="mySelect" multiple="multiple" >
            </select>
        </div>
        <div id="show-line" class="line-style"></div>
    </div>
    <div class="col-lg-6">
        <div id="show-pie-dataSources" class="stampType-style"></div>
    </div>

</div>

<script type="text/javascript"
        src="${pageContext.request.contextPath}/static/stampHtml/dataAnalysis/js/makeCom-show-line.js?id=2"></script>
<script type="text/javascript"
        src="${pageContext.request.contextPath}/static/stampHtml/dataAnalysis/js/show-pie-stampType.js?id=2"></script>

<script type="text/javascript">

    var selectValueStr =new Array();
    $("#mySelect").change(function () {
        selectValueStr = [];
        $("#mySelect").each(function () {
            selectValueStr.push($(this).val());

        });

        //开始全部隐藏
        for(var j = 0;j<tempArray.length;j++){

            option.legend.selected[tempArray[j]] = false;
        }
        myChart.setOption(option);
        //根据所勾选的显示
        if(selectValueStr[0] != null){
            for(var i = 0 ;i< selectValueStr[0].length;i++){
                option.legend.selected[selectValueStr[0][i]] = true;
            }
        }
        myChart.setOption(option);
    })


    $(document).ready(function () {
        //隐藏下拉框
        $("#selectDiv").hide();

        var  idArrayForTotal = new  Array('totalApplyCount','totalEngraveCount','totalDeliveryCount','totalUseComCount');
        var  idArrayForTd = new  Array('tdApplyCount','tdEngraveCount','tdDeliveryCount','tdUseComCount');
        $.ajax({
            type: 'POST',
            contentType: 'application/json',
            url: '${ctx}/MakeComCount/stampAndUseComCountForDate?dateFlag=today',
            dataType: 'json',
            success: function (data) {

                dynamicCount(idArrayForTd,data);

            },
            error: function (e) {
                console.log("错误：" + e);
                alert("请联系管理员！");
            }
        });

        $.ajax({
            type: 'POST',
            contentType: 'application/json',
            url: '${ctx}/MakeComCount/getTotalApplyCount',
            dataType: 'json',
            success: function (data) {

                dynamicCount(idArrayForTotal,data);
            },
            error: function () {
                console.log("错误：" + e);
                alert("请联系管理员！");
            }
        });


        //今日
        $("#today").click(
            function () {
                console.log("today-mouseover");
                $.ajax({
                    type: 'POST',
                    contentType: 'application/json',
                    url: '${ctx}/MakeComCount/stampAndUseComCountForDate?dateFlag=today',
                    dataType: 'json',
                    success: function (data) {

                        dynamicCount(idArrayForTd,data);

                        $("span.layui-badge").text("今日");

                        $("span.layui-badge").removeClass().addClass("layui-badge layui-bg-green layuiadmin-badge");
//                        $("span").attr("class", "layui-badge layui-bg-green layuiadmin-badge");

                    },
                    error: function () {
                        console.log("错误：" + e);
                        alert("请联系管理员！");
                    }
                });
            }
        );
        //本周
        $("#thisWeek").click(
            function () {
                console.log("thisWeek-mouseover");
                $.ajax({
                    type: 'POST',
                    contentType: 'application/json',
                    url: '${ctx}/MakeComCount/stampAndUseComCountForDate?dateFlag=thisWeek',
                    dataType: 'json',
                    success: function (data) {
                        dynamicCount(idArrayForTd,data);

                        $("span.layui-badge").text("本周");

                        $("span.layui-badge").removeClass().addClass("layui-badge layui-bg-blue layuiadmin-badge");
//                        $("span").attr("class", "layui-badge layui-bg-blue layuiadmin-badge");

                    },
                    error: function () {
                        console.log("错误：" + e);
                        alert("请联系管理员！");
                    }
                });
            }
        );
        //本月
        $("#thisMonth").click(
            function () {
                console.log("thisMonth-mouseover");
                $.ajax({
                    type: 'POST',
                    contentType: 'application/json',
                    url: '${ctx}/MakeComCount/stampAndUseComCountForDate?dateFlag=thisMonth',
                    dataType: 'json',
                    success: function (data) {
                        dynamicCount(idArrayForTd,data);

                        $("span.layui-badge").text("本月");

                        $("span.layui-badge").removeClass().addClass("layui-badge layui-bg-orange layuiadmin-badge");
//                        $("span").attr("class", "layui-badge layui-bg-orange layuiadmin-badge");

                    },
                    error: function () {
                        console.log("错误：" + e);
                        alert("请联系管理员！");
                    }
                });
            }
        );
        //今年
        $("#thisYear").click(
            function () {
                console.log("thisYear-mouseover");
                $.ajax({
                    type: 'POST',
                    contentType: 'application/json',
                    url: '${ctx}/MakeComCount/stampAndUseComCountForDate?dateFlag=thisYear',
                    dataType: 'json',
                    success: function (data) {
                        dynamicCount(idArrayForTd,data);

                        $("span.layui-badge").text("今年");

                        $("span.layui-badge").removeAttr("class").removeClass().addClass("layui-badge layui-bg-cyan layuiadmin-badge");
//                        $("span").attr("class", "layui-badge layui-bg-cyan layuiadmin-badge");

                    },
                    error: function () {
                        console.log("错误：" + e);
                        alert("请联系管理员！");
                    }
                });
            }
        );

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

    });
</script>
</body>
</html>
