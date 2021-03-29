package com.xmm0;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Program {
	
	public static void main(String[] args) {
		Server.setup();
		SpringApplication.run(Program.class, args);
	}
	
}