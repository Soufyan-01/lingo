package nl.hu.cisq1.lingo.trainer.domain.exceptions;

public class InvalidFeedbackException extends RuntimeException {
    public InvalidFeedbackException(Integer lengthFeedback, Integer lengthAttempt) {
        super("length of the feedback does not correspondent with the lenght of the attempt " + lengthFeedback + " feedback "+ lengthAttempt + " attempt lenght.");
    }
}
