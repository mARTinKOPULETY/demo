package cz.martinkopulety.demo.service;

import cz.martinkopulety.demo.entity.Snake;
import cz.martinkopulety.demo.repository.SnakeRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SnakeService {
    private final SnakeRepository snakeRepository;

    public SnakeService(SnakeRepository snakeRepository) {
        this.snakeRepository = snakeRepository;
    }

    ////////////////Create////////////////
    public Snake createSnake(Snake snake){
        return snakeRepository.save(snake);
    }

    ////////////////Read////////////////
    public List<Snake> getAllSnakes(){
        return snakeRepository.findAll();
    }

    public Snake getSnakeById(Long id){
       return snakeRepository.findById(id).orElseThrow(()->
               new RuntimeException("Snake with id "+ id + " was not found."));
    }

    ////////////////Update////////////////
    public Snake updateSnake(Snake snake, Long id){
        Snake existingSnake = snakeRepository.findById(id).orElseThrow(() ->
                new RuntimeException("Snake with id "+ id + " was not found."));
        existingSnake.setSnakePic(snake.getSnakePic());
        existingSnake.setSnakeName(snake.getSnakeName());
        existingSnake.setUserName(snake.getUserName());

        return snakeRepository.save(existingSnake);
    }

    ////////////////Delete////////////////
    public void deleteSnake(Long id){
        Snake existingSnake = snakeRepository.findById(id).orElseThrow(() ->
                new RuntimeException("Snake with id "+ id + " was not found."));
        snakeRepository.delete(existingSnake);

    }
}
