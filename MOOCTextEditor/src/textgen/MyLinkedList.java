package textgen;

import java.util.AbstractList;


/** A class that implements a doubly linked list
 * 
 * @author UC San Diego Intermediate Programming MOOC team
 *
 * @param <E> The type of the elements stored in the list
 */
public class MyLinkedList<E> extends AbstractList<E> {
	LLNode<E> head;
	LLNode<E> tail;
	int size;

	/** Create a new empty LinkedList */
	public MyLinkedList() {
		
		head = new LLNode<E>(null);
		tail = new LLNode<E>(null);
		size = 0;

		head.next = tail;
		tail.prev = head;
	}

	/**
	 * Appends an element to the end of the list
	 * @param element The element to add
	 */
	public boolean add(E element) 
	{
		LLNode<E> node;
		node = new LLNode<E>(element);
		
		this.size +=1;
		
		if (this.size==0){		
			node.next = head.next;
			node.prev = node.next.prev;
			node.next.prev = node;
			head.next = node;

		}else{			
			node.next = tail;
			node.prev = tail.prev;
			tail.prev.next = node;
			tail.prev = node;

		} 
		return true;
	}

	/**
	 * Add an element to the list at the specified index
	 * @param The index where the element should be added
	 * @param element The element to add
	 */
	public void add(int index, E element ) 
	{

		if (index<0 || index>=this.size){
			throw new IndexOutOfBoundsException("Your message");
		}	  

		LLNode<E> current = head.next;		
		LLNode<E> node = new LLNode<E>(element);
		
		this.size +=1;
		
		for (int i=0; i<size; i++){					
			if (i==index){
				node.next = current;			
				node.prev = current.prev;
				current.prev.next = node;
				current.prev = node;	
			}else{
				current = current.next;
			}
		} 
	}
	
	/** Get the element at position index 
	 * @throws IndexOutOfBoundsException if the index is out of bounds. */	
	public E get(int index) 
	{
		if (index<0 || index>=this.size || size==0){
			throw new IndexOutOfBoundsException("Your message");
		}

		LLNode<E> current = head.next;	

		for (int i=0; i<size; i++){					
			if (i==index){
				break;
				//return current.data;				
			}else{
				current = current.next;
			}
		}				
		return current.data;
	}
	
	// return the node of position index
	
	public LLNode<E> getNode(int index) 
	{
		if (index<0 || index>=this.size || size==0){
			throw new IndexOutOfBoundsException("Your message");
		}

		LLNode<E> current = head.next;	

		for (int i=0; i<size; i++){					
			if (i==index){
				break;
				//return current.data;				
			}else{
				current = current.next;
			}
		}				
		return new LLNode<E>(current.data, current.prev, current.next);
	}
	
	
	/** Return the size of the list */
	public int size() 
	{
		return this.size;
	}

	/** Remove a node at the specified index and return its data element.
	 * @param index The index of the element to remove
	 * @return The data element removed
	 * @throws IndexOutOfBoundsException If index is outside the bounds of the list
	 * 
	 */
	public E remove(int index) 
	{
		if (index<0 || index>=this.size || size==0){
			throw new IndexOutOfBoundsException("Your message");
		}	    
		
		this.size -=1;
		
		LLNode<E> current = head.next;		

		for (int i=0; i<size; i++){					
			if (i==index){				
				current.prev.next = current.next;
				current.next.prev = current.prev;

				current.prev = null;
				current.next = null;
			
				break;				
								
			}else{
				current = current.next;
			}
		}			
		return current.data;
	}

	/**
	 * Set an index position in the list to a new element
	 * @param index The index of the element to change
	 * @param element The new element
	 * @return The element that was replaced
	 * @throws IndexOutOfBoundsException if the index is out of bounds.
	 */
	public E set(int index, E element) 
	{
		if (index<0 || index>=this.size || size==0){
			throw new IndexOutOfBoundsException("Your message");
		}	    

		LLNode<E> current = head.next;

		for (int i=0; i<size; i++){						
			if (i==index){
				current.data = element;	
				break;
			}else{
				current = current.next;
			}
		}		
		return current.data;
	}   
}

class LLNode<E> 
{
	LLNode<E> prev;
	LLNode<E> next;
	E data;

	public LLNode(E e) 
	{
		this.data = e;
		this.prev = null;
		this.next = null;
	}
	
	public LLNode(E e, LLNode<E> prevNode, LLNode<E> nextNode) 
	{
		this.data = e;
		this.prev = prevNode;
		this.next = nextNode;
	}
	
	
}
