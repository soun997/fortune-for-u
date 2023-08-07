package com.ssafy.a403.domain.admin.controller;

import com.ssafy.a403.domain.admin.service.AdminService;
import com.ssafy.a403.domain.counselorform.dto.CounselorFormResponse;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RequestMapping("/api/admin")
@RestController
@RequiredArgsConstructor
public class AdminController {

    private final AdminService adminService;

    // 상담사신청정보 불러오기
    @ApiOperation(value = "사용자 정보", notes = "신청된 사용자 정보를 반환합니다")
    @ApiResponses({@ApiResponse(code = 200, message = "성공"),
            @ApiResponse(code = 401, message = "인증실패"),@ApiResponse(code = 404, message = "페이지 없음"),
            @ApiResponse(code = 500, message = "서버 오류")})
    @GetMapping("/counselor-forms")
    public ResponseEntity<?> counselorForms(@RequestParam String filter, Pageable pageable){

        List<CounselorFormResponse> formResponseList = adminService.getCounselorForms(filter, pageable);

        return new ResponseEntity<>(formResponseList, HttpStatus.OK);
    }
}
