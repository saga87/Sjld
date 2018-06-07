var url="";
var lay;
var manager;
$(function(){
    lay=$("#remind_layout").ligerLayout({rightWidth:560,isRightCollapse: true});
    
    manager = $("#remind_maingrid").ligerGrid({
        url:'remind/getEventOrder',
        columns: [
        { display: '投诉标题', name: 'event_title', id: 'event_title', width: '22%', align: 'center' },
        { display: '限制期限', name: 'remain_hours', id: 'remain_hours', width: '18%', align: 'center', render: showPic },
        { display: '承办单位', name: 'chengban', id: 'chengban', width: '10%', align: 'center' },
        { display: '业务类型', name: 'nature', id: 'nature', width: '10%', align: 'center' },
        { display: '受理渠道', name: 'source', id: 'source', width: '10%', align: 'center' },
        { display: '受理类别', name: 'content_type', id: 'content_type', width: '10%', align: 'center' },
        { display: '受理时间', name: 'event_inputtime', id: 'event_inputtime', width: '12%', align: 'center' },
        { display: '处理时限(天)', name: 'deal_days', id: 'deal_days', width: '8%', align: 'center' },
//        { display: '操作', isSort: false, width: '9%', render: function (rowdata, rowindex, value){
//            var h = "<a style='text-decoration:none;' onclick='print(\""+rowindex+"\")' href='javascript:void(0)'>[打印]</a>";
//            return h;
//            }
//        }
        ], height: '100%',
        width:'99.9%',
        usePager :true,
        rownumbers : true,
        alternatingRow: true,
        toolbar : {
			items : [ {
				text : '查看',
				click : view,
				icon : 'search',
				id:'remind/getEventOrder'
			}]
		}
    });
});

function showPic(rowdata, rowindex, value) {
    var temp = Math.abs(parseInt(rowdata.remain_hours))/24;
    temp = Math.round(temp*100)/100;
    if (rowdata.remain_hours < -1) {
        return "<img src='page/imgs/notover.png'/>"+rowdata.remain_hours+"小时("+temp+"天)";
    } else if (rowdata.remain_hours > 0) {
        return "<img src='page/imgs/over.gif'/>"+rowdata.remain_hours+"小时("+temp+"天)";
    } else {
        return "<img src='page/imgs/tover.gif'/>"+rowdata.remain_hours+"小时("+temp+"天)";
    }
}

function view(row){
	var g = $("#remind_maingrid").ligerGetGridManager();
	var r = g.getSelectedRow();
	if (r == undefined)	{
		$.ligerDialog.alert('请选择一条记录进行查看!');
		return;
	}
	
	parent.$.ligerDialog.open({
		url : "page/sjld/warning/remind_view.jsp",
		width : 900,
		height : 650,
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

