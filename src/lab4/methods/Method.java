package lab4.methods;

import lab4.functions.Function;

public interface Method {
    /**
     * поиск минимума исследуемой функции, стартуя с указанного начального приближения
     */
    double[] findMinimum(Function function, double[] x0);
}
