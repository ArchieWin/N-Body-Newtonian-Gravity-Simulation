package Planets;

import com.raylib.java.Raylib;
import com.raylib.java.core.Color;
import com.raylib.java.raymath.Raymath;
import com.raylib.java.raymath.Vector3;

import javax.swing.text.Position;

public class Planet {

    private final Raylib raylib;

    public float mass;
    public float radius;
    public boolean destroyed;
    private final Color color;

    public Vector3 position;
    public Vector3 velocity = new Vector3(0, 0, 0);

    public Planet(Raylib raylib, float mass, float radius, Color color, Vector3 position) {
        this.raylib = raylib;
        this.mass = mass;
        this.radius = radius;
        this.color = color;
        this.position = position;
        this.destroyed = false;

        PlanetManager.registerPlanet(this);
    }

    public void drawPlanet() {
        raylib.models.DrawSphere(position, radius, color);
    }


    public void updatePlanet() {
        int fps = raylib.core.GetFPS();

        position = Raymath.Vector3Add(position, new Vector3(velocity.x / fps, velocity.y / fps, velocity.z / fps));
    }

}