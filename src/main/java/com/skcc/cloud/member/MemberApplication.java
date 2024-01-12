package com.skcc.cloud.member;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

@SpringBootApplication
public class MemberApplication {
	private static final String PROPERTIES = "spring.config.location=classpath:/config/application/";

	public static void main(String[] args) {
		new SpringApplicationBuilder(MemberApplication.class)
				.properties(PROPERTIES)
				.run(args);
	}
}
