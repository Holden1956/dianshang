var reqpath = "paySuccess.html"
function initMenu() {
    // 获取本地存储中的数据并解析为 JavaScript 对象
    var PayInfo = JSON.parse(localStorage.getItem("PayInfo"));

    var payOkDiv = document.getElementById("PayOkDiv");
    // 更新订单信息的 div
    payOkDiv.innerHTML = `订单号：${PayInfo.id}，支付金额¥${PayInfo.total_price}，收款方达内学子商城`;
}
$(function () {
    initMenu();
})
$(function (){
    let authorization = localStorage.getItem("token");
    let username=localStorage.getItem("username")
    if (authorization){
        $(".fa_fa-user").html(username);
    }
})