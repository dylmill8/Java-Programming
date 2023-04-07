public class Coin implements Money {
    //value of coin in dollars (Ex: 0.01 for penny)
    private double value;
    //face on heads side of coin
    private String face;

    //sets value and face instance variables to parameters passed in as input
    public Coin(double value, String face) {
        this.value = value;
        this.face = face;
    }

    //returns value instance variable
    public double getValue() {
        return value;
    }

    //returns face instance variable
    public String getFace() {
        return face;
    }

    /* determine how many objects of coin/dollar bill
     * passed in as input are required to reach or exceed
     * value of the given coin
     * Example: if convert is called on a dime with a penny passed
     * in as input, return value will be 10 because there are 10
     * pennies in a dime.
     * Note: Return -1 if the value of the money object passed in is greater
     * than the value of the money object that the method is called on.
     */
    public int convert(Money money) {
        if (money.getValue() > this.value) {
            return -1;
        } else {
            double val = this.value / money.getValue();
            val = Math.round(val);
            int ret = (int)val;
            if (ret != val) {
                ret++;
            }
            return ret;
        }
    }

    /* determine if vending machines accept this type of coin or not
     * Note: generally all types of coins and $1 and $5 bills are
     * accepted by vending machine. Other dollar bills are not accepted.
     */
    public boolean acceptedVending() {
        return true;
    }
}