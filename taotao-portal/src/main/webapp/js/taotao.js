var TT = TAOTAO = {
    checkLogin: function () {
        //var cookies = document.cookie;
        //_ticket = _ticket.substring(9);
        var _ticket = getCookie("TT-TOKEN");
        //console.log(_ticket);
        //console.log("22222222222222222222");
        if (!_ticket) {
            return;
        }
        $.ajax({
            url: "http://sso.taotao.com/user/token/" + _ticket,
            dataType: "jsonp",
            type: "GET",
            success: function (data) {
                if (data.status == 200) {
                    var username = data.data.username;
                    var html = username + "，欢迎来到淘淘！<a href=\"http://sso.taotao.com/user/logout/"+_ticket+".html\" class=\"link-logout\">[退出]</a>";
                    $("#loginbar").html(html);
                }
            }
        });
    }
}

///获取cookie值
function getCookie(NameOfCookie) {
    // 首先我们检查下cookie是否存在.
    // 如果不存在则document.cookie的长度为0
    console.log(document.cookie);
    if (document.cookie.length > 0) {
        // 接着我们检查下cookie的名字是否存在于document.cookie
        // 因为不止一个cookie值存储,所以即使document.cookie的长度不为0也不能保证我们想要的名字的cookie存在
        //所以我们需要这一步看看是否有我们想要的cookie
        //如果begin的变量值得到的是-1那么说明不存在
        begin = document.cookie.indexOf(NameOfCookie + "=");
        if (begin != -1) {
            // 说明存在我们的cookie.
            begin += NameOfCookie.length + 1;//cookie值的初始位置
            end = document.cookie.indexOf(";", begin);//结束位置
            if (end == -1) end = document.cookie.length;//没有;则end为字符串结束位置
            return unescape(document.cookie.substring(begin, end));
        }
    }
    // cookie不存在返回null
    return null;
}

//查看cookies状态
// var dt = new Date();
// dt.setSeconds(dt.getSeconds() + 60);
// document.cookie = "cookietest=1; expires=" + dt.toGMTString();
// var cookiesEnabled = document.cookie.indexOf("cookietest=") != -1;
// if(!cookiesEnabled) {
//     //没有启用cookie
//     alert("没有启用cookie ");
// } else{
//     //已经启用cookie
//     alert("已经启用cookie ");
// }

$(function () {
    // 查看是否已经登录，如果已经登录查询登录信息
    TT.checkLogin();
});