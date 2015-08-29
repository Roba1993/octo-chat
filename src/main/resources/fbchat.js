function update() {
    var json = {messages: []};

    $("._1ht1").each(function(i) {
        json.messages[i] = {};

        // set the unread flag
        json.messages[i].unread = $(this).hasClass("_1ht3");

        // set the text message
        json.messages[i].text = $(this).find("._1htf").text();

        // set the user id
        json.messages[i].id = $(this).attr("data-reactid").split(":", 2)[1];

        // set the username
        json.messages[i].name = $(this).find("._1ht6").text();

        // set the time
        json.messages[i].time = $(this).find("._1ht7").text();

        // set online
        json.messages[i].online = $(this).find("._2pon").hasClass("_2poo");
    });

    return json;
}

function updateJava() {
    var json = {messages: []};

    $("._1ht1").each(function(i) {
        json.messages[i] = {};

        // set the unread flag
        json.messages[i].unread = $(this).hasClass("_1ht3");

        // set the text message
        json.messages[i].text = $(this).find("._1htf").text();

        // set the user id
        json.messages[i].id = $(this).attr("data-reactid").split(":", 2)[1];

        // set the username
        json.messages[i].name = $(this).find("._1ht6").text();

        // set the time
        json.messages[i].time = $(this).find("._1ht7").text();

        // set online
        json.messages[i].online = $(this).find("._2pon").hasClass("_2poo");
    });

    // send the data to java
    Java.updateStatus(json);
}


function updateNew() {
    $("._1ht1").each(function(i) {
        // set the unread flag
        var unread = $(this).hasClass("_1ht3");

        // set the text message
        var text = $(this).find("._1htf").text();

        // set the user id
        var id = $(this).attr("data-reactid").split(":", 2)[1];

        // set the username
        var name = $(this).find("._1ht6").text();

        // set the time
        var time = $(this).find("._1ht7").text();

        // set online
        var online = $(this).find("._2pon").hasClass("_2poo");

        javaNew(id, name, text, time, unread, online);
    });
}

// bind the a change listener
$('body').on('DOMNodeInserted', '._1ht1', function () {
    updateNew();
});