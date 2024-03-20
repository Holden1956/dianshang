//修改这个变量为实际控制器的地址，如../showGoods.do
var reqpath = "cart.html"
var typelist = [];
var SeInfolist = [];

async function loadDate() {
    var tbody = document.getElementById("BigCart");
    for (var i = 0; i < typelist.length; i++) {
        var item = typelist[i];

        var newRow = document.createElement("tr");
        newRow.innerHTML = `
        <td><input type="checkbox" class="ckitem" onclick="calcTotal()"/></td>
        <td><img src="${item.product_image}"class="img-responsive"/></td>
        <td>${item.product_name}</td>
        <td>¥<span id="goodsPrice${i + 1}">${item.product_price}</span></td>
        <td>
            <input type="button" value="-" class="num-btn" onclick="reduceNum(${i+1},${item.product_id})" />
            <input id="goodsCount${i + 1}" type="text" value="${item.product_num}" size="2" readonly="readonly" class="num-text">
            <input type="button" value="+" class="num-btn" onclick="addNum(${i+1},${item.product_id})" />
        </td>
        <td>¥<span id="goodsCast${i + 1}">${item.product_total}</span></td>
        <td><input type="button" onclick="delCartItem(this,${i})" class="cart-del btn btn-default btn-xs" value="删除" /></td>
    `;
        tbody.appendChild(newRow);
    }
}

function initMenu() {
    let authorization = localStorage.getItem("token");

    if (authorization) {
        axios.get('/api/cart/list', {
            headers: {
                Authorization: authorization
            }
        })
            .then(resp => {
                console.log(resp);
                typelist = resp.data.data;
                console.log("typelistLength" + typelist.length);
                loadDate();
            })
            .catch(err => {
                if (resp.data.code == 40400) {
                    window.confirm("购物车里什么都没有");
                }
                console.error(err);
            });
    } else {
        window.confirm("还未登录");
    }
}

$(function () {
    initMenu();
})

/*按加号数量增*/
function addNum(rid, productId) {
    let mm=$("#goodsCount" + rid).val(parseInt($("#goodsCount" + rid).val()) + 1);
    console.log("mm",mm);
    let authorization = localStorage.getItem("token");
    if (authorization) {
        axios.put('/api/cart/update', {
            product_id: productId,
            product_status: true
        }, {
            headers: {
                Authorization: authorization
            }
        }).then(resp=> {
                console.log(resp);
                typelist = resp.data.data;
                $("#goodsCast" + rid).html(typelist.product_total);
                console.log(typelist.product_total);
            })
            .catch(err => {
                console.log(err);
            })
    } else {
        window.confirm("用户未登录")
    }
}

/*按减号数量减*/
function reduceNum(rid, productId) {
    $("#goodsCount" + rid).val(parseInt($("#goodsCount" + rid).val()) - 1);
    console.log("#goodsCount" + rid);

    let authorization = localStorage.getItem("token");

    if (authorization) {
        axios.put('/api/cart/update', {
            product_id: productId,
            product_status: false
        }, {
            headers: {
                Authorization: authorization
            }
        })
            .then(resp => {
                console.log(resp);
                typelist = resp.data.data;
                $("#goodsCast" + rid).html(typelist.product_total);
            })
            .catch(err => {
                console.log(err);
            })
    } else {
        window.confirm("用户未登录")
    }
}


/*全选全不选*/
function checkall(ckbtn) {
    $(".ckitem").prop("checked", $(ckbtn).prop("checked"));
    calcTotal();
}

//删除按钮
function delCartItem(btn, rowIndex) {
    //console.log("Index"+rowIndex);
    $(btn).parents("tr").remove();
    let authorization = localStorage.getItem("token");

    const product_id = typelist[rowIndex].product_id; // 替换成你要传递的整数
    const url = `/api/cart/delete/${product_id}`;
    console.log("我将要删除id=" + product_id);
    console.log("token=" + authorization);
    if (authorization) {
        axios.delete(url, {
            headers: {
                Authorization: authorization
            }
        }).then(resp => {
            calcTotal();
        }).catch(err => {
            if (resp.data.code == 40400) {
                window.confirm("没有找到该商品");
            } else if (resp.data().code == 40300) {
                window.confirm("你没有权限删除该商品");
            } else if (resp.data().code == 50000) {
                window.confirm("删除失败");
            }
        });
    } else {
        window.confirm("还未登录");
    }
}

//批量删除按钮
function selDelCart() {
    //遍历所有按钮
    let productIds = []; // 初始化一个空数组来存储被选中产品的 product_id
    for (var i = $(".ckitem").length - 1; i >= 0; i--) {
        //如果选中
        if ($(".ckitem")[i].checked) {
            //删除
            $($(".ckitem")[i]).parents("tr").remove();
            productIds.push(typelist[i].product_id);
        }
    }
    let authorization = localStorage.getItem("token");
    const url = '/api/cart/delete1/' + productIds.join(',');
    // 输出 URL 和 productIds，供参考
    console.log("URL: ", url);
    console.log("Product IDs: ", productIds);
    if (authorization) {
        axios.delete(url, {
            headers: {
                Authorization: authorization
            }
        }).then(resp => {
            calcTotal();
        }).catch(err => {
            if (resp.data.code == 40400) {
                window.confirm("没有找到该商品");
            } else if (resp.data().code == 40300) {
                window.confirm("你没有权限删除该商品");
            } else if (resp.data().code == 50000) {
                window.confirm("删除失败");
            }
        });
    } else {
        window.confirm("还未登录");
    }
}

$(function () {
    // 绑定复选框状态变化事件
    $(".ckitem").click(function () {
        calcTotal();
    });
    calcTotal();
});

// //计算单行小计价格的方法
// function calcRow(rid) {
//     //取单价
//     var vprice = parseFloat($("#goodsPrice" + rid).html());
//     //取数量
//     var vnum = parseFloat($("#goodsCount" + rid).val());
//     //小计金额
//     var vtotal = vprice * vnum;
//     //赋值
//     $("#goodsCast" + rid).html(vtotal);
// }

//计算总价格的方法
// function calcTotal() {
// 	//选中商品的数量
// 	var vselectCount = 0;
// 	//选中商品的总价
// 	var vselectTotal = 0;
//
// 	//循环遍历所有tr
// 	for (var i = 0; i < $(".cart-body tr").length; i++) {
// 		//计算每个商品的价格小计开始
// 		//取出1行
// 		var $tr = $($(".cart-body tr")[i]);
// 		//取单价
// 		var vprice = parseFloat($tr.children(":eq(3)").children("span").html());
// 		//取数量
// 		var vnum = parseFloat($tr.children(":eq(4)").children(".num-text").val());
// 		//小计金额
// 		var vtotal = vprice * vnum;
// 		//赋值
// 		$tr.children(":eq(5)").children("span").html("¥" + vtotal);
// 		//计算每个商品的价格小计结束
//
// 		//检查是否选中
// 		if ($tr.children(":eq(0)").children(".ckitem").prop("checked")) {
// 			//计数
// 			vselectCount++;
// 			//计总价
// 			vselectTotal += vtotal;
// 		}
// 		//将选中的数量和价格赋值
// 		$("#selectTotal").html(vselectTotal);
// 		$("#selectCount").html(vselectCount);
// 	}
// }
function calcTotal() {
    // 重置计数和总价
    var vselectCount = 0;
    var vselectTotal = 0;
    // 循环遍历所有复选框
    $('.ckitem').each(function (index, checkbox) {
        // 如果复选框被选中
        if ($(checkbox).prop('checked')) {
            // 获取对应商品的价格小计
            var vtotal = parseFloat($(checkbox).closest('tr').find('td:eq(5) span').text().replace('¥', ''));
            // 累加计数和总价
            vselectCount++;
            vselectTotal += vtotal;
        }
    });
    console.log("计算总价");
    // 更新页面上显示的总数和总价
    $("#selectTotal").html(vselectTotal.toFixed(2));
    $("#selectCount").html(vselectCount);
}
$(function (){
    let authorization = localStorage.getItem("token");
    let username=localStorage.getItem("username")
    if (authorization){
        $(".fa_fa-user").html(username);
    }
})