package dev.trailsgroup.trailsproject.services.utils;

import java.text.Normalizer;

public class ClassUtils {

    public static String createLinkName(String name){
        return Normalizer.normalize(name, Normalizer.Form.NFD).replaceAll("[^\\p{ASCII}]", "").replaceAll("[^a-zA-Z0-9_.-]+", "-").toLowerCase();
    }


}
