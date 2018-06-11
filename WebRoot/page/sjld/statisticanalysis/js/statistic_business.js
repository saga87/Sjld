
var xs_combobox;
var type_combobox;

var gly = user_id;
var counties = dept_id;

var chengban_dept;


var type_name_business = new Array();
var type_nums_business = new Array();


$(function(){
	if(gly=="1"||gly=="58dfbf28-ed18-4d52-a051-ac407c182dcd"||counties=="04"){
		$("#counties").show();
	}else{
		$("#counties").hide();
		chengban_dept = counties;
	}
	
	$.ajax({
        url: "analysis/getBusinessTypeAnalysis",
        dataType : "json",  
        data: {chengban_dept: chengban_dept},
        type : "GET",
        success: function(ctt){        
              for (var i = 0; i < ctt.length; i++) {
            	  type_name_business.push(ctt[i].business_type);
            	  type_nums_business.push(ctt[i].nums);
              } 
              caseTypeZz();
              caseTypeBT();
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
	 chengban_dept = $("#chengban_dept_val").val();
//	 console.log($("#counties").is(":hidden")+"===="+chengban_dept);
	 if($("#counties").is(":hidden")||chengban_dept == undefined){
		 chengban_dept = counties;
	 }
//	 console.log(chengban_dept);
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
              caseTypeBT()
         	}
        }); 
}

function statisticBusiness(){
	type_name_business = new Array();
	type_nums_business = new Array();
	 chengban_dept = $("#chengban_dept_val").val();
	 if($("#counties").is(":hidden")||chengban_dept == undefined){
		 chengban_dept = counties;
	 }
	 var startTime = $("#startTime").val();
	 var endTime = $("#endTime").val();
	 
	$.ajax({
       url: "analysis/getBusinessTypeAnalysis",
       data: {chengban_dept: chengban_dept,
       	startTime: startTime,endTime: endTime},
       dataType : "json",  
       type : "GET",
       success: function(ctt){        
             for (var i = 0; i < ctt.length; i++) {
           	  type_name_business.push(ctt[i].business_type);
           	  type_nums_business.push(ctt[i].nums);
             } 
             caseTypeZz();
             caseTypeBT()
        	}
       }); 
}







function caseTypeBT() {
	var btChart = echarts.init(document.getElementById('caseTypeBT'));
	
	var arrNum = [];
	
	 for (var i = 0; i < type_nums.length; i++) {
         arrNum.push({"value": type_nums[i],"name":type_name[i]});
     }
	
	btChart.setOption({
//		    title : {
//		        text: '某站点用户访问来源',
//		        subtext: '纯属虚构',
//		        x:'center'
//		    },
//		    tooltip : {
//		        trigger: 'item',
//		        formatter: "{a} <br/>{b} : {c} ({d}%)"
//		    },
//		    legend: {
//		        orient : 'vertical',
//		        x : 'left',
//		        data:['直接访问','邮件营销','联盟广告','视频广告','搜索引擎']
//		    },
//		    toolbox: {
//		        show : true,
//		        feature : {
//		            mark : {show: true},
//		            dataView : {show: true, readOnly: false},
//		            magicType : {
//		                show: true, 
//		                type: ['pie', 'funnel'],
//		                option: {
//		                    funnel: {
//		                        x: '25%',
//		                        width: '50%',
//		                        funnelAlign: 'left',
//		                        max: 1548
//		                    }
//		                }
//		            },
//		            restore : {show: true},
//		            saveAsImage : {show: true}
//		        }
//		    },
		    calculable : true,
		   
		    series : [
		        {
//		            name:'访问来源',
		            type:'pie',
		            radius : '55%',
		            center: ['50%', '60%'],
		            data:arrNum
		        }
		    ]
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