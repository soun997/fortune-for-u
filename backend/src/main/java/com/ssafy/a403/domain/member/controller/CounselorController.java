package com.ssafy.a403.domain.member.controller;

import com.ssafy.a403.domain.member.dto.CounselorInfoResponse;
import com.ssafy.a403.domain.member.dto.UpdateCounselorRequest;
import com.ssafy.a403.domain.member.dto.UpdateCounselorResponse;
import com.ssafy.a403.domain.member.entity.Counselor;
import com.ssafy.a403.domain.member.service.CounselorService;
import com.ssafy.a403.domain.model.CounselorType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@Slf4j
public class CounselorController {

    private final CounselorService counselorService;

    /**
     * 상담사 정보 조회
     */
    @GetMapping("/api/counselors/{counselorNo}")
    public ResponseEntity<CounselorInfoResponse> getCounselorInfo(@PathVariable("counselorNo") Long counselorNo) {
        Counselor counselor = counselorService.findById(counselorNo);
        return ResponseEntity.ok(CounselorInfoResponse.from(counselor));
    }

    /**
     * 상담사 전체 조회
     */
    @GetMapping("/api/counselors")
    public ResponseEntity<List<CounselorInfoResponse>> getCounselorInfoList() {
        List<Counselor> findCounselors = counselorService.findCounselors();
        List<CounselorInfoResponse> counselorInfoList = findCounselors.stream()
                .map(m -> CounselorInfoResponse.from(m))
                .collect(Collectors.toList());
        return ResponseEntity.ok(counselorInfoList);
    }

    /**
     * 상담사 평점 순 조회
     */
    @GetMapping("/api/counselors/by_ratings")
    public Page<Counselor> getCounselorInfoByRating(@RequestParam(defaultValue = "BOTH") CounselorType counselorType,
                                                    @RequestParam(defaultValue = "0") int page,
                                                    @RequestParam(defaultValue = "6") int size) {
        return counselorService.findByRating(counselorType, page, size);
    }

    /**
     * 상담사 후기 순 조회
     */
    @GetMapping("/api/counselors/by_review")
    public Page<Counselor> getCounselorInfoByReview(@RequestParam(defaultValue = "BOTH") CounselorType counselorType,
                                                    @RequestParam(defaultValue = "0") int page,
                                                    @RequestParam(defaultValue = "6") int size) {
        return counselorService.findByReview(counselorType, page, size);
    }

    /**
     * 상담사 정보 변경
     */
    @PatchMapping("/api/counselors/update/{counselorNo}")
    public ResponseEntity<UpdateCounselorResponse> updateCounselorInfo(
            @PathVariable("counselorNo") Long id,
            @RequestBody UpdateCounselorRequest request) {
        counselorService.updateCounselorInfo(id, request);
        Counselor counselor = counselorService.findById(id);
        return ResponseEntity.ok(new UpdateCounselorResponse(counselor.getNo()));
    }

}
