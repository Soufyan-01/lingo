package nl.hu.cisq1.lingo.trainer.domain;

import lombok.Getter;
import lombok.Setter;
import nl.hu.cisq1.lingo.trainer.domain.exceptions.InvalidFeedbackException;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Getter
@Setter
public class Feedback {

    private String attempt;
    private List<Commentary> commentaries;

    public Feedback(String attempt, List<Commentary> commentaries) {
        if (attempt.length() != commentaries.size()) {
            throw new InvalidFeedbackException(attempt.length(), commentaries.size());
        }
        this.attempt = attempt;
        this.commentaries = commentaries;

    }


    public boolean isWordGuessed() {
        return commentaries.stream()
                .allMatch(commentaries -> commentaries == Commentary.CORRECT);
    }

    public boolean isWordInvalid() {
        long count = commentaries.stream()
                .filter(Commentary.INVALID::equals)
                .count();
        return count >= 1;
    } 

    public String giveHint(String wordToGuess, String previousHint) {
        if(wordToGuess.length() != commentaries.size()){
            throw new RuntimeException("Feedback has to be same length as word to guess");
        }

        String[] splitWord = wordToGuess.split("");
        String[] splitPrevHint = previousHint.split("");

        List<String> newHint = new ArrayList<>();

        for (int i = 0; i < splitWord.length; i++) {
            if (commentaries.get(i).equals(Commentary.CORRECT)) {
                newHint.add(splitWord[i]);
            } else {
                newHint.add(splitPrevHint[i]);
            }
        }

        return String.join("", newHint);
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Feedback feedback = (Feedback) o;
        return Objects.equals(attempt, feedback.attempt) &&
                Objects.equals(commentaries, feedback.commentaries);
    }

    @Override
    public int hashCode() {
        return Objects.hash(attempt, commentaries);
    }

    @Override
    public String toString() {
        return "Feedback{" +
                "attempt='" + attempt + '\'' +
                ", commentaries=" + commentaries +
                '}';
    }
}
