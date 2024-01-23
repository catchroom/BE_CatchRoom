package com.example.catchroom_be.domain.buyhistory.service;

import com.example.catchroom_be.domain.accommodation.entity.Accommodation;
import com.example.catchroom_be.domain.accommodation.entity.Room;
import com.example.catchroom_be.domain.buyhistory.dto.request.BuyRequest;
import com.example.catchroom_be.domain.buyhistory.dto.response.PaymentResponse;
import com.example.catchroom_be.domain.buyhistory.dto.response.PurchaseDetailResponse;
import com.example.catchroom_be.domain.buyhistory.entity.BuyHistory;
import com.example.catchroom_be.domain.buyhistory.repository.BuyHistoryRepository;
import com.example.catchroom_be.domain.orderhistory.entity.OrderHistory;
import com.example.catchroom_be.domain.product.entity.Product;
import com.example.catchroom_be.domain.product.repository.ProductRepository;
import com.example.catchroom_be.domain.product.type.DealState;
import com.example.catchroom_be.domain.user.entity.DepositDetails;
import com.example.catchroom_be.domain.user.entity.User;
import com.example.catchroom_be.domain.user.enumlist.DepositType;
import com.example.catchroom_be.domain.user.repository.DepositDetailsRepository;
import com.example.catchroom_be.domain.user.repository.UserEntityRepository;
import com.example.catchroom_be.global.common.ApiResponse;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

@Service
@RequiredArgsConstructor
public class BuyHistoryServiceImpl implements BuyHistoryService {

    private final BuyHistoryRepository buyHistoryRepository;
    private final ProductRepository productRepository;
    private final UserEntityRepository userRepository;
    private final DepositDetailsRepository depositRepository;

    @Override
    @Transactional
    public ApiResponse buyProduct(User user, BuyRequest buyRequest) {

        Product product = productRepository.findById(buyRequest.productId())
                .orElseThrow(() -> new IllegalArgumentException("해당 상품이 존재하지 않습니다."));

        if (!product.getDealState().equals(DealState.ONSALE))
            return ApiResponse.create(2042, "판매 중인 상품이 아닙니다.");

        BuyHistory buyHistory = buyRequest.toEntity(user, product);
        buyHistory.setReservationNumber();
        product.updateDealState(DealState.DONEDEAL);
        product.updateEndDate(LocalDateTime.now());
        buyHistoryRepository.save(buyHistory);

        recordSellHistory(product);
        System.out.println("buyHistory = " + buyHistory.getId());

        return ApiResponse.create(2039, "상품 구매에 성공했습니다.");
    }

    @Override
    public ApiResponse getPaymentInfo(User user, Long productId) {

        Product product = productRepository.findById(productId).
                orElseThrow(() -> new IllegalArgumentException("상품이 존재하지 않습니다."));

        System.out.println("productId = " + productId);

        OrderHistory orderHistory = product.getOrderHistory();
        Accommodation accommodation = orderHistory.getAccommodation();
        Room room = orderHistory.getRoom();

        PaymentResponse.ProductInfo productInfo = setProductInfo(accommodation, room, orderHistory);
        PaymentResponse.BuyerInfo buyerInfo = setBuyerInfo(user);
        PaymentResponse.PaymentInfo paymentInfo = setPaymentInfo(product);


        return PurchaseDetailResponse.create(2040, new PaymentResponse.PaymentResponseData(
                productInfo, buyerInfo, paymentInfo));

    }

    @Override
    public ApiResponse purchaseHistory(User user, Long buyHistoryId) {
        BuyHistory buyHistory = buyHistoryRepository.findById(buyHistoryId).
                orElseThrow(() -> new IllegalArgumentException("구매 내역이 존재하지 않습니다."));

        Product product = buyHistory.getProduct();
        OrderHistory orderHistory = product.getOrderHistory();
        Accommodation accommodation = orderHistory.getAccommodation();
        Room room = orderHistory.getRoom();


        PurchaseDetailResponse.BuyerInfo buyerInfo = createBuyerInfo(user);
        PurchaseDetailResponse.UserInfo userInfo = createUserInfo(buyHistory);
        PurchaseDetailResponse.AccommodationInfo accommodationInfo = createAccommodationInfo(
                buyHistory, accommodation, orderHistory, room);
        PurchaseDetailResponse.SellPriceInfo sellPriceInfo = createSellPriceInfo(product);
        PurchaseDetailResponse.SellerInfo sellerInfo = createSellInfo(product);

        return PurchaseDetailResponse.create(2041, new PurchaseDetailResponse.PurchaseHistoryDetailData(
                buyerInfo, userInfo, accommodationInfo, sellPriceInfo, sellerInfo, room.getIntroduction()));
    }

    public static PurchaseDetailResponse.BuyerInfo createBuyerInfo(User user) {
        return new PurchaseDetailResponse.BuyerInfo(
                user.getName(), user.getPhonenumber());
    }

    public static PurchaseDetailResponse.UserInfo createUserInfo(BuyHistory buyHistory) {

        return new PurchaseDetailResponse.UserInfo(buyHistory.getVisitorName(), buyHistory.getVisitorPhoneNumber());
    }

    public static PurchaseDetailResponse.AccommodationInfo createAccommodationInfo(
            BuyHistory buyHistory,
            Accommodation accommodation,
            OrderHistory orderHistory,
            Room room
    ) {
        return new PurchaseDetailResponse.AccommodationInfo(
                buyHistory.getReservationNumber(),
                accommodation.getThumbnailUrl(),
                accommodation.getName(),
                accommodation.getAddress(),
                orderHistory.getCheckIn(),
                orderHistory.getCheckOut(),
                calPeriod(orderHistory.getCheckIn(), orderHistory.getCheckOut()),
                orderHistory.getTransportation(),
                room.getName(),
                room.getNormalCapacity(),
                room.getMaxCapacity());
    }

    public static PurchaseDetailResponse.SellPriceInfo createSellPriceInfo(Product product) {

        int sellPrice = product.getSellPrice();
        int discountRate = product.getDiscountRate();

        return new PurchaseDetailResponse.SellPriceInfo(
                sellPrice, discountRate, sellPrice * (100 - discountRate) / 100);
    }

    public static PurchaseDetailResponse.SellerInfo createSellInfo(Product product) {

        return new PurchaseDetailResponse.SellerInfo(product.getSeller().getNickName());
    }

    public static Long calPeriod(LocalDate checkIn, LocalDate checkOut) {

        return ChronoUnit.DAYS.between(checkIn, checkOut);
    }

    public static PaymentResponse.ProductInfo setProductInfo(
            Accommodation accommodation, Room room, OrderHistory orderHistory) {
        return new PaymentResponse.ProductInfo(
                accommodation.getName(),
                room.getName(),
                orderHistory.getCheckIn(),
                orderHistory.getCheckOut());
    }

    public static PaymentResponse.BuyerInfo setBuyerInfo(User user) {
        return new PaymentResponse.BuyerInfo(
                user.getName(), user.getPhonenumber());
    }

    public static PaymentResponse.PaymentInfo setPaymentInfo(Product product) {
        return new PaymentResponse.PaymentInfo(
                product.getSellPrice(),
                (int) (product.getSellPrice() * 0.05),
                (int) (product.getSellPrice() * 1.05)
        );
    }

    private void recordSellHistory(Product product) {
        User seller = userRepository.findById(product.getSeller().getId())
            .orElseThrow(() -> new IllegalArgumentException("해당 유저는 존재하지 않습니다."));
        seller.updateBalance(product.getActualProfit());

        DepositDetails depositDetail = DepositDetails.builder()
            .type(DepositType.DEPOSIT.getType())
            .money(product.getSellPrice())
            .info(product.getOrderHistory().getAccommodation().getName())
            .user(seller)
            .build();
        depositRepository.save(depositDetail);
    }
}
