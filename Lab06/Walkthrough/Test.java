public class Test {
    public static void main(String[] args) {
        User user = new User("Purdue Pete", 81, "purduePete","purdue123!");
        System.out.println("Name: " + user.getName());
        System.out.println("Age: " + user.getAge());
        System.out.println("Username: " + user.getUsername());
        System.out.println("Password: " + user.getPassword());
        user.setName("Rowdy");
        user.setAge(18);
        user.setUsername("purdueRowdy");
        user.setPassword("456?boilerexpress");
        System.out.println("New Name: " + user.getName());
        System.out.println("New Age: " + user.getAge());
        System.out.println("New Username: " + user.getUsername());
        System.out.println("New Password: " + user.getPassword());
    }
}
