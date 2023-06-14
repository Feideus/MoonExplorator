package natan.inthemoon.service.implentation;

import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import natan.inthemoon.pojos.MoonMap;
import natan.inthemoon.pojos.PointDescription;
import natan.inthemoon.service.abstraction.AbstractMapGeneratorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static java.lang.Math.*;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class MapGeneratorService implements AbstractMapGeneratorService {

    private int mapDimension;

    public MoonMap generateMap(final int mapDimension) throws Exception {
        if(mapDimension <= 0){
            throw new Exception("Map dimension cannot be <= 0");
        }

        this.mapDimension = mapDimension;
        MoonMap moonMap = MoonMap
                .builder()
                .dimension(mapDimension)
                .pointList(generatePointList())
                .build();

        return moonMap;
    }

    private List<PointDescription> generatePointList() {
        List<PointDescription> pointList = new ArrayList<>();
        // La map est carree, on a donc dimension^2 points.
        for (int i = 0; i < this.mapDimension * mapDimension; i++) {
            boolean lignHasMaxObstacles = verifyMaxObstacles(pointList, i / this.mapDimension);
            pointList.add(generatePoint(i, lignHasMaxObstacles));
        }
        return pointList;
    }

    private PointDescription generatePoint(final int i, final boolean lignHasMaxObstacles) {
        boolean pointHasObstacle = false;
        if (!lignHasMaxObstacles) {
            pointHasObstacle = computeObstacle();
        }
        return PointDescription
                .builder()
                .y(i / this.mapDimension)
                .x(i % mapDimension)
                .obstacle(pointHasObstacle)
                .build();
    }

    private boolean computeObstacle() {
        return new Random().nextDouble() <= 1 / sqrt(this.mapDimension);
    }

    public boolean verifyMaxObstacles(final List<PointDescription> pointDescriptionList, final int lignIndex) {
        return pointDescriptionList
                .stream()
                .filter(pointDescription -> pointDescription.getY() == lignIndex)
                .filter(PointDescription::isObstacle)
                .count() >= floor(sqrt(this.mapDimension));
    }

}
