package cz.martinkopulety.demo.repository;


import cz.martinkopulety.demo.entity.Snake;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;


import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;

@DataJpaTest
public class SnakeRepositoryTest {

    @Autowired
    private SnakeRepository snakeRepository;

    ////////////////Create////////////////
    @Test
    @DisplayName("It should save the snake to the database")
    void createSnakeTest(){
        //arrange
        Snake adder = new Snake();
        adder.setSnakeName("Adder");
        adder.setSnakePic("vvvvvvvv:<");
        adder.setUserName("Karel");
        //act
        Snake newSnake = snakeRepository.save(adder);
        //assert
        assertNotNull(newSnake);
        assertEquals("Adder", newSnake.getSnakeName());
    }

    ////////////////Read////////////////
    @Test
    @DisplayName("It should return the snake list size of 2")
    void getAllSnakes() {
        //arrange
        Snake adder = new Snake();
        adder.setSnakeName("Adder");
        adder.setSnakePic("vvvvvvvv:<");
        adder.setUserName("Karel");

        Snake mamba = new Snake();
        mamba.setSnakePic("vvVVVvv:<");
        mamba.setUserName("Pepek");
        mamba.setSnakeName("Mamba");

        snakeRepository.save(adder);
        snakeRepository.save(mamba);
        //act
        List<Snake> snakeList = snakeRepository.findAll();

        //assert
        assertNotNull(snakeList);
        assertEquals(2, snakeList.size());


    }
    @Test
    @DisplayName("It should find a snake by its id")
    void getSnakeById(){
        //arrange
        Snake adder = new Snake();
        adder.setSnakeName("Adder");
        adder.setSnakePic("vvvvvvvv:<");
        adder.setUserName("Karel");
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
    void  updateSnake(){
        //arrange
        Snake adder = new Snake();
        adder.setSnakeName("Adder");
        adder.setSnakePic("vvvvvvvv:<");
        adder.setUserName("Karel");
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
    void deleteSnake(){
        //arrange
        Snake adder = new Snake();
        adder.setSnakeName("Adder");
        adder.setSnakePic("vvvvvvvv:<");
        adder.setUserName("Karel");

        Snake mamba = new Snake();
        mamba.setSnakePic("vvVVVvv:<");
        mamba.setUserName("Pepek");
        mamba.setSnakeName("Mamba");

        snakeRepository.save(adder);
        Long id =  adder.getSnakeId();
        snakeRepository.save(mamba);
        //act
        snakeRepository.delete(mamba);
        Optional<Snake> existingSnake= snakeRepository.findById(id);
        List<Snake> snakeList = snakeRepository.findAll();


        //assert
        assertNotNull(snakeList);
        assertEquals(1, snakeList.size());

    }


}
