function print(event_id){
    var LODOP = getLodop();
    if(LODOP!=null){
            var first="";
               $.ajax({
                   type:'POST',
                   url:'eventReportWf/WkrjEventReportWf/getPrintPage?event_id='+event_id,
                   //data: {event_id:event_id},
                   async:false,
                   success:function(data){
                       first=data;                                  
                   }  
               });
               LODOP.PRINT_INIT("打印");                       
            LODOP.ADD_PRINT_HTM(50, 60, "90%", "90%", first);
            LODOP.PREVIEW();                         
    }
}

function displayOrNot(rowdata, rowindex, value) {
    var temp = "";
    if (rowdata.user_secret == "隐藏用户信息") {
        temp = '隐藏';
    } else {
        temp = value;
    }
    return temp;
}

function comTrans(rowdata, rowindex, value) {
    var temp = "";
    if (value == "1") {
        temp = '是';
    } else if (value == "0") {
        temp = "否";
    } else {
        temp = "";
    }
    return temp;
}

function typeTrans(rowdata, rowindex, value) {
    var temp = "";
    if (value == "1") {
        temp = '完成';
    } else if (value == "2") {
        temp = "交办";
    } else if (value == "3") {
        temp = "转办";
    } else if (value == "4") {
        temp = "未完成";
    }
    return temp;
}

function natureTrans(rowdata, rowindex, value) {
    var temp = "";
    if (value == "201") {
        temp = '咨询';
    } else if (value == "202") {
        temp = "建议";
    } else if (value == "203") {
        temp = "投诉";
    } else if (value == "204") {
        temp = "举报";
    } else if (value == "205") {
        temp = "求助";
    }
    return temp;
}

function zhuanbanTrans(rowdata, rowindex, value) {
    var temp = "";
    if (value == "1") {
        temp = '是';
    } else if (value == "0") {
        temp = "<span style=\"color:red\">否</span>";
    } else {
        temp = "";
    }
    return temp;
}

function statusTrans(rowdata, rowindex, value) {
    if (rowdata.qianshou_status == 1) {
        return "处理中";
    } else if (rowdata.qianshou_status == 0) {
        return "新受理";
    } else if (rowdata.qianshou_status == 2 && rowdata.satisfy_status == null) {
        return "处理完毕待评价";
    } else if (rowdata.satisfy_status == 1) {
        return "已评价，满意";
    } else if (rowdata.satisfy_status == 2) {
        return "已评价，基本满意";
    } else if (rowdata.satisfy_status == 3) {
        return "已评价，不满意";
    }
}