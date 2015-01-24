import java.util.*;
public class Dijkstra {
	private Graph g;
	private int[] distTo;
	Node source;
	private int[] predecessor;
	private PriorityQueue<Node> pq;
	
	public Dijkstra(Graph G, int s) {
		this.g = G;
		g.chooseSource(s);
		pq = new PriorityQueue<Node> (new NodeComparator());
		source = g.getNode(s);
		pq.add(source);
		distTo = new int[g.getSize()];
		distTo[s - 1] = 0; 
		predecessor = new int[g.getSize() + 1];
		predecessor[s] = -1;
	}
	public void getShortestPath() {
		while (!pq.isEmpty()) {
			Node curr = pq.poll();
			for (Node w : curr.neighbors) {
				int distanceToSource = curr.dist + curr.edges.get(w).getWeight(curr, w);
				if (distanceToSource < w.dist) {
					pq.remove(w);
					w.dist = distanceToSource;
					predecessor[w.label] = curr.label;
					pq.add(w);
				}
			}
		}
	}
	/**
	 * get the shortest path from source to target node
	 * @param n
	 */
	public void getShortestPath(int target) {
		boolean found = false;
		while (!pq.isEmpty() && !found) {
			Node curr = pq.poll();
			for (Node w : curr.neighbors) {
				int distanceToSource = curr.dist + curr.edges.get(w).getWeight(curr, w);
				if (distanceToSource < w.dist) {
					pq.remove(w);
					w.dist = distanceToSource;
					predecessor[w.label] = curr.label;
					pq.add(w);
				}
				if (w.label == target) {
					found = true;
					break;
				}
			}
		}
		
	}
	public String shortestPath(int label) {
		Node target = g.getNode(label);
		String NEWLINE = System.getProperty("line.separator");
		StringBuilder sb = new StringBuilder();
		sb.append("The shortest distance from " + label + " to source " + source.label 
				+ " is: " + target.dist);
		sb.append(NEWLINE);
		int x;
		for (x = label; g.getNode(x).dist != 0; x = predecessor[x]) {
			sb.append(x + " -> ");
		}
		sb.append(source.label);
		//sb.delete(sb.length() - 4, sb.length() - 1);
		sb.append(NEWLINE);
		return sb.toString();
	}
	private class NodeComparator implements Comparator<Node> {
		
		public int compare(Node a, Node b) {
			if (a.dist - b.dist < 0)
				return -1;
			else if (a.dist - b.dist > 0)
				return 1;
			return 0;
		}
	}

}
