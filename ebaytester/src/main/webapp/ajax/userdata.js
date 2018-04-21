$.ajax({
        type: "get",
        url:  "http://localhost:5224/ebaytester/webapi/user/getalluser",
        dataType: "JSON",
        success: function(response){
        	var str=``;
          if(response!=null){
             str+=`<table style="width:100%;" id="table">
            <tr>
          <th>S.NO.</th>
          <th>firstname</th>
          <th>lastname</th>
          <th>email</th>
          <th>wallet_balance</th>
        </tr>`
            for (i=1;i<response.length;i++) {
            	  str+=`<tr>
            	  <td>${i}</td>
                  <td>${response[i].user_fname}</td>
                  <td>${response[i].user_lname}</td>
                  <td>${response[i].user_email}</td>
                  <td>${response[i].wallet_balance}</td>
                  </tr>`
           }
            str+=`</table>`
            	$('#user_table').html(str);
          }
        }
     });