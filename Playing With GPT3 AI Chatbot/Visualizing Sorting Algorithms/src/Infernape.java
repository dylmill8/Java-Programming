public class Infernape extends Pokemon {

    private String nickname;

    public Infernape(String nickname) {

        if (!nickname.isBlank()) {

            this.nickname = nickname;

        } else {

            this.nickname = "Infernape";

        }

    }

    @Override

    public Pokemon evolve() {

        return null;

    }

    @Override

    public String toString() {

        return "Infernape[" + nickname + "]";

    }
}