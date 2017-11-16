package javathreads.example.ch02;

public class CharacterEvent {

    public CharacterSource characterSource;
    public int character;

    public CharacterEvent(CharacterSource characterSource, int character) {
        this.character = character;
        this.characterSource = characterSource;
    }
}
