$(function(){
    $("#jqGrid").jqGrid({
        url: "unchecked/list",
        datatype: "json",
        mtype: "post",
        colModel: [
            {label: '课程id', name:'courseId', Index: 'courseId', hidden:true},
            {label: '课程名称', name:'courseName', Index: 'courseName', width: 40},
            {label: '任课教师', name:'teacherName', Index: 'teacherName', width: 30},
            {label: '先修课程', name:'prerequisite', Index: 'prerequisite', width: 60},
            {label: '课程简介', name:'intro', Index: 'intro', width: 20},
            {label: '学分', name:'credit', Index: 'credit', width: 20},
            {label: '剩余容量', name:'remain', Index: 'remain', width: 20},
        ],
        height: 700,
        rowNum: 10,
        styleUI: 'Bootstrap',
        loadtext: '信息读取中...',
        rownumbers: false,
        rownumWidth: 20,
        autowidth: true,
        multiselect: true,
        pager: "#jqGridPager",
        jsonReader: {
            root: "data.list",
            page: "data.currPage",
            total: "data.totalPage",
            records: "data.totalCount"
        },
        prmNames: {
            page: "page",
            rows: "limit",
            order: "order",
        },
        gridComplete: function () {
            //隐藏grid底部滚动条
            $("#jqGrid").closest(".ui-jqgrid-bdiv").css({"overflow-x": "hidden"});
        }
    })
})
/**
 * jqGrid重新加载
 */
function reload() {
    var page = $("#jqGrid").jqGrid('getGridParam', 'page');
    $("#jqGrid").jqGrid('setGridParam', {
        page: page
    }).trigger("reloadGrid");
}

// 选课
function toUnchecked(){
    var ids = getSelectedRows();
    if (ids == null) {
        return;
    }
    var text = "";
    var courseId = new Array();
    for(let i = 0; i < ids.length; i++){
        var rowData = $("#jqGrid").jqGrid('getRowData', ids[i]);
        text += rowData.courseName + " ";
        courseId[i] = rowData.courseId;
        console.log(rowData);
    }
    swal({
        titie: "确认弹框",
        text: "你确定要选修 " + text + " 吗？",
        icon: "warning",
        buttons: true,
        dangerMode: true,
    }).then((flag) => {
        if(flag) {
            $.ajax({
                type: "POST",
                url: "unchecked/select",
                contentType: "application/json",
                data: JSON.stringify(courseId),
                success: function (res){
                    if(res.resultCode == 200){
                        swal("选课成功", {
                            icon: "success",
                        });
                        $("#jqGrid").trigger("reloadGrid");
                    }else{
                        swal(res.message, {
                            icon: "error",
                        });
                    }
                }
            })
        }
    })
}