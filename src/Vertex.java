/**
 * Representation of a graph vertex
 */
public class Vertex implements Comparable<Vertex>{
	// label attached to this vertex
	private String label;
	public int cost;
	public Vertex path;

	/**
	 * Construct a new vertex
	 * 
	 * @param label
	 *            the label attached to this vertex
	 */
	public Vertex(String label) {
		if (label == null)
			throw new IllegalArgumentException("null");
		this.label = label;
		cost = Integer.MAX_VALUE;
		path = null;
	}

	/**
	 * Get a vertex label
	 * 
	 * @return the label attached to this vertex
	 */
	public String getLabel() {
		return label;
	}

	/**
	 * A string representation of this object
	 * 
	 * @return the label attached to this vertex
	 */
	public String toString() {
		return label;
	}

	// auto-generated: hashes on label
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((label == null) ? 0 : label.hashCode());
		return result;
	}

	// auto-generated: compares labels
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		final Vertex other = (Vertex) obj;
		if (label == null) {
			return other.label == null;
		} else {
			return label.equals(other.label);
		}
	}

	@Override
	public int compareTo(Vertex o) {

		return cost - o.cost;
	}



}
