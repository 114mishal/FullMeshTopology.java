import java.util.*;

class Workstation {
    String name;
    String ip;
    List<Workstation> connections;

    public Workstation(String name, String ip) {
        this.name = name;
        this.ip = ip;
        this.connections = new ArrayList<>();
    }

    @Override
    public String toString() {
        return "Name: " + name + ", IP: " + ip;
    }
}

class FullMeshGraph {
    List<Workstation> nodes;

    public FullMeshGraph() {
        nodes = new ArrayList<>();
    }

    public void addNode(String name, String ip) {
        for (Workstation node : nodes) {
            if (node.ip.equals(ip)) {
                System.out.println("IP address " + ip + " already exists. Please use a different IP.");
                return;
            }
        }

        Workstation newNode = new Workstation(name, ip);
        for (Workstation node : nodes) {
            if (!node.ip.equals(ip)) {
                node.connections.add(newNode);
                newNode.connections.add(node);
            }
        }
        nodes.add(newNode);
    }

    public void bfsRead(Workstation workstation) {
        Set<Workstation> visited = new HashSet<>();
        Queue<Workstation> queue = new LinkedList<>();
        Map<Workstation, Integer> levels = new HashMap<>();
        queue.add(workstation);
        visited.add(workstation);
        levels.put(workstation, 0);

        while (!queue.isEmpty()) {
            Workstation node = queue.poll();
            int level = levels.get(node);
            System.out.println("Node: " + node.name + ", IP: " + node.ip + ", Level: " + level);

            for (Workstation neighbor : node.connections) {
                if (!visited.contains(neighbor)) {
                    visited.add(neighbor);
                    queue.add(neighbor);
                    levels.put(neighbor, level + 1);
                }
            }
        }
    }
}

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        FullMeshGraph graph = new FullMeshGraph();

        while (true) {
            System.out.println("\n1. Add Node");
            System.out.println("2. Connection Test");
            System.out.println("3. Exit");

            System.out.print("Enter your choice: ");
            String choice = scanner.nextLine();

            if (choice.equals("1")) {
                System.out.print("Enter the name of the node: ");
                String name = scanner.nextLine();
                System.out.print("Enter the IP address of the node: ");
                String ip = scanner.nextLine();
                graph.addNode(name, ip);
            } else if (choice.equals("2")) {
                System.out.print("Enter the IP address of the node to test connection: ");
                String ip = scanner.nextLine();
                boolean found = false;
                for (Workstation node : graph.nodes) {
                    if (node.ip.equals(ip)) {
                        System.out.println("Performing connection test for node with IP " + ip + ":");
                        graph.bfsRead(node);
                        found = true;
                        break;
                    }
                }
                if (!found) {
                    System.out.println("No node with IP " + ip + " found.");
                }
            } else if (choice.equals("3")) {
                break;
            } else {
                System.out.println("Invalid choice. Please try again.");
            }
        }
    }
}
