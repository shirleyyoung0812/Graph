public class GraphTester {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		/*Graph g = new Graph();
		g.addVertex(1);
		g.addVertex(2);
		g.addVertex(3);
		g.addEdge(1, 2, 1);
		g.addEdge(2, 3, 5);
		g.addEdge(3, 1, 3);
		//System.out.println(g.toString());
		Graph x = new Graph(g);
		//System.out.println(x.toString());	
		//System.out.println(x.isConnected(1, 2));
		System.out.println(x.getNeighbors(2));
		System.out.println(x.neighborSize(2));*/
		Graph netWork = new Graph();
		netWork.addVertex(1);
		netWork.addVertex(2);
		netWork.addVertex(3);
		netWork.addVertex(4);
		netWork.addVertex(5);
		netWork.addVertex(6);
		netWork.addVertex(7);
		netWork.addVertex(8);
		netWork.addVertex(9);
		netWork.addVertex(10);
		netWork.addVertex(11);
		netWork.addVertex(12);
		netWork.addVertex(13);
		netWork.addVertex(14);
		netWork.addVertex(15);
		netWork.addVertex(16);
		netWork.addEdge(1, 2, 1);
		netWork.addEdge(1, 3, 3);
		netWork.addEdge(1, 5, 21);
		netWork.addEdge(1, 11, 11);
		netWork.addEdge(2, 4, 38);
		netWork.addEdge(2, 7, 72);
		netWork.addEdge(2, 9, 3);
		netWork.addEdge(2, 10, 56);
		netWork.addEdge(2, 11, 32);
		netWork.addEdge(3, 4, 1);
		netWork.addEdge(3, 7, 7);
		netWork.addEdge(3, 12, 56);
		netWork.addEdge(5, 6, 19);
		netWork.addEdge(5, 7, 33);
		netWork.addEdge(5, 8, 127);
		netWork.addEdge(6, 7, 33);
		netWork.addEdge(6, 8, 500);
		netWork.addEdge(6, 15, 13);
		netWork.addEdge(7, 9, 121);
		netWork.addEdge(7, 13, 20);
		netWork.addEdge(8, 9, 39);
		netWork.addEdge(8, 10, 14);
		netWork.addEdge(9, 10, 5);
		netWork.addEdge(9, 12, 6);
		netWork.addEdge(10, 13, 198);
		netWork.addEdge(10, 16, 2);
		netWork.addEdge(11, 12, 13);
		netWork.addEdge(11, 14, 38);
		netWork.addEdge(12, 14, 15);
		netWork.addEdge(12, 16, 1);
		netWork.addEdge(13, 16, 3);
		netWork.addEdge(14, 15, 27);
		netWork.addEdge(15, 16, 7);
		System.out.println(netWork.toString());
		//FindFriends ff = new FindFriends (netWork.getNode(9), 3, 16);
		//System.out.println(ff.recommendedFriend());
		//System.out.println(ff.connection(netWork.getNode(7)));
	}

}
