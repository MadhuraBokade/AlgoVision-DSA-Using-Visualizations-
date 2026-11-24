# AlgoVision – Interactive Data Structures & Algorithms Learning Platform

**AlgoVision** is a full-stack educational web application designed for second-year B.Tech Information Technology students. It bridges the gap between abstract algorithmic concepts and visual understanding by combining interactive step-by-step animations with synchronized theory, pseudocode, Java implementations, and time/space complexity analysis on a unified page.

Featuring an elegant **Pastel Academia** design theme and a charming mascot guide named **Lumi** (the lavender bunny), AlgoVision transforms Data Structures & Algorithms (DSA) study into an engaging, intuitive experience.

---

## 1. Problem Statement
Undergraduate engineering students frequently struggle to conceptualize how pointers advance, elements swap, and memory partitions shift when memorizing code from textbooks. Traditional platforms either present static code snippets or separate video lectures from actual algorithmic execution, lacking immediate step-level feedback and tactile input control.

## 2. Project Objectives
- **See It In Action:** Transform theoretical DSA concepts into live, step-by-step interactive animations.
- **Unified Single-Page Paradigm:** Adhere to the core principle: `ONE TOPIC = ONE DEDICATED PAGE = INTERACTIVE VISUALIZATION FIRST + WRITTEN THEORY DIRECTLY BELOW`.
- **True Java Execution Engine:** Ensure the Java Spring Boot backend executes the real algorithms and generates state snapshots (active indices, swapped pairs, sorted boundaries, pointer maps, and step explanations).
- **Tactile Learning:** Allow students to enter their own custom arrays, search targets, and test edge cases (reverse sorted, single element, duplicates).
- **Exam-Oriented Preparation:** Provide curated pseudocode, standard Java code, time/space complexity breakdowns, and B.Tech exam tips for every core topic.
- **Zero-Setup Persistence:** Persist learning progress, completed topics, and light/dark theme preferences using browser `localStorage` without requiring database installation.

---

## 3. Key Features

- **Cozy Pastel Academia Theme:** Minimalist lavender (`#E9DDF5`), blush pink (`#F8E2EA`), warm cream (`#FFF8ED`), and muted purple (`#806A9C`) palette with full dark mode support.
- **Lumi The Bunny Mascot:** A friendly, minimalist 2D mascot present in the welcome dashboard and celebration modals when completing a topic.
- **Complete Playback Control Deck:**
  - **Play / Pause:** Continuous automated execution.
  - **Next Step / Previous Step:** Step backward or forward through algorithm history.
  - **Restart:** Reset to step 0 instantly.
  - **Speed Selector:** Adjustable animation rates (0.5x, 1.0x, 2.0x, 4.0x).
  - **Random Input Generator:** Generate random values or sorted sequences on demand.
  - **Custom Input:** Validate and visualize user-provided numbers.
- **Multi-Representation Visualizer:**
  - **Vertical Bar Graphs:** Scaled height bars with value labels, comparison highlights (amber/orange), swap highlights (pink/red), and sorted elements (emerald green).
  - **Array Cells:** Equal-dimension rectangular memory blocks showing indices above and pointer tags below (e.g., `low`, `mid`, `high`, `i`, `j`).
  - **Faded Eliminated Regions:** Fades out discarded halves in Binary Search.
  - **Stack Blocks:** Vertical LIFO chamber supporting Push, Pop, and Peek with Overflow/Underflow diagnostics.
  - **Queue Structure:** Linear FIFO pipeline showing Front and Rear pointer shifts during Enqueue and Dequeue.
  - **Linked List Nodes:** Dynamic data blocks with next pointer arrows linked to terminal `null`.
- **Exam-Focused Topics:**
  - **Data Structures:** Arrays, Singly Linked List, Stack (LIFO), Queue (FIFO).
  - **Sorting Algorithms:** Bubble Sort, Selection Sort, Insertion Sort, Merge Sort (Divide & Conquer), Quick Sort (Lomuto Partition).
  - **Searching Algorithms:** Linear Search, Binary Search.

---

## 4. Architecture & Technologies Used

```
                       ┌────────────────────────────────────────┐
                       │          Browser Client                │
                       │  - HTML5, CSS3 (Pastel Academia Theme) │
                       │  - Vanilla JavaScript Engine           │
                       │  - LocalStorage (Progress & Themes)    │
                       └───────────────────▲────────────────────┘
                                           │  HTTP REST / JSON
                                           ▼
                       ┌────────────────────────────────────────┐
                       │         Spring Boot Backend            │
                       │  - Spring MVC (PageController)         │
                       │  - REST Controllers (API Endpoints)    │
                       │  - Services & Algorithm Engines        │
                       │  - Java 21 Execution & Step Generation │
                       └────────────────────────────────────────┘
```

### Technology Stack:
- **Backend:** Java 21, Spring Boot 3.2.x, Spring MVC, REST APIs, Maven 3.9.6.
- **Frontend:** HTML5, CSS3 (CSS Variables, Flexbox/Grid, Animations), Vanilla JavaScript (No React/Angular dependencies).
- **Icons & Typography:** Font Awesome 6, Google Fonts (*Inter* and *JetBrains Mono*).
- **Persistence:** Browser `localStorage` (Zero MySQL configuration needed).

---

## 5. Project Directory Structure

```
d:/AlgoVision Java MINIPROJECT/
├── pom.xml                                 # Maven project configuration
├── mvnw.cmd                                # Local Maven wrapper
├── run.bat                                 # Quick-start batch launcher for Windows
├── README.md                               # Project documentation
│
└── src/
    ├── main/
    │   ├── java/com/algovision/
    │   │   ├── AlgoVisionApplication.java  # Spring Boot Main Entry Point
    │   │   ├── algorithm/
    │   │   │   ├── datastructures/
    │   │   │   │   ├── ArrayVisualization.java
    │   │   │   │   ├── LinkedListVisualization.java
    │   │   │   │   ├── QueueVisualization.java
    │   │   │   │   └── StackVisualization.java
    │   │   │   ├── searching/
    │   │   │   │   ├── BinarySearchAlgorithm.java
    │   │   │   │   └── LinearSearchAlgorithm.java
    │   │   │   └── sorting/
    │   │   │       ├── BubbleSortAlgorithm.java
    │   │   │       ├── InsertionSortAlgorithm.java
    │   │   │       ├── MergeSortAlgorithm.java
    │   │   │       ├── QuickSortAlgorithm.java
    │   │   │       └── SelectionSortAlgorithm.java
    │   │   ├── controller/
    │   │   │   ├── AlgorithmController.java # REST endpoints (/api/sort, /api/search, /api/datastructure)
    │   │   │   └── PageController.java      # Thymeleaf view routing
    │   │   ├── dto/
    │   │   │   ├── AlgorithmRequest.java
    │   │   │   └── AlgorithmResponse.java
    │   │   ├── exception/
    │   │   │   └── GlobalExceptionHandler.java
    │   │   ├── model/
    │   │   │   └── VisualizationStep.java   # Step model (indices, pointers, operation, explanation)
    │   │   └── service/
    │   │       ├── DataStructureService.java
    │   │       ├── SearchingService.java
    │   │       └── SortingService.java
    │   │
    │   └── resources/
    │       ├── application.properties
    │       ├── templates/
    │       │   ├── index.html              # Dashboard view
    │       │   └── topic.html              # Topic visualization + theory view
    │       └── static/
    │           ├── css/
    │           │   ├── style.css           # Global theme variables & layout
    │           │   ├── dashboard.css       # Dashboard cards, banner, and stats
    │           │   ├── visualization.css   # Bars, cells, stacks, queues, and legends
    │           │   └── responsive.css      # Tablet and mobile styling
    │           └── js/
    │               ├── app.js              # Routing, API client, theory renderer, themes
    │               ├── dashboard.js        # Stats aggregator & quote rotator
    │               ├── progress.js         # LocalStorage progress manager
    │               ├── topic-data.js       # Complete DSA theory, pseudocode & Java implementations
    │               ├── visualization.js    # Canvas rendering engine
    │               └── animation-controller.js # Step-by-step playback controller
    │
    └── test/java/com/algovision/
        └── AlgoVisionTests.java            # Comprehensive Spring Boot & algorithm test suite
```

---

## 6. How to Run the Application (Windows Instructions)

### Prerequisites:
- **Java Development Kit (JDK):** Java 21 installed.
- **Port Availability:** Port `8080` free on your machine.

### Method 1: Using the Included Windows Launcher (Recommended)
Double-click `run.bat` in the project root folder. It will automatically build and start the Spring Boot server.

### Method 2: Using the Command Line
Open PowerShell or Command Prompt in the project directory:

```powershell
# Using the bundled maven wrapper
.\mvnw.cmd spring-boot:run
```

Or if Apache Maven is in your global `PATH`:
```bash
mvn spring-boot:run
```

### Accessing the Web Application:
Open your browser and navigate to:
```
http://localhost:8080
```

---

## 7. Explanation of the Visualization Engine

1. **User Action:** The student enters an array (e.g. `64, 34, 25, 12, 22, 11, 90`) and clicks **Run Algorithm**.
2. **REST Request:** Frontend sends a JSON payload to `/api/sort` with `{ "algorithm": "bubble", "array": [...] }`.
3. **Java Algorithm Execution:**
   - Spring Boot receives the request in `AlgorithmController`.
   - The corresponding algorithm engine (e.g., `BubbleSortAlgorithm.java`) executes genuine nested sorting loops.
   - For every comparison, swap, or pointer advance, a `VisualizationStep` is created containing:
     - `array`: Current state clone.
     - `activeIndices`: Indices currently being evaluated.
     - `swappedIndices`: Indices exchanged in this step.
     - `sortedIndices`: Cumulative elements locked into final sorted position.
     - `pointers`: Map of named pointers (e.g., `{"i": 0, "j": 1}`).
     - `operation`: Operation tag (`COMPARE`, `SWAP`, `SORTED`, `FOUND`).
     - `explanation`: Synchronized educational sentence explaining what happened and why.
4. **JSON Response:** The backend returns an `AlgorithmResponse` with all steps.
5. **Animation Orchestration:** `AnimationController` receives the steps, starts playback according to user-selected speed, and drives `VisualizationEngine` to render DOM elements with smooth transitions.

---

## 8. Implemented Topics & Complexity Summary

| Topic | Category | Best Case | Average Case | Worst Case | Space Complexity |
| :--- | :--- | :--- | :--- | :--- | :--- |
| **Arrays (1D)** | Data Structure | $O(1)$ Access | $O(n)$ Search | $O(n)$ Insert/Delete | $O(n)$ |
| **Singly Linked List**| Data Structure | $O(1)$ Insert Head | $O(n)$ Traversal | $O(n)$ Search | $O(n)$ |
| **Stack (LIFO)** | Data Structure | $O(1)$ Push/Pop | $O(1)$ Push/Pop | $O(1)$ Push/Pop | $O(n)$ |
| **Queue (FIFO)** | Data Structure | $O(1)$ Enqueue | $O(1)$ Dequeue | $O(1)$ Dequeue | $O(n)$ |
| **Bubble Sort** | Sorting | $O(n)$ | $O(n^2)$ | $O(n^2)$ | $O(1)$ |
| **Selection Sort** | Sorting | $O(n^2)$ | $O(n^2)$ | $O(n^2)$ | $O(1)$ |
| **Insertion Sort** | Sorting | $O(n)$ | $O(n^2)$ | $O(n^2)$ | $O(1)$ |
| **Merge Sort** | Sorting | $O(n \log n)$ | $O(n \log n)$ | $O(n \log n)$ | $O(n)$ |
| **Quick Sort** | Sorting | $O(n \log n)$ | $O(n \log n)$ | $O(n^2)$ | $O(\log n)$ |
| **Linear Search** | Searching | $O(1)$ | $O(n)$ | $O(n)$ | $O(1)$ |
| **Binary Search** | Searching | $O(1)$ | $O(\log n)$ | $O(\log n)$ | $O(1)$ |

---

## 9. Running Tests
To run the automated test suite verifying algorithm correctness and step generation:

```powershell
.\mvnw.cmd test
```

All unit tests validate initial states, comparison steps, swap operations, boundary conditions, and terminal completion flags across all data structures and algorithms.

---

## 10. Educational Significance & Academic Readiness
- Perfectly aligned with 2nd-year B.Tech Information Technology syllabus requirements.
- Strictly adheres to the requirement that Java executes the algorithms and produces state steps.
- Clean code architecture with clear package separation (`algorithm`, `controller`, `service`, `dto`, `model`).
- Responsive across laptops, tablets, and smartphones.
- Ready for live academic evaluation and viva demonstration.
