package ErrorDetection;

public class UndefinedIdException extends Exception {
    String message;
    public UndefinedIdException(String e){
        message="Undefined ID Exception: " + e;
    }
    @Override
    public String getMessage(){
        return message;
    }
}
