/**
 * Created by sks on 2017/9/19.
 */
(function () {
    function Main() {
        this.getElements();
        this.search();

    }


    Main.prototype.getElements = function () {
        this.resultContainer = $("#result");
        this.input = $("#search");

    };

    Main.prototype.search = function () {
        var self = this;
        $("button").click(function () {
            console.log(self.input.val());
            if (self.input.val()===""){
                alert("请输入搜索内容")
            }else {
                console.log(">>");
                self.getData(self.input.val());
            }
        });
        $(document).keydown(function(event){
            if(event.keyCode == 13){ //绑定回车
                event.preventDefault();
                $("button").click();
            }
        });

    };


    Main.prototype.getData = function (content) {
        var self = this;
        var inner =""

        $.ajax({
            type:"POST",
            dataType: 'json',
            data:content,
            contentType:'application/json;charset=UTF-8',
            url: "http://localhost:8080/searching"
        }).then(function(data, status, jqxhr) {
            console.log(data);
            for(var i=0;i<data.length;i++){
                var html = data[i];
                var addTime = new Date(html.dateTime);
                var date = addTime.getFullYear()+"-"+(addTime.getMonth()+1)+"-"+addTime.getDate();



                console.log(date);

                 inner += "<div class='title'><a href='"+html.url+"' target='_blank'><span class='col-md-8'>"+ html.title+"</span>" +
                    "<span class='col-md-4'>"+date+"</span></a></div>"
            }
            self.resultContainer.html(inner)
            // $("").innerHTML = html;

        }).then(function (err) {
            console.log(">>")
        });
    }



    new Main();


})();