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
public class WaypointDataComparator { //TODO: ADD COMPARSION OF WCM NOT TO REPEAT ITSELF
    DataRepository dataRepository;
    List<Waypoint> waypointChanges;
    List<WaypointComparsionModel> waypointChangesFull;
    private Logger logger = LoggerFactory.getLogger("Waypoint data comparator:");
    private static final String EMPTY_VALUE = "";
    public WaypointDataComparator(DataRepository dataRepository) {
        this.dataRepository = dataRepository;
        waypointChanges = new ArrayList<>();
        waypointChangesFull = new ArrayList<>();
    }

    public void compareWaypoints(){
        List<Waypoint> opsWaypoints = dataRepository.getOpsRepository();

        for(Waypoint waypoint: opsWaypoints) {
            isWaypointInJAD(waypoint);
            hasCoordinateChanged(waypoint);
            hasIdChanged(waypoint);
        }
    }

     boolean isWaypointInJAD(Waypoint opsWaypoint){
        if(jadContainsOps(opsWaypoint)) {
            return true;
        }else{
            opsWaypoint.setStatus("Status: waypoint not found");
            waypointChanges.add(opsWaypoint);
            logger.info("Data Processor: Waypoint with id = "+opsWaypoint.getWPT_id()+" not found in JAD data");
            return false;
        }
    }

     boolean hasCoordinateChanged(Waypoint opsWaypoint){
        List<Waypoint> jadWaypoints = dataRepository.getJadRepository();
        for(Waypoint jadWaypoint:jadWaypoints){
            if(jadWaypoint.getWPT_id().equals(opsWaypoint.getWPT_id())&&!jadWaypoint.getLongxlati().equals(opsWaypoint.getLongxlati())){
                opsWaypoint.setStatus("Status: invalid coordinates");
                waypointChanges.add(opsWaypoint);
                waypointChangesFull.add(createWaypointComparsionModel(opsWaypoint,jadWaypoint));
                logger.info("Data Processor: Waypoint with id = "+opsWaypoint.getWPT_id()+" has different coordinates");
                return true;
            }
        }
        return false;
    }

     boolean hasIdChanged(Waypoint opsWaypoint) {
        List<Waypoint> jadWaypoints = dataRepository.getJadRepository();
        for (Waypoint jadWaypoint : jadWaypoints) {
            if (!jadWaypoint.getWPT_id().equals(opsWaypoint.getWPT_id()) && jadWaypoint.getLongxlati().equals(opsWaypoint.getLongxlati())) {
                opsWaypoint.setStatus("Status: invalid ID");
                waypointChangesFull.add(createWaypointComparsionModel(opsWaypoint,jadWaypoint));
                waypointChanges.add(opsWaypoint);
                logger.info("Data Processor: Waypoint with coordinates = " + opsWaypoint.getLongxlati() + " has changed ID");
                return true;
            }
        }
        return false;
    }

     boolean jadContainsOps(Waypoint opsWaypoint){
        List<Waypoint> jadWaypoints = dataRepository.getJadRepository();
        for (Waypoint jadWaypoint : jadWaypoints) {
            if (jadWaypoint.getWPT_id().equals(opsWaypoint.getWPT_id()) || jadWaypoint.getLongxlati().equals(opsWaypoint.getLongxlati())) {
                waypointChangesFull.add(createWaypointComparsionModel(opsWaypoint,jadWaypoint));
                return true;
            }
        }
         waypointChangesFull.add(createWaypointComparsionModel(opsWaypoint,null));
        return false;
    }

    WaypointComparsionModel createWaypointComparsionModel(Waypoint opsWaypoint, Waypoint jadWaypoint){
        if(jadWaypoint==null){
            WaypointComparsionModel waypointComparsionModel = new WaypointComparsionModel();
            waypointComparsionModel.setCountryOPS(opsWaypoint.getCountry());
            waypointComparsionModel.setICAOOPS(opsWaypoint.getICAO());
            waypointComparsionModel.setWPT_idOPS(opsWaypoint.getWPT_id());
            waypointComparsionModel.setLatitudeOPS(opsWaypoint.getLatitude());
            waypointComparsionModel.setLongitudeOPS(opsWaypoint.getLongitude());
            waypointComparsionModel.setLongxlatiOPS(opsWaypoint.getLongxlati());
            waypointComparsionModel.setStatus("Status: waypoint not found");

            waypointComparsionModel.setCountryJAD(EMPTY_VALUE);
            waypointComparsionModel.setICAOJAD(EMPTY_VALUE);
            waypointComparsionModel.setWPT_idJAD(EMPTY_VALUE);
            waypointComparsionModel.setLatitudeJAD(EMPTY_VALUE);
            waypointComparsionModel.setLongitudeJAD(EMPTY_VALUE);
            waypointComparsionModel.setLongxlatiJAD(EMPTY_VALUE);
            return waypointComparsionModel;
        }else{
            WaypointComparsionModel waypointComparsionModel = new WaypointComparsionModel();
            waypointComparsionModel.setCountryOPS(opsWaypoint.getCountry());
            waypointComparsionModel.setICAOOPS(opsWaypoint.getICAO());
            waypointComparsionModel.setWPT_idOPS(opsWaypoint.getWPT_id());
            waypointComparsionModel.setLatitudeOPS(opsWaypoint.getLatitude());
            waypointComparsionModel.setLongitudeOPS(opsWaypoint.getLongitude());
            waypointComparsionModel.setLongxlatiOPS(opsWaypoint.getLongxlati());
            waypointComparsionModel.setStatus(opsWaypoint.getStatus());

            waypointComparsionModel.setCountryJAD(jadWaypoint.getCountry());
            waypointComparsionModel.setICAOJAD(jadWaypoint.getICAO());
            waypointComparsionModel.setWPT_idJAD(jadWaypoint.getWPT_id());
            waypointComparsionModel.setLatitudeJAD(jadWaypoint.getLatitude());
            waypointComparsionModel.setLongitudeJAD(jadWaypoint.getLongitude());
            waypointComparsionModel.setLongxlatiJAD(jadWaypoint.getLongxlati());
            return waypointComparsionModel;
        }
    }

    public List<Waypoint> getWaypointChanges(){
        return waypointChanges;
    }
    public List<WaypointComparsionModel> getWaypointChangesFull(){
        return waypointChangesFull;
    }
}
