package by.karpovich.shop.exception;

public class NotValidOrganization extends RuntimeException {

    public NotValidOrganization() {
    }

    public NotValidOrganization(String message) {
        super(message);
    }
}
