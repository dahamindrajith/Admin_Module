package com.mbsl;

import com.mbsl.dao.impl.TempAccessDaoImpl;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@ComponentScan(basePackages = {"com.*"})
@EnableScheduling
public class VelocityAdminApplication {

    public static void main(String[] args) {
        SpringApplication.run(VelocityAdminApplication.class, args);

    }

}
