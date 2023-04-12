/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mbsl.dao.impl;

import com.mbsl.Classes.securityManager;
import com.mbsl.Properties;
import com.mbsl.dao.DonloadRepoDao;
import com.mbsl.model.User;
import java.util.Locale;
import javax.sql.DataSource;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

/**
 *
 * @author MBSL2523
 */
@Repository
public class DonloadRepoDaoImpl implements DonloadRepoDao{
    
private static final Logger LOGGER = LogManager.getLogger(UserDaoImpl.class);

    @Autowired
    private MessageSource messageSource;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    DataSource dataSource;

    @Autowired
    Properties properties;

@Override
    public User donloadRepo(User repo) {
       
User array=new User();

    System.out.println("-----------"+repo.getRqstType());

return array;
    }

}
