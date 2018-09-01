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
    List<WaypointComparsionModel> waypointChangesFull;
    private Logger logger = LoggerFactory.getLogger("Waypoint data comparator:");

    public WaypointDataComparator(DataRepository dataRepository) {
        this.dataRepository = dataRepository;
        waypointChanges = new ArrayList<>();
        waypointChangesFull = new ArrayList<>();
    }

    public void compareWaypoints(){
        List<Waypoint> opsWaypoints = dataRepository.getOpsRepository();
        for(Waypoint opsWaypoint: opsWaypoints) {
            WaypointComparsionModel fullRaportRow = createOpsForComparsionModel(opsWaypoint);
            checkIfOpsIsInJad(opsWaypoint, fullRaportRow);
            hasCoordinateChanged(opsWaypoint, fullRaportRow);
            hasIdChanged(opsWaypoint, fullRaportRow);
            waypointChangesFull.add(fullRaportRow);
        }
    }

     void checkIfOpsIsInJad(Waypoint opsWaypoint, WaypointComparsionModel fullRaportRow){
        final String WAYPOINT_NOT_FOUND_STATUS = "Waypoint not found";
        Waypoint jadWaypoint = getJadForOps(opsWaypoint);
        if(jadWaypoint==null) {
            opsWaypoint.setStatus("Status: waypoint not found");
            waypointChanges.add(opsWaypoint);
            logger.info("Data Processor: Waypoint with id = " + opsWaypoint.getWPT_id() + " not found in JAD data");
            updateFullRaportRowStatus(WAYPOINT_NOT_FOUND_STATUS, fullRaportRow);
        }else{
            addJadForComparsionModel(jadWaypoint, fullRaportRow);
        }
    }

     boolean hasCoordinateChanged(Waypoint opsWaypoint, WaypointComparsionModel fullRaportRow){
        final String INVALID_COORDINATES_STATUS = "Invalid coordinates";
        List<Waypoint> jadWaypoints = dataRepository.getJadRepository();
        for(Waypoint jadWaypoint:jadWaypoints){
            if(jadWaypoint.getWPT_id().equals(opsWaypoint.getWPT_id())&&!jadWaypoint.getLongxlati().equals(opsWaypoint.getLongxlati())){
                opsWaypoint.setStatus("Status: invalid coordinates");
                waypointChanges.add(opsWaypoint);
                logger.info("Data Processor: Waypoint with id = "+opsWaypoint.getWPT_id()+" has different coordinates");
                updateFullRaportRowStatus(INVALID_COORDINATES_STATUS, fullRaportRow);
                return true;
            }
        }
        return false;
    }

     boolean hasIdChanged(Waypoint opsWaypoint, WaypointComparsionModel fullRaportRow) {
        final String INVALID_ID_STATUS = "Invalid ID";
        List<Waypoint> jadWaypoints = dataRepository.getJadRepository();
        for (Waypoint jadWaypoint : jadWaypoints) {
            if (!jadWaypoint.getWPT_id().equals(opsWaypoint.getWPT_id()) && jadWaypoint.getLongxlati().equals(opsWaypoint.getLongxlati())) {
                opsWaypoint.setStatus("Status: invalid ID");
                updateFullRaportRowStatus(INVALID_ID_STATUS, fullRaportRow);
                waypointChanges.add(opsWaypoint);
                logger.info("Data Processor: Waypoint with coordinates = " + opsWaypoint.getLongxlati() + " has changed ID");
                return true;
            }
        }
        return false;
    }

     Waypoint getJadForOps (Waypoint opsWaypoint){
        List<Waypoint> jadWaypoints = dataRepository.getJadRepository();
        for (Waypoint jadWaypoint : jadWaypoints) {
            if (jadWaypoint.getWPT_id().equals(opsWaypoint.getWPT_id()) || jadWaypoint.getLongxlati().equals(opsWaypoint.getLongxlati())) {
                return jadWaypoint;
            }
        }
        return null;
    }

    WaypointComparsionModel createOpsForComparsionModel(Waypoint opsWaypoint){
        WaypointComparsionModel waypointComparsionModel = new WaypointComparsionModel();
        waypointComparsionModel.setCountryOPS(opsWaypoint.getCountry());
        waypointComparsionModel.setICAOOPS(opsWaypoint.getICAO());
        waypointComparsionModel.setWPT_idOPS(opsWaypoint.getWPT_id());
        waypointComparsionModel.setLatitudeOPS(opsWaypoint.getLatitude());
        waypointComparsionModel.setLongitudeOPS(opsWaypoint.getLongitude());
        waypointComparsionModel.setLongxlatiOPS(opsWaypoint.getLongxlati());
        waypointComparsionModel.setStatus("");
        return waypointComparsionModel;
    }

    private void addJadForComparsionModel(Waypoint jadWaypoint, WaypointComparsionModel waypointComparsionModel){
        waypointComparsionModel.setCountryJAD(jadWaypoint.getCountry());
        waypointComparsionModel.setICAOJAD(jadWaypoint.getICAO());
        waypointComparsionModel.setWPT_idJAD(jadWaypoint.getWPT_id());
        waypointComparsionModel.setLatitudeJAD(jadWaypoint.getLatitude());
        waypointComparsionModel.setLongitudeJAD(jadWaypoint.getLongitude());
        waypointComparsionModel.setLongxlatiJAD(jadWaypoint.getLongxlati());
    }

    public void updateFullRaportRowStatus(String statusUpdate, WaypointComparsionModel fullRaportRow){
        fullRaportRow.setStatus(fullRaportRow.getStatus().concat(" "+statusUpdate));
    }

    public List<Waypoint> getWaypointChanges(){
        return waypointChanges;
    }
    public List<WaypointComparsionModel> getWaypointChangesFull(){
        return waypointChangesFull;
    }
}
