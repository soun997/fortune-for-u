package com.ssafy.a403.domain.reservation.controller;

import com.ssafy.a403.domain.reservation.dto.*;
import com.ssafy.a403.domain.reservation.service.CounselingReservationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("api/reservations")
public class CounselingReservationController {

    private final CounselingReservationService counselingReservationService;

    public CounselingReservationController(CounselingReservationService counselingReservationService) {
        this.counselingReservationService = counselingReservationService;
    }

    //예약 가능 시간 조회
    @GetMapping("/availabledate/{counselorNo}/{date}")
    public AvailableDateTime availableDate(@PathVariable Long counselorNo, @PathVariable String date){

        return counselingReservationService.availableDateTime(counselorNo, date);
    }



    //예약 db에 저장
    @PostMapping("/reserve")
    public String reserve(@RequestBody ReservationRequest reservationRequest,
                          RedirectAttributes redirectAttributes){

        Long memberId = reservationRequest.getMemberId();
        Long counselorId = reservationRequest.getCounselorId();
        LocalDateTime reservationDate = reservationRequest.getReservationDate();

        Long reservationNo = counselingReservationService.reservation(memberId, counselorId, reservationDate);
        redirectAttributes.addFlashAttribute("reservationNo", reservationNo);

        return "redirect:/api/mypage";
    }


    // 일반회원 id로 예약 조회
    @GetMapping("/member_rez_info/{memberId}")
    public List<ReservationResponse> getRezInfo(@PathVariable Long memberId){

        return  counselingReservationService.getReservation(memberId);

    }


    // 상담가 id로 예약 조회
    @GetMapping("/counselor_rez_info/{counselorId}")
    public List<ReservationResponse> getCounselorRezInfo(@PathVariable Long counselorId){

        return counselingReservationService.getCoReservation(counselorId);

    }


    // 예약 번호로 예약 조회
    @GetMapping("/reservations/{reservationNo}")
    public ReservationResponse getReservations(@PathVariable Long reservationNo) {

        return counselingReservationService.checkReservation(reservationNo);

    }


    //예약 취소
    @PatchMapping("/cancel/{reservationNo}")
    public String cancel(@PathVariable Long reservationNo) {
        counselingReservationService.cancelReservation(reservationNo);
        return "redirect:/";
    }


    //후기 작성
    @PostMapping("/{reservationNo}/save_review")
    public String saveReview(@RequestBody ReviewRequest reviewRequest,
                             @PathVariable Long reservationNo){
        Long memberId = reviewRequest.getMemberId();
        String review = reviewRequest.getReservationReview();
        counselingReservationService.postReview(reservationNo, memberId, review);

        return "ok";
    }


    // 후기 삭제
    @DeleteMapping("/{reservationNo}")
    public ResponseEntity<String> deleteReview(@PathVariable Long reservationNo) {
        try {
            counselingReservationService.deleteReview(reservationNo);
            return ResponseEntity.ok("리뷰 삭제 성공했습니다.");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }


    // 일반회원 후기 조회
    @GetMapping("/{memberId}/reviews")
    public List<ReviewResponse> getReview(@PathVariable Long memberId) {

        return counselingReservationService.getReview(memberId);

    }


//     상담가 후기 조회
    @GetMapping("/{counselorId}/co_reviews")
    public List<ReviewResponse> getCoReview(@PathVariable Long counselorId) {

        return counselingReservationService.getCoReview(counselorId);

    }


}
