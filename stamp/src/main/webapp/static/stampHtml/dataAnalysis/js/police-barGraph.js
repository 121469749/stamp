/**
 * Created by Administrator on 2018\10\23 0023.
 */
$(function () {
// 基于准备好的dom，初始化echarts实例
    var myChart = echarts.init(document.getElementById('show-barGraph'));
    // 加载提示
    myChart.showLoading({text: '正在努力加载...'});
 var option = {
        title: {
            text: '该区域下每个刻章点所刻制的印章数量',
            subtext: '数据来自肇庆市印章备案管理信息系统',
            x: 'center'
        },
        color: ['#3398DB'],
        tooltip: {
            trigger: 'axis',
            axisPointer: {            // 坐标轴指示器，坐标轴触发有效
                type: 'shadow'        // 默认为直线，可选为：'line' | 'shadow'
            },
            show: true
        },
        toolbox: { // 可操作图
            right: '5%',
            feature: {
                saveAsImage: {}
            }
        },
       /* legend: {
            data: ['刻章数量']
        },*/
        grid: {
            left: '3%',
            right: '10%',
            bottom: '3%',
            containLabel: true
        },
        xAxis: [
            {
                name:"刻章点",
                type: 'category',
                data: [],
                axisTick: {
                    alignWithLabel: true
                },
                axisLabel: {
                    rotate:25,
                    interval: 0,
                }

            }

        ],

        yAxis: [
            {
                name:"数量",
                type: 'value'
            }
        ],
        series: [
            {
                name: '刻章数量',
                type: 'bar',
                barWidth: '60%',
                data: [],
                itemStyle: {
                    normal: {
                        label: {
                            show: true, //开启显示
                            position: 'top', //在上方显示
                            textStyle: { //数值样式
                                color: 'black',
                                fontSize: 16
                            }
                        },
                     /*   color: new echarts.graphic.LinearGradient(
                            0, 0, 0, 1,
                            [
                                {offset: 0, color: '#2FDE80'},
                                {offset: 1, color: '#2FDECA'}
                            ]
                        )*/
                    },
                   /* emphasis: {
                        color: new echarts.graphic.LinearGradient(
                            0, 0, 0, 1,
                            [
                                {offset: 0, color: '#2FDECA'},
                                {offset: 1, color: '#2FDE80'}
                            ]
                        )
                    }*/

                },
            }
        ]
    };
    $.getJSON(ctx + '/baseCount/showBarMakeComDataSources',
        function (result) {
            if (result) {
                option.xAxis[0].data = result.legendData;
                option.series[0].data = result.seriesData;//坑得要命，由于这里series是一个数组，所以要以series[i]的形式
                // 使用刚指定的配置项和数据显示图表。
                myChart.setOption(option);
                // 隐藏加载提示
                myChart.hideLoading();
            } else {
                alert('提示：数据获取失败');
            }
        });
});
