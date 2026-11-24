package com.algovision.controller;
import com.algovision.dto.*;
import com.algovision.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.*;
@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*")
public class AlgorithmController {
    @Autowired private SortingService sortingService;
    @Autowired private SearchingService searchingService;
    @Autowired private DataStructureService dsService;
    @PostMapping("/sort")
    public ResponseEntity<AlgorithmResponse> sort(@RequestBody AlgorithmRequest req) {
        AlgorithmResponse resp = sortingService.execute(req.getAlgorithm(), req.getArray());
        return ResponseEntity.ok(resp);
    }
    @PostMapping("/search")
    public ResponseEntity<AlgorithmResponse> search(@RequestBody AlgorithmRequest req) {
        int target = req.getTarget() != null ? req.getTarget() : 0;
        AlgorithmResponse resp = searchingService.execute(req.getAlgorithm(), req.getArray(), target);
        return ResponseEntity.ok(resp);
    }
    @PostMapping("/datastructure")
    public ResponseEntity<AlgorithmResponse> dataStructure(@RequestBody AlgorithmRequest req) {
        AlgorithmResponse resp = dsService.execute(
            req.getAlgorithm(), req.getOperation(), req.getArray(),
            req.getValue(), req.getPosition(), req.getType());
        return ResponseEntity.ok(resp);
    }
}
