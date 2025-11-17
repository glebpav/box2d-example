package ru.samsung.gamestudio.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import ru.samsung.gamestudio.MyGame;

public class MenuScreen extends ScreenAdapter {

    private final MyGame game;

    public MenuScreen(MyGame game) {
        this.game = game;
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        if (Gdx.input.justTouched()) {
            game.setScreen(game.gameScreen);
        }
    }
}
