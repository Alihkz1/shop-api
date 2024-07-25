package com.shop.controller;

import com.shop.command.SavedProductCrudCommand;
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
    public ResponseEntity<Response> add(@RequestBody SavedProductCrudCommand command) {
        return service.add(command);
    }

    @DeleteMapping(path = "delete")
    public ResponseEntity<Response> delete(@RequestBody SavedProductCrudCommand command) {
        return service.delete(command);
    }

    @GetMapping(path = "is-saved")
    public ResponseEntity<Response> isSaved(@RequestParam(value = "userId") Long userId, @RequestParam(value = "productId") Long productId) {
        SavedProductCrudCommand command = new SavedProductCrudCommand();
        command.setUserId(userId);
        command.setProductId(productId);
        return service.isSaved(command);
    }
}
