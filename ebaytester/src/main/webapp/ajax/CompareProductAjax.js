$.ajax({
      type: "get",
      url:  "http://localhost:5224/ebaytester/webapi/category/getAllCategory",
      dataType: "JSON",
      success: function(response){
        if(response!=null)
        {var i;
          for (i=0;i<response.length;i++) {
          $('<option value="'+ response[i].category_name+'">' + response[i].category_name+ '</option>').appendTo('#Category');
            // catOptions += "<option>" + array[i] + "</option>";
         }
        }
      }
    });

$('#Category').on('change', function() {
	$('#Subcategory').empty();
	if(this.value!= ''){
		$.ajax({
		        type: "get",
		        url:  "http://localhost:5224/ebaytester/webapi/category/"+this.value,
		        dataType: "JSON",
		        success: function(response){
		           if(response!=null)
		             {
		               var i;
		               $('<option value="'+ 'Select SubCategory'+'">' + 'Select SubCategory'+ '</option>').appendTo('#Subcategory');
			              
		               for (i=0;i<response.length;i++) {
		            	   $('<option value="'+ response[i].sub_category_name+'">' + response[i].sub_category_name+ '</option>').appendTo('#Subcategory');
		                       }
		             }
		         }
		       });
		  }
	else{
		$('<option value="'+''+'">' + "sub Category"+ '</option>').appendTo('#Subcategory');
	}
    });

//for displaying product


$('#Subcategory').on('change', function() {
	$('#Product1').empty();
	if(this.value!= ''){
		$.ajax({
		        type: "get",
		        url:  "http://localhost:5224/ebaytester/webapi/products/getProductList/"+this.value,
		        dataType: "JSON",
		        success: function(response){
		           if(response!=null)
		             {
		               var i;
		               $('<option value="'+ 'Select Product'+'">' + 'Select Product'+ '</option>').appendTo('#Product1');
			              
		               for (i=0;i<response.length;i++) {
		            	   $('<option value="'+ response[i].product_name+'">' + response[i].product_name+ '</option>').appendTo('#Product1');
		                       }
		             }
		         }
		       });
		  }
	else{
		$('<option value="'+''+'">' + "Product 1"+ '</option>').appendTo('#Product1');
	}
    });

$('#Subcategory').on('change', function() {
	$('#Product2').empty();
	if(this.value!= ''){
		$.ajax({
		        type: "get",
		        url:  "http://localhost:5224/ebaytester/webapi/products/getProductList/"+this.value,
		        dataType: "JSON",
		        success: function(response){
		           if(response!=null)
		             {
		               var i;

	            	   $('<option value="'+ 'Select Product'+'">' + 'Select Product'+ '</option>').appendTo('#Product2');
		               for (i=0;i<response.length;i++) {
		            	   $('<option value="'+ response[i].product_name+'">' + response[i].product_name+ '</option>').appendTo('#Product2');
		                       }
		             }
		         }
		       });
		  }
	else{
		$('<option value="'+''+'">' + "Product 1"+ '</option>').appendTo('#Product2');
	}
    });

$('#ProductCompare').submit(function(){
	 // alert("saumya");
		$.ajax({
			type: "post",
			url:  "http://localhost:5224/ebaytester/webapi/products/getComparedProduct",
			data: $('#ProductCompare').serialize(),
			dataType: "JSON",
			success: function(response){
					//alert("done");
					var str='<table id="compareTable" class="table table-bordered table-hover">';
			        if(response!=null)
				         {
				        	 var prod1=response[0];
				        	 var prod2=response[1];
				        	 str+='<tr><td>Product Id</td><td>'+prod1.product_id+'</td><td>'+prod2.product_id+'</td><tr>';
				        	 str+='<tr><td>Product Name</td><td>'+prod1.product_name+'</td><td>'+prod2.product_name+'</td><tr>';
				        	 str+='<tr><td>product_condition</td><td>'+prod1.product_condition+'</td><td>'+prod2.product_condition+'</td><tr>';
				        	 str+='<tr><td>Product Description</td><td>'+prod1.product_description+'</td><td>'+prod2.product_description+'</td><tr>';
				        	 str+='<tr><td>Product Item Id</td><td>'+prod1.item_id+'</td><td>'+prod2.item_id+'</td><tr>';
				        	 str+='<tr><td>Product Brand</td><td>'+prod1.brand+'</td><td>'+prod2.brand+'</td><tr>';
				        	 str+='<tr><td>Product Price</td><td>'+prod1.product_price+'</td><td>'+prod2.product_price+'</td><tr>';
				        	 str+='<tr><td>Product Discount</td><td>'+prod1.product_discount+'</td><td>'+prod2.product_discount+'</td><tr>';
				        	 str+='<tr><td>Product Available quantity</td><td>'+prod1.product_available_quantity+'</td><td>'+prod2.product_available_quantity+'</td><tr>';
				        	 str+='<tr><td>Product Shipping</td><td>'+prod1.product_shipping+'</td><td>'+prod2.product_shipping+'</td><tr>';
				        	 str+='<tr><td>Product color</td><td>'+prod1.color+'</td><td>'+prod2.color+'</td><tr>';
				        /*	 str+='<tr><td>Product Id</td><td>'+prod1.product_id+'</td><td>'+prod2.product_id+'</td><tr>';
				        	 str+='<tr><td>Product Id</td><td>'+prod1.product_id+'</td><td>'+prod2.product_id+'</td><tr>';
				        	 str+='<tr><td>Product Id</td><td>'+prod1.product_id+'</td><td>'+prod2.product_id+'</td><tr>';
				        	 str+='<tr><td>Product Id</td><td>'+prod1.product_id+'</td><td>'+prod2.product_id+'</td><tr>';
				        	 str+='<tr><td>Product Id</td><td>'+prod1.product_id+'</td><td>'+prod2.product_id+'</td><tr>';*/
				         }
				       	document.getElementById("compareTable").innerHTML = str+'</table>';
					}
				     });
				   return false;
	     });		     
