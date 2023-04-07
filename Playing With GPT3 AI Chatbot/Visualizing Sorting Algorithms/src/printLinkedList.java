import java.util.LinkedList;

public class printLinkedList {
    public static void main(String[] args) {

        LinkedList<Integer> sll = new LinkedList<>();

        sll.push(5);

        sll.push(7);

        sll.push(9);

        sll.push(10);

        sll.push(11);

        sll.push(15);

        sll.push(19);

        sll.pop();

        sll.pop();

        System.out.println(sll.peek());

        printLinkedList(sll);

    }

    private static void printLinkedList(LinkedList<Integer> list) {

        int length = list.size();

        for (int i = 0; i < length; i++) {

            System.out.print(list.get(i));

            if (i != length - 1) {

                System.out.print(" -> ");

            }

        }

    }
}