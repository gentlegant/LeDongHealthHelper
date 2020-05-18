package com.nju.ledonghealthhelper.location;

public class LDLocationProvider {
    private static volatile LDLocationProvider ldLocationProvider;
    private LDLocationProvider () {

    }

    public static LDLocationProvider getInstance() {
        if (ldLocationProvider == null) {
            synchronized (LDLocationProvider.class) {
                if (ldLocationProvider == null) {
                    ldLocationProvider = new LDLocationProvider();
                }
            }
        }
        return ldLocationProvider;
    }
}
