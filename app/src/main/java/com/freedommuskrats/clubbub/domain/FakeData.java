package com.freedommuskrats.clubbub.domain;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FakeData {

    private static Club club1 = new Club("Provo Fishing", "We fish around Utah lake", 40, 40);
    private static Club club2 = new Club("Provo Knitting", "We knit sweaters and...", 20, 40);
    private static Club club3 = new Club("BYU ballroom", "Ready to learn how to dance, ..", 10, 40);
    private static Club club4 = new Club("SpringVille Potters", "Pottery is our passion.", 40, 10);



    public static List<Club> getFakeClubs() {
        return Arrays.asList(club1, club2, club3, club4);
    }



    public static List<Club> getClubsLimitedByDistance(int userLat, int userLong, int maxDist) {
        List<Club> clubs = getFakeClubs();
        List<Club> limited = new ArrayList<>();
        for (Club c : clubs) {
            int dist = Math.abs(userLat - c.getLatitude()) + Math.abs(userLong - c.getLongitude());
            if (dist < maxDist) {
                limited.add(c);
            }
        }
        return limited;
    }


    public static Person defaultPerson() {
        return new Person("Bob", "Crawson", 20, 30);
    }

}
