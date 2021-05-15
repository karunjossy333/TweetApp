package com.tweetapp.project.entity;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@Document(collection = "UserTweets")
public class UserTweets {

	@Transient
    public static final String SEQUENCE_NAME = "user_tweets";
	
	@Id
	private long id;
	private String tweet;
	private String hashtag;
	private long userid;
	private String postdate;
	private int likes;
	private List<Long> likedusers=new ArrayList<Long>();
	private Boolean isreply = false;
	private long replytweetid = 0;
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
