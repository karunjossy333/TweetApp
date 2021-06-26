package com.tweetapp.project.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import com.tweetapp.project.entity.UserDetails;

public interface UserDetailsRepository extends MongoRepository<UserDetails, Integer> {
	@Query(value= "{ 'loginid': ?0, 'password': ?1}")
    public UserDetails getUserDetails(String loginid, String password);
	
	@Query(value= "{ '_id': ?0}")
    public UserDetails getUser(long id);
	
	@Query(value= "{ 'loginid': ?0}")
    public UserDetails getUserId(String loginid);
	
	@Query(value= "{}", fields = "{ 'firstname': 1, 'lastname': 1, 'email': 1, 'loginid': 1 }")
	public List<UserDetails> getUserDetailsList();
}
