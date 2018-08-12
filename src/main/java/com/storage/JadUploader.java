package com.storage;

import com.core.Waypoint;
import org.springframework.stereotype.Component;
import java.io.InputStream;
import java.util.List;


@Component
public class JadUploader {

    private JadRepository jadRepository;
    private JadDataProcessor jadDataProcessor;

    public JadUploader(final JadRepository jadRepository, final JadDataProcessor jadDataProcessor){
        this.jadRepository = jadRepository;
        this.jadDataProcessor = jadDataProcessor;
    }

    public void upload(final InputStream inputStream) {
            jadDataProcessor.read(inputStream);
    }
}
