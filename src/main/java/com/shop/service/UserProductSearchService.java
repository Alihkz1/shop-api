package com.shop.service;

import com.shop.dto.UserProductSearchDto;
import com.shop.model.User;
import com.shop.model.UserProductSearch;
import com.shop.repository.ProductCacheRepository;
import com.shop.repository.UserProductSearchRepository;
import com.shop.shared.classes.BaseService;
import com.shop.shared.classes.Response;
import com.shop.shared.classes.UserThread;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserProductSearchService extends BaseService {

    private final UserProductSearchRepository repository;
    private final ProductCacheRepository cacheRepository;

    public void save(String searchQuery, Long userId) {
        Optional<UserProductSearch> findBySearch = repository.findBySearch(userId, searchQuery);
        if (findBySearch.isPresent()) return;
        UserProductSearch model = new UserProductSearch();
        model.setSearch(searchQuery);
        model.setUser(new User(userId));
        repository.save(model);
    }

    public ResponseEntity<Response> getByUserId() {
        Optional<List<UserProductSearch>> userSearchList = repository.findByUserId(UserThread.getUserId());
        if (userSearchList.isPresent()) {
            return successResponse(new UserProductSearchDto(userSearchList.get()));
        } else return successResponse();
    }

    public ResponseEntity<Response> deleteById(Long searchId) {
        repository.deleteById(searchId);
        return successResponse();
    }
}
