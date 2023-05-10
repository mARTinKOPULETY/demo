package cz.martinkopulety.demo.repository;

import cz.martinkopulety.demo.entity.Snake;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SnakeRepository extends JpaRepository<Snake, Long> {
    @Query(value = "SELECT  *  FROM snake  WHERE snake_pic LIKE BINARY CONCAT('%',:partOfSnakePic,'%')", nativeQuery = true)
    List<Snake> findBySnakePicContaining(@Param("partOfSnakePic") String partOfSnakePic);

    @Query(value = "SELECT  *  FROM snake  WHERE snake_name LIKE BINARY CONCAT('%',:partOfSnakeName,'%')", nativeQuery = true)
    List<Snake> findBySnakeNameContaining(String partOfSnakeName);
}
