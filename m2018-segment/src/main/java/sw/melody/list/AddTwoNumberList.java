package sw.melody.list;

/***
 *  create by wggy on 2019/6/18 0:01 
 **/
public class AddTwoNumberList {

    public static void main(String[] args) {
        ListNode p = new ListNode(2);
        p.next = new ListNode(4);
        p.next.next = new ListNode(3);

        ListNode q = new ListNode(5);
        q.next = new ListNode(6);
        q.next.next = new ListNode(4);

        ListNode newListNode = addTwoNumbers(p, q);
        while (newListNode != null) {
            System.out.print(newListNode.val + " ");
            newListNode = newListNode.next;
        }
    }

    static class ListNode {
        int val;
        ListNode next;
        public ListNode(int val) {
            this.val = val;
        }
    }

    public static ListNode addTwoNumbers(ListNode l1, ListNode l2) {
        ListNode temp = new ListNode(0);
        ListNode p = l1;
        ListNode q = l2;
        ListNode currNode = temp;
        int carry = 0;
        while (p != null || q != null) {
            int pVal = p == null ? 0 : p.val;
            int qVal = q == null ? 0 : q.val;
            int currVal = carry + pVal + qVal;
            carry = currVal / 10;
            currNode.next = new ListNode(currVal % 10);
            if (p != null)
                p = p.next;
            if (q != null)
                q = q.next;
            currNode = currNode.next;
        }
        if (carry > 0)
            currNode.next = new ListNode(carry);
        return temp.next;
    }
}
