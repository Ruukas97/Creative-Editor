package infinityitemeditor.util;

public class CEStringUtils {

    public static String uppercaseFirstLowercaseRest(String s) {
        return s.substring(0, 1).toUpperCase() + s.substring(1).toLowerCase();
    }

    public static String zeroPaddedInt(int number, int length){
        String nString = Integer.toString(number);
        if(nString.length() == length){
            return nString;
        }
        StringBuilder padding = new StringBuilder(length);
        for(int i=0;i<length-nString.length();i++){
          padding.append('0');
        }
        padding.append(nString);
        return padding.toString();
    }
}
