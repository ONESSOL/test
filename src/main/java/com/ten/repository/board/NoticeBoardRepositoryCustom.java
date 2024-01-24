package com.ten.repository.board;

import com.ten.domain.board.BoardPeriodCode;
import com.ten.domain.board.notice.NoticeBoard;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface NoticeBoardRepositoryCustom {

    Page<NoticeBoard> findByTitleAndContents(String search, Pageable pageable);

    Page<NoticeBoard> findByPeriod(BoardPeriodCode periodCode, Pageable pageable);

    Page<NoticeBoard> findByTitleWithPeriod(BoardPeriodCode periodCode, String title, Pageable pageable);

    Page<NoticeBoard> findByContentsWithPeriod(BoardPeriodCode periodCode, String contents, Pageable pageable);
}
