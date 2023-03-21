package by.karpovich.shop.exception;

public class IncorrectAmountException extends RuntimeException {

    public IncorrectAmountException() {
    }

    public IncorrectAmountException(String message) {
        super(message);
    }
}
