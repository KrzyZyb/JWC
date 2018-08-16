package com.controller;

import com.core.Waypoint;
import com.storage.JadRepository;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class WaypointController {
    private JadRepository jadRepository;

    public WaypointController(JadRepository jadRepository) {
        this.jadRepository = jadRepository;
    }


    @RequestMapping("/api/jadwaypoints")
    public List<Waypoint> getJadWaypoints(){
        return jadRepository.getJadRepository();
    }
}
