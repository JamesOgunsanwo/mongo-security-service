package com.dte.security.securityservice.utils;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;

import java.time.LocalDateTime;

@Setter
@Getter
@AllArgsConstructor
public class TableTestDao {

    @Id
    private String id;

    private LocalDateTime creation_date;

}
