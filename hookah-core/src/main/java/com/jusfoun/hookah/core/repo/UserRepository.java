package com.jusfoun.hookah.core.repo;

import com.jusfoun.hookah.core.domain.User;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * @author:jsshao
 * @date: 2017-3-3
 */
public interface UserRepository extends MongoRepository<User, String>{

}
