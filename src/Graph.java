import java.util.*;


//undirected graph
//user can only retrieve the node by its label
//all labels are unique
//if other data needs to be stored in the node, a <Node, data> map can be used
public class Graph {
	private Map<Integer, Node> vertices;
	//# of vertices
	private int size;
	//# of edges
	private int edges;
	
	
	public Graph (){
		size = 0;
		vertices = new HashMap<Integer, Node>();
		edges = 0;
	}
	
	//construct a graph from another graph
	public Graph (Graph G) {
		this.size = G.size;
		this.vertices = new HashMap<Integer, Node>();
		this.edges = G.edges;
		copyGraph(G);
	}
	//clone a graph from another one
	/*
	 * ConcurrentModificationException: 
	 * For example, it is not generally permissible 
	 * for one thread to modify a Collection 
	 * while another thread is iterating over it.
	 */
	private void copyGraph(Graph G) {
		//clone nodes
		for (Node n : G.vertices.values()) {
			this.vertices.put(n.label, new Node(n));
		}
		//clone edges
		for (Node n : G.vertices.values()) {
			Node curr = getNode(n.label);
			for (Node w : n.neighbors) {
				Node neighbor = getNode(w.label);
				if (!isConnected(curr, neighbor)) {
					int weight = n.edges.get(w).getWeight(n, w);
					Edge e = new Edge(curr, neighbor, weight);
					curr.edges.put(neighbor, e);
					neighbor.edges.put(curr, e);
				}
				
			}
		}
	}
	
	//Add vertex
	public void addVertex(int label) {
		Node n = new Node(label);
		vertices.put(label, n);
		size++;
	}
	
	//Add edge
	//The edge class will take care of adding neighbors to the neighbor list of the node
	public void addEdge(int l1, int l2, int distance) {
		Node v = getNode(l1);
		Node w = getNode(l2);
		if (v.neighbors.contains(w) && w.neighbors.contains(v)) {
			System.out.println("Edge exists!");
			return;
		}
		Edge e = new Edge(v, w, distance);
		v.edges.put(w, e);
		w.edges.put(v, e);
		edges++;
	}
	public void chooseSource(int label) {
		Node source = getNode(label);
		source.dist = 0;
		
	}
	
	//if a node exists
	public boolean containsVertex(int label) {
		return vertices.containsKey(label);
	}
	
	//if there exists an edge between two node
	public boolean isConnected(int l1, int l2) {
		return getNode(l1).edges.containsKey(getNode(l2)) 
				&& getNode(l2).edges.containsKey(getNode(l1));
	}
	private boolean isConnected(Node n1, Node n2) {
		return n1.edges.containsKey(n2) && n2.edges.containsKey(n1);
	}
	
	public Node getNode(int label) {
		if (containsVertex(label))
			return vertices.get(label);
		return null;
	}
	
	/*public Iterable<Node> getNeighbors(int label) {
		if(!containsVertex(label))
			throw new IllegalArgumentException("Graph doesn't contain such node!");
		return getNode(label).neighbors;
	}*/
	
	//get neighbors of a node
	public String getNeighbors(int label) {
		String neighbor = "";
		for (Node n : getNode(label).neighbors) {
			neighbor += n.label + ", ";
		}
		return neighbor.substring(0, neighbor.length() - 2);
	}
	
	public int neighborSize(int label) {
		if (!containsVertex(label))
			throw new IllegalArgumentException("Graph doesn't contain such node!");
		return getNode(label).neighbors.size();
	}
	
	
	public int getSize() {
		return size;
	}
	
	
	public String toString() {
		StringBuilder s = new StringBuilder();
		String NEWLINE = System.getProperty("line.separator");
		s.append(size + " vertices, " + edges + " edges " + NEWLINE);
		for (Node v : vertices.values()) {
			s.append("Node: " + v.label + "\n");
			for (Node w : v.neighbors) {
				s.append(w.label +  "; edge: " + v.edges.get(w).getWeight(v, w) + NEWLINE);
			}
			s.append(NEWLINE);
		}
		return s.toString();
	}
}
