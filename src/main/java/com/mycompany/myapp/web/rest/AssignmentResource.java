package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.service.AssignmentService;
import com.mycompany.myapp.service.dto.AssignmentDTO;
import java.time.Instant;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/assignment")
public class AssignmentResource {

    private final AssignmentService assignmentService;

    public AssignmentResource(AssignmentService assignmentService) {
        this.assignmentService = assignmentService;
    }

    @PostMapping("")
    public void saveAssignment(@RequestBody AssignmentDTO assignmentDTO) {
        assignmentDTO.setAssignDate(Instant.now());
        assignmentService.assign(assignmentDTO);
    }

    @DeleteMapping("/{id}")
    public void saveAssignment(@PathVariable Long id) {
        assignmentService.deleteAssignment(id);
    }

    @PutMapping("/{id}")
    public void finishAssignment(@PathVariable Long id) {
        assignmentService.assign(id);
    }
}
