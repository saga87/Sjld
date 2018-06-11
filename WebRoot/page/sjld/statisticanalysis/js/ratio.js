		var parentID = "6c3d5d3b-17c4-42ac-b465-370b1ff11b2e";
		var lstarttime,nstarttime,ostarttime;
		var lendtime,nendtime,oendtime;
		var ldate,ndate,odate;
		var lnum,nnum,onum;
		var x_names = new Array();
		var y_nums = new Array();
		$(function (){
			$.ajax({
                type:'POST',
                url:'eventWf/WkrjEventWf/getDataDictionary?parentID='+parentID,
                dataType:'json',
                success:function(datas){
                     $('#content_type').autocompleter({                
                         minChars:0,
                         // marker for autocomplete matches
                         highlightMatches: true,
                         // object to local or url to remote search
                         source: datas,
                         // custom template
                         template: '{{ label }}',
                         // show hint
                         hint: false,
                         // abort source if empty field
                         empty: true,
                         // max results
                         callback: function (value, index, selected) {
                             if (selected) {
                                 $("#content_type_hidden").val(selected.label);
                             }
                         }
                     });
                    
                }
            });
			
});
		
		function formatDate(value) {
		    var date = new Date(value).format("yyyy-MM-dd HH:mm");
		    if (date == "1970-01-01 08:00")
		        return "--";
		    else
		        return date;
		}
		
	function ratio(){
		var content_type = $("#content_type").val();
		var selectdate = $("#dateinfo").val();
		if("===请选择当前月份之前的月份===" == selectdate){
			alert("请选择月份");
			return;
		}
		var date = new   Date(Date.parse(selectdate.replace(/-/g,   "/")));
		
//		alert(date.getFullYear()+"==========="+date.getMonth());
		var year = date.getFullYear();
		var month = date.getMonth()+1;
		var upmonth = date.getMonth();
		
		odate = (year-1)+"年"+month+"月";
		ndate = year+"年"+month+"月";
		ostarttime = (year-1)+"-"+month+"-01"+" "+"00:00:00";
		nstarttime =  year+"-"+month+"-01"+" "+"00:00:00";
		
		if(month==12){
			oendtime = year+"-01"+"-01"+" "+"00:00:00";
			nendtime = (year+1)+"-01"+"-01"+" "+"00:00:00";
		}else{
			oendtime = (year-1)+"-"+(month+1)+"-01"+" "+"00:00:00";
			nendtime = year+"-"+(month+1)+"-01"+" "+"00:00:00";
		}
		
		
		
		if(upmonth==0){
			ldate = (year-1)+"年"+"12月";
			lstarttime = (year-1)+"-"+"12"+"-01"+" "+"00:00:00";
			lendtime = year+"-"+month+"-01"+" "+"00:00:00";
		}else{
			ldate = year+"年"+upmonth+"月";
			lstarttime = year+"-"+upmonth+"-01"+" "+"00:00:00";
			lendtime  = year+"-"+month+"-01"+" "+"00:00:00";
		}
		
//		alert("整个上月:"+lstarttime+"====="+lendtime);
//		console.log("当前月:"+ndate+",上一月:"+ldate+",去年:"+odate);
//		console.log("当前月开始:"+nstarttime+",当前月结束:"+nendtime);
//		console.log("上月开始:"+lstarttime+",上月结束:"+lendtime);
//		console.log("同比开始:"+ostarttime+",同比结束:"+oendtime);
//		alert(year+"==========="+month+"======="+upmonth);
		$.ajax({
	        url: "analysis/getRatio",
	        dataType : "json",  
	        data: {content_type: content_type,startTime:nstarttime,
	        	endTime:nendtime},
	        type : "GET",
	        success: function(data){
	        	  x_names.push(ndate);
	        	  y_nums.push(data.Rows[0].nums);
	        	  
	        	  $.ajax({
	      	        url: "analysis/getRatio",
	      	        dataType : "json",  
	      	        data: {content_type: content_type,startTime:lstarttime,
	      	        	endTime:lendtime},
	      	        type : "GET",
	      	        success: function(data2){
	      	        	  x_names.push(ldate);
	      	        	  y_nums.push(data2.Rows[0].nums);
	      	        	  
	      	        	  
	      	        	 $.ajax({
	     	      	        url: "analysis/getRatio",
	     	      	        dataType : "json",  
	     	      	        data: {content_type: content_type,startTime:ostarttime,
	     	      	        	endTime:oendtime},
	     	      	        type : "GET",
	     	      	        success: function(data3){
	     	      	        	  x_names.push(odate);
	     	      	        	  y_nums.push(data3.Rows[0].nums);
	     	      	        	  
	     	      	        	  ratioZz();
	     	      	         	}
	     	      	        }); 
	      	        	  
	      	        	  
	      	        	  
	      	         	}
	      	        }); 
	        	  
	        	  
	        	  
	        	  
	         	}
	        }); 
		
		
		
	}	
	
	
	function ratioZz() {
	    var myChart = echarts.init(document.getElementById('ratioZz'));
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
	        grid:{left:40,right:100},
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
	            name: '(同比环比)',
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
	            data: []
	        }]
	    });
	    myChart.showLoading(); //数据加载完之前先显示一段简单的loading动画
	    var names = x_names; //类别数组（实际用来盛放X轴坐标值）    
	    var series1 = y_nums;
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
		
		