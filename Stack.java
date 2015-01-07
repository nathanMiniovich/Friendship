package friends;

import java.util.NoSuchElementException;

public class Stack {
	Node top;
	int size;
	
	public Stack(){
		top = null;
		size = 0;
	}
	public boolean isEmpty(){
		return top == null;
	}
	
	public int size(){
		return size;
	}
	
	public void push(Vertex item){
		Node tmp = top;
		top = new Node(item);
		top.data = item;
		top.next = tmp;
		size++;
	}
	
	public Vertex pop ()
			throws NoSuchElementException{
		if(isEmpty()){
			throw new NoSuchElementException("Stack underflow");
		}
		Vertex item = top.data;
		top = top.next;
		size--;
		
		return item;
	}
	
	public Vertex peek ()
			throws NoSuchElementException{
		if(isEmpty()){
			throw new NoSuchElementException("Stack underflow");
		}
		return top.data;
	}
	
	public String toString(){
		
		return null;
	}
}
