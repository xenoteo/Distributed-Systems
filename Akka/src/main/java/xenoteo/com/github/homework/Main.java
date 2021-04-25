package xenoteo.com.github.homework;

import java.util.Random;

public class Main {

    public static void main(String[] args) {
        DBConnector.connect();
        DBConnector.reinitializeDB();

        Dispatcher dispatcher = new Dispatcher();

        Station station1 = new Station("station-1", dispatcher);
        Station station2 = new Station("station-2", dispatcher);
        Station station3 = new Station("station-3", dispatcher);

        Random rand = new Random();

        station1.query(100 + rand.nextInt(50), 50, 300);
        station1.query(100 + rand.nextInt(50), 50, 300);

        station2.query(100 + rand.nextInt(50), 50, 300);
        station2.query(100 + rand.nextInt(50), 50, 300);

        station3.query(100 + rand.nextInt(50), 50, 300);
        station3.query(100 + rand.nextInt(50), 50, 300);


        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        for (int id = 100; id < 200; id++){
            station1.queryError(id);
        }

    }
}
