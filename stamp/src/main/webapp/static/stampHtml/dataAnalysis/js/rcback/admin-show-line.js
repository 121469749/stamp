/**
 * Created by bb on 2018-08-06.
 */
var tempArray = new Array();
// 基于准备好的dom，初始化echarts实例
var myChart = echarts.init(document.getElementById('show-line'));
    // 加载提示
    myChart.showLoading({text: '正在努力加载...'});
// 指定图表的配置项和数据
var option2 = {
    title: {// 标题
        text: '印章刻制数量分析',
        subtext: '数据来自肇庆市印章备案管理信息系统',
        x: 'center'
    },
    tooltip : {// 鼠标移上去作提示
        trigger: 'axis',
        axisPointer: {
            type: 'cross',
            label: {
                backgroundColor: '#6a7985'
            }
        }
    },
    toolbox: { // 可操作图
        right: '20%',
        feature: {
            saveAsImage: {}
        }
    },
    legend: {// 曲线说明，需与series的name作对应
        data:[],
        x: 'left',
        selected:{}
    },
    xAxis: {// X轴
        name: '时间',
        type: 'category',
        boundaryGap: false,
        data: ['一月份', '二月份', '三月份', '四月份', '五月份', '六月份', '七月份', '八月份', '九月份', '十月份', '十一月份', '十二月份'],
        axisLabel:{
            interval:0,//横轴信息全部显示
            rotate:-30,//-30度角倾斜显示
        }

    },
    yAxis: {// y轴
        name: '印章数量（个）',
        type: 'value'
    },
    series: [// 数据
        {
            name: '',
            data: [],
            type: 'line', // 图类型
            label: {// 显示数据信息
                normal: {
                    show: true,
                    position: 'top'
                }
            }
            // areaStyle: {normal: {}}// 区域填充样式
        },
        {
            name: '',
            data: [],
            type: 'line',
            label: {
                normal: {
                    show: true,
                    position: 'top'
                }
            }
            // areaStyle: {normal: {}}
        }
    ]
};
function getShowLineData(areaName) {
    //每次进入此方法是清空select下的option
    // console.log("$(myselect).next().val():"+$("#myselect").next());
        $.getJSON(ctx+'/baseCount/showLineDataSources?areaName='+areaName,
            function (result) {
                if (!(typeof (result.legendData) == "undefined")) {
                    // option2.legend.data = result.legendData;
                    for (var i=0;i<result.legendData.length;i++){
                        //保存下拉框值

                        tempArray.push(result.legendData[i]);
                        $(".multiselect").append("<option value='"+result.legendData[i]+"'>"+result.legendData[i]+"</option>");
                        option2.legend.selected[result.legendData[i]] = false;
                        option2.series[i].data = result.seriesData[i];
                        option2.series[i].name = result.legendData[i];
                    }
                    var myDate = new Date;
                    var year = myDate.getFullYear()+'年';//获取当前年
                    console.log(year);
                    option2.legend.selected[year] = true;

                    // 使用刚指定的配置项和数据显示图表。
                    myChart.clear();
                    myChart.setOption(option2);

                    // 隐藏加载提示
                    myChart.hideLoading();
                    $('.multiselect').multiselect({
                        buttonClass: 'btn',
                        buttonWidth: 'auto',
                        buttonText: function(options) {
                            if (options.length == 0) {
                                return '请选择年份';
                            }
                            else {
                                var selected = '';
                                options.each(function() {
                                    selected += $(this).text() + ',';
                                });
                                return selected.substr(0, selected.length -1) ;
                            }
                        },
                    });
                    // $('.multiselect').multiselect('rebuild')
                    //显示下拉框
                    $("#selectDiv").show();

                }else {//没有任何数据
                    // 使用刚指定的配置项和数据显示图表。
                    //隐藏下拉框
                    $("#selectDiv").hide();

                    console.log("走到else")

                    myChart.clear();
                    // console.log("option2.series[i].data:::"+option2.series[0].data);
                    // myChart.setOption(option2);
                    // 隐藏加载提示
                    myChart.hideLoading();
                }
            });
    }




