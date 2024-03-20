//修改这个变量为实际控制器的地址，如../showGoods.do
var reqpath = "orderConfirm.html"
var SeInfoList = [];

function initMenu() {
    // 获取本地存储中的数据并解析为 JavaScript 对象
    var SeInfoList = JSON.parse(localStorage.getItem("SeInfoList"));

    // 获取要填充数据的目标元素
    var addressOption = document.getElementById("AddressOption");
    var orderBody = document.getElementById("OrderBody");
    var selectCount = document.getElementById("selectCount");
    var selectTotal = document.getElementById("selectTotal");

    // 清空之前的选项
    while (addressOption.firstChild) {
        addressOption.removeChild(addressOption.firstChild);
    }

    // 填充收货地址信息
    SeInfoList.addressList.forEach(function (address) {
        // 创建 option 元素
        var option = document.createElement("option");
        // 设置 option 的文本内容
        var addressText = `${address.name} / ${address.tag} / ${address.province_name} ${address.city_name} ${address.area_name} ${address.address} / ${address.phone}`;
        option.text = addressText;
        // 设置 option 的值（这里可以设置为 address 的某个唯一标识符，比如 address.id）
        option.value = addressText;
        // 将 option 添加到 select 元素中
        addressOption.add(option);
    });

    // 填充订单商品信息
    orderBody.innerHTML = "";
    SeInfoList.orderItemVOList.forEach(function (item) {
        var row = document.createElement("tr");
        row.innerHTML = `
        <td><img src="${item.image}1.jpg" class="img-responsive" /></td>
        <td>${item.title}</td>
        <td>¥<span>${item.price}</span></td>
        <td>${item.num}</td>
        <td>¥<span>${item.total}</span></td>
    `;
        orderBody.appendChild(row);
    });

    // 更新已选商品数量和总价
    selectCount.textContent = SeInfoList.orderItemVOList.length;
    var total = SeInfoList.orderItemVOList.reduce((acc, item) => acc + item.total, 0);
    selectTotal.textContent = total;
}

$(function () {
    initMenu();
})

$(function () {
    $(".link-pay").click(function () {
        // 获取选择的地址 option 对应的索引
        var PayInfo = [];
        var SeInfoList = JSON.parse(localStorage.getItem("SeInfoList"));
        var selectedIndex = document.getElementById("AddressOption").selectedIndex;

        // 获取选定的地址对象的id
        var address_id = SeInfoList.addressList[selectedIndex].id;
        let authorization = localStorage.getItem("token");
        const url = '/api/cart/onlinePay/' + address_id;

        // 输出 URL 和 productIds，供参考
        console.log("URL: ", url);

        if (authorization) {
            axios.get(url, {
                headers: {
                    Authorization: authorization
                }
            }).then(resp => {
                PayInfo = resp.data.data;
                localStorage.setItem("PayInfo", JSON.stringify(PayInfo));
                location.href = "payment.html";
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
