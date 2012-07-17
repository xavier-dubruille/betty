package be.betty.gwtp.shared;

public class BettyUtils {

	// Copied from org/apache/commons/lang/StringUtils
	public static String join(Object[] array, String separator) {
        if (array == null) {
            return null;
        }
        

        int startIndex = 0;
        int endIndex = array.length;
        
        // endIndex - startIndex > 0:   Len = NofStrings *(len(firstString) + len(separator))
        //           (Assuming that all Strings are roughly equally long)
        int bufSize = (endIndex - startIndex);
        if (bufSize <= 0) {
            return "";
        }

        bufSize *= ((array[startIndex] == null ? 16 : array[startIndex].toString().length())
                        + separator.length());

        StringBuffer buf = new StringBuffer(bufSize);

        for (int i = startIndex; i < endIndex; i++) {
            if (i > startIndex) {
                buf.append(separator);
            }
            if (array[i] != null) {
                buf.append(array[i]);
            }
        }
        return buf.toString();
    }
	
}
