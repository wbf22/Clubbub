package com.freedommuskrats.clubbub.domain;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

public class FakeData {


    private static Person bob = new Person(UUID.randomUUID(), "Bob", "Crawson", 20, 30);
    private static Person kristie = new Person(UUID.randomUUID(), "Kristie", "Johnson", 25, 31);
    private static List<Person> people = new ArrayList<>(Arrays.asList(bob, kristie));

    private static List<Person> initSingletonPerson(Person person) {
        return new ArrayList<>(Collections.singletonList(person));
    }
    private static List<String> initImageUrls() {
        return new ArrayList<>(Arrays.asList(null, null, null, null, null, null, null, null, null, null, null, null));
    }

    private static List<Post> chat = new ArrayList<>(Arrays.asList(new Post("Bob", "Hey guys, do you have any fish?"), new Post("Kristie","No we don't have any fish!")));
    private static List<Post> announcements = new ArrayList<>(Collections.singletonList(new Post("Bob", "Hey guys we're going to get a fish on wednesday")));


    private static Club club1 = new Club("Provo Fishing", null, "We fish around Utah lake", 40, 40, initSingletonPerson(bob), initSingletonPerson(bob), initImageUrls());
    private static Club club2 = new Club("Provo Knitting", null, "We knit sweaters and...", 20, 40, new ArrayList<>(people), initSingletonPerson(bob), initImageUrls());
    private static Club club3 = new Club("BYU ballroom", null, "Ready to learn how to dance, ..", 10, 40, new ArrayList<>(people), initSingletonPerson(kristie), initImageUrls());
    private static Club club4 = new Club("SpringVille Potters", null, "Pottery is our passion.", 40, 10, initSingletonPerson(kristie), initSingletonPerson(kristie), initImageUrls());

    private static List<Club> clubs = new ArrayList<>(Arrays.asList(club1, club2, club3, club4));


    public static List<Club> getFakeClubs() {
        return clubs;
    }

    public static List<Person> getFakePeople() {
        bob.setClubsMemberOf(Arrays.asList(club1, club2, club3));
        kristie.setClubsMemberOf(Arrays.asList(club2, club3, club4));

        bob.setClubsOwned(Arrays.asList(club1, club2));
        kristie.setClubsOwned(Arrays.asList(club3, club4));

        people = new ArrayList<>(Arrays.asList(bob, kristie));
        return people;
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

    public static boolean isMember(Person person, Club club) {
        for (Person p : club.getMembers()) {
            if (p.getId() == person.getId()) {
                return true;
            }
        }
        return false;
    }

    public static boolean isOwner(Person person, Club club) {
        for (Person p : club.getClubOwners()) {
            if (p.getId() == person.getId()) {
                return true;
            }
        }
        return false;
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

    public static List<Person> filterPersonResults(List<Person> clubs, String search, int distance, Person user) {
        List<Person> limited = getPeopleLimitedByDistance(clubs, user.getLatitude(), user.getLongitude(), distance);
        return getPeopleFilterBySearch(limited, search);
    }

    public static List<Person> getPeopleLimitedByDistance(List<Person> people, int userLat, int userLong, int maxDist) {
        List<Person> limited = new ArrayList<>();
        for (Person c : people) {
            int dist = Math.abs(userLat - c.getLatitude()) + Math.abs(userLong - c.getLongitude());
            if (dist < maxDist) {
                limited.add(c);
            }
        }
        return limited;
    }

    public static List<Person> getPeopleFilterBySearch(List<Person> people, String search) {
        List<Person> filtered = new ArrayList<>();
        for (Person c : people) {
            boolean inName = c.getFirstName().toLowerCase().contains(search.toLowerCase());
            boolean inDes = c.getLastName().toLowerCase().contains(search.toLowerCase());
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

    public static void addClub(Club club, Person creator) {
        clubs.add(club);

        club.setClubOwners(new ArrayList<>(Collections.singletonList(creator)));

        List<Club> owned = creator.getClubsOwned();
        owned.add(club);
        creator.setClubsOwned(owned);
    }

    public static Club updateClub(Club original, String title, String description) {
        int i = clubs.indexOf(original);
        clubs.get(i).setName(title);
        clubs.get(i).setDescription(description);

        return clubs.get(i);
    }

    public static Person updatePerson(Person original, String title, String description) {
        int i = people.indexOf(original);
        String[] splits = title.split(" ");
        if (splits.length > 0) {
            people.get(i).setFirstName(splits[0]);
            if (splits.length > 1) {
                people.get(i).setLastName(splits[1]);
            }
        }

        people.get(i).setDescription(description);

        return people.get(i);
    }

    public static void leaveAClub(Person user, Club club) {
        int i = people.indexOf(user);
        people.get(i).getClubsOwned().remove(club);
        people.get(i).getClubsMemberOf().remove(club);

        int j = clubs.indexOf(club);
        clubs.get(j).getClubOwners().remove(user);
        clubs.get(j).getMembers().remove(user);
    }

    public static void joinAClub(Person user, Club club) {
        int i = people.indexOf(user);
        people.get(i).getClubsOwned().add(club);
        people.get(i).getClubsMemberOf().add(club);

        int j = clubs.indexOf(club);
        clubs.get(j).getClubOwners().add(user);
        clubs.get(j).getMembers().add(user);
    }



}
