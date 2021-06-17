package xenoteo.com.github;

import lombok.Getter;
import lombok.Setter;

/**
 * The class representing the word and its number of occurrences in brewery names in a certain request.
 */
@Getter
@Setter
public class Word {
    String word;
    int frequency;

    public Word(String word, int frequency) {
        this.word = word;
        this.frequency = frequency;
    }
}
