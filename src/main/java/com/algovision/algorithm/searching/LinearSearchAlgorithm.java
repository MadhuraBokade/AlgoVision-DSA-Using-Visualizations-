package com.algovision.algorithm.searching;
import com.algovision.model.VisualizationStep;
import org.springframework.stereotype.Component;
import java.util.*;
@Component
public class LinearSearchAlgorithm {
    public List<VisualizationStep> generateSteps(int[] arr, int target) {
        List<VisualizationStep> steps = new ArrayList<>();
        int stepNum = 1;
        steps.add(VisualizationStep.builder()
            .stepNumber(stepNum++).operation("INITIAL").array(arr)
            .explanation("Linear Search for target=" + target + ". Checking each element one by one.")
            .extra("target:" + target).build());
        for (int i = 0; i < arr.length; i++) {
            steps.add(VisualizationStep.builder()
                .stepNumber(stepNum++).operation("CHECK")
                .array(arr).activeIndices(List.of(i))
                .pointers(Map.of("current",i))
                .explanation("Checking arr[" + i + "]=" + arr[i] + ". Is it equal to target=" + target + "?")
                .extra("target:" + target).build());
            if (arr[i] == target) {
                steps.add(VisualizationStep.builder()
                    .stepNumber(stepNum).operation("FOUND")
                    .array(arr).activeIndices(List.of(i))
                    .pointers(Map.of("found",i))
                    .explanation("Target " + target + " found at index " + i + "!")
                    .extra("target:" + target + ",foundIndex:" + i)
                    .completed(true).build());
                return steps;
            }
        }
        steps.add(VisualizationStep.builder()
            .stepNumber(stepNum).operation("NOT_FOUND")
            .array(arr)
            .explanation("Target " + target + " not found in the array after checking all " + arr.length + " elements.")
            .extra("target:" + target)
            .completed(true).build());
        return steps;
    }
}
