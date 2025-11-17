package ru.samsung.gamestudio.component;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.World;
import ru.samsung.gamestudio.physics.PhysicsObject;

public class Player extends ImageView implements Hittable {

    public PhysicsObject physicsObject;

    public Player(Texture texture, int width, int height, World world) {
        super(texture, width, height);
        physicsObject = new PhysicsObject.PhysicalObjectBuilder(world, BodyDef.BodyType.DynamicBody)
                .addCircularFixture((float) Math.max(width, height) / 2, (short) 1)
                .setInitialPosition(600, 600)
                .build(this);
    }

    public void moveLeft() {
        physicsObject.getBody().applyForceToCenter(
            new Vector2(-10, 0), true
        );
    }

    public void moveRight() {
        physicsObject.getBody().applyForceToCenter(
            new Vector2(10, 0), true
        );
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
        super.draw(batch);
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
