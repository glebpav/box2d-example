package ru.samsung.gamestudio;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2D;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import ru.samsung.gamestudio.physics.ContactManager;
import ru.samsung.gamestudio.screens.GameScreen;
import ru.samsung.gamestudio.screens.MenuScreen;

import static ru.samsung.gamestudio.GameSettings.*;

public class MyGame extends Game {

    public Box2DDebugRenderer debugRenderer;
    public OrthographicCamera camera;
    public Batch batch;

    public MenuScreen menuScreen;
    public GameScreen gameScreen;

    @Override
    public void create() {
        batch = new SpriteBatch();
        camera = new OrthographicCamera();
        camera.setToOrtho(false, 1280, 720);

        Box2D.init();
        debugRenderer = new Box2DDebugRenderer();

        menuScreen = new MenuScreen(this);
        gameScreen = new GameScreen(this);

        setScreen(menuScreen);
    }

}
