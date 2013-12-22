package com.FBLA.businesssim.util;

/**
 * Purpose: Helps with AI and path finding
 * -----
 * @author  Tripp
 * @date    11/12/13
 * @update  Created class, made constructor, and the Costs for tracking. Needs
 *          the methods to calc cost, and pick shortest path. (A* tracking)
 * -----
 */
public class Node {
    
    private Node parent;
    private Vector2i v;
    private double gCost, fCost, hCost;
    
    /**
     * Creates an instance of the node that is used for tracking and AI, use A*
     * @param parent the node that lead to the current node
     * @param v the vector position of the specific node
     * @param gCost the cost of travel from startNode to endNode
     * @param hCost the cost of travel from startPos to endPos
     */
    public Node(Node parent, Vector2i v, int gCost, int hCost)
    {
        this.parent = parent;
        this.v = v;
        this.gCost = gCost;
        this.hCost = hCost;
        this.fCost = gCost + hCost;
    }
}
