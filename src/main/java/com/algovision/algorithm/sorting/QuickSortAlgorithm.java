package com.algovision.algorithm.sorting;
import com.algovision.model.VisualizationStep;
import org.springframework.stereotype.Component;
import java.util.*;
@Component
public class QuickSortAlgorithm {
    private List<VisualizationStep> steps;
    private int stepNum;
    public List<VisualizationStep> generateSteps(int[] input) {
        int[] arr = input.clone();
        steps = new ArrayList<>();
        stepNum = 1;
        List<Integer> sorted = new ArrayList<>();
        steps.add(VisualizationStep.builder()
            .stepNumber(stepNum++).operation("INITIAL").array(arr)
            .explanation("Starting Quick Sort. We pick a pivot and partition around it.").build());
        quickSort(arr, 0, arr.length - 1, sorted);
        steps.add(VisualizationStep.builder()
            .stepNumber(stepNum).operation("COMPLETE").array(arr)
            .sortedIndices(rangeList(0, arr.length))
            .explanation("Quick Sort complete! Array is sorted.")
            .completed(true).build());
        return steps;
    }
    private void quickSort(int[] arr, int low, int high, List<Integer> sorted) {
        if (low < high) {
            int pi = partition(arr, low, high, sorted);
            sorted.add(pi);
            quickSort(arr, low, pi - 1, sorted);
            quickSort(arr, pi + 1, high, sorted);
        } else if (low == high) {
            sorted.add(low);
        }
    }
    private int partition(int[] arr, int low, int high, List<Integer> sorted) {
        int pivot = arr[high];
        steps.add(VisualizationStep.builder()
            .stepNumber(stepNum++).operation("SELECT_PIVOT")
            .array(arr).activeIndices(List.of(high))
            .pointers(Map.of("pivot",high,"low",low,"high",high))
            .explanation("Pivot = arr[" + high + "] = " + pivot + ". Partition range [" + low + ".." + high + "].").build());
        int i = low - 1;
        for (int j = low; j < high; j++) {
            steps.add(VisualizationStep.builder()
                .stepNumber(stepNum++).operation("COMPARE")
                .array(arr).activeIndices(List.of(j, high))
                .pointers(Map.of("pivot",high,"i",(int)(i<low?low:i),"j",(int)j))
                .explanation("Compare arr[" + j + "]=" + arr[j] + " with pivot=" + pivot + ".").build());
            if (arr[j] <= pivot) {
                i++;
                if (i != j) {
                    int tmp = arr[i]; arr[i] = arr[j]; arr[j] = tmp;
                    steps.add(VisualizationStep.builder()
                        .stepNumber(stepNum++).operation("SWAP")
                        .array(arr).activeIndices(List.of(i, j))
                        .swappedIndices(List.of(i, j))
                        .explanation("arr[" + j + "]=" + arr[i] + " <= pivot. Swap arr[" + i + "] and arr[" + j + "].").build());
                }
            }
        }
        int tmp = arr[i+1]; arr[i+1] = arr[high]; arr[high] = tmp;
        steps.add(VisualizationStep.builder()
            .stepNumber(stepNum++).operation("PIVOT_PLACED")
            .array(arr).activeIndices(List.of(i+1))
            .explanation("Pivot " + pivot + " placed at its correct position " + (i+1) + ". All left elements <= pivot, all right elements > pivot.").build());
        return i + 1;
    }
    private List<Integer> rangeList(int from, int to) {
        List<Integer> lst = new ArrayList<>();
        for (int i=from; i<to; i++) lst.add(i);
        return lst;
    }
}
