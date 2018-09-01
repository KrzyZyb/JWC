package com.controller;

import com.core.Waypoint;
import com.core.WaypointComparsionModel;
import com.core.WaypointDataComparator;
import com.storage.DataRepository;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class WaypointController {
    private DataRepository dataRepository;
    private WaypointDataComparator waypointDataComparator;

    public WaypointController(final DataRepository dataRepository, final WaypointDataComparator waypointDataComparator) {
        this.dataRepository = dataRepository;
        this.waypointDataComparator = waypointDataComparator;
    }


    @RequestMapping("/api/jadwaypoints")
    public List<Waypoint> getJadWaypoints(){
        return dataRepository.getJadRepository();
    }

    @RequestMapping("/api/opswaypoints")
    public List<Waypoint> getOpsWaypoints(){
        return dataRepository.getOpsRepository();
    }

    @RequestMapping("/api/raportwaypoints")
    public List<Waypoint> getWaypointsRaport(){ return waypointDataComparator.getWaypointChanges();
    }

    @RequestMapping("/api/fullraportwaypoints")
    public List<WaypointComparsionModel> getFullWaypointsRaport(){ return waypointDataComparator.getWaypointChangesFull();
    }

    @RequestMapping("/api/updatedopswaypoints")
    public List<Waypoint> getChangedOpsWaypoints(){ return waypointDataComparator.getUpdatedOpsData();
    }
}
