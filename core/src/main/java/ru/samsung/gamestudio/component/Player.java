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

public class Player extends ImageView implements Hittable {

    enum State {IDLE, RUNNING}

    private Animation<TextureRegion> idelAnimation;
    private Animation<TextureRegion> runAnimation;
    private State state;

    public PhysicsObject physicsObject;
    long animationStartTime;
    boolean shouldFlip = false;

    public Player(Texture texture, int width, int height, World world, float tileScale) {
        super(texture, width, height);
        physicsObject = new PhysicsObject.PhysicalObjectBuilder(world, BodyDef.BodyType.DynamicBody)
            .addCircularFixture((float) Math.max(width, height) / 2, (short) 1)
            .setInitialPosition(600, 600)
            .build(this);

        createAnimation();
        animationStartTime = System.currentTimeMillis();
        state = State.IDLE;

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
        physicsObject.getBody().setLinearVelocity(new Vector2(
            physicsObject.getBody().getLinearVelocity().x, 10)
        );
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

    @Override
    public void onHit() {
        System.out.println("From on hit in PhysicImage");
    }
}
