# Usage Instructions

## Compilation
```bash
javac CustomerCart.java Market.java Product.java UserInterface.java
```

## Executing the program
```bash
java Market
```

# Submission

# Class descriptions

* Product
    * Groups together all the data that a product contains into one object
* CustomerCart
    * Abstracts a cart of Products that a user can have
    * Also abstracts reading and writing from a file so the cart can persist sessions
* UserInterface
    * A collection of helper methods that we use in our main method
* Market
    * Holds the main entry point for our program

## Testing
The testing for a particular class X.java is in the corresponding test class TestX.java.

There is also a basic testing shellscript for a comprehensive integration test
