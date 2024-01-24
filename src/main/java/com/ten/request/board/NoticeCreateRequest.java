package com.ten.request.board;

import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@Getter @Setter
public class NoticeCreateRequest {

    private String title;
    private String contents;
    private int fileAttached;
    private List<MultipartFile> boardFile;


}
