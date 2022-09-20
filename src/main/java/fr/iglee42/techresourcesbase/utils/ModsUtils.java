package fr.iglee42.techresourcesbase.utils;

public class ModsUtils {

    public static String[] split(String base,String separator){
        String[] st = base.split(separator);
        String[] finale = new String[] {};
        for (String s : st){
            String fi = s;
            if (s.endsWith(separator))fi = s.substring(s.length()-1);
            finale[finale.length] = fi;
        }
        return finale;
    }

}
