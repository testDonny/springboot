package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@ComponentScan({"com.example"})

@SpringBootApplication
@EnableTransactionManagement // 交易
public class Coin1Application {

	public static void main(String[] args) {
		SpringApplication.run(Coin1Application.class, args);
	}

}
