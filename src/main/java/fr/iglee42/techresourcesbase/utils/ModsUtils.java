package fr.iglee42.techresourcesbase.utils;

public class ModsUtils {

    public static String[] split(String base,String separator){
        String[] st = base.split(separator);
        String[] finale = new String[st.length];
        int i = 0;
        for (String s : st){
            String fi = s;
            if (s.endsWith(separator))fi = s.substring(s.length()-1);
            finale[i] = fi;
            i += 1;
        }
        return finale;
    }

    public static String getUpperName(String name,String wordSeparator) {
        String[] nm = split(name,wordSeparator);
        String end = "";
        int i = 0;
        for (String n : nm) {
            i += 1;
            char fc = n.charAt(0);
            String fcs = String.valueOf(fc);
            String fs = fcs.toUpperCase() + n.substring(1);
            end = fs + (i == nm.length ? "" : " ");
        }

        return end;
    }

}
