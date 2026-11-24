package com.algovision.service;
import com.algovision.algorithm.searching.*;
import com.algovision.dto.AlgorithmResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
@Service
public class SearchingService {
    @Autowired private LinearSearchAlgorithm linearSearch;
    @Autowired private BinarySearchAlgorithm binarySearch;
    public AlgorithmResponse execute(String algorithm, List<Integer> array, int target) {
        if (array == null || array.isEmpty()) throw new IllegalArgumentException("Array cannot be empty.");
        if (array.size() > 20) throw new IllegalArgumentException("Array size must not exceed 20 elements.");
        int[] arr = array.stream().mapToInt(Integer::intValue).toArray();
        if (!algorithm.equals("linear")) {
            for (int i=1; i<arr.length; i++)
                if (arr[i] < arr[i-1]) throw new IllegalArgumentException("Array must be sorted in ascending order for " + algorithm + " search.");
        }
        return switch (algorithm.toLowerCase()) {
            case "linear" -> AlgorithmResponse.ok("Linear Search", linearSearch.generateSteps(arr, target));
            case "binary" -> AlgorithmResponse.ok("Binary Search", binarySearch.generateSteps(arr, target));
            default       -> throw new IllegalArgumentException("Unknown search algorithm: " + algorithm);
        };
    }
}
