package com.algovision.service;
import com.algovision.algorithm.sorting.*;
import com.algovision.dto.AlgorithmResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
@Service
public class SortingService {
    @Autowired private BubbleSortAlgorithm bubbleSort;
    @Autowired private SelectionSortAlgorithm selectionSort;
    @Autowired private InsertionSortAlgorithm insertionSort;
    @Autowired private MergeSortAlgorithm mergeSort;
    @Autowired private QuickSortAlgorithm quickSort;
    public AlgorithmResponse execute(String algorithm, List<Integer> array) {
        if (array == null || array.isEmpty()) throw new IllegalArgumentException("Array cannot be empty.");
        if (array.size() > 20) throw new IllegalArgumentException("Array size must not exceed 20 elements.");
        int[] arr = array.stream().mapToInt(Integer::intValue).toArray();
        return switch (algorithm.toLowerCase()) {
            case "bubble"    -> AlgorithmResponse.ok("Bubble Sort",    bubbleSort.generateSteps(arr));
            case "selection" -> AlgorithmResponse.ok("Selection Sort", selectionSort.generateSteps(arr));
            case "insertion" -> AlgorithmResponse.ok("Insertion Sort", insertionSort.generateSteps(arr));
            case "merge"     -> AlgorithmResponse.ok("Merge Sort",     mergeSort.generateSteps(arr));
            case "quick"     -> AlgorithmResponse.ok("Quick Sort",     quickSort.generateSteps(arr));
            default          -> throw new IllegalArgumentException("Unknown algorithm: " + algorithm);
        };
    }
}
