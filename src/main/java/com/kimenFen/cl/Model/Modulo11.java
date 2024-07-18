package com.kimenFen.cl.Model;

public class Modulo11 {
    public static boolean verificar(String rut) {
        rut = rut.replace(".", "").replace("-", "");
        if (rut.length() < 2) {
            return false;
        }

        char dv = rut.charAt(rut.length() - 1);
        String rutBody = rut.substring(0, rut.length() - 1);

        int suma = 0;
        int multi = 2;
        for (int i = rutBody.length() - 1; i >= 0; i--) {
            suma += Character.getNumericValue(rutBody.charAt(i)) * multi;
            multi = multi == 7 ? 2 : multi + 1;
        }

        int mod11 = 11 - (suma % 11);
        char expectedDv = mod11 == 11 ? '0' : mod11 == 10 ? 'K' : (char) (mod11 + '0');

        return expectedDv == dv;
    }

    public static String rutDV(String rut) {
        rut = rut.replace(".", "").replace("-", "");
        return rut.substring(0, rut.length() - 1);
    }
}
