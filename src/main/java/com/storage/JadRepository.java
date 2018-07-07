package com.storage;

import com.core.Waypoint;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class JadRepository {
    private List<Waypoint> jadRepository = new ArrayList<Waypoint>();

    void save(ArrayList<Waypoint> loadedWaypoints){
        jadRepository.addAll(loadedWaypoints);
    }
    List<Waypoint> getJadRepository(){
        return jadRepository;
    }
}
