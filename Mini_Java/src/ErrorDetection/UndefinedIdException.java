package ErrorDetection;

public class UndefinedIdException extends Exception {
    String message;
    public UndefinedIdException(String e){
        message="Cannot resolve symbol: " + e;
    }
    @Override
    public String getMessage(){
        return message;
    }
}
