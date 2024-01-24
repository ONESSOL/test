package com.ten.repository.board;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.ten.domain.board.BoardPeriodCode;
import com.ten.domain.board.notice.NoticeBoard;
import jakarta.persistence.EntityManager;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static com.ten.domain.board.notice.QNoticeBoard.noticeBoard;


public class NoticeBoardRepositoryImpl implements NoticeBoardRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    public NoticeBoardRepositoryImpl(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }

    @Override
    public Page<NoticeBoard> findByTitleAndContents(String search, Pageable pageable) {
        List<NoticeBoard> results = queryFactory
                .select(noticeBoard)
                .from(noticeBoard)
                .where(firstTitleContains(search).or(contentsContains(search)))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        JPAQuery<Long> countQuery = queryFactory
                .select(noticeBoard.count())
                .from(noticeBoard)
                .where(titleContains(search), contentsContains(search));

        return PageableExecutionUtils.getPage(results, pageable, countQuery::fetchOne);
    }

    @Override
    public Page<NoticeBoard> findByPeriod(BoardPeriodCode periodCode, Pageable pageable) {
        List<NoticeBoard> results = queryFactory
                .select(noticeBoard)
                .from(noticeBoard)
                .where(createdTimeBeforePeriod(periodCode))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        JPAQuery<Long> countQuery = queryFactory
                .select(noticeBoard.count())
                .from(noticeBoard)
                .where(createdTimeBeforePeriod(periodCode));

        return PageableExecutionUtils.getPage(results, pageable, countQuery::fetchOne);
    }

    @Override
    public Page<NoticeBoard> findByTitleWithPeriod(BoardPeriodCode periodCode, String title, Pageable pageable) {
        List<NoticeBoard> results = queryFactory
                .select(noticeBoard)
                .from(noticeBoard)
                .where(createdTimeBeforePeriod(periodCode), titleContains(title))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        JPAQuery<Long> countQuery = queryFactory
                .select(noticeBoard.count())
                .from(noticeBoard)
                .where(createdTimeBeforePeriod(periodCode), titleContains(title));

        return PageableExecutionUtils.getPage(results, pageable, countQuery::fetchOne);
    }

    @Override
    public Page<NoticeBoard> findByContentsWithPeriod(BoardPeriodCode periodCode, String contents, Pageable pageable) {
        List<NoticeBoard> results = queryFactory
                .select(noticeBoard)
                .from(noticeBoard)
                .where(createdTimeBeforePeriod(periodCode), contentsContains(contents))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        JPAQuery<Long> countQuery = queryFactory
                .select(noticeBoard.count())
                .from(noticeBoard)
                .where(createdTimeBeforePeriod(periodCode), contentsContains(contents));

        return PageableExecutionUtils.getPage(results, pageable, countQuery::fetchOne);
    }

    private BooleanExpression titleContains(String title) {
        return StringUtils.hasText(title) ? noticeBoard.title.contains(title) : null;
    }

    private BooleanExpression firstTitleContains(String title) {
        if(title == null) {
            title = "";
        }
        return noticeBoard.title.contains(title);
    }

    private BooleanExpression contentsContains(String contents) {
        return StringUtils.hasText(contents) ? noticeBoard.contents.contains(contents) : null;
    }

    private BooleanExpression createdTimeBeforePeriod(BoardPeriodCode periodCode) {
        Optional<BoardPeriodCode> nullCode = Optional.ofNullable(periodCode);
        BoardPeriodCode boardPeriodCode = nullCode.orElse(BoardPeriodCode.ALL);
        if(boardPeriodCode.getKey().equals("all")) {
            return noticeBoard.createdTime.loe(noticeBoard.createdTime);
        }
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime before = now.minusWeeks(Long.parseLong(periodCode.getKey()));
        return noticeBoard.createdTime.between(before, now);
    }
}





























