package com.algovision.algorithm.sorting;
import com.algovision.model.VisualizationStep;
import org.springframework.stereotype.Component;
import java.util.*;
@Component
public class SelectionSortAlgorithm {
    public List<VisualizationStep> generateSteps(int[] input) {
        int[] arr = input.clone();
        int n = arr.length;
        List<VisualizationStep> steps = new ArrayList<>();
        List<Integer> sorted = new ArrayList<>();
        int stepNum = 1;
        steps.add(VisualizationStep.builder()
            .stepNumber(stepNum++).operation("INITIAL").array(arr)
            .activeIndices(List.of()).sortedIndices(new ArrayList<>(sorted))
            .explanation("Starting Selection Sort. Array has " + n + " elements.").build());
        for (int i = 0; i < n - 1; i++) {
            int minIdx = i;
            steps.add(VisualizationStep.builder()
                .stepNumber(stepNum++).operation("FIND_MIN")
                .array(arr).activeIndices(List.of(i))
                .sortedIndices(new ArrayList<>(sorted))
                .pointers(Map.of("i",i,"minIdx",minIdx))
                .explanation("Pass " + (i+1) + ": Assume arr[" + i + "]=" + arr[i] + " is the minimum.").build());
            for (int j = i + 1; j < n; j++) {
                steps.add(VisualizationStep.builder()
                    .stepNumber(stepNum++).operation("COMPARE")
                    .array(arr).activeIndices(List.of(j, minIdx))
                    .sortedIndices(new ArrayList<>(sorted))
                    .pointers(Map.of("i",i,"j",j,"minIdx",minIdx))
                    .explanation("Comparing arr[" + j + "]=" + arr[j] + " with current min arr[" + minIdx + "]=" + arr[minIdx]).build());
                if (arr[j] < arr[minIdx]) {
                    minIdx = j;
                    steps.add(VisualizationStep.builder()
                        .stepNumber(stepNum++).operation("NEW_MIN")
                        .array(arr).activeIndices(List.of(minIdx))
                        .sortedIndices(new ArrayList<>(sorted))
                        .pointers(Map.of("i",i,"minIdx",minIdx))
                        .explanation("New minimum found: arr[" + minIdx + "]=" + arr[minIdx]).build());
                }
            }
            if (minIdx != i) {
                int tmp = arr[i]; arr[i] = arr[minIdx]; arr[minIdx] = tmp;
                steps.add(VisualizationStep.builder()
                    .stepNumber(stepNum++).operation("SWAP")
                    .array(arr).activeIndices(List.of(i, minIdx))
                    .swappedIndices(List.of(i, minIdx))
                    .sortedIndices(new ArrayList<>(sorted))
                    .explanation("Swapping minimum element " + arr[i] + " to position " + i + ".").build());
            }
            sorted.add(i);
            steps.add(VisualizationStep.builder()
                .stepNumber(stepNum++).operation("SORTED")
                .array(arr).sortedIndices(new ArrayList<>(sorted))
                .explanation("Pass " + (i+1) + " complete. arr[" + i + "]=" + arr[i] + " is in its correct position.").build());
        }
        sorted.add(n-1);
        steps.add(VisualizationStep.builder()
            .stepNumber(stepNum).operation("COMPLETE")
            .array(arr).sortedIndices(new ArrayList<>(sorted))
            .explanation("Selection Sort complete! Array sorted in ascending order.")
            .completed(true).build());
        return steps;
    }
}
