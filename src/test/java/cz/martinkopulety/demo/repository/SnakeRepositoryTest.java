package cz.martinkopulety.demo.repository;


import cz.martinkopulety.demo.entity.Snake;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;


import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class SnakeRepositoryTest {

    @Autowired
    private SnakeRepository snakeRepository;

    private Snake adder;
    private Snake mamba;
    @BeforeEach
    void init(){
        //arrange
        adder = new Snake();
        adder.setSnakeName("Adder");
        adder.setSnakePic("vvvWvvvvv:<");
        adder.setUserName("Karel");

        mamba = new Snake();
        mamba.setSnakePic("vvVVVvv:<");
        mamba.setUserName("Pepek");
        mamba.setSnakeName("Mamba");

    }

    ////////////////Create////////////////
    @Test
    @DisplayName("It should save the snake to the database")
    void createSnakeTest(){
        //arrange

        //act
        Snake newSnake = snakeRepository.save(adder);
        //assert
        assertNotNull(newSnake);
        assertEquals("Adder", newSnake.getSnakeName());
    }

    ////////////////Read////////////////
    @Test
    @DisplayName("It should return the snake list size of 2")
    void getAllSnakesTest() {
        //arrange

        snakeRepository.save(adder);
        snakeRepository.save(mamba);
        //act
        List<Snake> snakeList = snakeRepository.findAll();

        //assert
        assertNotNull(snakeList);
        assertEquals(2, snakeList.size());
    }


    @Test
    @DisplayName("It should find one snake by picture W")
    void getSnakesByPicTest() {
        //arrange

        snakeRepository.save(adder);
        snakeRepository.save(mamba);

        String pic = "W";
        //act
        List<Snake> snakeList = snakeRepository.findBySnakePicContaining(pic);

        //assert
        assertNotNull(snakeList);
        assertEquals(1, snakeList.size());
    }
    @Test
    @DisplayName("It should find one snake by name m")
    void getSnakesByNameTest() {
        //arrange

        snakeRepository.save(adder);
        snakeRepository.save(mamba);

        String name = "m";
        //act
        List<Snake> snakeList = snakeRepository.findBySnakeNameContaining(name);

        //assert
        assertNotNull(snakeList);
        assertEquals(1, snakeList.size());
    }

    @Test
    @DisplayName("It should find a snake by its id")
    void getSnakeByIdTest(){
        //arrange
        snakeRepository.save(adder);

        //act
        Snake findSnake = snakeRepository.findById(adder.getSnakeId()).get();

        //assert
        assertNotNull(findSnake);
        assertEquals("Adder", findSnake.getSnakeName() );
    }

    ////////////////Update////////////////
    @Test
    @DisplayName("It should update snake with name VIPER")
    void  updateSnakeTest(){
        //arrange
        snakeRepository.save(adder);
        Snake existingSnake = snakeRepository.findById(adder.getSnakeId()).get();

        //act
        existingSnake.setSnakeName("Viper");
        Snake updatedSnake = snakeRepository.save(existingSnake);

        //assert
        assertNotNull(updatedSnake);
        assertEquals("Viper", updatedSnake.getSnakeName() );
    }

    ////////////////Delete////////////////
    @Test
    @DisplayName("It should delete snake")
    void deleteSnakeTest(){
        //arrange
        snakeRepository.save(adder);
        Long id =  adder.getSnakeId();
        snakeRepository.save(mamba);

        //act
        snakeRepository.delete(adder);
        Optional<Snake> existingSnake= snakeRepository.findById(id);
        List<Snake> snakeList = snakeRepository.findAll();

        //assert
        assertThat(existingSnake).isEmpty();
        assertEquals(1, snakeList.size());
    }


}
