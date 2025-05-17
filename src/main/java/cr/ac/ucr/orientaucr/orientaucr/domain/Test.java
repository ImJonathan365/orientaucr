package cr.ac.ucr.orientaucr.orientaucr.domain;

import java.util.LinkedList;
import java.util.List;

public class Test {
    
    private String question_id;
    private String question_text;
    private LinkedList<Characteristic> characteristics;

    public Test() {}

    public Test(String question_id, String question_text, LinkedList<Characteristic> characteristics) {
        this.question_id = question_id;
        this.question_text = question_text;
        this.characteristics = characteristics;
    }

    public String getQuestion_id() {
        return question_id;
    }

    public void setQuestion_id(String question_id) {
        this.question_id = question_id;
    }

    public String getQuestion_text() {
        return question_text;
    }

    public void setQuestion_text(String question_text) {
        this.question_text = question_text;
    }

    public LinkedList<Characteristic> getCharacteristics() {
        return characteristics;
    }

    public void setCharacteristics(LinkedList<Characteristic> characteristics) {
        this.characteristics = characteristics;
    }
    
}
