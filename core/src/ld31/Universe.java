package ld31;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import ld31.entities.Moon;
import ld31.entities.Planet;
import ld31.entities.Ship;
import ld31.entities.Sun;
import panko.Panko;
import panko.PankoRoomBase;

/**
 * Created by shaun on 06/12/2014.
 */
public class Universe extends PankoRoomBase {

    @Override
    public void start() {
        super.start();

        // Add player ship
        Ship ship = Ship.newPlayerShip(this);
        Panko.addEntityToRoom(this, ship);

        double sunSize = 5000;
        double planetSize = 1000;
        double moonSize = 500;

        // Add a sun
        Sun sun = (Sun)Panko.addEntityToRoom(this, new Sun().setX(300).setY(300));
        sun.setRadius(sunSize);

        // Add a planet
        Planet planet = new Planet();
        planet.setParentBody(sun, 3000, 1);
        planet.setRadius(planetSize);
        Panko.addEntityToRoom(this, planet);

        // Add a moon for this planet
        Moon moon = new Moon();
        moon.setParentBody(planet, 1000, 3);
        moon.setRadius(moonSize);
        Panko.addEntityToRoom(this, moon);

        // Add another planet
        Planet planet2 = new Planet();
        planet2.setParentBody(sun, 6500, 0.25);
        planet2.setRotation(90);
        planet2.setRadius(planetSize);
        Panko.addEntityToRoom(this, planet2);


        bringEntityToFront(ship);

        LD31Sounds.demoMusic.loop();

    }

}
