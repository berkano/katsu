package ld31;

import ld31.entities.Moon;
import ld31.entities.Planet;
import ld31.entities.Ship;
import ld31.entities.Star;
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

        createUniverse();

        bringEntityToFront(ship);

        //LD31Sounds.demoMusic.loop();

    }

    private void createUniverse() {

        int numSystems = 10;

        double starSize = 5000;
        double planetSize = 2000;
        double moonSize = 1000;
        int starSpread = 50;

        double typicalPlanetDistance = planetSize * 5;
        double typicalMoonDistance = moonSize * 5;

        for (int i = 0; i < numSystems; i++) {
            // Find a good location for the star.

            int starX = Panko.random.nextInt((int)(starSize * starSpread)) - (int)starSize * starSpread/2;
            int starY = Panko.random.nextInt((int)(starSize * starSpread)) - (int)starSize * starSpread/2;

            Star star = new Star();

            star.setX(starX);
            star.setY(starY);
            star.setRadius(starSize);

            Panko.addEntityToRoom(this, star);

            int numPlanets = Panko.random.nextInt(5) + 1;

            for (int p = 0; p < numPlanets; p++) {

                // Add a planet
                Planet planet = new Planet();
                planet.setParentBody(star, (int)starSize + (int)(typicalPlanetDistance + typicalPlanetDistance * p), 0.1);
                planet.setRadius(planetSize);
                planet.setRotation(Panko.random.nextInt(360));
                Panko.addEntityToRoom(this, planet);

                int numMoons = Panko.random.nextInt(3);

                for (int m = 0; m < numMoons; m++) {

                    // Add a moon for this planet
                    Moon moon = new Moon();
                    moon.setParentBody(planet, (int)(typicalMoonDistance + typicalMoonDistance * m), 0.2);
                    moon.setRadius(moonSize);
                    moon.setRotation(Panko.random.nextInt(360));
                    Panko.addEntityToRoom(this, moon);
                }

            }

        }

    }

}
