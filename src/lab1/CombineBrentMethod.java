package lab1;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

/**
 * Method for minimization one-dimensional <code>Function</code> 
 * by <a href="https://en.wikipedia.org/wiki/Brent%27s_method">Brent's combine method</a>
 * @author Vladislav Pavlov, Daniil Monahov
 */
public class CombineBrentMethod extends OneDimensionMethod {
	
	private double epsilon = 0.0001;
	private final double K = (3 - Math.sqrt(5)) / 2;
	private ParabolicMethod parabolic;

	public CombineBrentMethod(Function<Double, Double> function) {
		super(function);
        parabolic = new ParabolicMethod(function);
	}

	@Override
	public String getName() {
		return "Комбинированный метод Брента";
	}

	@Override
	public String toString() {
		return "CombineBrentMethod";
	}
	
	
	@Override
	public double minimize(List<StepFrame<Double>> series, double leftBorder, double rightBorder, Settings sets) {
		epsilon = sets.epsilon;
		return minimize(series, leftBorder, rightBorder);
	}


	public double minimize(double leftBorder, double rightBorder, double eps) {
		epsilon = eps;
		return minimize(new ArrayList<>(), leftBorder, rightBorder);
	}

	@Override
	public double minimize(List<StepFrame<Double>> series, double leftBorder, double rightBorder) {
		clear(series);
		parabolic.clear(series);


		double a = leftBorder;      //левая граница
		double c = rightBorder;     //правая граница
		double x = a + K * (c - a); //условный минимум
		double w = x;               //2-ой по минимальности
		double v = x;               //предыдущее значение w
		double fx = function.apply(x);//значение функции в х
		double fw = fx;             //значение функции в w
		double fv = fx;             //значение функции в v

		double d = (c - a);
		double e = d;
		double g;

		double tol;

		double u = 0;
		
		addPointToSeries(series, leftBorder, function.apply(leftBorder), key, a, c);
		addPointToSeries(series, rightBorder, function.apply(rightBorder), key, a, c);
		
		addPointToSeries(series, x, fx, key++, a, c);

		while (true) {

			//lastLen = c - a;
			g = e;
			e = d;
			boolean uIsGood = false;

			tol = epsilon * Math.abs(x) + epsilon / 10.0;

			//проверка на завершение
			if (Math.abs(x - (a + c) / 2) + (c - a) / 2 <= 2 * tol) {
				break;
			}

			//пытаемся использовать Метод Парабол
			if (x != w && x != v && w != v && fx != fv && fx != fw && fv != fw) {
				//find u (min of par on v,x,w)

				u = parabolic.nextStep(series, v, x, w, fv, fx, fw);

				//проверяем, что u попадает в диапазон и достаточно хорошо сокращает отрезок
				if (a <= u && u <= c && Math.abs(u - x) < g / 2) {

					//говорим что u нам подходит
					uIsGood = true;
					//если u оказался слишком близко к границе, то мы двигаем его в x
					if (u - a < 2 * tol || c - u < 2 * tol) {
						u = x - Math.signum(x - (a + c) / 2) * tol;
					}
				}
			}

			//когда метод парабол не прошел, переходим на Золотое сечение
			if (!uIsGood) {
				if (x < (a + c) / 2) {
					u = x + K * (c - x); //[a, x]
					e = c - x;
				} else {
					u = x - K * (x - a); //[x, c]
					e = x - a;
				}
			}

			//разводим u и x на tol(лок eps)
			if (Math.abs(u - x) < tol) {
				u = x + Math.signum(u - x) * tol;
			}
			d = Math.abs(u - x);

			double fu = function.apply(u);

			//переставляем границы
			if (fu <= fx) {
				if (u >= x) {
					a = x;
				} else {
					c = x;
				}
				v = w;
				w = x;
				x = u;
				fv = fw;
				fw = fx;
				fx = fu;
			} else {
				if (u >= x) {
					c = u;
				} else {
					a = u;
				}
				if (fu <= fw || w == x) {
					v = w;
					w = u;
					fv = fw;
					fw = fu;
				} else if (fu <= fv || v == x || v == w) {
					v = u;
					fv = fu;
				}
			}
			addPointToSeries(series, x, fx, key++, a, c);
		}
		return x;
	}

}
