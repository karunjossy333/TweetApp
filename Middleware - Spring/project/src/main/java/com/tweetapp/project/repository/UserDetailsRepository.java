package com.tweetapp.project.repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import com.amazonaws.services.dynamodbv2.document.Item;
import com.amazonaws.services.dynamodbv2.document.ItemCollection;
import com.amazonaws.services.dynamodbv2.document.Table;
import com.amazonaws.services.dynamodbv2.document.spec.QuerySpec;
import com.amazonaws.services.dynamodbv2.document.spec.ScanSpec;
import com.tweetapp.project.configuration.DynamoDBConfig;
import com.tweetapp.project.entity.UserDetails;

@Repository
public class UserDetailsRepository {
//public interface UserDetailsRepository extends MongoRepository<UserDetails, Integer> {
	
	private String userTableName = "user_details";
	@Autowired
	private DynamoDBMapper dynamoDBMapper;
	
	public void save(UserDetails userData) {
		dynamoDBMapper.save(userData);
	}
	
	public void updatePassword(UserDetails userData) {
		dynamoDBMapper.save(userData);
    }
	
	public UserDetails getUserDetails(String loginid, String password) {
		Map<String, Object> conditionMap = new HashMap<>();
		conditionMap.put(":userid", loginid);
		conditionMap.put(":userpassword", password);
		QuerySpec query = new QuerySpec()
				.withKeyConditionExpression("loginid = :userid")
				.withFilterExpression("password = :userpassword")
				.withValueMap(conditionMap);

		DynamoDB client = new DynamoDB(new DynamoDBConfig().buildAmazonDynamoDB());
		Table table = client.getTable(userTableName);
   	    ItemCollection<?> result = null;
   	    try {
   		    result= table.query(query);
   	    }catch( Exception e) {
       	    System.err.println("Unable to write item: "+e);
       	    e.printStackTrace();
        }
   	 
		Iterator<?> itr = result.iterator();

		if (itr.hasNext()) {
			Item item = (Item) itr.next();
			item.getString("");
			
			UserDetails user = new UserDetails();
			user.setId(item.getLong("id"));
			user.setFirstname(item.getString("firstname"));
			user.setLastname(item.getString("lastname"));
			user.setEmail(item.getString("email"));
			user.setContactnumber(item.getString("contactnumber"));
			user.setLoginid(item.getString("loginid"));
			user.setPassword(item.getString("password"));
			return user;
		}

		return null;
	}

	public UserDetails getUser(long id) {
		Map<String, Object> conditionMap = new HashMap<>();
		conditionMap.put(":id", id);
		ScanSpec query = new ScanSpec()
				.withFilterExpression("id = :id")
				.withValueMap(conditionMap);
		DynamoDB client = new DynamoDB(new DynamoDBConfig().buildAmazonDynamoDB());
		Table table = client.getTable(userTableName);
		ItemCollection<?> result = null;
		try {
			result= table.scan(query);
		} catch( Exception e) {
			System.err.println("Unable to write item: "+e);
			e.printStackTrace();
		}
		Iterator<?> itr = result.iterator();
		UserDetails user = new UserDetails();
		while (itr.hasNext()) {
			Item item = (Item) itr.next();
			item.getString("");
			user.setId(item.getLong("id"));
			user.setFirstname(item.getString("firstname"));
			user.setLastname(item.getString("lastname"));
			user.setEmail(item.getString("email"));
			user.setContactnumber(item.getString("contactnumber"));
			user.setLoginid(item.getString("loginid"));
		}
		return user;
	}

	public UserDetails getUserId(String loginid) {
		Map<String, Object> conditionMap = new HashMap<>();
		conditionMap.put(":userid", loginid);
		QuerySpec query = new QuerySpec()
				.withKeyConditionExpression("loginid = :userid")
				.withValueMap(conditionMap);
		DynamoDB client = new DynamoDB(new DynamoDBConfig().buildAmazonDynamoDB());
		Table table = client.getTable(userTableName);
   	    ItemCollection<?> result = null;
   	    try {
   		    result= table.query(query);
   	    }catch( Exception e) {
       	    System.err.println("Unable to write item: "+e);
       	    e.printStackTrace();
        }
		Iterator<?> itr = result.iterator();
		if (itr.hasNext()) {
			Item item = (Item) itr.next();
			item.getString("");
			UserDetails user = new UserDetails();
			user.setId(item.getLong("id"));
			user.setFirstname(item.getString("firstname"));
			user.setLastname(item.getString("lastname"));
			user.setEmail(item.getString("email"));
			user.setContactnumber(item.getString("contactnumber"));
			user.setLoginid(item.getString("loginid"));
			user.setPassword(item.getString("password"));
			return user;
		}
		return null;
	}
	
	public List<UserDetails> getUserDetailsList() {
		ScanSpec query = new ScanSpec();
		DynamoDB client = new DynamoDB(new DynamoDBConfig().buildAmazonDynamoDB());
		Table table = client.getTable(userTableName);
		ItemCollection<?> result = null;
		try {
			result= table.scan(query);
		} catch( Exception e) {
			System.err.println("Unable to write item: "+e);
			e.printStackTrace();
		}

		Iterator<?> itr = result.iterator();
		List<UserDetails> userList = new ArrayList<UserDetails>();
		while (itr.hasNext()) {
			Item item = (Item) itr.next();
			item.getString("");
			UserDetails user = new UserDetails();
			user.setId(item.getLong("id"));
			user.setFirstname(item.getString("firstname"));
			user.setLastname(item.getString("lastname"));
			user.setEmail(item.getString("email"));
			user.setContactnumber(item.getString("contactnumber"));
			user.setLoginid(item.getString("loginid"));
//			user.setPassword(item.getString("password"));
			userList.add(user);
		}

		return userList;
	}
	
//	@Query(value= "{ 'loginid': ?0, 'password': ?1}")
//    public UserDetails getUserDetails(String loginid, String password);
//	
//	@Query(value= "{ '_id': ?0}")
//    public UserDetails getUser(long id);
//	
//	@Query(value= "{ 'loginid': ?0}")
//    public UserDetails getUserId(String loginid);
//	
//	@Query(value= "{}", fields = "{ 'firstname': 1, 'lastname': 1, 'email': 1, 'loginid': 1 }")
//	public List<UserDetails> getUserDetailsList();
}
