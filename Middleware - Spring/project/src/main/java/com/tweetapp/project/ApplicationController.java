package com.tweetapp.project;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.google.common.collect.Iterables;
import com.tweetapp.project.entity.UserDetails;
import com.tweetapp.project.entity.UserTweets;
import com.tweetapp.project.service.TweetService;
import com.tweetapp.project.service.UserService;

import io.swagger.annotations.ApiOperation;

@ApiOperation(tags="TweetApp Controller", value = "/api/v1.0/tweets/")
@RestController
//@CrossOrigin(origins = "http://localhost:4200")
@CrossOrigin(origins = "http://tweetapp-frontend-application.s3-website.ap-south-1.amazonaws.com/")
@RequestMapping(value="/api/v1.0/tweets/")
public class ApplicationController {

	@Autowired
	private UserService userService;
	@Autowired
	private TweetService tweetService;
	
	public static ObjectMapper mapper = new ObjectMapper();

	@ApiOperation(value="Register User API", response=ObjectNode.class)
	@RequestMapping(method= RequestMethod.POST, value="register")
	public ObjectNode registerUser(@RequestBody UserDetails requestObject) {
		ObjectNode status = userService.registerUser(requestObject);
	    return getResponse(status);
	}
	
	@ApiOperation(value="User Login API", response=UserDetails.class)
	@RequestMapping(method= RequestMethod.POST, value="login")
	public ObjectNode loginUser(@RequestBody UserDetails requestObject) {
		ObjectNode status = userService.loginUser(requestObject);
	    return getResponse(status);
	}
	
	@ApiOperation(value="Get User List API", response=Iterables.class)
	@RequestMapping(method= RequestMethod.GET, value="users/all")
	public ObjectNode getUsersList() {
		ObjectNode status = userService.getUsersList();
	    return getResponse(status);
	}
	
	@ApiOperation(value="Post Tweet API", response=ObjectNode.class)
	@RequestMapping(method= RequestMethod.POST, value="{loginid}/add")
	public ObjectNode postTweet(@RequestBody UserTweets requestObject, @PathVariable("loginid") String loginid) {
		ObjectNode status = tweetService.postTweet(loginid, requestObject);
	    return getResponse(status);
	}
	
	@ApiOperation(value="Get User Tweet API", response=Iterables.class)
	@RequestMapping(method= RequestMethod.GET, value="{loginid}")
	public ObjectNode getUserTweets(@PathVariable("loginid") String loginid) {
		ObjectNode status = tweetService.getUserTweets(loginid);
	    return getResponse(status);
	}
	
	@ApiOperation(value="Get All Tweets API", response=Iterables.class)
	@RequestMapping(method= RequestMethod.GET, value="all/{userid}")
	public ObjectNode getAllTweets(@PathVariable("userid") long userid) {
		ObjectNode status = tweetService.getAllTweets(userid);
	    return getResponse(status);
	}
	
	@ApiOperation(value="Like Tweet API", response=ObjectNode.class)
	@RequestMapping(method= RequestMethod.GET, value="{userid}/like/{tweetid}")
	public ObjectNode likeTweet(@PathVariable("userid") long userid, @PathVariable("tweetid") long tweetid) {
		ObjectNode status = tweetService.likeTweet(userid, (long) tweetid);
	    return getResponse(status);
	}
	
	@ApiOperation(value="Change Password API", response=ObjectNode.class)
	@RequestMapping(method= RequestMethod.POST, value="forgot")
	public ObjectNode changePassword(@RequestBody UserDetails requestObject) {
		ObjectNode status = userService.changePassword(requestObject);
	    return getResponse(status);
	}
	
	public ObjectNode getResponse(ObjectNode response) {
		ObjectNode responseNode = mapper.createObjectNode();
		responseNode.set("serviceResponse", response);
		return responseNode;
	}
	
}