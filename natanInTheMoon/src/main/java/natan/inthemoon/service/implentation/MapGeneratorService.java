package natan.inthemoon.service.implentation;

import lombok.RequiredArgsConstructor;
import natan.inthemoon.enums.ExceptionLabel;
import natan.inthemoon.exception.NatanInTheMoonException;
import natan.inthemoon.pojos.MoonMap;
import natan.inthemoon.pojos.PointDescription;
import natan.inthemoon.pojos.WeightedPointDescription;
import natan.inthemoon.service.abstraction.AbstractMapGeneratorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static java.lang.Math.floor;
import static java.lang.Math.sqrt;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class MapGeneratorService implements AbstractMapGeneratorService {

    // side length for the map
    private int mapDimension;

    /**
     * Generation of the map
     *
     * @param mapDimension length of the side of the map
     * @return
     */
    public MoonMap generateMap(final int mapDimension) {
        if (mapDimension <= 0) {
            throw new NatanInTheMoonException(HttpStatus.INTERNAL_SERVER_ERROR,
                    ExceptionLabel.NEGATIVE_OR_NULL_MAP_DIMENSION);
        }

        this.mapDimension = mapDimension;

        return MoonMap
                .builder()
                .dimension(mapDimension)
                .pointList(generatePointList())
                .build();
    }

    /**
     * Generation of positions on the map.
     * Obstacles are distributed randomly
     *
     * @return
     */
    private List<WeightedPointDescription> generatePointList() {
        List<WeightedPointDescription> pointList = new ArrayList<>();
        // La map est carree, on a donc dimension^2 points.
        for (int i = 0; i < mapDimension * mapDimension; i++) {
            boolean lignHasMaxObstacles = verifyMaxObstacles(pointList, i / this.mapDimension);
            pointList.add(generatePoint(i, lignHasMaxObstacles));
        }
        return pointList;
    }

    /**
     * PointDescription generation
     * Heuristics and cost are unset at creation
     *
     * @param i
     * @param lignHasMaxObstacles
     * @return
     */
    private WeightedPointDescription generatePoint(final int i, final boolean lignHasMaxObstacles) {
        boolean pointHasObstacle = false;
        if (!lignHasMaxObstacles) {
            pointHasObstacle = computeObstacle();
        }
        return new WeightedPointDescription(i % mapDimension, i / this.mapDimension, pointHasObstacle, 0.0, 0.0, null);
    }

    /**
     * Computing the presence of an obstable on the position
     *
     * @return
     */
    private boolean computeObstacle() {
        return new Random().nextDouble() <= 1 / sqrt(this.mapDimension);
    }

    /**
     * Check if lign has reached max capacity for number of obstacles
     *
     * @param pointDescriptionList
     * @param lignIndex
     * @return
     */
    public boolean verifyMaxObstacles(final List<WeightedPointDescription> pointDescriptionList, final int lignIndex) {
        return pointDescriptionList
                .stream()
                .filter(pointDescription -> pointDescription.getY() == lignIndex)
                .filter(PointDescription::isObstacle)
                .count() >= floor(sqrt(this.mapDimension));
    }

}
