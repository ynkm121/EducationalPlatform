var user = $('#test option:selected').text();
$("#test").change(function () {
    $('#edit-error-msg').css("display", "none");
    user = $('#test option:selected').text();
    if (user == "老师") {
        $('.student').css('display', 'none');
        $('.teacher').css('display', 'block');
    }
    if (user == "学生") {
        $('.student').css('display', 'block');
        $('.teacher').css('display', 'none');
    }
})


$("#buttonSubmit").click(function (){
    let formNum = $('#test option:selected').val();
    let data;
    let url;
    switch (formNum){
        case "1":
            data = $('#studentForm').serialize();
            if(studentRegist(data)){
                url = '/register/student';
            }else{
                return;
            }
            break;
        case "2":
            data = $('#teacherForm').serialize();
            if(teacherRegist(data)){
                url = '/register/teacher';
            }else{
                return;
            }
            break;
    }

    $.post({
        url:url,
        data:data,
        success: function (result){
            if (result.resultCode == 200) {
                $('#studentModal').modal('hide');
                swal("提交成功，请等待管理员批准", {
                    icon: "success",
                });
                window.href.location = "/";
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

function studentRegist(data){
    if(isNull(data)){
        return false;
    }
    let studentName = $('#studentName').val();
    let studentId = $('#studentId').val();
    let studentPwd = $('#studentPwd').val();
    let studentPwdRe = $('#studentPwdRe').val();
    let age = $('#age').val();
    if(isNull(studentName) || !validCN_ENString2_18(studentName)){
        $('#edit-error-msg').css("display", "block");
        $('#edit-error-msg').html("请输入符合规范的姓名！");
        return false;
    }
    if(isNull(studentId) || studentId.length != 11){
        $('#edit-error-msg').css("display", "block");
        $('#edit-error-msg').html("请输入符合规范的学号！");
        return false;
    }
    if(isNull(studentPwd) || validPassword(studentPwd)){
        $('#edit-error-msg').css("display", "block");
        $('#edit-error-msg').html("请输入符合规范的密码！");
        return false;
    }
    if(isNull(studentPwdRe) || studentPwdRe !== studentPwd){
        $('#edit-error-msg').css("display", "block");
        $('#edit-error-msg').html("两次密码不一致！");
        return false;
    }
    if(isNull(age)){
        $('#edit-error-msg').css("display", "block");
        $('#edit-error-msg').html("请输入符合规范的年龄！");
        return false;
    }
    return true;

}
function teacherRegist(data){
    if(isNull(data)){
        return false;
    }
    let teacherName = $('#teacherName').val();
    let teacherId = $('#teacherId').val();
    let teacherPwd = $('#teacherPwd').val();
    let teacherPwdRe = $('#teacherPwdRe').val();
    if(isNull(teacherName) || !validCN_ENString2_18(teacherName)){
        $('#edit-error-msg').css("display", "block");
        $('#edit-error-msg').html("请输入符合规范的姓名！");
        return false;
    }
    if(isNull(teacherId) || teacherId.length != 6){
        $('#edit-error-msg').css("display", "block");
        $('#edit-error-msg').html("请输入符合规范的工号！");
        return false;
    }
    if(isNull(teacherPwd) || validPassword(teacherPwd)){
        $('#edit-error-msg').css("display", "block");
        $('#edit-error-msg').html("请输入符合规范的密码！");
        return false;
    }
    if(isNull(teacherPwdRe) || teacherPwdRe !== teacherPwd){
        $('#edit-error-msg').css("display", "block");
        $('#edit-error-msg').html("两次密码不一致！");
        return false;
    }
    return true;
}
