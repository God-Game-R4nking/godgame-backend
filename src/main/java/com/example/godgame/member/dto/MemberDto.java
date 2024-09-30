package com.example.godgame.member.dto;

import com.example.godgame.member.entity.Member;
import com.example.godgame.validator.NotSpace;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class MemberDto {

    @Getter
    @AllArgsConstructor
    public static class Post{
        @NotBlank
        private String id;

        @NotBlank
        private String password;

        @Pattern(regexp = "^[가-힣]{1,10}$", message = "이름은 한글로 1자 이상 10자 이하이어야 합니다.")
        private String memberName;


        @Pattern(regexp = "^[가-힣a-zA-Z0-9]+(\\s[가-힣a-zA-Z0-9]+){0,49}$", message = "판매업체 주소는 한글, 영어, 숫자, 띄어쓰기가 포함될 수 있습니다.")
        private String nickName;

        @NotBlank
        @Pattern(regexp = "^\\d{3}-\\d{3,4}-\\d{4}$", message = "전화번호는 하이픈(-)포함 형식이어야 합니다")
        private String phone;

        @NotBlank
        @Pattern(regexp = "^(\\d{6}\\*{5})$", message = "주민 번호는 총 13자리를 그대로 줍니다")
        private String identificationNumber;
    }

    @Getter
    @Setter
    public static class Patch{
        private long memberId;

        @Pattern(regexp = "^[가-힣a-zA-Z0-9]+(\\s[가-힣a-zA-Z0-9]+){0,49}$", message = "판매업체 주소는 한글, 영어, 숫자, 띄어쓰기가 포함될 수 있습니다.")
        private String nickName;

        public void setMemberId(long memberId){
            this.memberId = memberId;
        }
    }

    @Getter
    @Setter
    public static class PasswordPatch{
        private String password;
        private String newPassword;
    }

    @Getter
    @Setter
    @NoArgsConstructor
    public static class PasswordGet{
        private String memberName;
        private String phone;
        private String identificationNumber;
    }

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class PasswordGetPatch{
        private String newPassword;
    }

    @AllArgsConstructor
    @Getter
    @NoArgsConstructor
    @Setter
    public static class Response {
        private String memberName;
        private String nickName;
        private Long totalPoint;
        private Member.MemberStatus memberStatus;

        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
        private LocalDateTime createdAt;

        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
        private LocalDateTime modifiedAt;
    }
}
