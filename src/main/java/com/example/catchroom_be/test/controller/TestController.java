package com.example.catchroom_be.test.controller;


import com.example.catchroom_be.global.common.ApiResponse;
import com.example.catchroom_be.global.exception.SuccessMessage;
import com.example.catchroom_be.test.entity.TestEntity;
import com.example.catchroom_be.test.repository.TestEntityRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/v1/test")
@RequiredArgsConstructor
public class TestController {

    private final TestEntityRepository testEntityRepository;

    @GetMapping("/do")
    public ResponseEntity<ApiResponse<SuccessMessage>> Test() {
        return ResponseEntity.ok(ApiResponse.create(9999,SuccessMessage.createSuccessMessage("테스트1 성공")));
    }
    @GetMapping("/cicd")
    public ResponseEntity<ApiResponse<SuccessMessage>> cicdTest() {
        return ResponseEntity.ok(ApiResponse.create(9998,SuccessMessage.createSuccessMessage("테스트2 성공")));
        }
    @GetMapping("/cicd2")
    public ResponseEntity<ApiResponse<SuccessMessage>> cicdTest2() {
        return ResponseEntity.ok(ApiResponse.create(9997,SuccessMessage.createSuccessMessage("테스트3 성공")));
    }

    @PostMapping("/dbtest")
    @Transactional
    public ResponseEntity<ApiResponse<TestEntity>> createTestEntity(@RequestParam String users) {
        TestEntity testEntity = new TestEntity(users);
        testEntityRepository.save(testEntity);
        return ResponseEntity.ok(ApiResponse.create(9996,testEntity));
    }
    @GetMapping("/check")
    public ResponseEntity<ApiResponse<Optional<TestEntity>>> testDB(Long id) {
        Optional<TestEntity> testEntity= testEntityRepository.findById(id);
        TestEntity test = testEntity.get();
        return ResponseEntity.ok(ApiResponse.create(9997,testEntity));
    }


}
