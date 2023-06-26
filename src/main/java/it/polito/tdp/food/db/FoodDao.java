package it.polito.tdp.food.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import it.polito.tdp.food.model.Condiment;
import it.polito.tdp.food.model.Food;
import it.polito.tdp.food.model.Portion;

public class FoodDao {
	public List<Food> listAllFoods(){
		String sql = "SELECT * FROM food" ;
		try {
			Connection conn = DBConnect.getConnection() ;

			PreparedStatement st = conn.prepareStatement(sql) ;
			
			List<Food> list = new ArrayList<>() ;
			
			ResultSet res = st.executeQuery() ;
			
			while(res.next()) {
				try {
					list.add(new Food(res.getInt("food_code"),
							res.getString("display_name")
							));
				} catch (Throwable t) {
					t.printStackTrace();
				}
			}
			
			conn.close();
			return list ;

		} catch (SQLException e) {
			e.printStackTrace();
			return null ;
		}

	}
	
	public List<Condiment> listAllCondiments(){
		String sql = "SELECT * FROM condiment" ;
		try {
			Connection conn = DBConnect.getConnection() ;

			PreparedStatement st = conn.prepareStatement(sql) ;
			
			List<Condiment> list = new ArrayList<>() ;
			
			ResultSet res = st.executeQuery() ;
			
			while(res.next()) {
				try {
					list.add(new Condiment(res.getInt("condiment_code"),
							res.getString("display_name"),
							res.getDouble("condiment_calories"), 
							res.getDouble("condiment_saturated_fats")
							));
				} catch (Throwable t) {
					t.printStackTrace();
				}
			}
			
			conn.close();
			return list ;

		} catch (SQLException e) {
			e.printStackTrace();
			return null ;
		}
	}
	
	public List<Portion> listAllPortions(){
		String sql = "SELECT * FROM portion" ;
		try {
			Connection conn = DBConnect.getConnection() ;

			PreparedStatement st = conn.prepareStatement(sql) ;
			
			List<Portion> list = new ArrayList<>() ;
			
			ResultSet res = st.executeQuery() ;
			
			while(res.next()) {
				try {
					list.add(new Portion(res.getInt("portion_id"),
							res.getDouble("portion_amount"),
							res.getString("portion_display_name"), 
							res.getDouble("calories"),
							res.getDouble("saturated_fats"),
							res.getInt("food_code")
							));
				} catch (Throwable t) {
					t.printStackTrace();
				}
			}
			
			conn.close();
			return list ;

		} catch (SQLException e) {
			e.printStackTrace();
			return null ;
		}

	}
	
	public List<String> getVertex(int c){
		
		String sql = "SELECT DISTINCT portion_display_name "
				+ "FROM portionn "
				+ "WHERE calories<?";
		
		try {
			Connection conn = DBConnect.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			st.setInt(1, c);
			ResultSet res = st.executeQuery();
			
			List<String> tipi = new LinkedList<>();
			
			while(res.next()) {
				tipi.add(res.getString("portion_display_name"));
			}
			
			conn.close();
			return tipi;
		}catch (SQLException e) {
			e.printStackTrace();
			return null ;
		}
	}
	
	public int getPeso(String s1, String s2, Connection conn) {
		
		String sql = "SELECT count(DISTINCT food_code) AS peso "
				+ "FROM portionn "
				+ "WHERE portion_display_name=? OR portion_display_name=?";
		
		try {
			//Connection conn = DBConnect.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			st.setString(1, s1);
			st.setString(2, s2);
			ResultSet res = st.executeQuery();
			
			res.first();
			
			int i = res.getInt("peso");
			
			conn.close();
			
			if(i>0) {
				return i;
			}else {
				return 0;
			}
			
		}catch (SQLException e) {
			e.printStackTrace();
			return 0 ;
		}
	}
	
	public int CiboinComune(String s1, String s2) {
		
		String sql = "SELECT COUNT(*) as c "
				+ "FROM portionn p1, portionn p2 "
				+ "WHERE p1.portion_display_name=? AND p2.portion_display_name=? AND p1.food_code=p2.food_code";
		
		try {
			Connection conn = DBConnect.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			st.setString(1, s1);
			st.setString(2, s2);
			ResultSet res = st.executeQuery();
			
			res.first();
			
			int i = res.getInt("c");
			
			//conn.close();
			
			if(i>0) {
				return this.getPeso(s1, s2, conn);
			}else {
				conn.close();
				return 0;
			}
		}catch (SQLException e) {
			e.printStackTrace();
			return 0 ;
		}
	}
	
	

}
