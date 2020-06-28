package com.assignment.colorgrid;

import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Stack;
import java.util.Vector;

/**
 *
 * @author ishani_s
 */
 
public class Graph {

    int V; //number of nodes 

    LinkedList<Integer>[] adjList; // adjacency lists 


    Graph(int V) {
        this.V = V;
        adjList = new LinkedList[V];

        for (int i = 0; i < adjList.length; i++) {
            adjList[i] = new LinkedList<Integer>();
        }

    }

    //for add an edge between given two nodes
    void addEdge(int node1, int node2) {
        adjList[node1].add(node2); // Add node2 to node1's adjacency list. 
    }

    // return a list with all not yet visited nodes reachable from startingNode that has the same color
    public LinkedList<Integer> DFS(int sourceNode, HashMap<Integer, NodeBean> grid) {
        LinkedList<Integer> result = new LinkedList<>();

        // initially mark all vertices as not visited 
        Vector<Boolean> visited = new Vector<>(V);
        for (int i = 0; i < V; i++) {
            visited.add(false);
        }

        // create a stack for DFS process
        Stack<Integer> stack = new Stack<>();

        // push the current node 
        stack.push(sourceNode);
        NodeBean startingNode = (NodeBean) grid.get(sourceNode);
        Integer color = startingNode.getValue(); //set the color of the findind block

        while (!stack.empty()) {
            // Pop a node from stack
            sourceNode = stack.peek();
            stack.pop();
 
            if (!visited.get(sourceNode)) {
                result.add(sourceNode);
                visited.set(sourceNode, true);
            }

            Iterator<Integer> itr = adjList[sourceNode].iterator();

            while (itr.hasNext()) {
                int v = itr.next();
                NodeBean val = (NodeBean) grid.get(v);
                if (!visited.get(v) && val.getValue() == color) {//push adjusant node if it has the same color and not visted before
                    stack.push(v);
                }
            }

        }
        return result;
    }
}
