package com.ten.response.board;

import com.ten.domain.board.notice.NoticeBoard;
import com.ten.domain.board.notice.NoticeBoardFileEntity;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter @Setter
public class NoticeDetailResponse {

    private String title;
    private String contents;
    private LocalDateTime createdTime;
    private LocalDateTime updatedTime;
    private String createdBy;
    private String updatedBy;
    private int fileAttached;
    private List<MultipartFile> boardFile;
    private List<String> originalFileName;
    private List<String> storedFileName;

    public static NoticeDetailResponse toSaveWithoutFile(NoticeBoard board) {
        NoticeDetailResponse response = new NoticeDetailResponse();
        response.setTitle(board.getTitle());
        response.setContents(board.getContents());
        response.setCreatedTime(board.getCreatedTime());
        response.setUpdatedTime(board.getUpdatedTime());
        response.setCreatedBy(board.getCreatedBy());
        response.setUpdatedBy(board.getUpdatedBy());
        return response;
    }

    public static NoticeDetailResponse toSaveWithFile(NoticeBoard board) {
        NoticeDetailResponse response = new NoticeDetailResponse();
        response.setTitle(board.getTitle());
        response.setContents(board.getContents());
        response.setCreatedTime(board.getCreatedTime());
        response.setUpdatedTime(board.getUpdatedTime());
        response.setCreatedBy(board.getCreatedBy());
        response.setUpdatedBy(board.getUpdatedBy());
        if(board.getFileAttached() == 0) {
            response.setFileAttached(board.getFileAttached());
        } else {
            response.setFileAttached(board.getFileAttached());
            List<String> originalFileNameList = new ArrayList<>();
            List<String> storedFileNameList = new ArrayList<>();
            for(NoticeBoardFileEntity boardFileEntity : board.getBoardFileEntities()) {
                originalFileNameList.add(boardFileEntity.getOriginalFileName());
                storedFileNameList.add(boardFileEntity.getStoredFileName());
            }
            response.setOriginalFileName(originalFileNameList);
            response.setStoredFileName(storedFileNameList);
        }
        return response;
    }
}
















