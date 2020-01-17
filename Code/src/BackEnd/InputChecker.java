package BackEnd;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class InputChecker {

	public boolean isInteger(String str) {
		Pattern p = Pattern.compile("[0-9]+");
		Matcher m = p.matcher(str);
		boolean b = m.matches();
		return b;
	}
	
	
	public static boolean isDateFormat(String str) {
		boolean checkFormat = false;
		if (str.matches("([0-9]{4})-([0-9]{2})-([0-9]{2})")) {
			checkFormat = true;
		}
		return checkFormat;
	}
	
	
}
