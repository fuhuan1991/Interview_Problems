import java.util.HashMap;

// 复制一个带有随机pointer的LinkedList。
// LinkedList中的每一个节点都有3个属性：next，val和random
// next指向下一个节点，val是一个整数，random就是随机指针，它可能指向任何一个链表中的节点，可以是自己，也可以是null。
// 输入参数只有一个，LinkedList 的 head

// 我们需要建立一个从旧链表到新链表的一对一映射。可以使用一个HashMap，key是旧链表的第n个节点，value就是新链表的第n个节点。

class Node {
    public int val;
    public Node next;
    public Node random;

    public Node() {}

    public Node(int _val, Node _next, Node _random) {
        val = _val;
        next = _next;
        random = _random;
    }
};

public class CopyListwithRandomPointer {
    // Visited dictionary to hold old node reference as "key" and new node reference as the "value"
    private static HashMap<Node, Node> visited = new HashMap<Node, Node>();

    // This function expects a node in the old LinkedList as the parameter and return a node in new LinkedList
    // The functions will return that node if it already exists, otherwise, it will create it and put it into the
    // directory.
    public static Node getCorrespondNode(Node node) {
        // If the node exists then
        if (node != null) {
            // Check if the node is in the visited dictionary
            if (visited.containsKey(node)) {
                // If its in the visited dictionary then return the new node reference from the dictionary
                return visited.get(node);
            } else {
                // Otherwise create a new node, add to the dictionary and return it
                visited.put(node, new Node(node.val, null, null));
                return visited.get(node);
            }
        }
        return null;
    }

    public static Node copyRandomList(Node head) {

        if (head == null) return null;

        Node oldNode = head;

        // Create a new head node.
        Node newNode = new Node(oldNode.val, null, null);
        visited.put(oldNode, newNode);

        // Iterate on the linked list until all nodes are cloned.
        while (oldNode != null) {
            // Get the clones of the nodes referenced by random and next pointers.
            newNode.random = getCorrespondNode(oldNode.random);
            newNode.next = getCorrespondNode(oldNode.next);

            // Move one step ahead in the linked list.
            oldNode = oldNode.next;
            newNode = newNode.next;
        }

        return visited.get(head);
    }

    // test
    public static void main(String[] args) {
        Node n1 = new Node(1, null, null);
        Node n2 = new Node(2, null, null);
        Node n3 = new Node(3, null, null);

        n1.next = n2;
        n2.next = n3;
        n1.random = n3;
        n2.random = null;
        n3.random = n3;

        Node new_n1 = copyRandomList(n1);
        Node new_n2 = new_n1.next;
        Node new_n3 = new_n2.next;
        System.out.println(n1.val);
        System.out.println(n2.val);
        System.out.println(n3.val);

        System.out.println(n1.random.val);
        System.out.println(n2.random);
        System.out.println(n3.random.val);
    }
}