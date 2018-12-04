package Mano;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.InputMismatchException;
import laborai.demo.*;
import laborai.studijosktu.HashType;
import laborai.studijosktu.MapKTUx;
import laborai.gui.MyException;
import java.util.LinkedList;
import java.util.NoSuchElementException;
import java.util.Queue;
import java.util.Random;
import java.util.ResourceBundle;
import java.util.Scanner;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Semaphore;
import java.util.concurrent.SynchronousQueue;
import laborai.studijosktu.Ks;
import laborai.studijosktu.MapKTU;

/**
 * @author eimutis
 */
public class GreitaveikosTyrimas {

    public static final String FINISH_COMMAND = "finishCommand";
    private static final ResourceBundle MESSAGES = ResourceBundle.getBundle("laborai.gui.messages");

    private final BlockingQueue resultsLogger = new SynchronousQueue();
    private final Semaphore semaphore = new Semaphore(-1);
    private final Timekeeper tk;

    private final String[] TYRIMU_VARDAI = {"add0.75", "add0.25", "rem0.75", "rem0.25", "get0.75", "get0.25"};
    private final int[] TIRIAMI_KIEKIAI = {1000, 2000, 4000, 6000};

    private final MapKTUx<String, Automobilis> autoAtvaizdis
            = new MapKTUx(new String(), new Automobilis(), 10, 0.75f, HashType.DIVISION);
    private final MapKTUx<String, Automobilis> autoAtvaizdis2
            = new MapKTUx(new String(), new Automobilis(), 10, 0.25f, HashType.DIVISION);
    private final Queue<String> chainsSizes = new LinkedList<>();

    public GreitaveikosTyrimas() {
        semaphore.release();
        tk = new Timekeeper(TIRIAMI_KIEKIAI, resultsLogger, semaphore);
    }

    ArrayList<String> raktai = new ArrayList<>();

    public void pradetiTyrima() {
        try {
            SisteminisTyrimas();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        } catch (Exception ex) {
            ex.printStackTrace(System.out);
        }
    }

    public void readDictionary() {
        try {   // ed - tai elementarūs duomenys, atskirti tarpais
            File file = new File("zodynas.txt");
            Scanner ed = new Scanner(file);

            while (ed.hasNextLine()) {
                raktai.add(ed.nextLine());
            }
            ed.close();

        } catch (FileNotFoundException e) {
            Ks.ern("Failo nera");
        } catch (InputMismatchException e) {
            Ks.ern("Blogas duomenų formatas");
        } catch (NoSuchElementException e) {
            Ks.ern("Trūksta duomenų");
        }
    }

    public void SisteminisTyrimas() throws InterruptedException {
        try {

            readDictionary();
            Random rand = new Random();
            chainsSizes.add(MESSAGES.getString("msg4"));
            chainsSizes.add("   kiekis      " + TYRIMU_VARDAI[0] + "   " + TYRIMU_VARDAI[1]);
            for (int k : TIRIAMI_KIEKIAI) {

                MapKTUOA<String, String> mapKTUOA = new MapKTUOA<>();
                MapKTU<String, String> mapKTU = new MapKTU<>();
                for (int i = 0; i < k; i++) {

                    mapKTUOA.put(raktai.get(i), raktai.get(i));
                    mapKTU.put(raktai.get(i), raktai.get(i));

                }

                tk.startAfterPause();
                tk.start();
                for (int i = 0; i < k; i++) {

                    if (mapKTUOA.contains(raktai.get(rand.nextInt(k)))) {

                    }
                }

                tk.finish("OA cont");
                for (int i = 0; i < k; i++) {

                    if (mapKTU.contains(raktai.get(rand.nextInt(k)))) {

                    }
                }

                tk.finish("KTU cont");
                for (int i = 0; i < k; i++) {
                    mapKTU.remove(raktai.get(rand.nextInt(k)));
                }

                tk.finish("OA remove");
                for (int i = 0; i < k; i++) {
                    mapKTUOA.remove(raktai.get(rand.nextInt(k)));
                }
                tk.finish("KTU remove");

                tk.seriesFinish();
            }

            StringBuilder sb = new StringBuilder();
            chainsSizes.stream().forEach(p -> sb.append(p).append(System.lineSeparator()));
            tk.logResult(sb.toString());
            tk.logResult(FINISH_COMMAND);
        } catch (MyException e) {
            tk.logResult(e.getMessage());
        }
    }

    public BlockingQueue<String> getResultsLogger() {
        return resultsLogger;
    }

    public Semaphore getSemaphore() {
        return semaphore;
    }
}
