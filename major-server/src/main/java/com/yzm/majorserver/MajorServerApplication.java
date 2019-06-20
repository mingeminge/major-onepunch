package com.yzm.majorserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

/**
 * @author yizuomin
 */
@SpringBootApplication
@EnableCaching
public class MajorServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(MajorServerApplication.class, args);
    }

}
