package cz.martinkopulety.demo.servie;

import cz.martinkopulety.demo.entity.Snake;
import cz.martinkopulety.demo.repository.SnakeRepository;
import cz.martinkopulety.demo.service.SnakeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class SnakeServiceTest {

    @InjectMocks
    private SnakeService snakeService;

    @Mock
    private SnakeRepository snakeRepository;

    private Snake adder;
    private Snake mamba;

    @BeforeEach
    void init(){
        //arrange
        adder = new Snake();
        adder.setSnakeId(1L);
        adder.setSnakeName("Adder");
        adder.setSnakePic("vvvWvvvvv:<");
        adder.setUserName("Karel");

        mamba = new Snake();
        mamba.setSnakeId(2L);
        mamba.setSnakePic("vvVVVvv:<");
        mamba.setUserName("Pepek");
        mamba.setSnakeName("Mamba");
    }

    ////////////////CREATE////////////////
    @Test
    @DisplayName("It should save a snake object into a database")
    void createSnakeTest(){
        //arrange
        when(snakeRepository.save(any(Snake.class))).thenReturn(adder);
        //act
        Snake createdSnake = snakeService.createSnake(adder);
        //assert
        assertNotNull(createdSnake);
        assertEquals("Adder", createdSnake.getSnakeName());
    }

    ////////////////READ - Get All ////////////////
    @Test
    @DisplayName("It should return size 2 of snake list")
    void getAllSnakesTest(){
        //arrange
        List<Snake> snakeList = new ArrayList<>();
        snakeList.add(adder);
        snakeList.add(mamba);
        when(snakeRepository.findAll()).thenReturn(snakeList);
        //act
        List<Snake> allSnakes= snakeService.getAllSnakes();
        //assert
        assertNotNull(allSnakes);
        assertEquals(2, snakeList.size());
    }
    ////////////////READ- Get by id////////////////
    @Test
    @DisplayName("It should return one snake")
    void getSnakeByIdTest(){
        //arrange
        when(snakeRepository.findById(anyLong())).thenReturn(Optional.of(adder));
        //act
        Snake foundSnake = snakeService.getSnakeById(1L);
        //assert
        assertNotNull(foundSnake);
        assertEquals(1, foundSnake.getSnakeId());
    }

    @Test
    @DisplayName("It should throws exception")
    void getSnakeByIdNoFound(){
        //arrange
        when(snakeRepository.findById(1L)).thenReturn(Optional.of(adder));
        //act&assert
        assertThrows(RuntimeException.class, () -> snakeService.getSnakeById(2L));
    }

    ////////////////READ- Get by picture////////////////
    @Test
    @DisplayName("It should returns one snake")
    void getSnakeByPicReturnOneSnakeTest(){
        //arrange
        List<Snake> snakeList = new ArrayList<>();
        snakeList.add(adder);
        when(snakeRepository.findBySnakePicContaining("v")).thenReturn(snakeList);
        //act
        List<Snake> snakesByPic = snakeService.getSnakeByPic("v");
        //assert
        assertNotNull(snakesByPic);
        assertEquals(1,snakeList.size());
    }

    @Test
    @DisplayName("It should returns two snakes")
    void getSnakeByPicReturnTwoSnakesTest(){
        //arrange
        List<Snake> snakeList = new ArrayList<>();
        snakeList.add(adder);
        snakeList.add(mamba);
        when(snakeRepository.findBySnakePicContaining("v")).thenReturn(snakeList);
        //act
        List<Snake> snakesByPic = snakeService.getSnakeByPic("v");
        //assert
        assertNotNull(snakesByPic);
        assertEquals(2,snakeList.size());
    }

    @Test
    @DisplayName("It should throws exception")
    void getSnakeByPicNoFoundTest(){
        //act&assert
        assertThrows(NoSuchElementException.class,() ->
                snakeService.getSnakeByPic("a"));

    }

    ////////////////READ- Get by name////////////////
    @Test
    @DisplayName("It should retrn one snake.")
    void getSnakeByNameShouldReturnOneSnake(){
        //arrange
        List<Snake> snakeList = new ArrayList<>();
        snakeList.add(adder);
        when(snakeRepository.findBySnakeNameContaining("a")).thenReturn(snakeList);
        //act
        List<Snake> foundSnakes= snakeService.getSnakeByName("a");
        //assert
        assertEquals(1,foundSnakes.size());
    }

    @Test
    @DisplayName("It should returns two snakes")
    void getSnakeByNameReturnTwoSnakesTest() {
        //arrange
        List<Snake> snakeList = new ArrayList<>();
        snakeList.add(adder);
        snakeList.add(mamba);
        when(snakeRepository.findBySnakeNameContaining(anyString())).thenReturn(snakeList);
        //act
        List<Snake> foundSnakes= snakeService.getSnakeByName("a");
        //assert
        assertEquals(2, foundSnakes.size());
    }

    @Test
    @DisplayName("It should return exception")
    void getSnakeByNameNoFoundTest(){
        assertThrows(NoSuchElementException.class, ()-> snakeService.getSnakeByName("a"));
    }

    ////////////////Update////////////////
    @Test
    @DisplayName("It should update name of the snake.")
    void updateSnakeTest(){
        //arrange
        when(snakeRepository.findById(1L)).thenReturn(Optional.of(adder));
        when(snakeRepository.save(any(Snake.class))).thenReturn(adder);
        adder.setSnakeName("Viper");
        //act
        Snake updatedSnake = snakeService.updateSnake(adder, 1L);
        //assert
        assertEquals("Viper",adder.getSnakeName());
        }

    ////////////////Delete////////////////
    @Test
    @DisplayName("It should delete the snake")
    void deleteSnakeTest(){
        //arrange
        when(snakeRepository.findById(1L)).thenReturn(Optional.of(adder));
        doNothing().when(snakeRepository).delete(any(Snake.class));
        //act
        snakeService.deleteSnake(1L);
        //assert
        verify(snakeRepository, times(1)).delete(adder);
    }
}



