package by.karpovich.shop.exception;

public class IncorrectAmount extends RuntimeException {

    public IncorrectAmount() {
    }

    public IncorrectAmount(String message) {
        super(message);
    }
}
