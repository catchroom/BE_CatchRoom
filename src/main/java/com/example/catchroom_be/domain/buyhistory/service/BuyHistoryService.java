package com.example.catchroom_be.domain.buyhistory.service;

import com.example.catchroom_be.domain.buyhistory.dto.request.BuyRequest;
import com.example.catchroom_be.domain.user.entity.User;
import com.example.catchroom_be.global.common.ApiResponse;

public interface BuyHistoryService {

    ApiResponse buyProduct(User user, BuyRequest buyRequest);

    ApiResponse getPaymentInfo(User user, Long productId);

    ApiResponse purchaseHistory(User user, Long buyHistoryId);
}
