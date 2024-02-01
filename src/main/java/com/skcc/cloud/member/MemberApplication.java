package com.skcc.cloud.member;

import com.skcc.cloud.member.member.application.port.out.MemberCommandPersistencePort;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;

@SpringBootApplication
public class MemberApplication {
	private static final String PROPERTIES = "spring.config.location=classpath:/config/application/";

	public static void main(String[] args) {
		new SpringApplicationBuilder(MemberApplication.class)
				.properties(PROPERTIES)
				.run(args);
	}

	@Bean
	@Profile("local")
	public TestDataInit testDataInit(MemberCommandPersistencePort memberCommandPersistencePort){
		return new TestDataInit(memberCommandPersistencePort);
	}

}
