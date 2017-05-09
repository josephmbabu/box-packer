/**
* Packer.java
* @uthor Joseph Mbabu
*
* March 2015
*/

import java.util.Scanner;
import java.util.Locale;

import java.io.File;
import java.io.FileNotFoundException;

import java.text.NumberFormat;

public class Packer
{
    static Locale locale = new Locale("en", "US");
    static NumberFormat formatter = NumberFormat.getCurrencyInstance(locale);

	static BookList<String> heap = new BookList<String>();

	static Box[] bx; // type of box, small, medium, large etc

	static Box box;  // holds a box object

    static Box[] boxes_used; // boxes used

	static Scanner sc, sc1, sc2;

	static String f1 = null;
	static String f2 = null;


    // returns type of boxes used
    static void typesUsed(Box[] x)
    {
        int small = 0;
        int medium = 0;
        int large = 0;
        int giant = 0;

        for(int i=0; i< x.length; i++)
        {
            if(x[i] != null)
            {
                if(x[i].getBoxType().equalsIgnoreCase("small"))
                    small++;
                if(x[i].getBoxType().equalsIgnoreCase("medium"))
                    medium++;
                if(x[i].getBoxType().equalsIgnoreCase("large"))
                    large++;
                if(x[i].getBoxType().equalsIgnoreCase("giant"))
                    giant++;
            }
        }

        int total = small + medium + large + giant;

        System.out.printf("small boxes = %s \nmedium boxes = %s\nlarge boxes = %s\ngiant boxes = %s\n", small, medium, large, giant);
        System.out.printf("--------------\nTotal boxes %s\n\n", total);
    }

    // creates boxes
    static void createBoxes(String f)
    {
    	int cnt = 0;

    	File file = new File(f);
    	try{
    		sc = new Scanner(file);
    		sc1 =new Scanner(file);

    	}
    	catch(FileNotFoundException e)
    	{
    		System.out.println("File not found");
    	}

        // type of boxes that can be used
    	while(sc.hasNext()){
    		sc.nextLine();
    		cnt++;
    	}

    	bx = new Box[cnt];

        int i = 0;

        // create box-types
    	while(sc1.hasNext())
    	{
    		String line = sc1.nextLine();
    		String[] p = line.split("/", 3);

    		box = new Box(p[0].trim(), Integer.parseInt(p[1].trim()), Integer.parseInt(p[2].trim()));
    		bx[i] = box;

    		i++;
    	}
    }


    /**
    * @param boxes type of boxes that can be used for packing
    * @param book book object
    */
    static void packBoxes(Box[] box)
    {
        int heap_weight = heap.weight(); // total weight of book list

        int len = box.length-1;

        int cnt = 0; // count of number of boxes used/created

        boxes_used = new  Box[10];


    	while(heap.weight() != 0)
    	{
    		Box b = null;

            // gets the largest box
    		for(int i =0; i<len; i++)
        	{
        		if(heap_weight <= box[i].getSize()){
        			b = box[i].newBox();
                    break;
                }
        	}

        	if(b == null)
            {
           		b = box[len].newBox();
            }

            //System.out.println(b.getSize()); // debugging

            while(true) //  if the box is not full, keep adding more books
            {
                // if the end of the heap is reached
                if(heap.weight() == 0)
                {
                    boxes_used[cnt] = b;
                    cnt++;
                    break;
                }

                String string = heap.peek();
                String pt[] = string.split("/", 3);

                //System.out.println(pt[1] + " "+ pt[2]); // debugging

                Book bk = new Book(pt[0].trim(), pt[1].trim(), Integer.parseInt(pt[2].trim()));

                if((bk.getWeight() + b.getWeight()) > b.getSize())
                {
                    if(packBoxHelper(bk)== true)
                    {
                        heap.remove();
                    }
                    else{
                        if(cnt < boxes_used.length)
                        {
                            boxes_used[cnt] = b;
                             cnt++;
                             break;
                        }
                    }
                }
                else
                {
                    String str = heap.remove();
                    String[] p = str.split("/" ,3);
                    Book book = new Book(p[0].trim(), p[1].trim(), Integer.parseInt(p[2].trim()));

                    b.add(book);

                    // debugging
                    //System.out.println("Heap weight "+ heap_weight);
                    //System.out.println("book weight " +book.getWeight());
                    //System.out.println("Bx weight " +b.getWeight());

                    heap_weight -= book.getWeight(); // subtract book weight from total weight;

                }
                //System.out.println(heap.weight());
    		}
        }
    }

    // Find if book can be packed in any of the used boxes
    static boolean packBoxHelper(Book book)
    {
        boolean flag = false;

        for(int i=0; i< boxes_used.length; i++)
        {
            if(boxes_used[i] != null)
            {
                if(book.getWeight()<= boxes_used[i].getSpace()){
                    boxes_used[i].add(book);
                    flag = true;
                }

            }
        }
        return flag;
    }


    /**
    * Prints the contents of a box
    * @param boxes boxes used
    */
    static void printBox(Box[] box)
    {
    	for(int i=0; i<box.length; i++)
    	{
    		if(box[i] != null)
    		{
    			System.out.printf("\nBox %s uses a %s box\n", i+1, box[i].getBoxType());
    			box[i].listContents();
    		}
    	}
    	subTotals(boxes_used);
    }

    // Finds the shipment subtotals
    static void subTotals(Box[] used_boxes)
    {
    	int _weight = 0; // total weight of boxes used
    	int _unused = 0; // total unused space
    	double _cost = 0; // total cost

    	for(int i=0; i<used_boxes.length; i++)
    	{
    		if(used_boxes[i] != null)
    		{
    		    _weight += used_boxes[i].getWeight();
    		    _unused += used_boxes[i].getSpace();
    		    _cost += used_boxes[i].getCost();
    		}
    	}
    	System.out.printf("\nTotal weight %s\nTotal unused %s\nTotal cost %s\n\n", _weight, _unused,
           formatter.format(_cost/(double)100));
    }

    // adds the book-list to the heap
	static void add(String f)
	{
		File file = new File(f);
		try
		{
			sc2 = new Scanner(file);
			while(sc2.hasNext())
			{
				String ln = sc2.nextLine();
                String[] prt = ln.split("/", 3);

                int pri = Integer.parseInt(prt[2]);
                heap.insert(pri, ln);
			}
		}
		catch(FileNotFoundException e)
		{
			System.out.println("File not found");
		}
	}

    // validates file names
    static boolean validateFiles(String str1, String str2)
    {
    	return !(str1.endsWith(".txt") && str2.endsWith(".txt")) ? false : true;
    }

	public static void main(String[] args)
	{
		if(args.length != 2)
			System.out.println("Invalid number of arguments. Enter <shipping_option_file> <book_order_file>");
		else
		{
			f1 = args[0];
			f2 = args[1];

			if(validateFiles(f1, f2) == false)
			{
				sc2 = new Scanner(System.in);
				while(true)
				{
					System.out.println("Enter valid file names with '.txt' extension");

					String input = sc2.nextLine();
					String[] in = input.split(" ");

					f1 = in[0].trim();
					f2 = in[1].trim();

					if(validateFiles(f1,f2) == true)
						break;
				}
		    }
		    add(f2);
		    createBoxes(f1);

            System.out.println("\nPacking list");

		    packBoxes(bx);

            printBox(boxes_used);

            typesUsed(boxes_used);
	   }
	}
}
