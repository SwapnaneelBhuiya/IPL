public class IPLAnalyserException extends Exception {
    enum ExceptionType {
        CENSUS_FILE_PROBLEM, UNABLE_TO_PARSE, NO_CENSUS_DATA
    }
    public IPLAnalyserException(String message, String name) {
        super(message);
        this.type = ExceptionType.valueOf(name);
    }

    ExceptionType type;

    public IPLAnalyserException(String message, ExceptionType type) {
        super(message);
        this.type = type;
    }

    public IPLAnalyserException(String message, ExceptionType type, Throwable cause) {
        super(message, cause);
        this.type = type;
    }
}
