/**
 * An implementation of a node in the doubly LinkedEventList.
 * Each node holds a reference to an event, along with previous and next pointers to maintain doubly linked list structure.
 *
 */
public class Node {
    public Event event;
    public Node prev;
    public Node next;

    /**
     * Constructs a new node with the given event
     * @param event with given event information
     */
    public Node(Event event) {
        this.event = event;
        this.prev = null;
        this.next = null;
    }
}
