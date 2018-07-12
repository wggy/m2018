package sw.melody.algorithm.leetcode1.list;

/***
 * Created by ping on 2018-7-6
 */
public class ReverseList {
    public static void main(String[] args) {
        ListNode head = new ListNode(1);
        head.next = new ListNode(2);
        head.next.next = new ListNode(3);
        head.next.next.next = new ListNode(4);
        head.next.next.next.next = new ListNode(5);
        new ReverseList().reverserList2(head);
    }

    public ListNode reverserList3(ListNode head) {
        if (head == null || head.next == null) {
            return head;
        }
        ListNode p1 = head;
        ListNode p2 = p1.next;
        while (p2 != null) {
            ListNode temp = p2.next;
            p2.next = p1;
            p1 = p2;
            p2 = temp;
        }
        head.next = null;
        return p1;
    }


    /****
     * 此法为迭代处理，时间复杂度O(N^2)
     * @param head
     * @return
     */
    public ListNode reverserList2(ListNode head) {
        if (head == null) {
            return null;
        }
        ListNode forLast = head;

        while (forLast.next != null) {
            forLast = forLast.next;
        }
        ListNode temp = forLast;
        next(head, temp);
        return forLast;
    }

    private void next(ListNode head, ListNode last) {
        ListNode nextNode = forNext(head, last);
        if (nextNode == null) {
            return;
        }
        nextNode.next = null;
        last.next = nextNode;
        next(head, nextNode);
    }

    /***
     * 此方法，先遍历寻找链表尾节点，从该节点出发寻找他的上个节点，递推到最后一个节点，即head节点，完成反转。
     * 时间复杂度为O(N^2)
     * @param head
     * @return
     */
    public ListNode reverseList(ListNode head) {
        if (head == null) {
            return null;
        }
        ListNode forLast = head;

        while (forLast.next != null) {
            forLast = forLast.next;
        }
        ListNode temp = forLast;
        while (temp != null) {
            ListNode nextNode = forNext(head, temp);
            if (nextNode == null) {
                break;
            }
            nextNode.next = null;
            temp.next = nextNode;
            temp = nextNode;
        }
        return forLast;
    }

    private ListNode forNext(ListNode head, ListNode last) {
        ListNode temp = head;
        while (temp != null) {
            if (temp.next == last) {
                return temp;
            }
            temp = temp.next;
        }
        return null;
    }
}
