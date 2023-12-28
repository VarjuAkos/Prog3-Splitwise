import java.io.*;
import java.util.ArrayList;


public class Sys implements Serializable {
    ArrayList<Event> events;        //kiadasok listaja
    //konstruktor
    public Sys() {
        events = new ArrayList<>();
    }


    //A name egyertelmuen azonosit egy Eventet
    //Parameterkent kapott String egyediseget vizsgalja
    //ha az megorzi az egyediseget hozzad egy uj Esemenyt
    //ha nem akkor nem hoz letre uj Esemenyt
    public boolean addEvent(String name,String date) {
        int szamlalo=this.events.size();
        for(int i=0;i<events.size();i++){
            if(!events.get(i).name.equals(name))
                szamlalo--;
        }
        if(szamlalo==0) {
            events.add(new Event(name, date));
            return true;
        }
        else
            return false;
    }
    //Parameterkent kapott Esemenyt torli
    public void delEvent(Event e) {
        events.remove(e);
    }

    //fajl kiiras
    //Datas.txt fajlbol
    void saveSys() throws RuntimeException {
        try {
            FileOutputStream out = new FileOutputStream("Data.txt");
            ObjectOutputStream outputStream = new ObjectOutputStream(out);
            outputStream.writeObject(events);
            outputStream.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    //fajl beolvasas
    //Datas.txt fajlbol
    void loadSys() throws RuntimeException {
        try {
            FileInputStream in = new FileInputStream("Data.txt");
            ObjectInputStream inputStream = new ObjectInputStream(in);
            events = (ArrayList<Event>) inputStream.readObject();
            inputStream.close();
            in.close();
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
    //A nev egyertelmuen azonosit egy Esemenyt
    //Visszadja a keresendo Eventet
    public Event findEvent(String name){
        for(int i=0;i<this.events.size();i++){
            if(this.events.get(i).name.equals(name))
                return this.events.get(i);
        }
        return null;
    }


/*
    //teszteles miatt irtam
    void start() {
        boolean exit = false;
        Scanner scanner = new Scanner(System.in);
        while (!exit) {
            String[] cmd = scanner.nextLine().split(" ");
            switch (cmd[0]) {
                case "list" -> {       //kilistázza az eventeket
                    this.listevents();
                }
                case "addEvent" -> {          //eventet ad hozza
                 this.addEvent(cmd[1],cmd[2]);
                }
                case "delEvent" ->{           //event torol
                    this.delEvent(this.getEvent(cmd[1]));
                }

                case "cd" -> {              //belemegy az eventbe cmd [1] nevű
                    //this.getEvent(cmd[1]).open();
                    System.out.println("Sikeresen visszatert a főmenübe");
                }
                case "save" -> {            //ment
                    this.saveSys();
                }
                case "load" -> {            //betolt
                    this.loadSys();
                }
                case "help" ->{
                    System.out.println("""
                        list                    lists the events
                        addEvent <name> <date>  add Event
                        delEvent <name>         delete Event
                        cd <Eventname>          get inside of Event
                        save                    save data
                        load                    load data
                        help
                        exit   
                        """
                    );
                }
                case "exit" ->{             //kilép
                    exit = true;
                }
            }
        }
    }
    public void listevents(){
        for (Event event : events) {
            System.out.println(event.name + " " + event.date);
        }
    }

 */
}