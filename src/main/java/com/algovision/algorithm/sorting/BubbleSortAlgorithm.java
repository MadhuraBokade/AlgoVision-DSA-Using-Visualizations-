package com.algovision.algorithm.sorting;
import com.algovision.model.VisualizationStep;
import org.springframework.stereotype.Component;
import java.util.*;
@Component
public class BubbleSortAlgorithm {
    public List<VisualizationStep> generateSteps(int[] input) {
        int[] arr = input.clone();
        int n = arr.length;
        List<VisualizationStep> steps = new ArrayList<>();
        List<Integer> sorted = new ArrayList<>();
        int stepNum = 1;
        steps.add(VisualizationStep.builder()
            .stepNumber(stepNum++).operation("INITIAL")
            .array(arr).activeIndices(List.of()).sortedIndices(new ArrayList<>(sorted))
            .explanation("Starting Bubble Sort. Array has " + n + " elements.")
            .build());
        for (int i = 0; i < n - 1; i++) {
            for (int j = 0; j < n - i - 1; j++) {
                steps.add(VisualizationStep.builder()
                    .stepNumber(stepNum++).operation("COMPARE")
                    .array(arr).activeIndices(List.of(j, j+1))
                    .sortedIndices(new ArrayList<>(sorted))
                    .pointers(Map.of("i",(int)i,"j",(int)j))
                    .explanation("Pass " + (i+1) + ": Comparing arr[" + j + "]=" + arr[j] + " and arr[" + (j+1) + "]=" + arr[j+1])
                    .build());
                if (arr[j] > arr[j+1]) {
                    int tmp = arr[j]; arr[j] = arr[j+1]; arr[j+1] = tmp;
                    steps.add(VisualizationStep.builder()
                        .stepNumber(stepNum++).operation("SWAP")
                        .array(arr).activeIndices(List.of(j, j+1))
                        .swappedIndices(List.of(j, j+1))
                        .sortedIndices(new ArrayList<>(sorted))
                        .explanation(arr[j+1] + " > " + arr[j] + ", swapping them. arr[" + j + "] and arr[" + (j+1) + "] are exchanged.")
                        .build());
                } else {
                    steps.add(VisualizationStep.builder()
                        .stepNumber(stepNum++).operation("NO_SWAP")
                        .array(arr).activeIndices(List.of(j, j+1))
                        .sortedIndices(new ArrayList<>(sorted))
                        .explanation(arr[j] + " <= " + arr[j+1] + ", no swap needed.")
                        .build());
                }
            }
            sorted.add(0, n - 1 - i);
            steps.add(VisualizationStep.builder()
                .stepNumber(stepNum++).operation("PASS_COMPLETE")
                .array(arr).sortedIndices(new ArrayList<>(sorted))
                .explanation("Pass " + (i+1) + " complete. Element " + arr[n-1-i] + " is now in its correct position.")
                .build());
        }
        sorted.add(0, 0);
        steps.add(VisualizationStep.builder()
            .stepNumber(stepNum).operation("COMPLETE")
            .array(arr).sortedIndices(new ArrayList<>(sorted))
            .explanation("Bubble Sort complete! Array is sorted in ascending order.")
            .completed(true).build());
        return steps;
    }
}
