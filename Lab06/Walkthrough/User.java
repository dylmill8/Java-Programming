public class User {

    //class variables
    private String name;
    private int age;
    private String username;
    private String password;

    public User(String name, int age, String username, String password) { //constructor
        this.name = name;
        this.age = age;
        this.username = username;
        this.password = password;
    }

    //accessor/getter methods
    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    //mutator/setter methods
    public void setName(String name) {
        this.name = name;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
