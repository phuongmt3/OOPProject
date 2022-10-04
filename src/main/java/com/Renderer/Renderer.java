package com.Renderer;

import javafx.scene.image.Image;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;

public abstract class Renderer {
    protected static Image sheet;
    protected final double scale = 2.0;
    protected final double side = 16 * scale;
    protected int oldDir = -1;
    public static enum Direction {
        center, left, right, up, down, middlerow, middlecol
    };

    public Renderer() {
        if (sheet == null) {
            try (InputStream stream = Files.newInputStream(Path.of("src/main/java/res/textures/SpriteSheet.png"))) {
                sheet = new Image(stream, 256 * scale, 256 * scale, true, false);
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
    }

}