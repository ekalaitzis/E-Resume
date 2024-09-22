package dev.manos.E_Resume;

import org.springframework.boot.SpringApplication;

public class TestEResumeApplication {

	public static void main(String[] args) {
		SpringApplication.from(EResumeApplication::main).with(TestcontainersConfiguration.class).run(args);
	}

}
