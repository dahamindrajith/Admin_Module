/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mbsl;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 *
 * @author MBSL2395
 */
@Configuration
public class WebConfig {

    @Bean
    public FilterRegistrationBean greetingFilterRegistrationBean() {
        FilterRegistrationBean registrationBean = new FilterRegistrationBean();
        registrationBean.setName("mainFilter");
        MainFilter greetingFilter = new MainFilter();
        registrationBean.setFilter(greetingFilter);
        registrationBean.setOrder(1);
        return registrationBean;
    }
}
