package com.wwlei.xbqlmz;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles("test")
class XbqlmzApplicationTests {

    @Test
    void contextLoads() {
        System.out.println("this is a test!");
    }
}
