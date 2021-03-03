
function onDocumentReceived(payload) {
    var documentContent = document.getElementById("shared-doc");
    // Do some postprocessing of the content
    var currentPos = documentContent.selectionStart;
    var incomingJson = JSON.parse(payload.body)
    savedText = incomingJson.content
    incomingTime = incomingJson.time
    incomingChange = documentContent.value
    if (Math.abs(incomingTime - lastChange) < TIME_DIFF){
        // add in both changes
        // currently does not support deletion. So if you want to delete, only 1 person delete at a time
        var unifiedString = ""
        Diff.diffChars(incomingChange, savedText).forEach(part=>{
            unifiedString = unifiedString + part.value
        })
        documentContent.value = unifiedString
    } else if (incomingTime - lastChange > TIME_DIFF) {
        documentContent.value = savedText
    } else if (incomingTime - lastChange < -TIME_DIFF){
        documentContent.value = incomingChange
    }

    // Fix the cursor position in the text box
    documentContent.setSelectionRange(currentPos, currentPos);

}

function sendDocument(event) {
    var documentContent = document.getElementById("shared-doc").value;

    if(stompClient) {
        var chatDocument = {
            sender: username,
            content: documentContent,
            roomId: id,
            roomName: name,
            time: Date.now()
        };

        stompClient.send("/room/document/"+id, {}, JSON.stringify(chatDocument));
    }
}
var delay = (function(){
    var timer = 0;
    return function(callback, ms){
        clearTimeout(timer);
        timer = setTimeout(callback,ms);
    };
})();
document.getElementById("shared-doc").addEventListener("keydown",e => {
        delay(function(){
            sendDocument(e.target.innerHTML)
        },1000)
        lastChange = Date.now();
});

function textAreaAdjust(element) {
  element.style.height = "1px";
  element.style.height = (25+element.scrollHeight)+"px";
}