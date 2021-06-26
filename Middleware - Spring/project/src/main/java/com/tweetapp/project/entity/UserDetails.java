package com.tweetapp.project.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@Document(collection = "UserDetails")
public class UserDetails {

	@Transient
    public static final String SEQUENCE_NAME = "users_sequence";
	
	@ApiModelProperty(notes="ID of the User", name="id", required=true)
	@Id
	private long id;
	private String firstname;
	private String lastname;
	@Indexed(unique=true)
	private String email;
	@Indexed(unique=true)
	private String loginid;
	private String password;
	private String contactnumber;
		
	public UserDetails() {
		super();
	}
	
	public UserDetails(String firstname,String lastname, String email, String password, String loginid, String contactnumber ) {
		this.firstname = firstname;
		this.lastname = lastname;
		this.email = email;
		this.loginid = loginid;
		this.password = password;
		this.contactnumber = contactnumber;
	}
	
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = (long) id;
	}

	public String getFirstname() {
		return firstname;
	}

	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}

	public String getLastname() {
		return lastname;
	}

	public void setLastname(String lastname) {
		this.lastname = lastname;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getLoginid() {
		return loginid;
	}

	public void setLoginid(String loginid) {
		this.loginid = loginid;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getContactnumber() {
		return contactnumber;
	}

	public void setContactnumber(String contactnumber) {
		this.contactnumber = contactnumber;
	}
	
}
