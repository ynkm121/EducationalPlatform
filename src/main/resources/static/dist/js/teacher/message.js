$('#confirmButton').click(function (){
    let message = $('#message').val();
    if(isNull(message)){
        swal("请填充完整信息", {
            icon: 'error'
        })
        return;
    }
    if(message.length > 200){
        swal("信息最大长度不能超过200", {
            icon: 'error'
        })
        return;
    }
    let data = $('#messageForm').serialize();
    let courseId = $('#courseId').val();
    let teacherId = $('#websocketTeacherId').val();
    $.post({
        url: '/teacher/message/send',
        data: data,
        success: function (result){
            if(result.resultCode == 200){
                swal("发送成功", {
                    icon: 'success'
                });
            stompClient.send("/app/message/teacher/topic", {}, JSON.stringify({
                'teacherId': teacherId,
                'courseId': courseId,
                'message': message,
            }));
            }else{
                swal(result.message, {
                    icon: 'error'
                })
            }
        },
        error: function (){
            swal("操作失败", {
                icon: 'error'
            })
        }
    })
})

var stompClient = null
window.onload = function (){
    let teacherId = $('#websocketTeacherId').val();
    console.log(teacherId);
    let socket = new SockJS('/point');
    stompClient = Stomp.over(socket);
    stompClient.connect({}, function (frame){
        console.log('Connected:' + frame);
        stompClient.subscribe("/app/message/teacher/" + teacherId, function (response){
            console.log("连接成功");
        })
    })
}