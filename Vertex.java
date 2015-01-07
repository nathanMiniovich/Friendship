package friends;

//The vertex class that defines the data at a point on the graph.
public class Vertex {
	String personName;
	String schoolName;
	Neighbor neighborNext;
	
	public Vertex(String personName, String schoolName, Neighbor neighborNext){
		this.personName = personName;
		this.schoolName = schoolName;
		this.neighborNext = neighborNext;
	}
}
