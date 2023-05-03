package cz.martinkopulety.demo.entity;


import jakarta.persistence.*;


@Entity
public class Snake {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="snake_id")
    private Long snakeId;
    @Column(name="snake_name")
    private String snakeName;
    @Column(name="snake_pic")
    private String snakePic;
    @Column(name="user_name")
    private String userName;

    public Snake() {
    }

    public Snake(Long snakeId, String snakeName, String snakePic, String userName) {
        this.snakeId = snakeId;
        this.snakeName = snakeName;
        this.snakePic = snakePic;
        this.userName = userName;
    }

    public Long getSnakeId() {
        return snakeId;
    }

    public void setSnakeId(Long snakeId) {
        this.snakeId = snakeId;
    }

    public String getSnakeName() {
        return snakeName;
    }

    public void setSnakeName(String snakeName) {
        this.snakeName = snakeName;
    }

    public String getSnakePic() {
        return snakePic;
    }

    public void setSnakePic(String snakePic) {
        this.snakePic = snakePic;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }


}



