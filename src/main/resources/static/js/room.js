function onEntryOrLeave(event){
    let payload = JSON.parse(event.body)
    if (payload.action == "connect"){
        userInOut("connect")
        console.log(payload.username + " is entering ")
    } else {
        userInOut("disconnect")
        console.log(payload.username+ " is leaving")
    }
}

function sendEntryOrLeave(event){
    var actionJson = {
        username: username,
        action: event
    }
    stompClient.send("/room/room/"+id, {}, JSON.stringify(actionJson))
}

function userInOut(event){
    var payload = JSON.parse(event.body)
    console.log(payload)
    console.log(payload.action == "connect")
    console.log(payload.username)
    chatMessages = document.getElementById("chat-messages");
    if (payload.action == "connect"){
    chatMessages.innerHTML +=
        `<p class="subtitle is-size-6 has-text-centered">`+ payload.username+" has joined the room"+
        `</p><br/>`
    } else {
        chatMessages.innerHTML +=
        `<p class="subtitle is-size-6 has-text-centered">`+ payload.username+" has left the room"+
        `</p><br/>`
    }
}