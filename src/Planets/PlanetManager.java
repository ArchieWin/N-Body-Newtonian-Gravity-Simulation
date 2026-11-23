package Planets;

import com.raylib.java.Raylib;
import com.raylib.java.raymath.Raymath;
import com.raylib.java.raymath.Vector3;

import java.util.ArrayList;
import java.util.Iterator;

public class PlanetManager {

    public static float gravitationalConstant = 1;
    public static float distanceMultiplier = 1;
    public static ArrayList<Planet> planets = new ArrayList<>();
    public static ArrayList<Planet> planetsToBeRemoved = new ArrayList<>();
    public static ArrayList<Planet> planetsToBeAdded = new ArrayList<>();

    public static void registerPlanet(Planet planet) {
        planetsToBeAdded.add(planet);
    }

    public static void updatePlanets(Raylib raylib) {
        //Handle collisions
        for (Planet planet : planets) {

            for (Planet otherPlanet : planets) {

                if (planet == otherPlanet) continue;
                if (planet.destroyed || otherPlanet.destroyed) continue;

                handleCollisions(planet, otherPlanet);
            }
        }

        //Apply gravity
        for (int i = 0; i < planets.size(); i++) {
            for (int j = i + 1; j < planets.size(); j++) {
                applyGravitationalForce(planets.get(i), planets.get(j), raylib);
            }
        }


        //Update the planets positions
        for (Planet planet : planets) {
            planet.updatePlanet();
        }
    }

    public static void drawPlanets() {

        //Add planets that should be added
        planets.addAll(planetsToBeAdded);
        planetsToBeAdded.clear();

        //Remove planets that have collided or been deleted
        for (Planet planet : planetsToBeRemoved) {
            planets.remove(planet);
        }

        planetsToBeRemoved.clear();

        for (Planet planet : planets) {
            planet.drawPlanet();
        }
    }

    public static void applyGravitationalForce(Planet planet1, Planet planet2, Raylib raylib) {

        int fps = raylib.core.GetFPS();



        Vector3 deltaVector = Raymath.Vector3Subtract(planet1.position, planet2.position);
        deltaVector = Raymath.Vector3Scale(deltaVector, distanceMultiplier);

        float distance = Raymath.Vector3Length(deltaVector);

        if (distance < 0.001f) {
            return;
        }

        Vector3 direction = Raymath.Vector3Normalize(deltaVector);

        float force = gravitationalConstant * planet1.mass * planet2.mass / (distance * distance + 0.01f);

        Vector3 planet1Acceleration = Raymath.Vector3Scale(direction, (force / planet1.mass) / fps);
        Vector3 planet2Acceleration = Raymath.Vector3Scale(direction, (force / planet2.mass) / fps);

        planet1.velocity = Raymath.Vector3Subtract(planet1.velocity, planet1Acceleration);
        planet2.velocity = Raymath.Vector3Add(planet2.velocity, planet2Acceleration);
    }

    public static void handleCollisions(Planet planet1, Planet planet2) {

        Vector3 deltaVector = Raymath.Vector3Subtract(planet1.position, planet2.position);
        float distance = Raymath.Vector3Length(deltaVector);

        if (distance < 0.001f) {
            return;
        }

        float biggestRadius = Math.max(planet1.radius, planet2.radius);

        if (distance > planet1.radius + planet2.radius) return;

        System.out.println(planet1.velocity.x + "," + planet1.velocity.y + "," + planet1.velocity.z);
        System.out.println(planet2.velocity.x + "," + planet2.velocity.y + "," + planet2.velocity.z);

        if (planet1.mass > planet2.mass) {

            Vector3 momentum1 = Raymath.Vector3Scale(planet1.velocity, planet1.mass);
            Vector3 momentum2 = Raymath.Vector3Scale(planet2.velocity, planet2.mass);

            Vector3 totalMomentum = Raymath.Vector3Add(momentum1, momentum2);

            float newMass = planet1.mass + planet2.mass;

            planet1.mass += planet2.mass;
            planet1.radius = (float) Math.cbrt(
                    Math.pow(planet1.radius, 3) + Math.pow(planet2.radius, 3)
            );
            planet1.velocity = Raymath.Vector3Scale(totalMomentum, 1.0f / newMass);

            planetsToBeRemoved.add(planet2);
            planet2.destroyed = true;
        } else {

            Vector3 momentum1 = Raymath.Vector3Scale(planet1.velocity, planet1.mass);
            Vector3 momentum2 = Raymath.Vector3Scale(planet2.velocity, planet2.mass);

            Vector3 totalMomentum = Raymath.Vector3Add(momentum1, momentum2);

            float newMass = planet1.mass + planet2.mass;

            planet2.mass += planet1.mass;
            planet2.radius += planet1.radius;
            planet2.velocity = Raymath.Vector3Scale(totalMomentum, 1.0f / newMass);

            planetsToBeRemoved.add(planet1);
            planet1.destroyed = true;
        }
    }

}
