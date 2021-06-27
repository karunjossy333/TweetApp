package com.tweetapp.project.repository;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
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
import com.tweetapp.project.entity.UserTweets;

//import java.util.List;

//import org.springframework.data.mongodb.repository.MongoRepository;
//import org.springframework.data.mongodb.repository.Query;

@Repository
public class UserTweetRepository {
//public interface UserTweetRepository extends MongoRepository<UserTweets, Integer> {
	
	private String tweetTableName = "user_tweets";
	@Autowired
	private DynamoDBMapper dynamoDBMapper;
	
	public void save(UserTweets tweet) {
		dynamoDBMapper.save(tweet);
	}
	
	public long getTweetId() {
		long ts = new Date().getTime();
		long randid = (long) Math.floor(Math.random() * 2);
		ts = ts + (long) Math.floor(Math.random() * 2);
		long uuid = ts + randid;
		return checkTweetId(uuid);
	}
	
	public long checkTweetId(long tweetId) {
		Map<String, Object> conditionMap = new HashMap<>();
		conditionMap.put(":id", tweetId);
		QuerySpec query = new QuerySpec()
				.withKeyConditionExpression("id = :id")
				.withValueMap(conditionMap);
		DynamoDB client = new DynamoDB(new DynamoDBConfig().buildAmazonDynamoDB());
		Table table = client.getTable(tweetTableName);
   	    ItemCollection<?> result = null;
   	    try {
   		    result= table.query(query);
   	    }catch( Exception e) {
       	    System.err.println("Unable to write item: "+e);
       	    e.printStackTrace();
        }
		if (result.iterator().hasNext()) {
			return 0;
		}
		return tweetId;
	}
	
	public List<UserTweets> getUserTweets(long userid) {
		Map<String, Object> conditionMap = new HashMap<>();
		conditionMap.put(":userid", userid);
		conditionMap.put(":isreply", 0);
		ScanSpec query = new ScanSpec()
				.withFilterExpression("userid = :userid and isreply = :isreply")
				.withValueMap(conditionMap);
		DynamoDB client = new DynamoDB(new DynamoDBConfig().buildAmazonDynamoDB());
		Table table = client.getTable(tweetTableName);
		ItemCollection<?> result = null;
		try {
			result= table.scan(query);
		} catch( Exception e) {
			System.err.println("Unable to write item: "+e);
			e.printStackTrace();
		}

		Iterator<?> itr = result.iterator();
		List<UserTweets> tweets = new ArrayList<UserTweets>();
		while (itr.hasNext()) {
			Item item = (Item) itr.next();
			item.getString("");
			UserTweets tweet = new UserTweets();
			tweet.setId(item.getLong("id"));
			tweet.setTweet(item.getString("tweet"));
			tweet.setHashtag(item.getString("hashtag"));
			if(item.getInt("isreply") == 0) {
				tweet.setIsreply(false);
			} else {
				tweet.setIsreply(true);
			}
			
			List<Long> likedUsers = new ArrayList<Long>();
			for (int i = 0; i < item.getList("likedusers").size(); i++) {
				BigDecimal userId = (BigDecimal) item.getList("likedusers").get(i);
				likedUsers.add(userId.longValue());
			}
			
			tweet.setLikedusers(likedUsers);
			tweet.setLikes(item.getInt("likes"));
			tweet.setPostdate(item.getString("postdate"));
			tweet.setReplyto(item.getString("replyto"));
			tweet.setReplytweetid(item.getLong("replytweetid"));
			tweet.setUserid(item.getLong("userid"));
			tweets.add(tweet);
		}
		return tweets;
	}
	
	public List<UserTweets> getReplyTweets(long replytweetid) {
		Map<String, Object> conditionMap = new HashMap<>();
		conditionMap.put(":replytweetid", replytweetid);
		conditionMap.put(":isreply", 1);
		ScanSpec query = new ScanSpec()
				.withFilterExpression("replytweetid = :replytweetid and isreply = :isreply")
				.withValueMap(conditionMap);
		DynamoDB client = new DynamoDB(new DynamoDBConfig().buildAmazonDynamoDB());
		Table table = client.getTable(tweetTableName);
		ItemCollection<?> result = null;
		try {
			result= table.scan(query);
		} catch( Exception e) {
			System.err.println("Unable to write item: "+e);
			e.printStackTrace();
		}

		Iterator<?> itr = result.iterator();
		List<UserTweets> tweets = new ArrayList<UserTweets>();
		while (itr.hasNext()) {
			Item item = (Item) itr.next();
			item.getString("");
			UserTweets tweet = new UserTweets();
			tweet.setId(item.getLong("id"));
			tweet.setTweet(item.getString("tweet"));
			tweet.setHashtag(item.getString("hashtag"));
			if(item.getInt("isreply") == 0) {
				tweet.setIsreply(false);
			} else {
				tweet.setIsreply(true);
			}
			List<Long> likedUsers = new ArrayList<Long>();
			for (int i = 0; i < item.getList("likedusers").size(); i++) {
				BigDecimal userId = (BigDecimal) item.getList("likedusers").get(i);
				likedUsers.add(userId.longValue());
			}
			tweet.setLikedusers(likedUsers);
			tweet.setLikes(item.getInt("likes"));
			tweet.setPostdate(item.getString("postdate"));
			tweet.setReplyto(item.getString("replyto"));
			tweet.setReplytweetid(item.getLong("replytweetid"));
			tweet.setUserid(item.getLong("userid"));
			tweets.add(tweet);
		}
		return tweets;
	}
	
	public UserTweets findTweetById(long id) {
		Map<String, Object> conditionMap = new HashMap<>();
		conditionMap.put(":id", id);
		QuerySpec query = new QuerySpec()
				.withKeyConditionExpression("id = :id")
				.withValueMap(conditionMap);
		DynamoDB client = new DynamoDB(new DynamoDBConfig().buildAmazonDynamoDB());
		Table table = client.getTable(tweetTableName);
		ItemCollection<?> result = null;
		try {
			result= table.query(query);
		} catch( Exception e) {
			System.err.println("Unable to write item: "+e);
			e.printStackTrace();
		}
		Iterator<?> itr = result.iterator();
		UserTweets tweet = new UserTweets();
		if (itr.hasNext()) {
			Item item = (Item) itr.next();
			item.getString("");
			tweet.setId(item.getLong("id"));
			tweet.setTweet(item.getString("tweet"));
			tweet.setHashtag(item.getString("hashtag"));
			if(item.getInt("isreply") == 0) {
				tweet.setIsreply(false);
			} else {
				tweet.setIsreply(true);
			}
			List<Long> likedUsers = new ArrayList<Long>();
			for (int i = 0; i < item.getList("likedusers").size(); i++) {
				BigDecimal userId = (BigDecimal) item.getList("likedusers").get(i);
				likedUsers.add(userId.longValue());
			}
			tweet.setLikedusers(likedUsers);
			tweet.setLikes(item.getInt("likes"));
			tweet.setPostdate(item.getString("postdate"));
			tweet.setReplyto(item.getString("replyto"));
			tweet.setReplytweetid(item.getLong("replytweetid"));
			tweet.setUserid(item.getLong("userid"));
		}
		return tweet;
	}
	
	public List<UserTweets> getAllTweets(){
		Map<String, Object> conditionMap = new HashMap<>();
		conditionMap.put(":isreply", 0);
		ScanSpec query = new ScanSpec()
				.withFilterExpression("isreply = :isreply")
				.withValueMap(conditionMap);
		DynamoDB client = new DynamoDB(new DynamoDBConfig().buildAmazonDynamoDB());
		Table table = client.getTable(tweetTableName);
		ItemCollection<?> result = null;
		try {
			result= table.scan(query);
		} catch( Exception e) {
			System.err.println("Unable to write item: "+e);
			e.printStackTrace();
		}

		Iterator<?> itr = result.iterator();
		List<UserTweets> tweets = new ArrayList<UserTweets>();
		while (itr.hasNext()) {
			Item item = (Item) itr.next();
			item.getString("");
			UserTweets tweet = new UserTweets();
			tweet.setId(item.getLong("id"));
			tweet.setTweet(item.getString("tweet"));
			tweet.setHashtag(item.getString("hashtag"));
			if(item.getInt("isreply") == 0) {
				tweet.setIsreply(false);
			} else {
				tweet.setIsreply(true);
			}
			List<Long> likedUsers = new ArrayList<Long>();
			for (int i = 0; i < item.getList("likedusers").size(); i++) {
				BigDecimal userId = (BigDecimal) item.getList("likedusers").get(i);
				likedUsers.add(userId.longValue());
			}
			tweet.setLikedusers(likedUsers);
			tweet.setLikes(item.getInt("likes"));
			tweet.setPostdate(item.getString("postdate"));
			tweet.setReplyto(item.getString("replyto"));
			tweet.setReplytweetid(item.getLong("replytweetid"));
			tweet.setUserid(item.getLong("userid"));
			tweets.add(tweet);
		}
		return tweets;
	}
	
//	@Query(value = "{ 'userid': ?0, 'isreply': false }")
//	public List<UserTweets> getUserTweets(Long loginid);
//	
//	@Query(value = "{ 'isreply': true, 'replytweetid': ?0 }")
//	public List<UserTweets> getReplyTweets(Long tweetid);
//	
//	@Query(value = "{ 'isreply': false }")
//	public List<UserTweets> getAllTweets();
	
}