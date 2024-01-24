package com.ten.controller.board;

import com.ten.domain.board.BoardPeriodCode;
import com.ten.request.board.NoticeCreateRequest;
import com.ten.request.board.NoticeUpdateRequest;
import com.ten.response.board.NoticeCreateResponse;
import com.ten.response.board.NoticeDetailResponse;
import com.ten.response.board.NoticeListResponse;
import com.ten.response.board.NoticeUpdateResponse;
import com.ten.service.board.NoticeBoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequiredArgsConstructor
@RequestMapping("/notice")
public class NoticeBoardController {

    private final NoticeBoardService noticeBoardService;

    @PostMapping("/save")
    public ResponseEntity<NoticeCreateResponse> saveNotice(@ModelAttribute NoticeCreateRequest request) throws IOException {
        return ResponseEntity.ok(noticeBoardService.saveNotice(request));
    }

    @GetMapping("/{id}")
    public ResponseEntity<NoticeDetailResponse> findById(@PathVariable Long id) {
        return ResponseEntity.ok(noticeBoardService.findById(id));
    }

    @GetMapping("/list")
    public ResponseEntity<Page<NoticeListResponse>> findAll(@PageableDefault(page = 1, sort = "id")Pageable pageable) {
        return ResponseEntity.ok(noticeBoardService.findAll(pageable));
    }

    @GetMapping("/search/title")
    public ResponseEntity<Page<NoticeListResponse>> findByTitle(@RequestParam String title,
                                                                @PageableDefault(page = 1, sort = "id") Pageable pageable) {
        return ResponseEntity.ok(noticeBoardService.findByTitle(title, pageable));
    }

    @GetMapping("/search/contents")
    public ResponseEntity<Page<NoticeListResponse>> findByContents(@RequestParam String contents,
                                                                   @PageableDefault(page = 1, sort = "id") Pageable pageable) {
        return ResponseEntity.ok(noticeBoardService.findByContents(contents, pageable));
    }

    @GetMapping("/search/title_contents")
    public ResponseEntity<Page<NoticeListResponse>> findByTitleAndContents(@RequestParam(required = false) String search,
                                                                           @PageableDefault(page = 1, sort = "id") Pageable pageable) {
        return ResponseEntity.ok(noticeBoardService.findByTitleAndContents(search, pageable));
    }

    @GetMapping("/search/createdBy")
    public ResponseEntity<Page<NoticeListResponse>> findByCreatedBy(@RequestParam String createdName,
                                                                    @PageableDefault(page = 1, sort = "id") Pageable pageable) {
        return ResponseEntity.ok(noticeBoardService.findByCreatedBy(createdName, pageable));
    }

    @GetMapping("/search/period")
    public ResponseEntity<Page<NoticeListResponse>> findByPeriod(@RequestParam(required = false) BoardPeriodCode periodCode,
                                                                 @PageableDefault(page = 1, sort = "id") Pageable pageable) {
        return ResponseEntity.ok(noticeBoardService.findByPeriod(periodCode, pageable));
    }

    @GetMapping("/search/title_period")
    public ResponseEntity<Page<NoticeListResponse>> findByTitleWithPeriod(@RequestParam BoardPeriodCode periodCode,
                                                                    @RequestParam String title,
                                                                    @PageableDefault(page = 1, sort = "id") Pageable pageable) {
        return ResponseEntity.ok(noticeBoardService.findByTitleWithPeriod(periodCode, title, pageable));
    }

    @GetMapping("/search/contents_period")
    public ResponseEntity<Page<NoticeListResponse>> findByContentsWithPeriod(@RequestParam BoardPeriodCode periodCode,
                                                                             @RequestParam String contents,
                                                                             @PageableDefault(page = 1, sort = "id") Pageable pageable) {
        return ResponseEntity.ok(noticeBoardService.findByContentsWithPeriod(periodCode, contents, pageable));
    }

    @PutMapping("/{id}")
    public ResponseEntity<NoticeUpdateResponse> update(@PathVariable Long id,
                                                       @ModelAttribute NoticeUpdateRequest request) throws IOException {
        return ResponseEntity.ok(noticeBoardService.update(id, request));
    }
}




































