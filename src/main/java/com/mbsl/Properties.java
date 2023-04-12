/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.mbsl;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

/**
 *
 * @author MBSL1638
 */
@Component
@PropertySource("classpath:db.properties")
public class Properties {
    @Value("${libraryName}")
    private String libraryName;

    @Value("${bankCode}")
    private String bankCode;

    /**
     * @return the libraryName
     */
    public String getLibraryName() {
        return libraryName;
    }

    /**
     * @return the bankCode
     */
    public String getBankCode() {
        return bankCode;
    }
    
    
}
