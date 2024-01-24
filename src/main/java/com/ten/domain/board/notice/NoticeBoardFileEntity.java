package com.ten.domain.board.notice;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;

import static jakarta.persistence.FetchType.LAZY;

@Entity
@Getter
public class NoticeBoardFileEntity {

    @Id @GeneratedValue
    @Column(name = "board_file_id")
    private Long id;
    private String originalFileName;
    private String storedFileName;
    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "board_id")
    private NoticeBoard noticeBoard;

    @Builder
    public NoticeBoardFileEntity(String originalFileName, String storedFileName, NoticeBoard noticeBoard) {
        this.originalFileName = originalFileName;
        this.storedFileName = storedFileName;
        createBoard(noticeBoard);
    }

    protected NoticeBoardFileEntity() {
    }

    public void createBoard(NoticeBoard board) {
        this.noticeBoard = board;
        board.addBoardFile(this);
    }
}

























