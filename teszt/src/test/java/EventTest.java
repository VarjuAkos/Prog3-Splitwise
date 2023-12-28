import static org.junit.jupiter.api.Assertions.*;

class EventTest {
    @org.junit.jupiter.api.Test
    void Event(){
        Event event=new Event("TName","T2022");
        assertEquals("TName",event.name);
        assertEquals("T2022",event.date);

    }

    @org.junit.jupiter.api.Test
    void addParticipant() {
        Event event=new Event("TName","T2022");
        event.addParticipant("laci");
        event.addParticipant("peter");
        event.addParticipant("laci");
        assertEquals("laci",event.participants.get(0).name);
        assertEquals("peter",event.participants.get(1).name);
        assertEquals(0,event.transactions.size());
        assertEquals(2, event.participants.size());

    }

    @org.junit.jupiter.api.Test
    void delParticipant() {
        Event event=new Event("TName","T2022");
        event.addParticipant("laci");
        event.delParticipant("laci");
        assertEquals(0, event.participants.size());
    }

    @org.junit.jupiter.api.Test
    void update() {
        Event event=new Event("TName","T2022");
        event.addParticipant("laci");
        event.participants.get(0).addExpense("pizza","1000");
        assertEquals(0,event.buget);
        event.update();
        assertEquals(1000,event.buget);


        event.addParticipant("peter");
        event.participants.get(1).addExpense("pia","1000");
        event.update();
        assertEquals(2000,event.buget);
        assertEquals(1000,event.average);
    }

    @org.junit.jupiter.api.Test
    void szamol() {
        Event event=new Event("TName","T2022");
        event.addParticipant("laci");
        event.addParticipant("akos");
        event.addParticipant("peter");
        event.participants.get(0).addExpense("pizza","1000");
        event.participants.get(1).addExpense("pia","8000");
        event.update();
        event.szamol();

        assertEquals(2,event.transactions.size());
        assertEquals(2000,event.transactions.get(0).amount);
        assertEquals("laci",event.transactions.get(0).paid_dby.name);


    }

    @org.junit.jupiter.api.Test
    void pnames() {
        Event event=new Event("TName","T2022");
        event.addParticipant("laci");
        event.addParticipant("akos");
        event.addParticipant("peter");
        String[] nevek =event.Pnames();
        assertEquals("laci",nevek[0]);
        assertEquals("akos",nevek[1]);
        assertEquals("peter",nevek[2]);

    }

    @org.junit.jupiter.api.Test
    void expnames() {
        Event event=new Event("TName","T2022");
        event.addParticipant("laci");
        event.participants.get(0).addExpense("pizza","1000");
        event.participants.get(0).addExpense("pia","1000");
        String[] nevek=event.Expnames();
        assertEquals(event.participants.get(0).expenses.get(0).payedby.name+" "+event.participants.get(0).expenses.get(0).name+" "+event.participants.get(0).expenses.get(0).amount,nevek[0]);

    }

    @org.junit.jupiter.api.Test
    void findP() {
        Event event=new Event("TName","T2022");
        event.addParticipant("laci");
        assertEquals("laci",event.findP("laci").name);
    }
}