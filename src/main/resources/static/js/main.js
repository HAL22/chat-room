'use strict';

var usernamePage = document.querySelector('#username-page');
var chatPage = document.querySelector('#chat-page');
var usernameForm = document.querySelector('#usernameForm');
var messageForm = document.querySelector('#messageForm');
var messageInput = document.querySelector('#message');
var messageArea = document.querySelector('#messageArea');
var connectingElement = document.querySelector('.connecting');
var errorMessage = document.querySelector('#error-message')

var stompClient = null;
var username = null;
var password = null

var colors = [
    '#2196F3', '#32c787', '#00BCD4', '#ff5652',
    '#ffc107', '#ff85af', '#FF9800', '#39bbb0'
];

function connect(event) {
    username = document.querySelector('#name').value.trim();
    password = document.querySelector('#pass').value.trim();

    if(username && password){
        var socket = new SockJS('/chat-websocket');
        stompClient = Stomp.over(socket);

        stompClient.connect({}, onLogin, onError);
    }

    event.preventDefault();
}

function onLogin(){
    // Subscribe to the login Topic
    stompClient.subscribe('/topic/login', onLoginDetails);

    // Tell your username to the server
    stompClient.send("/app/chat.loginUser",
        {},
        JSON.stringify({username: username,password:password})
    )

}


function onConnected() {
     stompClient.unsubscribe('/topic/login')

    // Subscribe to the Public Topic
    stompClient.subscribe('/topic/public', onMessageReceived);

    // Tell your username to the server
    stompClient.send("/app/chat.addUser",
        {},
        JSON.stringify({sender: username, type: 'JOIN'})
    )

    connectingElement.classList.add('hidden');
}

function onLoginDetails(payload){
    var message = JSON.parse(payload.body);

    if(message.isAUser){
        usernamePage.classList.add('hidden');
        chatPage.classList.remove('hidden');

        
        onConnected()
        
    }else{
        errorMessage.style.display = 'block'
        stompClient.unsubscribe('/topic/login')
    }
}


function onError(error) {
    connectingElement.textContent = 'Could not connect to WebSocket server. Please refresh this page to try again!';
    connectingElement.style.color = 'red';
}


function sendMessage(event) {
    stompClient.unsubscribe('/topic/login')
    var messageContent = messageInput.value.trim();
    if(messageContent && stompClient) {
        var chatMessage = {
            sender: username,
            content: messageInput.value,
            type: 'CHAT'
        };
        stompClient.send("/app/chat.sendMessage", {}, JSON.stringify(chatMessage));
        messageInput.value = '';
    }
    event.preventDefault();
}


function onMessageReceived(payload) {
    var message = JSON.parse(payload.body);

    var messageElement = document.createElement('li');

    if(message.type === 'JOIN') {
        messageElement.classList.add('event-message');
        message.content = message.sender + ' joined!';
    } else if (message.type === 'LEAVE') {
        messageElement.classList.add('event-message');
        message.content = message.sender + ' left!';
    } else if(message.type === "HISTORY" && username === message.sender){
        console.log(message.history)
        for(let i = 0; i < message.history.length; i++){
            console.log(message.history[i])
            messageElement.classList.add('chat-message');

            var avatarElement = document.createElement('i');
            var avatarText = document.createTextNode(message.history[i].sender[0]);
            avatarElement.appendChild(avatarText);
            avatarElement.style['background-color'] = getAvatarColor(message.history[i].sender);

            messageElement.appendChild(avatarElement);

            var usernameElement = document.createElement('span');
            var chatdetails = " < id:"+ message.history[i].contentId +", timestamp:" + message.history[i].timestamp + " >"
            var usernameText = document.createTextNode(message.history[i].sender+chatdetails);
            usernameElement.appendChild(usernameText);
            messageElement.appendChild(usernameElement);

            var textElement = document.createElement('p');
            
            var messageText = document.createTextNode(message.history[i].content);
            textElement.appendChild(messageText);

            messageElement.appendChild(textElement);

            messageArea.appendChild(messageElement);
            messageArea.scrollTop = messageArea.scrollHeight;
        }
    } else {
        messageElement.classList.add('chat-message');

        var avatarElement = document.createElement('i');
        var avatarText = document.createTextNode(message.sender[0]);
        avatarElement.appendChild(avatarText);
        avatarElement.style['background-color'] = getAvatarColor(message.sender);

        messageElement.appendChild(avatarElement);

        var usernameElement = document.createElement('span');
        var chatdetails = " < id:"+ message.contentId +", timestamp:" + message.timestamp + " >"
        var usernameText = document.createTextNode(message.sender+chatdetails);
        usernameElement.appendChild(usernameText);
        messageElement.appendChild(usernameElement);
    }

    if(message.type != "HISTORY" ){
        var textElement = document.createElement('p');
        var messageText = document.createTextNode(message.content);
        textElement.appendChild(messageText);

        messageElement.appendChild(textElement);

        messageArea.appendChild(messageElement);
        messageArea.scrollTop = messageArea.scrollHeight;
    }  
}


function getAvatarColor(messageSender) {
    var hash = 0;
    for (var i = 0; i < messageSender.length; i++) {
        hash = 31 * hash + messageSender.charCodeAt(i);
    }
    var index = Math.abs(hash % colors.length);
    return colors[index];
}



usernameForm.addEventListener('submit', connect, true)
messageForm.addEventListener('submit', sendMessage, true)