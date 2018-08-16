package com.controller;

import com.core.Waypoint;
import com.storage.DataRepository;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class WaypointController {
    private DataRepository dataRepository;

    public WaypointController(DataRepository dataRepository) {
        this.dataRepository = dataRepository;
    }


    @RequestMapping("/api/jadwaypoints")
    public List<Waypoint> getJadWaypoints(){
        return dataRepository.getJadRepository();
    }

    @RequestMapping("/api/opswaypoints")
    public List<Waypoint> getOpsWaypoints(){
        return dataRepository.getOpsRepository();
    }
}
