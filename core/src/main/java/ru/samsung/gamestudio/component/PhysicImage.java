package ru.samsung.gamestudio.component;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;

public class PhysicImage extends ImageView implements Hittable {

    private PhysicsObject physicsObject;

    public PhysicImage(Texture texture, int width, int height) {
        super(texture, width, height);
    }

    public void setPhysicsObject(PhysicsObject physicsObject) {
        this.physicsObject = physicsObject;
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
