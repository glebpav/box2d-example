package ru.samsung.gamestudio.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.utils.ScreenUtils;
import ru.samsung.gamestudio.MyGame;
import ru.samsung.gamestudio.physics.WorldManager;

import static ru.samsung.gamestudio.GameSettings.SCALE;

public class GameScreen extends ScreenAdapter {

    MyGame game;
    WorldManager worldManager;

    public GameScreen(MyGame game) {
        this.game = game;
        worldManager = new WorldManager();
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
        game.batch.begin();

        worldManager.player.draw(game.batch);
        game.batch.end();

        worldManager.mapRenderer.setView(game.camera);
        worldManager.mapRenderer.render();

        game.debugRenderer.render(worldManager.world, game.camera.combined.cpy().scl(1 / SCALE));
    }

    @Override
    public void dispose() {

    }

}
