package cz.martinkopulety.demo.integration;

import cz.martinkopulety.demo.entity.Snake;
import cz.martinkopulety.demo.repository.SnakeRepository;
import cz.martinkopulety.demo.service.SnakeService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.assertj.core.api.Assertions.assertThat;
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class SnakeIntegrationTest {

    @LocalServerPort
    private int port;

    private String baseUrl =  "http://localhost";

    private Snake adder;
    private Snake mamba;

    private static RestTemplate restTemplate;

    @Autowired
    private SnakeRepository snakeRepository;

    @Autowired
    private SnakeService snakeService;

    @BeforeAll
    public static void init(){
        restTemplate = new RestTemplate();
    }

    @BeforeEach
    public void beforeSetUp(){
        baseUrl = baseUrl + ":" + port + "/api/v1/snakes";

        adder = new Snake();
        adder.setSnakeName("Adder");
        adder.setSnakePic("vvvWvvvvv:<");
        adder.setUserName("Karel");

        mamba = new Snake();
        mamba.setSnakePic("vvVVVvv:<");
        mamba.setUserName("Pepek");
        mamba.setSnakeName("Mamba");

        adder = snakeRepository.save(adder);
        mamba = snakeRepository.save(mamba);
    }

    @AfterEach
    public void afterEach(){
        snakeRepository.deleteAll();
    }

    ////////////////Create////////////////
    @Test
    void iTestCreateSnake_shouldCreateSnake(){
        //arrange
        Snake anaconda = new Snake();
        anaconda.setSnakeName("Anaconda");
        anaconda.setSnakePic("/\\/\\/\\/:<");
        anaconda.setUserName("Karel");
        //arrange
        Snake newSnake = restTemplate.postForObject(baseUrl,anaconda,Snake.class);
        //assert
        assertNotNull(newSnake);
        assertThat(newSnake.getSnakeId()).isNotNull();
    }

    ////////////////Read - get all snakes ////////////////
    @Test
    void iTestGetAllSnakes_shouldReturn(){
        //act
        List<Snake> snakeList = restTemplate.getForObject(baseUrl, List.class);
        //assert
        assertEquals(2,snakeList.size());
    }

    ////////////////Read - get one snake by id ////////////////
    @Test
    void iTestGetSnakeById_shouldReturnOneSnake(){
        //act
        Snake foundSnake = restTemplate.getForObject(baseUrl+"/"+adder.getSnakeId(),Snake.class);
        //assert
        assertNotNull(foundSnake);
        assertEquals("Adder",adder.getSnakeName());
    }
    ////////////////Read - get one snake by pic ////////////////
    @Test
    void iTestGetSnakeByPic_shouldReturnSnakeByPic(){
        //act
        List<Snake> foundSnakes = restTemplate.getForObject(baseUrl+"/picture/{pic}", List.class,"W");
        //assert
        assertEquals(1,foundSnakes.size());
    }

    @Test
    void iTestGetSnakeByPic_shouldThrowsException(){
       // TODO Throwing correct exception. NoSuchElementException is expected, but InternalServerError was thrown in  method for finding snake py picture.
        /*
        //It throws different exception. Following test works.
        HttpServerErrorException.InternalServerError exception =   assertThrows(
                HttpServerErrorException.InternalServerError.class,
                () -> restTemplate.getForObject(baseUrl+"/picture/{pic}", List.class, "o")
        );
        */
        //act&assert
        assertThrows(
                NoSuchElementException.class,
                () -> restTemplate.getForObject(baseUrl+"/picture/{pic}", List.class, "o")
        );
    }


    ////////////////Read - get one snake by name ////////////////
    @Test
    void iTestGetSnakeByName_shouldReturnSnakeByName(){
        //act
        List<Snake> foundSnakes = restTemplate.getForObject(baseUrl+"/name/{name}", List.class,"m");
        //assert
        assertEquals(1,foundSnakes.size());
    }

    @Test
    void iTestGetSnakeByName_shouldThrowsException(){
        // TODO Throwing correct exception. NoSuchElementException is expected, but InternalServerError was thrown in  method for finding snake py name.

        /*
        //It throws different exception. Following test works.
        HttpServerErrorException.InternalServerError exception =   assertThrows(
                HttpServerErrorException.InternalServerError.class,
                () -> restTemplate.getForObject(baseUrl+"/name/{name}", List.class, "o")
        );
        */
        //act&assert
         assertThrows(
                NoSuchElementException.class,
                () -> restTemplate.getForObject(baseUrl+"/name/{name}", List.class, "o")
        );
    }

    ////////////////Delete////////////////
    @Test
    void iTestDeleteSnake_shouldDeleteSnake(){
        //act
        restTemplate.delete(baseUrl+"/"+adder.getSnakeId());

        int count = snakeRepository.findAll().size();

        //assert
        assertEquals(1,count);
    }

    ////////////////Update////////////////
    @Test
    void iTestUpdateSnake_shouldUpdateSnakeName(){
        //arrange
        adder.setSnakeName("Anaconda");
        //act
         restTemplate.put(baseUrl+"/{id}",adder, adder.getSnakeId());

         Snake result =restTemplate.getForObject(baseUrl+"/"+adder.getSnakeId(),Snake.class);
         //assert
        assertEquals("Anaconda", result.getSnakeName());
    }

}
