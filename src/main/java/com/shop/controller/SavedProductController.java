package com.shop.controller;

import com.shop.command.SavedProductCrudCommand;
import com.shop.service.SavedProductService;
import com.shop.shared.classes.Response;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController()
@RequestMapping("api/v1/saved")
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
public class SavedProductController {

    private final SavedProductService service;

    @GetMapping()
    public ResponseEntity<Response> getAll() {
        return service.getAll();
    }

    @PostMapping(path = "add")
    public ResponseEntity<Response> add(@RequestBody SavedProductCrudCommand command) {
        return service.add(command);
    }

    @DeleteMapping(path = "delete")
    public ResponseEntity<Response> delete(@RequestBody SavedProductCrudCommand command) {
        return service.delete(command);
    }

    @GetMapping(path = "is-saved")
    public ResponseEntity<Response> isSaved(@ModelAttribute SavedProductCrudCommand command) {
        return service.isSaved(command);
    }
}
