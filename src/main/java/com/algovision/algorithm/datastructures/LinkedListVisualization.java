package com.algovision.algorithm.datastructures;

import com.algovision.model.VisualizationStep;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class LinkedListVisualization {
    private static final int MAX_SIZE = 8;

    public List<VisualizationStep> generateInsertSteps(List<Integer> currentNodes, int position, int value) {
        List<VisualizationStep> steps = new ArrayList<>();
        int[] arr = currentNodes.stream().mapToInt(Integer::intValue).toArray();
        int stepNum = 1;

        if (arr.length >= MAX_SIZE) {
            steps.add(VisualizationStep.builder()
                .stepNumber(stepNum).operation("OVERFLOW")
                .array(arr)
                .explanation("Linked list has reached maximum display nodes (" + MAX_SIZE + ").")
                .completed(true).build());
            return steps;
        }

        int targetPos = Math.max(0, Math.min(position, arr.length));

        steps.add(VisualizationStep.builder()
            .stepNumber(stepNum++).operation("CREATE_NODE")
            .array(arr)
            .explanation("Creating new Node with data = " + value + " and next = null.")
            .extra("newNode:" + value)
            .build());

        if (targetPos == 0) {
            steps.add(VisualizationStep.builder()
                .stepNumber(stepNum++).operation("INSERT_HEAD")
                .array(arr)
                .pointers(Map.of("head", 0))
                .explanation("Inserting at HEAD. New node's next points to old head (" + (arr.length > 0 ? arr[0] : "null") + ").")
                .build());
        } else {
            for (int i = 0; i < targetPos; i++) {
                steps.add(VisualizationStep.builder()
                    .stepNumber(stepNum++).operation("TRAVERSE")
                    .array(arr)
                    .activeIndices(List.of(i))
                    .pointers(Map.of("current", i))
                    .explanation("Traversing list to find insertion point. At node " + arr[i] + " (pos " + i + ").")
                    .build());
            }
        }

        int[] newArr = new int[arr.length + 1];
        for (int i = 0, k = 0; i < newArr.length; i++) {
            if (i == targetPos) {
                newArr[i] = value;
            } else {
                newArr[i] = arr[k++];
            }
        }

        steps.add(VisualizationStep.builder()
            .stepNumber(stepNum).operation("COMPLETE")
            .array(newArr)
            .activeIndices(List.of(targetPos))
            .sortedIndices(List.of(targetPos))
            .pointers(Map.of("inserted", targetPos))
            .explanation("Node " + value + " successfully linked into the list at position " + targetPos + "!")
            .completed(true).build());

        return steps;
    }

    public List<VisualizationStep> generateDeleteSteps(List<Integer> currentNodes, int position) {
        List<VisualizationStep> steps = new ArrayList<>();
        int[] arr = currentNodes.stream().mapToInt(Integer::intValue).toArray();
        int stepNum = 1;

        if (arr.length == 0) {
            steps.add(VisualizationStep.builder()
                .stepNumber(stepNum).operation("UNDERFLOW")
                .array(arr)
                .explanation("Linked list is empty. Cannot delete.")
                .completed(true).build());
            return steps;
        }

        int delPos = Math.max(0, Math.min(position, arr.length - 1));

        for (int i = 0; i <= delPos; i++) {
            steps.add(VisualizationStep.builder()
                .stepNumber(stepNum++).operation("TRAVERSE")
                .array(arr)
                .activeIndices(List.of(i))
                .pointers(Map.of("current", i))
                .explanation("Traversing to target node. At node [" + arr[i] + "] at position " + i + ".")
                .build());
        }

        int delVal = arr[delPos];
        int[] newArr = new int[arr.length - 1];
        for (int i = 0, k = 0; i < arr.length; i++) {
            if (i != delPos) newArr[k++] = arr[i];
        }

        steps.add(VisualizationStep.builder()
            .stepNumber(stepNum).operation("COMPLETE")
            .array(newArr)
            .explanation("Deleted node " + delVal + ". Updated previous node's next pointer to bypass it.")
            .completed(true).build());

        return steps;
    }

    public List<VisualizationStep> generateTraverseSteps(List<Integer> currentNodes) {
        List<VisualizationStep> steps = new ArrayList<>();
        int[] arr = currentNodes.stream().mapToInt(Integer::intValue).toArray();
        int stepNum = 1;

        if (arr.length == 0) {
            steps.add(VisualizationStep.builder()
                .stepNumber(stepNum).operation("EMPTY")
                .array(arr)
                .explanation("Linked list is empty (Head == null).")
                .completed(true).build());
            return steps;
        }

        for (int i = 0; i < arr.length; i++) {
            steps.add(VisualizationStep.builder()
                .stepNumber(stepNum++).operation("VISIT")
                .array(arr)
                .activeIndices(List.of(i))
                .pointers(Map.of("temp", i))
                .explanation("Visiting node [" + arr[i] + "] at index " + i + ". Follow 'next' pointer.")
                .build());
        }

        steps.add(VisualizationStep.builder()
            .stepNumber(stepNum).operation("COMPLETE")
            .array(arr)
            .explanation("Reached end of list (next == null). Traversal complete.")
            .completed(true).build());

        return steps;
    }
}
