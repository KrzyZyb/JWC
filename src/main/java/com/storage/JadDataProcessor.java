package com.storage;

import com.core.Waypoint;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

@Service
@Slf4j
public class JadDataProcessor {
    private static final int SHORT_WAYPOINT_LINE=5;
    private static final int LONG_WAYPOINT_LINE=6;
    private JadRepository jadRepository;
    private Logger logger = LoggerFactory.getLogger("Jad Data Processor:");


    public JadDataProcessor(JadRepository jadRepository) {
        this.jadRepository = jadRepository;
    }

    public void read(final InputStream inputStream){
        String str = "";
        StringBuffer buf = new StringBuffer();
        try{
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
            if (inputStream != null) {
                while ((str = reader.readLine()) != null) {
                    if(isLineValid(str)) {
                        jadRepository.save(createWaypoint(str));
                    }else{
                        continue;
                    }
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
    private Waypoint createWaypoint(String waypointLine){
        String[] wpt =  waypointLine.split(",");
        if (wpt.length==SHORT_WAYPOINT_LINE){
            logger.info(waypointLine);
            return new Waypoint(wpt[0],wpt[1],wpt[2],wpt[3],wpt[4]);
        }else{
            logger.info(waypointLine);
            return new Waypoint(wpt[0],wpt[1],wpt[2],wpt[3],wpt[4],wpt[5]);
        }
    }
    private Boolean isLineValid(String waypointLine){
        int waypointLineLength = waypointLine.split(",").length;
        if(waypointLineLength==SHORT_WAYPOINT_LINE||waypointLineLength==LONG_WAYPOINT_LINE){
            return true;
        }else{
            logger.info("Invalid waypoint line length:" +waypointLineLength);
            return false;
        }
    }
}
