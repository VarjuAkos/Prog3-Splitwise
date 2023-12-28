import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ParticipantTest {

    @Test
    void addExpense() {
        Event event=new Event("teszt","2022.10.01");
        event.addParticipant("laci");
        event.participants.get(0).addExpense("pizza","2000");
        assertEquals(1,event.participants.get(0).expenses.size());
    }

    @Test
    void delExpense() {
        Event event=new Event("teszt","2022.10.01");
        Participant a=new Participant("laci",event);
        event.participants.add(a);
        Expense p=new Expense(a,"pizza",2000);
        event.participants.get(0).expenses.add(p);
        event.participants.get(0).delExpense(p);
        assertEquals(0,event.participants.get(0).expenses.size());
    }

    @Test
    void updateP() {
        Event e=new Event("teszt","2022.10.01");
        e.addParticipant("laic");
        e.participants.get(0).addExpense("pizza","2000");
        e.participants.get(0).addExpense("sor","2000");
        e.participants.get(0).updateP();
        assertEquals(4000,e.participants.get(0).balance);

    }

    @Test
    void findExp() {
        Event e=new Event("teszt","2022.10.01");
        e.addParticipant("laic");
        e.participants.get(0).addExpense("pizza","2000");
        e.participants.get(0).addExpense("sor","2000");
        assertEquals("pizza",e.participants.get(0).findExp("pizza").name);
        assertEquals("sor",e.participants.get(0).findExp("sor").name);
    }
}