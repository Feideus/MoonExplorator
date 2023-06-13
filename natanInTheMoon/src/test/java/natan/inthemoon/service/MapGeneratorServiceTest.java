package natan.inthemoon.service;

import natan.inthemoon.pojos.MoonMap;
import natan.inthemoon.pojos.PointDescription;
import natan.inthemoon.service.implentation.MapGeneratorService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static java.lang.Math.pow;
import static java.lang.Math.sqrt;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.InstanceOfAssertFactories.map;
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
        Map<Integer, List<PointDescription>> groupedPointsMap = moonMap
                .getPointList()
                .stream()
                .collect(Collectors.groupingBy(PointDescription::getY));

        assertTrue(groupedPointsMap
                .values()
                .stream()
                .anyMatch(pointDescriptionList -> pointDescriptionList
                        .stream()
                        .filter(PointDescription::isObstacle)
                        .count() <= sqrt(mapDimension)));
    }

    @Test
    void MapShouldHaveNoMoreThanSquareObstaclesFailTest() {
        fail("Not yet Implemented");
    }
}
