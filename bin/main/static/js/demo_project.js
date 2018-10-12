/**
 * 
 */

function searching(event){
    var inputContent = document.getElementById("search-input-box");
        if (event.keyCode === 13 || event == "ckmclick") {  //checks whether the pressed key is "Enter"
            console.log(inputContent.value);
            let newRawPost = inputContent.value;
            let newPost = normalizeSearchedInfor(newRawPost);
            $("#search-content-box").val("");
            $("#showResults").html('');
            let content = {"content" : newPost};
            $.ajax({
                type : "POST",
                contentType: "application/json",
                url : serverContextPath + "searching", 
                data : JSON.stringify(content),
                success : function (data){
                    let result = data;
                    console.log("response: ", result);
                    if(result != null && result != "" && result !== "FAIL"){
                        $("#search-notification").html("Tìm kiếm thành công");
                        setTimeout(function(){
                            $("#search-notification").html("");
                        }, 3000);
                        let length = result.length;
                        for(let i = 0; i < length; i++){
                            let rawName = result[i].name;
                            let name = normalizeName(rawName);
                            
                            let homeTown = result[i].homeTown;
                            let code = result[i].code;
                            if(homeTown == null){
                                homeTown = "Không có thông tin";
                            }
                            returnHintProfileOnHtml(code, name, homeTown);
                        }
                        
                    } else{
                        $("#search-notification").html("Không có kết quả");
                        setTimeout(function(){
                            $("#search-notification").html("");
                        }, 3000);
                    }
                    
                    $(document).ready(function(){
                        $("tr:not(:first)").hover(function(){
                            $(this).css("color", "blue");
                            $(this).css("cursor", "pointer");
                            }, function(){
                            $(this).css("color", "black");
                        });
                        
                        $("tr:not(:first)").click(function(){
                            $(this).toggleClass('clicked');
                        });
                        
                    });
                    
                    
                    
                },
                error : function(e) {
                    console.log('There is error while search content ', e);
                }
            });
        }
}

function normalizeName(rawName){
    rawName = rawName.trim();
    rawName = rawName.replace(/ +(?= )/g,'');
    let normalizeName = rawName;
    return normalizeName;
}

function normalizeSearchedInfor(rawContent){
    let normalizeContent = "";
    rawContent = rawContent.trim();
    rawContent = rawContent.replace(/ +(?= )/g,'');
    normalizeContent = rawContent.toLowerCase();
    return normalizeContent;
}

function returnHintProfileOnHtml(code, name, homeTown){
    $('#showResults').append('<tr value="' + code + '" onclick = "viewDocument('+ "'" + code + "'"+')" >' + '<td>'+ name +'</td>' + '<td>'+ code +'</td>' + '<td>'+ homeTown +'</td>' + '</tr>')
}

function viewDocument(code){
   console.log(serverContextPath);
   let url = serverContextPath + "downloadFile?document=" + code;
   window.open(url);
}

function sync(){
    $.ajax({
        type : "GET",
        contentType: "application/json",
        url : serverContextPath + "refreshDataBase", 
        success: function(data){
            if(data == "ok"){
                console.log('system have synchronized database with document resource');
                $("#syn-notification").text("Synchronizing completed");
                setTimeout(function(){
                    $("#syn-notification").text("");
                }, 3000);
            } else{
                console.log('there is error while synchronizing database with document resource');
                $("#syn-notification").text("Synchronizing error!");
                setTimeout(function(){ 
                    $("#syn-notification").text("");
                }, 3000);
            }
        },
        error: function(e){
            console.log('there is error while synchronizing database with document resource');
            $("#syn-notification").text("Synchronizing error!");
            setTimeout(function(){ 
                $("#syn-notification").text("");
            }, 3000);
        }
    });
}

function syncProcess(){
    $("#syn-notification").text("Synchronizing may take a lots time, please wait until completing");
    sync();
}

$(document).ready(function(){
    $("#searchimg").hover(function(){
        $(this).css("cursor", "pointer");
    });
    
});

