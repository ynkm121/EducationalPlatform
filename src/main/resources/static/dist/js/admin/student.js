$(function(){
    $('#jqGrid').jqGrid({
        url: '/admin/student/list',
        datatype: "json",
        mtype: "post",
        colModel: [
            {label: '学生id', name:'studentId', Index: 'studentId', hidden:true},
            {label: '学生姓名', name:'studentName', Index: 'studentName', width: 40},
            {label: '年级', name:'studentGrade', Index: 'studentGrade', width: 30, formatter: function (cellvalue){
                    switch (cellvalue) {
                        case 1: return "大一";
                        case 2: return "大二";
                        case 3: return "大三";
                        case 4: return "大四";
                    }
                }},
            {label: '密码', name:'password', Index: 'password', width: 50},
            {label: '年龄', name:'age', Index: 'age', width: 50},
            {label: '性别', name:'sex', Index: 'sex', width: 20, formatter: function (cellvalue){
                    if(cellvalue == 0){return "男";}
                    else{return "女";}
                }},
            {label: '所属院系', name:'departmentName', Index: 'departmentName', width: 40},
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
            id: 'studentId',
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
        url: '/admin/student/regist/list',
        datatype: "json",
        mtype: "post",
        colModel: [
            {label: '学生id', name:'studentId', Index: 'studentId', hidden:true},
            {label: '学生姓名', name:'studentName', Index: 'studentName', width: 40},
            {label: '年级', name:'studentGrade', Index: 'studentGrade', width: 30, formatter: function (cellvalue){
                    switch (cellvalue) {
                        case 1: return "大一";
                        case 2: return "大二";
                        case 3: return "大三";
                        case 4: return "大四";
                    }
                }},
            {label: '密码', name:'password', Index: 'password', width: 50},
            {label: '年龄', name:'age', Index: 'age', width: 50},
            {label: '性别', name:'sex', Index: 'sex', width: 20, formatter: function (cellvalue){
                    if(cellvalue == 0){return "男";}
                    else{return "女";}
                }},
            {label: '所属院系', name:'departmentName', Index: 'departmentName', width: 40},
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
            id: 'studentId',
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
    var studentId = $("#studentId").val();
    var studentName = $("#studentName").val();
    var password = $("#password").val();
    var age = $('#age').val()
    if (!validCN_ENString2_18(studentName)) {
        $('#edit-error-msg').css("display", "block");
        $('#edit-error-msg').html("请输入符合规范的名字！");
        return;
    }
    if (isNull(password) || password.length > 20){
        $('#edit-error-msg').css("display", "block");
        $('#edit-error-msg').html("请输入符合规范的密码！");
        return;
    }
    if(isNull(age) || age < 0 || age > 150){
        $('#edit-error-msg').css("display", "block");
        $('#edit-error-msg').html("请输入符合规范的年龄！");
        return;
    }
    var data = $("#studentForm").serialize();
    var url = '/admin/student/save';
    if(studentId != null && studentId > 0){
        url = '/admin/student/update';
    }
    $.ajax({
        type: 'POST',
        url: url,
        data: data,
        success: function (result){
            if (result.resultCode == 200) {
                $('#studentModal').modal('hide');
                swal("保存成功", {
                    icon: "success",
                });
                reset();
                reload();
            }
            else {
                $('#studentModal').modal('hide');
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

function addStudent(){
    reset();
    if($('#departmentName').val() == null){
        $.get("student/departInfo", function (res){
            if(res.resultCode == 200 && res.data != null){
                $('#departmentName').append("<option value='" + res.data[0] + "' selected  >" + res.data[0] + "</option>")
                for(let i = 1; i < res.data.length; i++){
                    var departName = res.data[i];
                    $('#departmentName').append("<option value='" + departName + "' >" + departName + "</option>")
                }
            }
        })
    }
    $('.modal-title').html('添加学生');
    $('#studentModal').modal('show');
}

function editStudent(){
    var id = getSelectedRow();
    if(id == null){
        return;
    }
    reset();
    var rowData = $('#jqGrid').jqGrid('getRowData', id);
    if($('#departmentName').val() == null){
        $.get("student/departInfo", function (res){
            if(res.resultCode == 200 && res.data != null){
                $('#departmentName').append("<option value='" + res.data[0] + ">" + res.data[0] + "</option>")
                for(let i = 1; i < res.data.length; i++){
                    var departName = res.data[i];
                    if(departName === rowData.departmentName){
                        $('#departmentName').append("<option value='" + departName + "' selected >" + departName + "</option>")
                    }else{
                        $('#departmentName').append("<option value='" + departName + "' >" + departName + "</option>")
                    }
                }
            }
        })
    }
    $("#studentId").val(rowData.studentId);
    $("#studentName").val(rowData.studentName);
    $("#password").val(rowData.password);
    $("#age").val(rowData.age);
    $("#studentGrade").find("option:contains('" + rowData.studentGrade + "')").prop("selected",true);
    $("#sex").find("option:contains('" + rowData.sex + "')").prop("selected",true);
    $('.modal-title').html('修改学生');
    $('#studentModal').modal('show');
}

function deleteStudent(){
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
                url: '/admin/student/delete',
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
        text: "确认要认证学生信息吗?",
        icon: "warning",
        buttons: true,
        dangerMode: true,
    }).then((flag) =>{
        if(flag){
            $.ajax({
                type: 'POST',
                url: '/admin/student/confirm',
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
    $("#studentName").val('');
    $("#password").val('');
    $("#age").val('');
    $("#departmentName option:first").prop("selected", 'selected');
    $("#studentGrade option:first").prop("selected", 'selected');
    $('#edit-error-msg').css("display", "none");
    $("#sex option:first").prop("selected", 'selected');
}