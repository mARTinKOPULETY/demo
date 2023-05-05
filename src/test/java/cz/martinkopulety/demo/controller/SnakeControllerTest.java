package cz.martinkopulety.demo.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import cz.martinkopulety.demo.entity.Snake;
import cz.martinkopulety.demo.service.SnakeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest
public class SnakeControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @MockBean
    private SnakeService snakeService;

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
    ////////////////Create////////////////
    @Test
    void testCreateSnake_shouldCreateTheSnake() throws Exception {
        //arrange
        when(snakeService.createSnake(any(Snake.class))).thenReturn(adder);
        //act
        this.mockMvc.perform(post("/api/v1/snakes")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(adder)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.snakeName",is(adder.getSnakeName())))
                .andExpect(jsonPath("$.snakePic",is(adder.getSnakePic())))
                .andExpect(jsonPath("$.userName", is(adder.getUserName())));
    }

    ////////////////Read - Get all////////////////
    @Test
    void testGetAllSnakes_shouldReturnListSize2() throws Exception {
        //arrange
        List<Snake> snakeList = new ArrayList<>();
        snakeList.add(adder);
        snakeList.add(mamba);
        when(snakeService.getAllSnakes()).thenReturn(snakeList);
        //act
        this.mockMvc.perform(get("/api/v1/snakes"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()", is(2)));
    }

    ////////////////Read - Get by id////////////////
    @Test
    void testGetSnakeById_itShouldReturnSnakeByItsId() throws Exception {
        //arrange
        when(snakeService.getSnakeById(anyLong())).thenReturn(adder);
        //act
        this.mockMvc.perform(get("/api/v1/snakes/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.snakeName",is("Adder")));
    }

    ////////////////Read - Get by part of picture////////////////
    @Test
    void testGetSnakeByPic_itShouldReturnListSize1() throws Exception {
        //arrange
        List<Snake> listSize1= new ArrayList<>();
        listSize1.add(mamba);
        when(snakeService.getSnakeByPic("v")).thenReturn(listSize1);
        //act
        this.mockMvc.perform(get("/api/v1/snakes/picture/v"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()", is(1)));
    }

    ////////////////Read - Get by part of name////////////////
    @Test
    void testGetSnakeByName_shouldReturnListSize1() throws Exception {
        //arrange
        List<Snake> listSize1 = new ArrayList<>();
        listSize1.add(adder);
        when(snakeService.getSnakeByName("a")).thenReturn(listSize1);
        //act
        this.mockMvc.perform(get("/api/v1/snakes/name/a"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()",is(1)));
    }

    ////////////////Update////////////////
    @Test
    void testUpdateSnake_shouldUpdateSnakeName() throws Exception {
        //arrange
        adder.setSnakeName("Anaconda");
        when(snakeService.updateSnake(any(Snake.class), anyLong())).thenReturn(adder);
        //act
        this.mockMvc.perform(put("/api/v1/snakes/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(adder)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.snakeName",is("Anaconda")));
    }

    ////////////////Delete////////////////
    @Test
    void testDeleteSnake_shouldDeleteSnake() throws Exception {
        //arrange
        doNothing().when(snakeService).deleteSnake(anyLong());
        //act
        this.mockMvc.perform(delete("/api/v1/snakes/1"))
                .andExpect(status().isNoContent());
    }
}
