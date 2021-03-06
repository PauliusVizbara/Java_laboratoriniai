package laborai.gui.swing;

import Mano.GreitaveikosTyrimas;
import java.util.Random;
import Mano.NonPlayableCharacter;
import laborai.gui.MyException;
import laborai.studijosktu.AvlSetKTUx;
import laborai.studijosktu.SortedSetADTx;
import laborai.studijosktu.BstSetKTUx;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.AdjustmentEvent;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Random;
import java.util.ResourceBundle;
import java.util.TreeSet;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import javax.swing.ImageIcon;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.WindowConstants;
import javax.swing.border.TitledBorder;

import laborai.studijosktu.Ks;
import laborai.studijosktu.SetADT;
import laborai.studijosktu.SortedSetADT;

/**
 * Lab2 langas su Swing'u
 * <pre>
 *                  Lab2Panel (BorderLayout)
 *  |-----------------------Center-------------------------|
 *  |  |-----------------scrollOutput-------------------|  |
 *  |  |  |------------------------------------------|  |  |
 *  |  |  |                                          |  |  |
 *  |  |  |                                          |  |  |
 *  |  |  |                 taOutput                 |  |  |
 *  |  |  |                                          |  |  |
 *  |  |  |                                          |  |  |
 *  |  |  |                                          |  |  |
 *  |  |  |                                          |  |  |
 *  |  |  |                                          |  |  |
 *  |  |  |------------------------------------------|  |  |                                                              |  |
 *  |  |------------------------------------------------|  |                                          |
 *  |------------------------South-------------------------|
 *  |  |~~~~~~~~~~~~~~~~~~~scrollSouth~~~~~~~~~~~~~~~~~~|  |
 *  |  |                                                |  |
 *  |  |    |------------||-----------||-----------|    |  |
 *  |  |    | panButtons || panParam1 || panParam2 |    |  |
 *  |  |    |            ||           ||           |    |  |
 *  |  |    |------------||-----------||-----------|    |  |
 *  |  |                                                |  |
 *  |  |~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~|  |
 *  |------------------------------------------------------|
 * </pre>
 *
 * @author darius.matulis@ktu.lt
 */
public class Lab2Window extends JFrame implements ActionListener {

    private static final ResourceBundle MESSAGES = ResourceBundle.getBundle("laborai.gui.messages");
    private static final int TF_WIDTH = 8;

    private final JTextArea taOutput = new JTextArea();
    private final JScrollPane scrollOutput = new JScrollPane(taOutput);
    private final JTextField tfDelimiter = new JTextField();
    private final JTextField tfInput = new JTextField();
    private final JComboBox cmbTreeType = new JComboBox();
    private final JPanel panSouth = new JPanel();
    private final JScrollPane scrollSouth = new JScrollPane(panSouth);
    private final JPanel panParam2 = new JPanel();
    private Menu menus;
    private Panels panParam1, panButtons;

    // private SortedSetADTx<Automobilis> autoSet;
    private SortedSetADTx<NonPlayableCharacter> nonPlayableCharacterSet;
    private SortedSetADTx<NonPlayableCharacter> filteredNonPlayableCharacterSet;

    private int sizeOfInitialSubSet, sizeOfGenSet, sizeOfLeftSubSet;
    private double coef;
    private String delimiter;
    private final String[] errors;

    public Lab2Window() {
        errors = new String[]{
            MESSAGES.getString("error1"),
            MESSAGES.getString("error2"),
            MESSAGES.getString("error3"),
            MESSAGES.getString("error4")
        };
        initComponents();
    }

    private void initComponents() {
        // Kad prijungiant tekstą prie JTextArea vaizdas visada nušoktų į apačią
        scrollOutput.getVerticalScrollBar().addAdjustmentListener((AdjustmentEvent e) -> {
            taOutput.select(taOutput.getCaretPosition()
                    * taOutput.getFont().getSize(), 0);
        });
        //======================================================================
        // Formuojamas mygtukų tinklelis (mėlynas). Naudojame klasę Panels.
        //======================================================================
        // 4 eilutes, 2 stulpeliai
        panButtons = new Panels(
                new String[]{
                    MESSAGES.getString("button1"),
                    MESSAGES.getString("button2"),
                    MESSAGES.getString("button3"),
                    MESSAGES.getString("button4"),
                    MESSAGES.getString("button5"),
                    MESSAGES.getString("button6"),
                    MESSAGES.getString("button7")}, 2, 4);
        panButtons.getButtons().forEach((btn) -> {
            btn.addActionListener(this);
        });
        enableButtons(false);
        //======================================================================
        // Formuojama pirmoji parametrų lentelė (žalia). Naudojame klasę Panels.
        //======================================================================
        panParam1 = new Panels(
                new String[]{
                    MESSAGES.getString("lblParam11"),
                    MESSAGES.getString("lblParam12"),
                    MESSAGES.getString("lblParam13"),
                    MESSAGES.getString("lblParam14"),
                    MESSAGES.getString("lblParam15")},
                new String[]{
                    MESSAGES.getString("tfParam11"),
                    MESSAGES.getString("tfParam12"),
                    MESSAGES.getString("tfParam13"),
                    MESSAGES.getString("tfParam14"),
                    MESSAGES.getString("tfParam15")}, TF_WIDTH);
        //======================================================================
        // Formuojama antroji parametrų lentelė (gelsva).
        //======================================================================
        panParam2.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.insets = new Insets(3, 6, 3, 4);
        // Išlygiavimas į kairę
        c.anchor = GridBagConstraints.WEST;
        // Komponentų išplėtimas iki maksimalaus celės dydžio
        c.fill = GridBagConstraints.BOTH;
        // Pirmas stulpelis
        c.gridx = 0;
        panParam2.add(new JLabel(MESSAGES.getString("lblParam21")), c);
        panParam2.add(new JLabel(MESSAGES.getString("lblParam22")), c);
        panParam2.add(new JLabel(MESSAGES.getString("lblParam23")), c);
        // Antras stulpelis
        c.gridx = 1;
        for (String s : new String[]{
            MESSAGES.getString("cmbTreeType1"),
            MESSAGES.getString("cmbTreeType2"),
            MESSAGES.getString("cmbTreeType3")
        }) {
            cmbTreeType.addItem(s);
        }
        cmbTreeType.addActionListener(this);
        panParam2.add(cmbTreeType, c);
        tfDelimiter.setHorizontalAlignment(JTextField.CENTER);
        panParam2.add(tfDelimiter, c);
        // Vėl pirmas stulpelis, tačiau plotis - 2 celės
        c.gridx = 0;
        c.gridwidth = 2;
        tfInput.setEditable(false);
        tfInput.setBackground(Color.lightGray);
        panParam2.add(tfInput, c);
        //======================================================================
        // Formuojamas bendras parametrų panelis (tamsiai pilkas).
        //======================================================================
        panSouth.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
        panSouth.add(panButtons);
        panSouth.add(panParam1);
        panSouth.add(panParam2);

        menus = new Menu(this);
        // Meniu juosta patalpinama šiame freime
        setJMenuBar(menus);
        // Formuojamas Lab2 panelis        
        JPanel lab2 = new JPanel();
        lab2.setLayout(new BorderLayout());
        // ..centre ir pietuose talpiname objektus..
        lab2.add(scrollOutput, BorderLayout.CENTER);
        lab2.add(scrollSouth, BorderLayout.SOUTH);

        // Šio freimo "viduje" talpinamas lab2 panelis
        getContentPane().add(lab2);
        appearance();
    }

    private void appearance() {
        // Grafinių objektų rėmeliai
        TitledBorder outputBorder = new TitledBorder(MESSAGES.getString("border1"));
        outputBorder.setTitleFont(new Font(Font.SANS_SERIF, Font.BOLD, 11));
        scrollOutput.setBorder(outputBorder);
        TitledBorder southBorder = new TitledBorder(MESSAGES.getString("border2"));
        southBorder.setTitleFont(new Font(Font.SANS_SERIF, Font.BOLD, 11));
        scrollSouth.setBorder(southBorder);

        panParam2.setBackground(new Color(255, 255, 153));// Gelsva
        panParam1.setBackground(new Color(204, 255, 204));// Šviesiai žalia
        panParam1.getTfOfTable().get(2).setEditable(false);
        panParam1.getTfOfTable().get(2).setForeground(Color.red);
        panParam1.getTfOfTable().get(4).setEditable(true);
        panParam1.getTfOfTable().get(4).setBackground(Color.white);
        panButtons.setBackground(new Color(112, 162, 255)); // Blyškiai mėlyna
        panSouth.setBackground(Color.GRAY);
        taOutput.setFont(Font.decode("courier new-12"));
        taOutput.setEditable(false);
    }

    @Override
    public void actionPerformed(ActionEvent ae) {
        try {
            System.gc();
            System.gc();
            System.gc();
            taOutput.setBackground(Color.white);
            Object source = ae.getSource();

            if (source.equals(panButtons.getButtons().get(0))) {
                treeGeneration(null);
            } else if (source.equals(panButtons.getButtons().get(1))) {
                treeIteration();
            } else if (source.equals(panButtons.getButtons().get(2))) {
                treeAdd();
            } else if (source.equals(panButtons.getButtons().get(3))) {
                treeEfficiency();
            } else if (source.equals(panButtons.getButtons().get(4))) {
                treeRemove();
            } else if (source.equals(panButtons.getButtons().get(5))) {
                treeSetsTesting();

            } else if (source.equals(panButtons.getButtons().get(6))) {
                getTreeLessThanParameter();

            } else if (source.equals(cmbTreeType)) {
                enableButtons(false);
            }
        } catch (MyException e) {
            if (e.getCode() >= 0 && e.getCode() <= 3) {
                KsSwing.ounerr(taOutput, errors[e.getCode()] + ": " + e.getMessage());
                if (e.getCode() == 2) {
                    panParam1.getTfOfTable().get(0).setBackground(Color.red);
                    panParam1.getTfOfTable().get(1).setBackground(Color.red);
                }
            } else if (e.getCode() == 4) {
                KsSwing.ounerr(taOutput, MESSAGES.getString("msg3"));
            } else {
                KsSwing.ounerr(taOutput, e.getMessage());
            }
        } catch (java.lang.UnsupportedOperationException e) {
            KsSwing.ounerr(taOutput, e.getLocalizedMessage());
        } catch (Exception e) {
            KsSwing.ounerr(taOutput, MESSAGES.getString("error5"));
            e.printStackTrace(System.out);
        }
    }

    public void getTreeLessThanParameter() {
        double maxHP;
        if (panParam1.getParametersOfTable().get(4).length() > 0) {
            maxHP = Double.valueOf(panParam1.getParametersOfTable().get(4).replace(',', '.'));
        } else {
            maxHP = 50;
        }

        SortedSetADT<NonPlayableCharacter> filteredNonPlayableCharacterSetADT = nonPlayableCharacterSet.headSet(new NonPlayableCharacter("", 0, 0, maxHP, true));
        filteredNonPlayableCharacterSet.clear();
        for (NonPlayableCharacter npc : filteredNonPlayableCharacterSetADT) {
            filteredNonPlayableCharacterSet.add(npc);
        }

        KsSwing.oun(taOutput, filteredNonPlayableCharacterSet.toVisualizedString(delimiter),
                MESSAGES.getString("msg5"));

    }

    public void treeSetsTesting() {
        NonPlayableCharacter NPC1 = new NonPlayableCharacter("Knight1", 15, 10, 60, true);
        SortedSetADT<NonPlayableCharacter> setas1 = nonPlayableCharacterSet.headSet(NPC1);
        KsSwing.oun(taOutput, "HeadSet, parametras: 60");
        KsSwing.oun(taOutput, setas1.toString());

        NonPlayableCharacter NPC2 = new NonPlayableCharacter("Knight1", 15, 10, 31, true);
        NonPlayableCharacter NPC3 = new NonPlayableCharacter("Knight1", 15, 10, 100, true);
        SortedSetADT<NonPlayableCharacter> setas2 = nonPlayableCharacterSet.subSet(NPC2, NPC3);
        KsSwing.oun(taOutput, "SubSet, parametras: 31-100");
        KsSwing.oun(taOutput, setas2.toString());

        NonPlayableCharacter NPC4 = new NonPlayableCharacter("Knight1", 15, 10, 60, true);
        SortedSetADT<NonPlayableCharacter> setas3 = nonPlayableCharacterSet.tailSet(NPC4);
        KsSwing.oun(taOutput, "TailSet, parametras: 60");
        KsSwing.oun(taOutput, setas3.toString());

        NonPlayableCharacter NPC5 = new NonPlayableCharacter("Knight1", 15, 10, 70, true);
        KsSwing.oun(taOutput, "Higher, parametras: 70");
        KsSwing.oun(taOutput, nonPlayableCharacterSet.higher(NPC1));

        KsSwing.oun(taOutput, "Didžiausias elementas: " + nonPlayableCharacterSet.pollLast());
        KsSwing.oun(taOutput, "Medžio aukštis: " + nonPlayableCharacterSet.treeHeight());

    }

    public void treeGeneration(String filePath) throws MyException {
        // Nuskaitomi uždavinio parametrai
        readTreeParameters();
        // Sukuriamas aibės objektas, priklausomai nuo medžio pasirinkimo
        // cmbTreeType objekte
        createTree();

        nonPlayableCharacterSet.clear();

        NonPlayableCharacter NPC1 = new NonPlayableCharacter("Knight", 15, 10, 40, true);
        NonPlayableCharacter NPC2 = new NonPlayableCharacter("Knight1", 15, 10, 60, true);
        NonPlayableCharacter NPC3 = new NonPlayableCharacter("Knight2", 15, 10, 70, true);
        NonPlayableCharacter NPC4 = new NonPlayableCharacter("Knight3", 15, 10, 30, true);

        nonPlayableCharacterSet.add(NPC1);
        nonPlayableCharacterSet.add(NPC2);
        nonPlayableCharacterSet.add(NPC3);
        nonPlayableCharacterSet.add(NPC4);
        nonPlayableCharacterSet.add(new NonPlayableCharacter("Knight3", 13, 10, 65, true));
        nonPlayableCharacterSet.add(new NonPlayableCharacter("Knight3", 13, 10, 75, true));
        nonPlayableCharacterSet.add(new NonPlayableCharacter("Knight3", 13, 10, 80, true));

        //.add(new);
        // Išvedami rezultatai
        // Nustatoma, kad eilutės pradžioje neskaičiuotų išvedamų eilučių skaičiaus
        KsSwing.setFormatStartOfLine(true);
        KsSwing.oun(taOutput, nonPlayableCharacterSet.toVisualizedString(delimiter),
                MESSAGES.getString("msg5"));
        // Nustatoma, kad eilutės pradžioje skaičiuotų išvedamų eilučių skaičių
        KsSwing.setFormatStartOfLine(false);
        enableButtons(true);
    }

    private void treeAdd() throws MyException {

        Random rand = new Random();
        int randomHP = rand.nextInt(100) + 1;
        NonPlayableCharacter randomNPC = new NonPlayableCharacter("Outsider", 1, 1, randomHP, true);
        nonPlayableCharacterSet.add(randomNPC);
        KsSwing.oun(taOutput, nonPlayableCharacterSet.toVisualizedString(delimiter),
                MESSAGES.getString("msg5"));
    }

    private void treeRemove() {
        double searchingHP;
        if (panParam1.getParametersOfTable().get(4).length() > 0) {
            searchingHP = Double.valueOf(panParam1.getParametersOfTable().get(4).replace(',', '.'));
        } else {
            searchingHP = 50;
        }

        KsSwing.setFormatStartOfLine(true);
        if (nonPlayableCharacterSet.isEmpty()) {
            KsSwing.ounerr(taOutput, MESSAGES.getString("msg4"));
            KsSwing.oun(taOutput, nonPlayableCharacterSet.toVisualizedString(delimiter));
        } else {

            int nr = new Random().nextInt(nonPlayableCharacterSet.size());
            NonPlayableCharacter tempNpc = (NonPlayableCharacter) nonPlayableCharacterSet.toArray()[nr];

            for (NonPlayableCharacter npc : nonPlayableCharacterSet) {
                if (npc.getHp() == searchingHP) {
                    tempNpc = npc;
                }
            }
            nonPlayableCharacterSet.remove(tempNpc);
            //KsSwing.oun(taOutput, auto, MESSAGES.getString("msg6"));
            KsSwing.oun(taOutput, "Šalinam:" + tempNpc.toString());
            KsSwing.oun(taOutput, nonPlayableCharacterSet.toVisualizedString(delimiter));
        }
        KsSwing.setFormatStartOfLine(false);
    }

    private void treeIteration() {
        KsSwing.setFormatStartOfLine(true);
        if (nonPlayableCharacterSet.isEmpty()) {
            KsSwing.ounerr(taOutput, MESSAGES.getString("msg4"));
        } else {
            KsSwing.oun(taOutput, nonPlayableCharacterSet, MESSAGES.getString("msg8"));
        }
        KsSwing.setFormatStartOfLine(false);
    }

    private void treeEfficiency() throws MyException {
        KsSwing.setFormatStartOfLine(true);
        KsSwing.oun(taOutput, "", MESSAGES.getString("msg2"));
        KsSwing.setFormatStartOfLine(false);
        boolean[] statesOfButtons = new boolean[panButtons.getButtons().size()];
        for (int i = 0; i < panButtons.getButtons().size(); i++) {
            statesOfButtons[i] = panButtons.getButtons().get(i).isEnabled();
            panButtons.getButtons().get(i).setEnabled(false);
        }
        cmbTreeType.setEnabled(false);
        for (Component component : menus.getComponents()) {
            component.setEnabled(false);
        }

        GreitaveikosTyrimas gt = new GreitaveikosTyrimas();

        // Sukuriamos dvi tuscios gijos. Panaudojamas Java Executor servisas.
        ExecutorService executorService = Executors.newFixedThreadPool(2);

        // Si gija paima rezultatus is greitaveikos tyrimo gijos ir isveda 
        // juos i taOutput. Gija baigia darbą kai gaunama FINISH_COMMAND
        executorService.submit(() -> {
            KsSwing.setFormatStartOfLine(false);
            try {
                String result;
                while (!(result = gt.getResultsLogger().take())
                        .equals(GreitaveikosTyrimas.FINISH_COMMAND)) {
                    KsSwing.ou(taOutput, result);
                    gt.getSemaphore().release();
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }

            gt.getSemaphore().release();

            for (int i = 0; i < panButtons.getButtons().size(); i++) {
                panButtons.getButtons().get(i).setEnabled(statesOfButtons[i]);
            }
            cmbTreeType.setEnabled(true);
            for (Component component : menus.getComponents()) {
                component.setEnabled(true);
            }
        });

        //Sioje gijoje atliekamas greitaveikos tyrimas
        executorService.submit(() -> gt.pradetiTyrima());
        executorService.shutdown();
    }

    private void readTreeParameters() throws MyException {
        // Truputėlis kosmetikos..
        for (int i = 0; i < 3; i++) {
            panParam1.getTfOfTable().get(i).setBackground(Color.WHITE);
        }
        // Nuskaitomos parametrų reiksmės. Jei konvertuojant is String
        // įvyksta klaida, sugeneruojama NumberFormatException situacija. Tam, kad
        // atskirti kuriame JTextfield'e ivyko klaida, panaudojama nuosava
        // situacija MyException
        int i = 0;
        try {
            // Pakeitimas (replace) tam, kad sukelti situaciją esant
            // neigiamam skaičiui            
            sizeOfGenSet = Integer.valueOf(panParam1.getParametersOfTable().get(i).replace("-", "x"));
            sizeOfInitialSubSet = Integer.valueOf(panParam1.getParametersOfTable().get(++i).replace("-", "x"));
            sizeOfLeftSubSet = sizeOfGenSet - sizeOfInitialSubSet;
            ++i;
            coef = Double.valueOf(panParam1.getParametersOfTable().get(++i).replace("-", "x"));
        } catch (NumberFormatException e) {
            // Galima ir taip: pagauti exception'ą ir vėl mesti
            throw new MyException(panParam1.getParametersOfTable().get(i), i);
        }
        delimiter = tfDelimiter.getText();
    }

    private void createTree() throws MyException {
        switch (cmbTreeType.getSelectedIndex()) {
            case 0:
                filteredNonPlayableCharacterSet = new BstSetKTUx(new NonPlayableCharacter());
                nonPlayableCharacterSet = new BstSetKTUx(new NonPlayableCharacter());
                break;
            case 1:
                filteredNonPlayableCharacterSet = new BstSetKTUx(new NonPlayableCharacter());
                nonPlayableCharacterSet = new AvlSetKTUx(new NonPlayableCharacter());
                break;
            default:
                enableButtons(false);
                throw new MyException(MESSAGES.getString("msg1"));
        }
    }

    private void enableButtons(boolean enable) {
        for (int i : new int[]{1, 2, 4, 5, 6}) {
            panButtons.getButtons().get(i).setEnabled(enable);
        }
    }

    public JTextArea getTaOutput() {
        return taOutput;
    }

    public static void createAndShowGUI() {
        try {
            UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName()
            // Arba sitaip, tada swing komponentu isvaizda priklausys
            // nuo naudojamos OS:
            //  UIManager.getSystemLookAndFeelClassName()
            // Arba taip:
            //  "com.sun.java.swing.plaf.motif.MotifLookAndFeel"
            // Linux'e dar taip:
            //  "com.sun.java.swing.plaf.gtk.GTKLookAndFeel"
            );
            UIManager.put("Button.defaultButtonFollowsFocus", Boolean.TRUE);
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException ex) {
            Ks.ou(ex.getMessage());
        }
        Lab2Window window = new Lab2Window();
        window.setLocation(50, 50);
        window.setIconImage(new ImageIcon(MESSAGES.getString("icon")).getImage());
        window.setTitle(MESSAGES.getString("title"));
        window.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        window.setPreferredSize(new Dimension(1100, 650));
        window.pack();
        window.setVisible(true);
    }
}
