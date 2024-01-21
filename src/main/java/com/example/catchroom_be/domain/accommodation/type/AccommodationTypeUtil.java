package com.example.catchroom_be.domain.accommodation.type;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class AccommodationTypeUtil {
    private final static Map<Integer, AccommodationType> map = new HashMap<>();

    static {
        map.put(0, AccommodationType.HOTEL);
        map.put(1, AccommodationType.RESORT);
        map.put(2, AccommodationType.PENSION);
        map.put(3, AccommodationType.MOTEL);
        map.put(4, AccommodationType.POOL_VILLA);
    }

    public enum AccommodationType {
        HOTEL,
        RESORT,
        PENSION,
        MOTEL,
        POOL_VILLA
        ;

    }

    /**
     * "0,1,2" -> {HOTEL, RESORT, PENSION} 으로 바꿔주는 util 클래스
     * @param accommodationTypeStr (ex. "0,1,2" )
     * @return {HOTEL, RESORT, PENSION}
     */
    public static List<AccommodationType> getAccommodationTypeList(String accommodationTypeStr) {
        return Arrays.stream(accommodationTypeStr.split(","))
                .mapToInt(Integer::parseInt)
                .mapToObj(it -> AccommodationTypeUtil.map.get(it))
                .collect(Collectors.toList());
    }


}
