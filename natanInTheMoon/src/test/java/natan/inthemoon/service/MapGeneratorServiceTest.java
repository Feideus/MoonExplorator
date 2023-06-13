package natan.inthemoon.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.fail;

@SpringBootTest
public class MapGeneratorServiceTest {

    @Value("${mapDimension:5}")
    private int mapDimension;

    @Test
    void MapShouldBeMeetDimensionsValidTest() {
        fail("Not yet Implemented");
    }

    @Test
    void MapShouldBeMeetDimensionsFailTest(){
        fail("Not yet Implemented");
    }

    @Test
    void MapShouldBehaveNoMoreThanSquareObstaclesValidTest(){
        fail("Not yet Implemented");
    }

    @Test
    void MapShouldBehaveNoMoreThanSquareObstaclesFailTest(){
        fail("Not yet Implemented");
    }
}
