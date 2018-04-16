var x;
var content="";
var result="";
//=============================================default ajax call for deals show in Carousel=======================================================//
$.ajax({
  type : "get",
  url : "http://localhost:5224/ebaytester/webapi/deal/list",
  dataType : "JSON",
  success : function(response) {
    if (response != null) {
      result=response;
      var i=0;
      var count=0;
      content="";
      console.log(response.length);
      for(i=0;i<(response.length);i=i+3)
      {
    	if(i==0){
    		content+=`<div class="item active">`
	  	}
	  	else{
	  		content+=`<div class="item">`
	  	}
	  	if(++count<=response.length && response[i].deal_name!="")
	  	{
	  		deal_image(i);
	  	}
        if(++count<=response.length && response[i+1].deal_name!="")
        {
          deal_image(i+1);
        }
        if(++count<=response.length && response[i+2].deal_name!="")
        {
          deal_image(i+2);
        }
        content+=`</div>`
      }
      $('#Deal_Images_List').html(content);
    }
  }
});
//============================================================function of show images dynamic HTML code===========================================//
function deal_image(x)
{
  content+=`<div>
  <span class="col-sm-4"><a href="#" onclick="deal_products(${x})"><img src="${result[x].product_img_url}" alt="Chicago" style="height:300px;width:100%"></a>
  <div class="carousel-caption">
    <a href="#" onclick="deal_products(${x})"><h3>${result[x].deal_name}</h3></a>
  </div>
  </span>
  </div>`
};
//======================================================after click on image redireact into deal products page in grid view=======================//
function deal_products(x)
{
	localStorage.deal_name = result[x].deal_name;
  alert(localStorage.deal_name);
  window.location = "http://localhost:5224/ebaytester/dealByName.html";
};
//========================================================================END OF CODE=============================================================//
