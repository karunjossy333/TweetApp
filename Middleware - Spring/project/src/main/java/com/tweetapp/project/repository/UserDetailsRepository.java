package com.tweetapp.project.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.tweetapp.project.entity.UserDetails;

public interface UserDetailsRepository extends MongoRepository<UserDetails, Integer> {

}
