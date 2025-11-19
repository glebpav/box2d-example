package ru.samsung.gamestudio.component;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;

import static ru.samsung.gamestudio.GameSettings.SCREEN_HEIGHT;
import static ru.samsung.gamestudio.GameSettings.SCREEN_WIDTH;

public class BlackoutComponent extends ImageView {

    Animation<Texture> animation;
    long stateTime;

    public BlackoutComponent() {
        super(
            new Texture("textures/blackout.png"),
            SCREEN_WIDTH,
            SCREEN_HEIGHT,
            0,
            0
        );

        Array<Texture> frames = new Array<>();
        frames.add(texture);
        frames.add(null);
        frames.add(null);
        frames.add(texture);
        frames.add(null);
        frames.add(texture);

        animation = new Animation<>(0.2f, frames,  Animation.PlayMode.LOOP);
        stateTime = System.currentTimeMillis();
    }

    public void draw(Batch batch, int playerX) {
        texture = animation.getKeyFrame((System.currentTimeMillis() - stateTime) / 1000f);

        if (texture == null) {
            return;
        }

        x = playerX - width / 2;

        super.draw(batch);
    }
}
