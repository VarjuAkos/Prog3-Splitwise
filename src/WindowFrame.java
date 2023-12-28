import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class WindowFrame extends JFrame {
    private final JTextField participantName,expenseValue,expenseName;
    private final JComboBox PparticipantBox,EXPparticipantBox,transactionBox,expenseBox;

    WindowFrame(Event event){
        ImageIcon icon=new ImageIcon("logo.png");
        this.setIconImage(icon.getImage());
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(900,280);
        setTitle(event.name);
        setVisible(true);
        this.setLayout(new GridLayout(1,3));
        //idaig alap beallitasok


        //JPanelek:
        JPanel left = new JPanel(new GridLayout(7, 2));
        JPanel middle = new JPanel(new GridLayout(7, 2));
        JPanel right = new JPanel(new GridLayout(7, 2));


        //komponenensek
        //left panel
        participantName=new JTextField(20);
        JButton pAddButton = new JButton("Add");
        PparticipantBox=new JComboBox<>(event.Pnames());
        JButton pDelButton = new JButton("Delete");

        //middle panel
        EXPparticipantBox=new JComboBox<>(event.Pnames());
        expenseName=new JTextField(20);
        expenseValue=new JTextField(20);
        JButton expAddButton = new JButton("Add");
        expenseBox=new JComboBox(event.Expnames());
        JButton expDelButton = new JButton("Delete");

        //right panel
        JButton calcButton = new JButton("Calculate");
        JLabel nunmberofp= new JLabel(String.valueOf(event.participants.size()));
        JLabel budget=new JLabel(String.valueOf(event.buget));
        JLabel avrage=new JLabel(String.valueOf(event.average));
        transactionBox =new JComboBox();

        //Participant hozzadasa
        //Add JButton
        ActionListener addParticipant= e -> {
            //vizsgálja az egyediseget
            //ha a kjev tovabbra is egyedi marad hozzaadaj es hozzaadaj az uj elemet JComboBoxba
            if(event.addParticipant(participantName.getText())) {
                PparticipantBox.addItem(participantName.getText());
                EXPparticipantBox.addItem(participantName.getText());
            }
            //ha uj resztvevot veszunk fel akkor a "regi" eredmeny nem pontos valtozik atlag...
            //torlolni kell a tranzakcio ComboBoxot
            transactionBox.removeAllItems();
            //Jlabel-ek beallitasa a pontos ertekre
            nunmberofp.setText(String.valueOf(event.participants.size()));
            participantName.setText("");
            avrage.setText(String.valueOf(event.average));
        };
        pAddButton.addActionListener(addParticipant);

        //Participant torlese
        //Delete JButton
        ActionListener delParticipant= e -> {
            //egyedi a nev, nev alapjan lehet azonsoitani: ezt torli
            event.delParticipant(PparticipantBox.getSelectedItem().toString());
            //frissiti a JComboBoxokat
            EXPparticipantBox.removeItem(PparticipantBox.getSelectedItem());
            PparticipantBox.removeItem(PparticipantBox.getSelectedItem());
            //torli a tranzakciokat
            transactionBox.removeAllItems();
            //JLabelek frissitese
            nunmberofp.setText(String.valueOf(event.participants.size()));
            budget.setText(String.valueOf(event.buget));
            avrage.setText(String.valueOf(event.average));
            //kiadasokat megjelenito ComboBox torlese
            //majd ujra ertekadasa
            //igy eltunnek a torolt felhasznalo kiadasai
            expenseBox.removeAllItems();
            for (int i = 0; i < event.participants.size(); i++) {
                for (int j = 0; j < event.participants.get(i).expenses.size(); j++) {
                    expenseBox.addItem(event.participants.get(i).name+" "+event.participants.get(i).expenses.get(j).name+" "+event.participants.get(i).expenses.get(j).amount);
                }
            }
        };
        pDelButton.addActionListener(delParticipant);

        //Expense hozzaadasa
        //Add Expense JButton
        ActionListener expAdd= e -> {
            Participant ez=event.findP(EXPparticipantBox.getSelectedItem().toString());
            //Expense neve egyedi az adott resztevonel, az alapjan keresi h egyedi marad e
            //ha igen hozzaadja majd frissiti a kiadasokat tartalmazo ComboBoxot
            if(ez.addExpense(expenseName.getText(),expenseValue.getText()))
                expenseBox.addItem(ez.name+" "+expenseName.getText()+" "+expenseValue.getText());
            //feltolteni az ujjal a combobox
            //frissitett adatok megjelenitese
            event.update();
            transactionBox.removeAllItems();
            avrage.setText(String.valueOf(event.average));
            budget.setText(String.valueOf(event.buget));
            expenseName.setText("");
            expenseValue.setText("");

        };
        expAddButton.addActionListener(expAdd);

        //Expense torlese
        //Delete Expense JButton
        ActionListener delexp= e -> {
            //kiolvassa a ComboBox erteket majd ertelmezi
            String[] cmd =expenseBox.getSelectedItem().toString().split(" ");
            //keresi a torlendo Expens-t majd tolri es eltavolitja az ExpenseBoxbol
            event.findP(cmd[0]).delExpense(event.findP(cmd[0]).findExp(cmd[1]));
            expenseBox.removeItem(expenseBox.getSelectedItem());
            //adatok frissitese
            event.update();
            transactionBox.removeAllItems();
            avrage.setText(String.valueOf(event.average));
            budget.setText(String.valueOf(event.buget));
        };
        expDelButton.addActionListener(delexp);

        //Eredmeny kiszamolasa
        //Calculate JButton
        ActionListener calc=e-> {
            //torol h ne jegyen a vegredemeny ketszer benne ha pl egymas utan ketszer nyomja meg a felhasznalo
            transactionBox.removeAllItems();
            event.transactions.clear();
            //frissit minden, h pontos ertekekkel szamoljon
            event.update();
            event.szamol();
            //feltolti a ComboBoxot a megoldassal
            for(int i = 0; i<event.transactions.size(); i++){
                transactionBox.addItem(event.transactions.get(i).paid_dby.name+"--->"+event.transactions.get(i).paid_to.name+": "+event.transactions.get(i).amount);
            }
        };
        calcButton.addActionListener(calc);


        //komponensek hozzadasa
        left.add(new JLabel("ADD PARTICIPANT"));
        left.add(new JLabel());
        left.add(new JLabel("Participant name:"));
        left.add(participantName);
        left.add(new JLabel("Click to add:"));
        left.add(pAddButton);
        left.add(new JLabel("Participants:"));
        left.add(PparticipantBox);
        left.add(new JLabel("Click to delete:"));
        left.add(pDelButton);

        middle.add(new JLabel("ADD EXPENSE"));
        middle.add(new JLabel());
        middle.add(new JLabel("Paid by:"));
        middle.add(EXPparticipantBox);
        middle.add(new JLabel("Expense name:"));
        middle.add(expenseName);
        middle.add(new JLabel(" value:"));
        middle.add(expenseValue);
        middle.add(new JLabel("Click to add:"));
        middle.add(expAddButton);
        middle.add(new JLabel("Expenses:"));
        middle.add(expenseBox);
        middle.add(new JLabel("Click to delete:"));
        middle.add(expDelButton);

        right.add(new JLabel("Let's calculate:"));
        right.add(calcButton);
        right.add(new JLabel("Event name:"));
        right.add(new JLabel(event.name));
        right.add(new JLabel("date:"));
        right.add(new JLabel(event.date));
        right.add(new JLabel("Participants:"));
        right.add(nunmberofp);
        right.add(new JLabel("Sum:"));
        right.add(budget);
        right.add(new JLabel("Average:"));
        right.add(avrage);
        right.add(new JLabel("Transactions:"));
        right.add(transactionBox);

        //szin bealliatsa
        left.setBackground(new Color(227,244	,251));
        middle.setBackground(new Color(203, 228, 249));
        right.setBackground(new Color(227,244	,251));
        this.add(left);
        this.add(middle);
        this.add(right);


        this.setLocationRelativeTo(null);
        this.setResizable(false);
    }
}
