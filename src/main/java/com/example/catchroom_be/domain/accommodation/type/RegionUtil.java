package com.example.catchroom_be.domain.accommodation.type;

import com.example.catchroom_be.domain.accommodation.type.AccommodationTypeUtil.AccommodationType;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class RegionUtil {

    private final static Map<Integer, String> map = new HashMap<>();

    static {
        map.put(0, "서울");
        map.put(1, "경기");
        map.put(2, "인천");
        map.put(3, "강원");
        map.put(4, "제주");
        map.put(5, "대전");
        map.put(6, "충청북도");
        map.put(7, "충청남도");
        map.put(8, "부산");
        map.put(9, "울산");
        map.put(10, "경상남도");
        map.put(11, "대구");
        map.put(12, "경상북도");
        map.put(13, "광주");
        map.put(14, "전라남도");
        map.put(15, "전라북도");
    }

    /**
     * regionStr = "0,1,2" 이면, {"서울", "경기", "인천"} 으로 바꿔주는 util 클래스
     * @param regionStr (ex. "0,1,2" )
     * @return {"서울", "경기", "인천"}
     */
    public static List<String> getRegionList(String regionStr) {
        return Arrays.stream(regionStr.split(","))
                .mapToInt(Integer::parseInt)
                .mapToObj(it -> RegionUtil.map.get(it))
                .collect(Collectors.toList());
    }

}
