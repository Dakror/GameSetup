package de.dakror.gamesetup.util.path;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import de.dakror.gamesetup.util.Vector;

/**
 * @author Dakror
 */
public abstract class AStar
{
	public static ArrayList<Node> openList;
	public static ArrayList<Node> closedList;
	static Vector target;
	
	public static AStar currentAStar;
	
	public static Path getPath(Vector start, Vector t)
	{
		try
		{
			openList = new ArrayList<>();
			closedList = new ArrayList<>();
			
			target = t;
			
			Comparator<Node> comparator = new Comparator<Node>()
			{
				@Override
				public int compare(Node o1, Node o2)
				{
					if (o1 == null || o2 == null) return 0;
					
					return Float.compare(o1.F, o2.F);
				}
			};
			
			openList.add(new Node(0, start.getDistance(target), start, null));
			
			Node activeNode = null;
			
			while (true)
			{
				if (openList.size() == 0)
				{
					return null; // no way
				}
				
				Collections.sort(openList, comparator);
				activeNode = openList.remove(0);
				
				closedList.add(activeNode);
				
				if (activeNode.H == 0)
				{
					break; // found way
				}
				
				currentAStar.handleNeighbors(activeNode, target, closedList, openList);
			}
			
			ArrayList<Node> path = new ArrayList<>();
			Node node = activeNode;
			path.add(node.clone());
			while (node.p != null)
			{
				path.add(node.p.clone());
				node = node.p;
			}
			
			Collections.reverse(path);
			return toPath(path);
		}
		catch (Exception e)
		{
			e.printStackTrace();
			return null;
		}
	}
	
	protected abstract void handleNeighbors(Node node, Vector target, ArrayList<Node> openList, ArrayList<Node> closedList);
	
	public static Path toPath(ArrayList<Node> list)
	{
		Path p = new Path();
		
		for (Node n : list)
			p.add(n.t);
		
		return p;
	}
}
