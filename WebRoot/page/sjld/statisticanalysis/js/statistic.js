
var xs_combobox;
var type_combobox;


var type_name = new Array();
var type_nums = new Array();

$(function(){
	$.ajax({
        url: "analysis/getTypeAnalysis",
        dataType : "json",  
        type : "GET",
        success: function(ctt){        
              for (var i = 0; i < ctt.length; i++) {
            	  type_name.push(ctt[i].content_type);
            	  type_nums.push(ctt[i].nums);
              } 
              caseTypeZz();
         	}
        }); 
	
	 $("#startTime").ligerDateEditor({ showTime: false, label: '', labelWidth: 100, labelAlign: 'left' });
	 $("#endTime").ligerDateEditor({ showTime: false, label: '', labelWidth: 100, labelAlign: 'left' });
	 xs_combobox = $("#chengban_dept").ligerComboBox({
         url: 'analysis/getCounties?parent_id=04',
         valueField: 'id',
         textField: 'name',
         width: 240,height: 30,selectBoxWidth: 240,selectBoxHeight: 100
     });
	 
	 type_combobox = $("#business_type").ligerComboBox({
         url: 'eventWf/WkrjEventWf/getDataDictionary?parentID=02',
         valueField: 'id',
         textField: 'name',
         width: 240,height: 30,selectBoxWidth: 240,selectBoxHeight: 100
     });
});

function statistic(){
	 type_name = new Array();
	 type_nums = new Array();
	 var chengban_dept = $("#chengban_dept_val").val();
	 var business_type = $("#business_type_val").val();
	 var startTime = $("#startTime").val();
	 var endTime = $("#endTime").val();
	 
	$.ajax({
        url: "analysis/getTypeAnalysis",
        data: {business_type: business_type,chengban_dept: chengban_dept,
        	startTime: startTime,endTime: endTime},
        dataType : "json",  
        type : "GET",
        success: function(ctt){        
              for (var i = 0; i < ctt.length; i++) {
            	  type_name.push(ctt[i].content_type);
            	  type_nums.push(ctt[i].nums);
              } 
              caseTypeZz();
         	}
        }); 
}

function caseTypeZz() {
    var myChart = echarts.init(document.getElementById('caseTypeZz'));
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
    var names = type_name; //类别数组（实际用来盛放X轴坐标值）    
    var series1 = type_nums;
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