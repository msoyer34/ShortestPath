import java.util.*;
import java.util.Map.Entry;

public class Graph {

    private HashMap<String,Node> nodes;
    private PriorityQueue<Node> heap;
    private HashMap<String, Node> flags;
    public Graph(){
        nodes = new HashMap<>();
        heap = new PriorityQueue<>();
        flags = new HashMap<>();
    }
    public void addNode(Node newNode){
        nodes.put(newNode.getName(),newNode);
    }
    public void addFlag(String name){
        flags.put(name, getNodeByName(name));
    }
    public Node getNodeByName(String name){
        return nodes.get(name);
    }
    public void computeShortestPath(Node startNode) {
        startNode.setCost(0);
        Set<Node> settledNodes = new HashSet<>();
        heap.add(startNode);
        while (settledNodes.size() != nodes.size()) {
            if (heap.isEmpty()) {
                return;
            }
            Node minDistanceNode = heap.poll();
            if (settledNodes.contains(minDistanceNode))
                continue;
            settledNodes.add(minDistanceNode);
            for (Entry<Node, Integer> neighborWeightPair : minDistanceNode.getNeighbors().entrySet()) {
                Node neighborNode = neighborWeightPair.getKey();
                Integer weight = neighborWeightPair.getValue();
                if (!settledNodes.contains(neighborNode)) {
                    int costOfMinDistanceNode = minDistanceNode.getCost();
                    if (costOfMinDistanceNode + weight < neighborNode.getCost()) {
                        neighborNode.setCost(costOfMinDistanceNode + weight);

                        LinkedList<Node> shortestPath = new LinkedList<>(startNode.getShortestPath());
                        shortestPath.add(startNode);
                        neighborNode.setShortestPath(shortestPath);
                    }
                    heap.add(neighborNode);
                }
            }
        }
    }

    public int computeFlagShortestPath(Node finishNode) {
        //Dijkstra again for finding flag shortest path.
        int totalCost = 0;
        finishNode.setDistance(0);
        PriorityQueue<Node> queue = new PriorityQueue<>(new Comparator<Node>()
                {
                    //Not to reset the costs again, i initialized another parameter as distance and compare them.
                    @Override
                    public int compare(Node node1, Node node2) {
                        return Integer.compare(node1.getDistance(), node2.getDistance());
                    }
                }
        );
        queue.add(finishNode);
        Set<Node> visitedNodes = new HashSet<>();
        while (!queue.isEmpty()) {
            Node current = queue.poll();
            if (flags.containsKey(current.getName())) {
                //if flag is contained and this flag is visited; we can visit it again with no cost.
                visitedNodes.add(current);
                totalCost = totalCost + current.getDistance();
                current.setDistance(0);
            }
            if (visitedNodes.size() == flags.size()) {
                break;
            }
            if(current.getNeighbors().isEmpty() && visitedNodes.size() != flags.size()){
                totalCost = -1;
            }
            for (Entry<Node, Integer> neighborWeightPair : current.getNeighbors().entrySet()) {
                Node neighborNode = neighborWeightPair.getKey();
                Integer weight = neighborWeightPair.getValue();

                int newDistance = current.getDistance() + weight;
                if (newDistance < neighborNode.getDistance()) {
                    neighborNode.setDistance(newDistance);
                    neighborNode.previous = current;
                    if (!visitedNodes.contains(neighborNode)) {
                        if(queue.contains(neighborNode)){
                            queue.remove(neighborNode);
                            queue.add(neighborNode);
                        }
                        else{
                            queue.add(neighborNode);
                        }
                    }

                }
            }
        }
        return totalCost;
    }
}
