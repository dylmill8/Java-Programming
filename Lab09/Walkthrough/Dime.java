public class Dime extends Coin {
    public Dime() {
        //Call coin constructor with specified parameters
        super(0.1, "Franklin D. Roosevelt");
    }

    @Override
    /* Override acceptedVending method in coin class to
     * return true because vending machines accept nickels
     */
    public boolean acceptedVending() {
        return true;
    }
}
