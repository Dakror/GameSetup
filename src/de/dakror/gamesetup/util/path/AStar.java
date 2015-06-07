/*******************************************************************************
 * Copyright 2015 Maximilian Stark | Dakror <mail@dakror.de>
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *   http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 ******************************************************************************/


package de.dakror.gamesetup.util.path;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import de.dakror.gamesetup.util.Vector;

/**
 * @author Dakror
 */
public abstract class AStar {
	public static ArrayList<Node> openList;
	public static ArrayList<Node> closedList;
	static Vector target;
	
	public synchronized static Path getPath(Vector start, Vector t, AStar method) {
		try {
			openList = new ArrayList<>();
			closedList = new ArrayList<>();
			
			target = t;
			
			Comparator<Node> comparator = new Comparator<Node>() {
				@Override
				public int compare(Node o1, Node o2) {
					if (o1 == null || o2 == null) return 0;
					
					return Float.compare(o1.F, o2.F);
				}
			};
			
			openList.add(new Node(0, start.getDistance(target), start, null));
			
			Node activeNode = null;
			
			while (true) {
				if (openList.size() == 0) {
					return null; // no way
				}
				
				Collections.sort(openList, comparator);
				activeNode = openList.remove(0);
				
				closedList.add(activeNode);
				
				if (activeNode.H == 0) {
					break; // found way
				}
				
				method.handleNeighbors(activeNode, target, openList, closedList);
			}
			
			ArrayList<Node> path = new ArrayList<>();
			Node node = activeNode;
			path.add(node.clone());
			while (node.p != null) {
				path.add(node.p.clone());
				node = node.p;
			}
			
			Collections.reverse(path);
			return toPath(path);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	protected abstract void handleNeighbors(Node node, Vector target, ArrayList<Node> openList, ArrayList<Node> closedList);
	
	public static Path toPath(ArrayList<Node> list) {
		Path p = new Path();
		
		for (Node n : list)
			p.add(n.t);
		
		return p;
	}
}
