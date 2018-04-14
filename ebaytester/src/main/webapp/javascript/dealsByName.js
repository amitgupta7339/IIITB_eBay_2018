$(document).ready(function(){
var results = ""
var strn=""
 var deal_name = localStorage.deal_name;
 $.ajax({
     type: "get",
     url:  "http://localhost:5224/ebaytester/webapi/deal/groups/"+deal_name,
     dataType: "JSON",
     success: function(response){
       if(response!=null){
    	   results = response;
         strn="";
         var remaining_products=response.length;
          console.log("hi 2");
          strn+=`<h3>Deals</h3>
                 <h4>${deal_name} Free!!</h4>`
                  // $('#a1').html(strn);

          for(var i=0;i<response.length;i++){

              var remaining_products=response[i].allDeals.length;
strn+=`<div style='border-bottom:1px solid #F9F9F9; padding:10px; margin:10px'>
          <hr></hr>
          <h3>Deal expires in ${response[i].allDeals[0].deal_validity} days</h3><br>`

              for(var j=0;j<Math.ceil(response[i].allDeals.length/3);j++)
              {
                strn+=`<div class='row'>`

                 for(var k=0;k<3 && remaining_products>0;k++)
                 {
                     remaining_products--;
                     var free;
                     if(response[i].allDeals[3*j+k].free_check==0){
                       free="";
                     }else{
                       free="Free";
                     }
                      strn+=`<div class='col-sm-4' id='div1'>
                              <div class='thumbnail'>
                                <p>Item id: ${response[i].allDeals[3*j+k].item_id}</p>
                                <img src="${response[i].allDeals[3*j+k].product_img_url}" style='height:200px;width:250px;' alt='img1'>
                                <div>
                                  <h6 style='text-align:center;'><b>${response[i].allDeals[3*j+k].product_name}</b></h6>
                                  <h6 style='text-align:center;'><b>Price:</b>${response[i].allDeals[3*j+k].product_price} ${free}</h6>
                                </div>
                              </div>
                            </div>`

                 }
                 strn+=`</div>`
               // $("#e1").append("\
               // </div>");

            }
            strn+=`<div class='text-center'>
                    <h6 style='text-align:center;'><b>Seller Email:</b>${response[i].allDeals[0].seller_email}</h6>
                    <button class='btn btn-primary' id='button1' onclick='buybutton(${response[i].allDeals[0].deal_id})'>Buy Now</button>\
                   </div>
                  </div>`
$('#a1').html(strn);
         }
       }
     }
 });
});

function buybutton(i)
{localStorage.product_id_buynow=null;
  localStorage.place_order=null;
	 alert(i);
	 localStorage.deal_id = i;
		localStorage.place_order=null;
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
			 window.location = "http://localhost:5224/ebaytester/orderReviewDeal.html";
			}

//	 if(localStorage.fname == null && localStorage.lname ==null)
//		 {
//		 window.location = "http://localhost:5224/ebaytester/login.html";
//		 }else{
////	 alert("Inside");
//	 window.location = "http://localhost:5224/ebaytester/orderReviewDeal.html";
//		 }
}
