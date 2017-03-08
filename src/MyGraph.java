import java.util.*;

/**
 * @author Lorenzo Pacis, Leah Ruisenor, Alex Merk.
 * @version March 5, 2017.
 * A representation of a graph. Assumes that we do not have negative cost edges
 * in the graph.
 */
public class MyGraph implements Graph {
	
	/**
	 * Collection of edges.
	 */
	private Collection<Edge>myEdges;
	
	/**
	 * Collection of Vertices.
	 */
	private Collection<Vertex> myVertices;
	/**
	 * Set of visited Vertices must be cleared after each method call of shortest path.
	 */
	public Set<Vertex> visited;
	/**
	 * A list of paths to be returned from shortest path method must be cleared after each call.
	 */
	public List<Vertex> verticiesPath;



	/**
	 * Creates a MyGraph object with the given collection of vertices and the
	 * given collection of edges.
	 * 
	 * @param v
	 *            a collection of the vertices in this graph
	 * @param e
	 *            a collection of the edges in this graph
	 */
	public MyGraph(Collection<Vertex> v, Collection<Edge> e) {
		
		if(v == null || e == null || v.isEmpty() || e.isEmpty()) {
			throw new IllegalArgumentException("null or empty collections");
		}
		verticiesPath = new ArrayList<Vertex>();
		myVertices = new ArrayList<Vertex>();
		myEdges = new ArrayList<Edge>();
		visited = new HashSet<Vertex>();
		addVerticies(v);
		checkEdgeWeights(e);
		generateEdges(e);

	}
	
	/**
	 * Generates the edges, and checks if that edge already exists with a different weight.
	 * @param e the colleciton of edges.
	 */
	private void generateEdges(Collection<Edge> e) {
		boolean addAble = false;
		for(Edge edg : e) {
			if(!myEdges.isEmpty()) {
				for(Edge ed : myEdges){
					if(edg.getSource().equals(ed.getSource()) 
							&& edg.getDestination().equals(ed.getDestination())) {
						
						if(edg.getWeight() != ed.getWeight()) {
							throw new IllegalArgumentException("Duplicate Edges with different weights");
						} else {
							addAble = false;
						}
					} else {
						addAble =true;
					}
				}
			} else {
				myEdges.add(edg);

			}
			if(addAble) {
				myEdges.add(edg);
			}

		}
		
	}
	
	/**
	 * Adds verticies to the collection if the collection does not already
	 * contain that vertex.
	 * @param v the collection of verticies.
	 */
	private void addVerticies(Collection<Vertex> v) {
		for(Vertex vert : v) {
			if(!myVertices.contains(vert)) {
				myVertices.add(vert);
			}
		}
		
	}
	

	
	
	/**
	 * Checks if any edge weights are 0 and throws an illegal argument exception.
	 * @param e the collection of edges.
	 */
	private void checkEdgeWeights(Collection<Edge> e) {
		for(Edge edg : e) {
			if(edg.getWeight() < 0) {
				throw new IllegalArgumentException("One or more edges "
						+ "contains a negative weight check file formating or input values");
			}
			
			
		}
	}


	/**
	 * Return the collection of vertices of this graph
	 * 
	 * @return the vertices as a collection (which is anything iterable)
	 */
	@Override
	public Collection<Vertex> vertices() {

		return myVertices;

	}

	/**
	 * Return the collection of edges of this graph
	 * 
	 * @return the edges as a collection (which is anything iterable)
	 */
	@Override
	public Collection<Edge> edges() {
		
		return myEdges;

	}

	/**
	 * Return a collection of vertices adjacent to a given vertex v. i.e., the
	 * set of all vertices w where edges v -> w exist in the graph. Return an
	 * empty collection if there are no adjacent vertices.
	 * 
	 * @param v
	 *            one of the vertices in the graph
	 * @return an iterable collection of vertices adjacent to v in the graph
	 * @throws IllegalArgumentException
	 *             if v does not exist.
	 */
	@Override
	public Collection<Vertex> adjacentVertices(Vertex v) {
		Collection<Vertex> adjacent = new ArrayList<Vertex>();
		for(Edge edg: myEdges) {
			if(edg.getSource().equals(v)) {
				adjacent.add(edg.getDestination());
			}
		}
		
		
		return adjacent;

	}

	/**
	 * Test whether vertex b is adjacent to vertex a (i.e. a -> b) in a directed
	 * graph. Assumes that we do not have negative cost edges in the graph.
	 * 
	 * @param a
	 *            one vertex
	 * @param b
	 *            another vertex
	 * @return cost of edge if there is a directed edge from a to b in the
	 *         graph, return -1 otherwise.
	 * @throws IllegalArgumentException
	 *             if a or b do not exist.
	 */
	@Override
	public int edgeCost(Vertex a, Vertex b) {
		for(Edge edg : myEdges){
			//If the vertex of the edge equals a and the destination of the edge equals b
			//return the weight otherwise return -1;
			if(edg.getSource().equals(a) && edg.getDestination().equals(b)) {
				return edg.getWeight();
			}
		}
		
		return -1;

	}
	
	/**
	 * Populates the priority que from the starting vertex.
	 * @param a the source vertex.
	 * @return A priority queue of vertex costs.
	 */
	private PriorityQueue<Vertex> populateQueue(Vertex a) {
	       PriorityQueue<Vertex> vertexQue = new PriorityQueue<>();
		   for(Vertex theVertex: myVertices) {
			   if(a.equals(theVertex)) {
				   theVertex.cost = 0;			
				   theVertex.path = null;
			   } else {
				   theVertex.cost = Integer.MAX_VALUE;
				   theVertex.path = null;
			   }
			   vertexQue.add(theVertex);
		   }
		   return vertexQue;
	}

	/**
	 * Returns the shortest path from a to b in the graph, or null if there is
	 * no such path. Assumes all edge weights are nonnegative. Uses Dijkstra's
	 * algorithm.
	 * 
	 * @param a
	 *            the starting vertex
	 * @param b
	 *            the destination vertex
	 * @return a Path where the vertices indicate the path from a to b in order
	 *         and contains a (first) and b (last) and the cost is the cost of
	 *         the path. Returns null if b is not reachable from a.
	 * @throws IllegalArgumentException
	 *             if a or b does not exist.
	 */
	public Path shortestPath(Vertex a, Vertex b) {
		
		//Queue to store the vertices
		 PriorityQueue<Vertex> vertexQue = populateQueue(a);

		 //Stores if we have visited a node or not
		 Set<Vertex> visited = new HashSet<>();
	     List<Vertex> path = new ArrayList<>();
	       
	       //Checks if we are already at the location.
			if(a.equals(b)) {
				return null;
			}
			for(Edge theEdge :myEdges) {
				theEdge.getSource().cost = Integer.MAX_VALUE;
				theEdge.getSource().path = null;
				theEdge.getDestination().cost = Integer.MAX_VALUE;
				theEdge.getDestination().path = null;
				
			}
	  
	       //While the queue of vertices is empty
	       while(!vertexQue.isEmpty()) {
	           Vertex source = vertexQue.poll();
	           
	           //sets the source to known
	           //since we have visited it
	           
	           visited.add(source);
	           for(Edge theEdge: myEdges) {
	        	   
	        	   //checks to make sure the edge cost actually exists
	        	   if(edgeCost(theEdge.getSource(),theEdge.getDestination()) != -1) {
	        		   
		               Vertex destination = theEdge.getDestination();
		               
		               Collection<Vertex> adjacentVertices = adjacentVertices(source);
		               
		               //If the destination hasn't been iterated through yet
		               //and the adjacent vertices adjacent to the source contains the edges destination
		               if(!visited.contains(destination) && source.equals(theEdge.getSource())
		            		   && adjacentVertices.contains(theEdge.getDestination())) {
		            	   
		                   if(source.cost + theEdge.getWeight() < destination.cost) {
		                	   
		                	   //sets this destinations cost
		                       destination.cost = source.cost + theEdge.getWeight();
		                       //and sets the destinations path
		                       destination.path = source;
		                       
		                       //if b is the same as the destination and the cost
		                       //is less than the cost of b
		                       //set them
		                       //allows for shorter paths to be found
		                       if(b.equals(destination) && destination.cost<b.cost) {
		                    	   
		                    	   b.path = destination.path;
		                    	   
		                    	   b.cost = destination.cost;
		                    	  
		                       }
		                       
		                       //adds the destination vertex into the que
		                       //so that it may be iterated over since we have not
		                       //"seen" it yet as a source
		                       vertexQue.add(destination);
		                   }
		               }
	        	   }

	           }
	       }

	       path.add(b.path);
	       return new Path(path, b.cost);
	    }



}