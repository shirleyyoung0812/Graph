
import java.util.*;
public class Edge {
	int weight;
	Node n1;
	Node n2;
	//default weight = 1
	public Edge(Node n1, Node n2) {
		if (n1.equals (n2))
			throw new IllegalArgumentException("Cannot form an edge with itself!");
		if (n1.neighbors.contains(n2)) 
			throw new IllegalArgumentException("Edege exists!");
		this.n1 = n1;
		this.n2 = n2;
		n1.neighbors.add(n2);
		n2.neighbors.add(n1);
		weight = 1;
	}
	//constructor that allows user defined weight
	public Edge(Node n1, Node n2, int weight) {
		if (n1.equals (n2))
			throw new IllegalArgumentException("Dont't cheat, connecting with yourself doens't mean you have one more fan!");
		if (n1.edges.containsKey(n2) && n2.edges.containsKey(n1)) 
			throw new IllegalArgumentException("Edege exists!");
		this.n1 = n1;
		this.n2 = n2;
		n1.neighbors.add(n2);
		n2.neighbors.add(n1);
		this.weight = weight;
	}
	//to modify the weight
	public void modifyWeight(int w) {
		if (w <= 0)
			throw new IllegalArgumentException("Weight must be positive!");
		weight = w;
	}
	
	//get the nodes that are connected by this edge
	public List<Node> getNodes() {
		List<Node> nodes = new ArrayList<Node> ();
		nodes.add(n1);
		nodes.add(n2);
		return nodes;
	}
	//get the weight
	public int getWeight(Node n1, Node n2) {
		if (!(n1.equals(this.n1) && n2.equals(this.n2)) && !(n1.equals(this.n2) && n2.equals(this.n1)))
			throw new IllegalArgumentException("Wrong input node!");
		return weight;
	}
}
