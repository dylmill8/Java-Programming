public class Penny extends Coin {
    public Penny() {
        //Call coin constructor with specified parameters
        super(0.01, "Abraham Lincoln");
    }

    @Override
    /* Override acceptedVending method in coin class to
     * return true because vending machines accept nickels
     */
    public boolean acceptedVending() {
        return true;
    }
}
