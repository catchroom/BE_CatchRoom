package com.example.catchroom_be.Controller;


import com.example.catchroom_be.Entity.TestEntity;
import com.example.catchroom_be.Repository.TestEntityRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

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
    public String createTestEntity(@RequestParam String user) {
        TestEntity testEntity = new TestEntity(user);
        testEntityRepository.save(testEntity);
        return "Data saved successfully!";
    }


}
