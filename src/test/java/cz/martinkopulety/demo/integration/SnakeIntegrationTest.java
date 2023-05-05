package cz.martinkopulety.demo.integration;

import cz.martinkopulety.demo.repository.SnakeRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.web.client.RestTemplate;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class SnakeIntegrationTest {

    @LocalServerPort
    private int port;

    private String baseUrl =  "http://localhost";

    private static RestTemplate restTemplate;

    @Autowired
    private SnakeRepository snakeRepository;

    @BeforeAll
    public static void init(){
        restTemplate = new RestTemplate();
    }

    @BeforeEach
    public void beforeSetUp(){
        baseUrl = baseUrl + ":" + port + "/api/v1/snakes";
    }
    @AfterEach
    public void afterEach(){
        snakeRepository.deleteAll();
    }

}
