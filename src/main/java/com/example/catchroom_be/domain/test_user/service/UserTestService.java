package com.example.catchroom_be.domain.test_user.service;

import com.example.catchroom_be.domain.product.service.OrderHistoryService;
import com.example.catchroom_be.domain.test_user.entity.Member;
import com.example.catchroom_be.domain.test_user.repository.MemberRepository;


import java.util.ArrayList;
import java.util.List;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 이 클래스는 서버를 킬때마다 실행되는 클래스입니다.
 * TestUser 계정을 만드는 로직과
 * 그 TestUser에 OrderHistory (야놀자 숙소 구매 내역) 데이터를 넣는 로직입니다.
 * 만약 데이터를 더 넣고 싶거나 하시면 이 코드를 활용해서 넣으시면 됩니다.
 *
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class UserTestService implements ApplicationRunner {
    //    private final UserTestRepository userTestRepository;
    private final MemberRepository memberRepository;
    private final OrderHistoryService orderHistoryService;

    @Override
    public void run(ApplicationArguments args) throws Exception {
//        insertUserTestOrderHistory();
    }

    @Transactional
    public void insertUserTest() {
        /** UserTest 넣는 로직 **/
        List<Member> userTestList = new ArrayList<>();
        for (int i = 1; i <= 3; i++) {
            String name = "test" + i;
            Member userTest = Member.builder().name(name).build();
            userTestList.add(userTest);
        }
        memberRepository.saveAll(userTestList);
    }

    @Transactional
    public void insertUserTestOrderHistory() {
        /** 모든 UserTest에 OrderHistory 넣는 로직
         * 만약 특정 계정에 값을 넣고 싶다면 findAll 대신 특정 계정의 id만을 사용해서 활용하세요
         **/

        List<Member> userTestList = memberRepository.findAll();
        for (Member userTest : userTestList) {
            orderHistoryService.insertTestDataOrderHistory(userTest);
        }
    }



}
