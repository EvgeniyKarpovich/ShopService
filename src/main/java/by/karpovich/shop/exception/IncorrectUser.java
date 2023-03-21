package by.karpovich.shop.exception;

public class IncorrectUser extends RuntimeException {

    public IncorrectUser() {
    }

    public IncorrectUser(String message) {
        super(message);
    }
}
