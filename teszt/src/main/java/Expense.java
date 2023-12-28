import java.io.Serializable;

public class Expense implements Serializable {
    String name;    //kiadas neve
    Participant payedby;    //az a Participant akinek a kiadasa Expense
    double amount;          //az Expense erteke
    //konstruktor
    public Expense(Participant payedby,String name,double amount){
        this.name=name;
        this.payedby=payedby;
        this.amount=amount;
    }
}
