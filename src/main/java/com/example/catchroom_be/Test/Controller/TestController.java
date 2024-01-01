package com.example.catchroom_be.Test.Controller;


import com.example.catchroom_be.Test.Entity.TestEntity;
import com.example.catchroom_be.Test.Repository.TestEntityRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/v1/test")
@RequiredArgsConstructor
public class TestController {

    private final TestEntityRepository testEntityRepository;

    @GetMapping("/do")
    public String Test() {
        return "나는 박건우입니다.";
    }
    @GetMapping("/cicd")
    public String cicdTest() {
        return "CI/CD 배포 테스트입니다!";
        }
    @GetMapping("/cicd2")
    public String cicdTest2() {
        return "코드 디플로이 테스트입니다.";
    }

    @PostMapping("/dbtest")
    @Transactional
    public String createTestEntity(@RequestParam String users) {
        TestEntity testEntity = new TestEntity(users);
        testEntityRepository.save(testEntity);
        return "Data saved successfully!";
    }
    @GetMapping("/check")
    public String testDB(Long id) {
        Optional<TestEntity> testEntity= testEntityRepository.findById(id);
        TestEntity test = testEntity.get();
        return test.getusers();
    }


}
