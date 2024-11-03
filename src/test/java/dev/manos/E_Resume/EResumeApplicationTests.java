package dev.manos.E_Resume;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.TestcontainersConfiguration;

@SpringBootTest
@Testcontainers
@ActiveProfiles("test")
class EResumeApplicationTests {

	@Container
	static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:15-alpine")
			.withDatabaseName("test_db")
			.withUsername("test")
			.withPassword("test");

	@DynamicPropertySource
	static void configureProperties(DynamicPropertyRegistry registry) {
		registry.add("spring.datasource.url", postgres::getJdbcUrl);
		registry.add("spring.datasource.username", postgres::getUsername);
		registry.add("spring.datasource.password", postgres::getPassword);
		registry.add("spring.jpa.hibernate.ddl-auto", () -> "create-drop");
		registry.add("spring.jpa.show-sql", () -> "true");
		registry.add("spring.jpa.properties.hibernate.dialect",
				() -> "org.hibernate.dialect.PostgreSQLDialect");
	}

	@Test
	void contextLoads() {
	}
}
