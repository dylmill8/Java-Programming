public class Monferno extends Pokemon {

    private String nickname;

    public Monferno(String nickname) {

        if (!nickname.isBlank()) {

            this.nickname = nickname;

        } else {

            this.nickname = "Monferno";

        }

    }

    @Override

    public Pokemon evolve() {

        return new Infernape(nickname);

    }

    @Override
    public String toString() {

        return "Monferno[" + nickname + "]";

    }

}
