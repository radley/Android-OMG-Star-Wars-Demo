package dev.radley.omgstarwars.utilities;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import dev.radley.omgstarwars.models.Film;
import dev.radley.omgstarwars.models.SWModel;
import dev.radley.omgstarwars.models.Starship;

/**
 * Sorting utilities
 */
public class SortUtils {


    /**
     * Sort films based on release date
     *
     * @param list
     * @return sorted list
     */
    public static ArrayList<Film> sortFilmsByEpisode(ArrayList<Film> list) {

        Collections.sort(list, (o1, o2) -> o1.episodeId - o2.episodeId);

        return list;
    }


    /**
     * A series of sorting filters that alphabetizes and then lists results in this order:
     *
     *      1. Explicit query string match (i.e for "star" query -> "star destroyer", "death star")
     *          - sort by index position (i.e "star destroyer" before "death star")
     *
     *      2. Title contains query string (i.e "starfighter", "starship")
     *          - sort by index position (i.e "jedi starfighter" before "naboo royal starship")
     *
     *      3. Starship special case (swapi also searches in Starship.model)
     *          a. Explicit query string match in .model
     *          b. .model title contains query string
     *
     *      4. anything left over (shouldn't happen, but future proof)
     *
     * @param list ArrayList<SWModel>
     * @param query String
     * @return sorted list
     */
    public static ArrayList<SWModel> sortForBestQueryMatch(ArrayList<SWModel> list, final String query) {

        ArrayList<SWModel> sortedList = new ArrayList<>();
        ArrayList<SWModel> explicitList = new ArrayList<>();
        ArrayList<SWModel> postExplicitList = new ArrayList<>();
        ArrayList<SWModel> containList = new ArrayList<>();
        ArrayList<SWModel> postContainList = new ArrayList<>();


        // Alphabetize for base hierarchy
        Collections.sort(list, (s1, s2) -> s1.getTitle().compareToIgnoreCase(s2.getTitle()));


        // get titles that match explicit query
        // TODO match for plurals?

        for (SWModel model : list) {

            boolean hasMatch = false;
            String[] arr = model.getTitle().split(" ");

            for ( String ss : arr) {
                if(ss.toLowerCase().equals(query.toLowerCase())) {
                    hasMatch = true;
                    break;
                }
            }

            if(hasMatch) {
                explicitList.add(model);
            } else {
                postExplicitList.add(model);
            }
        }

        // sort based on position
        if (explicitList.size() > 0) {

            Collections.sort(explicitList, new Comparator<SWModel>() {
                public int compare(SWModel str1, SWModel str2) {

                    int index1 = (str1.getTitle().toLowerCase()).indexOf(query.toLowerCase());
                    int index2 = (str2.getTitle().toLowerCase()).indexOf(query.toLowerCase());

                    return index1 - index2;
                }
            });

            sortedList.addAll(explicitList);
        }

        if (postExplicitList.size() == 0)
            return sortedList;



        // get titles that contain query
        for (SWModel model : postExplicitList) {
            if((model.getTitle()).toLowerCase().contains(query.toLowerCase())) {
                containList.add(model);
            } else {
                postContainList.add(model);
            }
        }

        // sort based on position
        if (containList.size() > 0) {
            Collections.sort(containList, new Comparator<SWModel>() {
                public int compare(SWModel str1, SWModel str2) {

                    int index1 = (str1.getTitle()).toLowerCase().indexOf(query.toLowerCase());
                    int index2 = (str2.getTitle()).toLowerCase().indexOf(query.toLowerCase());
                    return index1 - index2;
                }
            });

            sortedList.addAll(containList);
        }

        if (postContainList.size() == 0)
            return sortedList;

        // Special case for Starships
        // Swapi search results also return matching Starship.model items
        if(list.get(0) instanceof Starship) {
            ArrayList<SWModel> extras = sortForStarships(postContainList, query);
            sortedList.addAll(extras);
        }

        return sortedList;
    }


    /**
     * Sorting specific to <code>Starship.model</code> value
     *
     * @param list ArrayList<SWModel>
     * @param query String
     * @return sorted ArrayList<SWModel>
     */
    private static ArrayList<SWModel> sortForStarships(ArrayList<SWModel> list, final String query) {

        ArrayList<SWModel> newList = new ArrayList<SWModel>();

        ArrayList<SWModel> explicitList = new ArrayList<SWModel>();
        ArrayList<SWModel> postExplicitList = new ArrayList<SWModel>();

        ArrayList<SWModel> containList = new ArrayList<SWModel>();
        ArrayList<SWModel> postContainList = new ArrayList<SWModel>();


        // get Starship models that have explicit query word
        for (SWModel model : list) {

            boolean hasMatch = false;
            String[] arr = ((Starship) model).model.split(" ");

            for ( String ss : arr) {
                if(ss.toLowerCase().equals(query.toLowerCase())) {
                    hasMatch = true;
                    break;
                }
            }

            if(hasMatch) {
                explicitList.add(model);
            } else {
                postExplicitList.add(model);
            }
        }

        // sort based on position
        if (explicitList.size() > 0) {
            Collections.sort(explicitList, new Comparator<SWModel>() {
                public int compare(SWModel str1, SWModel str2) {

                    int index1 = ((Starship) str1).model.toLowerCase().indexOf(query.toLowerCase());
                    int index2 = ((Starship) str2).model.toLowerCase().indexOf(query.toLowerCase());
                    return index1 - index2;
                }
            });

            newList.addAll(explicitList);
        }

        if (postExplicitList.size() == 0)
            return newList;


        // get titles that contain query
        for (SWModel model : postExplicitList) {
            if(((Starship) model).model.toLowerCase().contains(query.toLowerCase())) {
                containList.add(model);
            } else {
                postContainList.add(model);
            }
        }

        // sort based on position
        if (containList.size() > 0) {
            Collections.sort(containList, new Comparator<SWModel>() {
                public int compare(SWModel str1, SWModel str2) {

                    int index1 = ((Starship) str1).model.toLowerCase().indexOf(query.toLowerCase());
                    int index2 = ((Starship) str2).model.toLowerCase().indexOf(query.toLowerCase());
                    return index1 - index2;
                }
            });

            newList.addAll(containList);
        }

        if (postContainList.size() == 0)
            return newList;

        // add remaining items
        newList.addAll(postContainList);
        return newList;
    }
}
