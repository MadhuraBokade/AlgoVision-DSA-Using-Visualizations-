package com.algovision.algorithm.datastructures;
import com.algovision.model.VisualizationStep;
import org.springframework.stereotype.Component;
import java.util.*;
@Component
public class StackVisualization {
    private static final int MAX_SIZE = 8;
    public List<VisualizationStep> generatePushSteps(List<Integer> currentStack, int value) {
        List<VisualizationStep> steps = new ArrayList<>();
        int[] arr = currentStack.stream().mapToInt(Integer::intValue).toArray();
        int stepNum = 1;
        if (arr.length >= MAX_SIZE) {
            steps.add(VisualizationStep.builder()
                .stepNumber(stepNum).operation("OVERFLOW")
                .array(arr)
                .explanation("Stack Overflow! Stack is full (max " + MAX_SIZE + " elements). Cannot push " + value + ".")
                .completed(true).build());
            return steps;
        }
        steps.add(VisualizationStep.builder()
            .stepNumber(stepNum++).operation("PUSH_START")
            .array(arr).pointers(Map.of("top",(int)(arr.length-1)))
            .explanation("Push operation: Inserting " + value + " onto the stack. Current top=" + (arr.length-1) + ".").build());
        int[] newArr = Arrays.copyOf(arr, arr.length + 1);
        newArr[arr.length] = value;
        steps.add(VisualizationStep.builder()
            .stepNumber(stepNum++).operation("PUSH")
            .array(newArr).activeIndices(List.of(newArr.length-1))
            .pointers(Map.of("top",(int)(newArr.length-1)))
            .explanation("Pushed " + value + " onto stack. New top=" + (newArr.length-1) + ".").build());
        steps.add(VisualizationStep.builder()
            .stepNumber(stepNum).operation("PUSH_DONE")
            .array(newArr).sortedIndices(List.of(newArr.length-1))
            .explanation("Push complete. Stack now has " + newArr.length + " element(s). Top element: " + value + ".")
            .completed(true).build());
        return steps;
    }
    public List<VisualizationStep> generatePopSteps(List<Integer> currentStack) {
        List<VisualizationStep> steps = new ArrayList<>();
        int[] arr = currentStack.stream().mapToInt(Integer::intValue).toArray();
        int stepNum = 1;
        if (arr.length == 0) {
            steps.add(VisualizationStep.builder()
                .stepNumber(stepNum).operation("UNDERFLOW")
                .array(arr)
                .explanation("Stack Underflow! Stack is empty. Cannot pop.")
                .completed(true).build());
            return steps;
        }
        int top = arr[arr.length - 1];
        steps.add(VisualizationStep.builder()
            .stepNumber(stepNum++).operation("POP_START")
            .array(arr).activeIndices(List.of(arr.length-1))
            .pointers(Map.of("top",(int)(arr.length-1)))
            .explanation("Pop operation: Removing top element " + top + " from stack.").build());
        int[] newArr = Arrays.copyOf(arr, arr.length - 1);
        steps.add(VisualizationStep.builder()
            .stepNumber(stepNum).operation("POP_DONE")
            .array(newArr)
            .pointers(newArr.length > 0 ? Map.of("top",(int)(newArr.length-1)) : Map.of())
            .explanation("Popped " + top + " from stack. Stack now has " + newArr.length + " element(s).")
            .completed(true).build());
        return steps;
    }
    public List<VisualizationStep> generatePeekSteps(List<Integer> currentStack) {
        List<VisualizationStep> steps = new ArrayList<>();
        int[] arr = currentStack.stream().mapToInt(Integer::intValue).toArray();
        int stepNum = 1;
        if (arr.length == 0) {
            steps.add(VisualizationStep.builder()
                .stepNumber(stepNum).operation("UNDERFLOW")
                .array(arr)
                .explanation("Stack is empty. Nothing to peek.")
                .completed(true).build());
            return steps;
        }
        int top = arr[arr.length - 1];
        steps.add(VisualizationStep.builder()
            .stepNumber(stepNum++).operation("PEEK")
            .array(arr).activeIndices(List.of(arr.length-1))
            .pointers(Map.of("top",(int)(arr.length-1)))
            .explanation("Peek operation: Looking at top element without removing it.").build());
        steps.add(VisualizationStep.builder()
            .stepNumber(stepNum).operation("PEEK_DONE")
            .array(arr).activeIndices(List.of(arr.length-1))
            .explanation("Top element is " + top + ". Stack unchanged.")
            .completed(true).build());
        return steps;
    }
}
