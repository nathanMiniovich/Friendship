package friends;

import java.util.NoSuchElementException;

public class Queue {
	
	Node front;
	Node rear;
	int size;
	
	public Queue() {
		front = null;
		rear = null;
		size = 0;
	}
	public boolean isEmpty() {
		return front == null;
	}
	
	public int size() {
		return size();
	}

	
	public void enqueue(Vertex item) {
		Node tmp = rear;
		rear = new Node(item);
		rear.data = item;
		rear.next = null;
		if(isEmpty()){
			front = rear;
		}else{
			tmp.next = rear;
		}
		size++;
	}

	public Vertex dequeue()
		throws NoSuchElementException {
		if (front == null){
			throw new NoSuchElementException("Queue underflow");
		}
		Vertex item = front.data;
		front = front.next;
		size--;
		return item;
		}
	
	public String toString(){
		
		return null;
	}
}
