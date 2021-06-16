$(function(){
    $('#jqGrid').jqGrid({
        url: '/teacher/courseware/list',
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
        autowidth: true,
        multiselect: true,
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
/**
 * jqGrid重新加载
 */
function reload() {
    var page = $("#jqGrid").jqGrid('getGridParam', 'page');
    $("#jqGrid").jqGrid('setGridParam', {
        page: page
    }).trigger("reloadGrid");
}

$(function (){
    let button = $('#uploadAttach'), interval;
    new AjaxUpload(button, {
        action: '/teacher/upload/courseware',
        name: 'courseware',
        autoSubmit: true,
        responseType: 'json',
        onComplete: function (file, r) {
            if (r != null && r.resultCode == 200) {
                $('#filename').text(r.message)
                return false;
            } else {
                alert(r.message);
            }
        }
    })
})

$('#saveButton').click(function (){
    let intro = $("input[name='intro']").val();
    let fileName = $('#filename').text();
    if(isNull(intro) || isNull(fileName)){
        swal("请完整输入表格信息", {
            icon: "error",
        });
        return;
    }
    let url = "/upload/" + fileName;
    let data = {
        "courseware_url": url,
        "courseware_intro": intro
    }
    let swlMessage = '上传成功';
    console.log(data);
    $.post({
        url: '/teacher/courseware/save',
        data: data,
        success: function (result) {
            console.log("success");
            if (result.resultCode == 200) {
                $('#articleModal').modal('hide');
                $('intro').text('');
                swal({
                    title: swlMessage,
                    type: 'success',
                    showCancelButton: false,
                    confirmButtonColor: '#3085d6',
                    confirmButtonText: '返回课件列表',
                    confirmButtonClass: 'btn btn-success',
                    buttonsStyling: false
                }).then(function () {
                    $("#jqGrid").trigger("reloadGrid");
                })
            }
            else {
                $('#articleModal').modal('hide');
                swal(result.message, {
                    icon: "error",
                });
            }
        },
        error: function () {
            swal("操作失败", {
                icon: "error",
            });
        }
    })
})

function downloadCourseware(url){
    window.location.href = url;
}