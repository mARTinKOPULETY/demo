package cz.martinkopulety.demo.repository;

import cz.martinkopulety.demo.entity.Snake;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SnakeRepository extends JpaRepository<Snake, Long> {
}
