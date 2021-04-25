package xenoteo.com.github.homework;

import java.util.Random;

/**
 * The satellite API.
 */
public class SatelliteAPI {

    /**
     * The enum of satellite statuses.
     */
    enum Status {
        OK, BATTERY_LOW, PROPULSION_ERROR, NAVIGATION_ERROR
    }

    /**
     * Gets the random satellite status.
     *
     * @param satelliteIndex  the satellite index
     * @return the random satellite status
     */
    public static Status getStatus(int satelliteIndex){

        Random rand = new Random();

        try {
            Thread.sleep(100 + rand.nextInt(400));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        double p = rand.nextDouble();

        if (p < 0.8) return Status.OK;
        else if (p < 0.9) return Status.BATTERY_LOW;
        else if (p < 0.95) return Status.NAVIGATION_ERROR;
        else return Status.PROPULSION_ERROR;
    }

}