import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class Main {

    public static void main(String[] args) throws Exception{
        Parser parser = new Parser();
        List<Deposit> allDeposits = new ArrayList<Deposit>(parser.getDepositList());

        //Sorting Array
        Collections.sort(allDeposits, new Comparator<Deposit>() {
            @Override
            public int compare(Deposit o1, Deposit o2) {
                return new BigDecimal(o2.getPayedInterest()).compareTo(new BigDecimal(o1.getPayedInterest()));
            }
        });

        //writing to file
        writeToFile(allDeposits);
    }

    private static void writeToFile(List<Deposit>allDeposits){
        File file = new File("output.txt");
        RandomAccessFile randomAccessFile = null;
        try {
            //create a new file if it doesn't exist already
            file.createNewFile();
            randomAccessFile = new RandomAccessFile(file, "rw");
            randomAccessFile.write(depositArrayToStr(allDeposits).getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (randomAccessFile != null) {
                try {
                    randomAccessFile.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static String depositArrayToStr(List<Deposit>allDeposits) {
        String result = "";
        for (Deposit deposit : allDeposits) {
            result = result + deposit.getCustomerNum() + "#" + deposit.getPayedInterest() + "\r\n";
        }
        return result;
    }

}
