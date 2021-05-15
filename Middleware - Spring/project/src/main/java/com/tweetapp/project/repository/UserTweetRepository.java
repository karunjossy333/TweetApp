package com.tweetapp.project.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import com.tweetapp.project.entity.UserTweets;

public interface UserTweetRepository extends MongoRepository<UserTweets, Integer> {

	@Query(value = "{ 'userid': ?0, 'isreply': false }")
	public List<UserTweets> getUserTweets(Long loginid);
	
	@Query(value = "{ 'isreply': true, 'replytweetid': ?0 }")
	public List<UserTweets> getReplyTweets(Long tweetid);
	
	@Query(value = "{ 'isreply': false }")
	public List<UserTweets> getAllTweets();
	
}