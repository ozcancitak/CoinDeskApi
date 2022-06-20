package com.coindesk.digitalcurrency;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;


@RunWith(SpringRunner.class)
@SpringBootTest
class DigitalCurrencyApplicationTest {

    @Autowired
    DigitalCurrencyApplication digitalCurrencyApplication;


    @Test
    void getRestTemplateTest() {

        digitalCurrencyApplication.getRestTemplate();
    }
}