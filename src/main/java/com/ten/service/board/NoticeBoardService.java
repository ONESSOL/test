package com.ten.service.board;

import com.ten.domain.board.BoardPeriodCode;
import com.ten.domain.board.notice.NoticeBoard;
import com.ten.domain.board.notice.NoticeBoardFileEntity;
import com.ten.domain.member.Member;
import com.ten.exception.board.BoardNotFoundException;
import com.ten.repository.board.NoticeBoardFileRepository;
import com.ten.repository.board.NoticeBoardRepository;
import com.ten.repository.member.MemberRepository;
import com.ten.request.board.NoticeCreateRequest;
import com.ten.request.board.NoticeUpdateRequest;
import com.ten.response.board.NoticeCreateResponse;
import com.ten.response.board.NoticeDetailResponse;
import com.ten.response.board.NoticeListResponse;
import com.ten.response.board.NoticeUpdateResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.Optional;
import java.util.UUID;

import static org.springframework.data.domain.Sort.Direction.DESC;

@Service
@RequiredArgsConstructor
public class NoticeBoardService {

    private final NoticeBoardRepository noticeBoardRepository;
    private final NoticeBoardFileRepository noticeBoardFileRepository;
    private final MemberRepository memberRepository;

    @Transactional
    public NoticeCreateResponse saveNotice(NoticeCreateRequest request) throws IOException {

        if (request.getBoardFile().get(0).isEmpty()) {
            request.setFileAttached(0);
            NoticeBoard board = noticeBoardRepository.save(
                    NoticeBoard.builder()
                            .title(request.getTitle())
                            .contents(request.getContents())
                            .fileAttached(request.getFileAttached())
                            .build());
            return NoticeCreateResponse.toSaveWithoutFile(board);
        } else {
            request.setFileAttached(1);
            NoticeBoard board = noticeBoardRepository.save(
                    NoticeBoard.builder()
                            .title(request.getTitle())
                            .contents(request.getContents())
                            .fileAttached(request.getFileAttached())
                            .build());
            String originalFileName = "";
            String storedFileName = "";
            for (MultipartFile boardFile : request.getBoardFile()) {
                originalFileName = boardFile.getOriginalFilename();
                storedFileName = UUID.randomUUID().toString() + "_" + originalFileName;
                String savePath = "C:\\Users\\user\\Desktop\\board\\image\\" + storedFileName;
                boardFile.transferTo(new File(savePath));
                noticeBoardFileRepository.save(
                        NoticeBoardFileEntity.builder()
                                .originalFileName(originalFileName)
                                .storedFileName(storedFileName)
                                .noticeBoard(board)
                                .build());
            }
            return NoticeCreateResponse.toSaveWithFile(board);
        }
    }

    @Transactional(readOnly = true)
    public NoticeDetailResponse findById(Long id) {

        NoticeBoard noticeBoard = noticeBoardRepository.findById(id).orElseThrow(BoardNotFoundException::new);
        if (noticeBoard.getFileAttached() == 0) {
            return NoticeDetailResponse.toSaveWithoutFile(noticeBoard);
        }
        return NoticeDetailResponse.toSaveWithFile(noticeBoard);
    }

    @Transactional(readOnly = true)
    public Page<NoticeListResponse> findAll(Pageable pageable) {

        int firstPage = pageable.getPageNumber() - 1;
        PageRequest pageRequest = PageRequest.of(firstPage, 10, Sort.by(DESC, "id"));
        Page<NoticeBoard> boardList = noticeBoardRepository.findAll(pageRequest);
        return boardList.map(NoticeListResponse::toSave);
    }

    @Transactional(readOnly = true)
    public Page<NoticeListResponse> findByTitle(String title, Pageable pageable) {
        int firstPage = pageable.getPageNumber() - 1;
        PageRequest pageRequest = PageRequest.of(firstPage, 10, Sort.by(DESC, "id"));
        Page<NoticeBoard> boardList = noticeBoardRepository.findByTitleContaining(title, pageRequest);
        return boardList.map(NoticeListResponse::toSave);
    }

    @Transactional
    public Page<NoticeListResponse> findByContents(String contents, Pageable pageable) {
        int firstPage = pageable.getPageNumber() - 1;
        PageRequest pageRequest = PageRequest.of(firstPage, 10, Sort.by(DESC, "id"));
        Page<NoticeBoard> boardList = noticeBoardRepository.findByContentsContaining(contents, pageRequest);
        return boardList.map(NoticeListResponse::toSave);
    }

    @Transactional
    public Page<NoticeListResponse> findByTitleAndContents(String search, Pageable pageable) {

        int firstPage = pageable.getPageNumber() - 1;
        PageRequest pageRequest = PageRequest.of(firstPage, 10, Sort.by(DESC, "id"));
        Page<NoticeBoard> boardList = noticeBoardRepository.findByTitleAndContents(search, pageRequest);
        return boardList.map(NoticeListResponse::toSave);
    }

    @Transactional
    public Page<NoticeListResponse> findByCreatedBy(String createdName, Pageable pageable) {

        Optional<Member> optionalMember = memberRepository.findByUsername(createdName);
        String createdBy = "";
        if(optionalMember.isEmpty()) {
            createdBy = String.valueOf(-1);
        } else {
            createdBy = String.valueOf(optionalMember.get().getId());
        }
        int firstPage = pageable.getPageNumber() - 1;
        PageRequest pageRequest = PageRequest.of(firstPage, 10, Sort.by(DESC, "id"));
        Page<NoticeBoard> boardList = noticeBoardRepository.findByCreatedByContaining(createdBy, pageRequest);
        return boardList.map(NoticeListResponse::toSave);
    }

    @Transactional
    public Page<NoticeListResponse> findByPeriod(BoardPeriodCode periodCode, Pageable pageable) {

        int firstPage = pageable.getPageNumber() - 1;
        PageRequest pageRequest = PageRequest.of(firstPage, 10, Sort.by(DESC, "id"));
        Page<NoticeBoard> boardList = noticeBoardRepository.findByPeriod(periodCode, pageRequest);
        return boardList.map(NoticeListResponse::toSave);
    }

    @Transactional
    public Page<NoticeListResponse> findByTitleWithPeriod(BoardPeriodCode periodCode, String title, Pageable pageable) {

        int firstPage = pageable.getPageNumber() - 1;
        PageRequest pageRequest = PageRequest.of(firstPage, 10, Sort.by(DESC, "id"));
        Page<NoticeBoard> boardList = noticeBoardRepository.findByTitleWithPeriod(periodCode, title, pageRequest);
        return boardList.map(NoticeListResponse::toSave);
    }

    @Transactional
    public Page<NoticeListResponse> findByContentsWithPeriod(BoardPeriodCode periodCode, String contents, Pageable pageable) {

        int firstPage = pageable.getPageNumber() - 1;
        PageRequest pageRequest = PageRequest.of(firstPage, 10, Sort.by(DESC, "id"));
        Page<NoticeBoard> boardList = noticeBoardRepository.findByContentsWithPeriod(periodCode, contents, pageRequest);
        return boardList.map(NoticeListResponse::toSave);
    }

    @Transactional
    public NoticeUpdateResponse update(Long boardId, NoticeUpdateRequest request) throws IOException {

        NoticeBoard board = noticeBoardRepository.findById(boardId).orElseThrow(BoardNotFoundException::new);
        if (board.getBoardFileEntities().isEmpty()) {
            if (request.getBoardFile().get(0).isEmpty()) {
                request.setFileAttached(0);
                board.update(request.getTitle(), request.getContents(), request.getFileAttached());
            } else {
                request.setFileAttached(1);
                board.update(request.getTitle(), request.getContents(), request.getFileAttached());
                String originalFileName = "";
                String storedFileName = "";
                for (MultipartFile boardFile : request.getBoardFile()) {
                    originalFileName = boardFile.getOriginalFilename();
                    storedFileName = UUID.randomUUID().toString() + "_" + originalFileName;
                    String savePath = "C:\\Users\\user\\Desktop\\board\\4\\" + storedFileName;
                    boardFile.transferTo(new File(savePath));
                    noticeBoardFileRepository.save(
                            NoticeBoardFileEntity.builder()
                                    .originalFileName(originalFileName)
                                    .storedFileName(storedFileName)
                                    .noticeBoard(board)
                                    .build());
                }
            }
        } else {
            if (request.getBoardFile().get(0).isEmpty()) {
                request.setFileAttached(0);
                for (NoticeBoardFileEntity boardFileEntity : board.getBoardFileEntities()) {
                    deleteFile(boardFileEntity);
                }
                board.getBoardFileEntities().removeAll(board.getBoardFileEntities());
                board.update(request.getTitle(), request.getContents(), request.getFileAttached());
            } else {
                request.setFileAttached(1);
                for (NoticeBoardFileEntity boardFileEntity : board.getBoardFileEntities()) {
                    deleteFile(boardFileEntity);
                }
                board.getBoardFileEntities().removeAll(board.getBoardFileEntities());
                for (MultipartFile boardFile : request.getBoardFile()) {
                    String originalFileName = boardFile.getOriginalFilename();
                    String storedFileName = UUID.randomUUID() + "_" + originalFileName;
                    String savePath = "C:\\Users\\user\\Desktop\\board\\4\\" + storedFileName;
                    boardFile.transferTo(new File(savePath));
                    noticeBoardFileRepository.save(NoticeBoardFileEntity.builder()
                                    .originalFileName(originalFileName)
                                    .storedFileName(storedFileName)
                                    .noticeBoard(board)
                                    .build());
                }
                board.update(request.getTitle(), request.getContents(), request.getFileAttached());
            }
        }
        return NoticeUpdateResponse.toSave(board);
    }

    private void deleteFile(NoticeBoardFileEntity boardFileEntity) {
        noticeBoardFileRepository.delete(boardFileEntity);
    }
}

































