package io.gitplanner.server;

import org.jasypt.encryption.pbe.StandardPBEStringEncryptor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class GitPlannerServerApplicationTests {

	@Test
	void contextLoads() {
	}

    @Value("${jasypt.encryptor.password}")
    private String password;

    @Test
    void jasypt() {
        System.out.println("password: " + password);

        String id = "";
        String secret = "";

        System.out.println(jasyptEncoding(id));
        System.out.println(jasyptEncoding(secret));
    }

    public String jasyptEncoding(String value) {
        StandardPBEStringEncryptor pbeEnc = new StandardPBEStringEncryptor();
        pbeEnc.setAlgorithm("PBEWithMD5AndDES");
        pbeEnc.setPassword(password);
        return pbeEnc.encrypt(value);
    }
}
