package ru.samsung.gamestudio.physics;

import com.badlogic.gdx.physics.box2d.*;
import ru.samsung.gamestudio.component.Enemy;
import ru.samsung.gamestudio.component.Player;
import ru.samsung.gamestudio.component.StaticBlock;

public class ContactManager implements ContactListener {

    @Override
    public void beginContact(Contact contact) {

        Fixture fixtureA = contact.getFixtureA();
        Fixture fixtureB = contact.getFixtureB();

        Object object1 = fixtureA.getUserData();
        Object object2 = fixtureB.getUserData();

        if (object1 instanceof Player && object2 instanceof StaticBlock) {
            ((Player) object1).onTouchFlour();
        } else if (object1 instanceof StaticBlock && object2 instanceof Player) {
            ((Player) object2).onTouchFlour();
        }

        if (object1 instanceof Player && object2 instanceof Enemy) {
            ((Player) object1).onTouchEnemy();
            ((Enemy) object2).onTouchPlayer();
        } else if (object1 instanceof Enemy && object2 instanceof Player) {
            ((Player) object2).onTouchEnemy();
            ((Enemy) object1).onTouchPlayer();
        }

    }

    @Override
    public void endContact(Contact contact) {

    }

    @Override
    public void preSolve(Contact contact, Manifold oldManifold) {

    }

    @Override
    public void postSolve(Contact contact, ContactImpulse impulse) {

    }

}
