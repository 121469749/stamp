//地图容器
var chart = echarts.init(document.getElementById('main'));
//34个省、市、自治区的名字拼音映射数组
var provinces = {
    //23个省
    "台湾": "taiwan",
    "河北": "hebei",
    "山西": "shanxi",
    "辽宁": "liaoning",
    "吉林": "jilin",
    "黑龙江": "heilongjiang",
    "江苏": "jiangsu",
    "浙江": "zhejiang",
    "安徽": "anhui",
    "福建": "fujian",
    "江西": "jiangxi",
    "山东": "shandong",
    "河南": "henan",
    "湖北": "hubei",
    "湖南": "hunan",
    "广东": "guangdong",
    "海南": "hainan",
    "四川": "sichuan",
    "贵州": "guizhou",
    "云南": "yunnan",
    "陕西": "shanxi1",
    "甘肃": "gansu",
    "青海": "qinghai",
    //5个自治区
    "新疆": "xinjiang",
    "广西": "guangxi",
    "内蒙古": "neimenggu",
    "宁夏": "ningxia",
    "西藏": "xizang",
    //4个直辖市
    "北京": "beijing",
    "天津": "tianjin",
    "上海": "shanghai",
    "重庆": "chongqing",
    //2个特别行政区
    "香港": "xianggang",
    "澳门": "aomen"
};

//直辖市和特别行政区-只有二级地图，没有三级地图
var special = ["北京","天津","上海","重庆","香港","澳门"];
var mapdata = [];

//绘制全国地图
$.getJSON(ctxJson+'static/map/china.json', function(data){

    var d = [];
	//注册地图
	echarts.registerMap('china', data);
    //绘制地图
    renderMap("china",d);
    //统计申请、已制作、已交付、企业总个数
    getToatalData("中国");
    //折线图
    getShowLineData("中国");

    mapdata = getData(data,"中国");

});

//地图点击事件
chart.on('click', function (params) {
	// console.log( params );
	if( params.name in provinces ){
		//如果点击的是34个省、市、自治区，绘制选中地区的二级地图
		$.getJSON(ctxJson+'static/map/province/'+ provinces[params.name] +'.json', function(data){
			echarts.registerMap( params.name, data);
			//统计申请、已制作、已交付、企业总个数
            getToatalData(params.name);
            //折线图
            getShowLineData(params.name);
            getData(data,params.name);

		});
	}else if( params.seriesName in provinces ){
		//如果是【直辖市/特别行政区】只有二级下钻
		if(  special.indexOf( params.seriesName ) >=0  ){
            //统计申请、已制作、已交付、企业总个数
            getToatalData("中国");
            //折线图
            getShowLineData(params.name);
			renderMap('china',mapdata);
		}else{
            console.log("市："+params.name);
			//显示县级地图
			$.getJSON(ctxJson+'static/map/city/'+ cityMap[params.name] +'.json', function(data){
				echarts.registerMap( params.name, data);
                //统计申请、已制作、已交付、企业总个数
                getToatalData(params.name);
                //折线图
                getShowLineData(params.name);

                getDataForArea(data,params.name);
			});	
		}	
	}else{
	    console.log("到这了了");
        //统计申请、已制作、已交付、企业总个数
        getToatalData("中国");
        //折线图
        getShowLineData("中国");
		renderMap('china',mapdata);
	}
});

//初始化绘制全国地图配置
var option = {
	// backgroundColor: '#000',
    title : {
        text: '各省份印章数据分布情况',
        subtext: '数据来自印章备案管理信息系统',
        left: 'center',
        textStyle:{
            // color: '#fff',
            fontSize:30,
            fontWeight:'normal',
            fontFamily:"Microsoft YaHei"
        },
        subtextStyle:{
        	// color: '#ccc',
            fontSize:20,
            fontWeight:'normal',
            fontFamily:"Microsoft YaHei"
        }
    },
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
        max: 60000,
        inRange: {
            color: ['lightskyblue','yellow', 'orangered']
        }
    },
    tooltip: {
        trigger: 'item',
        formatter: function (params) {
            var info = '<p style="font-size:18px">' + params.name + '</p><p style="font-size:14px">印章数量：'+ params.value + '个</p>'
            return info;
        },
        // formatter: '{b}'
    },
    toolbox: {
        show: true,
        orient: 'vertical',
        right: '5%',
        top: 'center',
        feature: {
            dataView: {readOnly: false},
            restore: {},
            saveAsImage: {}
        },
        iconStyle:{
        	normal:{
        		color:'#ff7f50'
        	}
        }
    },
    animationDuration:1000,
	animationEasing:'cubicOut',
	animationDurationUpdate:1000
     
};
function renderMap(map,data){
    // console.log("map:"+map);
    if(map=="china"){
        option.title.subtext = '中国'
    }else{
        option.title.subtext = map;
    }
    option.series = [ 
		{
            name: map,
            type: 'map',
            mapType: map,
            roam: false,
            nameMap:{
			    'china':'中国'
			},
            label: {
	            normal:{
					show:true,
					textStyle:{
						// color:'#999',
						fontSize:13
					}  
	            },
	            emphasis: {
	                show: true,
	                textStyle:{
						color:'#fff',
						fontSize:13
					}
	            }
	        },
	        itemStyle: {
	            normal: {
	                // areaColor: '#323c48',
	                borderColor: 'dodgerblue'
	            },
	            emphasis: {
	                areaColor: 'darkorange'
	            }
	        },
            data:data
        }	
    ];
    //渲染地图
    chart.setOption(option);
}



//国家、省、市级别
function getData(data,areaName) {
    var d = [];
    $.ajax({
        type:'POST',
        contentType :'application/json',
        url :ctx+'/adminCount/getTotalApplyCountList?areaName='+areaName,
        dataType: 'json',
        success:function (returnData) {
            for( var i=0;i<data.features.length;i++ ){
                for (var j=0;j<returnData.length;j++){
                    if(data.features[i].id == returnData[j].code){
                        /*console.log("省或市："+data.features[i].properties.name);
                        console.log("data.features[i].id="+data.features[i].id+"   returnData[j].code= "+returnData[j].code);
                        console.log("returnData[j].value="+returnData[j].value);
                        console.log("-----------------");*/
                        d.push({
                            name:data.features[i].properties.name,
                            value:returnData[j].value
                        })
                    }
                }
                if(areaName == "中国"){
                    areaName = "china";
                }
                renderMap(areaName,d);
            }
        },
        error:function (e) {
            console.log("错误："+e);
            alert("请联系管理员！");
        }
    });
    return d;
}
//区级别
function getDataForArea(data,areaName) {
    var d = [];
    $.ajax({
        type:'POST',
        contentType :'application/json',
        url :ctx+'/adminCount/getTotalApplyCountList?areaName='+areaName,
        dataType: 'json',
        success:function (returnData) {
            for( var i=0;i<data.features.length;i++ ){
                for (var j=0;j<returnData.length;j++){
                    if(data.features[i].properties.name == returnData[j].areaName){
                        d.push({
                            name:data.features[i].properties.name,
                            value:returnData[j].value
                        })
                    }
                }

                renderMap(areaName,d);
            }
        },
        error:function (e) {
            console.log("错误："+e);
            alert("请联系管理员！");
        }
    });
    return d;
}