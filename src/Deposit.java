/**
 * Created by m.farsiabi on 5/23/2016.
 */

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.math.RoundingMode;

public class Deposit implements Comparable {
    private String customerNumber;
    private BigDecimal balance;
    private int duration;
    private BigDecimal payedInterest;
    private DepositType depositType;

    public void setDepositTypeInitially(String depositTypeVal) throws ExceptionPool{
        try {
            Class depositTypeClasses = Class.forName(depositTypeVal + "Deposit");
            Method reflectedMethod = depositTypeClasses.getMethod("setInterestRate");
            depositType = (DepositType)depositTypeClasses.newInstance();
            reflectedMethod.invoke(depositType);
            setPayedInterest();
        }catch (ClassNotFoundException e) {
            throw new ExceptionPool("Deposit type is invalid!");
        } catch (NoSuchMethodException e) {
            throw new ExceptionPool("There is no setInterestRate method!");
        } catch (IllegalAccessException e) {
            throw new ExceptionPool("You are not allowed to access this method!");
        }catch (InstantiationException e) {
            throw new ExceptionPool("Unable to create an instance of deposit for this data!");
        } catch (InvocationTargetException e) {
            throw new ExceptionPool("Unable to invoke the specified method on your object");
        }
    }

    public Deposit(String customerNumVal, String balanceVal, int durationVal, String depositTypeVal) throws ExceptionPool{
        customerNumber = customerNumVal;
        balance = new BigDecimal(balanceVal);
        duration = durationVal;

        setDepositTypeInitially(depositTypeVal);
    }

    public String getCustomerNum() {
        return customerNumber;
    }

    public void setPayedInterest() {
        payedInterest = balance.multiply(new BigDecimal(depositType.getInterestRate() * duration));
        payedInterest = payedInterest.divide(new BigDecimal(36500), 2, RoundingMode.HALF_UP);
    }

    public BigDecimal getPayedInterest() {
        return payedInterest;
    }

    @Override
    public int compareTo(Object o) {
        return this.getPayedInterest().compareTo(((Deposit) o).getPayedInterest());
    }
}
