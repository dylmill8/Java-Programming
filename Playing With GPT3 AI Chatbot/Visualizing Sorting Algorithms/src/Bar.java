public class Bar extends Foo {

    private int number;

    public Bar(int num) {

        super(num++);

        number = num;

    }

}
