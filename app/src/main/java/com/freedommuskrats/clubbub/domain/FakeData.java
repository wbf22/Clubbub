package com.freedommuskrats.clubbub.domain;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

public class FakeData {


    private static Person bob = new Person(UUID.randomUUID(), "Bob", "Crawson", 20, 30);
    private static Person kristie = new Person(UUID.randomUUID(), "Kristie", "Johnson", 25, 31);
    private static List<Person> initAllPeople() {
        return new ArrayList<>(Arrays.asList(bob, kristie));
    }
    private static List<Person> initSingletonPerson(Person person) {
        return new ArrayList<>(Collections.singletonList(person));
    }
    private static List<String> initImageUrls() {
        return new ArrayList<>(Arrays.asList(null, null, null, null, null, null, null, null, null, null, null, null));
    }

    private static List<Post> chat = Arrays.asList(new Post("Bob", "Hey guys, do you have any fish?"), new Post("Kristie","No we don't have any fish!"));
    private static List<Post> announcements = Collections.singletonList(new Post("Bob", "Hey guys we're going to get a fish on wednesday"));


    private static Club club1 = new Club("Provo Fishing", null, "We fish around Utah lake", 40, 40, initSingletonPerson(bob), initSingletonPerson(bob), initImageUrls());
    private static Club club2 = new Club("Provo Knitting", null, "We knit sweaters and...", 20, 40, initAllPeople(), initSingletonPerson(bob), initImageUrls());
    private static Club club3 = new Club("BYU ballroom", null, "Ready to learn how to dance, ..", 10, 40, initAllPeople(), initSingletonPerson(kristie), initImageUrls());
    private static Club club4 = new Club("SpringVille Potters", null, "Pottery is our passion.", 40, 10, initSingletonPerson(kristie), initSingletonPerson(kristie), initImageUrls());



    public static List<Club> getFakeClubs() {
        return Arrays.asList(club1, club2, club3, club4);
    }

    public static List<Post> getChat() { return chat; }

    public static List<Post> getAnnouncements() { return announcements; }

    public static List<Club> getClubsPersonIsMember(Person person) {
        List<Club> selected = new ArrayList<>();
        for (Club c : getFakeClubs()) {
            for (Person member : c.getMembers()) {
                if (member.getId() == person.getId()) {
                    selected.add(c);
                }
            }

        }
        return selected;
    }

    public static List<Club> getClubsPersonIsOwner(Person person) {
        List<Club> selected = new ArrayList<>();
        for (Club c : getFakeClubs()) {
            for (Person member : c.getClubOwners()) {
                if (member.getId() == person.getId()) {
                    selected.add(c);
                }
            }
        }
        return selected;
    }

    public static Club getClubByName(String clubName) {
        List<Club> clubs = getFakeClubs();
        for (Club c : clubs) {
            if (c.getName().equals(clubName)) {
                return c;
            }
        }
        return null;
    }

    public static List<Club> filterResults(List<Club> clubs, String search, int distance, Person user) {
        List<Club> limited = getClubsLimitedByDistance(clubs, user.getLatitude(), user.getLongitude(), distance);
        return getClubsFilterBySearch(limited, search);
    }

    public static List<Club> getClubsLimitedByDistance(List<Club> clubs, int userLat, int userLong, int maxDist) {
        List<Club> limited = new ArrayList<>();
        for (Club c : clubs) {
            int dist = Math.abs(userLat - c.getLatitude()) + Math.abs(userLong - c.getLongitude());
            if (dist < maxDist) {
                limited.add(c);
            }
        }
        return limited;
    }

    public static List<Club> getClubsFilterBySearch(List<Club> clubs, String search) {
        List<Club> filtered = new ArrayList<>();
        for (Club c : clubs) {
            boolean inName = c.getName().toLowerCase().contains(search.toLowerCase());
            boolean inDes = c.getDescription().toLowerCase().contains(search.toLowerCase());
            if (inName) {
                filtered.add(0, c);
            } else {
                if (inDes) {
                    filtered.add(c);
                }
            }

        }
        return filtered;
    }

    public static Person defaultPerson() {
        return bob;
    }

}
