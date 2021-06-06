package xenoteo.com.github.client;

import java.util.*;

public class DataGenerator {
    public List<Integer> generateList(int size){
        List<Integer> data = new ArrayList<>();
        for (int i = 0; i < size; i++){
            data.add(i);
        }
        return data;
    }

    public Set<Integer> generateSet(int size){
        Set<Integer> data = new HashSet<>();
        for (int i = 0; i < size; i++){
            data.add(i);
        }
        return data;
    }

    public Map<Integer, Integer> generateMap(int size){
        Map<Integer, Integer> data = new HashMap<>();
        for (int i = 0; i < size; i++){
            data.put(i, i);
        }
        return data;
    }
}
