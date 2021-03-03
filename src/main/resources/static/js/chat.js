function addChat(event, receive){
    chatMessages = document.getElementById("chat-messages");
    console.log(event)
    if(receive){
        var message = JSON.parse(event.body)
        chatMessages.innerHTML += `<div class="card">
      <div class="card-content">
        <p class="content is-medium">`
          +message.content+
        `</p>
        <p class="subtitle is-size-6 has-text-right">`+
          "- "+message.sender+
        `</p>
      </div>
      </div><br/>`
    }
    else {
        event.preventDefault();
        message = document.getElementById("chat-message").value;
        sendMessage(message);
        document.getElementById("chat-message").value = ""
   }
   chatMessages.scrollTop = chatMessages.scrollHeight;
}

function onMessageReceived(payload){
    addChat(payload, true)
}

function sendMessage(message){
    var chatContent = message;
    if(stompClient && /\S/.test(chatContent) ) {
        var chatMessage = {
            sender: username,
            content: chatContent
        };
        stompClient.send("/room/chat/"+id, {}, JSON.stringify(chatMessage));
    }
}
