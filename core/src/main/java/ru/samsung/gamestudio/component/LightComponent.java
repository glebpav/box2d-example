package ru.samsung.gamestudio.component;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;

public class LightComponent extends ImageView{
    public LightComponent() {
        super(
            new Texture("textures/light-circle.png"),
            3000,
            3000
        );
    }

    public void draw(Batch batch, int playerX, int playerY) {
        x = playerX - width / 2;
        y = playerY - height / 2;
        super.draw(batch);
    }

}
