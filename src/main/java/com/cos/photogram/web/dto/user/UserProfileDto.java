package com.cos.photogram.web.dto.user;

import com.cos.photogram.domain.user.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserProfileDto {

    private boolean pageOwnerState; //이페이지의 주인인지 아닌지
    private int imageCount;
    private  boolean subscribeState;
    private  int subscribeCount;
    private User user;
}
