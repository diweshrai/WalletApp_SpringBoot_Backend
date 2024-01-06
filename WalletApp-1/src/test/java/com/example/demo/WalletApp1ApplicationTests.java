package com.example.demo;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class WalletApp1ApplicationTests {

    Calculation cal = new Calculation();

    @Test
    void contextLoads() {
    }

    @Test
    void addNumber() {

        int expecres = 10;

        int res = cal.add(10, 2);

        assertThat(res).isEqualTo(expecres);

    }

}
