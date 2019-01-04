package ErrorDetection;

public class ClassRegisterException extends Exception{
    String message;
    public ClassRegisterException(String e){
        message=e;
    }
    @Override
    public String getMessage(){
        return message;
    }
}
