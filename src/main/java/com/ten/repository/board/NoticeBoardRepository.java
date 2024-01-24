package com.ten.repository.board;

import com.ten.domain.board.notice.NoticeBoard;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NoticeBoardRepository extends JpaRepository<NoticeBoard, Long>, NoticeBoardRepositoryCustom {

    Page<NoticeBoard> findByTitleContaining(String title, Pageable pageable);

    Page<NoticeBoard> findByContentsContaining(String Contents, Pageable pageable);

    Page<NoticeBoard> findByCreatedByContaining(String createdBy, Pageable pageable);
}
