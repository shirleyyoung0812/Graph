import java.util.*;

/*
 * This class is used for 
 * 1. find recommendations from friends of friends of the user
 * 2. find the closest connections between the user and a person
 */
public class FindFriends {
	Node user;
	List<Node> potentialFriends;
	//all friends of the user that connect with fof <fof, friend list>
	Map<Node, List<Node>> connectingFriends;
	Map<Node, Double> distanceFOF;
	int numRecommendations;
	int GraphSize;
	
	/*
	 * this constructor will find the potential friends list for the user 
	 * and generate the distance between the user and a potential friend (fof)
	 */
	public FindFriends(Node n, int numRecommendations, int GraphSize){
		if (numRecommendations <= 0)
			throw new IllegalArgumentException("At least one recommendation, please...");
		user = n;
		potentialFriends = new ArrayList<Node> ();
		connectingFriends = new HashMap<Node, List<Node>>();
		distanceFOF = new HashMap<Node, Double> ();
		this.numRecommendations = numRecommendations;
		this.GraphSize = GraphSize;
		getPotentialFriends(n);
		getDistance();
	}
	
	//find all fof from user's friends
	private void getPotentialFriends(Node n) {
		for (Node w : n.neighbors) {
			for (Node fof : w.neighbors) {
				if (!n.neighbors.contains(fof) && !fof.equals(user)) {
					if (!potentialFriends.contains(fof))
						potentialFriends.add(fof);
					if (!connectingFriends.containsKey(fof)) {
						connectingFriends.put(fof, new ArrayList<Node>());
						connectingFriends.get(fof).add(w);
					}
					else 
						connectingFriends.get(fof).add(w);
				}
			}
		}
	}
	/*
	 * distance is calculated by a formula from FB
	 * the weight of the edge is actually the number of days 
	 * that two people are connected
	 */
	private void getDistance() {
		for (Node fof : potentialFriends) {
			double v = 0;
			for (Node friend : connectingFriends.get(fof)) {
				//distance between user and friend
				double u_f = (double)user.edges.get(friend).getWeight(user, friend);
				//distance between friend and fof
				double f_fof = (double)friend.edges.get(fof).getWeight(friend, fof);
				v += Math.pow(u_f * f_fof, -0.3) / Math.sqrt(friend.neighbors.size());
			}
			distanceFOF.put(fof, v);
		}
	}
	/*
	 * find recommendations
	 * fofs with shortest distance will be recommended
	 */
	private PriorityQueue<Node> getRecommendations() {
		NodeComparator nc = new NodeComparator() ;
		PriorityQueue<Node> recommendedFriend = new PriorityQueue<Node>(numRecommendations, nc); 			
		Collections.sort(potentialFriends, nc);
		Node last = null;
		for (Node fof : potentialFriends) {
			//System.out.println(fof.label);
			if (recommendedFriend.size() < numRecommendations) {
				recommendedFriend.add(fof);
				last = fof;
			}
			else {
				if(nc.compare(fof, last) < 0) {
					System.out.println(last.label);
					recommendedFriend.remove(last);
					recommendedFriend.add(fof);
					last = fof;
				}
			}
		}
		return recommendedFriend;
	}
	public String recommendedFriend() {
		PriorityQueue<Node> pq = getRecommendations();
		StringBuilder sb = new StringBuilder();
		sb.append("People you may know: \n");
		for (Node n : pq) {
			sb.append(n.label).append(", ");
		}
		return sb.delete(sb.length() - 2, sb.length() - 1).append("\n*****").toString();
	}
	private class NodeComparator implements Comparator<Node> {
		public int compare(Node a, Node b) {
			double da = distanceFOF.get(a);
			double db = distanceFOF.get(b);
			if (da - db < 0)
				return -1;
			else if (da - db > 0)
				return 1;
			return 0;
			
		}
	
	}
	
	/*
	 * find the shortest path from the user to a random (-.-)person
	 * BFS is used
	 */
	private List<List<Node>> findConnections(Node target) {
		//<Current node, Previous node>
		Map<Node, List<Node>> predecessor = new HashMap<Node, List<Node>>();
		//<Current node, distance to user>
		Map<Node, Integer> distance = new HashMap<Node, Integer>(); 
		List<List<Node>> connections = new ArrayList<List<Node>> ();
		getConnections(target, predecessor, distance);
		if (distance.size() == 1)
			return connections;
		getList(target, predecessor, distance, connections, new ArrayList<Node>());
		return connections;
	}
	
	private void getConnections(Node target, Map<Node, List<Node>> predecessor, 
			Map<Node, Integer> distance) {
		Queue<Node> q = new LinkedList<Node> ();
		predecessor.put(user, new ArrayList<Node>());
		distance.put(user, 0);
		q.offer(user);
		boolean found = false;
		//is it possible to terminate the recursion without knowing the graph size
		//if no path is found? 
		while (distance.size() < GraphSize && predecessor.size() < GraphSize && !found) {
			Node curr = q.poll();
			if (curr.equals(target)) {
				found = true;
				continue;
			}
			for (Node neighbor : curr.neighbors) {
				if (neighbor.equals(user))
					continue;
				if (!predecessor.containsKey(neighbor)) {
					predecessor.put(neighbor, new ArrayList<Node>());
					predecessor.get(neighbor).add(curr);
				}
				else {
					if (!predecessor.get(neighbor).contains(curr))
						predecessor.get(neighbor).add(curr);
				}
				if (!distance.containsKey(neighbor)) {
					distance.put(neighbor, distance.get(curr) + 1);
				}
				q.add(neighbor);
			}
		}
	}
	private void getList(Node curr, Map<Node, List<Node>> predecessor, Map<Node, Integer> distance, 
			List<List<Node>> connections, List<Node> path) {
		path.add(curr);
		if (curr.equals(user)) {
			Collections.reverse(path);
			connections.add(new ArrayList<Node> (path));
			//Never ever forget this!
			Collections.reverse(path);
		}
		else {
			for (Node n : predecessor.get(curr)) {
				if (distance.containsKey(n) && distance.get(curr) == distance.get(n) + 1) {
					getList(n, predecessor, distance, connections, path);
				}
			}
		}
		path.remove(path.size() - 1);
	}
	public String connection(Node target) {
		List<List<Node>> connections = findConnections(target);
		String NEWLINE = System.getProperty("line.separator");
		StringBuilder sb = new StringBuilder();
		sb.append("You can be connected with " + target.label + " through: " + NEWLINE);
		for (List<Node> path : connections) {
			for (Node n : path) {
				sb.append(n.label + " -> ");
			}
			sb.delete(sb.length() - 4, sb.length() - 1);
			sb.append(NEWLINE);
			
		}
		return sb.toString();
	}
	
}
