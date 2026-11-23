import Planets.Planet;
import Planets.PlanetManager;
import com.raylib.java.Raylib;
import com.raylib.java.raymath.Raymath;
import com.raylib.java.raymath.Vector3;

import javax.swing.*;
import java.awt.*;

public class SimulationMenu {

    private static JFrame menu;
    private static JTextField radiusField;
    private static JTextField massField;
    private static JTextField gravitationalConstantField;
    private static JTextField xVelocityTextField;
    private static JTextField yVelocityTextField;
    private static JTextField zVelocityTextField;
    private static JTextField distanceMultiplierField;
    private static JButton createPlanetButton;
    private static JButton gravitationalConstantButton;
    private static JButton randomisePlanetsButton;
    private static JButton enterVelocityButton;
    private static JButton distanceMultiplierButton;

    public static float savedMass;
    public static float savedRadius;
    public static com.raylib.java.core.Color savedColour;

    public static void createMenu(Raylib raylib) {
        menu = new JFrame("Simulation Menu");
        menu.setSize(300, 400);
        menu.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        menu.setLayout(new FlowLayout());

        radiusField = new JTextField(20);
        massField = new JTextField(20);
        gravitationalConstantField = new JTextField(20);
        xVelocityTextField = new JTextField(20);
        yVelocityTextField = new JTextField(20);
        zVelocityTextField = new JTextField(20);
        distanceMultiplierField = new JTextField(20);

        createPlanetButton = new JButton("     Create Planet     ");
        gravitationalConstantButton = new JButton("Set Gravitational Constant");
        randomisePlanetsButton = new JButton("Spawn Random Planets");
        enterVelocityButton = new JButton("Enter Planet Velocity");
        distanceMultiplierButton = new JButton("Set Distance Multiplier");

        createPlanetCreatorLogic(raylib);
        createGravitationalConstantButtonLogic();
        createRandomPlanetSpawnLogic(raylib);
        createVelocitySetButtonLogic();
        createDistanceMultiplierLogic();

        menu.add(new JLabel("Radius:"));
        menu.add(radiusField);
        menu.add(new JLabel("Mass:"));
        menu.add(massField);
        menu.add(createPlanetButton);
        menu.add(new JLabel("Set Planet Velocity:"));
        menu.add(new JLabel("X:"));
        menu.add(xVelocityTextField);
        menu.add(new JLabel("Y:"));
        menu.add(yVelocityTextField);
        menu.add(new JLabel("Z:"));
        menu.add(zVelocityTextField);
        menu.add(enterVelocityButton);
        menu.add(new JLabel("Gravitational Constant:"));
        menu.add(gravitationalConstantField);
        menu.add(gravitationalConstantButton);
        menu.add(randomisePlanetsButton);
        menu.add(new JLabel("Distance Multiplier:"));
        menu.add(distanceMultiplierField);
        menu.add(distanceMultiplierButton);

        menu.setVisible(true);
    }

    public static void createVelocitySetButtonLogic() {
        enterVelocityButton.addActionListener(e -> {

            if (PlanetSelector.clickedPlanet == null) return;

            float xVel;
            float yVel;
            float zVel;

            if (xVelocityTextField.getText().isBlank()) {
                xVel = 0;
            } else {
                xVel = Float.parseFloat(xVelocityTextField.getText());
            }

            if (yVelocityTextField.getText().isBlank()) {
                yVel = 0;
            } else {
                yVel = Float.parseFloat(yVelocityTextField.getText());
            }

            if (zVelocityTextField.getText().isBlank()) {
                zVel = 0;
            } else {
                zVel = Float.parseFloat(zVelocityTextField.getText());
            }

            Vector3 newVel = new Vector3(xVel, yVel, zVel);
            newVel = Raymath.Vector3Scale(newVel, 1 / PlanetManager.distanceMultiplier);
            PlanetSelector.clickedPlanet.velocity = newVel;

        });
    }

    public static void createPlanetCreatorLogic(Raylib raylib) {
        createPlanetButton.addActionListener(e -> {
            float radius = Float.parseFloat(radiusField.getText());
            float mass = Float.parseFloat(massField.getText());
            Color colour = JColorChooser.showDialog(
                    menu,
                    "Choose the planets colour",
                    Color.WHITE
            );

            savedRadius = radius;
            savedMass = mass;
            savedColour = translateColour(colour);

            new Planet(raylib, mass, radius, translateColour(colour), Main.camera.camera.getPosition());
        });
    }

    public static void createGravitationalConstantButtonLogic() {
        gravitationalConstantButton.addActionListener(e -> {
            PlanetManager.gravitationalConstant = Float.parseFloat(gravitationalConstantField.getText());
        });
    }

    public static void createRandomPlanetSpawnLogic(Raylib raylib) {
        randomisePlanetsButton.addActionListener(e -> {
            PlanetRandomiser.spawnRandomisedPlanets(raylib);
        });
    }

    public static void createDistanceMultiplierLogic() {
        distanceMultiplierButton.addActionListener(e -> {
            PlanetManager.distanceMultiplier = Float.parseFloat(distanceMultiplierField.getText());
        });
    }

    public static com.raylib.java.core.Color translateColour(Color color) {
        int r = color.getRed();
        int g = color.getGreen();
        int b = color.getBlue();
        int a = color.getAlpha();

        return new com.raylib.java.core.Color(r, g, b, a);
    }

}
