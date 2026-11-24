/**
 * ALGOVISION - DSA TOPIC LIBRARY DATA & CURRICULUM
 * Tailored for second-year B.Tech Information Technology syllabus.
 * Contains definitions, step-by-step algorithms, Java code, and time/space complexity analysis.
 */

const TOPIC_DATABASE = {
  // ==========================================
  // DATA STRUCTURES
  // ==========================================
  "arrays": {
    id: "arrays",
    title: "Arrays (1D)",
    category: "Data Structures",
    difficulty: "Beginner",
    vizType: "cells",
    defaultData: [12, 25, 38, 44, 59, 71],
    intro: "An array is a linear data structure that stores elements of the same data type in contiguous memory locations with direct O(1) index access.",
    dsOperations: ["insert", "delete", "access"],
    theory: {
      definition: "An array is a collection of elements of homogeneous data types stored at contiguous memory locations. Individual elements are accessed using zero-based numerical indices.",
      purpose: "Arrays provide instantaneous random access O(1) to any element via its index formula: MemoryAddress(i) = BaseAddress + (i * ElementSize).",
      howItWorks: "Because elements reside right next to each other in RAM, jumping to index `k` takes constant time. However, inserting or deleting elements at arbitrary positions requires shifting all subsequent elements left or right to maintain contiguity.",
      pseudocode: `// Inserting into Array at position k:
Algorithm Insert(A, n, k, value):
  If n >= MAX_SIZE Then Return OVERFLOW
  For i = n - 1 DownTo k Do:
    A[i + 1] = A[i]       // Shift element right
  A[k] = value            // Place new element
  n = n + 1               // Increment length`,
      javaCode: `public class ArrayDemo {
    public static void insertAt(int[] arr, int n, int index, int value) {
        // Shift elements to the right
        for (int i = n - 1; i >= index; i--) {
            arr[i + 1] = arr[i];
        }
        arr[index] = value;
    }

    public static void main(String[] args) {
        int[] arr = new int[10];
        arr[0] = 10; arr[1] = 20; arr[2] = 30;
        insertAt(arr, 3, 1, 99); // Inserts 99 at index 1
    }
}`,
      complexity: {
        access: "O(1) Constant",
        search: "O(n) Linear",
        insert: "O(n) Linear (shifting required)",
        delete: "O(n) Linear (shifting required)",
        space: "O(n) Auxiliary"
      },
      advantages: [
        "O(1) instant random access using indices.",
        "Excellent CPU cache locality due to sequential memory layout.",
        "Minimal memory overhead (no pointers needed)."
      ],
      disadvantages: [
        "Fixed static size in basic arrays (resizing is expensive).",
        "Costly insertion and deletion (requires shifting O(n) elements)."
      ],
      applications: [
        "Lookup tables and constant-time math buffers.",
        "Building block for stacks, queues, hash tables, and matrices."
      ]
    }
  },

  "linked-list": {
    id: "linked-list",
    title: "Singly Linked List",
    category: "Data Structures",
    difficulty: "Medium",
    vizType: "linkedlist",
    defaultData: [15, 28, 42, 67, 89],
    intro: "A Singly Linked List is a dynamic linear data structure composed of nodes, where each node stores a data value and a reference pointer to the subsequent node.",
    dsOperations: ["insert", "delete", "traverse"],
    theory: {
      definition: "A singly linked list is a linear collection of data elements called nodes. Unlike arrays, nodes are not stored sequentially in physical memory; each node points to the next node via a memory reference ('next').",
      purpose: "Enables dynamic memory allocation without knowing the collection size in advance, allowing fast O(1) insertions at the head without memory reallocations.",
      howItWorks: "The list is referenced by a 'Head' pointer. The last node's pointer is null, indicating the end of the list. Traversing requires following pointers from Head to Tail sequentially.",
      pseudocode: `// Node Structure:
Class Node:
  data: Integer
  next: Node Reference

// Insert at Head:
Algorithm InsertAtHead(Head, value):
  newNode = New Node(value)
  newNode.next = Head
  Head = newNode`,
      javaCode: `class Node {
    int data;
    Node next;
    Node(int data) { this.data = data; this.next = null; }
}

public class SinglyLinkedList {
    Node head;

    public void insertAtHead(int val) {
        Node newNode = new Node(val);
        newNode.next = head;
        head = newNode;
    }

    public void traverse() {
        Node current = head;
        while (current != null) {
            System.out.print(current.data + " -> ");
            current = current.next;
        }
        System.out.println("null");
    }
}`,
      complexity: {
        access: "O(n) Sequential traversal",
        search: "O(n) Linear",
        insert: "O(1) at Head, O(n) at Tail",
        delete: "O(1) at Head, O(n) at Tail",
        space: "O(n) with pointer overhead"
      },
      advantages: [
        "Dynamic size: Grows and shrinks seamlessly at runtime.",
        "O(1) insertion/deletion at the beginning without shifting elements.",
        "No memory wastage from fixed buffer over-allocation."
      ],
      disadvantages: [
        "No direct random indexing: Accessing element k takes O(k) steps.",
        "Extra memory consumed per node for pointer references."
      ],
      applications: [
        "Dynamic memory managers and operating system free-lists.",
        "Implementation of stacks, queues, and chaining in hash maps."
      ]
    }
  },

  "stack": {
    id: "stack",
    title: "Stack (LIFO)",
    category: "Data Structures",
    difficulty: "Beginner",
    vizType: "stack",
    defaultData: [10, 20, 30, 40],
    intro: "A Stack is a fundamental linear data structure operating strictly on the Last-In, First-Out (LIFO) principle, where insertions and removals occur at a single end called 'Top'.",
    dsOperations: ["push", "pop", "peek"],
    theory: {
      definition: "A stack is an abstract data type that serves as a collection of elements with two principal operations: Push (adds an element to the top) and Pop (removes the most recently added element).",
      purpose: "Enforces strict LIFO processing, perfect for tracking state, nested operations, recursion, and reversing sequences.",
      howItWorks: "All operations occur at the `Top` index. When an item is pushed, `top = top + 1` and `stack[top] = value`. When popped, the item at `top` is returned and `top = top - 1`.",
      pseudocode: `Algorithm Push(S, value):
  If top >= MAX - 1 Then Return OVERFLOW
  top = top + 1
  S[top] = value

Algorithm Pop(S):
  If top < 0 Then Return UNDERFLOW
  val = S[top]
  top = top - 1
  Return val`,
      javaCode: `public class CustomStack {
    private int[] arr = new int[10];
    private int top = -1;

    public void push(int val) {
        if (top >= arr.length - 1) throw new RuntimeException("Stack Overflow");
        arr[++top] = val;
    }

    public int pop() {
        if (top == -1) throw new RuntimeException("Stack Underflow");
        return arr[top--];
    }

    public int peek() {
        if (top == -1) throw new RuntimeException("Stack is Empty");
        return arr[top];
    }
}`,
      complexity: {
        push: "O(1) Constant",
        pop: "O(1) Constant",
        peek: "O(1) Constant",
        search: "O(n) Linear",
        space: "O(n) Storage"
      },
      advantages: [
        "Guaranteed O(1) time complexity for all primary operations.",
        "Prevents arbitrary data corruption by restricting access strictly to Top.",
        "Simple and memory-efficient implementation."
      ],
      disadvantages: [
        "Inaccessible internal elements without popping all elements above.",
        "Risk of Stack Overflow in fixed memory implementations."
      ],
      applications: [
        "Function call execution stack and recursive state preservation in JVM.",
        "Expression evaluation and syntax parsing (infix to postfix).",
        "Undo / Redo buffers in code editors and browsers."
      ]
    }
  },

  "queue": {
    id: "queue",
    title: "Queue (FIFO)",
    category: "Data Structures",
    difficulty: "Beginner",
    vizType: "queue",
    defaultData: [11, 22, 33, 44],
    intro: "A Queue is a linear data structure operating on the First-In, First-Out (FIFO) principle, where elements enter at the Rear and depart from the Front.",
    dsOperations: ["enqueue", "dequeue"],
    theory: {
      definition: "A queue is an ordered list where elements are inserted at one end called 'Rear' (Enqueue) and deleted from the other end called 'Front' (Dequeue).",
      purpose: "Handles asynchronous buffer synchronization, round-robin CPU scheduling, and orderly request queuing.",
      howItWorks: "Enqueue adds an element at `rear` and increments `rear`. Dequeue removes the element at `front` and advances `front`. When front exceeds rear, the queue is empty.",
      pseudocode: `Algorithm Enqueue(Q, value):
  If rear == MAX - 1 Then Return OVERFLOW
  If front == -1 Then front = 0
  rear = rear + 1
  Q[rear] = value

Algorithm Dequeue(Q):
  If front == -1 Or front > rear Then Return UNDERFLOW
  val = Q[front]
  front = front + 1
  Return val`,
      javaCode: `public class LinearQueue {
    private int[] data = new int[10];
    private int front = 0, rear = -1, size = 0;

    public void enqueue(int val) {
        if (size == data.length) throw new RuntimeException("Queue Overflow");
        data[++rear] = val;
        size++;
    }

    public int dequeue() {
        if (size == 0) throw new RuntimeException("Queue Underflow");
        int val = data[front++];
        size--;
        return val;
    }
}`,
      complexity: {
        enqueue: "O(1) Constant",
        dequeue: "O(1) Constant",
        peek: "O(1) Constant",
        search: "O(n) Linear",
        space: "O(n) Storage"
      },
      advantages: [
        "Fairness: Orders tasks exactly in the sequence they were received (FIFO).",
        "Guaranteed O(1) performance for insertion and extraction."
      ],
      disadvantages: [
        "In simple array queues, unutilized space accumulates at the front (solved by Circular Queues).",
        "No direct access to middle elements."
      ],
      applications: [
        "CPU job scheduling and printer spooling.",
        "Breadth-First Search (BFS) algorithm traversal queue."
      ]
    }
  },

  // ==========================================
  // SORTING ALGORITHMS
  // ==========================================
  "bubble-sort": {
    id: "bubble-sort",
    title: "Bubble Sort",
    category: "Sorting Algorithms",
    difficulty: "Beginner",
    vizType: "bars",
    defaultData: [64, 34, 25, 12, 22, 11, 90],
    apiName: "bubble",
    intro: "Bubble Sort is a comparison-based sorting algorithm that repeatedly steps through the list, compares adjacent elements, and swaps them if they are in the wrong order.",
    theory: {
      definition: "Bubble Sort is an elementary comparison sort where adjacent elements are checked pairwise and swapped if the preceding element is greater than the succeeding element, causing large values to 'bubble up' to the end.",
      howItWorks: "During Pass 1, comparing pairs (0,1), (1,2)... places the largest element at index n-1. In Pass 2, the second largest lands at index n-2. This repeats for n-1 passes until fully sorted.",
      pseudocode: `Algorithm BubbleSort(A, n):
  For i = 0 To n - 2 Do:
    For j = 0 To n - i - 2 Do:
      If A[j] > A[j + 1] Then:
        Swap(A[j], A[j + 1])`,
      javaCode: `public class BubbleSort {
    public static void sort(int[] arr) {
        int n = arr.length;
        for (int i = 0; i < n - 1; i++) {
            boolean swapped = false;
            for (int j = 0; j < n - i - 1; j++) {
                if (arr[j] > arr[j + 1]) {
                    int temp = arr[j];
                    arr[j] = arr[j + 1];
                    arr[j + 1] = temp;
                    swapped = true;
                }
            }
            if (!swapped) break; // Optimized early exit
        }
    }
}`,
      complexity: {
        best: "O(n) with swap flag",
        average: "O(n²)",
        worst: "O(n²)",
        space: "O(1) In-Place",
        stability: "Stable"
      },
      advantages: [
        "Extremely easy to understand and implement.",
        "In-place sorting: Requires O(1) auxiliary memory.",
        "Stable sort: Preserves relative order of equal elements."
      ],
      disadvantages: [
        "Very poor O(n²) time complexity for large datasets.",
        "High number of redundant swap operations."
      ],
      applications: [
        "Educational demonstrations in introductory CS curriculums.",
        "Nearly-sorted datasets with small input sizes."
      ]
    }
  },

  "selection-sort": {
    id: "selection-sort",
    title: "Selection Sort",
    category: "Sorting Algorithms",
    difficulty: "Beginner",
    vizType: "bars",
    defaultData: [29, 10, 14, 37, 13, 8],
    apiName: "selection",
    intro: "Selection Sort divides the list into a sorted and unsorted region, repeatedly finding the minimum element from the unsorted region and placing it at the beginning.",
    theory: {
      definition: "Selection Sort is an in-place comparison algorithm that systematically selects the smallest element from the unsorted subarray and swaps it into its final sorted position.",
      howItWorks: "Maintains two subarrays: sorted (left) and unsorted (right). In each iteration, scans the unsorted partition to find the minimum value's index, then performs exactly one swap to append it to the sorted partition.",
      pseudocode: `Algorithm SelectionSort(A, n):
  For i = 0 To n - 2 Do:
    minIdx = i
    For j = i + 1 To n - 1 Do:
      If A[j] < A[minIdx] Then minIdx = j
    If minIdx != i Then:
      Swap(A[i], A[minIdx])`,
      javaCode: `public class SelectionSort {
    public static void sort(int[] arr) {
        int n = arr.length;
        for (int i = 0; i < n - 1; i++) {
            int minIdx = i;
            for (int j = i + 1; j < n; j++) {
                if (arr[j] < arr[minIdx]) {
                    minIdx = j;
                }
            }
            int temp = arr[minIdx];
            arr[minIdx] = arr[i];
            arr[i] = temp;
        }
    }
}`,
      complexity: {
        best: "O(n²)",
        average: "O(n²)",
        worst: "O(n²)",
        space: "O(1) In-Place",
        stability: "Unstable (in standard implementation)"
      },
      advantages: [
        "Makes at most O(n) swaps: Useful when write operations to flash memory are expensive.",
        "Simple logic and O(1) auxiliary memory."
      ],
      disadvantages: [
        "O(n²) time complexity across all cases (even already sorted arrays).",
        "Not adaptive."
      ],
      applications: [
        "Systems where memory write cycles are severely limited (EEPROM/Flash)."
      ]
    }
  },

  "insertion-sort": {
    id: "insertion-sort",
    title: "Insertion Sort",
    category: "Sorting Algorithms",
    difficulty: "Beginner",
    vizType: "bars",
    defaultData: [43, 21, 56, 12, 34, 78],
    apiName: "insertion",
    intro: "Insertion Sort builds the final sorted array one item at a time by repeatedly taking the next element and shifting larger elements to insert it into its correct position.",
    theory: {
      definition: "Insertion Sort mimics how people sort playing cards in their hands. It inspects elements one by one, shifting larger sorted elements to the right to create an opening for the 'key' element.",
      howItWorks: "Starts with the 0-th element as a 1-element sorted subarray. For each subsequent element from index 1 to n-1, it compares backward against the sorted region, shifting larger elements right until the key's position is found.",
      pseudocode: `Algorithm InsertionSort(A, n):
  For i = 1 To n - 1 Do:
    key = A[i]
    j = i - 1
    While j >= 0 And A[j] > key Do:
      A[j + 1] = A[j]   // Shift right
      j = j - 1
    A[j + 1] = key      // Insert key`,
      javaCode: `public class InsertionSort {
    public static void sort(int[] arr) {
        for (int i = 1; i < arr.length; i++) {
            int key = arr[i];
            int j = i - 1;
            while (j >= 0 && arr[j] > key) {
                arr[j + 1] = arr[j];
                j--;
            }
            arr[j + 1] = key;
        }
    }
}`,
      complexity: {
        best: "O(n) when array is already sorted",
        average: "O(n²)",
        worst: "O(n²) when array is reverse sorted",
        space: "O(1) In-Place",
        stability: "Stable"
      },
      advantages: [
        "Highly efficient for small arrays (n < 50) and nearly sorted inputs.",
        "Online algorithm: Can sort a list as it receives continuous streaming data.",
        "Stable and in-place."
      ],
      disadvantages: [
        "O(n²) worst-case performance makes it unsuitable for large lists."
      ],
      applications: [
        "Used as the base case in hybrid algorithms like Timsort (Java's Arrays.sort) and introsort."
      ]
    }
  },

  "merge-sort": {
    id: "merge-sort",
    title: "Merge Sort",
    category: "Sorting Algorithms",
    difficulty: "Medium",
    vizType: "bars",
    defaultData: [38, 27, 43, 3, 9, 82, 10],
    apiName: "merge",
    intro: "Merge Sort is a classic Divide and Conquer algorithm that recursively divides the array into two halves, sorts them, and merges the sorted halves into a single sorted array.",
    theory: {
      definition: "Merge Sort is an efficient, comparison-based, divide-and-conquer sorting algorithm that guarantees O(n log n) running time in all cases.",
      howItWorks: "1. Divide: Split the array into two halves at midpoint m = (l + r) / 2. 2. Conquer: Recursively sort each half. 3. Combine: Merge the two sorted halves into a temporary buffer and copy back.",
      pseudocode: `Algorithm MergeSort(A, l, r):
  If l < r Then:
    m = (l + r) / 2
    MergeSort(A, l, m)
    MergeSort(A, m + 1, r)
    Merge(A, l, m, r)`,
      javaCode: `public class MergeSort {
    public static void mergeSort(int[] arr, int l, int r) {
        if (l < r) {
            int m = l + (r - l) / 2;
            mergeSort(arr, l, m);
            mergeSort(arr, m + 1, r);
            merge(arr, l, m, r);
        }
    }

    private static void merge(int[] arr, int l, int m, int r) {
        int[] left = java.util.Arrays.copyOfRange(arr, l, m + 1);
        int[] right = java.util.Arrays.copyOfRange(arr, m + 1, r + 1);
        int i = 0, j = 0, k = l;
        while (i < left.length && j < right.length) {
            if (left[i] <= right[j]) arr[k++] = left[i++];
            else arr[k++] = right[j++];
        }
        while (i < left.length) arr[k++] = left[i++];
        while (j < right.length) arr[k++] = right[j++];
    }
}`,
      complexity: {
        best: "O(n log n)",
        average: "O(n log n)",
        worst: "O(n log n)",
        space: "O(n) Auxiliary buffer",
        stability: "Stable"
      },
      advantages: [
        "Predictable, consistent O(n log n) performance regardless of initial array ordering.",
        "Guaranteed stability.",
        "Ideal for external sorting (data on disk too large for RAM) and linked lists."
      ],
      disadvantages: [
        "Requires O(n) additional memory buffer during merge phase."
      ],
      applications: [
        "Sorting linked lists in O(n log n) without extra space overhead.",
        "External sorting in database management systems."
      ]
    }
  },

  "quick-sort": {
    id: "quick-sort",
    title: "Quick Sort",
    category: "Sorting Algorithms",
    difficulty: "Hard",
    vizType: "bars",
    defaultData: [45, 12, 85, 32, 89, 39, 69, 44],
    apiName: "quick",
    intro: "Quick Sort is a highly efficient divide-and-conquer algorithm that selects a 'pivot' element and partitions the array such that all smaller elements precede the pivot and larger follow it.",
    theory: {
      definition: "Quick Sort works by picking an element as a pivot and partitioning the array around the picked pivot. Subarrays are then sorted recursively.",
      howItWorks: "1. Choose pivot (e.g. last element). 2. Partition: Scan with pointer `j`. When `arr[j] <= pivot`, swap `arr[++i]` with `arr[j]`. 3. Place pivot at `i+1`. 4. Recurse left and right partitions.",
      pseudocode: `Algorithm QuickSort(A, low, high):
  If low < high Then:
    pi = Partition(A, low, high)
    QuickSort(A, low, pi - 1)
    QuickSort(A, pi + 1, high)

Algorithm Partition(A, low, high):
  pivot = A[high]
  i = low - 1
  For j = low To high - 1 Do:
    If A[j] <= pivot Then:
      i = i + 1
      Swap(A[i], A[j])
  Swap(A[i + 1], A[high])
  Return i + 1`,
      javaCode: `public class QuickSort {
    public static void sort(int[] arr, int low, int high) {
        if (low < high) {
            int pi = partition(arr, low, high);
            sort(arr, low, pi - 1);
            sort(arr, pi + 1, high);
        }
    }

    private static int partition(int[] arr, int low, int high) {
        int pivot = arr[high];
        int i = low - 1;
        for (int j = low; j < high; j++) {
            if (arr[j] <= pivot) {
                i++;
                int temp = arr[i]; arr[i] = arr[j]; arr[j] = temp;
            }
        }
        int temp = arr[i + 1]; arr[i + 1] = arr[high]; arr[high] = temp;
        return i + 1;
    }
}`,
      complexity: {
        best: "O(n log n)",
        average: "O(n log n)",
        worst: "O(n²) (when array already sorted with poor pivot selection)",
        space: "O(log n) Call stack",
        stability: "Unstable"
      },
      advantages: [
        "In practice, typically 2-3x faster than Merge Sort and Heap Sort due to low constant factors.",
        "In-place sorting: O(log n) auxiliary stack space."
      ],
      disadvantages: [
        "O(n²) worst-case time complexity without randomized pivot selection.",
        "Unstable sort."
      ],
      applications: [
        "C++ std::sort, Java Arrays.sort for primitive types (Dual-Pivot Quicksort)."
      ]
    }
  },

  // ==========================================
  // SEARCHING ALGORITHMS
  // ==========================================
  "linear-search": {
    id: "linear-search",
    title: "Linear Search",
    category: "Searching Algorithms",
    difficulty: "Beginner",
    vizType: "cells",
    defaultData: [24, 76, 12, 85, 33, 91, 48],
    defaultTarget: 85,
    apiName: "linear",
    intro: "Linear Search is the most fundamental searching algorithm. It sequentially checks every element in the list until a match is found or the end is reached.",
    theory: {
      definition: "Linear Search (Sequential Search) finds a target value within a collection by checking each element in order from start to end.",
      howItWorks: "Starts at index 0. Compares current value `arr[i]` with target. If equal, returns index `i`. If not equal, advances to `i + 1`. If loop finishes without a match, returns -1.",
      pseudocode: `Algorithm LinearSearch(A, n, target):
  For i = 0 To n - 1 Do:
    If A[i] == target Then:
      Return i    // Target found
  Return -1       // Target not present`,
      javaCode: `public class LinearSearch {
    public static int search(int[] arr, int target) {
        for (int i = 0; i < arr.length; i++) {
            if (arr[i] == target) {
                return i; // Found at index i
            }
        }
        return -1; // Not found
    }
}`,
      complexity: {
        best: "O(1) Target is at the very first element",
        average: "O(n/2) -> O(n)",
        worst: "O(n) Target at last index or absent",
        space: "O(1) Auxiliary"
      },
      advantages: [
        "Works on any array (unsorted or sorted).",
        "Does not require elements to be ordered or comparable beyond equality.",
        "Zero setup cost; very simple."
      ],
      disadvantages: [
        "Very slow on large datasets (O(n) comparisons)."
      ],
      applications: [
        "Searching in small lists (< 20 elements) or unsorted files."
      ]
    }
  },

  "binary-search": {
    id: "binary-search",
    title: "Binary Search",
    category: "Searching Algorithms",
    difficulty: "Beginner",
    vizType: "cells",
    defaultData: [4, 11, 18, 25, 36, 42, 59, 73, 88],
    defaultTarget: 42,
    apiName: "binary",
    intro: "Binary Search is a highly efficient search algorithm for sorted arrays that repeatedly divides the search interval in half by comparing the target with the middle element.",
    theory: {
      definition: "Binary Search finds the position of a target value within a sorted array by comparing the target to the middle element and eliminating half the search range in each step.",
      howItWorks: "1. Maintain `low = 0` and `high = n - 1`. 2. Compute `mid = low + (high - low) / 2`. 3. If `arr[mid] == target`, found! 4. If `arr[mid] < target`, eliminate left half by setting `low = mid + 1`. 5. If `arr[mid] > target`, eliminate right half by setting `high = mid - 1`.",
      pseudocode: `Algorithm BinarySearch(A, n, target):
  low = 0
  high = n - 1
  While low <= high Do:
    mid = low + (high - low) / 2
    If A[mid] == target Then Return mid
    Else If A[mid] < target Then low = mid + 1
    Else high = mid - 1
  Return -1`,
      javaCode: `public class BinarySearch {
    public static int search(int[] arr, int target) {
        int low = 0, high = arr.length - 1;
        while (low <= high) {
            int mid = low + (high - low) / 2;
            if (arr[mid] == target) return mid;
            if (arr[mid] < target) low = mid + 1;
            else high = mid - 1;
        }
        return -1;
    }
}`,
      complexity: {
        best: "O(1) Target is at exact midpoint on first check",
        average: "O(log n)",
        worst: "O(log n)",
        space: "O(1) Iterative, O(log n) Recursive"
      },
      advantages: [
        "Extremely fast: Logarithmic time means searching through 1,000,000 items takes at most 20 comparisons!",
        "Predictable and optimal for sorted data."
      ],
      disadvantages: [
        "Strict prerequisite: Array must be sorted beforehand."
      ],
      applications: [
        "Database B-tree indexing and dictionary lookups.",
        "Java's Collections.binarySearch and C++'s std::binary_search."
      ]
    }
  }
};
