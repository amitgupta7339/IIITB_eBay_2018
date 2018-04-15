package org.ebay_project.ebaytester.resource;

import java.util.List;
import java.util.ArrayList;
import java.util.Collection;

import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;

import org.ebay_project.ebaytester.model.AllDeals;
import org.ebay_project.ebaytester.model.DealFPStructure;
import org.ebay_project.ebaytester.model.ProductsDealStructure;
import org.ebay_project.ebaytester.service.AllDealsService;
//import org.json.JSONObject;
import org.ebay_project.ebaytester.service.CartService;

@Path("/deal")
public class AllDealsResource {

	@GET// (written by Prakhar)
	@Path("/list")
	@Produces(MediaType.APPLICATION_JSON)
	public ArrayList<DealFPStructure> DealList(){
		AllDealsService allDealsService = new AllDealsService();
		return allDealsService.showDealNames();
		
	}
	
    @GET// (written by Prakhar)
    @Path("/groups/{deal_name}")
    @Produces(MediaType.APPLICATION_JSON)
	public ArrayList<ProductsDealStructure> getAllGroups(@PathParam("deal_name") String deal_name)
	{
		AllDealsService allDealsService = new AllDealsService();
		ArrayList<ProductsDealStructure> x = allDealsService.showDealGroups(deal_name);
		return x;
	}

	@GET// (written by Prakhar)
	@Path("/deal_id/{deal_id}")
	@Produces(MediaType.APPLICATION_JSON)
	public ArrayList<AllDeals> DealItems(@PathParam("deal_id") int deal_id){
		AllDealsService allDealsService = new AllDealsService();
		return allDealsService.getDealItems(deal_id);
		
	}
	
    @POST// (written by Prakhar)
    @Path("/buydealproducts/{user_id}/{deal_id}")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(MediaType.TEXT_PLAIN)
    public String buyAllCartItems(@PathParam("user_id") int user_id,@PathParam("deal_id") int deal_id,@FormParam("card_number") String card_number, 
			  @FormParam("cvv")String cvv,
			  @FormParam("ex_month")String ex_month,
			  @FormParam("ex_year") String ex_year)
    {	AllDealsService C1=new AllDealsService();
    System.out.println("Inside new txn");
    String tmp = C1.buyCartItems(user_id,deal_id,card_number,cvv,ex_month,ex_year);
    System.out.println(tmp);
    	return tmp;
    	
    }
		
}
