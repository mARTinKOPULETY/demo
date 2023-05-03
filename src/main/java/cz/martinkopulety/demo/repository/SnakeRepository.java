package cz.martinkopulety.demo.repository;

import cz.martinkopulety.demo.entity.Snake;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SnakeRepository extends JpaRepository<Snake, Long> {
    List<Snake> findBySnakePicContaining(String partOfSnakePic);
    List<Snake> findBySnakeNameContaining(String partOfSnakeName);
}
