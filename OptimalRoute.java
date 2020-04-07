

/**
 * OptimalRoute.java
 *
 * A tester for an improved "Insert at Smallest Increase" heuristic
 * in which, as the points are being inserted using the given heuristic,
 * the route is improved.
 * 
 * @author Krish Agarwal (largely based on InsertAtNearest.java and SmallestIncrease.java)
 * @version 1.0
 * @since 4/6/2020
 */

import java.util.Scanner;
import java.awt.Font;
import java.awt.Color;

public class OptimalRoute
{
    /** The dimensions of the window */
    private int HEIGHT, WIDTH, BORDER;
    
    /** The file to read points from */
    private String file;
    
    /** The optimal route between the points */
	private ShortestRoute route;
    
    /** Constructs an OptimalRoute object */
	public OptimalRoute(String fileName)
	{
		file = new String(fileName);
		route = new ShortestRoute();
	}
    
    /**
     * Entry point for the program.
     * @param args the arguments given when the program is run (uses
     *             the first parameter to determine the file to be read)
     */
	public static void main(String[] args) 
	{
		// get dimensions, set up canvas and drawing
		String f = new String("p100.txt");
		if(args.length > 0)
		{
			f = args[0];
		}
		OptimalRoute run = new OptimalRoute(f);
		run.getFileInputAndCreateLinkedList();
		run.showFinalRouteAndInfo();
	}
    
    /**
     * Reads from the input file, and as points are being read
     * from the file, those points are being inserted into the route
     */
	public void getFileInputAndCreateLinkedList ( )
	{
		Scanner inFile = OpenFile.openToRead(file);
		WIDTH = inFile.nextInt();
		HEIGHT = inFile.nextInt();
		BORDER = 20;
		StdDraw.setCanvasSize(WIDTH, HEIGHT + BORDER);
		StdDraw.setXscale(0, WIDTH);
		StdDraw.setYscale(-BORDER, HEIGHT);
		Font font = new Font("Arial", Font.BOLD, 18);
		StdDraw.setFont(font);

		// turn on double buffering
		StdDraw.enableDoubleBuffering();

		// run improved smallest insertion heuristic
		while(inFile.hasNext())
		{
			double x = inFile.nextDouble();
			double y = inFile.nextDouble();
			Point p = new Point(x, y);
			route.insertAndImprove(p);
		}
    }
    
    /**
     * Draws the route and prints the order/length/size of the route
     */
	public void showFinalRouteAndInfo ( )
	{
		// draw to standard draw 
		route.draw();
		StdDraw.setPenColor(new Color(0,0,0));
		StdDraw.textLeft(20, 0, "length = " + route.length());
		StdDraw.show();
        
		// print tour to standard output
		System.out.println("\n\n\nROUTE IN ORDER OF POINTS VISITED: \n\n" + route);
		System.out.printf("Route length = %.4f\n", route.length());
		System.out.printf("Number of points = %d\n", route.size());
		System.out.println("\n\n\n");
	}
}