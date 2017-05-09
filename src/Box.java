/**
* Box.java
* @author Joseph Mbabu
* April 2015
*/

import java.util.Locale;
import java.text.NumberFormat;

public class Box
{  
	static Locale locale = new Locale("en", "US");  
    static NumberFormat formatter = NumberFormat.getCurrencyInstance(locale);

	private Book[] books; 
    
    private String bx_type;
	private int size;  
	private int l;      // index of last book
	private int unused_space;  // space left in the box after adding a book

	private int cost;
	
	public Box(String bx_type, int size, int cost)
	{   
		this.bx_type = bx_type;
		this.size = size;
		books = new Book[size]; 
		unused_space = size;

		this.cost = cost;

		l = -1;
	}
    
    /**
    * Getters
    */
    public int getCost()
    {
    	return cost;
    }

    public int getSpace()
    {
    	return unused_space;
    }

    public int getSize()
    {
    	return size;
    }

    public String getBoxType()
    {
    	return  bx_type;
    }

    /**
    * Gets total weight of books in the 
    * @return weight weight of total order
    */
    public int getWeight()
    {   
    	int weight = 0;

        for(int i=0; i<books.length; i++)
        {
        	if(books[i] != null)
        	{
           		weight += books[i].getWeight();
        	}
        }
        return weight;
    }
    
    /**
    * Lists the contents of a box
    */
    public void listContents()
    {
    	for(int i=0; i<books.length; i++)
    	{
    		if(books[i] != null)
    			System.out.printf("%s %s %s\n", books[i].getISBN(), books[i].getTitle(), books[i].getWeight());
    		else 
    			break;
    	}
    	System.out.println("------------------------------------");
    	System.out.printf("Box weight %s Box cost %s\nSpace unused %s\n", getWeight(), formatter.format(getCost()/(double)100), getSpace());
    }

    /**
    * Adds a book to the box
    * @param book book object
    */
    public void add(Book book)
    {
       l++; 
       books[l] = book;
       unused_space -= book.getWeight();
    }

    /**
    * Creates a new box object
    */
    public Box newBox()
    {
    	return new Box(getBoxType(), getSize(), getCost());
    }	
}