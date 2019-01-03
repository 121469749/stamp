/**
 * Created by bb on 2018-08-06.
 */
var option;
var myChart;
var tempArray = new Array();
$(function () {
// 基于准备好的dom，初始化echarts实例
myChart = echarts.init(document.getElementById('show-line'));
    // 加载提示
    myChart.showLoading({text: '正在努力加载...'});
// 指定图表的配置项和数据
    option = {
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
        data: ['一月份', '二月份', '三月份', '四月份', '五月份', '六月份', '七月份', '八月份', '九月份', '十月份', '十一月份', '十二月份']
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
$.getJSON(ctx+'/baseCount/showLineDataSources?areaName=',
        function (result) {

            if (result) {
                // option.legend.data = result.legendData;
                for (var i=0;i<result.legendData.length;i++){
                    //保存下拉值
                    tempArray.push(result.legendData[i]);
                    $(".multiselect").append("<option value='"+result.legendData[i]+"'>"+result.legendData[i]+"</option>");
                    option.legend.selected[result.legendData[i]] = false;
                    option.series[i].data = result.seriesData[i];
                    option.series[i].name = result.legendData[i];

                }
                var myDate = new Date;
                var year = myDate.getFullYear()+'年';//获取当前年
                console.log(year);
                option.legend.selected[year] = true;

                // 使用刚指定的配置项和数据显示图表。
                myChart.setOption(option);
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
                //显示下拉框
                $("#selectDiv").show();
            }else {
                alert('提示：数据获取失败');
            }
        });

});
