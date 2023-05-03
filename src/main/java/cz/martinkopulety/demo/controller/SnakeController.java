package cz.martinkopulety.demo.controller;

import cz.martinkopulety.demo.entity.Snake;
import cz.martinkopulety.demo.service.SnakeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/snakes")
public class SnakeController {
    @Autowired
    private SnakeService snakeService;

    ////////////////Create////////////////
    @PostMapping
    public ResponseEntity<Snake> createSnake(@RequestBody Snake snake){
       return new ResponseEntity<>(snakeService.createSnake(snake), HttpStatus.CREATED);
    }

    ////////////////Read////////////////
    @GetMapping
    public ResponseEntity<List<Snake>> getAllSnakes(){
        return new ResponseEntity<>(snakeService.getAllSnakes(),HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Snake> getSnakeById(@PathVariable Long id){
        return new ResponseEntity<>(snakeService.getSnakeById(id),HttpStatus.OK);
    }

    ////////////////Update////////////////
    @PutMapping("/{id}")
    public ResponseEntity<Snake> updateSnake(@PathVariable Long id, @RequestBody Snake snake){
        return new ResponseEntity<>(snakeService.updateSnake(snake, id), HttpStatus.OK );
    }

    ////////////////Delete////////////////
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSnake(@PathVariable Long id){
        snakeService.deleteSnake(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
