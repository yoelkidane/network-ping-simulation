/**
 *
 * An implementation of a linked list that manages a sequence of events in relation to their arrival time.
 * This class provides insertion, removal, and general information about the list (size, simulation time)
 */


public class LinkedEventList implements FutureEventList {

    private Node head;
    private int size;
    private int simulationTime;

    /**
     * Creation of an empty LinkedEventList
     */
    public LinkedEventList() {
        head = null;
        size = 0;
        simulationTime = 0;
    }


    /**
     * Removes the first event from the LinkedEventList and returns the event. While doing this, it accounts
     * for any changes that occurred in the list's simulation time
     *
     * @return the event at the front of the list
     */
    @Override
    public Event removeFirst() {
        Node nodeToRemove = head;
        head = head.next;
        if (head != null) {
            head.prev = null;
        }
        simulationTime = nodeToRemove.event.getArrivalTime();
        size--;
        return nodeToRemove.event;
    }


    /**
     * Removes a specific event "e" from the list
     *
     * @param e an Event to remove from the list
     * @return true if an element was found and removed, otherwise false
     */
    @Override
    public boolean remove(Event e) {
        Node currentNode = head;
        while (currentNode != null) {
            if (currentNode.event.equals(e)) {

                if (currentNode.prev != null) {
                    currentNode.prev.next = currentNode.next;
                } else {
                    head = currentNode.next;
                }

                if (currentNode.next != null) {
                    currentNode.next.prev = currentNode.prev;
                }

                size--;
                e.cancel();
                return true;
            }
            currentNode = currentNode.next;
        }
        return false;
    }

    /**
     * Inserts a new event into the list based on its arrival time.
     *
     * @param event an Event to insert into the list
     */
    @Override
    public void insert(Event event) {
        Node newNode = new Node(event);
        event.setInsertionTime(simulationTime);

        if (head == null || head.event.getArrivalTime() >= event.getArrivalTime()) {

            newNode.next = head;

            if (head != null) {
                head.prev = newNode;
            }
            head = newNode;

        } else {
            Node currentNode = head;

            while (currentNode.next != null && currentNode.next.event.getArrivalTime() < event.getArrivalTime()) {
                currentNode = currentNode.next;
            }

            newNode.next = currentNode.next;
            newNode.prev = currentNode;

            if (currentNode.next != null) {
                currentNode.next.prev = newNode;
            }

            currentNode.next = newNode;
        }
        size++;
    }

    /**
     * The current number of events in the list
     * @return current size of the list
     */
    @Override
    public int size() {
        return size;
    }

    /**
     * The max size of the list (in this instance, it will always be equal to size)
     *
     * @return capacity of the list
     */
    @Override
    public int capacity() {
        return size;
    }

    /**
     * Returns the current simulation time.
     *
     * @return the current simulation time
     */
    @Override
    public int getSimulationTime() {
        return simulationTime;
    }


}
