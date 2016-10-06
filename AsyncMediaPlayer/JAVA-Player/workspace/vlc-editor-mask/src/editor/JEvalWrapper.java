/**
 * 
 */
package editor;

import net.sourceforge.jeval.EvaluationException;
import net.sourceforge.jeval.Evaluator;

/**
 * @author vlc
 *
 */
public class JEvalWrapper {
	Evaluator eval = new Evaluator();

	final static float maxReturnValue = 2.0f;
	final static float minReturnValue = 0.8f;

	/**
	 * @param args
	 */
	public static void main(String[] args) {

	}

	public float getValue(String func, Float x)
	{
		Evaluator eval = new Evaluator();
		String funcWithVal = func.replaceAll("x", x.toString());
		
		try {
			float returnValue = Float.parseFloat(eval.evaluate(funcWithVal));
			
			if (returnValue > maxReturnValue) return maxReturnValue;
			if (returnValue < minReturnValue) return minReturnValue;
			return returnValue;
			
		} catch (EvaluationException e) {
			// TODO Auto-generated catch block
			return -1;
		}

	}
}
