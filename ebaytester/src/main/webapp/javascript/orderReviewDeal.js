//Author : Pulkit Agarwal(MT2017085)
alert("called");
var cart_product_list="";/*use to show products list*/
var result;/*use to store response */
var x;
var remove_pid;
var pid;
var total=0;

var count=0;
/*===================================================show products list in cart==================================================================*/

cart_product_list="";
total=0;
alert(localStorage.deal_id);
$('#deal_name').html("Deal Name: "+localStorage.deal_name);
$.ajax({
    type: "get",
    url:  "http://localhost:5224/ebaytester/webapi/deal/deal_id/"+localStorage.deal_id,
    dataType: "JSON",
    success: function(response){
      if(response!=null){

				result = response;
				localStorage.deal_id = result[0].deal_id;
				cart_product_list+='<ul class="list-group">';
				for(i in result)
				{
				cart_list(i);/*function call*/
				}
				cart_product_list+='</ul>';

				document.getElementById('cart').innerHTML=cart_product_list;
				document.getElementById('total_cost').innerHTML="Rs. "+total;
				localStorage.Total=total;
      }
		}
});

/*==========================================================Dynamic HTML code of products List=========================================================================================================== */
function cart_list(x){
    cart_product_list+='<li class="list-group-item">';
    if(result[x].free_check==1)
    	{
        cart_product_list+='<p id="seller_name"><b>Seller Email : '+result[x].seller_email+'</b></p>'+
			'<div class="row">'+
			'<div class="col-md-4">'+
			'<img id="image1" class="img-thumbnail" src="'+result[x].product_img_url+'" style="height:100px;width:100px">'+
			'</div>'+
			'<div class="col-md-4">'+
			'<p>'+result[x].product_name+'</p>'+
			'</div>'+
		 '<div class="col-md-4">'+
		 '<p><b>FREE</b></p>'+
		 '<p><b>Rs.'+result[x].product_price.toFixed(2)+'</b></p>'+
		 '<p id="item_id"><b>Item id: '+result[x].item_id+'</b></p>'+

         '</div>'+
    '</li>'

    	}
    else{
     cart_product_list+='<p id="seller_name"><b>FROM : '+result[x].seller_email+'</b></p>'+
     					'<div class="row">'+
     					'<div class="col-md-4">'+
     					'<img id="image1" class="img-thumbnail" src="'+result[x].product_img_url+'" style="height:100px;width:100px">'+
     					'</div>'+
     					'<div class="col-md-4">'+
     					'<p>'+result[x].product_name+'</p>'+
     					'</div>'+
						 '<div class="col-md-4">'+
						 '<p><b>Rs.'+result[x].product_price.toFixed(2)+'</b></p>'+
						 '<p id="item_id"><b>Item id: '+result[x].item_id+'</b></p>'+
             '</div>'+
            '</li>'
							total=parseFloat(parseFloat(total)+parseFloat(result[x].product_price)).toFixed(2);
   //  price.push(parseFloat(result[x].product_price.toFixed(2)));
    };
};
/*==========================================================================END OF CODE==========================================================*/
