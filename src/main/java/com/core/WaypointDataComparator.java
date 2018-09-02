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
public class WaypointDataComparator { //TODO: Data not upodated to that from JAD Waypoint in updated column
    DataRepository dataRepository;
    List<Waypoint> waypointChanges;
    List<WaypointComparsionModel> waypointChangesFull;
    List<Waypoint> updatedOpsData = new ArrayList<>();
    private Logger logger = LoggerFactory.getLogger("Waypoint data comparator:");

    public WaypointDataComparator(DataRepository dataRepository) {
        this.dataRepository = dataRepository;
        waypointChanges = new ArrayList<>();
        waypointChangesFull = new ArrayList<>();
    }

    public void compareOpsAndJadData() {
        List<Waypoint> opsWaypoints = dataRepository.getOpsRepository();
        this.updatedOpsData = getOpsWaypointsListCopy(opsWaypoints);
        for (Waypoint opsWaypoint : opsWaypoints) {
            WaypointComparsionModel rowForFullRaportTable = createOpsForComparsionModel(opsWaypoint);
            checkIfOpsWaypointIsInJadFile(opsWaypoint, rowForFullRaportTable);
            checkIfCoordinatesHaveChanged(opsWaypoint, rowForFullRaportTable);
            checkIfIdHasChanged(opsWaypoint, rowForFullRaportTable);
            waypointChangesFull.add(rowForFullRaportTable);
        }
    }

    void checkIfOpsWaypointIsInJadFile(Waypoint opsWaypoint, WaypointComparsionModel rowForFullRaportTable) {
        final String WAYPOINT_NOT_FOUND_STATUS = "Waypoint not found";
        Waypoint jadWaypoint = getJadWaypointFromOpsWaypointData(opsWaypoint);
        if (jadWaypoint == null) {
            opsWaypoint.setStatus("Status: waypoint not found");
            waypointChanges.add(opsWaypoint);
            logger.info("Data Processor: Waypoint with id = " + opsWaypoint.getWPT_id() + " not found in JAD data");
            updateFullRaportRowStatus(WAYPOINT_NOT_FOUND_STATUS, rowForFullRaportTable);
            updateWaypointInUpdatedWaypointsTable(jadWaypoint, opsWaypoint);
        } else {
            addJadForComparsionModel(jadWaypoint, rowForFullRaportTable);
        }
    }

    boolean checkIfCoordinatesHaveChanged(Waypoint opsWaypoint, WaypointComparsionModel rowForFullRaportTable) {
        final String INVALID_COORDINATES_STATUS = "Invalid coordinates";
        List<Waypoint> jadWaypoints = dataRepository.getJadRepository();
        for (Waypoint jadWaypoint : jadWaypoints) {
            if (jadWaypoint.getWPT_id().equals(opsWaypoint.getWPT_id()) && !jadWaypoint.getLongxlati().equals(opsWaypoint.getLongxlati())) {
                opsWaypoint.setStatus("Status: invalid coordinates");
                waypointChanges.add(opsWaypoint);
                logger.info("Data Processor: Waypoint with id = " + opsWaypoint.getWPT_id() + " has different coordinates");
                updateFullRaportRowStatus(INVALID_COORDINATES_STATUS, rowForFullRaportTable);
                updateWaypointInUpdatedWaypointsTable(jadWaypoint, opsWaypoint);
                return true;
            }
        }
        return false;
    }

    boolean checkIfIdHasChanged(Waypoint opsWaypoint, WaypointComparsionModel rowForFullRaportTable) {
        final String INVALID_ID_STATUS = "Invalid ID";
        List<Waypoint> jadWaypoints = dataRepository.getJadRepository();
        for (Waypoint jadWaypoint : jadWaypoints) {
            if (!jadWaypoint.getWPT_id().equals(opsWaypoint.getWPT_id()) && jadWaypoint.getLongxlati().equals(opsWaypoint.getLongxlati())) {
                opsWaypoint.setStatus("Status: invalid ID");
                updateFullRaportRowStatus(INVALID_ID_STATUS, rowForFullRaportTable);
                waypointChanges.add(opsWaypoint);
                logger.info("Data Processor: Waypoint with coordinates = " + opsWaypoint.getLongxlati() + " has changed ID");
                updateWaypointInUpdatedWaypointsTable(jadWaypoint, opsWaypoint);
                return true;
            }
        }
        return false;
    }

    Waypoint getJadWaypointFromOpsWaypointData(Waypoint opsWaypoint) {
        List<Waypoint> jadWaypoints = dataRepository.getJadRepository();
        for (Waypoint jadWaypoint : jadWaypoints) {
            if (jadWaypoint.getWPT_id().equals(opsWaypoint.getWPT_id()) || jadWaypoint.getLongxlati().equals(opsWaypoint.getLongxlati())) {
                return jadWaypoint;
            }
        }
        return null;
    }

    WaypointComparsionModel createOpsForComparsionModel(Waypoint opsWaypoint) {
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

    private void addJadForComparsionModel(Waypoint jadWaypoint, WaypointComparsionModel waypointComparsionModel) {
        waypointComparsionModel.setCountryJAD(jadWaypoint.getCountry());
        waypointComparsionModel.setICAOJAD(jadWaypoint.getICAO());
        waypointComparsionModel.setWPT_idJAD(jadWaypoint.getWPT_id());
        waypointComparsionModel.setLatitudeJAD(jadWaypoint.getLatitude());
        waypointComparsionModel.setLongitudeJAD(jadWaypoint.getLongitude());
        waypointComparsionModel.setLongxlatiJAD(jadWaypoint.getLongxlati());
    }

    public void updateFullRaportRowStatus(String statusUpdate, WaypointComparsionModel fullRaportRow) {
        fullRaportRow.setStatus(fullRaportRow.getStatus().concat(" " + statusUpdate));
    }

    public List<Waypoint> getWaypointChanges() {
        return waypointChanges;
    }

    public List<WaypointComparsionModel> getWaypointChangesFull() {
        return waypointChangesFull;
    }

    public List<Waypoint> getUpdatedOpsData() {
        return updatedOpsData;
    }

    public List<Waypoint> getOpsWaypointsListCopy(List<Waypoint> opsListToCopy) {
        List<Waypoint> waypointsCopy = new ArrayList<>();
        for (Waypoint waypoint : opsListToCopy) {
            Waypoint waypointCopy = new Waypoint(waypoint.getCountry(), waypoint.getICAO(), waypoint.getWPT_id(), waypoint.getLatitude(), waypoint.getLongitude());
            logger.info("Waypoint cloned: " + waypointCopy.getWPT_id());
            waypointsCopy.add(waypointCopy);
        }
        logger.info("Waypoint list cloned: " + waypointsCopy.size());
        return waypointsCopy;
    }

    public Waypoint findWaypointByWptId(String wptId, List<Waypoint> waypoints) {
        for (Waypoint waypoint : waypoints) {
            if (waypoint.getWPT_id().equals(wptId)) {
                return waypoint;
            }
        }
        return null;
    }

    public void updateWaypointInUpdatedWaypointsTable(Waypoint jadWatpoint, Waypoint opsWaypoint) {
        if (jadWatpoint == null) {
            Waypoint updatedWaypoint = findWaypointByWptId(opsWaypoint.getWPT_id(), updatedOpsData);
            updatedOpsData.remove(updatedWaypoint);
            logger.warn("Waypoint removed");
        } else {
            Waypoint updatedWaypoint = findWaypointByWptId(opsWaypoint.getWPT_id(), updatedOpsData);
                updatedWaypoint.setCountry(jadWatpoint.getCountry());
                updatedWaypoint.setICAO(jadWatpoint.getICAO());
                updatedWaypoint.setWPT_id(jadWatpoint.getWPT_id());
                updatedWaypoint.setLatitude(jadWatpoint.getLongitude());
                updatedWaypoint.setLongitude(jadWatpoint.getLongitude());
                updatedWaypoint.setLongxlati(jadWatpoint.getLongxlati());
        }
    }
}
