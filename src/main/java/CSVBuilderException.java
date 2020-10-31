public class CSVBuilderException {
    enum ExceptionType {
        IPL_FILE_PROBLEM,UNABLE_TO_PARSE
    }
    ExceptionType type;

    public CSVBuilderException(String message, ExceptionType type) {
        super();
        this.type = type;
    }
}
