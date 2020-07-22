package com.codecool.shifterbackend.controller;

import com.codecool.shifterbackend.controller.dto.ShiftAssignmentDetails;
import com.codecool.shifterbackend.entity.Shift;
import com.codecool.shifterbackend.entity.WorkerShift;
import com.codecool.shifterbackend.service.ShiftService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin
public class ShiftController {

    @Autowired
    private ShiftService shiftService;

    @GetMapping("/shifts")
    private List<Shift> getAllShifts() {
        return shiftService.getAllBaseShifts();
    }

    @GetMapping("/worker-shifts")
    private List<WorkerShift> getAllWorkerShifts() {
        return shiftService.getAllWorkerShifts();
    }

    @PostMapping("/assign-shift")
    private List<WorkerShift> assignShiftToWorker(@RequestBody ShiftAssignmentDetails shiftAssignmentDetails) {
        shiftService.assignShiftToUser(
                shiftAssignmentDetails.getShiftId(),
                shiftAssignmentDetails.getWorkerId(),
                shiftAssignmentDetails.getStartDate(),
                shiftAssignmentDetails.getEndDate()
        );
        return shiftService.getAllWorkerShifts();
    }

    @PostMapping("/shifts")
    private ResponseEntity<String> createNewShift(@Valid @RequestBody Shift shift){
        shiftService.addNewShiftToRepository(shift);
        return ResponseEntity.ok("Shift created");
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String, String> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return errors;
    }
}
