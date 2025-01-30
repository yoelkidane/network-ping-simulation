import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) throws FileNotFoundException {
            // Enter new or custom simulation file path here
            File file = new File("Simulation Files/simulation3.txt");
            Scanner scanner = new Scanner(file);

            // Read the host address in the middle of the star topology
            int firstHostAddress = scanner.nextInt();

            // Create a FutureEventList instance
            LinkedEventList eventList = new LinkedEventList();
            HashMap<Integer, SimpleHost> mapForHosts = new HashMap<>();

            // Create the "head" node for "sub" nodes to connect to
            SimpleHost firstHost = new SimpleHost(firstHostAddress, eventList);
            mapForHosts.put(firstHostAddress, firstHost);

            // Read neighbor connections until encountering the sentinel value -1
            int neighborAddress;
            int distance;


        while ((neighborAddress = scanner.nextInt()) != -1) {
                distance = scanner.nextInt();

                // Create a new neighbor host and set its parameters
                SimpleHost neighbor = new SimpleHost(neighborAddress, eventList);
                mapForHosts.put(neighborAddress, neighbor);

                // Add each host as a neighbor to the other
                firstHost.addNeighbor(neighbor, distance);
                neighbor.addNeighbor(firstHost, distance);
        }

        int senderAddress, receiverAddress, interval, duration;

        while (scanner.hasNext()) {
            senderAddress = scanner.nextInt();
            receiverAddress = scanner.nextInt();
            interval = scanner.nextInt();
            duration = scanner.nextInt();

            mapForHosts.get(senderAddress).sendPings(receiverAddress, interval, duration);
        }

        while(eventList.size() != 0){
            Event event = eventList.removeFirst();
            event.handle();
        }

        scanner.close();
    }
}
