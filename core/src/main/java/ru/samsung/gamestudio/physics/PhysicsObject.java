package ru.samsung.gamestudio.physics;

import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.utils.Disposable;

import java.util.ArrayList;

import static ru.samsung.gamestudio.GameSettings.SCALE;


public class PhysicsObject implements Disposable {

    private final Body body;
    private final ArrayList<Fixture> fixturesList;

    private PhysicsObject(Body body, ArrayList<Fixture> fixturesList, Object objectHolder) {
        this.body = body;
        this.fixturesList = fixturesList;

        for (Fixture fixture : this.fixturesList) {
            fixture.setUserData(objectHolder);
        }
    }

    public int getX() {
        return (int) (body.getPosition().x / SCALE);
    }

    public int getY() {
        return (int) (body.getPosition().y / SCALE);
    }

    @Override
    public void dispose() {
        for (Fixture fixture : fixturesList) {
            body.destroyFixture(fixture);
        }
    }

    public Body getBody() {
        return body;
    }

    public static class PhysicalObjectBuilder {
        private final Body body;
        private final ArrayList<Fixture> fixturesList;

        public PhysicalObjectBuilder(World world, BodyDef.BodyType bodyType) {
            BodyDef bodyDef = new BodyDef();
            bodyDef.type = bodyType;
            body = world.createBody(bodyDef);
            body.setLinearDamping(2f);
            fixturesList = new ArrayList<>();
        }

        public PhysicalObjectBuilder setInitialPosition(float x, float y) {
            body.setTransform(x * SCALE, y * SCALE, 0);
            return this;
        }

        public PhysicalObjectBuilder addCircularFixture(float radius, short categoryBits) {
            FixtureDef fixtureDef = new FixtureDef();
            CircleShape circleShape = new CircleShape();
            circleShape.setRadius(radius * SCALE);
            fixtureDef.shape = circleShape;
            fixtureDef.filter.categoryBits = categoryBits;
            fixturesList.add(body.createFixture(fixtureDef));
            circleShape.dispose();
            return this;
        }

        public PhysicalObjectBuilder addRectangularFixture(float width, float height, short categoryBits) {
            FixtureDef fixtureDef = new FixtureDef();
            PolygonShape polygonShape = new PolygonShape();
            polygonShape.setAsBox(width / 2 * SCALE, height / 2 * SCALE);
            fixtureDef.filter.categoryBits = categoryBits;
            fixtureDef.shape = polygonShape;
            fixturesList.add(body.createFixture(fixtureDef));
            polygonShape.dispose();
            return this;
        }

        public PhysicalObjectBuilder setBodyAsSensor() {
            for (Fixture fixture : fixturesList) {
                fixture.setSensor(true);
            }
            return this;
        }

        public PhysicsObject build(Object objectHolder) {
            return new PhysicsObject(body, fixturesList, objectHolder);
        }


    }
}
