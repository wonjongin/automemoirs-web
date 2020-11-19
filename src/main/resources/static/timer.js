var totalTime = 300
var min = ""
var sec = ""

var x = setInterval(function() {
    //parseInt() : 정수를 반환
    min = parseInt(totalTime/60); //몫을 계산
    sec = totalTime%60; //나머지를 계산

    document.getElementById("timer").innerHTML = min + " 분 " + sec + " 초" +" 남았어요!";
    totalTime--;

    //타임아웃 시
    if (totalTime < 0) {
        clearInterval(x); //setInterval() 실행을 끝냄
        document.getElementById("timer").innerHTML = "시간이 초과됐어요. 새로고침을 하여 양식을 다시 보내주세요.";
        document.getElementById("downloadBtn").setAttribute("href", "/timeout.html")
        
    }
}, 1000);