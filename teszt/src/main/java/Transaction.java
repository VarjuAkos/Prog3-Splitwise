import java.io.Serializable;

public class Transaction implements Serializable{       //eredmenyekert felelo osztaly
    Participant paid_dby;       //Az a Participant aki penzt ad
    Participant paid_to;        //Az a Participant akinek penzt adnak
    double amount;              //osszeg

    //konstruktor
    public Transaction(Participant paid_dby, Participant paid_to, double amount){
        this.paid_dby=paid_dby;
        this.paid_to=paid_to;
        this.amount=amount;
    }
}
