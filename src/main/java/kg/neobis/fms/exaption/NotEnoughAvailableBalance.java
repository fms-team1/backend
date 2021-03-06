package kg.neobis.fms.exaption;

public class NotEnoughAvailableBalance extends Exception{
    public NotEnoughAvailableBalance(String message){
        super(message);
    }
}
