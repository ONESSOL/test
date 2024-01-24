package com.ten.repository.board;

import com.ten.domain.board.notice.NoticeBoard;
import com.ten.domain.board.notice.NoticeBoardFileEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface NoticeBoardFileRepository extends JpaRepository<NoticeBoardFileEntity, Long> {

    Optional<NoticeBoardFileEntity> findByNoticeBoard(NoticeBoard noticeBoard);
}
