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

import static ru.samsung.gamestudio.GameSettings.ENEMY_WALK_LENGTH;


public class Enemy {

    enum State {IDLE, RUNNING, GET_DAMAGE, DEAD}

    enum WalkDirection {LEFT, RIGHT}

    PhysicsObject physicsObject;

    float width;
    float height;

    private Boolean shouldFlip;
    private State state;
    private long animationStartTime;

    float initialX;
    private WalkDirection walkDirection;

    Animation<TextureRegion> idleAnimation;
    Animation<TextureRegion> runAnimation;
    Animation<TextureRegion> deadAnimation;

    public Enemy(float width, float height, float initialX, float initialY, World world) {

        this.width = width;
        this.height = height;
        this.initialX = initialX;

        physicsObject = new PhysicsObject.PhysicalObjectBuilder(world, BodyDef.BodyType.DynamicBody)
            .addRectangularFixture(width, height, (short) 2)
            .setInitialPosition(initialX, initialY)
            .build(this);

        createAnimation();

        shouldFlip = false;
        walkDirection = WalkDirection.LEFT;
        state = State.IDLE;
        animationStartTime = System.currentTimeMillis();
    }

    private void createAnimation() {
        Texture texture = new Texture("textures/player/player_tileset_2.png");
        Array<TextureRegion> frames = new Array<>();

        for (int i = 0; i < 6; i++) {
            frames.add(
                new TextureRegion(texture, i * 90, 0, 90, 96)
            );
        }
        idleAnimation = new Animation<>(0.15f, frames, Animation.PlayMode.LOOP);
        runAnimation = new Animation<>(0.15f, frames, Animation.PlayMode.LOOP);
        deadAnimation = new Animation<>(0.15f, frames, Animation.PlayMode.LOOP);
        frames.clear();
    }

    public void moveLeft() {
        physicsObject.getBody().applyForceToCenter(
            new Vector2(-5, 0), true
        );
        state = State.RUNNING;
        shouldFlip = false;
    }

    public void moveRight() {
        physicsObject.getBody().applyForceToCenter(
            new Vector2(5, 0), true
        );
        state = State.RUNNING;
        shouldFlip = true;
    }

    private void updateWalk() {
        if (walkDirection == WalkDirection.LEFT) {
            moveLeft();
        } else if (walkDirection == WalkDirection.RIGHT) {
            moveRight();
        }

        if (physicsObject.getX() < initialX - ENEMY_WALK_LENGTH) {
            walkDirection = WalkDirection.RIGHT;
        } else if (physicsObject.getX() > initialX) {
            walkDirection = WalkDirection.LEFT;
        }
    }

    public void draw(Batch batch) {

        if (state == State.DEAD) {
            physicsObject.destroy();
            return;
        }

        updateWalk();

        long time = (System.currentTimeMillis() - animationStartTime) / 100;
        TextureRegion region;
        if (state == State.RUNNING) {
            region = runAnimation.getKeyFrame(time);
        } else if (state == State.GET_DAMAGE) {
            region = deadAnimation.getKeyFrame(time);
        } else {
            region = idleAnimation.getKeyFrame(time);
        }
        if (shouldFlip == region.isFlipX()) {
            region.flip(true, false);
        }

        batch.draw(
            region,
            physicsObject.getX() - width / 2,
            physicsObject.getY() - height / 2,
            width,
            height
        );
    }

    public void onTouchPlayer() {
        state = State.DEAD;
    }

}
