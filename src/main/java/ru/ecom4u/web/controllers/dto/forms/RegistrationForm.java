package ru.ecom4u.web.controllers.dto.forms;

import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;

public class RegistrationForm {

	@Size(min = 2, max = 32)
	private String fname;
	
	@Size(min = 1, max = 32)
	private String lname;
	
	@NotEmpty
	@Email
	private String email;
	
	@Size(min = 7, max = 32)
	private String pwd1;

	private String pwd2;
	
	private boolean distribution = true;
	private boolean acceptRules = true;

	public String getFname() {
		return fname;
	}

	public void setFname(String fname) {
		this.fname = fname;
	}

	public String getLname() {
		return lname;
	}

	public void setLname(String lname) {
		this.lname = lname;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPwd1() {
		return pwd1;
	}

	public void setPwd1(String pwd1) {
		this.pwd1 = pwd1;
	}

	public String getPwd2() {
		return pwd2;
	}

	public void setPwd2(String pwd2) {
		this.pwd2 = pwd2;
	}

	public boolean isDistribution() {
		return distribution;
	}

	public void setDistribution(boolean distribution) {
		this.distribution = distribution;
	}

	public boolean isAcceptRules() {
		return acceptRules;
	}

	public void setAcceptRules(boolean acceptRules) {
		this.acceptRules = acceptRules;
	}

}
