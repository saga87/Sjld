var gly = user_id;
var counties = dept_id;
var chengban_dept;

var type_name_content = new Array();
var type_nums_content = new Array();

$(function(){
	if(gly=="1"||gly=="58dfbf28-ed18-4d52-a051-ac407c182dcd"||counties=="04"){
		$("#counties").show();
	}else{
		$("#counties").hide();
		chengban_dept = counties;
	}
	$.ajax({
        url: "analysis/getTypeAnalysis",
        dataType : "json",  
        data: {chengban_dept: chengban_dept},
        type : "GET",
        success: function(ctt){        
              for (var i = 0; i < ctt.length; i++) {
            	  type_name_content.push(ctt[i].content_type);
            	  type_nums_content.push(ctt[i].nums);
              } 
              caseTypeZz();
         	}
        }); 
});

function caseTypeZz() {
    var myChart = echarts.init(document.getElementById('caseSXTypeZz'));
    // 显示标题，图例和空的坐标轴
    myChart.setOption({
        title: {
            text: ''
        },
        tooltip: {
            trigger: 'axis'
        },
        legend: {
            data: ['']
        },
        grid:{left:40,right:48},
        toolbox: {
            show: false,
            feature: {
                mark: {
                    show: true
                },
                dataView: {
                    show: true,
                    readOnly: false
                },
                magicType: {
                    show: true,
                    type: ['line', 'bar']
                },
                restore: {
                    show: true
                },
                saveAsImage: {
                    show: true
                }
            }
        },
        calculable: true,
        xAxis: {
            name: '(类别)',
            type: 'category',
            boundaryGap: true, //取消左侧的间距
            data: [],
            axisLabel: {
				interval:0, //全部显示
	//			rotate:-30, //倾斜
                show: true,
                textStyle: {
                    color: '#000',
                    fontSize: '10'
                }
            },
            axisLine: {
                lineStyle: {
                    color: '#000'
                }
            },
			//隐藏坐标轴
			 axisTick: {
          show: false
     }      
        },
        yAxis: {
            name: '(数量)',
            type: 'value',
            interval: 100,//y轴刻度
            splitLine: {
                show: false
            }, //去除网格线
            axisLine: {
                lineStyle: {
                    color: '#000'
                }
            }
        },
        series: [{
            name: '录入工单数',
            type: 'bar',
            data: [],
        }]
    });
    myChart.showLoading(); //数据加载完之前先显示一段简单的loading动画
    var names = type_name_content; //类别数组（实际用来盛放X轴坐标值）    
    var series1 = type_nums_content;
            myChart.hideLoading(); //隐藏加载动画
            myChart.setOption({ //加载数据图表
               xAxis: {
		        type: 'category',
		        data: names
		    },
		    yAxis: {
		        type: 'value'
		    },
		    series: [{
		        data: series1,
		        type: 'bar',
		        barWidth : 30
    }]
            });
};