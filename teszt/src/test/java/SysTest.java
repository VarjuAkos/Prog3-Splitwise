import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SysTest {

    @Test
    void addEvent() {
        Sys data=new Sys();
        data.addEvent("buli","2022.11.26");
        data.addEvent("buli","2022.11.26");
        assertEquals(1,data.events.size());
        data.addEvent("kocsma","2022.11.20");
        assertEquals(2,data.events.size());
        assertEquals("buli",data.events.get(0).name);
        assertEquals("kocsma",data.events.get(1).name);
    }

    @Test
    void delEvent() {
        Sys data=new Sys();
        Event buli=new Event("buli","2022.11.26");
        data.events.add(buli);
        data.delEvent(buli);
        assertEquals(0,data.events.size());
    }
    @Test
    void findEvent() {
        Sys data=new Sys();
        data.addEvent("buli","2022.11.26");
        assertEquals("buli",data.findEvent("buli").name);
    }
}