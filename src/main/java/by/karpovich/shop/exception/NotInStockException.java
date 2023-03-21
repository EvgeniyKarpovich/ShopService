package by.karpovich.shop.exception;

public class NotInStockException extends RuntimeException {

    public NotInStockException() {
    }

    public NotInStockException(String message) {
        super(message);
    }
}
