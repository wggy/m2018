package sw.melody.list;

/***
 *  create by wggy on 2019/6/16 23:33
 *
 *  反转链表
 *
 **/
public class ReverseList {

    public static void main(String[] args) {
        Node a = new Node(1);
        Node b = new Node(2);
        Node c = new Node(3);
        Node d = new Node(4);
        Node e = new Node(5);
        a.next = b;
        b.next = c;
        c.next = d;
        d.next = e;
        Node temp = a;
        while (temp != null) {
            System.out.print(temp.val + " ");
            temp = temp.next;
        }
        Node head = reverse(a);
        System.out.println();
        System.out.println("==============================");
        Node t = head;
        while (t != null) {
            System.out.print(t.val + " ");
            t = t.next;
        }
    }

    static class Node {
        int val;
        Node next;

        public Node(int val) {
            this.val = val;
        }
    }

    static Node reverse(Node head) {
        Node next = null, pre = null;
        while (head != null) {
            next = head.next;
            head.next = pre;
            pre = head;
            head = next;
        }
        return pre;
    }

}
