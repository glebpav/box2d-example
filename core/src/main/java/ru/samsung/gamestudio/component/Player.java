package ru.samsung.gamestudio.component;


import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import ru.samsung.gamestudio.physics.PhysicsObject;

import static ru.samsung.gamestudio.GameSettings.COUNT_OF_LIVES;

public class Player extends ImageView {

    enum State {IDLE, RUNNING, ATTACHING}

    private Animation<TextureRegion> idelAnimation;
    private Animation<TextureRegion> runAnimation;
    private State state;

    private int countOfLives;

    public PhysicsObject physicsObject;
    long animationStartTime;
    boolean shouldFlip = false;
    private boolean isTouchingFlor;

    public Player(Texture texture, int width, int height, World world, float tileScale) {
        super(texture, width, height);
        physicsObject = new PhysicsObject.PhysicalObjectBuilder(world, BodyDef.BodyType.DynamicBody)
            .addCircularFixture((float) Math.max(width, height) / 2, (short) 1)
            .setInitialPosition(600, 600)
            .build(this);

        createAnimation();
        animationStartTime = System.currentTimeMillis();
        state = State.IDLE;
        isTouchingFlor = true;

        countOfLives = COUNT_OF_LIVES;

    }

    private void createAnimation() {

        Texture texture = new Texture("textures/player/player_tileset.png");

        Array<TextureRegion> frames = new Array<>();

        for (int i = 0; i < 6; i++) {
            frames.add(
                new TextureRegion(texture, i * 48, 0, 48, 48)
            );
        }
        idelAnimation = new Animation<TextureRegion>(0.15f, frames, Animation.PlayMode.LOOP);
        frames.clear();

        for (int i = 0; i < 6; i++) {
            frames.add(
                new TextureRegion(texture, i * 48, 48, 48, 48)
            );
        }
        runAnimation = new Animation<>(0.15f, frames, Animation.PlayMode.LOOP);
    }

    public void moveLeft() {
        physicsObject.getBody().applyForceToCenter(
            new Vector2(-10, 0), true
        );
        state = State.RUNNING;
        shouldFlip = false;
    }

    public void moveRight() {
        physicsObject.getBody().applyForceToCenter(
            new Vector2(10, 0), true
        );
        state = State.RUNNING;
        shouldFlip = true;
    }

    public void moveUp() {
        if (isTouchingFlor == false) {
            return;
        }

        physicsObject.getBody().setLinearVelocity(new Vector2(
            physicsObject.getBody().getLinearVelocity().x, 5)
        );

        isTouchingFlor = false;
    }

    @Override
    public void draw(Batch batch) {

        x = physicsObject.getX() - width / 2;
        y = physicsObject.getY() - height / 2;

        long time = (System.currentTimeMillis() - animationStartTime) / 100;
        TextureRegion region;
        if (state == State.RUNNING) {
            region = runAnimation.getKeyFrame(time);
        } else {
            region = idelAnimation.getKeyFrame(time);
        }
        if (shouldFlip == region.isFlipX()) {
            region.flip(true, false);
        }

        batch.draw(region, x, y, width, height);
    }

    @Override
    public void dispose() {
        super.dispose();
        physicsObject.dispose();
    }

    public void onTouchFlour() {
        System.out.println("Player has touched flor");
        isTouchingFlor = true;
    }

    public void onTouchEnemy() {
        if (state != State.ATTACHING) {
            countOfLives -= 1;
            System.out.println("Left count of lives: " + countOfLives);
        }
    }
}
