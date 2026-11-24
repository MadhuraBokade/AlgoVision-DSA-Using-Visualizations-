package com.algovision.algorithm.datastructures;

import com.algovision.model.VisualizationStep;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class ArrayVisualization {
    private static final int MAX_SIZE = 10;

    public List<VisualizationStep> generateInsertSteps(List<Integer> currentList, int index, int value) {
        List<VisualizationStep> steps = new ArrayList<>();
        int[] arr = currentList.stream().mapToInt(Integer::intValue).toArray();
        int stepNum = 1;

        if (arr.length >= MAX_SIZE) {
            steps.add(VisualizationStep.builder()
                .stepNumber(stepNum).operation("OVERFLOW")
                .array(arr)
                .explanation("Array is at maximum demonstration capacity (" + MAX_SIZE + " elements). Cannot insert.")
                .completed(true).build());
            return steps;
        }

        if (index < 0 || index > arr.length) {
            steps.add(VisualizationStep.builder()
                .stepNumber(stepNum).operation("ERROR")
                .array(arr)
                .explanation("Invalid index " + index + ". Valid range is 0 to " + arr.length + ".")
                .completed(true).build());
            return steps;
        }

        steps.add(VisualizationStep.builder()
            .stepNumber(stepNum++).operation("INITIAL")
            .array(arr)
            .pointers(Map.of("insertIdx", index))
            .explanation("Preparing to insert value " + value + " at index " + index + ".")
            .build());

        int[] newArr = new int[arr.length + 1];
        System.arraycopy(arr, 0, newArr, 0, arr.length);

        // Show shifting of elements to the right
        for (int i = arr.length - 1; i >= index; i--) {
            newArr[i + 1] = newArr[i];
            steps.add(VisualizationStep.builder()
                .stepNumber(stepNum++).operation("SHIFT")
                .array(newArr)
                .activeIndices(List.of(i, i + 1))
                .pointers(Map.of("shiftFrom", i, "shiftTo", i + 1))
                .explanation("Shift element " + newArr[i + 1] + " from index " + i + " to index " + (i + 1) + " to make room.")
                .build());
        }

        // Place new value
        newArr[index] = value;
        steps.add(VisualizationStep.builder()
            .stepNumber(stepNum++).operation("INSERT")
            .array(newArr)
            .activeIndices(List.of(index))
            .sortedIndices(List.of(index))
            .pointers(Map.of("inserted", index))
            .explanation("Inserted value " + value + " at index " + index + ".")
            .build());

        steps.add(VisualizationStep.builder()
            .stepNumber(stepNum).operation("COMPLETE")
            .array(newArr)
            .sortedIndices(List.of(index))
            .explanation("Insertion complete! Array size is now " + newArr.length + ".")
            .completed(true).build());

        return steps;
    }

    public List<VisualizationStep> generateDeleteSteps(List<Integer> currentList, int index) {
        List<VisualizationStep> steps = new ArrayList<>();
        int[] arr = currentList.stream().mapToInt(Integer::intValue).toArray();
        int stepNum = 1;

        if (arr.length == 0) {
            steps.add(VisualizationStep.builder()
                .stepNumber(stepNum).operation("UNDERFLOW")
                .array(arr)
                .explanation("Array is empty. Cannot delete.")
                .completed(true).build());
            return steps;
        }

        if (index < 0 || index >= arr.length) {
            steps.add(VisualizationStep.builder()
                .stepNumber(stepNum).operation("ERROR")
                .array(arr)
                .explanation("Invalid index " + index + ". Valid range is 0 to " + (arr.length - 1) + ".")
                .completed(true).build());
            return steps;
        }

        int delVal = arr[index];
        steps.add(VisualizationStep.builder()
            .stepNumber(stepNum++).operation("SELECT")
            .array(arr)
            .activeIndices(List.of(index))
            .pointers(Map.of("deleteIdx", index))
            .explanation("Element " + delVal + " at index " + index + " selected for deletion.")
            .build());

        int[] newArr = new int[arr.length - 1];
        int k = 0;
        for (int i = 0; i < arr.length; i++) {
            if (i != index) {
                newArr[k++] = arr[i];
            }
        }

        steps.add(VisualizationStep.builder()
            .stepNumber(stepNum).operation("COMPLETE")
            .array(newArr)
            .explanation("Deleted " + delVal + " at index " + index + ". Remaining elements shifted left.")
            .completed(true).build());

        return steps;
    }

    public List<VisualizationStep> generateAccessSteps(List<Integer> currentList, int index) {
        List<VisualizationStep> steps = new ArrayList<>();
        int[] arr = currentList.stream().mapToInt(Integer::intValue).toArray();
        int stepNum = 1;

        if (index < 0 || index >= arr.length) {
            steps.add(VisualizationStep.builder()
                .stepNumber(stepNum).operation("ERROR")
                .array(arr)
                .explanation("Array index out of bounds: " + index)
                .completed(true).build());
            return steps;
        }

        steps.add(VisualizationStep.builder()
            .stepNumber(stepNum++).operation("ACCESS")
            .array(arr)
            .activeIndices(List.of(index))
            .pointers(Map.of("access", index))
            .explanation("Direct O(1) Access: arr[" + index + "] = " + arr[index] + ".")
            .completed(true).build());

        return steps;
    }
}
