package com.algovision.algorithm.searching;
import com.algovision.model.VisualizationStep;
import org.springframework.stereotype.Component;
import java.util.*;
@Component
public class BinarySearchAlgorithm {
    public List<VisualizationStep> generateSteps(int[] arr, int target) {
        List<VisualizationStep> steps = new ArrayList<>();
        int stepNum = 1;
        steps.add(VisualizationStep.builder()
            .stepNumber(stepNum++).operation("INITIAL").array(arr)
            .pointers(Map.of("low",0,"high",arr.length-1))
            .explanation("Binary Search for target=" + target + ". Array must be sorted. Low=0, High=" + (arr.length-1))
            .extra("target:" + target).build());
        int low = 0, high = arr.length - 1;
        while (low <= high) {
            int mid = low + (high - low) / 2;
            steps.add(VisualizationStep.builder()
                .stepNumber(stepNum++).operation("SET_MID")
                .array(arr).activeIndices(List.of(mid))
                .pointers(Map.of("low",low,"mid",mid,"high",high))
                .explanation("Low=" + low + ", High=" + high + ". Mid = (" + low + "+" + high + ")/2 = " + mid + ". Check arr[" + mid + "]=" + arr[mid] + ".")
                .extra("target:" + target + ",low:" + low + ",high:" + high + ",mid:" + mid).build());
            if (arr[mid] == target) {
                steps.add(VisualizationStep.builder()
                    .stepNumber(stepNum).operation("FOUND")
                    .array(arr).activeIndices(List.of(mid))
                    .pointers(Map.of("low",low,"mid",mid,"high",high,"found",mid))
                    .explanation("arr[" + mid + "]=" + arr[mid] + " == target=" + target + ". Found!")
                    .extra("target:" + target + ",foundIndex:" + mid)
                    .completed(true).build());
                return steps;
            } else if (arr[mid] < target) {
                steps.add(VisualizationStep.builder()
                    .stepNumber(stepNum++).operation("ELIMINATE_LEFT")
                    .array(arr).activeIndices(List.of(mid))
                    .pointers(Map.of("low",mid+1,"high",high))
                    .explanation("arr[" + mid + "]=" + arr[mid] + " < target=" + target + ". Target is in RIGHT half. Eliminate left. New Low=" + (mid+1) + ".")
                    .extra("target:" + target + ",eliminated:0-" + mid).build());
                low = mid + 1;
            } else {
                steps.add(VisualizationStep.builder()
                    .stepNumber(stepNum++).operation("ELIMINATE_RIGHT")
                    .array(arr).activeIndices(List.of(mid))
                    .pointers(Map.of("low",low,"high",mid-1))
                    .explanation("arr[" + mid + "]=" + arr[mid] + " > target=" + target + ". Target is in LEFT half. Eliminate right. New High=" + (mid-1) + ".")
                    .extra("target:" + target + ",eliminated:" + mid + "-" + high).build());
                high = mid - 1;
            }
        }
        steps.add(VisualizationStep.builder()
            .stepNumber(stepNum).operation("NOT_FOUND")
            .array(arr)
            .explanation("Binary Search complete. Target=" + target + " is not in the array.")
            .extra("target:" + target)
            .completed(true).build());
        return steps;
    }
}
