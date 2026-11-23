import Planets.Planet;
import Planets.PlanetManager;
import com.raylib.java.Raylib;
import com.raylib.java.core.Color;
import com.raylib.java.raymath.Raymath;
import com.raylib.java.raymath.Vector3;

import java.util.Random;

public class PlanetRandomiser {

    private static final int spawnRadius = 1000;
    private static final float spawnProbability = 0.01F;
    private static final int spacing = 100;
    private static final Random random = new Random();

    public static void spawnRandomisedPlanets(Raylib raylib) {

        Vector3 cameraPos = Main.camera.camera.getPosition();

        Vector3 start = new Vector3(cameraPos.x - spawnRadius, cameraPos.y - spawnRadius, cameraPos.z - spawnRadius);
        Vector3 end = new Vector3(cameraPos.x + spawnRadius, cameraPos.y + spawnRadius, cameraPos.z + spawnRadius);

        for (float x = start.x; x < end.x; x += spacing) {

            for (float y = start.y; y < end.y; y += spacing) {

                for (float z = start.z; z < end.z; z += spacing) {

                    float randomProb = random.nextFloat(0, 1);

                    if (randomProb <= spawnProbability) {
                        float randomMass = random.nextInt(1, 50);
                        float randomRadius = randomMass / 2;
                        Color randomColour = new Color(random.nextInt(0, 256), random.nextInt(0, 256), random.nextInt(0, 256), 255);
                        Vector3 planetPos = new Vector3(x, y, z);
                        boolean spawnPlanet = true;

                        for (Planet planet : PlanetManager.planets) {
                            spawnPlanet = !Raymath.Vector3Equals(planet.position, planetPos);
                        }

                        if (spawnPlanet) {
                            new Planet(raylib, randomMass, randomRadius, randomColour, planetPos);
                        }

                    }

                }
            }


        }
    }
}
