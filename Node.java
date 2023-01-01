import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

public class Node  implements Comparable<Node> {

    private String name;
    boolean isVisited = false;
    private Map<Node,Integer> adjacentNodes;
    private LinkedList<Node> shortestPath;
    public Node previous;
    private int cost;
    private int distance;
    
    //Constructor of node.
    public Node(String name){
        this.name = name;
        shortestPath = new LinkedList<>();
        adjacentNodes = new HashMap<>();
        cost = Integer.MAX_VALUE;
        distance = Integer.MAX_VALUE;
    }
    
    public String getName(){
        return name;
    }
    public void SetName(String name){
        this.name = name;
    }
    
    public void setDistance(int distance){
        this.distance = distance;
    }
    public int getDistance(){
        return distance;
    }
    
    public void setShortestPath(LinkedList<Node> shortestPath){
        this.shortestPath = shortestPath;
    }
    public LinkedList<Node> getShortestPath(){
        return shortestPath;
    }
    
    public void add(Node neighborNode, int cost){
        adjacentNodes.put(neighborNode, cost);
    }
    public Map<Node,Integer> getNeighbors(){
        return adjacentNodes;
    }
    
    public void setCost(int cost){
        this.cost = cost;
    }
    public int getCost(){
        return cost;
    }
    //Compare cost.
    @Override
    public int compareTo(Node otherNode) {
        return Integer.compare(this.cost, otherNode.cost);
    }

}
