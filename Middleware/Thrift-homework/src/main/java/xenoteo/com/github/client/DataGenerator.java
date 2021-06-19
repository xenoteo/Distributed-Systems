package xenoteo.com.github.client;

import java.util.*;

/**
 * A class generating data structures filled with the provided number of elements.
 */
public class DataGenerator {

    /**
     * Generates a list filled with the provided number of elements.
     *
     * @param size  the size of the list
     * @return the list filled with the provided number of elements
     */
    public List<Integer> generateList(int size){
        List<Integer> data = new ArrayList<>();
        for (int i = 0; i < size; i++){
            data.add(i);
        }
        return data;
    }

    /**
     * Generates a set filled with the provided number of elements.
     *
     * @param size  the size of the set
     * @return the set filled with the provided number of elements
     */
    public Set<Integer> generateSet(int size){
        Set<Integer> data = new HashSet<>();
        for (int i = 0; i < size; i++){
            data.add(i);
        }
        return data;
    }

    /**
     * Generates a map filled with the provided number of elements.
     *
     * @param size  the size of the map
     * @return the map filled with the provided number of elements
     */
    public Map<Integer, Integer> generateMap(int size){
        Map<Integer, Integer> data = new HashMap<>();
        for (int i = 0; i < size; i++){
            data.put(i, i);
        }
        return data;
    }
}
