package com.assignment.colorgrid;

import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Random;

/**
 *
 * @author ishani_s
 */
public class ColorGrid {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        HashMap<Integer, NodeBean> gridExample = createGridTest();//create example grid given to test
        findBlock(gridExample, 12, 10); //call method to find the result block
    }

    
    /**
     * Method to create a random colour grid
     *
     * @param n
     * @param x
     * @param y
     * @return
     */
    private static HashMap<Integer, NodeBean> createRandomGrid(int n, int x, int y) {
        int count = 0;
        HashMap<Integer, NodeBean> gridMap = new HashMap<>();
        for (int i = 0; i < x; i++) {
            for (int j = 0; j < y; j++) {
                NodeBean bean = new NodeBean();
                Integer color = new Random().nextInt(n);//get random a number which represent the color
                bean.setValue(color);
                bean.setNodeIndex(count); //first element index is 0 and other indexes incremently indexed from left to right and top to bottom
                gridMap.put(count, bean);
                count++;
            }
        }
        return gridMap;
    }
    
    /**
     * method to print the grid
     *
     * @param grid
     * @param x
     * @param y
     */
    private static void printColorGrid(HashMap<Integer, NodeBean> grid, int x, int y) {
        int count = 0;
        for (int i = 0; i < x; i++) {
            for (int j = 0; j < y; j++) {
                NodeBean gridBean = (NodeBean) grid.get(count++);
                System.out.print(gridBean.getValue());
            }
            System.out.println();
        }
    }

    /**
     * Create a graph for the given grid
     *
     * @param grid
     * @param x
     * @param y
     * @return
     */
    private static Graph createGraph(HashMap<Integer, NodeBean> grid, int x, int y) {
        Graph g = new Graph(x * y);

        for (NodeBean key : grid.values()) {
            NodeBean bean = key;
            Integer node = bean.getNodeIndex(); //get the name of the paticular node

            if (node == 0) {
                //left top element
                g.addEdge(node, node + 1);
                g.addEdge(node, node + y);
            } else if (node == y - 1) {
                //right top element
                g.addEdge(node, node - 1);
                g.addEdge(node, node + y);
            } else if (node == (y * (x - 1))) {
                //left bottom element
                g.addEdge(node, node - y);
                g.addEdge(node, node + 1);
            } else if (node == x * y - 1) {
                //right bottom element
                g.addEdge(node, node - y);
                g.addEdge(node, node - 1);
            } else if (node < y - 1) {
                //top row elements
                g.addEdge(node, node + 1);
                g.addEdge(node, node - 1);
                g.addEdge(node, node + y);
            } else if (node % y == 0) {
                //left corner column elements
                g.addEdge(node, node - y);
                g.addEdge(node, node + y);
                g.addEdge(node, node + 1);
            } else if (node % y == (x - 1)) {
                //right corner column elements
                g.addEdge(node, node - 1);
                g.addEdge(node, node - y);
                g.addEdge(node, node + y);
            } else if (y * (x - 1) < node && node < (x * y) - 1) {
                //bottom row elements
                g.addEdge(node, node - 1);
                g.addEdge(node, node + 1);
                g.addEdge(node, node - y);
            } else {
                g.addEdge(node, node - 1);
                g.addEdge(node, node + 1);
                g.addEdge(node, node - y);
                g.addEdge(node, node + y);
            }
        }
        return g;
    }

    /**
     * Method to run customized DFS algorithm to find the relevant code block
     * @param g
     * @param grid
     * @param x
     * @param y 
     */ 
    private static void runDFS(Graph g, HashMap<Integer, NodeBean> grid, int x, int y) {
        LinkedList<Integer> result = new LinkedList<Integer>();
        LinkedList<Integer> temp = new LinkedList<Integer>();
        String indent = " ";
        
        //run dfs method to every node to find the largeset node block
        for (int i = 0; i < x * y; i++) {
            temp = g.DFS(i, grid);
            if (temp.size() > result.size()) {
                result = temp;
            }
        }

        Collections.sort(result); //sort the node index list
        
        //print the node block indexes(Note: first index is 0 and other indexes incremently indexed from left to right and top to bottom)
        for (int i = 0; i < result.size(); i++) {
            System.out.print(result.get(i) + indent);
        }
    }
    
    /**
     * Method to find the largest adjacent node block with the same colour
     * @param grid
     * @param x
     * @param y 
     */
    private static void findBlock(HashMap<Integer, NodeBean> grid, int x, int y) {
        //printColorGrid(grid, x, y);
        Graph g = createGraph(grid, x, y);
        runDFS(g, grid, x, y);
    }

    /**
     * Test - create given example grid to test the code
     *
     * @param x
     * @param y
     * @return
     */
    private static HashMap<Integer, NodeBean> createGridTest() {
        int count = 0;
        HashMap<Integer, NodeBean> gridMap = new HashMap<>();
        for (int i = 0; i < 12; i++) {
            for (int j = 0; j < 10; j++) {
                //1-blue,2-grey,3-red
                Integer[] arr = {
                    1, 1, 2, 1, 1, 3, 3, 1, 3, 1,
                    1, 3, 3, 3, 2, 3, 3, 3, 2, 1,
                    2, 2, 2, 2, 3, 2, 3, 2, 2, 2,
                    2, 2, 2, 1, 2, 1, 1, 1, 3, 2,
                    1, 1, 1, 3, 2, 3, 1, 3, 2, 3,
                    3, 1, 1, 3, 3, 1, 1, 3, 2, 3,
                    3, 2, 1, 3, 1, 3, 2, 1, 1, 1,
                    1, 2, 3, 3, 3, 3, 1, 1, 3, 3,
                    3, 3, 2, 1, 2, 2, 1, 3, 3, 2,
                    3, 1, 1, 1, 1, 1, 3, 1, 1, 2,
                    1, 3, 1, 1, 3, 1, 1, 1, 3, 3,
                    1, 3, 1, 1, 2, 1, 1, 1, 2, 3
                };
                NodeBean bean = new NodeBean();
                bean.setValue(arr[count]);
                bean.setNodeIndex(count);
                gridMap.put(count, bean);
                count++;
            }
        }
        return gridMap;
    }
}
