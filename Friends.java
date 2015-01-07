//Jonathan Risinger and Nathan Miniovich
package friends;
import friends.GraphMethods;

import java.io.FileNotFoundException;
import java.util.Scanner;

public class Friends{
	

	public static void main(String[] args) throws FileNotFoundException
	{	
		//Input variables
		Scanner userInput = new Scanner(System.in);
		GraphMethods graph = new GraphMethods();
		System.out.print("Please enter the friendship graph file name: ");
		
		String fileName = userInput.nextLine();
		
		//Creates a new graph object.
		graph.graphCreate(fileName);
		
		//This just calls the print utility to make sure that graph was built properly.
		//graph.print();
		
		//Condition to break out of the Options Menu
		boolean quit = false;
		
		//Prints the options menu and takes in user Input.
		while(!quit)
		{	
		System.out.println("\nFriendship Graph Options:\n");
		System.out.println("1. Subgraph: Students at a school");
		System.out.println("2. Shortest path: Intro Chain");
		System.out.println("3. Connected Islands: Cliques");
		System.out.println("4. Connectors: Friends who keep friends together");
		System.out.println("5. Quit");
		
		System.out.println("\nPlease enter your choice: ");

		int userChoice = userInput.nextInt();

			switch(userChoice)
			{
				case 1:
					System.out.println("Enter the name of the school: ");
					userInput.nextLine();
					String schoolSubGraph = userInput.nextLine();
					graph.subGraph(schoolSubGraph);
					break;
				case 2:
					System.out.println("Enter the name of the first person: ");
					userInput.nextLine();
					String firstPerson = userInput.nextLine();
					System.out.println("Enter the name of the second person: ");
					String secondPerson = userInput.nextLine();
					graph.shortestPath(firstPerson, secondPerson);
					break;
				case 3:
					System.out.println("Enter the name of the school: ");
					userInput.nextLine();
					String schoolIsland = userInput.nextLine();
					graph.connectedIslands(schoolIsland);
					break;
				case 4:
					graph.graphCreate(fileName);
					graph.connectors();
					break;
				case 5:
					quit = true;
			}
		}
		return;
	}
}