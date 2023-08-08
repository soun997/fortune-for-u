package com.ssafy.a403.domain.member.dto;

import com.ssafy.a403.domain.member.entity.Counselor;
import lombok.Data;

@Data
public class CounselorInfoResponse {
    private String name;
    private String profileImg;
    private Long counselorNo;
    private Enum CounselorType;
    private String major;
    private String intro;
    private String career;
    private String address;
    private String phone;
    private int reviewCnt;
    private float ratingAvg;

    public static CounselorInfoResponse from(Counselor counselor) {
        CounselorInfoResponse counselorInfoResponse = new CounselorInfoResponse();

        counselorInfoResponse.name = counselor.getMember().getName();
        counselorInfoResponse.profileImg = counselor.getMember().getProfileImage();
        counselorInfoResponse.counselorNo = counselor.getNo();
        counselorInfoResponse.CounselorType = counselor.getCounselorType();
        counselorInfoResponse.major = counselor.getMajor();
        counselorInfoResponse.intro = counselor.getIntro();
        counselorInfoResponse.career = counselor.getCareer();
        counselorInfoResponse.address = counselor.getAddress();
        counselorInfoResponse.phone = counselor.getPhone();
        counselorInfoResponse.reviewCnt = counselor.getReviewCnt();
        counselorInfoResponse.ratingAvg = counselor.getRatingAvg();


        return counselorInfoResponse;
    }
}
