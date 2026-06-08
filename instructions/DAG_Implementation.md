# Directed Acyclic Graph Facade

## Premise
The Directed Acyclic Graph (DAG) is a particular structure that shows up frequently in computer science. Like its name suggests, it cannot contain any cycles (loops). It also does not have weights on the edges. Your task is to create a DAG facade, with a nearly-identical public API to the original DWGraph. However, it must:
- Reject any ``add(...)`` call that would result in a cycle being added to the graph by throwing a custom Exception
- Hide or otherwise prevent graph weights from being manipulated

Additionally, DAGs are most useful when all the edges point the same direction. When arranged like this, the graph is sorted Topologically. In fact, a topological sort is a property exclusive to DAGs; no graphs that contain a cycle can be sorted this way. You also need to:
- Make a topoSort() method that returns a list of the nodes topologically sorted. 
 

## Specs
Add:
- DAG.java

### DAG.java API changes
From the DWGraph API, remove or obscure anything having to do with edge weights and add: public static List<Nodes> topoSort().

## Reflection Questions:
Provide your answer at the top of the NEW DAG.java file, in a block comment.
- What was your process to create the DAG? How does it differ from the original DWGraph?
- How did you handle verifying that the graph is acyclic? Defend your design choices.

## Learning Outcomes
The goal of this extra credit unit is to have the student practice: critical thinking, problem solving, independent code development, independent research, and sorting method implementation.
