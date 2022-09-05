package tradeindicatorservice.tradeindicator.Indicator.RSI;


import java.io.Serializable;
import java.util.Arrays;

public class RSI implements Serializable {
    private int period;
    private EMA ema;

    public RSI(int period) {
        this.period = period;
        this.ema = new EMA(2*period - 1);
    }

    public double count(final double[] prizes) {

        final double[] up = new double[prizes.length - 1];
        final double[] down = new double[prizes.length - 1];
        for (int i = 0; i < prizes.length - 1; i++) {
            if (prizes[i] > prizes[i + 1]) {
                up[i] = prizes[i] - prizes[i + 1];
                down[i] = 0;
            }
            if (prizes[i] < prizes[i + 1]) {
                down[i] = Math.abs(prizes[i] - prizes[i + 1]);
                up[i] = 0;
            }
        }

        final int emaLength = prizes.length - 1;
        double rsi = 0.0;
        if (emaLength > 0) {

            double au = Arrays.stream(up).sum()/emaLength;
            double ad = Arrays.stream(down).sum()/emaLength;

            rsi = au/(au+ad);
        }

        return rsi;
    }
}
