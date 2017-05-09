/**
* BookList.java
* @author Joseph Mbabu
* March 2015
*/

public class BookList<E> implements PriQue<E>
{
	private static final int SIZE = 1000;

	private Entry<E> heap[];

    private int cur_size;


	private class Entry<E>
	{
		public int pri;
		public E data;

		Entry(int pri, E data)
		{
			this.pri = pri;
			this.data = data;
		}

        public void setPir(int pri)
        {
        	this.pri = pri;
        }

		public int getPri()
		{
			return pri;
		}

		public void setData( E data)
		{
			this.data = data;
		}

		public E getData(){
			return data;
		}
	}

	public BookList()
	{
        heap  = new Entry[SIZE];
 		cur_size = 0;
	}


    /**
    * Returns current size of heap-array
    * @return cur_size current size of heap array
    */
    public int getCurrentSize()
    {
    	return cur_size;
    }

    /**
    * Trickles item with a higher priority up the queue
    * @param index index of newly inserted item
    */
	public void bubbleUp(int index)
	{
		int p = index / 2;
	    Entry bottom = heap[index];

	   while(index > 0 && bottom.getPri() > heap[p].getPri())
	   {
			heap[index]  = heap[p];

			index = p;
			p = p / 2;
	   }
	   heap[index] = bottom;
	}

    /**
    * Trickles item with a lower prioriry down the queue
    * @param index idex of item at root
    */
	public void bubbledDown(int index)
	{
		int bigger; // index of the larger child
		Entry top = heap[index];  // root/parent

		while(index < cur_size / 2)
		{
			int lChild = (index *2 ) + 1;
			int rChild = lChild + 1;

            // finds which of the two children is heavier
			if( rChild < cur_size && heap[lChild].getPri() < heap[rChild].getPri())
				bigger = rChild;
			else
				bigger = lChild;

			// if the weight of the root is smaller
			if(top.getPri() >= heap[bigger].getPri())
				break;

            // book with smaller weight is moved to the front
			heap[index] = heap[bigger];
			index = bigger;
		}
		heap[index] = top;
	}

    /**
    * Inserts data
    * @param pri data priority
    * @param data data being inserted
    */
	public void insert(int pri, E data)
	{
		Entry node = new Entry(pri, data);
		heap[cur_size] = node;
		bubbleUp(cur_size);
		cur_size++;
	}

    /**
    * Delete item with the hight priority
    */
	public E remove()
	{
		if(cur_size == 0)
		{
			return null;
		}
		E r = heap[0].getData();
		cur_size--;
	    heap[0] = heap[cur_size];
	   	bubbledDown(0);
        return r;
	}


	public boolean isEmpty()
	{
		return cur_size == 0;
		//return cur_size == 0 ? true : false;
	}





    // for debugging purposes
	public void display()
	{
		for(int i=0; i< cur_size; i++)
		{
			if(heap[i] != null)
				System.out.println("\t" + heap[i].getPri() + " " + heap[i].getData());
			else
				System.out.println("---------");
		}
	}


    // Finds the total weight of books in the list
	public int weight()
	{
	   int w = 0;
       for(int i=0; i<cur_size; i++)
       {
       		if(heap[i] != null)
       		{
       			w += heap[i].getPri();
       		}
       }
       return w;
	}

    // Peeks at the first item in the heap array
	public E peek()
	{
		return heap[0].getData();
	}
}
