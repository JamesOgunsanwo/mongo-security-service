package com.dte.security.securityservice.repositories;


import com.dte.security.securityservice.utils.TableTestDao;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface TableTestRepository extends MongoRepository<TableTestDao, String> {
}
