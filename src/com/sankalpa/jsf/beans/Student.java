package com.sankalpa.jsf.beans;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import com.sankalpa.jsf.dao.StudentDAO;

@ManagedBean
@ViewScoped
public class Student implements Serializable{

	private static final long serialVersionUID = 5976281625639035872L;
	
	private String first_name;
	private String last_name;
	private String email;
	private String roll_no;
	
	private int province;
	private String district;
	private int ward_no;
	private String local_level;

	public Student() {}
	
	public Student(String first_name, String last_name, String email, String roll_no, int province, String district,
			int ward_no, String local_level) {
		super();
		this.first_name = first_name;
		this.last_name = last_name;
		this.email = email;
		this.roll_no = roll_no;
		this.province = province;
		this.district = district;
		this.ward_no = ward_no;
		this.local_level = local_level;
	}
	public int getProvince() {
		return province;
	}
	public void setProvince(int province) {
		this.province = province;
	}
	public String getDistrict() {
		return district;
	}
	public void setDistrict(String district) {
		this.district = district;
	}
	public int getWard_no() {
		return ward_no;
	}
	public void setWard_no(int ward_no) {
		this.ward_no = ward_no;
	}
	public String getLocal_level() {
		return local_level;
	}
	public void setLocal_level(String local_level) {
		this.local_level = local_level;
	}
	public String getFirst_name() {
		return first_name;
	}
	public void setFirst_name(String first_name) {
		this.first_name = first_name;
	}
	public String getLast_name() {
		return last_name;
	}
	public void setLast_name(String last_name) {
		this.last_name = last_name;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getRoll_no() {
		return roll_no;
	}
	public void setRoll_no(String roll_no) {
		this.roll_no = roll_no;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	public String addNewStudent() {
		return "student";
	}

	public String saveStudentInformation() {
		
		// if save successful else stay at student.xhtml
		return "admin";
	}

	
	private List<String> districtList;
	
	public List<String> getDistrictList() {
		return districtList;
	}
	
	public void loadDistricts() {
		this.districtList = AdministrativeLevels.getDistricts(province);
	}
	
	private List<String> localLevelList;
	
	public List<String> getLocalLevelList(){
		return localLevelList;
	}
	
	public void loadLocalLevels() {
		this.localLevelList = AdministrativeLevels.getLocalLevels(district);
	}
	

	public String submit() {
		
		boolean submitted = StudentDAO.submit(first_name, last_name, email, roll_no, province, district, ward_no,
				local_level);
		
		if (submitted) {
			return "admin";
		} else {
			FacesContext.getCurrentInstance().addMessage(
					null,
					new FacesMessage(FacesMessage.SEVERITY_WARN,
							"Incorrect Information ",
							"Please enter correct student information and address"));
			return "student";
		}
	}
	
	private Student[] allStudents;
	
	public void setAllStudents(Student[] students) {
		this.allStudents = students;
	}

	public Student[] getAllStudents() {
		return allStudents;
	}
	
	@PostConstruct
	public void init() {
		
		Student[] students = StudentDAO.getAllStudents();
		setAllStudents(students);
	}

	@Override
	public String toString() {
		return "Student [first_name=" + first_name + ", last_name=" + last_name + ", email=" + email + ", roll_no="
				+ roll_no + ", province=" + province + ", district=" + district + ", ward_no=" + ward_no
				+ ", local_level=" + local_level + "]";
	}

}
