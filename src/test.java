import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.Set;

import org.apache.commons.jexl2.Expression;
import org.apache.commons.jexl2.JexlContext;
import org.apache.commons.jexl2.JexlEngine;
import org.apache.commons.jexl2.MapContext;



public class test {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		  double amount = 2192.015;
		  NumberFormat formatter = new DecimalFormat("#0.0");
		  System.out.println("The Decimal Value is:"+formatter.format(amount));
		

	  }


}
