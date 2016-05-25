/**
 * Created by m.farsiabi on 5/24/2016.
 */
public class LongTermDeposit extends DepositType {
    public static final double LONG_TERM_RATE = 0.2;

    public void setInterestRate() {
        super.setInterestRate(LONG_TERM_RATE);
    }
}
