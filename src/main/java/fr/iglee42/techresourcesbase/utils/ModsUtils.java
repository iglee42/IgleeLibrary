package fr.iglee42.techresourcesbase.utils;

public class ModsUtils {

    public static String[] split(String base,String separator){
        String[] st = base.split(separator);
        String[] finale = new String[2];
        for (String s : st){
            if (s.endsWith(separator))s.substring(s.length()-1);
            finale[finale.length] = s;
        }
        return finale;
    }

}
