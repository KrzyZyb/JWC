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
public class DataProcessor {
    private static final int SHORT_WAYPOINT_LINE=5;
    private static final int LONG_WAYPOINT_LINE=6;
    public static final String OPS_FILE="OPS";
    public static final String JAD_FILE="JAD";
    private DataRepository dataRepository;
    private Logger logger = LoggerFactory.getLogger("Jad Data Processor:");


    public DataProcessor(DataRepository dataRepository) {
        this.dataRepository = dataRepository;
    }

    public void read(final InputStream inputStream, final String dataType){
        String str = "";
        StringBuffer buf = new StringBuffer();
        try{
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
            if (inputStream != null) {
                while ((str = reader.readLine()) != null) {
                    if(isLineValid(str)&&dataType.equals(OPS_FILE)) {
                        dataRepository.saveOps(createWaypoint(str,OPS_FILE));
                    }else if(isLineValid(str)&&dataType.equals(JAD_FILE)){
                        dataRepository.saveJad(createWaypoint(str,JAD_FILE));
                    }
                    else{
                        continue;
                    }
                }
            }
        } catch (IOException e){
            System.err.println("Input-Output error: " +e);
        }
        finally {
            try {
                int repositoryElementsCounter=0;
                if(dataType.equals(JAD_FILE)){
                    repositoryElementsCounter = dataRepository.getJadRepository().size();
                }else{
                    repositoryElementsCounter = dataRepository.getOpsRepository().size();
                }
                logger.info("Data Processor: " +repositoryElementsCounter +" new "+dataType +" waypoints added.");
                inputStream.close(); } catch (Throwable ignore) {}
        }
        System.out.println(buf.toString());
    }
    private Waypoint createWaypoint(String waypointLine, String waypointType){
        String[] wpt =  waypointLine.split(",");
        if (wpt.length==SHORT_WAYPOINT_LINE){
            Waypoint waypoint = new Waypoint(wpt[0],wpt[1],wpt[2],wpt[3],wpt[4]);
            return waypoint;
        }else{
            Waypoint waypoint = new Waypoint(wpt[0],wpt[1],wpt[2],wpt[3],wpt[4],wpt[5]);
            return waypoint;
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
