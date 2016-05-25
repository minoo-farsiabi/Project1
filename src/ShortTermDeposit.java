/**
 * Created by m.farsiabi on 5/23/2016.
 */
public class ShortTermDeposit extends DepositType {
    public static final double SHORT_TERM_RATE = 0.1;

    public void setInterestRate() {
        super.setInterestRate(SHORT_TERM_RATE);
    }
}
