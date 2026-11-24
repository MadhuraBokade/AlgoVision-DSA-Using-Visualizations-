package com.algovision;

import com.algovision.algorithm.datastructures.ArrayVisualization;
import com.algovision.algorithm.datastructures.LinkedListVisualization;
import com.algovision.algorithm.datastructures.QueueVisualization;
import com.algovision.algorithm.datastructures.StackVisualization;
import com.algovision.algorithm.searching.BinarySearchAlgorithm;
import com.algovision.algorithm.searching.LinearSearchAlgorithm;
import com.algovision.algorithm.sorting.*;
import com.algovision.model.VisualizationStep;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class AlgoVisionTests {

    @Autowired private BubbleSortAlgorithm bubbleSort;
    @Autowired private SelectionSortAlgorithm selectionSort;
    @Autowired private InsertionSortAlgorithm insertionSort;
    @Autowired private MergeSortAlgorithm mergeSort;
    @Autowired private QuickSortAlgorithm quickSort;

    @Autowired private LinearSearchAlgorithm linearSearch;
    @Autowired private BinarySearchAlgorithm binarySearch;

    @Autowired private StackVisualization stackViz;
    @Autowired private QueueVisualization queueViz;
    @Autowired private ArrayVisualization arrayViz;
    @Autowired private LinkedListVisualization linkedListViz;

    @Test
    void contextLoads() {
        assertNotNull(bubbleSort);
        assertNotNull(linearSearch);
        assertNotNull(stackViz);
    }

    @Test
    void testBubbleSortStepGeneration() {
        int[] input = {5, 2, 8, 1};
        List<VisualizationStep> steps = bubbleSort.generateSteps(input);
        assertFalse(steps.isEmpty(), "Steps should not be empty");
        VisualizationStep lastStep = steps.get(steps.size() - 1);
        assertTrue(lastStep.isCompleted(), "Last step must be marked completed");
        assertArrayEquals(new int[]{1, 2, 5, 8}, lastStep.getArray(), "Final array must be sorted");
    }

    @Test
    void testSelectionSortStepGeneration() {
        int[] input = {29, 10, 14, 37};
        List<VisualizationStep> steps = selectionSort.generateSteps(input);
        assertFalse(steps.isEmpty());
        VisualizationStep lastStep = steps.get(steps.size() - 1);
        assertTrue(lastStep.isCompleted());
        assertArrayEquals(new int[]{10, 14, 29, 37}, lastStep.getArray());
    }

    @Test
    void testInsertionSortStepGeneration() {
        int[] input = {43, 21, 56, 12};
        List<VisualizationStep> steps = insertionSort.generateSteps(input);
        assertFalse(steps.isEmpty());
        VisualizationStep lastStep = steps.get(steps.size() - 1);
        assertTrue(lastStep.isCompleted());
        assertArrayEquals(new int[]{12, 21, 43, 56}, lastStep.getArray());
    }

    @Test
    void testMergeSortStepGeneration() {
        int[] input = {38, 27, 43, 3};
        List<VisualizationStep> steps = mergeSort.generateSteps(input);
        assertFalse(steps.isEmpty());
        VisualizationStep lastStep = steps.get(steps.size() - 1);
        assertTrue(lastStep.isCompleted());
        assertArrayEquals(new int[]{3, 27, 38, 43}, lastStep.getArray());
    }

    @Test
    void testQuickSortStepGeneration() {
        int[] input = {45, 12, 85, 32};
        List<VisualizationStep> steps = quickSort.generateSteps(input);
        assertFalse(steps.isEmpty());
        VisualizationStep lastStep = steps.get(steps.size() - 1);
        assertTrue(lastStep.isCompleted());
        assertArrayEquals(new int[]{12, 32, 45, 85}, lastStep.getArray());
    }

    @Test
    void testLinearSearchStepGeneration() {
        int[] input = {24, 76, 12, 85};
        List<VisualizationStep> steps = linearSearch.generateSteps(input, 12);
        assertFalse(steps.isEmpty());
        VisualizationStep lastStep = steps.get(steps.size() - 1);
        assertEquals("FOUND", lastStep.getOperation());
        assertTrue(lastStep.isCompleted());
    }

    @Test
    void testBinarySearchStepGeneration() {
        int[] sorted = {4, 11, 18, 25, 36, 42};
        List<VisualizationStep> steps = binarySearch.generateSteps(sorted, 25);
        assertFalse(steps.isEmpty());
        VisualizationStep lastStep = steps.get(steps.size() - 1);
        assertEquals("FOUND", lastStep.getOperation());
        assertTrue(lastStep.isCompleted());
    }

    @Test
    void testStackOperations() {
        List<Integer> initialStack = Arrays.asList(10, 20);
        List<VisualizationStep> pushSteps = stackViz.generatePushSteps(initialStack, 30);
        assertFalse(pushSteps.isEmpty());
        assertEquals("PUSH_DONE", pushSteps.get(pushSteps.size() - 1).getOperation());

        List<VisualizationStep> popSteps = stackViz.generatePopSteps(Arrays.asList(10, 20, 30));
        assertFalse(popSteps.isEmpty());
        assertEquals("POP_DONE", popSteps.get(popSteps.size() - 1).getOperation());
    }

    @Test
    void testQueueOperations() {
        List<Integer> initialQueue = Arrays.asList(10, 20);
        List<VisualizationStep> enqueueSteps = queueViz.generateEnqueueSteps(initialQueue, 30, false);
        assertFalse(enqueueSteps.isEmpty());
        assertEquals("ENQUEUE_DONE", enqueueSteps.get(enqueueSteps.size() - 1).getOperation());

        List<VisualizationStep> dequeueSteps = queueViz.generateDequeueSteps(Arrays.asList(10, 20, 30), false);
        assertFalse(dequeueSteps.isEmpty());
        assertEquals("DEQUEUE_DONE", dequeueSteps.get(dequeueSteps.size() - 1).getOperation());
    }

    @Test
    void testArrayOperations() {
        List<Integer> list = Arrays.asList(10, 20, 30);
        List<VisualizationStep> insertSteps = arrayViz.generateInsertSteps(list, 1, 99);
        assertFalse(insertSteps.isEmpty());
        assertEquals("COMPLETE", insertSteps.get(insertSteps.size() - 1).getOperation());
    }

    @Test
    void testLinkedListOperations() {
        List<Integer> list = Arrays.asList(15, 28, 42);
        List<VisualizationStep> insertSteps = linkedListViz.generateInsertSteps(list, 0, 99);
        assertFalse(insertSteps.isEmpty());
        assertEquals("COMPLETE", insertSteps.get(insertSteps.size() - 1).getOperation());
    }
}
