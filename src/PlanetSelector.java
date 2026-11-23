import Planets.Planet;
import Planets.PlanetManager;
import com.raylib.java.Raylib;
import com.raylib.java.core.Color;
import com.raylib.java.core.ray.Ray;
import com.raylib.java.core.ray.RayCollision;
import com.raylib.java.core.rcamera.Camera3D;

import java.util.ArrayList;
import java.util.HashMap;

public class PlanetSelector {

    public static Planet clickedPlanet;

    public static void clickPlanet(Raylib raylib, Camera3D camera) {
        Ray ray = raylib.core.GetMouseRay(raylib.core.GetMousePosition(), camera);

        Planet clickedPlanet1 = null;
        float clickedPlanetDistance = -1;

        for (Planet planet : PlanetManager.planets) {
            RayCollision collision = raylib.models.GetRayCollisionSphere(
                    ray,
                    planet.position,
                    planet.radius
            );

            if (collision.hit) {

                if (clickedPlanet1 == null || clickedPlanetDistance == -1 || clickedPlanetDistance > collision.distance) {
                    clickedPlanet1 = planet;
                    clickedPlanetDistance = collision.distance;
                }


            }
        }

        clickedPlanet = clickedPlanet1;

    }

    public static void drawClickedPlanetData(Raylib raylib) {
        if (clickedPlanet == null) return;

        if (clickedPlanet.destroyed) {
            clickedPlanet = null;
            return;
        }

        raylib.text.DrawText("Planet Information:", 10, 870, 20, Color.RED);
        raylib.text.DrawText("Mass: " + clickedPlanet.mass, 10, 890, 20, Color.RED);
        raylib.text.DrawText("Radius: " + clickedPlanet.radius, 10, 910, 20, Color.RED);
        raylib.text.DrawText("Position: X: " + clickedPlanet.position.x + " Y: " + clickedPlanet.position.y + " Z: " + clickedPlanet.position.z, 10, 930, 20, Color.RED);
        raylib.text.DrawText("Velocity: X: " + clickedPlanet.velocity.x + " Y: " + clickedPlanet.velocity.y + " Z: " + clickedPlanet.velocity.z, 10, 950, 20, Color.RED);
    }

    public static void drawClickedPlanetWireFrame(Raylib raylib) {
        if (clickedPlanet == null) return;

        if (clickedPlanet.destroyed) {
            clickedPlanet = null;
            return;
        }

        raylib.models.DrawSphereWires(
                clickedPlanet.position,
                clickedPlanet.radius * 1.05f,
                16,
                16,
                Color.RED
        );
    }

    public static Planet getSelectedPlanet() {
        if (clickedPlanet == null) return null;

        return clickedPlanet;
    }

}
