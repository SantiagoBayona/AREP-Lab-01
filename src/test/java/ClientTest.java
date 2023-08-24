import edu.escuelaing.HttpConnection;
import org.junit.Test;

import java.io.IOException;

public class ClientTest {

    @Test
    public void testConcurrentClientRequests() {
        int numThreads = 5;

        for (int i = 0; i < numThreads; i++) {
            Thread thread = new Thread(new Runnable() {
                public void run() {
                    try {
                        HttpConnection httpConnection = new HttpConnection();

                        String[] movieTitles = {"Blade Runner", "The Godfather", "Karate Kid"};

                        for (String movieTitle : movieTitles) {
                            String response = httpConnection.getData(movieTitle);
                            System.out.println("Movie: " + movieTitle);
                            System.out.println(response);
                            System.out.println();
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            });
            thread.start();
        }

        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
