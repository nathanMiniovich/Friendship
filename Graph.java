package friends;
import java.util.ArrayList;
import java.util.HashMap;

//The graph class holds the adjList and the lookUp hash table.
public class Graph 
{
	ArrayList<Vertex> adjList;
	HashMap<String, Integer> lookUp;

	public Graph(ArrayList<Vertex> adjList, HashMap<String, Integer> lookUp) 
	{
		this.adjList = adjList;
		this.lookUp =lookUp;
	}
}
