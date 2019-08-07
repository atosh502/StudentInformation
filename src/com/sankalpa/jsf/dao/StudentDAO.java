package com.sankalpa.jsf.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.mysql.jdbc.Statement;
import com.sankalpa.jsf.util.DataConnect;

public class StudentDAO {
	
	public static boolean submit(String first_name, String last_name, String email, String roll_no,
			int province, String district, int ward_no, String local_level) {
		
		Connection con = null;
		PreparedStatement ps = null;
		
		try {
			
			con = DataConnect.getConnection();
			ps = con.prepareStatement("insert into student(first_name, last_name, email, roll_no) "
					+ "values(?, ?, ?, ?);", Statement.RETURN_GENERATED_KEYS);
			ps.setString(1, first_name);
			ps.setString(2, last_name);
			ps.setString(3, email);
			ps.setString(4, roll_no);
			
			int affectedRows = ps.executeUpdate();
			int student_id;
			
			if (affectedRows == 1) {
				
				// query executed successfully
				try (ResultSet generatedKeys = ps.getGeneratedKeys()) {
		            if (generatedKeys.next()) {
		                student_id = generatedKeys.getInt(1);
		            }
		            else {
		                throw new SQLException("Creating student failed, no ID obtained.");
		            }
		        }
				
				ps = con.prepareStatement("insert into address(province, district, ward_no, local_level, student_id) "
						+ "values(?, ?, ?, ?, ?);");
				ps.setInt(1, province);
				ps.setString(2, district);
				ps.setInt(3, ward_no);
				ps.setString(4, local_level);
				ps.setInt(5, student_id);
				
				affectedRows = ps.executeUpdate();
				
				if(affectedRows == 1) {
					return true;
				}
			}
			
		} catch (SQLException ex) {
			System.out.println("Error while saving student information-->" + ex.getMessage());
			return false;
		} finally {
			DataConnect.close(con);
		}
		return false;
	}

}
