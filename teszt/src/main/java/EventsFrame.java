import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
public class EventsFrame extends JFrame  {
    private final JTextField nameTextField,dateTextField;
    private final JComboBox comboBox;

    EventsFrame(Sys data){
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(600,160);
        setTitle("Events");
        setLayout(new GridLayout(1,2));
        ImageIcon icon=new ImageIcon("logo.png");
        this.setIconImage(icon.getImage());
        //idaig alap beallitasok

        //ket JPanel letrehozasa
        JPanel leftPanel = new JPanel(new GridLayout(4, 2));
        JPanel rightPanel = new JPanel(new GridLayout(4, 2));

        //komponensek letrehozasa
        nameTextField= new JTextField(20);
        dateTextField=new JTextField(20);
        comboBox = new JComboBox<>();
        JButton openEvent = new JButton("Open");
        JButton createEvent = new JButton("Create");
        JButton saveSystem = new JButton("Save");
        JButton delEvent = new JButton("Delete");
        JButton loadSystem = new JButton("Load");


        //Event megnyitasa
        //Open JButton
        ActionListener openAction= e -> {
            //itt megnyitja a masik JFramet = WindowFrame
            //a ComboBox erteket, azaz a kivalasztott Eventet kapja parameterkent
            JFrame opened=new WindowFrame(data.findEvent(comboBox.getSelectedItem().toString()));
            opened.setVisible(true);
        };
        openEvent.addActionListener(openAction);

        //Adatok kimentese
        //Save JButton
        ActionListener save= e -> data.saveSys();
        saveSystem.addActionListener(save);

        //Event torlese
        //Delete JButton
        ActionListener del= e -> {
            data.delEvent(data.findEvent(comboBox.getSelectedItem().toString()));
            //remove az amit torol
            comboBox.removeItem(comboBox.getSelectedItem());
        };
        delEvent.addActionListener(del);

        //Adatok betoltese
        //Load JBUtton
        ActionListener load= e -> {
            data.loadSys();
            //torli az osszes ComboBox elemet
            comboBox.removeAllItems();
            //majd ujra hozza adja mindet
            for(int i=0;i<data.events.size();i++)
                comboBox.addItem(data.events.get(i).name);
        };
        loadSystem.addActionListener(load);

        //Uj Event letrehozasa
        //Create JButton
        ActionListener createAction= e -> {
            //data.addEvent boolean-t ad vissza
            //csak akkor adja hozza ha a nev egyedi
            //ha egyedi hozzaadja a comboboxhoz az uj element
            if(data.addEvent(nameTextField.getText(), dateTextField.getText()))
                comboBox.addItem(nameTextField.getText());
            nameTextField.setText("");
            dateTextField.setText("");
        };
        createEvent.addActionListener(createAction);


        //komponensek hozzaadasa
        leftPanel.add(new JLabel("Events:"));
        leftPanel.add(comboBox);
        leftPanel.add(openEvent);
        leftPanel.add(delEvent);
        leftPanel.add(new JLabel("Load data:"));
        leftPanel.add(loadSystem);
        leftPanel.add(new JLabel("Save data:"));
        leftPanel.add(saveSystem);

        rightPanel.add(new JLabel("Create New Event:"));
        rightPanel.add(new JLabel());
        rightPanel.add(new JLabel("Name:"));
        rightPanel.add(nameTextField);
        rightPanel.add(new JLabel("Date:"));
        rightPanel.add(dateTextField);
        rightPanel.add(new JLabel("Click to create:"));
        rightPanel.add(createEvent);

        //szin beallitasa
        leftPanel.setBackground(new Color(203, 228, 249));
        rightPanel.setBackground(new Color(227,244,251));

        this.add(leftPanel);
        this.add(rightPanel);

        this.setLocationRelativeTo(null);
        this.setResizable(false);
    }
}
