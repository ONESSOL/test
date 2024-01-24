package com.ten.domain.board.notice;

import com.ten.domain.BaseEntity;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static jakarta.persistence.CascadeType.ALL;

@Entity
@Getter
public class NoticeBoard extends BaseEntity {

    @Id @GeneratedValue
    @Column(name = "notice_board_id")
    private Long id;
    private String title;
    @Lob
    private String contents;
    private int fileAttached;
    @OneToMany(mappedBy = "noticeBoard", cascade = ALL, orphanRemoval = true)
    private List<NoticeBoardFileEntity> boardFileEntities = new ArrayList<>();

    @Builder
    public NoticeBoard(String title, String contents, int fileAttached) {
        this.title = title;
        this.contents = contents;
        this.fileAttached = fileAttached;
    }

    protected NoticeBoard() {
    }

    public void addBoardFile(NoticeBoardFileEntity boardFileEntity) {
        this.getBoardFileEntities().add(boardFileEntity);
    }

    public void update(String title, String contents, int fileAttached) {
        this.title = title;
        this.contents = contents;
        this.fileAttached = fileAttached;
    }
}




















