import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class project4 {
    public static void main(String[] args) {
        long start = System.currentTimeMillis();
        Graph computingGraph = new Graph();
        HashMap<String, String[]> costMap = new HashMap<>();
        BufferedReader br = null;
        try {
            br = new BufferedReader(
                    new FileReader(args[0]));

        } catch (FileNotFoundException e) {
            System.err.println("Exception caught: Input file not found.");
            System.exit(1);
        } catch (ArrayIndexOutOfBoundsException e) {
            System.err.println("Exception caught: Input file path must be provided.");
            System.exit(1);
        }

        try{
            //Read vertex Number
            int vertexNum = Integer.parseInt(br.readLine());
            //Read flag Number
            int flagNum = Integer.parseInt(br.readLine());
            //Read start-end nodes
            String[] startEndNode = br.readLine().split("\s");
            //Read Flag places
            String[] flagPlaces = br.readLine().split("\s");
            //create Cost map
            for(int i = 0; i < vertexNum; i++){
                String[] node_info = br.readLine().split("\s");
                Node toBeAddedNode = new Node(node_info[0]);
                costMap.put(toBeAddedNode.getName(), node_info);
                computingGraph.addNode(toBeAddedNode);
            }
            //addFlags
            for(int i = 0; i < flagNum; i++){
                computingGraph.addFlag(flagPlaces[i]);
            }
            //add costs.
            for(var key : costMap.keySet()){
                var node = computingGraph.getNodeByName(key);
                for(int j = 1; j < costMap.get(key).length - 1; j++){
                    var Neighbour = computingGraph.getNodeByName(costMap.get(key)[j]);
                    node.add(Neighbour, Integer.parseInt(costMap.get(key)[j+1]));
                    Neighbour.add(node,Integer.parseInt(costMap.get(key)[j+1]) );
                    j++;
                }
            }
            //Create Writer
            BufferedWriter writer = new BufferedWriter(new FileWriter(args[1]));
            //Compute Shortest Path
            computingGraph.computeShortestPath(computingGraph.getNodeByName(startEndNode[0]));
            //Write Shortest Path
            writer.write(String.valueOf((computingGraph.getNodeByName(startEndNode[1]).getCost() == Integer.MAX_VALUE) ? -1 : computingGraph.getNodeByName(startEndNode[1]).getCost()) + "\n");
            //Compute Shortest Path for flags and return total cost.
            writer.write( String.valueOf(computingGraph.computeFlagShortestPath(computingGraph.getNodeByName(flagPlaces[0]))));
            writer.close();
        }
        catch (IOException e) {
            throw new RuntimeException(e);
        }
        long elapsedTime = System.currentTimeMillis()-start;
        float elapsedTimeSec = elapsedTime/1000F;
        System.out.println(elapsedTimeSec);
    }
}
