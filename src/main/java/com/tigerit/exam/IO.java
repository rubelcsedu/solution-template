package com.tigerit.exam;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * @author Faisal Ahmed
 * This is a helper class for input/output in java.
 * Your don't need to use it if you want. This is
 * just for your convenience. Don't use Scanner or
 * System.console() stuff for input.
 */
public class IO {

    private final BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

    //private IO() {}

    public String readLine() {
        String value;
        try {
            value = reader.readLine();
        } catch (IOException ex) {
            value = null;
        }
        return value;
    }

    public Integer readLineAsInteger() {
        return Integer.parseInt(readLine());
    }

    public void printLine(Object value) {
        System.out.println(value);
    }
}