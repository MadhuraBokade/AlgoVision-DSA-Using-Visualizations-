package com.algovision.algorithm.sorting;
import com.algovision.model.VisualizationStep;
import org.springframework.stereotype.Component;
import java.util.*;
@Component
public class InsertionSortAlgorithm {
    public List<VisualizationStep> generateSteps(int[] input) {
        int[] arr = input.clone();
        int n = arr.length;
        List<VisualizationStep> steps = new ArrayList<>();
        List<Integer> sorted = new ArrayList<>();
        int stepNum = 1;
        sorted.add(0);
        steps.add(VisualizationStep.builder()
            .stepNumber(stepNum++).operation("INITIAL").array(arr)
            .sortedIndices(new ArrayList<>(sorted))
            .explanation("Starting Insertion Sort. First element " + arr[0] + " is already sorted.").build());
        for (int i = 1; i < n; i++) {
            int key = arr[i];
            steps.add(VisualizationStep.builder()
                .stepNumber(stepNum++).operation("SELECT_KEY")
                .array(arr).activeIndices(List.of(i))
                .sortedIndices(new ArrayList<>(sorted))
                .pointers(Map.of("key",i,"i",i))
                .explanation("Key = arr[" + i + "] = " + key + ". Insert this into the sorted portion.").build());
            int j = i - 1;
            while (j >= 0 && arr[j] > key) {
                steps.add(VisualizationStep.builder()
                    .stepNumber(stepNum++).operation("COMPARE")
                    .array(arr).activeIndices(List.of(j, j+1))
                    .sortedIndices(new ArrayList<>(sorted))
                    .pointers(Map.of("key",i,"j",j))
                    .explanation("arr[" + j + "]=" + arr[j] + " > key=" + key + ". Shift arr[" + j + "] one position right.").build());
                arr[j+1] = arr[j];
                steps.add(VisualizationStep.builder()
                    .stepNumber(stepNum++).operation("SHIFT")
                    .array(arr).activeIndices(List.of(j, j+1))
                    .sortedIndices(new ArrayList<>(sorted))
                    .explanation("Shifted " + arr[j+1] + " from position " + j + " to " + (j+1) + ".").build());
                j--;
            }
            arr[j+1] = key;
            sorted.add(i);
            steps.add(VisualizationStep.builder()
                .stepNumber(stepNum++).operation("INSERT")
                .array(arr).activeIndices(List.of(j+1))
                .sortedIndices(new ArrayList<>(sorted))
                .explanation("Inserted key=" + key + " at position " + (j+1) + ". Sorted portion is now 0 to " + i + ".").build());
        }
        steps.add(VisualizationStep.builder()
            .stepNumber(stepNum).operation("COMPLETE")
            .array(arr).sortedIndices(new ArrayList<>(sorted))
            .explanation("Insertion Sort complete! All elements are in sorted order.")
            .completed(true).build());
        return steps;
    }
}
