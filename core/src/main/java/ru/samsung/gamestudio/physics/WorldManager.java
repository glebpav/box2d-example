package ru.samsung.gamestudio.physics;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.renderers.OrthoCachedTiledMapRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import ru.samsung.gamestudio.component.Player;
import ru.samsung.gamestudio.component.StaticBlock;

import static ru.samsung.gamestudio.GameSettings.*;

public class WorldManager {

    public OrthoCachedTiledMapRenderer mapRenderer;
    public World world;

    public Player player;

    float accumulator;

    public WorldManager() {

        world = new World(new Vector2(0, -10), true);
        world.setContactListener(new ContactManager());

        MapManager mapManager = new MapManager("levels/level1.tmx");
        mapRenderer = new OrthoCachedTiledMapRenderer(mapManager.getMap(), mapManager.getTileScale());
        mapRenderer.setBlending(true);

        buildWorld(mapManager);
    }

    public void buildWorld(MapManager mapManager) {
        float tileScale = mapManager.getTileScale();


        for (
            RectangleMapObject object :
            mapManager.getMap().getLayers().get("Wall_colliders").getObjects().getByType(RectangleMapObject.class)
        ) {
            Rectangle rect = object.getRectangle();
            new StaticBlock(
                rect.x * tileScale,
                rect.y * tileScale,
                rect.width * tileScale,
                rect.height * tileScale,
                world
            );
        }

        for (
            RectangleMapObject object :
            mapManager.getMap().getLayers().get("Actors").getObjects().getByType(RectangleMapObject.class)
        ) {
            Rectangle rect = object.getRectangle();

            if (object.getName().equals("player")) {
                player = new Player(new Texture("falling.png"), (int) rect.width, (int) rect.height, world);
            }
        }
        /*
        for (
            RectangleMapObject object :
            mapManager.getMap().getLayers().get(INTERACTIVE_OBJECTS.getName()).getObjects().getByType(RectangleMapObject.class)
        ) {
            Rectangle rect = object.getRectangle();

            if (object.getName().equals(MapObjects.PIT.getName())) {
                new PitBlock(world, rect, onLoseListener);
            } else if (object.getName().equals(MapObjects.FINISH.getName())) {
                FinishLine finishLine = new FinishLine(world, rect, onWinListener, tileScale);
                actorsList.add(finishLine);
                disposablesList.add(finishLine);
            } else if (object.getName().equals(MapObjects.COIN.getName())) {
                Coin coin = new Coin(world, rect, onScoreEarnedListener, onRemoveBodyListener, tileScale);
                actorsList.add(coin);
                disposablesList.add(coin);
            } else if (object.getName().equals(MapObjects.LADDER.getName())) {
                new Ladder(world, rect);
            } else if (object.getName().equals(MapObjects.BONUS.getName())) {
                BonusBlock bonusBlock = new BonusBlock(world, rect, onRemoveBodyListener, onScoreEarnedListener, tileScale);
                actorsList.add(bonusBlock);
                disposablesList.add(bonusBlock);
            }

        }*/
    }

    public void stepWorld() {
        float delta = Gdx.graphics.getDeltaTime();
        accumulator += Math.min(delta, 0.25f);

        if (accumulator >= STEP_TIME) {
            accumulator -= STEP_TIME;
            world.step(STEP_TIME, VELOCITY_ITERATIONS, POSITION_ITERATIONS);
        }
    }

}
