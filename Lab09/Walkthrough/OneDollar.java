public class OneDollar extends DollarBill {
    public OneDollar() {
        super(1, "George Washington");
    }

    @Override
    public boolean acceptedVending() {
        return true;
    }
}
