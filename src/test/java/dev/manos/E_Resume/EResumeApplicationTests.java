package dev.manos.E_Resume;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;

@Import(TestcontainersConfiguration.class)
@SpringBootTest
class EResumeApplicationTests {

	@Test
	void contextLoads() {
	}

}
