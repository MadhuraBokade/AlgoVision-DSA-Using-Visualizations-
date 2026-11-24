package com.algovision.algorithm.sorting;
import com.algovision.model.VisualizationStep;
import org.springframework.stereotype.Component;
import java.util.*;
@Component
public class MergeSortAlgorithm {
    private List<VisualizationStep> steps;
    private int stepNum;
    public List<VisualizationStep> generateSteps(int[] input) {
        int[] arr = input.clone();
        steps = new ArrayList<>();
        stepNum = 1;
        steps.add(VisualizationStep.builder()
            .stepNumber(stepNum++).operation("INITIAL").array(arr)
            .explanation("Starting Merge Sort on array of " + arr.length + " elements. Divide and conquer!").build());
        mergeSort(arr, 0, arr.length - 1);
        steps.add(VisualizationStep.builder()
            .stepNumber(stepNum).operation("COMPLETE").array(arr)
            .sortedIndices(rangeList(0, arr.length))
            .explanation("Merge Sort complete! All subarrays merged into sorted array.")
            .completed(true).build());
        return steps;
    }
    private void mergeSort(int[] arr, int l, int r) {
        if (l >= r) return;
        int m = l + (r - l) / 2;
        steps.add(VisualizationStep.builder()
            .stepNumber(stepNum++).operation("SPLIT")
            .array(arr).activeIndices(rangeList(l, r+1))
            .pointers(Map.of("left",l,"mid",m,"right",r))
            .phase("Splitting [" + l + ".." + r + "] -> [" + l + ".." + m + "] and [" + (m+1) + ".." + r + "]")
            .explanation("Dividing subarray [" + l + ".." + r + "] at midpoint " + m + ".").build());
        mergeSort(arr, l, m);
        mergeSort(arr, m + 1, r);
        merge(arr, l, m, r);
    }
    private void merge(int[] arr, int l, int m, int r) {
        int n1 = m - l + 1, n2 = r - m;
        int[] left = Arrays.copyOfRange(arr, l, m+1);
        int[] right = Arrays.copyOfRange(arr, m+1, r+1);
        steps.add(VisualizationStep.builder()
            .stepNumber(stepNum++).operation("MERGE_START")
            .array(arr).activeIndices(rangeList(l, r+1))
            .pointers(Map.of("left",l,"mid",m,"right",r))
            .phase("Merging [" + l + ".." + m + "] and [" + (m+1) + ".." + r + "]")
            .explanation("Merging left subarray [" + l + ".." + m + "] with right [" + (m+1) + ".." + r + "].").build());
        int i=0, j=0, k=l;
        while (i < n1 && j < n2) {
            steps.add(VisualizationStep.builder()
                .stepNumber(stepNum++).operation("MERGE_COMPARE")
                .array(arr).activeIndices(List.of(l+i, m+1+j))
                .pointers(Map.of("li",(int)(l+i),"ri",(int)(m+1+j),"k",(int)k))
                .explanation("Comparing left[" + i + "]=" + left[i] + " with right[" + j + "]=" + right[j] + ".").build());
            if (left[i] <= right[j]) { arr[k++] = left[i++]; }
            else { arr[k++] = right[j++]; }
            steps.add(VisualizationStep.builder()
                .stepNumber(stepNum++).operation("MERGE_PLACE")
                .array(arr).activeIndices(List.of(k-1))
                .explanation("Placed " + arr[k-1] + " at position " + (k-1) + ".").build());
        }
        while (i < n1) { arr[k++] = left[i++]; }
        while (j < n2) { arr[k++] = right[j++]; }
        steps.add(VisualizationStep.builder()
            .stepNumber(stepNum++).operation("MERGE_DONE")
            .array(arr).activeIndices(rangeList(l, r+1))
            .explanation("Subarray [" + l + ".." + r + "] merged and sorted.").build());
    }
    private List<Integer> rangeList(int from, int to) {
        List<Integer> lst = new ArrayList<>();
        for (int i=from; i<to; i++) lst.add(i);
        return lst;
    }
}
