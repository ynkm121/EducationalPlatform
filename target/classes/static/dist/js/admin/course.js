$(function(){
    $('#jqGrid').jqGrid({
        url: '/admin/course/list',
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
            {label: '选课人数', name:'selectCount', Index: 'selectCount', width: 20},
            {label: '创建时间', name:'createTime', Index: 'createTime', width: 60},
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
            records: "data.totalCount",
            id: 'courseId',
        },
        prmNames: {
            page: "page",
            rows: "limit",
            order: "order"
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

// 绑定保存按钮
$('#saveButton').click(function () {
    var courseId = $("#courseId").val();
    var courseName = $("#courseName").val();
    var intro = $("#intro").val();
    var credit = $("#credit").val();
    var remain = $("#remain").val();
    if (!validCN_ENString2_100(intro)) {
        $('#edit-error-msg').css("display", "block");
        $('#edit-error-msg').html("请输入符合规范的简介！");
        return;
    }
    if (isNull(credit) || credit < 0) {
        $('#edit-error-msg').css("display", "block");
        $('#edit-error-msg').html("请输入符合规范的学分！");
        return;
    }
    if (isNull(remain) || remain < 0) {
        $('#edit-error-msg').css("display", "block");
        $('#edit-error-msg').html("请输入符合规范的剩余容量！");
        return;
    }
    var url = '/admin/course/save';
    if (courseId != null && courseId > 0) {
        url = '/admin/course/update';
    }
    var params = $("#courseForm").serialize();
    console.log(courseId);
    $.ajax({
        type: 'POST',//方法类型
        url: url,
        data: params,
        success: function (result) {
            if (result.resultCode == 200) {
                $('#courseModal').modal('hide');
                swal("保存成功", {
                    icon: "success",
                });
                reset();
                reload();
            }
            else {
                $('#courseModal').modal('hide');
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

function addCourse(){
    reset();
    getTeacherInfo(null);
    getCourseInfo();
    $('.modal-title').html('添加课程');
    $('#courseModal').modal('show');
}


function editCourse(){
    var id = getSelectedRow();
    if(isNull(id)){
        return;
    }
    reset();
    var rowData = $('#jqGrid').jqGrid('getRowData', id);
    getTeacherInfo(rowData);
    getCourseInfo();
    $("#courseName").val(rowData.courseName);
    $("#intro").val(rowData.intro);
    $("#credit").val(rowData.credit);
    $("#remain").val(rowData.remain);
    $("#courseId").val(rowData.courseId);
    $('.modal-title').html('修改课程');
    $('#courseModal').modal('show');
}

function deleteCourse(){
    // var ids = getSelectedRows();
    var ids = $("#jqGrid").jqGrid('getGridParam','selarrrow');
    if (ids == null) {
        return;
    }
    swal({
        title: "确认弹框",
        text: "确认要删除数据吗?",
        icon: "warning",
        buttons: true,
        dangerMode: true,
    }).then((flag) => {
        if(flag){
            $.ajax({
                type: "POST",
                url: "/admin/course/delete",
                contentType: "application/json",
                data: JSON.stringify(ids),
                success: function (r) {
                    if (r.resultCode == 200) {
                        swal("删除成功", {
                            icon: "success",
                        });
                        $("#jqGrid").trigger("reloadGrid");
                    } else {
                        swal(r.message, {
                            icon: "error",
                        });
                    }
                }
            });
        }
    })
}

function reset() {
    $('#courseId').val('');
    $("#courseName").val('');
    $("#intro").val('');
    $("#credit").val('');
    $("#remain").val(0);
    $('#edit-error-msg').css("display", "none");
    $("#teacherName option:first").prop("selected", 'selected');
}
function getTeacherInfo(rowData) {
    let teacherName = $('#teacherName');
    if(isNull(teacherName.val())) {
        $.get("course/teacherInfo", function (res) {
            if (res.resultCode == 200 && res.data != null) {
                for (let i = 0; i < res.data.length; i++) {
                    var name = res.data[i];
                    if (rowData != null && name === rowData.teacherName) {
                        teacherName.append("<option value='" + name + "' selected >" + name + "</option>")
                    } else {
                        teacherName.append("<option value='" + name + "' >" + name + "</option>")
                    }
                }
            }
        })
    }
}

function getCourseInfo() {
    let prerequisite = $('#prerequisite');
    if(prerequisite[0].options.length === 1){
        $.get("course/courseInfo", function (res){
            if(res.resultCode == 200 && res.data != null){
                for (let i = 0; i < res.data.length; i++) {
                    var course = res.data[i];
                    prerequisite.append("<option value='" + course.courseId + "' >" + course.courseName + "</option>")
                }
            }
        })
    }
}
