/**
 * 
 */
var GLOBAL_ID = 4;

const SUCCESS = "OK";
$(function(){
    
    $.ajax({
        type: "GET",
        contentType: "applicatin/json",
        url: "/getInitialPost",
        success: function(newPost){
            let contentNew = newPost.newPost;
            let length = contentNew.length;
            for(let i = 0; i < length; i++){
                let id = length - i;
                $("#newest-post").append('<br> <div id="'+ id + '"> </div> <br>');
                $('#'+ id ).text(contentNew[i]);
            }
        },
        error: function(e) {
            console.log("error loading new post: ", e);
        }
     });
    
});

function checkPost(event){
    var inputContent = document.getElementById("input-box");
        if (event.keyCode === 13) {  //checks whether the pressed key is "Enter"
            console.log(inputContent.value);
            let newPost = inputContent.value;
            let content = {"content" : newPost};
            $("#input-box").val("");
            $.ajax({
                type : "POST",
                contentType: "application/json",
                url : "/newPost", 
                data : JSON.stringify(content),
                success : function (data){
                    console.log("post status: ", data);
//                    let jsn = JSON.parse(data);
                    let result = data.response;
                    console.log("response: ", result);
                    if(result === SUCCESS){
                        $("#post-notification").html("successful");
                        setTimeout(function(){
                            $("#post-notification").html("");
                        }, 3000);
                    }
                    
                    $.ajax({
                       type: "GET",
                       contentType: "application/json",
                       url: "/getNewPost",
                       success: function(newPost){
                           let contentNew = newPost.newPost;
                           $("#newest-post").prepend('<br> <div id="'+ GLOBAL_ID + '"> </div> <br>');
                           $('#'+ GLOBAL_ID).text(contentNew);
                       },
                       error: function(e) {
                           console.log("error loading new post: ", e);
                       }
                    });
                },
                error : function(e) {
                    console.log('There is error while uploading content ', e);
                }
            });
            
        }
}

function checkSearch(event){
    var inputContent = document.getElementById("search-input-box");
        if (event.keyCode === 13) {  //checks whether the pressed key is "Enter"
            console.log(inputContent.value);
            let newPost = inputContent.value;
            $("#search-input-box").val("");
            let content = {"content" : newPost};
            $.ajax({
                type : "POST",
                contentType: "application/json",
                url : "/searchContent", 
                data : JSON.stringify(content),
                success : function (data){
                    console.log("post status: ", data);
                    let result = data.response;
                    console.log("response: ", result);
                    if(result != null && result != "" && result !== "FAIL"){
                        $("#search-notification").html("successful");
                        setTimeout(function(){
                            $("#search-notification").html("");
                        }, 3000);
                        
                        $("#search-result").text(result);
                        
                    } else{
                        $("#search-notification").html("Search Fail");
                        setTimeout(function(){
                            $("#search-notification").html("");
                        }, 3000);
                    }
                    
                },
                error : function(e) {
                    console.log('There is error while search content ', e);
                }
            });
        }
}
