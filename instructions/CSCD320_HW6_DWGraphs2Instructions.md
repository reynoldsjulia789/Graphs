# Directed Weighted Graphs 2
**Due:** Wednesday by 11:59pm\
**Points:** 288\
**Submission:** upload zip file to Canvas

For part 2 of this Graphs project, you will implement each of the search algorithms we have discussed in class.  These include Dijkstra's, Bellman Ford, and Floyd-Warshall.  Additionally, you will be using composition to compose a DWGraph object with one of these search algorithms at runtime.  This will be accomplished using the Strategy design pattern.  https://refactoring.guru/design-patterns/strategy

### New files to implement:

* Search (interface)
  * Path (a Record within Search that contains String src, String dest, an integer cost, the graph object, and String[] path consisting of the names of vertices on the shortest path from src to dest).  You may need to find a way to return multiple of these for Floyd-Warshall. 
* Dijkstras (class that implements Search)
  * Should be instantiated if src is the name of a vertex in the graph, dest is the name of a vertex in the graph, and there are no negative edge weights in the graph. 
* BellmanFord (class that implements Search)
  * Should be instantiated if src is the name of a vertex in the graph, dest is the name of a vertex in the graph, and there are negative edge weights in the graph. 
* FloydWarshall (class that implements Search)
  * Should be instantiated if src and dest are the exact string "\<ALL>"

### Modifications to existing classes:

* DWGraph will need an additional field of type Search.  This should not be instantiated in the constructor.
* DWGraph will need an additional method search(String src, String dest) that analyzes the current graph, sets the Search field to the best algorithm to handle the search query, and returns a Path representing the shortest path.

### At top of Search interface file in a block comment:
How did you implement your negative edge weights?  What are the benefits and detriments of your design decision?

### Extra Credit:

*You can do two options from here and receive points for both. Doing all 3 will not grant credit for the third.*

EC-1. Adjacency Maps vs Adjacency Lists  ||  \<specs to come>

EC-2. Directed Acyclic Graph Facade  ||  DAG_Implementation.md

EC-3. Seven Degrees of Kevin Bacon  ||  Baconator_Game.md

### Submission:
Put all your code files into a flat directory folder (no subfolders, no package declarations), zip it, and submit it here. Include all files in the project, not just the files modified for this assignment. Ensure your submitted folders have uniquely-identifiable names (aka not "DWG", "src", "Graph", "HW", "320HW5", etc...). The name does not need to be related to the assignment.

### Grading:

* Search interface: 12 points
* Path struct: 12 points
* Dijkstra's implementation: 36 points
* Bellman Ford implementation: 48 points
* Floyd-Warshall implementation: 84 points
* DWGraph changes: 24 points
* Reflection questions: 24 points
* Code professionalism and documentation: 24 points
* Adherence to API and implementation of Strategy: 24 points
* Extra credit: 36 points for one or 72 points for two.
* Unique Folder Name: 2 points for unique and creative, 3 points for it also differing significantly in theme from every folder name you've submitted thus far. Yes I will check.

**Total:** Up to 365 points, graded out of 288.

**Late Submissions:** Extensions will not be granted and late submissions not accepted, unless the student can demonstrate exceptional circumstances in accordance with University policy.