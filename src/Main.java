import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Main {

    public static void main(String[] args) throws ExceptionPool{
        Parser parser = new Parser();
        List<Deposit> allDeposits = new ArrayList<Deposit>(parser.getDepositList());

        Collections.sort(allDeposits);

        writeToFile(allDeposits);
    }

    @Override
    public String toString() {
        return super.toString();
    }

    private static void writeToFile(List<Deposit>allDeposits){
        RandomAccessFile randomAccessFile = null;
        try {
            randomAccessFile = new RandomAccessFile("output.txt", "rw");
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
