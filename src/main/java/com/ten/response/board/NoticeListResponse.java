package com.ten.response.board;

import com.ten.domain.board.notice.NoticeBoard;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter @Setter
public class NoticeListResponse {

    private Long id;
    private String title;
    private LocalDateTime createdTime;
    private LocalDateTime updatedTime;
    private String createdBy;
    private String updatedBy;

    public static NoticeListResponse toSave(NoticeBoard board) {
        NoticeListResponse response = new NoticeListResponse();
        response.setId(board.getId());
        response.setTitle(board.getTitle());
        response.setCreatedTime(board.getCreatedTime());
        response.setUpdatedTime(board.getUpdatedTime());
        response.setCreatedBy(board.getCreatedBy());
        response.setUpdatedBy(board.getUpdatedBy());
        return response;
    }
}


























