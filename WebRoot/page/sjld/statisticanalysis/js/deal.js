var gly = user_id;
var counties = dept_id;
var chengban_dept;

var type_name_content = new Array();
var type_nums_content = new Array();

var satis_name = new Array();
var satis_nums = new Array();



var lay1;
var manager1;


$(function(){
	if(gly=="1"||gly=="58dfbf28-ed18-4d52-a051-ac407c182dcd"||counties=="04"){
		$("#qxrank").show();
	}else{
		$("#qxrank").hide();
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
	
	
	 	lay1=$("#rank_layout").ligerLayout({rightWidth:560,isRightCollapse: true});
		
		manager1 = $("#rank_maingrid").ligerGrid({
			url:'analysis/dealRankingOpt',
	        columns: [
	        { display: '县市区', name: 'chengban_dept', id: 'chengban_dept', width: '70%', align: 'center' },
	        { display: '数目', name: 'nums', id: 'nums', width: '30%', align: 'center' },
	        ], height: '100%',
	        width:'99.9%',
	        usePager :true,
			rownumbers : true,
	        alternatingRow: true
	    });
	
		$.ajax({
	        url: "analysis/getSatisfaction",
	        dataType : "json",  
	        data: {dept_id: chengban_dept},
	        type : "GET",
	        success: function(data){
	        	  var ctt = data.Rows;
	              for (var i = 0; i < ctt.length; i++) {
	            	  if("1"==ctt[i].satisfy_status){
	            		  satis_name.push("满意");
	            	  }else if("2"==ctt[i].satisfy_status){
	            		  satis_name.push("基本满意");
	            	  }else if("3"==ctt[i].satisfy_status){
	            		  satis_name.push("不满意");
	            	  }
	            	  satis_nums.push(ctt[i].nums);
	              } 
	              caseTypeBT();
	         	}
	        }); 
		
	
});

function rank(){
	var a = $("input[name='radio']:checked").val();
	var b = $("#content_type").val();
	manager1 = $("#rank_maingrid").ligerGrid({
		url:'analysis/dealRankingOpt?opt='+a+"&content_type="+b,
        columns: [
        { display: '县市区', name: 'chengban_dept', id: 'chengban_dept', width: '70%', align: 'center' },
        { display: '数目', name: 'nums', id: 'nums', width: '30%', align: 'center' },
        ], height: '100%',
        width:'99.9%',
        usePager :true,
		rownumbers : true,
        alternatingRow: true
    });
}


function caseTypeBT() {
	var btChart = echarts.init(document.getElementById('SatisfactionBT'));
	var arrNum = [];
	 for (var i = 0; i < satis_nums.length; i++) {
         arrNum.push({"value": satis_nums[i],"name":satis_name[i]});
     }
	btChart.setOption({
			title:{
				show:true,//显示策略，默认值true,可选为：true（显示） | false（隐藏）
	            text: '满意度',//主标题文本，'\n'指定换行
	            x:'100px',
	            y:'100px'
			},
		    calculable : true,
		    series : [
		        {
		            type:'pie',
		            radius : '55%',
		            center: ['50%', '60%'],
		            data:arrNum
		        }
		    ]
		});
}


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
        grid:{left:40,right:80},
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
            name: '(类别事项)',
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
            name: '工单数',
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