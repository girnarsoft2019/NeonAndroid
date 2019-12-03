package com.gaadi.neon.util;

public class LanguageHelper {
    private static LanguageHelper singletonInstance;
    private static EnglishStrings englishStrings;
    private static BahasaStrings bahasaStrings;

    private LanguageHelper() {
    }

    public synchronized static LanguageHelper getSingletonInstance() {
        if (singletonInstance == null) {
            singletonInstance = new LanguageHelper();
        }
        return singletonInstance;
    }

    public Object getLanguageSpecificStrings() {
        if (NeonImagesHandler.getSingletonInstance().getGenericParam() != null && NeonImagesHandler.getSingletonInstance().getGenericParam().getCustomParameters() != null) {
            if (NeonImagesHandler.getSingletonInstance().getGenericParam().getCustomParameters().getLanguageId() == 1) {
                if (bahasaStrings == null) {
                    bahasaStrings = new BahasaStrings();
                }
                return bahasaStrings;
            } else {
                if (englishStrings == null) {
                    englishStrings = new EnglishStrings();
                }
                return englishStrings;
            }
        } else {
            if (englishStrings == null) {
                englishStrings = new EnglishStrings();
            }
            return englishStrings;
        }
    }
}
