package com.dte.security.securityservice.repositories;

import com.dte.security.securityservice.services.user.models.UserDao;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends MongoRepository<UserDao, String> {

    Optional<UserDao> findByEmail(String email);

    Optional<UserDao> findByUsername(String username);

    List<UserDao> findByCreatedDateBetween(Date from, Date to);

}
