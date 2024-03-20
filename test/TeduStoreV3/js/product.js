// $(function() {
// 	/*商品小图片加鼠标移入加边框、移出移除边框*/
// 	$(".img-small").hover(function() {
// 			$(this).css("border", "1px solid #4288c3");
// 		},
// 		function() {
// 			$(this).css("border", "");
// 		})
// 	//点击时变化大图片
// 	$(".img-small").click(function() {
// 			//获得点击的小图片数据
// 			var n = $(this).attr("data");
// 			//所有大图隐藏
// 			$(".img-big").hide();
// 			//显示点击的小图对应的大图
// 			$(".img-big[data='" + n + "']").show();
// 		})
// 		//购物数量加1
// 	$("#numUp").click(function() {
// 		var n = parseInt($("#num").val());
// 		$("#num").val(n + 1);
// 	})
// 	//购物数量-1
// 	$("#numDown").click(function() {
// 		var n = parseInt($("#num").val());
// 		if (n == 1) {
// 			return;
// 		}
// 		$("#num").val(n - 1);
// 	})
// 	//点购物车跳页面
// 	$(".go-cart").click(function() {
// 		location.href = "cart.html";
// 	});
// 	$(".img-big:eq(0)").show();
// })
// var id=10000015;
// var num=1;
// $(function() {
// 	/*商品小图片加鼠标移入加边框、移出移除边框*/
// 	$(".img-small").hover(function() {
// 			$(this).css("border", "1px solid #4288c3");
// 		},
// 		function() {
// 			$(this).css("border", "");
// 		})
// 	//点击时变化大图片
// 	$('.img-small').click(function() {
// 		//获得点击的小图片数据
// 		var n = $(this).attr("data");
// 		//所有大图隐藏
// 		$(".img-big").hide();
// 		//显示点击的小图对应的大图
// 		$(".img-big[data=" + n + "]").show();
// 	})
// 	$(".img-big:eq(0)").show();
// })
// $(function (){
// 	//点购物车跳页面
// 	$(".go-cart").click(function() {
// 		let authorization=localStorage.getItem("token");
// 		if(authorization){
// 			axios.post('/api/cart/addIntoCart',{
// 				id:id,
// 				num:num
// 			}, {
// 				headers:{
// 					Authorization:authorization
// 				}
//
// 			}).then(resp=>{
// 				console.log(resp);
//
// 				location.href="cart.html";
// 			}).catch(err=>{
// 				console.log(err)
// 			})
// 		}
// 	});
// })
//
// $(function (){
// 	let authorization=localStorage.getItem("token");
//
// 	if(authorization){
// 		axios.get('/api/product/'+id,{
// 			headers:{
// 				Authorization:authorization
// 			}
// 		}).then(resp=>{
// 			console.log(resp)
// 			reLoad(id);
// 		}).catch(err=>{
// 			console.log(err)
// 		})
// 	}
// })
var id = 10000015;
var num = 1;
$(function () {
    /*商品小图片加鼠标移入加边框、移出移除边框*/
    $(".img-small").hover(function () {
            $(this).css("border", "1px solid #4288c3");
        },
        function () {
            $(this).css("border", "");
        })
    //点击时变化大图片
    $('.img-small').click(function () {
        //获得点击的小图片数据
        var n = $(this).attr("data");
        //所有大图隐藏
        $(".img-big").hide();
        //显示点击的小图对应的大图
        $(".img-big[data=" + n + "]").show();
    })
    $(".img-big:eq(0)").show();
})
$(function () {
    //点购物车跳页面
    $(".go-cart").click(function () {
        let authorization = localStorage.getItem("token");
        if (authorization) {
            axios.post('/api/cart/addIntoCart', {
                id: id,
                num: num
            }, {
                headers: {
                    Authorization: authorization
                }

            }).then(resp => {
                console.log(resp);

                location.href = "cart.html";
            }).catch(err => {
                console.log(err)
            })
        }
    });
})

$(function () {
    let authorization = localStorage.getItem("token");

    if (authorization) {
        axios.get('/api/product/' + id, {
            headers: {
                Authorization: authorization
            }
        }).then(resp => {
            console.log(resp)
            reLoad(id);
        }).catch(err => {
            console.log(err)
        })
    }
})
$(function (){
    let authorization = localStorage.getItem("token");
    let username=localStorage.getItem("username")
    if (authorization){
        $(".fa_fa-user").html(username);
    }
})

function reLoad(id) {
    var div = document.getElementById("divid")
    var newRow = document.createElement("tr");
    newRow.innerHTML = `
       <td>
          <label for="num1">数量：</label>
          <input id="addNum1" type="button" value="-" class="num-btn"  onclick="reduceNum()"/>
          <input id="num" type="text" size="2" readonly="readonly" class="num-text" value="${num}">
          <input id="reduceNum1" type="button" value="+" class="num-btn" onclick="addNum()"/>
       </td>
    `;
    div.append(newRow);
}

//购物数量加1
function addNum() {
    $("#num").val(parseInt($("#num").val()) + 1);
    num++;
}

//购物数量-1
function reduceNum() {
    $("#num").val(parseInt($("#num").val()) - 1);
    if (num <= 0) {
        window.confirm("商品数量不能为0");
        $("#num").val(1);
        num = 1;
    } else {
        num--;
    }
    $(function () {
        let authorization = localStorage.getItem("token");
        let username = localStorage.getItem("username")
        if (authorization) {
            $(".fa_fa-user").html(username);
        }
    })
}