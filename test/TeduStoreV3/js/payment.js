var reqpath = "payment.html"
function initMenu() {
    // 获取本地存储中的数据并解析为 JavaScript 对象
    var PayInfo = JSON.parse(localStorage.getItem("PayInfo"));
    console.log(PayInfo);
    // 检查PayInfo是否存在且是否包含所需信息
    if (!PayInfo || typeof PayInfo !== 'object' || !PayInfo.id || !PayInfo.total_price) {
        console.error("Invalid PayInfo data.");
        return;
    }
    // 一个用于显示订单信息的 div
    var orderBaseInfoDiv = document.getElementById("OrderBaseInfo");
    // 一个用于显示支付金额的 span
    var goToPaySpan = document.getElementById("GoToPay");
    // 更新订单基本信息的 div
    orderBaseInfoDiv.innerHTML = `订单号：${PayInfo.id}，支付金额¥${PayInfo.total_price}，收款方达内学子商城`;
    // 更新支付按钮的 span
    goToPaySpan.innerHTML = `¥${PayInfo.total_price.toFixed(2)} <input type="button" value="确认付款" class="btn btn-primary btn-lg link-success"/>`;
}
$(function () {
    initMenu();
})
$(function () {
    $(".link-success").click(function () {
        let authorization = localStorage.getItem("token");
        const url = '/api/cart/PayOk/';

        // 输出 URL 和 productIds，供参考
        console.log("URL: ", url);

        if (authorization) {
            axios.get(url, {
                headers: {
                    Authorization: authorization
                }
            }).then(resp => {
                PayOkInfo = resp.data.data;
                localStorage.setItem("PayOkInfo", JSON.stringify(PayOkInfo));
                location.href = "paySuccess.html";
            }).catch(err => {
                if (resp.data.code == 40400) {
                    window.confirm("用户不存在");
                } else if (resp.data().code == 40000) {
                    window.confirm("你支付了该订单");
                } else if (resp.data().code == 50000) {
                    window.confirm("更新失败");
                }
            });
        } else {
            window.confirm("还未登录");
        }
    })
})
$(function (){
    let authorization = localStorage.getItem("token");
    let username=localStorage.getItem("username")
    if (authorization){
        $(".fa_fa-user").html(username);
    }
})