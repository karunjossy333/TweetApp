package com.tweetapp.project.entity;

import java.util.ArrayList;
import java.util.List;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;

//@Getter
//@Setter
//@ToString
//@Document(collection = "UserTweets")

@DynamoDBTable(tableName="user_tweets")
public class UserTweets {

//	@Transient
//    public static final String SEQUENCE_NAME = "user_tweets";
	
//	@Id
	@DynamoDBHashKey
	private long id;
	@DynamoDBAttribute
	private String tweet;
	@DynamoDBAttribute
	private String hashtag;
	@DynamoDBAttribute
	private long userid;
	@DynamoDBAttribute
	private String postdate;
	@DynamoDBAttribute
	private int likes;
	@DynamoDBAttribute
	private List<Long> likedusers=new ArrayList<Long>();
	@DynamoDBAttribute
	private Boolean isreply = false;
	@DynamoDBAttribute
	private long replytweetid = 0;
	@DynamoDBAttribute
	private String replyto = "";
		
	public UserTweets() {
		super();
		this.likes = 0;
	}
	
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getTweet() {
		return tweet;
	}

	public void setTweet(String tweet) {
		this.tweet = tweet;
	}

	public String getHashtag() {
		return hashtag;
	}

	public void setHashtag(String hashtag) {
		this.hashtag = hashtag;
	}

	public long getUserid() {
		return userid;
	}

	public void setUserid(long userid) {
		this.userid = userid;
	}

	public String getPostdate() {
		return postdate;
	}

	public void setPostdate(String postdate) {
		this.postdate = postdate;
	}

	public int getLikes() {
		return likes;
	}

	public void setLikes(int likes) {
		this.likes = likes;
	}

	public List<Long> getLikedusers() {
		return likedusers;
	}

	public void setLikedusers(List<Long> likedusers) {
		this.likedusers = likedusers;
	}

	public Boolean getIsreply() {
		return isreply;
	}

	public void setIsreply(Boolean isreply) {
		this.isreply = isreply;
	}

	public long getReplytweetid() {
		return replytweetid;
	}

	public void setReplytweetid(long replytweetid) {
		this.replytweetid = replytweetid;
	}
	
	public String getReplyto() {
		return replyto;
	}

	public void setReplyto(String replyto) {
		this.replyto = replyto;
	}
	
	public void postLike(long userid) {
		this.likes++;
		this.likedusers.add(userid);
	}
	
}
