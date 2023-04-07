public class Nickel extends Coin {
    public Nickel() {
        //Call coin constructor with specified parameters
        super(0.05, "Thomas Jefferson");
    }

    @Override
    /* Override acceptedVending method in coin class to
     * return true because vending machines accept nickels
     */
    public boolean acceptedVending() {
        return true;
    }
}
