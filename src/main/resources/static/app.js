var stompClient = null;

function setConnected(connected) {
    $("#connect").prop("disabled", connected);
    $("#disconnect").prop("disabled", !connected);
    if (connected) {
        $("#conversation").show();
    }
    else {
        $("#conversation").hide();
    }
    $("#greetings").html("");/////////////////////////////////
}

function connect() {
    var socket = new SockJS('/gs-guide-websocket');
    stompClient = Stomp.over(socket);
    stompClient.connect({}, function (frame) {
        setConnected(true);
        console.log('Connected: ' + frame);
        stompClient.subscribe('/topic/greetings', function (greeting) {
            showGreeting(JSON.parse(greeting.body));
            console.log(JSON.parse(greeting.body))///////////////////////////////////////
        });
    });
}

function disconnect() {
    if (stompClient !== null) {
        stompClient.disconnect();
    }
    setConnected(false);
    console.log("Disconnected");
}

function sendName() {
let field = {
option:[],
label: document.getElementById('label').value,
type: document.getElementById('type').value,
isRequired: document.getElementById('isRequired').value == "on"? "true" : "false",
isActive: document.getElementById('isActive').value== "on"? "true" : "false"
};
    stompClient.send("/app/hello", {}, JSON.stringify(field));/////////////////////////////////////
console.log(JSON.stringify(field));
}

function showGreeting(message) {
    $("#greetings").append("<tr><td>" + message + "</td></tr>");///////////////////////////////////////
}

 function fun1() {
 var chbox;
 chbox=document.getElementById('isRequired');
 	if (chbox.checked) {
 		alert('Выбран');
 	}
 	else {
 		alert ('Не выбран');
 	}
 }
 function fun2() {
 var chbox;
 chbox=document.getElementById('isActive');
 	if (chbox.checked) {
 		alert('Выбран');
 	}
 	else {
 		alert ('Не выбран');
 	}
 }

$(function () {
    $("form").on('submit', function (e) {
        e.preventDefault();
    });
    $( "#connect" ).click(function() { connect(); });
    $( "#disconnect" ).click(function() { disconnect(); });
    $( "#send" ).click(function() { sendName(); });
});