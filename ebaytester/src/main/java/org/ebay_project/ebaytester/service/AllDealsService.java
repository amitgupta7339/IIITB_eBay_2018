package org.ebay_project.ebaytester.service;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

import org.ebay_project.ebaytester.model.AllDeals;
import org.ebay_project.ebaytester.model.DealFPStructure;
import org.ebay_project.ebaytester.model.Payment;
import org.ebay_project.ebaytester.model.ProductsDealStructure;

public class AllDealsService {

	Connection connection = null;
	Payment pay = new Payment();
	PaymentService payser = new PaymentService();
	TransactionService t = new TransactionService();

	public AllDealsService() {

		try {

			Class.forName("com.mysql.jdbc.Driver");
			connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/ebaytest", "root", "root");

			System.out.println("connection !");
		} catch (Exception e) {
			System.out.println("Exception found" + e);
			try {
				connection.close();
			} catch (Exception ee) {
				System.out.println("Connection close error");
			}
		}
	}
	String deal_name;
//=============================================PAYMENT THROUGH CARD DEAL ITEMS====================================//
	public String buyCartItems(int user_id, int deal_id, String card_number, String cvv, String ex_month,
			String ex_year) { 
		try {
			System.out.println("DealId:" + deal_id);
			System.out.println("SellerId:" + user_id);
			String ex_date = ex_month + "/" + ex_year;
			int product_discount = 0;// Deal applied already
			pay = payser.cardDetailsValidation(card_number, cvv, ex_date);
			if (pay != null) {
				String query = "select C.free_check,P.product_price from product as P,(select product_id,free_check from seller_deal where deal_id="
						+ deal_id + ") as C where C.product_id=P.product_id";
				System.out.println(query);
				Statement stmt = connection.createStatement();
				ResultSet rs = stmt.executeQuery(query);
				float total = 0;
				int discount = 0;
				while (rs.next()) {
					float price = rs.getFloat("product_price");
					if (rs.getInt("free_check") == 1) {
						discount = 100;
					} else {
						discount = 0;
					}
				   query="SELECT * FROM seller_deal WHERE deal_id= ?";
				   PreparedStatement pstmt = connection.prepareStatement(query);
					pstmt.setInt(1, deal_id);
					System.out.println(query);
					ResultSet rs1=pstmt.executeQuery();
					if(rs1.next()) {
						java.sql.Date end_date=rs1.getDate("end_date");
						java.sql.Date start_date=rs1.getDate("start_date");
						deal_name=rs1.getString("deal_name");
						java.sql.Date current_date = null;
						String query3 = "SELECT CURDATE() today";
						PreparedStatement preparedstmnt3=null;
						preparedstmnt3 = connection.prepareStatement(query3);
						ResultSet rs3 = preparedstmnt3.executeQuery();
						if (rs3.next()) {
							current_date = rs3.getDate("today");
							// System.out.println(current_date);
						}
						if(!current_date.after(start_date)&&!current_date.before(end_date) ) {
							return "Deal Expire";
						}
					}
					
					// int discount = rs.getInt("product_discount");
					price = price - (price * discount) / 100;
					System.out.println("Price:" + price);
					total = total + (price);
				}
				if (total > pay.getBalance()) {
					return "insufficient balance";
				} else {
					System.out.println("Users current balance before buying product is " + pay.getBalance());
					System.out.println("Total buy price is " + total);
					float balance = pay.getBalance() - total;
					query = "update cardDetails set balance= ? where card_number=?;";
					PreparedStatement pstmt = connection.prepareStatement(query);
					pstmt.setFloat(1, balance);
					pstmt.setString(2, pay.getCard_number());
					pstmt.execute();
					System.out.println("Remaining balance after buying product is  " + balance);
					query = "select balance from cardDetails where card_number=000000000000000;";
					rs = stmt.executeQuery(query);
					if (rs.next())
						balance = rs.getFloat("balance");
					query = "update cardDetails set balance=" + (balance + total)
							+ " where card_number=000000000000000;";
					stmt.execute(query);
					String transaction = t.enterCartTransactionDeal(deal_id, user_id,deal_name);
					query = "select product_id from seller_deal where deal_id=" + deal_id;
					rs = stmt.executeQuery(query);
					while (rs.next()) {
						Statement st = connection.createStatement();
						String update = "select * from product where product_id='" + rs.getInt("product_id") + "';";
						ResultSet t = st.executeQuery(update);
						if (t.next()) {
							Statement ss = connection.createStatement();
							int updatequantity = t.getInt("product_available_quantity");
							String u = "update product set product_available_quantity=" + (updatequantity - 1)
									+ " where product_id='" + rs.getInt("product_id") + "';";
							ss.executeUpdate(u);

							int soldquantity = t.getInt("product_sold_quantity");
							u = "update product set product_sold_quantity=" + (soldquantity + 1) + " where product_id='"
									+ rs.getInt("product_id") + "';";
							ss.executeUpdate(u);

						}
					}
					System.out.println("true" + "TXN000" + transaction);
					return "true" + "TXN000" + transaction;
				}

			} else
				return "invalid card details";
		} catch (Exception e) {
			e.printStackTrace();
			return "transaction failure";
		}
	}

// ===============================================================================================================//
	public ArrayList<DealFPStructure> showDealNames() {
		ArrayList<DealFPStructure> dealnames = new ArrayList<DealFPStructure>();

		PreparedStatement preparedstmnt = null;
		PreparedStatement preparedstmnt1 = null;

		try {
			String query = "select DISTINCT deal_name, min(product_id) as prod_id from seller_deal group by deal_name";
			preparedstmnt = connection.prepareStatement(query);
			ResultSet rs = preparedstmnt.executeQuery();

			while (rs.next()) {
				int x = rs.getInt("prod_id");
				DealFPStructure dealFPStructure = new DealFPStructure();
				String query1 = "select product_img_url from product where product_id = ?";
				preparedstmnt1 = connection.prepareStatement(query1);
				preparedstmnt1.setInt(1, x);
				ResultSet rs1 = preparedstmnt1.executeQuery();
				if (rs1.next()) {
					dealFPStructure.setDeal_name(rs.getString("deal_name"));
					dealFPStructure.setProduct_img_url(rs1.getString("product_img_url"));
				}

				dealnames.add(dealFPStructure);

			}

		} catch (Exception e) {
			e.printStackTrace();
			;
		}

		return dealnames;
	}

// ============================================ODER REVIEW PAGE LIST==============================================//
	public ArrayList<AllDeals> getDealItems(int deal_id) {
		AllDeals allDeals;
		int buyno = 0;// waste values change later
		int getno = 0;
		int disc = 0;
		PreparedStatement preparedstmnt3 = null;
		PreparedStatement preparedstmnt4 = null;
		PreparedStatement preparedstmnt7 = null;
		PreparedStatement preparedstmnt8 = null;
		PreparedStatement preparedstmnt9 = null;
		ArrayList<AllDeals> productsInADeal = new ArrayList<AllDeals>();
		try {
			String query4 = "select * from seller_deal where deal_id = ?";
			preparedstmnt4 = connection.prepareStatement(query4);
			preparedstmnt4.setInt(1, deal_id);
			ResultSet rsq4 = preparedstmnt4.executeQuery();

			while (rsq4.next()) {
				allDeals = new AllDeals();
				java.sql.Date enddate = rsq4.getDate("end_date");
				java.sql.Date startdate = rsq4.getDate("start_date");
				System.out.println(enddate);
				allDeals.setSeller_deal_id(rsq4.getInt("seller_deal_id"));
				allDeals.setDeal_id(rsq4.getInt("deal_id"));
				allDeals.setSeller_id(rsq4.getInt("seller_id"));
				allDeals.setDeal_name(rsq4.getString("deal_name"));
				allDeals.setProduct_id(rsq4.getInt("product_id"));
				allDeals.setStart_date(rsq4.getDate("start_date"));
				allDeals.setEnd_date(rsq4.getDate("end_date"));
				allDeals.setFree_check(rsq4.getInt("free_check"));
				allDeals.setBuy_number(buyno);
				allDeals.setGet_number(getno);
				allDeals.setDiscount(disc);

				String query7 = "SELECT user_email from user where user_id = ?";
				preparedstmnt7 = connection.prepareStatement(query7);
				preparedstmnt7.setInt(1, rsq4.getInt("seller_id"));
				ResultSet rs7 = preparedstmnt7.executeQuery();
				if (rs7.next())
					allDeals.setSeller_email(rs7.getString("user_email"));

				String query8 = "SELECT * from product where product_id = ?";
				preparedstmnt8 = connection.prepareStatement(query8);
				preparedstmnt8.setInt(1, rsq4.getInt("product_id"));
				ResultSet rs8 = preparedstmnt8.executeQuery();
				if (rs8.next()) {
					allDeals.setProduct_img_url(rs8.getString("product_img_url"));
					allDeals.setItem_id(rs8.getString("item_id"));
					allDeals.setProduct_name(rs8.getString("product_name"));
					allDeals.setProduct_price(rs8.getFloat("product_price"));
					allDeals.setProduct_rating(rs8.getInt("product_rating"));
					allDeals.setProduct_description(rs8.getString("product_description"));
				}
				java.sql.Date current_date = null;
				String query3 = "SELECT CURDATE() today";
				preparedstmnt3 = connection.prepareStatement(query3);
				ResultSet rs3 = preparedstmnt3.executeQuery();
				if (rs3.next()) {
					current_date = rs3.getDate("today");
					// System.out.println(current_date);
				}

				String query9 = "SELECT DATEDIFF('" + enddate + "','" + current_date + "') days";
				preparedstmnt9 = connection.prepareStatement(query9);
				ResultSet rs9 = preparedstmnt9.executeQuery();
				if (rs9.next()) {
					int number_of_days = rs9.getInt("days");
					number_of_days++;
					allDeals.setDeal_validity(number_of_days);
				}
				productsInADeal.add(allDeals);

			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return productsInADeal;
	}
// ========================================SHOW PRODUCTS IN A CHOOSSEN DEAL PRODUCT===============================//
	public ArrayList<ProductsDealStructure> showDealGroups(String deal_name) {
		// List<ArrayList<AllDeals>> alldealsbyname = new
		// ArrayList<ArrayList<AllDeals>>();
		ArrayList<ProductsDealStructure> alldealsbyname = new ArrayList<ProductsDealStructure>();
		ArrayList<Integer> dealid = new ArrayList<Integer>();
		int buyno = 0;
		int getno = 0;
		int disc = 0;
		// int x=0;
		AllDeals allDeals;

		PreparedStatement preparedstmnt = null;
		// PreparedStatement preparedstmnt1 = null;
		PreparedStatement preparedstmnt2 = null;
		PreparedStatement preparedstmnt3 = null;
		PreparedStatement preparedstmnt4 = null;
		PreparedStatement preparedstmnt5 = null;
		PreparedStatement preparedstmnt6 = null;
		PreparedStatement preparedstmnt7 = null;
		PreparedStatement preparedstmnt8 = null;
		PreparedStatement preparedstmnt9 = null;

		try {

			String query = "select buy_number, get_number, discount from admin_deal where deal_name = ?";
			preparedstmnt = connection.prepareStatement(query);
			preparedstmnt.setString(1, deal_name);
			ResultSet rs = preparedstmnt.executeQuery();

			if (rs.next()) {
				buyno = rs.getInt("buy_number");
				getno = rs.getInt("get_number");
				disc = rs.getInt("discount");
				System.out.println(disc);
			}

			String query2 = "select deal_id from seller_deal where deal_name = ? group by deal_id";
			preparedstmnt2 = connection.prepareStatement(query2);
			preparedstmnt2.setString(1, deal_name);
			ResultSet rs2 = preparedstmnt2.executeQuery();

			while (rs2.next()) {

				String query6 = "SELECT distinct end_date,start_date from seller_deal where deal_id = ?";
				preparedstmnt6 = connection.prepareStatement(query6);
				preparedstmnt6.setInt(1, rs2.getInt("deal_id"));
				ResultSet rs6 = preparedstmnt6.executeQuery();
				java.sql.Date enddate = null;
				java.sql.Date start_date = null;
				if (rs6.next()) {
					enddate = rs6.getDate("end_date");
					start_date = rs6.getDate("start_date");
				}

				java.sql.Date current_date = null;
				String query3 = "SELECT CURDATE() today";
				preparedstmnt3 = connection.prepareStatement(query3);
				ResultSet rs3 = preparedstmnt3.executeQuery();
				if (rs3.next()) {
					current_date = rs3.getDate("today");
					// System.out.println(current_date);
				}

				if (!start_date.after(current_date) && !enddate.before(current_date)) {
					/* historyDate <= todayDate <= futureDate */
					System.out.println("hello");
					String query5 = "SELECT DATEDIFF('" + enddate + "','" + current_date + "') days";
					preparedstmnt5 = connection.prepareStatement(query5);
					ResultSet rs5 = preparedstmnt5.executeQuery();
					if (rs5.next()) {
						int datediff = rs5.getInt("days");
						// System.out.println(datediff);
						datediff++;/* change */
						if (datediff > 0)
							dealid.add(rs2.getInt("deal_id"));

					}
				}
				System.out.println("============");
				System.out.println(dealid.size());

			}

			for (int i = 0; i < dealid.size(); i++) {
				ArrayList<AllDeals> productsInADeal = new ArrayList<AllDeals>();

				String query4 = "select * from seller_deal where deal_id = ?";
				preparedstmnt4 = connection.prepareStatement(query4);
				preparedstmnt4.setInt(1, dealid.get(i));
				ResultSet rsq4 = preparedstmnt4.executeQuery();

				while (rsq4.next()) {
					allDeals = new AllDeals();
					java.sql.Date enddate = rsq4.getDate("end_date");
					java.sql.Date startdate = rsq4.getDate("start_date");
					System.out.println(enddate);
					allDeals.setSeller_deal_id(rsq4.getInt("seller_deal_id"));
					allDeals.setDeal_id(rsq4.getInt("deal_id"));
					allDeals.setSeller_id(rsq4.getInt("seller_id"));
					allDeals.setDeal_name(rsq4.getString("deal_name"));
					allDeals.setProduct_id(rsq4.getInt("product_id"));
					allDeals.setStart_date(rsq4.getDate("start_date"));
					allDeals.setEnd_date(rsq4.getDate("end_date"));
					allDeals.setFree_check(rsq4.getInt("free_check"));
					allDeals.setBuy_number(buyno);
					allDeals.setGet_number(getno);
					allDeals.setDiscount(disc);

					String query7 = "SELECT user_email from user where user_id = ?";
					preparedstmnt7 = connection.prepareStatement(query7);
					preparedstmnt7.setInt(1, rsq4.getInt("seller_id"));
					ResultSet rs7 = preparedstmnt7.executeQuery();
					if (rs7.next())
						allDeals.setSeller_email(rs7.getString("user_email"));

					String query8 = "SELECT * from product where product_id = ?";
					preparedstmnt8 = connection.prepareStatement(query8);
					preparedstmnt8.setInt(1, rsq4.getInt("product_id"));
					ResultSet rs8 = preparedstmnt8.executeQuery();
					if (rs8.next()) {
						allDeals.setProduct_img_url(rs8.getString("product_img_url"));
						allDeals.setItem_id(rs8.getString("item_id"));
						allDeals.setProduct_name(rs8.getString("product_name"));
						allDeals.setProduct_price(rs8.getFloat("product_price"));
						allDeals.setProduct_rating(rs8.getInt("product_rating"));
						allDeals.setProduct_description(rs8.getString("product_description"));
					}
					java.sql.Date current_date = null;
					String query3 = "SELECT CURDATE() today";
					preparedstmnt3 = connection.prepareStatement(query3);
					ResultSet rs3 = preparedstmnt3.executeQuery();
					if (rs3.next()) {
						current_date = rs3.getDate("today");
						// System.out.println(current_date);
					}

					String query9 = "SELECT DATEDIFF('" + enddate + "','" + current_date + "') days";
					preparedstmnt9 = connection.prepareStatement(query9);
					ResultSet rs9 = preparedstmnt9.executeQuery();
					if (rs9.next()) {
						int number_of_days = rs9.getInt("days");
						number_of_days++;
						allDeals.setDeal_validity(number_of_days);
					}

					productsInADeal.add(allDeals);

				}
				ProductsDealStructure dealStructure = new ProductsDealStructure(productsInADeal);
				alldealsbyname.add(dealStructure);

			}
			System.out.println(alldealsbyname.size());

		} catch (Exception e) {
			e.printStackTrace();
			;
		}

		return alldealsbyname;

	}
}
// ================================================END OF CODE====================================================//