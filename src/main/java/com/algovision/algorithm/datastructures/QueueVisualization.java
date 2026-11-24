package com.algovision.algorithm.datastructures;
import com.algovision.model.VisualizationStep;
import org.springframework.stereotype.Component;
import java.util.*;
@Component
public class QueueVisualization {
    private static final int MAX_SIZE = 8;
    public List<VisualizationStep> generateEnqueueSteps(List<Integer> currentQueue, int value, boolean isCircular) {
        List<VisualizationStep> steps = new ArrayList<>();
        int[] arr = currentQueue.stream().mapToInt(Integer::intValue).toArray();
        int stepNum = 1;
        if (arr.length >= MAX_SIZE) {
            steps.add(VisualizationStep.builder()
                .stepNumber(stepNum).operation("OVERFLOW")
                .array(arr)
                .explanation((isCircular?"Circular ":"") + "Queue Overflow! Queue is full. Cannot enqueue " + value + ".")
                .completed(true).build());
            return steps;
        }
        steps.add(VisualizationStep.builder()
            .stepNumber(stepNum++).operation("ENQUEUE_START")
            .array(arr)
            .pointers(arr.length>0 ? Map.of("front",0,"rear",(int)(arr.length-1)) : Map.of("front",-1,"rear",-1))
            .explanation("Enqueue " + value + " to the " + (isCircular?"circular ":"") + "queue.").build());
        int[] newArr = Arrays.copyOf(arr, arr.length + 1);
        newArr[arr.length] = value;
        steps.add(VisualizationStep.builder()
            .stepNumber(stepNum).operation("ENQUEUE_DONE")
            .array(newArr).activeIndices(List.of(newArr.length-1))
            .pointers(Map.of("front",0,"rear",(int)(newArr.length-1)))
            .explanation("Enqueued " + value + ". Rear pointer now at " + (newArr.length-1) + ". Queue size: " + newArr.length + ".")
            .completed(true).build());
        return steps;
    }
    public List<VisualizationStep> generateDequeueSteps(List<Integer> currentQueue, boolean isCircular) {
        List<VisualizationStep> steps = new ArrayList<>();
        int[] arr = currentQueue.stream().mapToInt(Integer::intValue).toArray();
        int stepNum = 1;
        if (arr.length == 0) {
            steps.add(VisualizationStep.builder()
                .stepNumber(stepNum).operation("UNDERFLOW")
                .array(arr)
                .explanation((isCircular?"Circular ":"") + "Queue Underflow! Queue is empty. Cannot dequeue.")
                .completed(true).build());
            return steps;
        }
        int front = arr[0];
        steps.add(VisualizationStep.builder()
            .stepNumber(stepNum++).operation("DEQUEUE_START")
            .array(arr).activeIndices(List.of(0))
            .pointers(Map.of("front",0,"rear",(int)(arr.length-1)))
            .explanation("Dequeue from front. Front element: " + front + ".").build());
        int[] newArr = Arrays.copyOfRange(arr, 1, arr.length);
        steps.add(VisualizationStep.builder()
            .stepNumber(stepNum).operation("DEQUEUE_DONE")
            .array(newArr)
            .pointers(newArr.length>0 ? Map.of("front",0,"rear",(int)(newArr.length-1)) : Map.of("front",-1,"rear",-1))
            .explanation("Dequeued " + front + " from front. Queue now has " + newArr.length + " element(s).")
            .completed(true).build());
        return steps;
    }
}
