/**
 * Implementation of a SimpleHost in a network traffic and pinging simulation tool
 *
 * The SimpleHost is responsible for sending and receiving responses to and from hosts.
 * Pings are sent consistently, which allows for determining the round-trip time (RTT) on a response.
 */

public class SimpleHost extends Host {

    private int intervalTimerEventId;
    private int durationTimerEventId;
    private int distance;
    private int firstArrival;
    private int destAddr;
    private int interval;

    /**
     * Creation of a SimpleHost with its given host address and establishes a FutureEventList to go with it.
     *
     * @param hostAddress address of the host
     * @param fel FutureEventList to hold and order each event
     */
    public SimpleHost(int hostAddress, LinkedEventList fel) {
        super();
        //Set the host params.
        setHostParameters(hostAddress, fel);
        //Add the given host as a neighbor
        addNeighbor(this, distance);
    }


    /**
     * Determines what to do based upon if a message with either a request or a response, and acts accordingly.
     *
     * @param msg the Message event received
     */
    @Override
    protected void receive(Message msg) {
        String messageType = msg.getMessage();

        // Switch on the type of message received (request or response)
        switch (messageType) {
            case "request":
                sendPingResponse(msg.getSrcAddress(), msg.getDestAddress());
                break;
            case "response":
                computeRTT(msg);
                break;
            default:
                System.out.println("SHOULD BE UNREACHABLE");
                break;
        }
    }

    /**
     * Invoked when either the internal timer or the duration timer expires.
     * When internal timer expires, the method sends a ping request.
     * When duration timer expires, the method stops all pings
     *
     * @param eventId the event id of the Timer event which expired
     */
    @Override
    protected void timerExpired(int eventId) {

        Message pingRequest = null;
        if (eventId == intervalTimerEventId) {

            int currentTime = getCurrentTime();
            pingRequest = new Message("request", getHostAddress(), destAddr);

            firstArrival = getCurrentTime();

            System.out.println("[" + currentTime + "ts]" + " Host " + getHostAddress() + ": Sent ping to host " + destAddr);
            sendToNeighbor(pingRequest);

            intervalTimerEventId = newTimer(interval);

        } else if (eventId == durationTimerEventId) {
            // If the duration timer expired, cancel the interval timer to stop sending pings
            cancelTimer(intervalTimerEventId);
        }
    }

    /**
     * Stops the timer from sending pings when the interval timer is expired.
     *
     * @param eventId the event id of the Timer event which was cancelled
     */
    @Override
    protected void timerCancelled(int eventId) {
        System.out.println("[" + getCurrentTime() + "ts] Host " + getHostAddress() + ": Stopped sending pings");
    }

    /**
     * Method to send ping requests at regular intervals for a specified duration
     *
     * @param destAddr
     * @param interval
     * @param duration
     */
    public void sendPings(int destAddr, int interval, int duration) {
        this.interval = interval;
        this.destAddr = destAddr;
        // Create a timer for the interval
        intervalTimerEventId = newTimer(interval);
        // Create a timer for the total duration before stopping
        durationTimerEventId = newTimer(duration);
    }

    /**
     * Called when a host receives a ping request.
     * The appropriate response is to send back a "response" message back to the source address
     *
     * @param srcAddr the address the ping came from
     * @param destAddr the address the ping response is being sent to
     */
    private void sendPingResponse(int srcAddr, int destAddr) {
        Message pingResponse = new Message("response", destAddr, srcAddr);
        System.out.println(formatTimestamp() + "Host " + getHostAddress() + ": Ping request from host " + pingResponse.getDestAddress());
        sendToNeighbor(pingResponse);
    }

    /**
     *  It calculates the RTT as the difference between the current time and the time the ping request was sent.
     *
     * @param pingResponse the ping response message received
     */
    private void computeRTT(Message pingResponse) {
        int simTime = getCurrentTime();
        int rtt = simTime - firstArrival;

        System.out.println(formatTimestamp() + "Host " + getHostAddress() + ": Ping response from host " + pingResponse.getSrcAddress() + " (RTT = " + rtt + "ts)");
    }


    /**
     * Helper method to format the string of time correctly
     *
     * @return properly formatted time
     */
    private String formatTimestamp() {
        return "[" + getCurrentTime() + "ts] ";
    }

}