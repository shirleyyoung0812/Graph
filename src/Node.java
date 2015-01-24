import java.util.*;

public class Node {
	int label;
	List<Node> neighbors;
	//<neighbor node, connecting edge>
	Map<Node, Edge> edges;
	int dist;
	
	
	Node(int x) {
		label = x;
		neighbors = new ArrayList<Node> ();
		edges = new HashMap<Node, Edge> ();
		dist = Integer.MAX_VALUE;
	}
	Node(Node n) {
		//only copy the label from another node, 
		//neighbors and edges need
		//to be redefined
		this.label = n.label;
		neighbors = new ArrayList<Node>();
		edges = new HashMap<Node, Edge> ();
		dist = Integer.MAX_VALUE;
	}
	/**
	 * Construct a node object with specified source
	 * @param n
	 * @param source: source node
	 */
	
	//the hash function to determine the equality of two nodes
	//for graphs with unique nodes, this is not required
	private int getHash() {
		int hash_a = 378551;
		int hash_b = 63689;
		int hash = 0;
		for (Node neighbor : neighbors) {
			hash += hash * hash_a + neighbor.label;
			hash_a = hash_a * hash_b;
		}
		//hash += label;
		return hash;
	}
	
	public boolean equals(Node n) {
		return label == n.label && (getHash() == n.getHash());
	}
}
