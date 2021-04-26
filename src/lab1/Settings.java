package lab1;

/**
 * <code>Settings</code> of epsilon, sigma and delta
 * @author Vladislav Pavlov
 */
public class Settings {
    public double epsilon, delta = 0.000_01, sigma = 0.000_000_001;

    public Settings(double epsilon, double sigma, double delta) {
        this.delta = delta;
        this.epsilon = epsilon;
        this.sigma = sigma;
    }

    public Settings(double epsilon) {
        this.epsilon = epsilon;
    }

}
