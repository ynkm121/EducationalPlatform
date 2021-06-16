$(function(){
    $('#jqGrid').jqGrid({
        url: '/student/courseware/list',
        datatype: "json",
        mtype: "post",
        colModel: [
            {label: '课件简介', name:'coursewareIntro', Index: 'coursewareIntro', width: 120},
            {label: '创建时间', name:'createTime', Index: 'createTime', width: 60},
            {label: '下载课件', name: 'coursewareUrl', Index: 'coursewareUrl',formatter: function (cellvalue){
                    return "<a id='download' type='button' class='btn btn-block btn-info btn-sm' style='width: 50%;' onclick='downloadCourseware(\"" + cellvalue + "\")'>下载</a>";
                }, width: 60},
        ],
        height: 700,
        rowNum: 10,
        styleUI: 'Bootstrap',
        loadtext: '信息读取中...',
        rownumbers: true,
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

})

function downloadCourseware(url){
    window.location.href = url;
}


