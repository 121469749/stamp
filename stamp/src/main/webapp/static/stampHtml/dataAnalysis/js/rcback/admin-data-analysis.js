
function randomData() {
    return Math.round(Math.random()*5000);
}
$(function () {
var dataMap =[
    {name: '北京',value: '100' },{name: '天津',value: randomData() },
    {name: '上海',value: randomData() },{name: '重庆',value: randomData() },
    {name: '河北',value: randomData() },{name: '河南',value: randomData() },
    {name: '云南',value: randomData() },{name: '辽宁',value: randomData() },
    {name: '黑龙江',value: randomData() },{name: '湖南',value: randomData() },
    {name: '安徽',value: randomData() },{name: '山东',value: randomData() },
    {name: '新疆',value: randomData() },{name: '江苏',value: randomData() },
    {name: '浙江',value: randomData() },{name: '江西',value: randomData() },
    {name: '湖北',value: randomData() },{name: '广西',value: randomData() },
    {name: '甘肃',value: randomData() },{name: '山西',value: randomData() },
    {name: '内蒙古',value: randomData() },{name: '陕西',value: randomData() },
    {name: '吉林',value: randomData() },{name: '福建',value: randomData() },
    {name: '贵州',value: randomData() },{name: '广东',value: randomData() },
    {name: '青海',value: randomData() },{name: '西藏',value: randomData() },
    {name: '四川',value: randomData() },{name: '宁夏',value: randomData() },
    {name: '海南',value: randomData() },{name: '台湾',value: randomData() },
    {name: '香港',value: randomData() },{name: '澳门',value: randomData() },{name: '南海诸岛',value: randomData() }
];
// 需要在页面上直接标记出来的城市

var specialMap = [];
// 对dataMap进行处理，使其可以直接在页面上展示
for (var i = 0; i < specialMap.length; i++) {
    for (var j = 0; j < dataMap.length; j++) {
        if (specialMap[i] == dataMap[j].name) {
            dataMap[j].selected = true;
            break;
        }

    }
}

var option = {
    title: {
        text: '各省份印章数据分布情况',
        subtext: '数据来自印章备案管理信息系统',
        x: 'center'
    },
    tooltip: {
        formatter: function (params) {
            var info = '<p style="font-size:18px">' + params.name + '</p><p style="font-size:14px">印章数量：'+ params.value + '个</p>'
            return info;
        },
        backgroundColor: "#ff7f50",//提示标签背景颜色
        textStyle: { color: "#fff" } //提示标签字体颜色
    },
//左侧小导航图标
    visualMap: {
        show : true,
        x: 'left',
        y: 'center',
       /* splitList: [
            {start: 5000, end:6000},{start: 4000, end: 500},
            {start: 3000, end: 4000},{start: 2000, end: 3000},
            {start: 1000, end: 2000},{start: 0, end: 1000},
        ],
        color: ['#5475f5', '#9feaa5', '#85daef','#74e2ca', '#e6ac53', '#9fb5ea']*/
        calculable: true,
        text: ['高','低'],
        min: 0,
        max: 5000,
        inRange: {
            color: ['lightskyblue','yellow', 'orangered']
        }
    },
    series: [
        {
            name: '中国',
            type: 'map',
            mapType: 'china',

            label: {
                normal: {
                    show: true,//显示省份标签
                },
                emphasis: {
                    show: true,//对应的鼠标悬浮效果
                }
            },

            data: dataMap
        }
    ]
};
//初始化echarts实例
var myChart = echarts.init(document.getElementById('container'));
//使用制定的配置项和数据显示图表
myChart.setOption(option);
});