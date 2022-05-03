/*
 * AUTHOR: Mason Holter
 * CLASS: MaxPQ.class
 * PURPOSE: This program defines the MaxPQ collection type, which implements a priority 
 * queue backed by a maximum heap. It includes functions to enqueue, dequeue, alter node data, 
 * peek node data, and print, as well as several helper functions.
 * 
 */

public class MaxPQ {
	private Link[] queue;
	private int size;
	private int DEFAULT_SIZE = 100;
	private int nextEmpty = 1;
	
	/*
     * A class initialization method which which creates an empty heap
     * and zeroed size integer
     */
	public MaxPQ() { 
		queue = new Link[DEFAULT_SIZE];
		size = 0;
	}
	
	/*
     * Enqueue adds a Link object to the end of the heap, then corrects
     * the objects placement if necessary.
     * 
     * @param page, Link object
     */
	public void enqueue(Link page) {
        Link newPage = page;
        if (nextEmpty == DEFAULT_SIZE) {
            increaseArray();
        }
        queue[nextEmpty] = newPage;
        if ((1 <= getParent(nextEmpty)) && getParent(nextEmpty) <= size) {
            bubbleUp(nextEmpty);
        }
        nextEmpty += 1;
        size += 1;
    }
	
	/*
     * Enqueue adds a new Link object to the end of the heap, then corrects
     * the objects placement if necessary.
     * 
     * @param name, Link object name string
     * 
     * @param priority, Link object priority integer
     */
    public void enqueue(int priority, String page) {
        Link newPage = new Link(priority, page);
        if (nextEmpty == DEFAULT_SIZE) {
            increaseArray();
        }
        queue[nextEmpty] = newPage;
        if ((1 <= getParent(nextEmpty)) && getParent(nextEmpty) <= size) {
            bubbleUp(nextEmpty);
        }
        nextEmpty += 1;
        size += 1;
    }
    
    /*
     * Dequeue removes the Link object from the front of the queue and
     * replaces it with the object at the end, correcting its position.
     * 
     * @return returnString, string containing dequeued Link's name
     */
    public String dequeue() {
        if (size == 0) {
            System.out.println("Empty queue error");
        }

        String returnString = queue[1].name;
        queue[1] = queue[size];
        queue[size] = null;
        bubbleDown(1);

        size--;
        nextEmpty--;
        return returnString;
    }
    
    /*
     * Replaces current heap with new heap, identical in content but with
     * increased size.
     */
    private void increaseArray() {
        DEFAULT_SIZE *= 2;
        Link[] tempArray = new Link[DEFAULT_SIZE];
        for (int i = 1; i <= this.size; i++) {
            tempArray[i] = this.queue[i];
        }
        this.queue = tempArray;
    }
    
    /*
     * Tests array for existing content
     * 
     * @return true or false depending on size check
     */
    public boolean isEmpty() { 
    	return size == 0;
    }
    
    /*
     * Helper function to find index of "parent" of index in heap.
     * 
     * @return parent index
     */
    private int getParent(int index) {
        if (index / 2 == 0) {
            return 1;
        }
        return index / 2;
    }
    
    /*
     * Helper function to find index of left child of index in heap.
     * 
     * @return index of left child
     */
    private int getLeft(int index) {
        return index * 2;
    }
    
    /*
     * Helper function to find index of right child of index in heap.
     * 
     * @return index of right child
     */
    private int getRight(int index) {
        return (index * 2) + 1;
    }
    
    /*
     * Function which utilizes recursion to correctly position Link
     * objects based on priority in maximum heap. This function checks if child
     * node is correctly placed beneath parent node.
     */
    private void bubbleUp(int index) {
        int parent = getParent(index);
        if (queue[index].priority > queue[parent].priority) {
            Link tempPage = queue[parent];
            queue[parent] = queue[index];
            queue[index] = tempPage;
            bubbleUp(parent);
            bubbleUp(index);
        }
    }
    
    /*
     * Function which utilizes recursion to correctly position Link
     * objects based on priority in maximum heap. This function checks if parent
     * is correctly placed relative to both possible child nodes.
     */
    private void bubbleDown(int index) {
        if (getLeft(index) < size) {
            if (queue[getLeft(index)].priority > queue[index].priority) {
                int left = getLeft(index);
                Link tempPage = queue[index];
                queue[index] = queue[left];
                queue[left] = tempPage;
                bubbleDown(left);
                if (getRight(left) <= size) {
                    bubbleUp(getRight(left));
                }
            }

        }
        if (getRight(index) < size) {
            if (queue[getRight(index)].priority > queue[index].priority) {
                int right = getRight(index);
                Link tempPage = queue[index];
                queue[index] = queue[right];
                queue[right] = tempPage;
                bubbleDown(right);
                if (getLeft(right) <= size) {
                    bubbleUp(getLeft(right));
                }
            }
        }
    }
	
    /*
     * This class defines the Link object, which acts as a node containing
     * the priority of the Wikipedia page and the page's name.
     * 
     * @param prio, the amount of links shared with the end page
     * 
     * @param name, Wikipedia page name
     */
	public class Link { 
		public int priority;
		public String name;
		
		public Link(int prio, String name) { 
			this.name = name;
			priority = prio;
		}
	}
}

