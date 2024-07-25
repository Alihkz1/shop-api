package com.shop.controller;

import com.shop.command.SavedProductAddCommand;
import com.shop.service.SavedProductService;
import com.shop.shared.classes.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController()
@RequestMapping("api/v1/saved")
@CrossOrigin(origins = "*")
public class SavedProductController {

    private final SavedProductService service;

    @Autowired
    SavedProductController(SavedProductService service) {
        this.service = service;
    }

    @GetMapping(path = "{userId}")
    public ResponseEntity<Response> getAll(@PathVariable Long userId) {
        return service.getAll(userId);
    }

    @PostMapping(path = "add")
    public ResponseEntity<Response> add(SavedProductAddCommand command) {
        return service.add(command);
    }

    @DeleteMapping(path = "delete/{id}")
    public ResponseEntity<Response> add(Long id) {
        return service.deleteById(id);
    }
}
