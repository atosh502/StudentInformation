package com.sankalpa.jsf.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.mysql.jdbc.Statement;
import com.sankalpa.jsf.beans.Student;
import com.sankalpa.jsf.util.DataConnect;

public class StudentDAO {
	
	public static Student[] getAllStudents() {
		
		Connection con = null;
		PreparedStatement ps = null;
		
		try {
			con = DataConnect.getConnection();
			ps = con.prepareStatement("select first_name, last_name, email, roll_no, "
					+ "province, district, ward_no, local_level "
					+ "from student inner join address on "
					+ "student.sid = address.student_id;");
			
			ResultSet rs = ps.executeQuery();
			
			List<Student> studentList = new ArrayList<>();
			while (rs.next()) {
				String first_name = rs.getString("first_name");
				String last_name = rs.getString("last_name");
				String email = rs.getString("email");
				String roll_no = rs.getString("roll_no");
				String district = rs.getString("district");
				String local_level = rs.getString("local_level");
				int province = rs.getInt("province");
				int ward_no = rs.getInt("ward_no");
				
				studentList.add(new Student(first_name, last_name, email, roll_no,
						province, district, ward_no, local_level));
			}
			
			return studentList.toArray(new Student[studentList.size()]);
			
		} catch (SQLException ex) {
			System.out.println("Error while loading all students information-->" + ex.getMessage());
			return new Student[] {};
		} finally {
			DataConnect.close(con);
		}
	}
	
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
