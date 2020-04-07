import java.util.Scanner;
import java.awt.Font;
import java.awt.Color;

public class InsertAtNearest
{
	private int HEIGHT, WIDTH, BORDER;
	private String file;
	private ShortestRoute route;
	
	public InsertAtNearest(String fileName)
	{
		file = new String(fileName);
		route = new ShortestRoute();
	}
	public static void main(String[] args) 
	{
		// get dimensions, set up canvas and drawing
		String f = new String("p100.txt");
		if(args.length > 0)
		{
			f = args[0];
		}
		InsertAtNearest run = new InsertAtNearest(f);
		run.getFileInputAndCreateLinkedList();
		run.showFinalRouteAndInfo();
	}
	
	public void getFileInputAndCreateLinkedList ( )
	{
		Scanner inFile = OpenFile.openToRead(file);
		WIDTH = inFile.nextInt();
		HEIGHT = inFile.nextInt();
		BORDER = 20;
		StdDraw.setCanvasSize(WIDTH * 2 / 3, (HEIGHT + BORDER) * 2 / 3);
		StdDraw.setXscale(0, WIDTH);
		StdDraw.setYscale(-BORDER, HEIGHT);
		Font font = new Font("Arial", Font.BOLD, 18);
		StdDraw.setFont(font);

		// turn on double buffering
		StdDraw.enableDoubleBuffering();
		System.out.println("\n\n");

		// run smallest insertion heuristic
		while(inFile.hasNext())
		{
			double x = inFile.nextDouble();
			double y = inFile.nextDouble();
			Point p = new Point(x, y);
			route.insertPointAtNearestNeighbor(p);
		}
		// route.makeRoute();
	}
	
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