/**
 * Created by bb on 2018-08-07.
 */
$(function () {

// 基于准备好的dom，初始化echarts实例
var myChart = echarts.init(document.getElementById('show-pie-dataSources'),'light');

// 加载提示
myChart.showLoading({text: '正在努力加载...'});

// 指定图表的配置项和数据
var option = {
    title: {
        text: '辖区印章数据对比',
        subtext: '数据来自肇庆市印章备案管理信息系统',
        x: 'center'
    },
    tooltip : {// 鼠标移上去作提示
        trigger: 'item',
        formatter: "{a} <br/>{b} : {c} ({d}%)"//{a}（系列名称），{b}（数据项名称），{c}（数值）, {d}（百分比）
    },
    toolbox: { // 可操作图
        right: '15%',
        feature: {
            saveAsImage: {}
        }
    },
    legend: {// 图例组件
        orient: 'vertical',
        left: '10%',
        data: []//区域录入
    },
    series : [{
        name: '数据来源',
        type: 'pie',
        radius: '50%',
        // roseType: 'angle',
        data:[],/*{value:135, name:'多证合一', itemStyle:{color:'#ffaeb3'}},
         {value:310, name:'备案系统录入', itemStyle:{color:'#5fc3e8'}}*/
        itemStyle: {
            emphasis: {// hover 的时候通过阴影突出
                shadowBlur: 100,
                shadowOffsetX: 0,
                shadowColor: 'rgba(0, 0, 0, 0.5)'
            }
        },
        label:{
            show:true,
            formatter:'{b}({d}%)'
        },
        labelLine: {// 引导线的颜色
            normal: {
                lineStyle: {
                    // color: 'rgba(255, 255, 255, 0.3)'
                }
            }
        }
    }]
};

$.getJSON(ctx+'/baseCount/showAreaDataSources',
    function (result) {
    if (result) {
        option.legend.data = result.legendData;
        option.series[0].data = result.seriesData;//坑得要命，由于这里series是一个数组，所以要以series[i]的形式
        // 使用刚指定的配置项和数据显示图表。
        myChart.setOption(option);
        // 隐藏加载提示
        myChart.hideLoading();
    }else {
        alert('提示：数据获取失败');
    }
});



});
