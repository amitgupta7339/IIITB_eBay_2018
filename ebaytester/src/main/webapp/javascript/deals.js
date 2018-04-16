
$.ajax({
       type: "get",
       url:  "http://localhost:5224/ebaytester/webapi/deal/list",
       dataType: "JSON",
       success: function(response){
         if(response!=null){
             var remaining_products=response.length;
             var strng = "";
             for(var i=0;i<Math.ceil(response.length/4);i++){
              strng += `<div class="container-fluid">
                <div class="row">`
                for(var j=0;j<4 && remaining_products>0;j++){
                  remaining_products--;
                  strng+=`<div class="col-sm-3" id="div1">
                            <div class="thumbnail" >
                             <a href="#"><img src="${response[4*i+j].product_img_url}" onclick="deal('${response[4*i+j].deal_name}')" alt="img1" style="height:200px;width:100%">
                             <h4 style="text-align:center" id="img" onclick="deal('${response[4*i+j].deal_name}')">${response[4*i+j].deal_name}</h4>
                             </a>
                            </div>
                          </div>`
                }
                strng+=`</div></div>`;
             }
             $('#d1').html(strng);
          }
        }
     });

   function deal(deal_name)
   {
	   localStorage.deal_name = deal_name;
	   window.location = "http://localhost:5224/ebaytester/dealByName.html";
   }
