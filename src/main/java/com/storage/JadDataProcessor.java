package com.storage;

import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

@Service
public class JadDataProcessor {

    public void read(final InputStream inputStream){
        String str = "";
        StringBuffer buf = new StringBuffer();
        try{
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
            if (inputStream != null) {
                while ((str = reader.readLine()) != null) {
                    buf.append(str + "\n" );
                }
            }
        } catch (IOException e){
            System.err.println("Input-Output error: " +e);
        }
        finally {
            try { inputStream.close(); } catch (Throwable ignore) {}
        }
        System.out.println(buf.toString());
    }
}
