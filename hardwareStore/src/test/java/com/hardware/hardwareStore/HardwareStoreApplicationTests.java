package com.hardware.hardwareStore;

import com.hardware.hardwareStore.Service.EmailService;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

@SpringBootTest
class HardwareStoreApplicationTests {

	@MockBean
	private EmailService emailService;

	@Test
	void contextLoads() {
	}

}
