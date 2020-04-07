/*************************************************************************
 *  DO NOT MODIFY THIS FILE
 *
 *  Time the two heuristics by generated random instances of size n.
 *
 *  % java -Xint ShortestRouteTimer 1000
 *
 *
 *  Tour length = 26338.42949015926
 *  Nearest insertion:  0.056 seconds
 *
 *  Tour length = 15505.745750759515
 *  Smallest insertion:  0.154 seconds
 *
 *
 *  The -Xint flag turns off various compiler optimizations, which
 *  helps normalize and stabilize the timing data that you collect.
 *
 *************************************************************************/

public class ShortestRouteTimer 
{
	public static void main(String[] args) 
	{
		double lo = 10.0, hi = 740.0;
		int n = 1000;
		if(args.length > 0)
		{
			n = Integer.parseInt(args[0]);
		}

		// generate data and run nearest insertion heuristic
		Stopwatch timer1 = new Stopwatch();
		ShortestRoute route1 = new ShortestRoute();
		for (int i = 0; i < n; i++) 
		{
			double x = lo + Math.random()*(hi - lo);
			double y = lo + Math.random()*(hi - lo);
			Point p = new Point(x, y);
			route1.insertPointAtNearestNeighbor(p);
		}
		double length1 = route1.length();
		double elapsed1 = timer1.elapsedTime();
		System.out.println("\n\n\nShortest Route length = " + length1);
		System.out.println("Nearest insertion:  " + elapsed1 + " seconds");
		System.out.println();

		// generate data and run smallest insertion heuristic
		Stopwatch timer2 = new Stopwatch();
		ShortestRoute route2 = new ShortestRoute();
		for (int i = 0; i < n; i++) 
		{
			double x = lo + Math.random()*(hi - lo);
			double y = lo + Math.random()*(hi - lo);
			Point p = new Point(x, y);
			route2.insertPointAtSmallestIncrease(p);
		}
		double length2 = route2.length();
		double elapsed2 = timer2.elapsedTime();
		System.out.println("Shortest Route length = " + length2);
		System.out.println("Smallest insertion:  " + elapsed2 + " seconds\n\n\n");
	}
}
