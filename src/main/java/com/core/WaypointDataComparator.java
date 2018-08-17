package com.core;

import com.storage.DataRepository;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class WaypointDataComparator {
    DataRepository dataRepository;
    List<Waypoint> waypointChanges;
    private Logger logger = LoggerFactory.getLogger("Waypoint data comparator:");
    public WaypointDataComparator(DataRepository dataRepository) {
        this.dataRepository = dataRepository;
        waypointChanges = new ArrayList<>();
    }

    public void compareWaypoints(){
        List<Waypoint> opsWaypoints = dataRepository.getOpsRepository();

        for(Waypoint waypoint: opsWaypoints) {
            isWaypointInJAD(waypoint);
            hasCoordinateChanged(waypoint);
            hasIdChanged(waypoint);
        }
    }

    private boolean isWaypointInJAD(Waypoint opsWaypoint){
        if(jadContainsOps(opsWaypoint)) {
            return true;
        }else{
            waypointChanges.add(opsWaypoint);
            opsWaypoint.setStatus("Waypoint not found in JAD data");
            logger.info("Data Processor: Waypoint with id = "+opsWaypoint.getWPT_id()+" not found in JAD data");
            return false;
        }
    }

    private boolean hasCoordinateChanged(Waypoint opsWaypoint){
        List<Waypoint> jadWaypoints = dataRepository.getJadRepository();
        for(Waypoint jadWaypoint:jadWaypoints){
            if(jadWaypoint.getWPT_id().equals(opsWaypoint.getWPT_id())&&!jadWaypoint.getLongxlati().equals(opsWaypoint.getLongxlati())){
                waypointChanges.add(opsWaypoint);
                opsWaypoint.setStatus("Waypoint's coordinates probably changed.");
                logger.info("Data Processor: Waypoint with id = "+opsWaypoint.getWPT_id()+" has different coordinates");
                return true;
            }
        }
        return false;
    }

    private boolean hasIdChanged(Waypoint opsWaypoint) {
        List<Waypoint> jadWaypoints = dataRepository.getJadRepository();
        for (Waypoint jadWaypoint : jadWaypoints) {
            if (!jadWaypoint.getWPT_id().equals(opsWaypoint.getWPT_id()) && jadWaypoint.getLongxlati().equals(opsWaypoint.getLongxlati())) {
                waypointChanges.add(opsWaypoint);
                opsWaypoint.setStatus("Waypoint's id probably changed.");
                logger.info("Data Processor: Waypoint with coordinates = " + opsWaypoint.getLongxlati() + " has changed ID");
                return true;
            }
        }
        return false;
    }

    private boolean jadContainsOps(Waypoint opsWaypoint){
        List<Waypoint> jadWaypoints = dataRepository.getJadRepository();
        for (Waypoint jadWaypoint : jadWaypoints) {
            if (jadWaypoint.getWPT_id().equals(opsWaypoint.getWPT_id()) || jadWaypoint.getLongxlati().equals(opsWaypoint.getLongxlati())) {
                return true;
            }
        }
        return false;
    }

    public List<Waypoint> getWaypointChanges(){
        return waypointChanges;
    }
}
