package ts.andrey.exception;

public class CoffeeServerException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public CoffeeServerException() {
        super();
    }

    public CoffeeServerException(String type, Integer id) {
        super(type + " с идентификатором " + id + " не найден");
    }

    public CoffeeServerException(String type, String findField) {
        super(type + " по параметру " + findField + " не найден");
    }

    public CoffeeServerException(String message) {
        super(message);
    }

    public CoffeeServerException(String message, Throwable cause) {
        super(message, cause);
    }

    public CoffeeServerException(Throwable cause) {
        super(cause);
    }

    protected CoffeeServerException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

}
