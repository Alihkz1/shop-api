package com.shop.service;

import com.shop.model.UserProductSearch;
import com.shop.repository.UserProductSearchRepository;
import com.shop.shared.classes.BaseService;
import com.shop.shared.classes.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class UserProductSearchService extends BaseService {
    private final UserProductSearchRepository repository;

    @Autowired
    public UserProductSearchService(UserProductSearchRepository repository) {
        this.repository = repository;
    }

    public void save(String searchQuery, Long userId) {
        UserProductSearch model = new UserProductSearch();
        model.setSearch(searchQuery);
        model.setUserId(userId);
        repository.save(model);
    }

    public ResponseEntity<Response> getByUserId(Long userId) {
        Optional<List<UserProductSearch>> userSearchList = repository.findByUserId(userId);
        if (userSearchList.isPresent()) {
            Map<String, List<UserProductSearch>> map = new HashMap<>();
            map.put("history", userSearchList.get());
            return successResponse(map);
        } else return successResponse();
    }
}
