$(function(){
    $("#jqGrid").jqGrid({
        url: '/student/select/list',
        datatype: "json",
        mtype: "post",
        colModel: [
            {label: '课程名称', name:'courseName', Index: 'courseName', width: 40},
            {label: '教师名称', name:'teacherName', Index: 'teacherName', width: 60},
            {label: '课程简介', name:'intro', Index: 'intro', width: 80},
            {label: '课程成绩', name:'studentScore', Index: 'studentScore', width: 20, formatter:function (cellvalue){
                if(cellvalue != -1){
                    return cellvalue;
                }else{
                    return "未结课";
                }
                }},
            {label: '查看课件', name: 'courseId', Index: 'courseId',align: 'center', formatter: function (cellvalue, rowObject){
                    let id = rowObject.rowId;
                    let rowData = $('#jqGrid').jqGrid('getRowData');
                    console.log(rowData)
                    return "<a type=\"button\" class=\"btn btn-block btn-success btn-sm\" style=\"width: 50%;\" onclick=\"toCourseware(" + cellvalue + ")\">查看</a>";
                }, width: 60}
        ],
        height: 700,
        rowNum: 10,
        styleUI: 'Bootstrap',
        loadtext: '信息读取中...',
        rownumbers: false,
        rownumWidth: 20,
        autowidth: true,
        pager: "#jqGridPager",
        jsonReader:{
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
        gridComplete: function(){
            $("#jqGrid").closest(".ui-jqgrid-bdiv").css({"overflow-x": "hidden"});
        },


    })

});
$(window).resize(function () {
    $("#jqGrid").setGridWidth($(".card-body").width());
});

function toCourseware(id){
    window.location.href = "/student/courseware/" + id;
}

function toUnchecked(){
    window.location.href = "/student/unchecked";
}