package ru.samsung.gamestudio.screens;

import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.utils.ScreenUtils;
import ru.samsung.gamestudio.MyGame;
import ru.samsung.gamestudio.component.PhysicImage;
import ru.samsung.gamestudio.component.PhysicsObject;

import static ru.samsung.gamestudio.GameSettings.SCALE;

public class GameScreen extends ScreenAdapter {

    PhysicImage fallingComponent;
    PhysicImage groundComponent;

    MyGame game;

    public GameScreen(MyGame game) {

        this.game = game;

        fallingComponent = new PhysicImage(new Texture("falling.png"), 60, 60);
        fallingComponent.setPhysicsObject(
            new PhysicsObject.PhysicalObjectBuilder(game.world, BodyDef.BodyType.DynamicBody)
                .addCircularFixture(30f, (short) 1)
                .setInitialPosition(600, 600)
                .build(fallingComponent)
        );

        groundComponent = new PhysicImage(new Texture("ground.png"), 1000, 40);
        groundComponent.setPhysicsObject(
            new PhysicsObject.PhysicalObjectBuilder(game.world, BodyDef.BodyType.StaticBody)
                .addRectangularFixture(1000f, 40f, (short) 2)
                .setInitialPosition(600f, 100)
                .build(groundComponent)
        );

    }

    @Override
    public void render(float delta) {

        game.stepWorld();

        game.batch.setProjectionMatrix(game.camera.combined);

        ScreenUtils.clear(Color.CLEAR);
        game.batch.begin();
        groundComponent.draw(game.batch);
        fallingComponent.draw(game.batch);
        game.batch.end();

        game.debugRenderer.render(game.world, game.camera.combined.cpy().scl(1 / SCALE));
    }

    @Override
    public void dispose() {

    }

}
