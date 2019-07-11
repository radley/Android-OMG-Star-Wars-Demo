package dev.radley.omgstarwars.utilities

import java.util.ArrayList
import java.util.Comparator

import dev.radley.omgstarwars.models.Film
import dev.radley.omgstarwars.models.SWModel
import dev.radley.omgstarwars.models.Starship

/**
 * Sorting utilities
 */
object SortUtils {


    /**
     * Sort films based on release date
     *
     * @param list
     * @return sorted list
     */
    fun sortFilmsByEpisode(list: ArrayList<Film>): ArrayList<Film> {

        list.sortWith(Comparator { o1, o2 -> o1.episodeId - o2.episodeId })

        return list
    }


    /**
     * A series of sorting filters that alphabetizes and then lists results in this order:
     *
     * 1. Explicit query string match (i.e for "star" query -> "star destroyer", "death star")
     * - sort by index position (i.e "star destroyer" before "death star")
     *
     * 2. Title contains query string (i.e "starfighter", "starship")
     * - sort by index position (i.e "jedi starfighter" before "naboo royal starship")
     *
     * 3. Starship special case (swapi also searches in Starship.model)
     * a. Explicit query string match in .model
     * b. .model title contains query string
     *
     * 4. anything left over (shouldn't happen, but future proof)
     *
     * @param list ArrayList<SWModel>
     * @param query String
     * @return sorted list
    </SWModel> */
    fun sortForBestQueryMatch(list: ArrayList<SWModel>, query: String): ArrayList<SWModel> {

        val sortedList = ArrayList<SWModel>()
        val explicitList = ArrayList<SWModel>()
        val postExplicitList = ArrayList<SWModel>()
        val containList = ArrayList<SWModel>()
        val postContainList = ArrayList<SWModel>()


        // Alphabetize for base hierarchy
        list.sortWith(Comparator { s1, s2 -> s1.title.compareTo(s2.title, ignoreCase = true) })


        // get titles that match explicit query

        for (model in list) {

            var hasMatch = false
            val arr = model.title.split(" ".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()

            for (ss in arr) {
                if (ss.toLowerCase() == query.toLowerCase()) {
                    hasMatch = true
                    break
                }
            }

            if (hasMatch) {
                explicitList.add(model)
            } else {
                postExplicitList.add(model)
            }
        }

        // sort based on position
        if (explicitList.size > 0) {

            explicitList.sortWith(Comparator { str1, str2 ->
                val index1 = str1.title.toLowerCase().indexOf(query.toLowerCase())
                val index2 = str2.title.toLowerCase().indexOf(query.toLowerCase())

                index1 - index2
            })

            sortedList.addAll(explicitList)
        }

        if (postExplicitList.size == 0)
            return sortedList


        // get titles that contain query
        for (model in postExplicitList) {
            if (model.title.toLowerCase().contains(query.toLowerCase())) {
                containList.add(model)
            } else {
                postContainList.add(model)
            }
        }

        // sort based on position
        if (containList.size > 0) {
            containList.sortWith(Comparator { str1, str2 ->
                val index1 = str1.title.toLowerCase().indexOf(query.toLowerCase())
                val index2 = str2.title.toLowerCase().indexOf(query.toLowerCase())
                index1 - index2
            })

            sortedList.addAll(containList)
        }

        if (postContainList.size == 0)
            return sortedList

        // Special case for Starships
        // Swapi search results also return matching Starship.model items
        if (list[0] is Starship) {
            val extras = sortForStarships(postContainList, query)
            sortedList.addAll(extras)
        }

        return sortedList
    }


    /**
     * Sorting specific to `Starship.model` value
     *
     * @param list ArrayList<SWModel>
     * @param query String
     * @return sorted ArrayList<SWModel>
    </SWModel></SWModel> */
    private fun sortForStarships(list: ArrayList<SWModel>, query: String): ArrayList<SWModel> {

        val newList = ArrayList<SWModel>()

        val explicitList = ArrayList<SWModel>()
        val postExplicitList = ArrayList<SWModel>()

        val containList = ArrayList<SWModel>()
        val postContainList = ArrayList<SWModel>()


        // get Starship models that have explicit query word
        for (model in list) {

            var hasMatch = false
            val arr = (model as Starship).model.split(" ".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()

            for (ss in arr) {
                if (ss.toLowerCase() == query.toLowerCase()) {
                    hasMatch = true
                    break
                }
            }

            if (hasMatch) {
                explicitList.add(model)
            } else {
                postExplicitList.add(model)
            }
        }

        // sort based on position
        if (explicitList.size > 0) {
            explicitList.sortWith(Comparator { str1, str2 ->
                val index1 = (str1 as Starship).model.toLowerCase().indexOf(query.toLowerCase())
                val index2 = (str2 as Starship).model.toLowerCase().indexOf(query.toLowerCase())
                index1 - index2
            })

            newList.addAll(explicitList)
        }

        if (postExplicitList.size == 0)
            return newList


        // get titles that contain query
        for (model in postExplicitList) {
            if ((model as Starship).model.toLowerCase().contains(query.toLowerCase())) {
                containList.add(model)
            } else {
                postContainList.add(model)
            }
        }

        // sort based on position
        if (containList.size > 0) {
            containList.sortWith(Comparator { str1, str2 ->
                val index1 = (str1 as Starship).model.toLowerCase().indexOf(query.toLowerCase())
                val index2 = (str2 as Starship).model.toLowerCase().indexOf(query.toLowerCase())
                index1 - index2
            })

            newList.addAll(containList)
        }

        if (postContainList.size == 0)
            return newList

        // add remaining items
        newList.addAll(postContainList)
        return newList
    }
}
