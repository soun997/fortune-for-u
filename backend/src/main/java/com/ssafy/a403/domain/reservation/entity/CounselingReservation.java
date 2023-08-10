package com.ssafy.a403.domain.reservation.entity;

import com.ssafy.a403.domain.member.entity.Counselor;
import com.ssafy.a403.domain.member.entity.Member;
import com.ssafy.a403.domain.model.CounselorType;
import com.ssafy.a403.domain.model.ReportStatus;
import com.ssafy.a403.domain.model.ReservationStatus;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Slf4j
@Getter
@Entity
@NoArgsConstructor
public class CounselingReservation {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long reservationNo;

    @ManyToOne
    @JoinColumn(name = "member_no")
    private Member member;

    @ManyToOne
    @JoinColumn(name = "counselor_no")
    private Counselor counselor;

    @Column(name="rez_time")
    private LocalDateTime reservationDateTime;


    @Column(name="rez_status")
    @Enumerated(EnumType.STRING)
    private ReservationStatus reservationStatus;

    @Column(name="rez_url")
    private String sessionId;

    @Column(name="rez_review")
    private String reservationReview;

    @Column(name="rez_report")
    private String reservationReport;

    @Column(name="rez_report_status")
    @Enumerated(EnumType.STRING)
    private ReportStatus reportStatus;

    @Column(name="rez_recorded")
    private String reservationRecorded;

    @Column(name="rez_score")
    private float reservationScore;

    @Column(name="rez_type")
    private String reservationType;


    @Builder
    public CounselingReservation(Long reservationNo, Member member, Counselor counselor, LocalDateTime reservationDateTime, ReservationStatus reservationStatus,
                                 String sessionId, String reservationReview, String reservationReport, ReportStatus reportStatus,
                                 String reservationRecorded, float reservationScore, String reservationType) {
        this.reservationNo = reservationNo;
        this.member = member;
        this.counselor = counselor;
        this.reservationDateTime = reservationDateTime;
        this.reservationStatus = reservationStatus;
        this.sessionId = sessionId;
        this.reservationReview = reservationReview;
        this.reservationReport = reservationReport;
        this.reportStatus = reportStatus;
        this.reservationRecorded = reservationRecorded;
        this.reservationScore = reservationScore;
        this.reservationType = reservationType;
    }


    //예약 취소
    public void cancel() {
        if (reservationDateTime.isAfter(LocalDateTime.now())) {
            reservationStatus = ReservationStatus.CANCEL;
        } else {
            throw new IllegalArgumentException("취소 가능한 날짜가 지났습니다.");
        }
    }


    // 리뷰 저장
    public void saveReview(String review, float score) {
        if (review.length() > 200) {
            throw new IllegalArgumentException("200자를 초과하였습니다.");
        }
        reservationReview = review;
        reservationScore = score;

        counselor.updateCounselorReview(score);
    }


    //리뷰 삭제
    public void deleteReview() {
        reservationReview = null;
    }


    // 리뷰가 비어있는지 확인
    public boolean checkEmpty() {
        return reservationReview == null || reservationReview.isEmpty();
    }

    // 권한 확인
    public boolean checkMemberId(Long memberId) {
        return getMember().getNo().equals(memberId);
    }

    // 상담 종료 확인
    public boolean checkStatus(){
        return reservationStatus.equals(ReservationStatus.END);
    }



}
