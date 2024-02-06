package com.skcc.cloud.member.member.application.port.in;

import lombok.*;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.data.domain.Sort.Order;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MemberQueryRequestDTO {
    final static int DEFAULT_SIZE = 10;
    final static int MAX_SIZE = 50;

    // 검색 조건
    private List<MemberKeyType> searchKeys;
    private List<String> searchValues;

    // 페이징 조건
    private int page = 1;
    private int size = DEFAULT_SIZE;

    // 정렬 조건
    private List<MemberKeyType> sortKeys;
    private List<MemberSortType> sortValues;

    // 페이지 Default 세팅
    public void setPage(int page) {
        this.page = page <= 0 ? 1 : page;
    }

    // 페이지 사이즈 Default 세팅
    public void setSize(int size) {
        this.size = size > MAX_SIZE ? DEFAULT_SIZE : size;
    }

    // 페이징 및 정렬 객체 반환
    public PageRequest of() {
        List<Order> sortList = null;
        if (sortKeys != null && sortKeys.size() > 0) {
            sortList = new ArrayList<Order>();

            for (int i = 0; i < sortKeys.size(); i++) {
                MemberKeyType key = sortKeys.get(i);
                MemberSortType value = sortValues.get(i);
                Order sortOrder = new Order(Sort.Direction.valueOf(value.name()), key.name());
                sortList.add(sortOrder);
            }
        }

        if (sortList != null && sortList.size() > 0) {
            return PageRequest.of(page - 1, size, Sort.by(sortList));
        } else {
            return PageRequest.of(page - 1, size);
        }
    }

    public enum MemberKeyType {
        name,
        email
    }

    public enum MemberSortType {
        ASC,
        DESC
    }
}
