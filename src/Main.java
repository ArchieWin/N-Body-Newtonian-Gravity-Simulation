import Planets.PlanetManager;
import com.raylib.java.Raylib;
import com.raylib.java.core.Color;

public class Main {

    public static boolean paused = true;
    public static boolean mouseEnabled = false;
    public static FlightCamera camera;
    public static Raylib raylib;

    public static void main(String[] args) {

        raylib = new Raylib();

        raylib.core.InitWindow(1600, 1000, "Astronomical Simulation");
        raylib.core.SetTargetFPS(60);

        camera = new FlightCamera(raylib);

        SimulationMenu.createMenu(raylib);

        while (!raylib.core.WindowShouldClose()) {

            //Take player input
            InputManager.takeInput(raylib, camera);

            //Update physics states
            if (!paused) {
                PlanetManager.updatePlanets(raylib);
            }

            //Handle mouse cursor and camera
            if (mouseEnabled) {
                raylib.core.EnableCursor();
            } else {
                raylib.core.DisableCursor();
            }

            if (!mouseEnabled) camera.updateCamera();

            //Draw the elements on screen
            drawElements();
        }

        raylib.core.CloseWindow();
    }

    public static void drawElements() {
        raylib.core.BeginDrawing();

        raylib.core.ClearBackground(Color.WHITE);

        raylib.core.BeginMode3D(camera.camera);

        // Ground grid
        raylib.models.DrawGrid(100, 1.0f);
        PlanetManager.drawPlanets();

        PlanetSelector.drawClickedPlanetWireFrame(raylib);

        raylib.core.EndMode3D();

        PlanetSelector.drawClickedPlanetData(raylib);

        if (paused) {
            raylib.text.DrawText("PAUSED", raylib.core.GetScreenWidth() - 100, 10, 20, Color.RED);
        }

        //Draw essential info
        raylib.text.DrawFPS(10, 10);
        raylib.text.DrawText("Planet Count: " + PlanetManager.planets.toArray().length, 10, 30, 20, Color.RED);
        raylib.text.DrawText("Gravitational Constant: " + PlanetManager.gravitationalConstant, 10, 50, 20, Color.RED);
        raylib.text.DrawText("Distance Multiplier: " + PlanetManager.distanceMultiplier, 10, 70, 20, Color.RED);
        raylib.text.DrawText("X Pos: " + camera.camera.getPosition().x, 10, 90, 20, Color.RED);
        raylib.text.DrawText("Y Pos: " + camera.camera.getPosition().y, 10, 110, 20, Color.RED);
        raylib.text.DrawText("Z Pos: " + camera.camera.getPosition().z, 10, 130, 20, Color.RED);

        raylib.core.EndDrawing();
    }
}