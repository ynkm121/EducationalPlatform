$(function(){
    $('#jqGrid').jqGrid({
        url: '/admin/teacher/list',
        datatype: "json",
        mtype: "post",
        colModel: [
            {label: '教师id', name:'teacherId', Index: 'teacherId', hidden:true},
            {label: '教师姓名', name:'teacherName', Index: 'teacherName', width: 40},
            {label: '密码', name:'password', Index: 'password', width: 50},
            {label: '最近登陆时间', name:'loginTime', Index: 'loginTime', width: 60},
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
            id: 'teacherId',
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
$(function (){

    $('#jqGridRegister').jqGrid({
        url: '/admin/teacher/regist/list',
        datatype: "json",
        mtype: "post",
        colModel: [
            {label: '教师id', name:'teacherId', Index: 'teacherId', hidden:true},
            {label: '教师姓名', name:'teacherName', Index: 'teacherName', width: 40},
            {label: '密码', name:'password', Index: 'password', width: 50},
            {label: '最近登陆时间', name:'loginTime', Index: 'loginTime', width: 60},
            {label: '创建时间', name:'createTime', Index: 'createTime', width: 60},
        ],
        height: 700,
        width: 1226,
        rowNum: 10,
        styleUI: 'Bootstrap',
        loadtext: '信息读取中...',
        multiselect: true,
        pager: "#jqGridPagerRegister",
        jsonReader:{
            root: "data.list",
            page: "data.currPage",
            total: "data.totalPage",
            records: "data.totalCount",
            id: 'teacherId',
        },
        prmNames: {
            page: "page",
            rows: "limit",
            order: "order"
        },
        gridComplete: function(){
            $("#jqGridRegister").closest(".ui-jqgrid-bdiv").css({"overflow-x": "hidden"});
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

function reloadUnregist(){
    var page = $("#jqGridRegister").jqGrid('getGridParam', 'page');
    $("#jqGridRegister").jqGrid('setGridParam', {
        page: page
    }).trigger("reloadGrid");
}

//绑定保存按钮
$('#saveButton').click(function () {
    var teacherId = $("#teacherId").val();
    var teacherName = $("#teacherName").val();
    var password = $("#password").val();
    if (!validCN_ENString2_18(teacherName)) {
        $('#edit-error-msg').css("display", "block");
        $('#edit-error-msg').html("请输入符合规范的名字！");
        return;
    }
    if (isNull(password) || password.length > 20){
        $('#edit-error-msg').css("display", "block");
        $('#edit-error-msg').html("请输入符合规范的密码！");
        return;
    }
    var data = $("#teacherForm").serialize();
    var url = '/admin/teacher/save';
    if(teacherId != null && teacherId > 0){
        url = '/admin/teacher/update';
    }
    $.ajax({
        type: 'POST',
        url: url,
        data: data,
        success: function (result){
            if (result.resultCode == 200) {
                $('#teacherModal').modal('hide');
                swal("保存成功", {
                    icon: "success",
                });
                reset();
                reload();
            }
            else {
                $('#teacherModal').modal('hide');
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

function addTeacher(){
    reset();
    $('.modal-title').html('添加教师');
    $('#teacherModal').modal('show');
}

function editTeacher(){
    var id = getSelectedRow();
    if(id == null){
        return;
    }
    reset();
    var rowData = $('#jqGrid').jqGrid('getRowData', id);
    $("#teacherId").val(rowData.teacherId);
    $("#teacherName").val(rowData.teacherName);
    $("#password").val(rowData.password);
    $('.modal-title').html('修改教师');
    $('#teacherModal').modal('show');
}

function deleteTeacher(){
    var ids = getSelectedRows();
    if (ids == null) {
        return;
    }
    swal({
        title: "确认弹框",
        text: "确认要删除数据吗?",
        icon: "warning",
        buttons: true,
        dangerMode: true,
    }).then((flag) =>{
        if(flag){
            $.ajax({
                type: 'POST',
                url: '/admin/teacher/delete',
                data: JSON.stringify(ids),
                contentType: "application/json",
                success: function (res){
                    if(res.resultCode == 200){
                        swal("删除成功", {
                            icon: "success",
                        });
                        $("#jqGrid").trigger("reloadGrid");
                    }else{
                        swal(res.message, {
                            icon: "error"
                        })
                    }
                },
                error: function () {
                    swal("操作失败", {
                        icon: "error",
                    });
                }
            })

        }
    })
}

function confirmRegist(){
    var grid = $("#jqGridRegister");
    var rowKey = grid.getGridParam("selrow");
    if (!rowKey) {
        swal("请选择一条记录", {
            icon: "warning",
        });
        return;
    }
    var ids =  grid.getGridParam("selarrrow");
    swal({
        title: "确认弹框",
        text: "确认要认证教师信息吗?",
        icon: "warning",
        buttons: true,
        dangerMode: true,
    }).then((flag) =>{
        if(flag){
            $.ajax({
                type: 'POST',
                url: '/admin/teacher/confirm',
                data: JSON.stringify(ids),
                contentType: "application/json",
                success: function (res){
                    if(res.resultCode == 200){
                        swal("认证成功", {
                            icon: "success",
                        });
                        $("#jqGridRegister").trigger("reloadGrid");
                        $("#jqGrid").trigger("reloadGrid");
                    }else{
                        swal(res.message, {
                            icon: "error"
                        })
                    }
                },
                error: function () {
                    swal("操作失败", {
                        icon: "error",
                    });
                }
            })
        }
    })
}

function reset(){
    $("#teacherName").val('');
    $("#password").val('');
}