import java.io.Serializable;
import java.util.ArrayList;
import java.lang.Double;

public class Event implements Serializable {
    String name;    //event neve
    String date;    //datum
    ArrayList<Participant> participants;    //részvevők
    ArrayList<Transaction> transactions;    //vegeredmeny ide lesz kiiratva
    double buget;   //keret
    double average; //atlag

    //sima konstruktor
    public Event(String n,String d){
        this.name=n;
        this.date=d;
        participants=new ArrayList<>();
        transactions =new ArrayList<>();
        this.buget=0;
        this.average=0;
    }

    //parameterkent kapott nevvel letrehoz egy uj resztvevot es hozza adja az Eventhez: ha EGYEDI a nev
    //ha nem egyedi hamissal ter vissza
    public boolean addParticipant(String name){
        int szamlalo=this.participants.size();
        for (Participant participant : participants)
            if (!participant.name.equals(name))
                szamlalo--;
        //csak akkor 0 a szamlalo ha a name egyedi
        //ebben az esetben letrehozza es hozzaadja az uj Participant
        //es igazzal tér vissza erre a WindowFrame-be van szukseg
        //ha igazzal ter vissza frissiti azt JComboBoxot ami a Participantokat tartalmazza
        if(szamlalo==0){
            participants.add(new Participant(name,this));
            this.update();
            return true;
        }
        else
            return false;
    }


    //Parameterkent kapott nev alapjan toroli az adott Participant-t
    public void delParticipant(String name){
        this.participants.remove(this.findP(name));
        this.update();
    }


    //budget es az average attributumokat ujra szamolja
    //ha add/del Participant ujra kell szamolni az average-t
    //ga add/del Expense ujra kell szamolni a budget-et
    public void update(){
        buget=0;
        for(int i=0;i<participants.size();i++){
            for(int j=0;j<participants.get(i).expenses.size();j++){
                if(participants.get(i).expenses.size()!=0)
                    buget=buget+participants.get(i).expenses.get(j).amount;
            }
        }
        average=buget/this.participants.size();
        for(int i=0;i<participants.size();i++){
            participants.get(i).updateP();
        }
    }


    //resztvevok adatai es a kiadosak adataibol elosztja a fizetendo penzt
    //a vegeredmenyt a Tranzaction listába irja
    //dokumentacioban reszletesebben
    public void szamol(){
        for(int i=0;i<participants.size();i++){ //ciklus végig megy az összes részvevőn
            if(participants.get(i).kulonbseg<0){    //tartoznak neki
                for(int j=0; j<participants.size();j++){
                    if(participants.get(i)!=participants.get(j)){   //ha nem ugyan az a i mint a j
                        if(participants.get(j).kulonbseg>0 && participants.get(i).balance!=average){ //tarzozik
                            double keresztmetszet;//belemegy ha j-nek van mit kifizetnie
                                                                    //i.k >0 és j.k<0
                            int ij=Double.compare(participants.get(i).kulonbseg,-participants.get(j).kulonbseg);//j.k-->-j.k igy az is pozitív
                            //megvizsgálja melyiknek nagyobb a k-ja
                            //ha ij=0 egyenlő
                            if(ij==0){
                                keresztmetszet=participants.get(j).kulonbseg;
                                Transaction t0=new Transaction(participants.get(j),participants.get(i),keresztmetszet); //letrehozza a tranzakciot
                                this.transactions.add(t0);
                                participants.get(i).balance=participants.get(i).balance-keresztmetszet;
                                participants.get(j).balance=participants.get(j).balance+keresztmetszet;
                                participants.get(i).kulonbseg=0;
                                participants.get(j).kulonbseg=0;
                            }
                            if(ij>0){ //i.k nagyobb még tartoznak neki
                                        //a szűk keresztmetszet = j.k
                                keresztmetszet=-participants.get(i).kulonbseg;
                                Transaction t1=new Transaction(participants.get(j),participants.get(i),keresztmetszet); //letrehozza a tranzakciot
                                participants.get(j).kulonbseg=participants.get(j).kulonbseg-keresztmetszet; //participants.get(j).kulonbseg+keresztmetszet;     //átállítja a j különbségét
                                participants.get(i).kulonbseg=0;     //beallitja
                                this.transactions.add(t1);
                                participants.get(i).balance=participants.get(i).balance-keresztmetszet;
                                participants.get(j).balance=participants.get(j).balance+keresztmetszet;
                            }
                            if(ij<0){   //j.k nagyobb mint i.k
                                        //j.k még tartozik
                                        //i.k kiegyenlítve
                                keresztmetszet=participants.get(j).kulonbseg;
                                Transaction t2=new Transaction(participants.get(j),participants.get(i),keresztmetszet);
                                 //participants.get(j).kulonbseg+keresztmetszet;     //átállítja a j különbségét
                                participants.get(i).kulonbseg=participants.get(i).kulonbseg+keresztmetszet;     //j.k negatív , keresztmetszet pozitív + kell
                                participants.get(j).kulonbseg=0;
                                this.transactions.add(t2);
                                participants.get(i).balance=participants.get(i).balance-keresztmetszet;
                                participants.get(j).balance=participants.get(j).balance+keresztmetszet;
                            }
                            //keresztmetszet;     //tfh megvan a keresztmetszet
                        }
                    }
                }
            }
        }
    }
    //Visszaad egy String tombot mellyel erteket adok Participantokat tarolo ComboBoxnak
    public String[] Pnames(){
        String[] nevek = new String[this.participants.size()];
        for(int i=0;i<this.participants.size();i++){
            nevek[i]=this.participants.get(i).name;
        }
        return nevek;
    }
    //Visszaad egy String tombot mellyel erteket adok a Expenseket tarolo ComboBoxnak
    public String[] Expnames(){
        int dbevent=0;
        for(int i=0;i<this.participants.size();i++){
            for(int j=0;j<this.participants.get(i).expenses.size();j++)
                dbevent++;
        }
        //expenseBox.addItem(ez.name+" "+expenseName.getText()+" "+expenseValue.getText());
        int index=0;
        String[] eventnames=new String[dbevent];
        for(int i=0;i<this.participants.size();i++){
            for(int j=0;j<this.participants.get(i).expenses.size();j++)
                eventnames[index++]=this.participants.get(i).name+" "+this.participants.get(i).expenses.get(j).name+" "+this.participants.get(i).expenses.get(j).amount;
        }
        return eventnames;
    }
    //Participant neve egyedi
    //Nev alapjan keres es visszaadja a keresendot
    public Participant findP(String name){
        for (Participant participant : participants) {
            if (participant.name.equals(name))
                return participant;
        }
        return null;
    }


    //Teszteles miatt irtam
    //nem akartam kitorolni fontos volt a mukodes szempontjabol
    /*
    public void open(){
        boolean exit = false;
        Scanner scanner = new Scanner(System.in);
        while (!exit) {
            String[] cmd = scanner.nextLine().split(" ");
            switch (cmd[0]) {
                case "list" -> {       //kilistázza a résztvevőket és a kiadásokat
                    this.list();
                }
                case "addP" ->{        //résztvevőt ad hozza
                    this.addParticipant(cmd[1]);
                }
                case "delP" ->{
                    this.delParticipant(cmd[1]);
                }
                case "addEx" ->{
                    findP(cmd[1]).addExpense(cmd[2],cmd[3]);
                    update();
                }
                case "delEx" ->{
                    delEx(cmd[1]);
                    update();
                }
                case "calc" ->{
                    this.tranzactions.clear();
                    this.update();
                    this.szamol();
                    this.result();

                }
                case "help" ->{
                    System.out.println(
                            """
                                    list                         lists everything in the current event
                                    addP <nameofParticipant>     add Participant to the current event.
                                    delP <nameofParticipant>     del Participant from the current event.
                                    addEx <P.name~paidby> <nameofExpense> <amount>   add Expense
                                    delEx <nameofExpense>           delete Expense
                                    calc                            calc the results
                                    help
                                    exit
                                    """
                    );
                }
                case "exit"->{
                    exit=true;
                }
            }
        }
    }
    //nev alapjan torol egy kiadast
    public void delEx(String name){
        for(int i=0;i<this.participants.size();i++){
            for(int j=0;j<this.participants.get(i).expenses.size();j++){
                if(participants.get(i).expenses.get(j).name.equals(name))
                    participants.get(i).delExpense(participants.get(i).expenses.get(j));
            }
        }
    }
    //kiirja az eredmenyeket
    public void result(){
        for(int i=0;i<tranzactions.size();i++){
            System.out.println(tranzactions.get(i).paid_dby.name+" ---> "+tranzactions.get(i).paid_to.name+" osszeg :"+tranzactions.get(i).amount);
        }
    }
    //kilistazza az adatokat
    public void list(){
        System.out.println("Participants:");
        for (Participant participant : participants) System.out.println(participant.name);
        System.out.println("\n");
        for(Participant participant: participants){
            for(int i=0;i<participant.expenses.size();i++)
                System.out.println("Paid by:"+participant.expenses.get(i).payedby.name+"\tExpense name:"+participant.expenses.get(i).name+"\t\tAmount:"+participant.expenses.get(i).amount);
        }

    }
    */
}
