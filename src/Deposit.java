/**
 * Created by m.farsiabi on 5/23/2016.
 */

import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.math.RoundingMode;

public class Deposit {
    private String customerNum;
    private BigDecimal balance;
    private int duration;
    private BigDecimal payedInterest;
    private DepositType depositType;

    public Deposit(String customerNumVal, String balanceVal, int durationVal, String depositTypeVal) {
        customerNum = customerNumVal;
        balance = new BigDecimal(balanceVal);
        duration = durationVal;

        //reflection
        try {
            Class depositTypeClasses = Class.forName(depositTypeVal + "Deposit");
            Method reflectedMethod = depositTypeClasses.getMethod("setInterestRate");
            depositType = (DepositType)depositTypeClasses.newInstance();
            reflectedMethod.invoke(depositType);
            setPayedInterest();
        }catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String getCustomerNum() {
        return customerNum;
    }

    public String setPayedInterest() {
        payedInterest = balance.multiply(new BigDecimal(depositType.getInterestRate() * duration));
        payedInterest = payedInterest.divide(new BigDecimal(36500), 2, RoundingMode.HALF_UP);
        return payedInterest.toString();
    }

    public String getPayedInterest() {
        return payedInterest.toString();
    }
}
