var url="";
var lay1,lay2,lay3,lay4;
var manager1,manager2,manager3,manager4;
var perm;
$(function(){
	$("#tab").ligerTab({
        //  onAfterSelectTabItem：这个方法是选中tab后进行的操作
        onAfterSelectTabItem : function (tabid)     {
          if(tabid=='tab1'){
        	  
          }else if(tabid=='tab2'){
        	 lay2=$("#xzliuyan_layout").ligerLayout({rightWidth:560,isRightCollapse: true});
        		
        		manager2 = $("#xzliuyan_maingrid").ligerGrid({
        			url:'viewStt/countXiaozhang',
        	        columns: [
        	        { display: '校长', name: 'user_realname', id: 'user_realname', width: '20%', align: 'center' },
        	        { display: '学校', name: 'school_name', id: 'school_name', width: '60%', align: 'center' },
        	        { display: '留言数', name: 'cts', id: 'cts', width: '20%', align: 'center' },
        	        ], height: '100%',
        	        width:'99.9%',
        	        usePager :true,
        			rownumbers : true,
        	        alternatingRow: true
        	    });
          }else if(tabid=='tab3'){
        	 lay3=$("#xs_layout").ligerLayout({rightWidth:560,isRightCollapse: true});
     		
     		manager3 = $("#xs_maingrid").ligerGrid({
     			url:'viewStt/countByCounties',
     	        columns: [
     	        { display: '县市区', name: 'counties', id: 'counties', width: '70%', align: 'center' },
     	        { display: '留言数', name: 'cts', id: 'cts', width: '30%', align: 'center' },
     	        ], height: '100%',
     	        width:'99.9%',
     	        usePager :true,
     			rownumbers : true,
     	        alternatingRow: true
     	    });
          }else if(tabid=='tab4'){
        	  
        	  $.ajax({
        	        type:'POST',
        	        url:'viewStt/countCaseTotal',
        	        dataType:'json',
        	        success:function(datas){
        	        	$("#caseTotal").text(datas);
        	        }
        	    });
        	  
        	  
        	 lay4=$("#case_layout").ligerLayout({rightWidth:560,isRightCollapse: true});
       		
       		 manager4 = $("#case_maingrid").ligerGrid({
       			url:'viewStt/countCase',
       	        columns: [
       	        { display: '标题', name: 'must_read_title', id: 'must_read_title', width: '50%', align: 'center' },
       	        { display: '浏览量', name: 'pageview', id: 'pageview', width: '30%', align: 'center' },
       	        { display: '评论数', name: 'cts', id: 'cts', width: '20%', align: 'center' },
       	        ], height: '100%',
       	        width:'99.9%',
       	        usePager :true,
       			rownumbers : true,
       	        alternatingRow: true
       	    });
          }   

}});
	
	

	lay1=$("#viewstatistics_layout").ligerLayout({rightWidth:560,isRightCollapse: true});
	
	manager1 = $("#viewstatistics_maingrid").ligerGrid({
		url:'viewStt/countNum',
        columns: [
        { display: '标题', name: 'must_read_title', id: 'must_read_title', width: '50%', align: 'center' },
        { display: '浏览量', name: 'pv', id: 'pv', width: '30%', align: 'center' },
        { display: '留言数', name: 'cts', id: 'cts', width: '20%', align: 'center' },
        ], height: '100%',
        width:'99.9%',
        usePager :true,
		rownumbers : true,
        alternatingRow: true
    });
	
	
});
