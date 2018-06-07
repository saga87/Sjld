<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!doctype html>
<html lang="en">
<%@include file="/systemdev/myContext.jsp" %>
 <head>
 <link rel="stylesheet" type="text/css" href="page/sjld/homepage/css/style.css">
<script type="text/javascript" src="page/sjld/homepage/js/main.js"></script>
<script type="text/javascript">

	function f_click(a){
		
		top.tab.addTabItem({text:"公告", url: 'page/sjld/homepage/news.jsp?na_id='+a});
	}
	

	$(function(){
		$.ajax({
		url:'homepage/news/getHotNews',
		type:'post',
		data:{page:1,pagesize:10},
		dataType:'json',
		success:function (data) {
		 var ret =  data.Rows;
		 var sum = ret.length;
		 if(sum>0){
		 	for (var i = 0; i < sum; i++) {
		 	var news = ret[i];
		 	var valueStr = JSON.stringify(news);
		 //	console.log(valueStr);
		 	$('#newsul').append('<li style="margin-top:5px;cursor:pointer" onclick="f_click(\''+ret[i].na_id+'\')">'+(i+1)+'.'+ret[i].na_title+'&nbsp;<span style="color:red;">new!</span></li>');
		 	}
		 }
			
		}
	});
	
	
	$.ajax({
		url:'remind/getEventOrder',
		type:'post',
		data:{page:1,pagesize:5},
		dataType:'json',
		success:function (data) {
		 var ret =  data.Rows;
		 var sum = ret.length;
		 if(sum>0){
		 	for (var i = 0; i < sum; i++) {
		 	$('#naul').append('<li style="margin-top:5px;" >'+(i+1)+'.'+ret[i].event_title+'&nbsp;<span style="color:red;">new!</span></li>');
		 	}
		 }
			
		}
	});
	
	
	});
</script>
 </head>
 <body onload=initial();>
 
 	<div style="margin-top: 30px;margin-left: 10px;border:1px solid #000;width:400px;padding:10px">
      	<p style="font-size: 15px;color: red">最新公告:</p>
      	<ul id="newsul" style="padding: 5px;">
      		
      	</ul>
      </div>
 
  	<div style="margin-top: 30px;margin-left: 450px;border:1px solid #000;width:400px;padding:10px">
      	<p style="font-size: 15px;color: red">工单提醒:</p>
      	<ul id="naul" style="padding: 5px;">
      		
      	</ul>
      </div>
 
 	<iframe name="weather_inc" src="http://i.tianqi.com/index.php?c=code&id=57" width="650" height="427" frameborder="0" marginwidth="0" marginheight="0" scrolling="no"
 	 style = "margin-left: 450px;margin-top: 10px"></iframe>
 	
	<div id="content">
	
	
	
<!-- 显示日期详情 -->
  <div id="detail">
       <div id="date_content"></div>

       <div id="animal_year"></div>
        <!-- 显示时钟 -->
        <p id="clock"></p>
  </div>
  <form name="calender_content" style="margin-top: -15px;">
                  <table class="week">
                    <tbody>
                       <tr>
                          <td class="ch">
                                  <!-- 日历头部 -->
                                 <table>
                                     <tbody>
                                           <tr class="day">
                                                  <td>日</td>
                                                  <td>一</td>
                                                  <td>二</td>
                                                  <td>三</td>
                                                  <td>四</td>
                                                  <td>五</td>
                                                  <td>六</td>
                                              </tr>
                                         </tbody>
                                  </table>
                                  </tr>
                                  </td>
                                    <!-- 头部END -->
                                 
                 <!-- js动态的向表格写入日期 -->
                            <script>
                            var Num; //Num计算出日期位置
                            for(i=0;i<6;i++) {

                                    document.write('<table id="cal-content"><tr>');
                                   
                             for(j=0;j<7;j++) {
                                Num = i*7+j;
                                document.write('<td id="SD' + Num +'" onclick="addDay(' + Num +')" ');
                        //周六 周日 假期样式设定
                              if(j == 0|| j == 6)
                              {
                                    document.write(' class="aorange"');
                               }else{
                                    document.write(' class="one"');
                              }
                                    document.write('title=""> </td>')
                             }

                                document.write('</tr></table></td></tr><tr><td><table style="width:399;"><tr style="text-align:center"> ');
                           //农历
                           for(j=0;j<7;j++) {
                              Num = i*7+j;
                              document.write('<td id="LD' + Num +'" onclick="addDay(' + Num +')" class="bs" title=""> </td>')

                           }
                              document.write('</tr></table></td></tr>');
                           
                         }
                         </script>  
             <!-- 生成日期 END    -->
                        </tr>
                     </table>
                   </td>
                </tr>   
              </tbody>
            </table>
             <table>
               <tbody>
                <tr>
                  <td class="sm">
                    <table class="table_head">
                      <tbody>
                      <tr>
                        <td> 
                        <!-- 选择年份菜单 -->
                          <div class="year_select">
                              <span onClick="BtN('year_d')"><img src="page/sjld/homepage/img/left.png"></span>
                                <select onChange="chaCld()" name="SY">
                                  <script>
                                     for(i=1900;i<2050;i++) 
                                    document.write('<option>'+i+"年")
                                  </script>
                                </select> 
                              <span onClick="BtN('year_a')"><img src="page/sjld/homepage/img/right.png"></span> 
                          </div>
                          <!-- 回到当天菜单 -->
                          <div  class="home_select">
                               <span onClick="BtN('')"><img src="page/sjld/homepage/img/2.png" style="width:26px;height:26px"></span>
                          </div>

                          <!-- 选择月份菜单 -->
                              <div style="display:inline-block;">
                                  <span onClick="BtN('month_d')"><img src="page/sjld/homepage/img/left.png"></span>
                                   <select onChange="chaCld()" name="SM">
                                    <script>
                                    for(i=1;i<13;i++) document.write('<option>'+i+"月")
                                    </script>
                                    </select> 
                                  <span onClick="BtN('month_a')"><img src="page/sjld/homepage/img/right.png"></span>
                              </div>
                           </td>
                       </tr>
                     </tbody>
                    </table>
                  </td>
                </tr> 
             </tbody>
           </table>
      </form>
      
      
      
      
   </div>
 </body>
 </html>