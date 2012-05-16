package iControl.utils;

public class ULong {

	public static Double doubleValue(iControl.services.CommonULong64 ulong) {
		long high = ulong.getHigh(); 
		long low = ulong.getLow(); 
		Double retVal; 
		Double rollOver = new Double((double)0x7fffffff); 
		rollOver = new Double(rollOver.doubleValue() + 1.0); 
		if(high >=0) {
			retVal = new Double((high << 32 & 0xffff0000)); 
		} else { 
			retVal = new Double(((high & 0x7fffffff) << 32) + (0x80000000 << 32)); 
		}
		if(low >=0) {
			retVal = new Double(retVal.doubleValue() + (double)low); 
		} else {
			retVal = new Double(retVal.doubleValue() + (double)((low & 0x7fffffff)) + rollOver.doubleValue()); 
		}
		return retVal; 
	}
	
	public static String toString(iControl.services.CommonULong64 ulong, int significant_digits) { 
		String retString = null; 
		String size = ""; 
		Double value = ULong.doubleValue(ulong); 
		double dval = value.doubleValue(); 
		if(dval / 1024 >= 1.0) { 
			size = "K"; 
			dval = dval / 1024; 
		} 
		if(dval / 1024 >= 1.0) { 
			size = "M"; 
			dval = dval / 1024; 
		} 
		if(dval / 1024 >= 1.0) { 
			size = "G"; 
			dval = dval / 1024; 
		} 
		retString = new Double(dval).toString(); 
		retString = retString.substring(0,retString.indexOf(".") + significant_digits) + size; 
		return retString; 
	} 

}
