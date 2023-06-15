package natan.inthemoon.service;

import natan.inthemoon.pojos.MoonMap;
import natan.inthemoon.pojos.WeightedPointDescription;
import natan.inthemoon.service.implentation.MapGeneratorService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static java.lang.Math.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class MapGeneratorServiceTest {

    @InjectMocks
    private MapGeneratorService mapGeneratorService;

    @Test
    void MapShouldBeMeetDimensionsValidTest() throws Exception {
        // GIVEN
        int mapDimension = 10;

        // WHEN
        MoonMap moonMap = this.mapGeneratorService.generateMap(mapDimension);

        // THEN
        assertEquals(moonMap.getPointList().size(), (int) pow(mapDimension, 2));
    }

    @Test
    void MapShouldBeMeetDimensionsFailTest() {
        // GIVEN
        int mapDimension = 0;

        // WHEN
        var exception = Assertions.assertThrows(Exception.class, () ->
                this.mapGeneratorService.generateMap(mapDimension));

        // THEN
        assertEquals("Map dimension cannot be <= 0", exception.getMessage());
    }

    @Test
    void MapShouldHaveNoMoreThanSquareObstaclesValidTest() throws Exception {
        // GIVEN
        int mapDimension = 5;

        // WHEN
        MoonMap moonMap = this.mapGeneratorService.generateMap(mapDimension);

        // THEN
        Map<Integer, List<WeightedPointDescription>> groupedPointsMap = moonMap
                .getPointList()
                .stream()
                .collect(Collectors.groupingBy(WeightedPointDescription::getY));

        assertTrue(groupedPointsMap
                .values()
                .stream()
                .anyMatch(pointDescriptionList -> pointDescriptionList
                        .stream()
                        .filter(WeightedPointDescription::isObstacle)
                        .count() <= floor(sqrt(mapDimension))));
    }

    @Test
    void verifyMaxObstaclesForLignIndexFailTest() throws Exception {
        // GIVEN
        List<WeightedPointDescription> pointList = new ArrayList<>();
        pointList.add(new WeightedPointDescription(0,0,true,0.0,0.0,null));
        pointList.add(new WeightedPointDescription(1,0,true,0.0,0.0,null));
        pointList.add(new WeightedPointDescription(2,0,true,0.0,0.0,null));
        pointList.add(new WeightedPointDescription(3,0,true,0.0,0.0,null));
        pointList.add(new WeightedPointDescription(4,0,false,0.0,0.0,null));
        // WHEN
        // sqrt(5) = 2. So this pointList exceeds max obstacles by 2.
        this.mapGeneratorService.generateMap(5);
        boolean result = this.mapGeneratorService.verifyMaxObstacles(pointList, 0);

        // THEN
        assertTrue(result);
    }

    @Test
    void verifyMaxObstaclesForLignIndexValidTest() throws Exception {
        // GIVEN
        List<WeightedPointDescription> pointList = new ArrayList<>();
        pointList.add(new WeightedPointDescription(0,0,true,0.0,0.0,null));
        pointList.add(new WeightedPointDescription(1,0,false,0.0,0.0,null));
        pointList.add(new WeightedPointDescription(2,0,false,0.0,0.0,null));
        pointList.add(new WeightedPointDescription(3,0,false,0.0,0.0,null));
        pointList.add(new WeightedPointDescription(4,0,false,0.0,0.0,null));

        // WHEN
        // sqrt(5) = 2. So this pointList doesnt exceed max obstacles
        this.mapGeneratorService.generateMap(5);
        boolean result = this.mapGeneratorService.verifyMaxObstacles(pointList, 0);

        // THEN
        assertFalse(result);
    }
}
