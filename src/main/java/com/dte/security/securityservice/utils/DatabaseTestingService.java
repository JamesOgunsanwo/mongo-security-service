package com.dte.security.securityservice.utils;

import com.dte.security.securityservice.repositories.TableTestRepository;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Objects;

@Service
public class DatabaseTestingService {

    private static Logger logger = Logger.getLogger(DatabaseTestingService.class);

    @Autowired
    private TableTestRepository tableTestRepository;

    public String test() {
        logger.info("Start of DatabaseTestingService.tableTestRepository()");
        TableTestDao result = tableTestRepository.save(new TableTestDao(null, LocalDateTime.now()));
        logger.info("End of DatabaseTestingService.tableTestRepository()");
        return Objects.nonNull(result) ? "OK" : "KO";
    }
}
