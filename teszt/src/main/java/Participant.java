import java.io.Serializable;
import java.util.ArrayList;

public class Participant implements Serializable {
    String name;        //Resztvevo neve
    ArrayList<Expense> expenses;    //Resztvevo kiadasia
    double kulonbseg;   //esemeny kerete - atlag
                        //ha kulonbseg>0 tartoznak neki
                        //ha kulonbseg==0 kész
                        //ha kulonbseg<0 tartozik
    Event insideof;     //Az event aminek a resze
    double balance;     //az általa fizetett összeg


    //konstruktor
    public Participant(String n,Event insideof){

        this.name=n;
        this.expenses=new ArrayList<>();
        this.kulonbseg=insideof.average-balance;
        this.insideof=insideof;
    }


    //A Expense name egyediseget vizsgalja, a parameterkent kapott Stringgel
    //ha egyedi a kapott Stringgel hozzaadj egy uj Expenset aminek az erteket a parameterben kapott value
    //ha nem egyedi, nem adja hozza
    public boolean addExpense(String expensename, String value){
        double d=Double.parseDouble(value);
        int szamlalo=this.expenses.size();
        for(int i=0;i<this.expenses.size();i++)
            if(!this.expenses.get(i).name.equals(expensename))
                szamlalo--;
        if(szamlalo==0){
            this.expenses.add(new Expense(this,expensename,d));
            this.updateP();
            return true;
        }
        else
            return false;

    }

    //a parameterkent kapott Expenset torli
    public void delExpense(Expense ex){
        this.expenses.remove(ex);
    }


    //A resztvevok adatait frissiti
    public void updateP(){
        this.balance=0;     //le nzullazza
        for(int i=0;i<expenses.size();i++){
            this.balance=this.balance+expenses.get(i).amount;
        }
        this.kulonbseg=insideof.average-balance;
    }

    //Nev az egyertelmuen azonositja a kiadast
    //az alapjan keres es visszadaja a keresendot
    public Expense findExp(String name){
        for(int i=0;i<this.expenses.size();i++){
            if(this.expenses.get(i).name.equals(name))
                return this.expenses.get(i);
        }
        return null;
    }

}
