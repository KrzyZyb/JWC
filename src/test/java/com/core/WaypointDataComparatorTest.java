package com.core;

import com.storage.DataRepository;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class WaypointDataComparatorTest {

    WaypointDataComparator waypointDataComparator;
    List<Waypoint> testedJadCollection;

    @Before
    public void setUp() throws Exception {
        Waypoint waypoint1 = new Waypoint("country1","icao1","id1","latitude1","longitude1");
        Waypoint waypoint2 = new Waypoint("country2","icao2","id2","latitude3","longitude2");
        Waypoint waypoint3 = new Waypoint("country3","icao3","id3","latitude3","longitude3");
        testedJadCollection = new ArrayList<>();
        testedJadCollection.add(waypoint1);
        testedJadCollection.add(waypoint2);
        testedJadCollection.add(waypoint3);
        DataRepository repository = new DataRepository();
        repository.getJadRepository().addAll(testedJadCollection);
        waypointDataComparator = new WaypointDataComparator(repository);
    }

    @Test
    public void shouldIndicateThatWaypointIsInJad(){
        //before
        Waypoint waypoint = new Waypoint("country1","icao1","id1","latitude1","longitude1");
        //when
        boolean result = waypointDataComparator.isWaypointInJAD(waypoint);
        assertTrue(result);
    }
    @Test
    public void shouldIndicateThatWaypointIsNotInJad(){
        //before
        Waypoint waypoint = new Waypoint("country4","icao4","id4","latitude4","longitude4");
        //when
        boolean result = waypointDataComparator.isWaypointInJAD(waypoint);
        assertFalse(result);
    }
    @Test
    public void shouldIndicateThatWaypointHasChangedCoordinates(){
        //before
        Waypoint waypoint = new Waypoint("country1","icao1","id1","latitude5","longitude5");
        //when
        boolean result = waypointDataComparator.checkIfCoordinatesHaveChanged(waypoint);
        assertTrue(result);
    }
    @Test
    public void shouldIndicateThatWaypointHasNotChangedCoordinates(){
        //before
        Waypoint waypoint = new Waypoint("country1","icao1","id1","latitude1","longitude1");
        //when
        boolean result = waypointDataComparator.checkIfCoordinatesHaveChanged(waypoint);
        assertFalse(result);
    }

    @Test
    public void shouldIndicateThatWaypointHasChangedId(){
        //before
        Waypoint waypoint = new Waypoint("country1","icao1","id2","latitude1","longitude1");
        //when
        boolean result = waypointDataComparator.checkIfIdHasChanged(waypoint);
        assertTrue(result);
    }
    @Test
    public void shouldIndicateThatWaypointHasNotChangedId(){
        //before
        Waypoint waypoint = new Waypoint("country1","icao1","id1","latitude1","longitude1");
        //when
        boolean result = waypointDataComparator.checkIfIdHasChanged(waypoint);
        assertFalse(result);
    }
}