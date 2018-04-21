// console.log("hi in deals");
// console.log(jQuery("#rishav").text());
//  jQuery("#rishav").on('click',function(){
//    console.log("Hii rishav");
//   alert("its fine");
//  });
//
$(document).ready(function()
 {
	$('#category').html(localStorage.category_name);
  // alert("hi in deals");
   var category=localStorage.category_name;
   alert(category);
   $.ajax({
     type: "get",
     url:  "http://localhost:5224/ebaytester/webapi/products/category/"+category,
     success: function(response){
       //alert("Ajax call se response aa gya");
       $("#yo").load("javascript/product.html");
       console.log("deals!!!!"+response.length);
       var x=10/4.0;
       console.log(x);
       var remaining_products=response.length;
       for(var i=0;i<Math.ceil(response.length/4);i++){
         $("#c1").append("\
        <div class='container-fluid'> \
        <div class='row'>");
          for(var j=0;j<4 && remaining_products>0;j++){
            remaining_products--;
            if(response[4*i+j].productDiscount!=0){
            $("#c1").append("<div class='col-sm-3' id='div1'style='height:500px;'>\
         						<div class='thumbnail'>\
         						<img src='"+response[4*i+j].productImageUrl +"' alt='img1'style='height:200px;width:100%'>\
         						<div>\
         							<h4 style='text-align:center'>"+response[4*i+j].productName+"</h4>\
         							<h4 style='text-align:center'>Price:<strike>"+response[4*i+j].productPrice+"</strike></h4>\
         							<h4 style='text-align:center'>Discounted Price:"+(response[4*i+j].productPrice-(response[4*i+j].productDiscount/100)*response[4*i+j].productPrice)+"</h4>\
         							<br><button type='button' class='btn btn-primary center-block' onclick='buybutton("+response[4*i+j].productId+")'>Buy Now</button>\
         							<br><button type='button' class='btn btn-primary center-block'onclick='addToCartButton("+response[4*i+j].productId+")'>Add To Cart</button>\
         						</div>\
         						</div>\
         					</div>");
            }else{
                $("#c1").append("<div class='col-sm-3' id='div1'style='height:500px;'>\
 						<div class='thumbnail'>\
 						<img src='"+response[4*i+j].productImageUrl +"' alt='img1' style='height:200px;width:100%'>\
 						<div>\
 							<h4 style='text-align:center'>"+response[4*i+j].productName+"</h4>\
 							<h4 style='text-align:center'>Final Price:"+(response[4*i+j].productPrice-(response[4*i+j].productDiscount/100)*response[4*i+j].productPrice)+"</h4>\
 							<br><button type='button' class='btn btn-primary center-block'>Buy Now</button>\
 							<br><button type='button' class='btn btn-primary center-block'>Add To Cart</button>\
 						</div>\
 						</div>\
 					</div>");
            	
            }
          }

        $("#c1").append("<br><br></div>\
         				</div>");

       }

     //  $("#yoyo").innerHTML=html;
     }
   });
  // $("#yo").load("javascript/product.txt");

});

function buybutton(prod_id) {
	// alert("inside");
	localStorage.product_id_buynow = prod_id;
	localStorage.place_order=null;
	localStorage.deal_id=null;
	if(localStorage.user_Id==null)
		{
		 window.location = "http://localhost:5224/ebaytester/login.html";
		}
		else if(parseInt(localStorage.user_PINCODE)==8888)
		{
			window.location = "http://localhost:5224/ebaytester/address_form_buyer.html";
		}
	else
		{
		 window.location = "http://localhost:5224/ebaytester/orderReview.html";
		}
}

function addToCartButton(prod_id) {
	alert(prod_id);
	if(localStorage.user_Id==null)
		{
		window.location="http://localhost:5224/ebaytester/login.html";
		}
	else
		{
	$.ajax({
	       type: "post",
	       url:  "http://localhost:5224/ebaytester/webapi/cart/addToCart/"+prod_id+"/"+localStorage.user_Id+"/"+1,
	       complete: function(response){
             window.location="http://localhost:5224/ebaytester/cart.html";
	       	}
		   })
		}
}
// if(!(response[i].deal ==null || response[i].deal=="")){
//
//
//   console.log(i);
//   console.log(response[i].product_name);
// }
