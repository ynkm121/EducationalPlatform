$(function(){
    $("#jqGrid").jqGrid({
        url: '/teacher/course/list',
        datatype: "json",
        mtype: "post",
        colModel: [
            {label: '课程名称', name:'courseName', Index: 'courseName', width: 60},
            {label: '课程简介', name:'intro', Index: 'intro', width: 80},
            {label: '学分', name:'credit', Index: 'credit', width: 30},
            {label: '查看课件', name: 'courseId', Index: 'courseId',formatter: function (cellvalue){
                    return "<a type=\"button\" class=\"btn btn-block btn-success btn-sm\" style=\"width: 50%;\" onclick=\"toCourseware(" + cellvalue + ")\">查看</a>";
                }, width: 40},
            {label: '进行打分', name: 'courseId', Index: 'courseId',formatter: function (cellvalue){
                    return "<a type=\"button\" class=\"btn btn-block btn-success btn-sm\" style=\"width: 50%;\" onclick=\"toScale(" + cellvalue + ")\">查看</a>";
                }, width: 40}
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
    window.location.href = "/teacher/courseware/" + id;
}

function toScale(id){
    window.location.href = "/teacher/scale/" + id;
}
