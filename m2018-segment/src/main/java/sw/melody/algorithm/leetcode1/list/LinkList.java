package sw.melody.algorithm.leetcode1.list;


/***
 * Created by ping on 2018/6/15
 * 给定一个链表，删除链表的倒数第 n 个节点，并且返回链表的头结点。
 *
 * 示例：
 *
 * 给定一个链表: 1->2->3->4->5, 和 n = 2.
 * 当删除了倒数第二个节点后，链表变为 1->2->3->5.
 *
 * 说明：
 * 给定的 n 保证是有效的。
 * 进阶：
 * 你能尝试使用一趟扫描实现吗？
 *
 */
public class LinkList {

    public static void main(String[] args) {
        ListNode head = new ListNode(1);
        ListNode next = new ListNode(2);
        head.next = next;
        removeNthFromEnd(head, 1);
    }

    public static ListNode removeNthFromEnd(ListNode head, int n) {
        if (n<=0) {
            return null;
        }
        int len=0;
        ListNode next = head;
        while (next != null) {
            len++;
            next = next.next;
        }
        if (n>len) {
            return null;
        }
        ListNode nt = head;
        int idx = 0;
        while (nt != null) {
            if (idx+n+1==len) {
                if (nt.next != null) {
                    nt.val = nt.next.val;
                    nt.next = nt.next.next;
                } else {
                    nt = null;
                }
            }
            nt = nt.next;
            idx++;
        }

        return head;
    }
}
