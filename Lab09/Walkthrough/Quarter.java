public class Quarter extends Coin {

    public Quarter() {
        //Call coin constructor with specified parameters
        super(0.25, "George Washington");
    }

    @Override
    /* Override acceptedVending method in coin class to
     * return true because vending machines accept nickels
     */
    public boolean acceptedVending() {
        return true;
    }
}
