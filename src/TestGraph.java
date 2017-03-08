import java.util.ArrayList;
import java.util.Collection;
import java.util.Scanner;

/**
 * Testing class for the MyGraph class.
 * @author Lorenzo Pacis, Leah Ruisenor, Alex Merk.
 * @version March 5, 2017.
 *
 */
public class TestGraph {
	public static void main(String[] args) {
		Collection<Vertex> vertex = new ArrayList<Vertex>();
		Collection<Edge> edge = new ArrayList<Edge>();
		Vertex a = new Vertex("Point a");
		Vertex b = new Vertex("Point b");
		Vertex c = new Vertex("Point c");
		Vertex d = new Vertex("Point d");
		Vertex e = new Vertex("Point e");
		Vertex f = new Vertex("Point f");
		
		vertex.add(a);
		vertex.add(b);
		vertex.add(c);
		vertex.add(d);
		vertex.add(e);
		vertex.add(f);
		edge.add(new Edge(b,c,100));
		edge.add(new Edge(a,b,20));
		
		//Ignores the second edge since they have the same weight.
		edge.add(new Edge(c,f,50));
		edge.add(new Edge(c,f,50));
		
		//Throws an error, cannot have two directions with different weights.
		//edge.add(new Edge(c,f,10));
		//This throws an error, weights cannot be negative.
		//edge.add(new Edge(a,b,-10));
	
		//This  throws an error, edge direction to itself should not exist
		//edge.add(new Edge(b,b,100));

		
		MyGraph g = new MyGraph(vertex,edge);
		Scanner console = new Scanner(System.in);
		System.out.println("Vertices are "+g.vertices());
		System.out.println("Edges are "+g.edges());
		while(true) {
			System.out.print("Start vertex? ");
			Vertex a1 = new Vertex(console.nextLine());
			if(!vertex.contains(a1)) {
				System.out.println("no such vertex");
				System.exit(0);
			}
			
			System.out.print("Destination vertex? ");
			Vertex b1 = new Vertex(console.nextLine());
			if(!vertex.contains(b)) {
				System.out.println("no such vertex");
				System.exit(1);
			}
			Path p =g.shortestPath(a1, b1);
			if(p != null) {
				
				System.out.println("SHORTEST PATH:\n" + p.vertices +"\n" +p.cost);


			} else {
				System.out.println("Path Does Not Exist or Path is to itself.");
			}

		}
	
	}
}
