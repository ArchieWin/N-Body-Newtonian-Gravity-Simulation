import Planets.Planet;
import Planets.PlanetManager;
import com.raylib.java.Raylib;
import com.raylib.java.core.input.Keyboard;

public class InputManager {

    public static void takeInput(Raylib raylib, FlightCamera camera) {
        if (raylib.core.IsKeyPressed(Keyboard.KEY_P)) {
            Main.paused = !Main.paused;
        }

        if (raylib.core.IsKeyPressed(Keyboard.KEY_M)) {
            Main.mouseEnabled = !Main.mouseEnabled;
        }

        if (raylib.core.IsKeyPressed(Keyboard.KEY_DELETE)) {
            if (PlanetSelector.getSelectedPlanet() != null) {
                PlanetManager.planetsToBeRemoved.add(PlanetSelector.getSelectedPlanet());
            }
        }

        if (raylib.core.IsKeyPressed(Keyboard.KEY_E)) {
            if (SimulationMenu.savedColour != null) {
                new Planet(raylib, SimulationMenu.savedMass, SimulationMenu.savedRadius, SimulationMenu.savedColour, camera.camera.getPosition());
            }
        }

        if (Main.mouseEnabled) {
            raylib.core.EnableCursor();
        }

        if (raylib.core.IsMouseButtonPressed(0)) {
            PlanetSelector.clickPlanet(raylib, camera.camera);
        }
    }

}
