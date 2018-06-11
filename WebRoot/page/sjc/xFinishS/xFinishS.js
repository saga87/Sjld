
var url="";
var lay;
var manager;
var perm;
$(function(){

    lay=$("#onceFinishS_layout").ligerLayout({rightWidth:560,isRightCollapse: true});
    
    manager = $("#onceFinishS_maingrid").ligerGrid({
        url:'xFinishS/WkrjXFinishS/getEventCntByBanjieTimes',
        columns: [
        { display: '部门名称', name: 'dept_name', id: 'dept_name', width: '10%', align: 'center',
        	totalSummary:
            {
                type: 'count'
            }
        },
        //{ display: '一次办结', name: 'oncecnt', id: 'oncecnt', width: '10%', align: 'center' },
        { display: '一次办结', name: 'oncecnt', id: 'oncecnt', isSort: false, width: '10%', render: function (rowdata, rowindex, value){
        	var h = "<a style='text-decoration:none;' onclick='oncecnt(\""+rowindex+"\")' href='javascript:void(0)'>"+value+"</a>";
        	return h;
        	}
        },
        //{ display: '多次办结', name: 'timescnt', id: 'timescnt', width: '30%', align: 'center' },
        //{ display: '二次办结', name: 'secondcnt', id: 'secondcnt', width: '10%', align: 'center' },
        { display: '二次办结', name: 'secondcnt', id: 'secondcnt', isSort: false, width: '10%', render: function (rowdata, rowindex, value){
        	var h = "<a style='text-decoration:none;' onclick='secondcnt(\""+rowindex+"\")' href='javascript:void(0)'>"+value+"</a>";
        	return h;
        	}
        },
        //{ display: '三次办结', name: 'thirdcnt', id: 'thirdcnt', width: '10%', align: 'center' },
        { display: '三次办结', name: 'thirdcnt', id: 'thirdcnt', isSort: false, width: '10%', render: function (rowdata, rowindex, value){
        	var h = "<a style='text-decoration:none;' onclick='thirdcnt(\""+rowindex+"\")' href='javascript:void(0)'>"+value+"</a>";
        	return h;
        	}
        },
        //{ display: '四次及以上办结', name: 'fourthcnt', id: 'fourthcnt', width: '10%', align: 'center' },
        { display: '四次及以上办结', name: 'fourthcnt', id: 'fourthcnt', isSort: false, width: '10%', render: function (rowdata, rowindex, value){
        	var h = "<a style='text-decoration:none;' onclick='fourthcnt(\""+rowindex+"\")' href='javascript:void(0)'>"+value+"</a>";
        	return h;
        	}
        },
        { display: '多次办结汇总', name: 'timescnt', id: 'timescnt', isSort: false, width: '10%', render: function (rowdata, rowindex, value){
        	var h = "<a style='text-decoration:none;' onclick='timescnt(\""+rowindex+"\")' href='javascript:void(0)'>"+value+"</a>";
        	return h;
        	}
        },
        { display: '一次办结率', name: 'oncerate', id: 'oncerate', width: '9%', align: 'center',
        	totalSummary:
            {
                type: 'sum', render: total_oncerate
            }
        },
        { display: '二次办结率', name: 'secondrate', id: 'secondrate', width: '10%', align: 'center' },
        { display: '三次办结率', name: 'thirdrate', id: 'thirdrate', width: '10%', align: 'center' },
        { display: '四次及以上办结率', name: 'fourthrate', id: 'fourthrate', width: '10%', align: 'center' }
        ], height: '100%',
        width:'99.9%',
        usePager : false,
        rownumbers : true,
        alternatingRow: true
    });
});

function total_oncerate(rowdata, rowindex, value) {
    var temp = "";
    if (rowdata.count == "0") {
        temp = '';
    } else {
        temp = "平均一次办结率："+(parseFloat(rowdata.sum)/rowdata.count).toFixed(2)+"%";
    }
    return temp;
}

function oncecnt(rowindex) {
	var g = $("#onceFinishS_maingrid").ligerGetGridManager();
	var r = g.getRow(rowindex);
	var type = $('#type').val();
	var start_date = $('#start_date').val();
    var end_date = $('#end_date').val();
    r.start_date = start_date;
    r.end_date = end_date;
	r.type = type;
	r.flag = "1";//一次办结
    var s = parent.$.ligerDialog.open({
        url : "page/sjc/onceFinishS/finishedEvent.jsp",
        width : 900,
        height :650,
        data: {
            content:r
        },
        buttons : [ {
            text : '取消',
            onclick : function(item, dialog) {
                dialog.close();
            }
        } ]

    });
}

function secondcnt(rowindex) {
	var g = $("#onceFinishS_maingrid").ligerGetGridManager();
	var r = g.getRow(rowindex);
	var type = $('#type').val();
	var start_date = $('#start_date').val();
    var end_date = $('#end_date').val();
    r.start_date = start_date;
    r.end_date = end_date;
	r.type = type;
	r.flag = "2";//二次办结
    var s = parent.$.ligerDialog.open({
        url : "page/sjc/onceFinishS/finishedEvent.jsp",
        width : 900,
        height :650,
        data: {
            content:r
        },
        buttons : [ {
            text : '取消',
            onclick : function(item, dialog) {
                dialog.close();
            }
        } ]

    });
}

function thirdcnt(rowindex) {
	var g = $("#onceFinishS_maingrid").ligerGetGridManager();
	var r = g.getRow(rowindex);
	var type = $('#type').val();
	var start_date = $('#start_date').val();
    var end_date = $('#end_date').val();
    r.start_date = start_date;
    r.end_date = end_date;
	r.type = type;
	r.flag = "3";//三次办结
    var s = parent.$.ligerDialog.open({
        url : "page/sjc/onceFinishS/finishedEvent.jsp",
        width : 900,
        height :650,
        data: {
            content:r
        },
        buttons : [ {
            text : '取消',
            onclick : function(item, dialog) {
                dialog.close();
            }
        } ]

    });
}

function fourthcnt(rowindex) {
	var g = $("#onceFinishS_maingrid").ligerGetGridManager();
	var r = g.getRow(rowindex);
	var type = $('#type').val();
	var start_date = $('#start_date').val();
    var end_date = $('#end_date').val();
    r.start_date = start_date;
    r.end_date = end_date;
	r.type = type;
	r.flag = "4";//四次及以上办结
    var s = parent.$.ligerDialog.open({
        url : "page/sjc/onceFinishS/finishedEvent.jsp",
        width : 900,
        height :650,
        data: {
            content:r
        },
        buttons : [ {
            text : '取消',
            onclick : function(item, dialog) {
                dialog.close();
            }
        } ]

    });
}

function timescnt(rowindex) {
	var g = $("#onceFinishS_maingrid").ligerGetGridManager();
	var r = g.getRow(rowindex);
	var type = $('#type').val();
	var start_date = $('#start_date').val();
    var end_date = $('#end_date').val();
    r.start_date = start_date;
    r.end_date = end_date;
	r.type = type;
	r.flag = "5";//多次办结
    var s = parent.$.ligerDialog.open({
        url : "page/sjc/onceFinishS/finishedEvent.jsp",
        width : 900,
        height :650,
        data: {
            content:r
        },
        buttons : [ {
            text : '取消',
            onclick : function(item, dialog) {
                dialog.close();
            }
        } ]

    });
}

//根据条件检索(查询)
function searchE() {
    //var dialog = frameElement.dialog; //调用页面的dialog对象(ligerui对象)
    var start_date = $('#start_date').val();
    var end_date = $('#end_date').val();
    var isshizhi = $('#isshizhi').val();
    //lay.setRightCollapse(false);
    var g = $("#onceFinishS_maingrid").ligerGetGridManager();
    g.set({url:'xFinishS/WkrjXFinishS/getEventCntByBanjieTimes?start_date='+start_date+'&end_date='+end_date+'&isshizhi='+isshizhi});    
    g.reload();
}
/*
 * 显示全部
 */
function showAllE() {
    var g = $("#onceFinishS_maingrid").ligerGetGridManager();
    g.set({url:'xFinishS/WkrjXFinishS/getEventCntByBanjieTimes'});
    g.reload();
}
function exportExcel(){
	$.ligerDialog.waitting('正在导出中,请稍候...');
//    var business_type = $('#business_type').ligerGetComboBoxManager().getValue();
    var start_date = $("#start_date").val();
    var end_date = $("#end_date").val();
    var isshizhi = $('#isshizhi').val();
    $.post('xFinishS/WkrjXFinishS/export', {
    	isshizhi : isshizhi,
        start_date : start_date,
        end_date : end_date
    }, function(result) {
        var result = eval('(' + result + ')');
        result = eval('(' + result + ')');
        $.ligerDialog.closeWaitting();
        if (result.success) {
            window.location.href = "upload/" + result.file_name;
        } else {
            $.ligerDialog.alert('导出失败');
        }
    });
}