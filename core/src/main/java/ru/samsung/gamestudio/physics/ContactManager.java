package ru.samsung.gamestudio.physics;

import com.badlogic.gdx.physics.box2d.*;
import ru.samsung.gamestudio.component.Hittable;

public class ContactManager implements ContactListener {

    @Override
    public void beginContact(Contact contact) {

        Fixture fixtureA = contact.getFixtureA();
        Fixture fixtureB = contact.getFixtureB();

        System.out.println("Categorial bit a: " + fixtureA.getFilterData().categoryBits);
        System.out.println("User data a     : " + fixtureA.getUserData());
        System.out.println("Categorial bit b: " + fixtureB.getFilterData().categoryBits);
        System.out.println("User data b     : " + fixtureB.getUserData());

        if (
            fixtureA.getUserData() instanceof Hittable
                && fixtureB.getUserData() instanceof Hittable
        ) {
            Hittable hittable1 = (Hittable) fixtureA.getUserData();
            Hittable hittable2 = (Hittable) fixtureB.getUserData();
            hittable1.onHit();
            hittable2.onHit();
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
