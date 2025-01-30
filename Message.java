/**
 * The representation of what a message event is in the simulation.
 * Contains information about the message, its source address, destination address, and timing.
 */

public class Message extends Event {

    private String message;
    private int srcAddress;
    private int destAddress;
    private Host nextHop;
    private int distance;


    /**
     * Creates a message with the given content, source address, and destination address
     *
     * @param message content of message
     * @param srcAddress source of the message
     * @param destAddress where the message is being sent
     */
    public Message(String message, int srcAddress, int destAddress) {
        this.message = message;
        this.srcAddress = srcAddress;
        this.destAddress = destAddress;
    }

    // Implementation of abstract methods of the Event class


    /**
     * "Handles" message by forwarding it to the next hop.
     * Used whenever a message event occurs
     */
    @Override
    public void handle() {
        nextHop.receive(this);
    }

    /**
     * Assigns the insertion time and calculates the arrival time of a message.
     *
     * @param currentTime the current simulation time (which is what insertion time is)
     */
    @Override
    public void setInsertionTime(int currentTime) {
        this.insertionTime = currentTime;
        //Arrival time = the time required to reach the destination + the start time
        this.arrivalTime = currentTime + distance;
    }

    /**
     * Unused method from Event
     */
    @Override
    public void cancel() {
        //not used
    }

    /**
     * Returns the content of the message
     * @return message content
     */
    public String getMessage() {
        return message;
    }

    /**
     * Returns source address of the message
     * @return message source address
     */
    public int getSrcAddress() {
        return srcAddress;
    }

    /**
     * Returns destination address of the message
     * @return message destination address
     */
    public int getDestAddress() {
        return destAddress;
    }

    /**
     * Assigns the next hop for the message, along with its given distance.
     *
     * @param nextHop the next host that should receive the message
     * @param distance how far away the hosts are from each-other
     */
    public void setNextHop(Host nextHop, int distance) {
        this.nextHop = nextHop;
        this.distance = distance;
    }
}
