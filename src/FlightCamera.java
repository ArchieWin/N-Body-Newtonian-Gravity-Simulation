import com.raylib.java.Raylib;
import com.raylib.java.core.input.Keyboard;
import com.raylib.java.core.ray.Ray;
import com.raylib.java.core.rcamera.Camera3D;
import com.raylib.java.raymath.Raymath;
import com.raylib.java.raymath.Vector2;
import com.raylib.java.raymath.Vector3;

import java.util.Vector;

public class FlightCamera {

    private Raylib raylib;

    Camera3D camera;

    private float yaw = 0.0F;
    private float pitch = 0.0F;

    private final float mouseSensitivity = 0.002F;
    private float fov = 60.0F;
    private float speed = 10.0F;
    private float fastSpeed = 100.0F;
    private float slowSpeed = 10.0F;

    private Vector3 position = new Vector3(0, 0, 0);
    private Vector3 target = new Vector3(0, 2, 0);
    private Vector3 up = new Vector3(0, 1, 0);
    private Vector2 lastMousePos = new Vector2(0, 0);

    public FlightCamera(Raylib raylib) {
        this.raylib = raylib;

        camera = new Camera3D(
                raylib,
                position,
                target,
                up,
                fov,
                Camera3D.CameraProjection.CAMERA_PERSPECTIVE
        );

        raylib.core.DisableCursor();
        lastMousePos = raylib.core.GetMousePosition();
    }

    public void updateCamera() {
        Vector2 currentMousePos = raylib.core.GetMousePosition();

        Vector2 mouseDelta = new Vector2(
                currentMousePos.x - lastMousePos.x,
                currentMousePos.y - lastMousePos.y
        );

        lastMousePos = currentMousePos;

        yaw -= mouseDelta.x * mouseSensitivity;
        pitch -= mouseDelta.y * mouseSensitivity;

        pitch = Raymath.Clamp(
                pitch,
                -(float)Math.PI / 2.0f + 0.01f,
                (float)Math.PI / 2.0f - 0.01f
        );

        Vector3 forward = new Vector3(
                (float) (Math.cos(pitch) * Math.sin(yaw)),
                (float) (Math.sin(pitch)),
                (float) (Math.cos(pitch) * Math.cos(yaw))
        );

        forward = Raymath.Vector3Normalize(forward);
        Vector3 right = Raymath.Vector3Normalize(Raymath.Vector3CrossProduct(forward, up));

        calculateMovement(forward, right);
    }

    public void calculateMovement(Vector3 forward, Vector3 right) {

        speed = slowSpeed;

        if (raylib.core.IsKeyDown(Keyboard.KEY_LEFT_SHIFT)) {
            speed = fastSpeed;
        }

        float frameSpeed = speed * raylib.core.GetFrameTime();

        if (raylib.core.IsKeyDown(Keyboard.KEY_W)) {
            position = Raymath.Vector3Add(position, Raymath.Vector3Scale(forward, frameSpeed));
        }

        if (raylib.core.IsKeyDown(Keyboard.KEY_S)) {
            position = Raymath.Vector3Subtract(position, Raymath.Vector3Scale(forward, frameSpeed));
        }

        if (raylib.core.IsKeyDown(Keyboard.KEY_D)) {
            position = Raymath.Vector3Add(position, Raymath.Vector3Scale(right, frameSpeed));
        }

        if (raylib.core.IsKeyDown(Keyboard.KEY_A)) {
            position = Raymath.Vector3Subtract(position, Raymath.Vector3Scale(right, frameSpeed));
        }



        camera.setTarget(Raymath.Vector3Add(position, forward));
        camera.setPosition(position);
    }

}
