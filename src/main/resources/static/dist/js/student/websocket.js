var stompClient = null;

window.onload = function (){
    let studentId = $('#websocketStudentId').val();
    let socket = new SockJS('/point');
    stompClient = Stomp.over(socket);
    stompClient.connect({}, function (frame){
        console.log('Connected:' + frame);
        stompClient.subscribe("/message/student/" + studentId, function (response){
            let message = JSON.parse(response.body);
            let teacherName = message.teacherName;
            let courseName = message.courseName;
            let content = message.content;
            parent.$(document).Toasts('create', {
                class: 'bg-info',
                title: '收到一条来自' + teacherName +'老师的新消息',
                subtitle: courseName,
                body: content
            })
        })
    })
}
