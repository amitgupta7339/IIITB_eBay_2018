package org.ebay_project.ebaytester.model;

import java.util.ArrayList;

public class ProductsDealStructure {

	ArrayList<AllDeals> allDeals;

	public ProductsDealStructure() {
		
	}


	public ProductsDealStructure(ArrayList<AllDeals> allDeals) {
		
		this.allDeals = allDeals;
	}


	public ArrayList<AllDeals> getAllDeals() {
		return allDeals;
	}


	public void setAllDeals(ArrayList<AllDeals> allDeals) {
		this.allDeals = allDeals;
	}
	
	
	
	
}
