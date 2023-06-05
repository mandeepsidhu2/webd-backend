package com.tabber.tabby;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
public class TabbyApplication {

	public static void main(String[] args) {
		SpringApplication.run(TabbyApplication.class, args);
		System.out.println("Tabby is up");
	}
}
