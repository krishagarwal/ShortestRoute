/**
 * ShortestRoute.java
 *
 * Determines one of the shortest routes that can be taken
 * between a set of points (not necessarily the absolute shortest)
 * by reading in points one at a time and inserting the point in 
 * an optimal location. Applies 2 main heuristics to determine these
 * locations: inserting a read-in point after the point in the route
 * it is closest two, inserting a read-in point at the location where
 * the insertion would cause the least change in the route's length.
 * An improved heuristic is also included where the heuristic takes
 * a read-in point, inserts the point using the "Insert at Smallest Increase"
 * heuristic, and traverses the route, swapping any adjacent points for which
 * a shorter length is produced. The route is modeled as a circular linked list, 
 * with each node representing a point in the route.
 * 
 * @author Krish Agarwal
 * @version 1.0
 * @since 3/22/2020
 */

import java.awt.Color;

public class ShortestRoute
{
    /** The first node in the linked list used to store the route */
    private ListNode first;
    
    /**
     * Constructs a ShortestRoute object
     */
	public ShortestRoute()
	{
		first = null;
    }
    
    /**
     * Returns the number of points in the route
     * @return the size of the linked list
     */
	public int size ()
	{
        if (first == null)
            return 0;
        int count = 0;
        ListNode curr = first;
        do
            count++;
        while ((curr = curr.getNext()) != first);
		return count;
	}
    
    /**
     * Returns the distance traveled in the route taken
     * @return the length of the route
     */
	public double length ( )
	{
        if (first == null)
            return 0.0;
        double length = 0.0;
        ListNode curr = first;
        Point currPoint = (Point)(curr.getValue());
        do
            length += currPoint.getDist(currPoint = (Point)(curr.getNext().getValue()));
        while ((curr = curr.getNext()) != first);
        return length;
    }

    /**
     * Takes in a point and inserts the point in the linked list
     * after the nearest point to it
     * @param p the point to be inserted
     */
	public void insertPointAtNearestNeighbor(Point p)
	{
        if (first == null)
        {
            first = new ListNode(p, null);
            first.setNext(first);
            return;
        }
        ListNode nearest = first;
        double smallestLength =  p.getDist((Point)(nearest.getValue())), newLength;
        for (ListNode curr = first.getNext(); curr != first; curr = curr.getNext())
        {
            newLength = p.getDist((Point)(curr.getValue()));
            if (newLength < smallestLength)
            {
                nearest = curr;
                smallestLength = newLength;
            }
        }
        nearest.setNext(new ListNode(p, nearest.getNext()));
    }

    /**
     * Takes in a point and inserts the point in the linked list
     * at the location where the length of the route would increase
     * the least
     * @param p the point to be inserted
     */
    public void insertPointAtSmallestIncrease(Point p)
    {
        if (first == null)
        {
            first = new ListNode(p, null);
            first.setNext(first);
            return;
        }
        ListNode nearest = null, curr = first;
        Point currP = (Point)(curr.getValue());
        double smallestIncrease = Double.MAX_VALUE;
        do
        {
            Point nextP = (Point)(curr.getNext().getValue());
            double increase = currP.getDist(p) + nextP.getDist(p) - currP.getDist(nextP);
            if (increase < smallestIncrease)
            {
                nearest = curr;
                smallestIncrease = increase;
            }
            curr = curr.getNext();
            currP = nextP;
        }
        while (curr != first);
        nearest.setNext(new ListNode(p, nearest.getNext()));
    }

    /**
     * Uses the insertPointAtSmallestIncrease() method to insert the
     * point at an optimal location and then improves the route by traversing
     * the route and swapping any adjacent points for which swapping yields a smaller
     * length, useful mainly for reducing the effects of reading in the points in an order
     * that causes a less optimal route.
     */
    public void insertAndImprove(Point p)
    {
        insertPointAtSmallestIncrease(p);
        if (first.getNext().getNext().getNext() == first)
            return;
        ListNode curr = first;
        do
        {
            Point p1 = (Point)(curr.getValue()), p2 = (Point)(curr.getNext().getValue()), p3 = (Point)(curr.getNext().getNext().getValue()), p4 = (Point)(curr.getNext().getNext().getNext().getValue());
            if (p1.getDist(p2) + p2.getDist(p3) + p3.getDist(p4) > p1.getDist(p3) + p3.getDist(p2) + p2.getDist(p4))
            {
                curr.getNext().setValue(p3);
                curr.getNext().getNext().setValue(p2);
            }
            curr = curr.getNext();
        }
        while (curr != first);
    }

    /**
     * Draws the route by drawing dots at each point and lines
     * between consecutive points in the route
     */
	public void draw ( )
	{
        ListNode node = first;
        do
        {
            ListNode next = node.getNext();
            Point curr = (Point)(node.getValue());
            StdDraw.setPenColor(new Color(0, 0, 255));
            StdDraw.line(((Point)(next.getValue())).getX(), ((Point)(next.getValue())).getY(), curr.getX(), curr.getY());
			StdDraw.setPenColor(new Color(255, 0, 0));
            StdDraw.filledCircle(curr.getX(), curr.getY(), 4);
            node = node.getNext();
        }
        while (node != first);
	}
    
    /**
     * Returns a string that contains the route taken, maintaining
     * order of the points visited
     * @return the string representation of the route
     */
    public String toString()
    {
		int count = 0;
		ListNode node = first;
        String result = new String("");
		do
		{
			result += String.format("%4d: %s%n",count,(Point)node.getValue());
			node = node.getNext();
			count++;
        }
        while (node != first);
		return result;
    }
}