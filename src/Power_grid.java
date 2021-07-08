import java.util.*;

// 有一堆node，有些node之间有链接，有些没有。每一个链接都有一个cost。现在任务是找出一个方案，把所有node都连接在一起，并且cost最小。
// 我们要做的就是需要在所有链接中选出一部分，来联通所有node，重点是尽可能选出cost最少的链接。有个图可以看～
// 链接都是双向的，并且题目必定会一个有效的方案。

// 我们首先需要给所有的链接按照cost排个序，然后使用union find算法，每一次取出当前cost最小的链接，检查我们是否需要它。
// 如果当前链接联通了两个不同的group，那我们就需要它（因为能起到相同作用的链接cost都比这个大），反之则不需要（以为我们之前一定遇到过比当前链接更好的）。

public class Power_grid {

    static class Connection{
        char from, to;
        int cost;
        public Connection(char from, char to, int cost){
            this.from = from;
            this.to = to;
            this.cost = cost;
        }

        @Override
        public String toString() {
            return "{" + from + "-->" + to + " " + cost + '}';
        }
    }

    public static void main(String[] args) {

        // build the graph
        LinkedList<Connection> connections = new LinkedList<>();
        connections.add(new Connection('A', 'B', 1));
        connections.add(new Connection('B', 'C', 4));
        connections.add(new Connection('B', 'D', 6));
        connections.add(new Connection('D', 'E', 5));
        connections.add(new Connection('C', 'E', 1));
        connections.add(new Connection('A', 'C', 8));

        // connections is a LinkedList and that is the only input parameter.
        // now it's time to solve the problem:

        // put all the connections into a customized priority queue(heap)
        PriorityQueue<Connection> pq = new PriorityQueue<>(new Comparator<>() {
            public int compare(Connection o1, Connection o2){
                return o1.cost - o2.cost;
            };
        });
        pq.addAll(connections);


        // Union find
        HashMap<Character, Character> leaderMap = new HashMap<>(); // a map helps you find the leader of each group
        HashMap<Character, ArrayList<Character>> groups = new HashMap<>(); // a map helps find all member of a group
        // traverse all the connections, prepare for union find
        for (Connection c : connections) {
            char from = c.from;
            char to = c.to;
            if (!leaderMap.containsKey(from)) {
                leaderMap.put(from, from);
            }
            if (!leaderMap.containsKey(to)) {
                leaderMap.put(to, to);
            }
            if (!groups.containsKey(from)) {
                ArrayList<Character> list = new ArrayList<>();
                list.add(from);
                groups.put(from, list);
            }
            if (!groups.containsKey(to)) {
                ArrayList<Character> list = new ArrayList<>();
                list.add(to);
                groups.put(to, list);
            }
        }
//    System.out.println(leaderMap);
//    System.out.println(groups);

        // search--------------
        ArrayList<Connection> result = new ArrayList<>();
        // At this time, all the connections are sorted, and we can traverse them from the lowest cost to highest cost.
        while (!pq.isEmpty()) {
            Connection c = pq.poll();
            char v1 = c.from;
            char v2 = c.to;

            char leader1 = leaderMap.get(v1);
            char leader2 = leaderMap.get(v2);

            if (leader1 == leader2) {
                // some group means they are already connected
                continue;
            } else {
                // otherwise, merge the groups
                result.add(c);
                ArrayList<Character> g1 = groups.get(leader1);
                ArrayList<Character> g2 = groups.get(leader2);

                for (char node : g2) {
                    leaderMap.put(node, leader1);
                    g1.addAll(g2);
                }
            }
        }
        // At this time point, there should be only 1 group left. That means all the nodes are connected.
        System.out.println(result);
    }
}
