package com.algovision.service;

import com.algovision.algorithm.datastructures.*;
import com.algovision.dto.AlgorithmResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class DataStructureService {
    @Autowired private StackVisualization stackViz;
    @Autowired private QueueVisualization queueViz;
    @Autowired private ArrayVisualization arrayViz;
    @Autowired private LinkedListVisualization linkedListViz;

    public AlgorithmResponse execute(String algorithm, String operation, List<Integer> data, Integer value, Integer position, String type) {
        if (data == null) data = new ArrayList<>();
        return switch (algorithm.toLowerCase()) {
            case "stack" -> executeStack(operation, data, value);
            case "queue", "circularqueue" -> executeQueue(operation, data, value, algorithm.equalsIgnoreCase("circularqueue"));
            case "array", "arrays" -> executeArray(operation, data, value, position);
            case "linkedlist", "linked-list" -> executeLinkedList(operation, data, value, position);
            default -> throw new IllegalArgumentException("Unknown data structure: " + algorithm);
        };
    }

    private AlgorithmResponse executeStack(String operation, List<Integer> data, Integer value) {
        return switch (operation.toLowerCase()) {
            case "push" -> {
                if (value == null) throw new IllegalArgumentException("Value is required for push operation.");
                yield AlgorithmResponse.ok("Stack Push", stackViz.generatePushSteps(data, value));
            }
            case "pop" -> AlgorithmResponse.ok("Stack Pop", stackViz.generatePopSteps(data));
            case "peek" -> AlgorithmResponse.ok("Stack Peek", stackViz.generatePeekSteps(data));
            default -> throw new IllegalArgumentException("Unknown stack operation: " + operation);
        };
    }

    private AlgorithmResponse executeQueue(String op, List<Integer> data, Integer value, boolean circular) {
        return switch (op.toLowerCase()) {
            case "enqueue" -> {
                if (value == null) throw new IllegalArgumentException("Value is required for enqueue operation.");
                yield AlgorithmResponse.ok("Queue Enqueue", queueViz.generateEnqueueSteps(data, value, circular));
            }
            case "dequeue" -> AlgorithmResponse.ok("Queue Dequeue", queueViz.generateDequeueSteps(data, circular));
            default -> throw new IllegalArgumentException("Unknown queue operation: " + op);
        };
    }

    private AlgorithmResponse executeArray(String op, List<Integer> data, Integer value, Integer position) {
        int pos = position != null ? position : data.size();
        return switch (op.toLowerCase()) {
            case "insert" -> {
                if (value == null) throw new IllegalArgumentException("Value is required for insert operation.");
                yield AlgorithmResponse.ok("Array Insert", arrayViz.generateInsertSteps(data, pos, value));
            }
            case "delete" -> AlgorithmResponse.ok("Array Delete", arrayViz.generateDeleteSteps(data, pos));
            case "access" -> AlgorithmResponse.ok("Array Access", arrayViz.generateAccessSteps(data, pos));
            default -> throw new IllegalArgumentException("Unknown array operation: " + op);
        };
    }

    private AlgorithmResponse executeLinkedList(String op, List<Integer> data, Integer value, Integer position) {
        int pos = position != null ? position : data.size();
        return switch (op.toLowerCase()) {
            case "insert" -> {
                if (value == null) throw new IllegalArgumentException("Value is required for insert operation.");
                yield AlgorithmResponse.ok("Linked List Insert", linkedListViz.generateInsertSteps(data, pos, value));
            }
            case "delete" -> AlgorithmResponse.ok("Linked List Delete", linkedListViz.generateDeleteSteps(data, pos));
            case "traverse" -> AlgorithmResponse.ok("Linked List Traverse", linkedListViz.generateTraverseSteps(data));
            default -> throw new IllegalArgumentException("Unknown linked list operation: " + op);
        };
    }
}
