package nl.hu.cisq1.lingo.trainer.domain;

import nl.hu.cisq1.lingo.trainer.domain.exceptions.InvalidFeedbackException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.List;
import java.util.stream.Stream;

import static nl.hu.cisq1.lingo.trainer.domain.Commentary.*;
import static org.junit.jupiter.api.Assertions.*;

class FeedbackTest {

    @Test
    @DisplayName("word is guessed if all letters are correct")
    void wordIsGuessed() {
        Feedback feedback = new Feedback("woord", List.of(CORRECT, CORRECT, CORRECT, CORRECT, CORRECT));

        assertTrue(feedback.isWordGuessed());
    }

    @Test
    @DisplayName("word is not guessed if not all letters are correct")
    void wordIsNotGuessed() {
        Feedback feedback = new Feedback("woord", List.of(CORRECT, CORRECT, ABSENT, CORRECT, CORRECT));

        assertFalse(feedback.isWordGuessed());
    }

    @Test
    @DisplayName("word contains invalid letters")
    void guessIsInvalid() {
            Feedback feedback = new Feedback("woord", List.of(INVALID, INVALID, INVALID, INVALID, INVALID));
        assertTrue(feedback.isWordInvalid());
    }

    @Test
    @DisplayName("word does not contain invalid letters")
    void guessIsNotInvalid() {
        Feedback feedback = new Feedback("woord", List.of(CORRECT, CORRECT, ABSENT, CORRECT, CORRECT));

        assertFalse(feedback.isWordInvalid());
    }


    @Test
    @DisplayName("feedback and attempt length do not match")
    void InvalidFeedback() {
        assertThrows(
                InvalidFeedbackException.class,
                () -> new Feedback("woord", List.of(CORRECT))
        );
    }

    @Test
    @DisplayName("feedback length can not be longer than word to guess")
    void feedbackLength() {
        Feedback feedback = new Feedback("woord", List.of(CORRECT, CORRECT, CORRECT, CORRECT, CORRECT));
        String wordToGuess = "hoi";
        String prevHint = "h..";

        assertThrows(
                RuntimeException.class,
                () -> feedback.giveHint(wordToGuess, prevHint)
        );
    }

    @ParameterizedTest
    @MethodSource("provideHintExamples")
    @DisplayName("feedback based on guessed word")
    void giveHintTest(String wordToGuess, String attempt, String previousHint, List<Commentary> commentaries, String hint) {
        Feedback feedback = new Feedback(attempt, commentaries);
        String actualHint = feedback.giveHint(wordToGuess, previousHint);

        assertEquals(hint, actualHint);
    }

    static Stream<Arguments> provideHintExamples() {
        return Stream.of(
                Arguments.of("woord", "waard", "w....", List.of(CORRECT, ABSENT, ABSENT, CORRECT, CORRECT), "w..rd"),
                Arguments.of("woord", "waard", "wo...", List.of(CORRECT, ABSENT, ABSENT, CORRECT, CORRECT), "wo.rd"),
                Arguments.of("woord", "waard", "woo..", List.of(CORRECT, ABSENT, ABSENT, CORRECT, CORRECT), "woord"),
                Arguments.of("woord", "baken", "w....", List.of(ABSENT, ABSENT, ABSENT, ABSENT, ABSENT), "w....")
        );
    }

    @Test
    @DisplayName("same feedback is equal")
    public void testFeedbackEquals() {
        Feedback feedback = new Feedback("woord", List.of(CORRECT, CORRECT, CORRECT, CORRECT, CORRECT));
        Feedback feedback2 = new Feedback("woord", List.of(CORRECT, CORRECT, CORRECT, CORRECT, CORRECT));

        assertTrue(feedback.equals(feedback2));
    }

    @Test
    @DisplayName("different feedback is not equal")
    public void testFeedbackDoesntEquals() {
        Feedback feedback = new Feedback("woord", List.of(CORRECT, CORRECT, CORRECT, CORRECT, CORRECT));
        Feedback feedback2 = new Feedback("woord", List.of(CORRECT, ABSENT, CORRECT, CORRECT, CORRECT));

        assertFalse(feedback.equals(feedback2));
    }

    @Test
    @DisplayName("test the hashcode")
    public void testEquals_Symmetric() {
        Feedback feedback = new Feedback("woord", List.of(CORRECT, CORRECT, CORRECT, CORRECT, CORRECT));
        Feedback feedback2 = new Feedback("woord", List.of(CORRECT, CORRECT, CORRECT, CORRECT, CORRECT));

        assertEquals(feedback.hashCode(), feedback2.hashCode());
    }
}