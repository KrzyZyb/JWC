package com.storage;

import org.springframework.stereotype.Component;
import java.io.InputStream;


@Component
public class FileUploader {

    private DataRepository dataRepository;
    private DataProcessor dataProcessor;
    public static final String OPS_FILE="OPS";
    public static final String JAD_FILE="JAD";

    public FileUploader(final DataRepository dataRepository, final DataProcessor dataProcessor){
        this.dataRepository = dataRepository;
        this.dataProcessor = dataProcessor;
    }

    public void uploadJad(final InputStream inputStream) {
        dataProcessor.read(inputStream, JAD_FILE);
    }
    public void uploadOps(final InputStream inputStream) {
        dataProcessor.read(inputStream, OPS_FILE);
    }
}