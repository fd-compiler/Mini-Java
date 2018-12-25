package ErrorDetection;

public class UndefinedIdException extends Exception {
    String message;
    public UndefinedIdException(String e){
        message=e;
    }
    @Override
    public String getMessage(){
        return message;
    }
}
