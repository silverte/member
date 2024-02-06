package com.skcc.cloud.member.member.adapter.out.persistence;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.skcc.cloud.member.member.application.port.out.MemberQueryPersistencePort;
import com.skcc.cloud.member.member.domain.Member;
import com.skcc.cloud.member.member.domain.QMember;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

import static com.skcc.cloud.member.member.application.port.in.MemberQueryRequestDTO.MemberKeyType;

@Slf4j
@Repository
@RequiredArgsConstructor
public class MemberQueryPersistence implements MemberQueryPersistencePort {
    private final JPAQueryFactory query;
    private final QMember member = QMember.member;

    @Override
    public Page<Member> searchAllAnd(List<MemberKeyType> searchKeys, List<String> searchValues, Pageable pageable) {
        log.debug("[Persistence] MemberQueryDslRepository Called - searchAllAnd {}{}{}", searchKeys, searchValues, pageable);

        List<Member> content = query
                .selectFrom(member)
                .where(likeMemberSearchKeys(WhereType.AND, searchKeys, searchValues))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        Long count = query
                .select(member.count())
                .from(member)
                .where(likeMemberSearchKeys(WhereType.AND, searchKeys, searchValues))
                .fetchOne();

        return new PageImpl<>(content, pageable, count);
    }

    @Override
    public Page<Member> searchAllOr(List<MemberKeyType> searchKeys, List<String> searchValues, Pageable pageable) {
        log.debug("[Persistence] MemberQueryDslRepository Called - searchAllOr {}{}{}", searchKeys, searchValues, pageable);

        List<Member> content = query
                .selectFrom(member)
                .where(likeMemberSearchKeys(WhereType.OR, searchKeys, searchValues))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        Long count = query
                .select(member.count())
                .from(member)
                .where(likeMemberSearchKeys(WhereType.OR, searchKeys, searchValues))
                .fetchOne();

        return new PageImpl<>(content, pageable, count);
    }

    private BooleanExpression likeMemberSearchKeys(WhereType whereType, List<MemberKeyType> searchKeys, List<String> searchValues) {
        if (searchKeys == null) return null;

        List<BooleanExpression> expressions = new ArrayList<>();

        for (int i = 0; i < searchKeys.size(); i++) {
            MemberKeyType key = searchKeys.get(i);
            String value = searchValues.get(i);

            expressions.add(switch (key) {
                case name -> member.name.like("%" + value + "%");
                case email -> member.email.like("%" + value + "%");
                default -> throw new IllegalStateException("Unexpected value: " + key);
            });
        }
        if (whereType == WhereType.AND) return Expressions.allOf(expressions.toArray(BooleanExpression[]::new));
        else return Expressions.anyOf(expressions.toArray(BooleanExpression[]::new));
    }
}
