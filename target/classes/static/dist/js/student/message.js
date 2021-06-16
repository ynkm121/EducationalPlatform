function sortCourse(){
    let val = $('#courses option').val();
    console.log(val);
    let courseId = $('#courses option:selected').val();
    let data = 'courseId=' + courseId;
    console.log(courseId);
    if(courseId == 0){
        return;
    }
    $.get({
        url: '/student/message/course',
        data: data,
        success: function (data){
            $("#messageWindow").html(data);
            var select = document.getElementById(courses);

            for (var i = 0; i < select.options.length; i++){
                if (select.options[i].value == courseId){
                    select.options[i].selected = true;
                    break;
                }
            }
        },
        error: function (){
            swal('操作异常',{
                icon: 'error'
            })
        }
    })
}

function toMessageDetail(messageId){
    window.location.href = '/student/message/' + messageId;
}