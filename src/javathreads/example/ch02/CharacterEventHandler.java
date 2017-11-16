package javathreads.example.ch02;

import java.util.Vector;

public class CharacterEventHandler {
    private Vector<CharacterListener> listeners = new Vector();

    public void addCharacterListener(CharacterListener cl) {

        listeners.add(cl);
    }

    public void removeCharacterListener(CharacterListener cl) {

        listeners.remove(cl);
    }

    public void fireNewCharacter(CharacterSource cs, int c) {

        CharacterEvent characterEvent = new CharacterEvent(cs, c);
        //存在线程安全问题，直接使用vector的iteror迭代器时快速失败的，enumeration不是快速失败的

        CharacterListener[] array = listeners.toArray(new CharacterListener[0]);
        for (int i  = 0; i < array.length; ++i) {
            array[i].newCharacter(characterEvent);
        }
    }
}
