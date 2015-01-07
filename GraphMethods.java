package friends;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.NoSuchElementException;
import java.util.Scanner;


public class GraphMethods {
	
	//These are the global variables that are made in Graph Create.
	//These variables are accessed in the other Graph Methods.
	//Each key in the adjList is unique for this assignment is indexed in the order it is received. 
	ArrayList<Vertex> adjList = new ArrayList<Vertex>();
	
	//The Hash map holds the key which is the personName string and the value which is the adjList
	//index.  This allows for quick access to the adjList.
	HashMap<String, Integer> lookUp = new HashMap<String,Integer>(500,2.0f);
	//Holds the adjList and lookUp HashMap
	Graph friendGraph;
	
	/********************************************************************
	Graph build
	
    Input: file that lists names of all people and edges between them. 
    Here is the format of the input file for the sample friendship graph 
    given in the Background section.
    
    Result: The adjacency linked list representation, along with a data structure
    to be able to quickly translate from a person's name to vertex number. 
    There should also be a quick way to translate from vertex number to the person's name.

	Output: You don't have to print the representation, we will check your code 
	to see if you are storing the graph in this representation. 
	********************************************************************/
	public void graphCreate(String graphFile) throws FileNotFoundException{
		
		//These are local variables used in Graph Create.
		Scanner sc = new Scanner(new File(graphFile));
		String size = sc.nextLine();
		int sizeInt = Integer.parseInt(size);
		
		//This loop creates the adjList and lookUp table.
		for (int v = 0; v < sizeInt; v++ )
		{
			//Variables that cut up the input string into useful parts.
			String data = sc.nextLine();
			String[] dataSplit = data.split("\\|");
			String name = dataSplit[0];
			String enrolled = dataSplit[1];
			String school = null;
			
			//Logic for handling school attendance data.
			if(enrolled.equals("y"))
			{
				school = dataSplit[2];
				school = school.toLowerCase();
				Vertex yesSchool = new Vertex(name, school, null);
				adjList.add(yesSchool);
			}else{
				Vertex noSchool = new Vertex(name, null, null);
				adjList.add(noSchool);
			}
			
			//Hash the name with an index value equal to the loop counter.
			lookUp.put(name, v);
		}
		
		//This loop adds friendships with the Neighbor class to the adjList personName keys.
		while (sc.hasNext())
		{
			//Variables that cut up the input string into useful parts.
			String friendship = sc.next();
			String[] friendshipSplit = friendship.split("\\|");
			
			String nameOne = friendshipSplit[0];
			String nameTwo = friendshipSplit[1];
			
			nameOne = nameOne.toLowerCase();
			nameTwo = nameTwo.toLowerCase();
			
			int v1 = lookUp.get(nameOne);
			int v2 = lookUp.get(nameTwo);
			
			//Adds the Neighbor nodes to the correct keys.
			adjList.get(v1).neighborNext = new Neighbor(v2, adjList.get(v1).neighborNext);
			adjList.get(v2).neighborNext = new Neighbor(v1, adjList.get(v2).neighborNext);
		}
		
		//This is the final Graph with the adjList and lookUp table.
		friendGraph = new Graph(adjList,lookUp);
	}
	
	/********************************************************************
	Subgraph (students at a school)
	
    Input: Name of school (case insensitive), e.g. "penn state"

    Result: Subgraph of original graph, vertices are all students at the given school, 
    edges are a subset of the edges of the original graph such that both end points are 
    students at the school. The subgraph must be in stored in the adjacency linked lists form, 
    just as for the original graph.

    Output: Print the subgraph in the same format as the input in the Graph build section above.
    NOTE: This also means if there is an edge x--y in the graph, then your output must have 
    either x--y or y--x, but NOT both. 
	********************************************************************/
	
	public void subGraph(String school)
	{	
		//Checks for bad input
		if(school==null){
			return;
		}
		
		//Makes the input string case insensitive
		school = school.toLowerCase();
		
		//Creates the variables for the Sub Graph
		ArrayList<Vertex> sAdjList = new ArrayList<Vertex>();
		HashMap<String, Integer> sLookUp = new HashMap<String,Integer>(1000,2.0f);
		Graph subGraph = new Graph(sAdjList,sLookUp);
		
		
		//This loop creates the adjList and lookUp table for the sub graph.
		for(int i = 0; i < adjList.size(); i ++){
			Vertex vA = adjList.get(i);
			String currentSchool = null;
			if(vA.schoolName!=null)
			{
				currentSchool = vA.schoolName.toLowerCase();
				//If the school matches the sub graph is updated.
				if(currentSchool.equals(school))
				{
					Vertex vB = new Vertex(vA.personName, currentSchool, null);
					subGraph.adjList.add(vB);
					subGraph.lookUp.put(vB.personName, subGraph.adjList.size()-1);
				}
			}
		}
		
		//This loop adds friendships with the Neighbor class to the adjList personName keys.
		for(int j = 0; j < adjList.size(); j++){
			Vertex vC = adjList.get(j);
			Neighbor x = vC.neighborNext;
			
			if(school.equals(vC.schoolName)){
					//The inner loop scans all of the Neighbor nodes in a given key.
					while(x!=null){
						Vertex vD = adjList.get(x.vertexNumber);
						
						//If the school matches, the sub graph is updated
						if(school.equals(vD.schoolName)){
							int index1 = subGraph.lookUp.get(vD.personName);
							int index2 = subGraph.lookUp.get(vC.personName);
						
							Neighbor tmp2 = subGraph.adjList.get(index2).neighborNext;
							Neighbor tmp = new Neighbor(index1, tmp2);
							
							subGraph.adjList.get(index2).neighborNext = tmp;
					}	
						x = x.next;
				}
			}
		}
		
		
		//Prints the size of the sub graph
		System.out.println(subGraph.adjList.size());
		
		//Prints the names in the sub graph with their school information.
		for(int k = 0; k<subGraph.adjList.size();k++){
			System.out.println(subGraph.adjList.get(k).personName+"|y|"+school);
		}
		
		//A visit list to avoid duplicates in the print output.
		boolean[] visit = new boolean[adjList.size()];
		
		//Prints unique friendships in the sub graph
		for(int h = 0; h < subGraph.adjList.size(); h++){
			for(Neighbor neighbors = subGraph.adjList.get(h).neighborNext; neighbors!=null; neighbors=neighbors.next){		
				if(visit[h]==false || visit[neighbors.vertexNumber] == false){
					visit[h] = true;
					visit[neighbors.vertexNumber] = true;
					System.out.println(subGraph.adjList.get(h).personName + "|" + subGraph.adjList.get(neighbors.vertexNumber).personName);
				}
			}
		}
	}
	
	/********************************************************************
	Shortest path (Intro chain)
    
    Input: Name of person who wants the intro, and the name of the other person, 
    e.g. "sam" and "aparna" for the graph in the Background section. 
    (Neither of these, nor any of the intermediate people are required to be students, 
    in the same school or otherwise.)

    Result: The shortest chain of people in the graph starting at 
    the first and ending at the second.

    Output: Print the chain of people in the shortest path, for example:

    sam--jane-bob--samir--aparna

    If there is no way to get from the first person to the second person, 
    then the output should be a message to this effect. 
	 ********************************************************************/

	public void shortestPath(String firstPerson, String secondPerson)
			throws NoSuchElementException{
				if(firstPerson == null || secondPerson == null)
				{
					throw new NoSuchElementException("Please Enter Input:");
				}
				
				firstPerson = firstPerson.toLowerCase();
				secondPerson = secondPerson.toLowerCase();
				
				if(!lookUp.containsKey(firstPerson) || !lookUp.containsKey(secondPerson))
				{
					throw new NoSuchElementException();
				}
				
				int index;
				int front;
				Vertex source;
				Neighbor tracker;
				
				Queue storage = new Queue();
				
				int size = friendGraph.adjList.size();
				
				Vertex[] path = new Vertex[size];
				int[] distance = new int[size];
				
				for(int i = 0; i < size; i++){
					distance[i] = -1;
				}
				
				index = lookUp.get(secondPerson);
				distance[index] = 0;
				storage.enqueue(friendGraph.adjList.get(index));
				
				while(storage.isEmpty()==false){
					source = storage.dequeue();
					tracker = source.neighborNext;
					
					while(tracker != null){
						if(distance[tracker.vertexNumber] == -1){
							distance[tracker.vertexNumber] = distance[lookUp.get(source.personName)] + 1;
							path[tracker.vertexNumber] = source;
							
							storage.enqueue(friendGraph.adjList.get(tracker.vertexNumber));
						}
						tracker = tracker.next;
					}
				}
				
				if(distance[lookUp.get(firstPerson)] == -1 || firstPerson.equals(secondPerson)){
					throw new NoSuchElementException("No Path Exists!");
				}
				
				front = lookUp.get(firstPerson);
				
				while(secondPerson.equals(friendGraph.adjList.get(front).personName)==false){
					
					System.out.print(friendGraph.adjList.get(front).personName + "--");
					
					front = lookUp.get(path[front].personName);
				}
				System.out.println(secondPerson);
			}
	
	/********************************************************************
	Connected Islands (cliques)
    
    Input: Name of school for which cliques are to be found, e.g. "rutgers"

    Result: The subgraphs for each of the cliques.

    Output: Print the subgraph for each clique, 
    in the same format as the input described in the Graph build section. 
    For example:

    Clique 1:

    <subgraph output>

    Clique 2:

    <subgraph output>

    etc...

    Note: If there is even one student at the named school in the graph, 
    then there must be at least one clique in the output. 
    If the graph has no students at all at that school, then the output will be empty. 
	 ********************************************************************/

	public void connectedIslands(String school)
	{
		school = school.toLowerCase();
		boolean[] visit = new boolean[friendGraph.adjList.size()];
		Queue Q = new Queue();
		int index = 0;	
	
		
		for(int t = 0; t<friendGraph.adjList.size(); t++)
		{
			ArrayList<Vertex> cAdjList = new ArrayList<Vertex>();
			HashMap<String, Integer> cLookUp = new HashMap<String,Integer>(1000,2.0f);
			int count = 0;
			if(visit[t]==false)
			{
				if(school.equals(friendGraph.adjList.get(t).schoolName))
				{
					Q.enqueue(friendGraph.adjList.get(t));
					visit[t] = true;	
					cAdjList.add(0, friendGraph.adjList.get(t));
					cLookUp.put(friendGraph.adjList.get(t).personName, 0);
					t++;
				}
				while(Q.isEmpty()!=true)
				{	
					
					index = friendGraph.lookUp.get(Q.dequeue().personName);
					for(Neighbor neighbors = friendGraph.adjList.get(index).neighborNext; neighbors!=null; neighbors=neighbors.next)
					{	Vertex vA = friendGraph.adjList.get(neighbors.vertexNumber);
								if(visit[neighbors.vertexNumber]!=true){
									visit[neighbors.vertexNumber] = true;
									String currentSchool = null;
									if(vA.schoolName!=null){
										currentSchool = vA.schoolName;
										if(currentSchool.equals(school)){
											count++;
											Q.enqueue(friendGraph.adjList.get(neighbors.vertexNumber));
											Vertex vB = new Vertex(vA.personName, currentSchool, null);
											cAdjList.add(vB);
											
											cLookUp.put(vA.personName, count);
											
								}
							}
						}
					}
				}
			Graph cliqueGraph = new Graph(cAdjList,cLookUp);
			
			for(int k = 0; k<cliqueGraph.adjList.size();k++)
			{
				cliqueGraph.adjList.get(k).neighborNext = null;
			}
			if(cliqueGraph.adjList.size()!=0)
			{
				for(int j = 0; j < adjList.size(); j++)
				{
					Vertex vC = adjList.get(j);
					Neighbor x = vC.neighborNext;
					
					if(school.equals(vC.schoolName))
					{
						//The inner loop scans all of the Neighbor nodes in a given key.
						while(x!=null)
						{
							Vertex vD = adjList.get(x.vertexNumber);
		
							//If the school matches, the sub graph is updated
							
							if(school.equals(vD.schoolName))
							{
								if((cliqueGraph.lookUp.get(vD.personName)!=null)
								&&(cliqueGraph.lookUp.get(vC.personName)!=null)){
								int index1 = cliqueGraph.lookUp.get(vD.personName);
								int index2 = cliqueGraph.lookUp.get(vC.personName);
							
								Neighbor tmp2 = cliqueGraph.adjList.get(index2).neighborNext;
								Neighbor tmp = new Neighbor(index1, tmp2);
								
								cliqueGraph.adjList.get(index2).neighborNext = tmp;
								}
							}	
							x = x.next;
						}
					}
				}
				
				
				System.out.println(cliqueGraph.adjList.size());
				for(int k = 0; k<cliqueGraph.adjList.size();k++){
					System.out.println(cliqueGraph.adjList.get(k).personName+"|y|"+school);
				}
				boolean[] printVisit = new boolean[cliqueGraph.adjList.size()];
				
					for(int h = 0; h < cliqueGraph.adjList.size(); h++)
					{
						for(Neighbor neighborsC = cliqueGraph.adjList.get(h).neighborNext; neighborsC!=null; neighborsC=neighborsC.next){	
							if(printVisit[h]==false || printVisit[neighborsC.vertexNumber] == false){
								printVisit[h] = true;
								printVisit[neighborsC.vertexNumber] = true;
								System.out.println(cliqueGraph.adjList.get(h).personName + "|" + cliqueGraph.adjList.get(neighborsC.vertexNumber).personName);
							}
						}
					}
				}
			}
		}
	}
	
	/********************************************************************
	Connectors (Friends who keep friends together)

    Input: Nothing

    Result: Names of all people who are connectors in the graph

    Output: Print names of all people who are connectors in the graph, 
    comma separated, in any order. 
	 ********************************************************************/

	public void connectors() {
		boolean[] visited = new boolean[adjList.size()];
		int size = adjList.size();
		int[] dfsNum = new int[size];
		int[] back = new int[size];
		int dfsNumCounter = 0, backNumCounter = 0;
		int[] kimchi = new int[size];

		for (int i = 0; i < size; i++) {

			if (!visited[i]) {
				kimchi[i] = 1;


		DFS(adjList.get(i), visited, dfsNum, back, dfsNumCounter, backNumCounter, kimchi);
		
			}
		}

		System.out.print("Friends who keep friends together: ");
		for (int i = 0; i < adjList.size(); i++) 
		{
			if (kimchi[i] == 3) 
			{
				System.out.print(adjList.get(i).personName + " ");
				}
			}
			System.out.println();
		}

private void DFS(Vertex v, boolean[] visited, int[] dfsNum, int[] back, int dfsNumCount, int backCount, int[] connectors) {

		int index = lookUp.get(v.personName);
		visited[index] = true;
		dfsNum[index] = dfsNumCount;
		dfsNumCount++;
		back[index] = backCount;
		backCount++;

		
		Neighbor neighbor = v.neighborNext;
		while (neighbor != null) 
		{
			if (!visited[neighbor.vertexNumber]) 
			{
				Vertex next = adjList.get(neighbor.vertexNumber);
				DFS(next, visited, dfsNum, back, dfsNumCount, backCount, connectors);
				if (dfsNum[index] > back[neighbor.vertexNumber]) 
				{
					back[index] = Math.min(back[index], back[neighbor.vertexNumber]);
				}else{
					
				switch (connectors[index]) 
					{
		            case 0: connectors[index] = 3;
		                     break;
		            case 1:  connectors[index] = 2;
		                     break;
		            case 2:  connectors[index] = 3;
		                     break;
					}
				}	
			}
			
			if(dfsNum[index] > back[neighbor.vertexNumber]){	
				back[index] = Math.min(back[index], dfsNum[neighbor.vertexNumber]);
			}
			neighbor = neighbor.next;
		}
}
	/********************************************************************
	 A Print Utility
	 ********************************************************************/
	
	public void print(){
		System.out.println();
		for(int j = 0; j < adjList.size(); j++){
			System.out.print(adjList.get(j).personName);
			for(Neighbor neighbors = adjList.get(j).neighborNext; neighbors!=null; neighbors=neighbors.next){
				System.out.print(" ---> " + adjList.get(neighbors.vertexNumber).personName);
			}
			System.out.println("\n");
		}
	}
}
