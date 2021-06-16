$(function(){
    $('#jqGrid').jqGrid({
        url: '/teacher/scale/list',
        datatype: "json",
        mtype: "post",
        colModel: [
            {label: '学号', name: 'studentId', Index: 'studentId', width: 60},
            {label: '学生名字', name:'studentName', Index: 'studentName', width: 40},
            {label: '学生年级', name: 'grade', Index: 'grade', width: 60, formatter: function (cellvalue) {
                    switch (cellvalue) {
                        case 1:
                            return "大一";
                        case 2:
                            return "大二";
                        case 3:
                            return "大三";
                        case 4:
                            return "大四";
                    }
                }
            },
            {label: '学生系别', name:'department', Index: 'department', width: 60},
            {label: '打分', name: 'studentId', Index: 'studentId',formatter: function (cellvalue){
                    return "<a id='download' type='button' class='btn btn-block btn-info btn-sm' style='width: 50%;' onclick='scale(\"" + cellvalue + "\")'>打分</a>";
                }, width: 60},
        ],
        height: 700,
        rowNum: 10,
        styleUI: 'Bootstrap',
        loadtext: '信息读取中...',
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

/**
 * jqGrid重新加载
 */
function reload() {
    var page = $("#jqGrid").jqGrid('getGridParam', 'page');
    $("#jqGrid").jqGrid('setGridParam', {
        page: page
    }).trigger("reloadGrid");
}

function scale(studentId){
    reset();
    $('#studentId').val(studentId);
    $('.modal-title').html('学生打分');
    $('#scaleModal').modal('show');
}

function reset(){
    $('#studentId').val('');
    $('#perform').val('');
    $('#final').val('');
    $('#finalGrade').text('');
    $('#edit-error-msg').css("display", "none");
}
//绑定保存按钮
$('#saveButton').click(function () {
    var perform = $("#perform").val();
    var final = $("#final").val();
    if (perform < 0 || perform > 100) {
        $('#edit-error-msg').css("display", "block");
        $('#edit-error-msg').html("请输入[0,100]区间的平时分！");
        return;
    }
    if (final < 0 || final > 100){
        $('#edit-error-msg').css("display", "block");
        $('#edit-error-msg').html("请输入[0,100]区间的期末成绩！");
        return;
    }
    var data = $("#scaleForm").serialize();
    var url = '/teacher/scale/save';
    $.ajax({
        type: 'POST',
        url: url,
        data: data,
        success: function (result){
            if (result.resultCode == 200) {
                $('#scaleModal').modal('hide');
                swal("提交成功", {
                    icon: "success",
                });
                reset();
                reload();
            }
            else {
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

function finalNum(){
    let finalGrade = $('#finalGrade');
    let perform = $('#perform').val();
    if(perform < 0 || perform > 100){
        finalGrade.css('color', 'red');
        finalGrade.text('err, 平时分错误');
        return;
    }
    let final = $('#final').val();
    if(final < 0 || final > 100){
        finalGrade.css('color', 'red');
        finalGrade.text('err, 期末成绩错误');
        return;
    }
    finalGrade.css('color', 'black');
    let num = perform * 0.4 + final * 0.6;
    finalGrade.text(num);
}