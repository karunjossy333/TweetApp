package com.tweetapp.project.entity;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;

//@Getter
//@Setter
//@ToString
//@Document(collection = "UserDetails")

@DynamoDBTable(tableName="user_details")
public class UserDetails {

//	@Transient
//    public static final String SEQUENCE_NAME = "users_sequence";
	
//	@ApiModelProperty(notes="ID of the User", name="id", required=true)
//	@Id
	@DynamoDBAttribute
	private long id;

	@DynamoDBAttribute
	private String firstname;
	@DynamoDBAttribute
	private String lastname;
//	@Indexed(unique=true)
	@DynamoDBAttribute
	private String email;
//	@Indexed(unique=true)
	@DynamoDBHashKey
	private String loginid;
	@DynamoDBAttribute
	private String password;
	@DynamoDBAttribute
	private String contactnumber;
		
	public UserDetails() {
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
		this.id = id;
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
