package friends;

//The neighbor class that defines vertex connections on the graph.
//The vertex connections are friendships.
public class Neighbor {
	public int vertexNumber;
	public Neighbor next;
	
	public Neighbor(int vertexNumber, Neighbor neighbors){
		this.vertexNumber = vertexNumber;
		next = neighbors;
	}
}
