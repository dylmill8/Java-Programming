public class Chimchar extends Pokemon {

    private String nickname;

    public Chimchar(String nickname) {

        if (!nickname.isBlank()) {

            this.nickname = nickname;

        } else {

            this.nickname = "Chimchar";

        }

    }

    @Override

    public Pokemon evolve() {

        return new Monferno(nickname);

    }

    @Override

    public String toString() {

        return "Chimchar[" + nickname + "]";

    }

}
