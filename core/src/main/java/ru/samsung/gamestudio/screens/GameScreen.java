package ru.samsung.gamestudio.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.utils.ScreenUtils;
import ru.samsung.gamestudio.MyGame;
import ru.samsung.gamestudio.component.BlackoutComponent;
import ru.samsung.gamestudio.component.LightComponent;
import ru.samsung.gamestudio.physics.WorldManager;

import static ru.samsung.gamestudio.GameSettings.SCALE;

public class GameScreen extends ScreenAdapter {

    MyGame game;
    WorldManager worldManager;
    LightComponent lightComponent;
    BlackoutComponent blackoutComponent;

    public GameScreen(MyGame game) {
        this.game = game;
        worldManager = new WorldManager();
        lightComponent = new LightComponent();
        blackoutComponent = new BlackoutComponent();
    }

    public void handleInput() {
        if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
            worldManager.player.moveLeft();
        }
        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
            worldManager.player.moveRight();
        }
        if (Gdx.input.isKeyPressed(Input.Keys.UP)) {
            worldManager.player.moveUp();
        }
    }

    @Override
    public void render(float delta) {

        handleInput();

        worldManager.stepWorld();

        game.camera.position.x = worldManager.player.physicsObject.getX();
        game.camera.update();
        game.batch.setProjectionMatrix(game.camera.combined);

        ScreenUtils.clear(Color.CLEAR);

        worldManager.mapRenderer.setView(game.camera);
        worldManager.mapRenderer.render();

        game.batch.begin();

        worldManager.player.draw(game.batch);

        for (int i = 0; i < worldManager.enemyList.size(); i++) {
            worldManager.enemyList.get(i).draw(game.batch);
        }

        /*lightComponent.draw(
            game.batch,
            worldManager.player.physicsObject.getX(),
            worldManager.player.physicsObject.getY()
        );*/
        /*blackoutComponent.draw(
            game.batch,
            worldManager.player.physicsObject.getX()
        );*/
        game.batch.end();


        game.debugRenderer.render(worldManager.world, game.camera.combined.cpy().scl(1 / SCALE));
    }

    @Override
    public void dispose() {

    }

}
