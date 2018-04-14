package org.ebay_project.ebaytester.model;

public class AllDeals {
	
	private int seller_deal_id;
	private int deal_id;
	private int seller_id;
	private String deal_name;
	private int product_id;
	java.sql.Date start_date;
	java.sql.Date end_date;
	private int free_check;
	
	
	private int discount;
	private int buy_number;
	private int get_number;
	
	private String product_img_url;
	private String item_id;
	private String product_name;
	private String seller_email;
	private float product_price;
	private int product_rating;
	private String product_description;
	
	private int deal_validity;
	
	
	
	public AllDeals() {
	
	}


	public int getSeller_deal_id() {
		return seller_deal_id;
	}


	public void setSeller_deal_id(int seller_deal_id) {
		this.seller_deal_id = seller_deal_id;
	}


	public int getDeal_id() {
		return deal_id;
	}


	public void setDeal_id(int deal_id) {
		this.deal_id = deal_id;
	}


	public int getSeller_id() {
		return seller_id;
	}


	public void setSeller_id(int seller_id) {
		this.seller_id = seller_id;
	}


	public String getDeal_name() {
		return deal_name;
	}


	public void setDeal_name(String deal_name) {
		this.deal_name = deal_name;
	}

	public int getProduct_id() {
		return product_id;
	}


	public void setProduct_id(int product_id) {
		this.product_id = product_id;
	}


	public int getBuy_number() {
		return buy_number;
	}


	public void setBuy_number(int buy_number) {
		this.buy_number = buy_number;
	}


	public int getGet_number() {
		return get_number;
	}


	public void setGet_number(int get_number) {
		this.get_number = get_number;
	}


	public java.sql.Date getStart_date() {
		return start_date;
	}


	public void setStart_date(java.sql.Date start_date) {
		this.start_date = start_date;
	}


	public java.sql.Date getEnd_date() {
		return end_date;
	}


	public void setEnd_date(java.sql.Date end_date) {
		this.end_date = end_date;
	}


	public int getFree_check() {
		return free_check;
	}


	public void setFree_check(int free_check) {
		this.free_check = free_check;
	}


	public int getDiscount() {
		return discount;
	}


	public void setDiscount(int discount) {
		this.discount = discount;
	}


	public String getProduct_img_url() {
		return product_img_url;
	}


	public void setProduct_img_url(String product_img_url) {
		this.product_img_url = product_img_url;
	}


	public String getItem_id() {
		return item_id;
	}


	public void setItem_id(String item_id) {
		this.item_id = item_id;
	}


	public String getProduct_name() {
		return product_name;
	}


	public void setProduct_name(String product_name) {
		this.product_name = product_name;
	}


	public String getSeller_email() {
		return seller_email;
	}


	public void setSeller_email(String seller_email) {
		this.seller_email = seller_email;
	}


	public float getProduct_price() {
		return product_price;
	}


	public void setProduct_price(float product_price) {
		this.product_price = product_price;
	}


	public int getProduct_rating() {
		return product_rating;
	}


	public void setProduct_rating(int product_rating) {
		this.product_rating = product_rating;
	}


	public String getProduct_description() {
		return product_description;
	}


	public void setProduct_description(String product_description) {
		this.product_description = product_description;
	}


	public int getDeal_validity() {
		return deal_validity;
	}


	public void setDeal_validity(int deal_validity) {
		this.deal_validity = deal_validity;
	}


	@Override
	public String toString() {
		return "AllDeals [seller_deal_id=" + seller_deal_id + ", deal_id=" + deal_id + ", seller_id=" + seller_id
				+ ", deal_name=" + deal_name + ", product_id=" + product_id + ", start_date=" + start_date
				+ ", end_date=" + end_date + ", free_check=" + free_check + ", discount=" + discount + ", buy_number="
				+ buy_number + ", get_number=" + get_number + ", product_img_url=" + product_img_url + ", item_id="
				+ item_id + ", product_name=" + product_name + ", seller_email=" + seller_email + ", product_price="
				+ product_price + ", product_rating=" + product_rating + ", product_description=" + product_description
				+ ", deal_validity=" + deal_validity + "]";
	}
	
	
	
	

}
