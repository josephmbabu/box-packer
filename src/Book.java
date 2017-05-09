/**
* Book.java
* @author Joseph Mbabu
* April 2015
*/

public class Book
{
	private String isbn;
	private String title;
	private int weight;

	public Book(String isbn, String title, int weight)
	{
		this.isbn = isbn;
		this.title = title;
		this.weight = weight;
	}

	public String getISBN()
	{
		return isbn;
	}

	public String getTitle()
	{
		return title;
	}

	public int getWeight()
	{
		return weight;
	}
}
