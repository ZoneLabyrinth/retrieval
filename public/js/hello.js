(function () {
    $(document).ready(function() {
        console.log("JH")
        $.ajax({
            // url: "http://localhost:8080/greeting-javaconfig?name=you"
            url: "http://localhost:8080/searching"
        }).then(function(data, status, jqxhr) {
            $('.greeting-id').append(data.url);
            $('.greeting-content').append(data.content);
            var html = data.content;
            // $("").innerHTML = html;

            console.log("'...")
            console.log(jqxhr);
        }).then(function (err) {
            console.log(">>")
        });
    });
})();