package com.tweetapp.project.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.tweetapp.project.entity.UserDetails;
import com.tweetapp.project.entity.UserTweets;
import com.tweetapp.project.repository.UserDetailsRepository;
import com.tweetapp.project.repository.UserTweetRepository;

@Service
public class TweetService {

	@Autowired
	private UserTweetRepository userTweetRepository;
	@Autowired
	private UserDetailsRepository userDetailsRepository;
//	@Autowired
//	private SequenceGeneratorService sequenceGeneratorService;
	
	public static ObjectMapper mapper = new ObjectMapper();
	
	public ObjectNode postTweet(String loginid, UserTweets userTweet) {
		ObjectNode statusNode = mapper.createObjectNode();
		try {
//			userTweet.setId(sequenceGeneratorService.generateSequence(UserTweets.SEQUENCE_NAME));
			long tweetId = 0;
			int count = 0;
			do {
				tweetId = userTweetRepository.getTweetId();
				if(tweetId != 0) {
					userTweet.setId(tweetId);
			        userTweetRepository.save(userTweet);
			        statusNode.put("status", true);
			        break;
				}
				if(count == 10) {
					throw new Exception("Some error occured");
				}
				count++;
			} while(tweetId == 0);
		} catch (Exception e) {
			statusNode.put("status", false);
			statusNode.put("errors", e.getMessage());
//			sequenceGeneratorService.decrement(UserTweets.SEQUENCE_NAME);
		}
		return statusNode;
	}
	
	public ObjectNode getUserTweets(String loginid) {
		ObjectNode statusNode = mapper.createObjectNode();
		UserDetails user = userDetailsRepository.getUserId(loginid);
		ArrayNode tweetArray = mapper.createArrayNode();
		try {
			List<UserTweets> tweets = userTweetRepository.getUserTweets(user.getId());
			for (UserTweets tweet : tweets) {
				List<UserTweets> replyTweets = userTweetRepository.getReplyTweets(tweet.getId());
				
				ObjectNode tweetNode = mapper.createObjectNode();
				ArrayNode replyTweetArray = mapper.createArrayNode();
				
				for (UserTweets replyTweet : replyTweets) {
					ObjectNode replyTweetNode = mapper.createObjectNode();
					
					UserDetails repliedUser = userDetailsRepository.getUser(replyTweet.getUserid());
					
					replyTweetNode.put("userName", repliedUser.getFirstname() + ' ' + repliedUser.getLastname());
					replyTweetNode.put("loginId", repliedUser.getLoginid());
					replyTweetNode.put("tweet", replyTweet.getTweet());
					replyTweetNode.put("postTime", replyTweet.getPostdate());
					replyTweetNode.put("hashTag", replyTweet.getHashtag());
					replyTweetNode.put("isLiked", this.isLiked(user.getId(), replyTweet.getLikedusers()));
					replyTweetNode.put("noOfLikes", replyTweet.getLikes());
					replyTweetNode.put("id", replyTweet.getId());
					replyTweetNode.put("replyId", replyTweet.getReplyto());
					replyTweetNode.put("replyTweetId", replyTweet.getReplytweetid());
					
					replyTweetArray.add(replyTweetNode);
				}
				
				tweetNode.put("userName", user.getFirstname() + ' ' + user.getLastname());
				tweetNode.put("loginId", user.getLoginid());
				tweetNode.put("tweet", tweet.getTweet());
				tweetNode.put("postTime", tweet.getPostdate());
				tweetNode.put("hashTag", tweet.getHashtag());
				tweetNode.put("isLiked", this.isLiked(user.getId(), tweet.getLikedusers()));
				tweetNode.put("noOfLikes", tweet.getLikes());
				tweetNode.put("id", tweet.getId());
				
				tweetNode.putArray("replyTweet").addAll(replyTweetArray);
				tweetArray.add(tweetNode);
			}
			statusNode.put("status", true);
			statusNode.putArray("usertweets").addAll(tweetArray);
		} catch (Exception e) {
			statusNode.put("status", false);
			statusNode.put("errors", e.getMessage());
		}
		return statusNode;
	}
	
	public ObjectNode getAllTweets(long userid) {
		ObjectNode statusNode = mapper.createObjectNode();
		ArrayNode tweetArray = mapper.createArrayNode();
		try {
			List<UserTweets> tweets = userTweetRepository.getAllTweets();
			for (UserTweets tweet : tweets) {
				UserDetails user = userDetailsRepository.getUser(tweet.getUserid());
				List<UserTweets> replyTweets = userTweetRepository.getReplyTweets(tweet.getId());
				
				ObjectNode tweetNode = mapper.createObjectNode();
				ArrayNode replyTweetArray = mapper.createArrayNode();
				
				for (UserTweets replyTweet : replyTweets) {
					ObjectNode replyTweetNode = mapper.createObjectNode();
					
					UserDetails repliedUser = userDetailsRepository.getUser(replyTweet.getUserid());
					
					replyTweetNode.put("userName", repliedUser.getFirstname() + ' ' + repliedUser.getLastname());
					replyTweetNode.put("loginId", repliedUser.getLoginid());
					replyTweetNode.put("tweet", replyTweet.getTweet());
					replyTweetNode.put("postTime", replyTweet.getPostdate());
					replyTweetNode.put("hashTag", replyTweet.getHashtag());
					replyTweetNode.put("isLiked", this.isLiked(userid, replyTweet.getLikedusers()));
					replyTweetNode.put("noOfLikes", replyTweet.getLikes());
					replyTweetNode.put("id", replyTweet.getId());
					replyTweetNode.put("replyId", replyTweet.getReplyto());
					replyTweetNode.put("replyTweetId", replyTweet.getReplytweetid());
					
					replyTweetArray.add(replyTweetNode);
					
				}
				
				tweetNode.put("userName", user.getFirstname() + ' ' + user.getLastname());
				tweetNode.put("loginId", user.getLoginid());
				tweetNode.put("tweet", tweet.getTweet());
				tweetNode.put("postTime", tweet.getPostdate());
				tweetNode.put("hashTag", tweet.getHashtag());
				tweetNode.put("isLiked", this.isLiked(userid, tweet.getLikedusers()));
				tweetNode.put("noOfLikes", tweet.getLikes());
				tweetNode.put("id", tweet.getId());
				
				tweetNode.putArray("replyTweet").addAll(replyTweetArray);
				tweetArray.add(tweetNode);
			}		
			statusNode.put("status", true);
			statusNode.putArray("tweets").addAll(tweetArray);
		} catch (Exception e) {
			statusNode.put("status", false);
			statusNode.put("errors", e.getMessage());
		}
		return statusNode;
	}
	
	public ObjectNode likeTweet(long userid, long tweetid) {
		ObjectNode statusNode = mapper.createObjectNode();
		try {
			UserTweets tweet = userTweetRepository.findTweetById(tweetid);
			tweet.postLike(userid);
			userTweetRepository.save(tweet);
			statusNode.put("status", true);
		} catch (Exception e) {
			statusNode.put("status", false);
			statusNode.put("errors", e.getMessage());
		}
		return statusNode;
	}

	public boolean isLiked(long userid, List<Long> likedUsers) {
		for(long user: likedUsers) {
			  if(user == userid) {
				  return true;
			  }
		}
		return false;
	}
	
}
