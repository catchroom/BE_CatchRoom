package com.example.catchroom_be.domain.orderhistory.service;

import com.example.catchroom_be.domain.accommodation.entity.Room;
import com.example.catchroom_be.domain.accommodation.repository.RoomRepository;
import com.example.catchroom_be.domain.orderhistory.dto.OrderHistoryCandidateResponse;
import com.example.catchroom_be.domain.orderhistory.entity.OrderHistory;
import com.example.catchroom_be.domain.orderhistory.repository.OrderHistoryRepository;
import com.example.catchroom_be.domain.product.type.TransportationType;
import com.example.catchroom_be.domain.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderHistoryService {
    private final OrderHistoryRepository orderHistoryRepository;
    private final RoomRepository roomRepository;

    @Transactional(readOnly = true)
    public List<OrderHistoryCandidateResponse> findProductCandidate(User user) {
        List<OrderHistory> orderHistoryList = orderHistoryRepository
            .findAllByIsFreeCancelAndIsSaleAndUserId(false, false,user.getId());
        return DatefilterOrderHisotryList(orderHistoryList);
    }

    private List<OrderHistoryCandidateResponse> DatefilterOrderHisotryList(List<OrderHistory> orderHistoryList) {
        return orderHistoryList.stream()
            .filter(orderHistory -> orderHistory.getCheckIn().isAfter(LocalDate.now().minusDays(1)))
            .map(OrderHistoryCandidateResponse::fromEntity)
            .collect(Collectors.toList());
    }

    @Transactional
    public void insertDataOrderHistory(User user) {

        LocalDate startDate = LocalDate.of(2024, 01, 15);
        LocalDate endDate = LocalDate.of(2024, 02, 28);
        LocalDate currDate = startDate;

        //1월 15일부터 2월 28일까지 (1박 2일 or 2박 3일 or 3박 4일 데이터), 랜덤하게 데이터셋 넣음.
        //숙박 데이터 랜덤

        List<OrderHistory> orderHistoryList = new ArrayList<>();

        Random random = new Random();

        while (!currDate.isAfter(endDate)) {
            long roomIdRandom = random.nextLong(815L) + 1;
            Room room = null;
            while (true) {
                room = roomRepository.findById(roomIdRandom).orElseThrow();
                // 이미지 있는 객실만 가져옴.
                if (room.getRoomImageList().size() > 0) break;
            }

            int period = random.nextInt(3) + 1;
            LocalDate checkIn = currDate;
            LocalDate checkOut = currDate.plusDays(period);

            int price = room.getPrice() * period; //구매 가격

            OrderHistory orderHistory = OrderHistory.builder()
                    .room(room).accommodation(room.getAccommodation())
                    .checkIn(checkIn).checkOut(checkOut)
                    .price(price).user(user).transportation(getTransportationType(random))
                    .isFreeCancel(getIsFreeCancel(random))
                    .isSale(false)
                    .build();

            orderHistoryList.add(orderHistory);
            int nextDays = random.nextInt(5) + 1;
            currDate = currDate.plusDays(nextDays);
        }
        orderHistoryRepository.saveAll(orderHistoryList);
    }

    private TransportationType getTransportationType(Random random) {
        int transportationRandomInt = random.nextInt(2);
        TransportationType transportationType;
        if (transportationRandomInt == 0) transportationType = TransportationType.CAR;
        else transportationType = TransportationType.WALK;
        return transportationType;
    }

    private boolean getIsFreeCancel(Random random) {
        int freeCancel = random.nextInt(10) + 1;
        if (freeCancel == 1) return true;
        return false;
    }
}
