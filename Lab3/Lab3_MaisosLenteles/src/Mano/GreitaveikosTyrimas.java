package Mano;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Random;
import laborai.gui.MyException;
import java.util.ResourceBundle;
import java.util.TreeSet;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Semaphore;
import java.util.concurrent.SynchronousQueue;

public class GreitaveikosTyrimas {

    public static final String FINISH_COMMAND = "finish";
    private static final ResourceBundle MESSAGES = ResourceBundle.getBundle("laborai.gui.messages");

    private static final String[] TYRIMU_VARDAI = {"treeSetAdd", "hashSetAdd", "treeSetContains", "hashSetContains"};
    private static final int[] TIRIAMI_KIEKIAI = {10000, 20000, 40000, 80000};

    private final BlockingQueue resultsLogger = new SynchronousQueue();
    private final Semaphore semaphore = new Semaphore(-1);
    private final Timekeeper tk;
    private final String[] errors;

    TreeSet<Integer> treeSet = new TreeSet();
    HashSet<Integer> hashSet = new HashSet();

    public GreitaveikosTyrimas() {
        semaphore.release();
        tk = new Timekeeper(TIRIAMI_KIEKIAI, resultsLogger, semaphore);
        errors = new String[]{
            MESSAGES.getString("error1"),
            MESSAGES.getString("error2"),
            MESSAGES.getString("error3"),
            MESSAGES.getString("error4")
        };
    }

    public void pradetiTyrima() {
        try {
            SisteminisTyrimas();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        } catch (Exception ex) {
            ex.printStackTrace(System.out);
        }
    }

    public void SisteminisTyrimas() throws InterruptedException {
        try {
            Random random = new Random();
            ArrayList<Integer> randomInts = new ArrayList();
            for (int k : TIRIAMI_KIEKIAI) {
                for (int i = 0; i < k; i++) {

                    int randomInt = random.nextInt(1000) + 1;
                    randomInts.add(randomInt);
                }
                treeSet.clear();
                hashSet.clear();

                tk.startAfterPause();

                tk.start();
                for (int i = 0; i < k; i++) {

                    treeSet.add(randomInts.get(i));
                }

                tk.finish(TYRIMU_VARDAI[0]);
                for (int i = 0; i < k; i++) {
                    hashSet.add(randomInts.get(i));
                }
                tk.finish(TYRIMU_VARDAI[1]);
                for (int i = 0; i < k; i++) {

                    treeSet.contains(randomInts.get(i));
                }
                tk.finish(TYRIMU_VARDAI[2]);
                for (int i = 0; i < k; i++) {

                    treeSet.contains(randomInts.get(i));
                }
                tk.finish(TYRIMU_VARDAI[3]);
                tk.seriesFinish();
            }
            tk.logResult(FINISH_COMMAND);
        } catch (MyException e) {
            System.out.println(e);
        }
    }

    public BlockingQueue<String> getResultsLogger() {
        return resultsLogger;
    }

    public Semaphore getSemaphore() {
        return semaphore;
    }
}
