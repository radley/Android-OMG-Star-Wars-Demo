package dev.radley.omgstarwars.utilities;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import dev.radley.omgstarwars.data.Film;
import dev.radley.omgstarwars.data.SWModel;
import dev.radley.omgstarwars.data.Starship;
import timber.log.Timber;

public class SortUtil {

    public static ArrayList<SWModel> sortForBestQueryMatch(ArrayList<SWModel> list, final String query) {

        // films only need to be sorted by episode
        if(list.get(0) instanceof Film) {
            //return list;
            return sortFilmsByEpisode(list);
        }

        // avoid losing list reference for adapters
        ArrayList<SWModel> sortedList = new ArrayList<>();
        ArrayList<SWModel> explicitList = new ArrayList<>();
        ArrayList<SWModel> postExplicitList = new ArrayList<>();
        ArrayList<SWModel> containList = new ArrayList<>();
        ArrayList<SWModel> postContainList = new ArrayList<>();

        // make it alphabetical so matching comparisons still have hierarchy
        Collections.sort(list, new Comparator<SWModel>() {

            public int compare(SWModel s1, SWModel s2) {
                return s1.getTitle().compareToIgnoreCase(s2.getTitle());
            }
        });


        // get titles that match explicit query
        // TODO match for plurals
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

        // starship results include Starship.model match
        if(list.get(0) instanceof Starship) {
            ArrayList<SWModel> extras = sortForStarships(postContainList, query);
            sortedList.addAll(extras);

            Timber.d(" ");
            Timber.d("newList:");
            for (SWModel model : sortedList) {
                Timber.d(model.getTitle() +" (" + model.getId() +")");
            }
        }


        return sortedList;
    }

    public static ArrayList<SWModel> sortFilmsByEpisode(ArrayList<SWModel> list) {
        // just sort by episode
        Collections.sort(list, new Comparator<SWModel>() {
            public int compare(SWModel o1, SWModel o2) {
                return ((Film) o1).episodeId - ((Film) o2).episodeId;
            }
        });

        return list;
    }

    private static ArrayList<SWModel> sortForStarships(ArrayList<SWModel> list, final String query) {

        Timber.d(" ");
        Timber.d("sortStarships(): ");
        for (SWModel model : list) {
            Timber.d(model.getTitle());
        }

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

            Timber.d(" ");
            Timber.d("explicitList:");
            for (SWModel model : explicitList) {
                Timber.d(model.getTitle());
            }

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

            Timber.d(" ");
            Timber.d("containList:");
            for (SWModel model : containList) {
                Timber.d(model.getTitle());
            }

            newList.addAll(containList);
        }

        if (postContainList.size() == 0)
            return newList;


        Timber.d(" ");
        Timber.d("leftovers:");
        for (SWModel model : postContainList) {
            Timber.d(model.getTitle());
        }
        // add remaining items
        newList.addAll(postContainList);
        return newList;
    }
}
