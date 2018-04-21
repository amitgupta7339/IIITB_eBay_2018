var transaction_product_list="";/*use to show products list*/
var result;/*use to store response */
var x;
var pid;
var user = 0;
var count=0;
var result_for_name;
var result_for_name1;

//localStorage.user_Id = 2;
//localStorage.track_order_txn = 'TXN00004';

transaction_list();

/* order related to transaction_id product display */
function transaction_list(){
		transaction_product_list="";
	  $.ajax({
	  				  type :"GET",
	  					url: "http://localhost:5224/ebaytester/webapi/transaction/getATransaction/"+localStorage.user_Id+"/"+localStorage.track_order_txn,
	            dataType:'JSON',
	  					success: function(response){
	            result=response;

	            user = result[0].a1;
	            //alert(user);

	            transaction_product_list+='<ul class="list-group">';
	              for(i in result)
	              {
	            	//alert(user);
	                txn_list(i);/*function call*/
	              }
	              transaction_product_list+='</ul>';

	              document.getElementById('product_display').innerHTML=transaction_product_list;
	            }
	          });
	  return false;
};



function txn_list(x){
	//alert("hello");
	transaction_product_list+='<li class="list-group-item">';
	transaction_product_list+=
					'<div class="row">'+
					  '<div class = "container">'+
						'<div class="col-sm-3">'+
    							'<img id="image1" src="'+result[x].product_img_url+'" style="height:75px;width:75px">'+
    						'</div>'+
    					'<div class="col-sm-8">'+
    						'<div class="row" style="margin-top:10px;">'+
    							'<div class="col-sm-2">'+
    								'<p id = "product_id">item id: '+result[x].item_id+'</p>'+
    								'</div>'+
    							'<div class="col-sm-6">'+
    								'<p id = "product_name">'+result[x].product_name+'</p>'+
    								'</div>'+
    							'<div class="col-sm-4">'+
    								'<button type="submit" class="btn-sm btn-success" id="rate_seller" data-toggle="modal" data-target="#rateModal" data-backdrop="static" data-keyboard="false" onclick="sellerRate('+x+')" >Rate Seller</button>'+
    								'</div>'+
    						'</div>'+
    						'<div class="row" style="margin-top:10px;">'+
    							'<div class="col-sm-2">'+
    								'<p>Status : </p>'+
    								'</div>'+
    							'<div class="col-sm-6">'+
    								'<p id = "product_status">'+result[x].product_status+'</p>'+
    								'</div>'+
    							'<div class="col-sm-4">'+
    								'<button type="submit" class="btn-sm btn-warning" id="update_staus" onclick="update_staus('+x+')">Update</button>'+
    								'</div>'+
    						'</div>'+
    					'</div>'+
    				'</div>'+
    			'</div>'+
    		'</li>'
return false;
};


function sellerRate(x){

	//alert("http://localhost:5224/ebaytester/webapi/products/getproduct/"+ result[x].product_id);

	$.ajax({
        type :"GET",
        url: "http://localhost:5224/ebaytester/webapi/products/getproduct/"+ result[x].product_id,
        async: false,
        dataType:'JSON',
        success: function(response){


			result_for_name = response.user_id;

		//	alert(result_for_name);
        }
      });


	var seller_id = parseInt(result_for_name);
	//alert("http://localhost:5224/ebaytester/webapi/user/getUserName/"+ seller_id);

	$.ajax({
        type :"GET",
        url: "http://localhost:5224/ebaytester/webapi/user/getUserName/"+ seller_id,
        async: false,
        dataType:'text',
        complete: function(response){

        	var a = JSON.stringify(response);
			var b = JSON.parse(a);
			//alert(b.responseText);

			result_for_name1 = b.responseText;
        }
      });
	localStorage.seller_to_be_rated = result[x].product_id;
	document.getElementById("rateModal_seller_name").innerHTML =  result_for_name1;
	return false;
	};


$('#rateSeller').click(function(e){
		  //e.preventDefault();
		  var amt = $("#enterAmount").val();
		  var e = document.getElementById("rateselecter");
		  var strUser = e.options[e.selectedIndex].value;
		  var str = "http://localhost:5224/ebaytester/webapi/transaction/rateSeller/"
			  + localStorage.user_Id + "/" + localStorage.track_order_txn + "/" + localStorage.seller_to_be_rated +"/"+ user +"/"+strUser;

		  $.ajax({
	            type :"PUT",
	            url: "http://localhost:5224/ebaytester/webapi/transaction/rateSeller/"
	            	+ localStorage.user_Id + "/" + localStorage.track_order_txn + "/" + localStorage.seller_to_be_rated +"/"+ user +"/"+strUser,
	            async: false,
	            dataType:'text',
	            complete: function(response){

	            	var a = JSON.stringify(response);
					var b = JSON.parse(a);
					alert(b.responseText);

					$('#myModal').modal('hide');
					transaction_list();/*function call*/

	            }
	          });
		});


function update_staus(x) {

	if(user == 1){
		$.ajax({
			type : "PUT",
			url : "http://localhost:5224/ebaytester/webapi/transaction/updateUserTransaction/"
				+localStorage.user_Id+"/"+localStorage.track_order_txn+"/"+result[x].product_id,
			dataType : 'text',
			complete : function(response) {

				var a = JSON.stringify(response);
				var b = JSON.parse(a);
				alert(b.responseText);

				transaction_list();/*function call*/
			}
		});
	}else{
		$.ajax({
			type : "PUT",
			url : "http://localhost:5224/ebaytester/webapi/transaction/updateSellerTransaction/"
					+ localStorage.user_Id + "/" + localStorage.track_order_txn + "/" + result[x].product_id,
			dataType : 'text',
			complete : function(response) {
				var a = JSON.stringify(response);
				var b = JSON.parse(a);
				alert(b.responseText);
				transaction_list();/*function call*/
			}
		});
	}

};
