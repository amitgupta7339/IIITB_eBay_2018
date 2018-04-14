package org.ebay_project.ebaytester.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.ebay_project.ebaytester.model.Message;
import org.ebay_project.ebaytester.model.Product;
import org.ebay_project.ebaytester.model.Product_desc;

public class ProductService {

	Connection conn;

	public ProductService() {

		try {
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/ebaytest", "root", "root");
			System.out.println("connection !");
		} catch (Exception e) {
			System.out.println("Exception found" + e);
			try {
				conn.close();
			} catch (Exception ee) {
				System.out.println("Connection close error");
			}
		}
	}
// ===========================================PRODUCT COMPARISON =================================================//
	
		public List<Product> getComparedProduct(String product1,String product2)
		{
			Product p1 ;
			List<Product> list = new ArrayList<>();
			PreparedStatement preparedstmnt;
			String[] arr= {product1,product2};
			try
			{
				for(int i=0;i<2;i++)
				{
				String query="select * from product where product_name = ?";
				preparedstmnt=	(PreparedStatement) conn.prepareStatement(query);
				preparedstmnt.setString(1, arr[i]);
				ResultSet rs = preparedstmnt.executeQuery();
				rs = preparedstmnt.executeQuery();

				while (rs.next()) {
					p1= new Product();
					p1.setProduct_id(rs.getInt(1));
					p1.setSub_category_id(rs.getInt(2));
					p1.setCategory_id(rs.getInt(3));
					p1.setUser_id(rs.getInt(4));
					p1.setProduct_name(rs.getString(5));				
					p1.setProduct_price(rs.getInt(6));
					p1.setProduct_discount(rs.getInt(7));
					p1.setProduct_condition(rs.getString(8));
					p1.setProduct_shipping(rs.getString(9));
					p1.setProduct_sold_quantity(rs.getInt(10));
					p1.setProduct_img_url(rs.getString(11));
					p1.setProduct_available_quantity(rs.getInt(12));
					p1.setProduct_description(rs.getString(13));
					p1.setProduct_rating(rs.getInt(14));
					p1.setItem_id(rs.getString(15));
					p1.setBrand(rs.getString(16));
					p1.setColor(rs.getString(17));
					p1.setScreen_size(rs.getString(18));
					p1.setProcessor(rs.getString(19));
					p1.setStorage(rs.getString(20));
					p1.setWarranty(rs.getString(21));
					p1.setOperating_system(rs.getString(22));
					p1.setProduct_year(rs.getInt(23));
					p1.setGender(rs.getString(24));
					p1.setApplicable(rs.getString(25));
					p1.setMaterial(rs.getString(26));
					p1.setClothing_size(rs.getString(27));
					p1.setStyle(rs.getString(28));
					p1.setWarranty_type(rs.getString(29));
					p1.setCard_class(rs.getString(30));
					
					list.add(p1);
					}
				}
				System.out.println(list);
			} catch (Exception e) {
				System.out.println(e);
			}
			
			return list;
		}
		
// ===============================================================================================================//		
		public List<Product> getProductList(String subCategoryName)
		{
			Product p1 ;
			List<Product> list = new ArrayList<>();
			PreparedStatement preparedstmnt;
			try
			{
				String query="select sub_category_id from sub_category where sub_category_name = ?";
				preparedstmnt=	(PreparedStatement) conn.prepareStatement(query);
				preparedstmnt.setString(1, subCategoryName);
				ResultSet rs = preparedstmnt.executeQuery();
				int sub_category_id=0;
				if(rs.next())
					sub_category_id=rs.getInt(1);
				query="select * from product where sub_category_id = ?";
				preparedstmnt=	(PreparedStatement) conn.prepareStatement(query);
				preparedstmnt.setInt(1, sub_category_id);
				rs = preparedstmnt.executeQuery();

				while (rs.next()) {
					p1= new Product();
					p1.setSub_category_id(rs.getInt(2));
					p1.setCategory_id(rs.getInt(3));
					p1.setUser_id(rs.getInt(4));
					p1.setProduct_name(rs.getString(5));				
					p1.setProduct_price(rs.getInt(6));
					p1.setProduct_discount(rs.getInt(7));
					p1.setProduct_condition(rs.getString(8));
					p1.setProduct_shipping(rs.getString(9));
					p1.setProduct_sold_quantity(rs.getInt(10));
					p1.setProduct_img_url(rs.getString(11));
					p1.setProduct_available_quantity(rs.getInt(12));
					p1.setProduct_description(rs.getString(13));
					p1.setProduct_rating(rs.getInt(14));
					p1.setItem_id(rs.getString(15));
					p1.setBrand(rs.getString(16));
					p1.setColor(rs.getString(17));
					p1.setScreen_size(rs.getString(18));
					p1.setProcessor(rs.getString(19));
					p1.setStorage(rs.getString(20));
					p1.setWarranty(rs.getString(21));
					p1.setOperating_system(rs.getString(22));
					p1.setProduct_year(rs.getInt(23));
					p1.setGender(rs.getString(24));
					p1.setApplicable(rs.getString(25));
					p1.setMaterial(rs.getString(26));
					p1.setClothing_size(rs.getString(27));
					p1.setStyle(rs.getString(28));
					p1.setWarranty_type(rs.getString(29));
					p1.setCard_class(rs.getString(30));
					
					list.add(p1);
				}
			} catch (Exception e) {
				System.out.println(e);
			}
			
			return list;
		}
// ===========================================PRODUCT DESCRIPTION PAGE============================================//
	public Product_desc getProductById(int prod_id) {
		Product_desc product = new Product_desc();
		try {
			PreparedStatement stmt = conn.prepareStatement("select * from product where product_id = ?");
			System.out.println(prod_id);
			stmt.setInt(1, prod_id);
			ResultSet rs = stmt.executeQuery();
			rs.next();
			System.out.println(rs.getInt(1));
			product.setProduct_id(rs.getInt(1));
			product.setCategory_id(rs.getInt(3));
			product.setSub_category_id(rs.getInt(2));
			product.setSeller_id(rs.getInt(4));
			stmt = conn.prepareStatement("select * from user where user_id = ?");
			stmt.setInt(1, rs.getInt(4));
			ResultSet rs2 = stmt.executeQuery();
			if (rs2.next()) {
				product.setSeller_name(rs2.getString(2) + " " + rs2.getString(3));
				product.setSeller_email(rs2.getString(4));
				product.setSeller_country(rs2.getString(6));
				product.setSeller_state(rs2.getString(9));
				product.setSeller_city(rs2.getString(8));
				product.setSeller_address(rs2.getString(7));
				product.setSeller_contact(rs2.getString(11));
			}
			product.setProduct_name(rs.getString(5));
			product.setProduct_price(rs.getFloat(6));
			product.setProduct_discount(rs.getInt(7));
			product.setProduct_condition(rs.getString(8));
			product.setProduct_shipping(rs.getString(9));
			product.setProduct_sold_quantity(rs.getInt(10));
			product.setProduct_img_url(rs.getString(11));
			product.setProduct_available_quantity(rs.getInt(12));
			product.setProduct_description(rs.getString(13));
			product.setRating(rs.getInt(14));
			product.setItem_id(rs.getString(15));
			product.setColor(rs.getString(17));
			product.setBrand(rs.getString(16));
			product.setScreen_Size(rs.getString(18));
			product.setProcessor(rs.getString(19));
			product.setStorage(rs.getString(20));
			product.setWarranty(rs.getString(21));
			product.setOS(rs.getString(22));
			product.setYear(rs.getInt(23));
			product.setGender(rs.getString(24));
			product.setApplicable(rs.getString(25));
			product.setMaterial(rs.getString(26));
			product.setSize(rs.getString(27));
			product.setStyle(rs.getString(28));
			product.setWarranty_Type(rs.getString(29));
			product.setCard_Class(rs.getString(30));

			System.out.println(rs.getString(12));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return product;
	}
// ============================================GET ALL PRODUCTS==================================================//
	public List<Product> getAllProducts() {
		Product p1;
		List<Product> list = new ArrayList<>();
		PreparedStatement preparedstmnt;
		try {
			String query = "select * from product";
			preparedstmnt = (PreparedStatement) conn.prepareStatement(query);
			ResultSet rs = preparedstmnt.executeQuery();

			while (rs.next()) {
				p1 = new Product();
				p1.setProduct_id(rs.getInt(1));
				p1.setSub_category_id(rs.getInt(2));
				p1.setCategory_id(rs.getInt(3));
				p1.setUser_id(rs.getInt(4));
				p1.setProduct_name(rs.getString(5));
				p1.setProduct_price(rs.getInt(6));
				p1.setProduct_discount(rs.getInt(7));
				p1.setProduct_condition(rs.getString(8));
				p1.setProduct_shipping(rs.getString(9));
				p1.setProduct_sold_quantity(rs.getInt(10));
				p1.setProduct_img_url(rs.getString(11));
				p1.setProduct_available_quantity(rs.getInt(12));
				p1.setProduct_description(rs.getString(13));
				p1.setProduct_rating(rs.getInt(14));
				p1.setItem_id(rs.getString(15));
				p1.setBrand(rs.getString(16));
				p1.setColor(rs.getString(17));
				p1.setScreen_size(rs.getString(18));
				p1.setProcessor(rs.getString(19));
				p1.setStorage(rs.getString(20));
				p1.setWarranty(rs.getString(21));
				p1.setOperating_system(rs.getString(22));
				p1.setProduct_year(rs.getInt(23));
				p1.setGender(rs.getString(24));
				p1.setApplicable(rs.getString(25));
				p1.setMaterial(rs.getString(26));
				p1.setClothing_size(rs.getString(27));
				p1.setStyle(rs.getString(28));
				p1.setWarranty_type(rs.getString(29));
				p1.setCard_class(rs.getString(30));

				list.add(p1);
			}
		} catch (Exception e) {
			System.out.println(e);
		}
		return list;
	}
// ===========================================END OF CODE GET ALL PRODUCTS=======================================//
//	public List<Product> dealImagesList() {
//		Product p1;
//		List<Product> list = new ArrayList<>();
//		PreparedStatement preparedstmnt;
//		try {
//			String query = "SELECT a.* FROM product AS a,(SELECT MIN(product_id)AS product_id,deal FROM product GROUP BY deal)AS b where a.product_id = b.product_id";
//			preparedstmnt = (PreparedStatement) conn.prepareStatement(query);
//			ResultSet rs = preparedstmnt.executeQuery();
//			while (rs.next()) {
//				p1 = new Product();
//				p1.setProduct_id(rs.getInt(1));
//				p1.setSub_category_id(rs.getInt(2));
//				p1.setCategory_id(rs.getInt(3));
//				p1.setUser_id(rs.getInt(4));
//				p1.setProduct_name(rs.getString(5));
//				p1.setProduct_price(rs.getInt(6));
//				p1.setProduct_discount(rs.getInt(7));
//				p1.setProduct_condition(rs.getString(8));
//				p1.setProduct_shipping(rs.getString(9));
//				p1.setProduct_sold_quantity(rs.getInt(10));
//				p1.setProduct_img_url(rs.getString(11));
//				p1.setProduct_available_quantity(rs.getInt(12));
//				p1.setProduct_description(rs.getString(13));
//				p1.setProduct_rating(rs.getInt(14));
//				p1.setItem_id(rs.getString(15));
//				p1.setBrand(rs.getString(16));
//				p1.setColor(rs.getString(17));
//				p1.setScreen_size(rs.getString(18));
//				p1.setProcessor(rs.getString(19));
//				p1.setStorage(rs.getString(20));
//				p1.setWarranty(rs.getString(21));
//				p1.setOperating_system(rs.getString(22));
//				p1.setProduct_year(rs.getInt(23));
//				p1.setGender(rs.getString(24));
//				p1.setApplicable(rs.getString(25));
//				p1.setMaterial(rs.getString(26));
//				p1.setClothing_size(rs.getString(27));
//				p1.setStyle(rs.getString(28));
//				p1.setWarranty_type(rs.getString(29));
//				p1.setCard_class(rs.getString(30));
//
//				list.add(p1);
//			}
//		} catch (Exception e) {
//			System.out.println(e);
//		}
//		return list;
//	}
// ===========================================GET ALL PRODUCT BASES ON SELLER ID=================================//
	public List<Product> getSellerAllProducts(int user_id) {
		Product p1;
		List<Product> list = new ArrayList<>();
		PreparedStatement preparedstmnt;
		try {
			String query = "select * from product where user_id = ?";
			preparedstmnt = (PreparedStatement) conn.prepareStatement(query);
			preparedstmnt.setInt(1, user_id);
			ResultSet rs = preparedstmnt.executeQuery();

			while (rs.next()) {
				p1 = new Product();
				p1.setProduct_id(rs.getInt(1));
				p1.setSub_category_id(rs.getInt(2));
				p1.setCategory_id(rs.getInt(3));
				p1.setUser_id(rs.getInt(4));
				p1.setProduct_name(rs.getString(5));
				p1.setProduct_price(rs.getInt(6));
				p1.setProduct_discount(rs.getInt(7));
				p1.setProduct_condition(rs.getString(8));
				p1.setProduct_shipping(rs.getString(9));
				p1.setProduct_sold_quantity(rs.getInt(10));
				p1.setProduct_img_url(rs.getString(11));
				p1.setProduct_available_quantity(rs.getInt(12));
				p1.setProduct_description(rs.getString(13));
				p1.setProduct_rating(rs.getInt(14));
				p1.setItem_id(rs.getString(15));
				p1.setBrand(rs.getString(16));
				p1.setColor(rs.getString(17));
				p1.setScreen_size(rs.getString(18));
				p1.setProcessor(rs.getString(19));
				p1.setStorage(rs.getString(20));
				p1.setWarranty(rs.getString(21));
				p1.setOperating_system(rs.getString(22));
				p1.setProduct_year(rs.getInt(23));
				p1.setGender(rs.getString(24));
				p1.setApplicable(rs.getString(25));
				p1.setMaterial(rs.getString(26));
				p1.setClothing_size(rs.getString(27));
				p1.setStyle(rs.getString(28));
				p1.setWarranty_type(rs.getString(29));
				p1.setCard_class(rs.getString(30));

				list.add(p1);
			}
		} catch (Exception e) {
			System.out.println(e);
		}
		return list;
	}
// ===============================END OF CODE GET ALL PRODUCT BASES ON SELLER ID=================================//

// ==========================================PRODUCT DETAILS ON PRODUCT ID=======================================//
	public Product getProductDetail(int product_id) {
		Product p1 = new Product();
		PreparedStatement preparedstmnt;
		try {
			String query = "SELECT * FROM product WHERE product_id =? ";
			preparedstmnt = (PreparedStatement) conn.prepareStatement(query);
			preparedstmnt.setInt(1, product_id);
			ResultSet rs = preparedstmnt.executeQuery();
			while (rs.next()) {
				p1.setSub_category_id(rs.getInt(2));
				p1.setCategory_id(rs.getInt(3));
				p1.setUser_id(rs.getInt(4));
				p1.setProduct_name(rs.getString(5));
				p1.setProduct_price(rs.getInt(6));
				p1.setProduct_discount(rs.getInt(7));
				p1.setProduct_condition(rs.getString(8));
				p1.setProduct_shipping(rs.getString(9));
				p1.setProduct_sold_quantity(rs.getInt(10));
				p1.setProduct_img_url(rs.getString(11));
				p1.setProduct_available_quantity(rs.getInt(12));
				p1.setProduct_description(rs.getString(13));
				p1.setProduct_rating(rs.getInt(14));
				p1.setItem_id(rs.getString(15));
				p1.setBrand(rs.getString(16));
				p1.setColor(rs.getString(17));
				p1.setScreen_size(rs.getString(18));
				p1.setProcessor(rs.getString(19));
				p1.setStorage(rs.getString(20));
				p1.setWarranty(rs.getString(21));
				p1.setOperating_system(rs.getString(22));
				p1.setProduct_year(rs.getInt(23));
				p1.setGender(rs.getString(24));
				p1.setApplicable(rs.getString(25));
				p1.setMaterial(rs.getString(26));
				p1.setClothing_size(rs.getString(27));
				p1.setStyle(rs.getString(28));
				p1.setWarranty_type(rs.getString(29));
				p1.setCard_class(rs.getString(30));
			}
		} catch (Exception e) {
			System.out.println(e);
		}
		return p1;
	}
// ===============================END OF CODE OF PRODUCT DETAILS ON PRODUCT ID===================================//

// =======================================GET SINGLE PRODUCT BY SELLER===========================================//
	public Product getSellerProduct(int user_id, String product_name) {
		Product p1 = new Product();
		PreparedStatement preparedstmnt;
		try {
			String query = "select * from product where user_id =? and product_name =?";
			preparedstmnt = (PreparedStatement) conn.prepareStatement(query);
			preparedstmnt.setInt(1, user_id);
			preparedstmnt.setString(2, product_name);
			ResultSet rs = preparedstmnt.executeQuery();

			while (rs.next()) {
				p1.setSub_category_id(rs.getInt(2));
				p1.setCategory_id(rs.getInt(3));
				p1.setUser_id(rs.getInt(4));
				p1.setProduct_name(rs.getString(5));
				p1.setProduct_price(rs.getInt(6));
				p1.setProduct_discount(rs.getInt(7));
				p1.setProduct_condition(rs.getString(8));
				p1.setProduct_shipping(rs.getString(9));
				p1.setProduct_sold_quantity(rs.getInt(10));
				p1.setProduct_img_url(rs.getString(11));
				p1.setProduct_available_quantity(rs.getInt(12));
				p1.setProduct_description(rs.getString(13));
				p1.setProduct_rating(rs.getInt(14));
				p1.setItem_id(rs.getString(15));
				p1.setBrand(rs.getString(16));
				p1.setColor(rs.getString(17));
				p1.setScreen_size(rs.getString(18));
				p1.setProcessor(rs.getString(19));
				p1.setStorage(rs.getString(20));
				p1.setWarranty(rs.getString(21));
				p1.setOperating_system(rs.getString(22));
				p1.setProduct_year(rs.getInt(23));
				p1.setGender(rs.getString(24));
				p1.setApplicable(rs.getString(25));
				p1.setMaterial(rs.getString(26));
				p1.setClothing_size(rs.getString(27));
				p1.setStyle(rs.getString(28));
				p1.setWarranty_type(rs.getString(29));
				p1.setCard_class(rs.getString(30));
			}
		} catch (Exception e) {
			System.out.println(e);
		}
		return p1;
	}
// ==================================END OF CODE GET SINGLE PRODUCT BY SELLER====================================//
// =========================================new product upload===================================================//
	public Product setProductInfo(String product_name, String category, String subcategory, int seller_id,
			int product_price, int quantity, String product_condition, String product_shipping,
			String product_description, int product_discount, String item_id, String brand, String color,
			String screen_size, String processor, String storage, String warranty, String operating_system,
			int product_year, String gender, String warranty_type, String applicable, String material,
			String clothing_size, String style, String card_class) {
		int product_sold_quantity = 0, product_available_quantity = quantity, product_rating = 0;
		String product_img_url = "";
		PreparedStatement preparedstmnt;
		ResultSet rs;
		try {
			String query = "select category_id from category where category_name=?";
			preparedstmnt = (PreparedStatement) conn.prepareStatement(query);
			preparedstmnt.setString(1, category);
			rs = preparedstmnt.executeQuery();
			int category_id = 0;
			if (rs.next())
				category_id = rs.getInt("category_id");

			query = "select sub_category_id from sub_category where sub_category_name=?";
			preparedstmnt = (PreparedStatement) conn.prepareStatement(query);
			preparedstmnt.setString(1, subcategory);
			rs = preparedstmnt.executeQuery();
			int sub_category_id = 0;
			if (rs.next())
				sub_category_id = rs.getInt("sub_category_id");
			item_id = String.valueOf(seller_id) + String.valueOf(category_id) + String.valueOf(sub_category_id)
					+ item_id;
			query = "insert into product(sub_category_id,category_id,user_id,product_name,product_price,product_discount,product_condition,product_shipping, product_sold_quantity, product_img_url, product_available_quantity, product_description,product_rating, item_id, brand, color, screen_size,processor, storage, warranty, operating_system, product_year, gender, applicable, material, clothing_size, style, warranty_type, card_class) values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?);";
			preparedstmnt = (PreparedStatement) conn.prepareStatement(query);
			preparedstmnt.setInt(1, sub_category_id);
			preparedstmnt.setInt(2, category_id);
			preparedstmnt.setInt(3, seller_id);
			preparedstmnt.setString(4, product_name);
			preparedstmnt.setInt(5, product_price);
			preparedstmnt.setInt(6, product_discount);
			preparedstmnt.setString(7, product_condition);
			preparedstmnt.setString(8, product_shipping);
			preparedstmnt.setInt(9, product_sold_quantity); // product_sold_quantity
			preparedstmnt.setString(10, product_img_url);
			preparedstmnt.setInt(11, quantity);
			preparedstmnt.setString(12, product_description);
			preparedstmnt.setInt(13, product_rating);
			preparedstmnt.setString(14, item_id);
			preparedstmnt.setString(15, brand);
			preparedstmnt.setString(16, color);
			preparedstmnt.setString(17, screen_size);
			preparedstmnt.setString(18, processor);
			preparedstmnt.setString(19, storage);
			preparedstmnt.setString(20, warranty);
			preparedstmnt.setString(21, operating_system);
			preparedstmnt.setInt(22, product_year);
			preparedstmnt.setString(23, gender);
			preparedstmnt.setString(24, applicable);
			preparedstmnt.setString(25, material);
			preparedstmnt.setString(26, clothing_size);
			preparedstmnt.setString(27, style);
			preparedstmnt.setString(28, warranty_type);
			preparedstmnt.setString(29, card_class);

			preparedstmnt.execute();

			query = "select * from product where product_name =? AND user_id=? ";
			preparedstmnt = (PreparedStatement) conn.prepareStatement(query);
			preparedstmnt.setString(1, product_name);
			preparedstmnt.setInt(2, seller_id);
			rs = preparedstmnt.executeQuery();
			int product_id = 0;
			if (rs.next())
				product_id = rs.getInt("product_id");

			Product product = new Product(product_id, sub_category_id, sub_category_id, seller_id, product_price,
					product_discount, product_sold_quantity, product_available_quantity, product_rating, product_year,
					product_name, product_condition, product_shipping, product_img_url, product_description, brand,
					color, screen_size, processor, storage, warranty, operating_system, gender, applicable, material,
					clothing_size, style, warranty_type, card_class);
			System.out.println(product_id);
			product.setItem_id(rs.getString("item_id"));
			new File(PathSetup.imagePath + "products/" + product_id + "/images").mkdirs();
			// new ProductService().Upload()

			InputStream fileInputStream = null;
			OutputStream outputStream = null;
			String path = PathSetup.imagePath + "products/" + product_id + "/";

			try {
				fileInputStream = new FileInputStream(PathSetup.imagePath + "defaultProductPic.jpg");
				outputStream = new FileOutputStream(new File(path + "productPic.jpg"));
				int read = 0;
				byte[] bytes = new byte[1024];
				while ((read = fileInputStream.read(bytes)) != -1) {
					outputStream.write(bytes, 0, read);
				}
				product.setProduct_img_url("productPic.jpg");
				outputStream.close();
				fileInputStream.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
			return product;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
// =====================================end code of upload product===============================================//

// =====================================modify product details===================================================//
	public String updateSellerProduct(String original_product_name, String product_name, int user_id, int product_price,
			int quantity, String product_condition, String product_shipping, String product_description,
			int product_discount, String brand, String color, String screen_size, String processor, String storage,
			String warranty, String operating_system, int product_year, String gender, String warranty_type,
			String applicable, String material, String clothing_size, String style, String card_class) {

		// int status=0;
		PreparedStatement preparedstmnt;
		int rs = 0;
		try {
			String query = "UPDATE product set product_name = ? , product_price = ?, " + "product_available_quantity=?,"
					+ "product_condition=?,product_shipping=?,product_description=?,"
					+ "product_discount=?,brand=?, color=?, screen_size=?,processor=?,storage=?,warranty=?,"
					+ "operating_system=?, product_year=?, gender=?, warranty_type=?,applicable=?,material=?,"
					+ "clothing_size=?, style=?,card_class=? where user_id = ? and product_name = ?";
			preparedstmnt = (PreparedStatement) conn.prepareStatement(query);
			preparedstmnt = (PreparedStatement) conn.prepareStatement(query);
			preparedstmnt.setString(1, product_name);
			preparedstmnt.setInt(2, product_price);
			preparedstmnt.setInt(3, quantity);
			preparedstmnt.setString(4, product_condition);
			preparedstmnt.setString(5, product_shipping);
			preparedstmnt.setString(6, product_description);
			preparedstmnt.setInt(7, product_discount);
			preparedstmnt.setString(8, brand);
			preparedstmnt.setString(9, color);
			preparedstmnt.setString(10, screen_size);
			preparedstmnt.setString(11, processor);
			preparedstmnt.setString(12, storage);
			preparedstmnt.setString(13, warranty);
			preparedstmnt.setString(14, operating_system);
			preparedstmnt.setInt(15, product_year);
			preparedstmnt.setString(16, gender);
			preparedstmnt.setString(17, warranty_type);
			preparedstmnt.setString(18, applicable);
			preparedstmnt.setString(19, material);
			preparedstmnt.setString(20, clothing_size);
			preparedstmnt.setString(21, style);
			preparedstmnt.setString(22, card_class);
			preparedstmnt.setInt(23, user_id);
			preparedstmnt.setString(24, original_product_name);
			rs = preparedstmnt.executeUpdate();

		} catch (Exception e) {
			System.out.println(e);
		}
		if (rs > 0) {
			return "Succesfull";
		}
		return "unsuccessfull";
	}
// =====================================END OF CODE MODIFY=======================================================//

// ======================================DELETE PRODUCT==========================================================//
	public Message DeleteProduct(String product_name, int seller_id) {
		PreparedStatement preparedstmnt;
		Message mssg;
		try {
			String query = "delete from product where product_name=? and user_id=?";
			preparedstmnt = (PreparedStatement) conn.prepareStatement(query);
			preparedstmnt.setString(1, product_name);
			preparedstmnt.setInt(2, seller_id);
			int rs = preparedstmnt.executeUpdate();
			if (rs > 0) {
				mssg = new Message("Success");

			} else {
				mssg = new Message("Failure");
			}
			System.out.println(mssg.getMessage());
			return mssg;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
// ======================================END OF CODE OF DELETE PRODUCT===========================================//

// ========================================UPLOAD PRODUCT IMAGE==================================================//
	@SuppressWarnings("finally")
	public String uploadProductPic(InputStream fileInputStream, String fileName, int id) {
		System.out.println("now uploading the product");
		System.out.println(id);
		int product_id = id;
		PreparedStatement preparedStatement3;
		File folder = new File(PathSetup.imagePath + "products/" + product_id);
		File[] listOfFiles = folder.listFiles();
		listOfFiles[0].delete();
		// System.out.println(userName+" "+listOfFiles[1].getName()+" profilepic to
		// delete");

		// System.out.println(f.delete());
		OutputStream outputStream = null;
		OutputStream outputStream1 = null;
		fileName = "" + Calendar.getInstance().getTimeInMillis() + fileName;
		String path = PathSetup.imagePath + "products/" + product_id + "/images/";
		String productPicPath = PathSetup.imagePath + "products/" + product_id + "/";
		String databaseimagepath = "http://localhost:5224/ebaytester/products/" + product_id + "/";
		Product product = new Product();
		try {
			outputStream = new FileOutputStream(new File(path + fileName));
			outputStream1 = new FileOutputStream(new File(productPicPath + "p" + fileName));
			String query = "update product set product_img_url = ? where product_id = ?";
			preparedStatement3 = conn.prepareStatement(query);
			preparedStatement3.setString(1, databaseimagepath + "p" + fileName);
			preparedStatement3.setInt(2, product_id);
			preparedStatement3.executeUpdate();
			int read = 0;
			byte[] bytes = new byte[1024];
			while ((read = fileInputStream.read(bytes)) != -1) {
				outputStream.write(bytes, 0, read);
				outputStream1.write(bytes, 0, read);
			}
			outputStream.close();
			outputStream1.close();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (outputStream != null) {
				product.setProduct_img_url(productPicPath + "p" + fileName);
				System.out.println(product.getProduct_img_url() + "........................................says hello");
				return product.getProduct_img_url();
			}
			return null;
		}
	}
// ================================END OF CODE OF UPLOAD PRODUCT IMAGE===========================================//

// ==================================CHECK PRODUCT ID VALID OR NOT===============================================//
	public int ValidateProductId(int product_id) {
		try {

			PreparedStatement stmt = conn.prepareStatement("Select product_id from product_info where product_id = ?");
			stmt.setInt(1, product_id);

			ResultSet rs = stmt.executeQuery();
			if (rs.next()) {
				return 1;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 0;
	}
}
//=============================================END OF CODE======================================================//
