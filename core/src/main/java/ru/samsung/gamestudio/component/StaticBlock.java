package ru.samsung.gamestudio.component;

import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.World;
import ru.samsung.gamestudio.physics.PhysicsObject;

public class StaticBlock {

    PhysicsObject physicsObject;

    public StaticBlock(float x, float y, float width, float height, World world) {
        physicsObject = new PhysicsObject.PhysicalObjectBuilder(world, BodyDef.BodyType.StaticBody)
            .addRectangularFixture(width, height, (short) 4)
            .setInitialPosition(x + width / 2, y + height / 2)
            .build(this);
    }

}
