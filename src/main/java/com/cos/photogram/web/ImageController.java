package com.cos.photogram.web;

import com.cos.photogram.config.auth.PrincipalDetails;
import com.cos.photogram.service.ImageService;
import com.cos.photogram.web.dto.image.ImageUploadDto;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@RequiredArgsConstructor
@Controller
public class ImageController {

    private final ImageService imageService;

    @GetMapping({"/","/image/story"})
    public String story(){
        return "image/story";
    }

    @GetMapping({"/image/popular"})
    public String popular(){
        return "image/popular";
    }

    @GetMapping({"/image/upload"})
    public String upload(){
        return "image/upload";
    }

    @PostMapping("/image")
    public String image(ImageUploadDto imageUploadDto, @AuthenticationPrincipal PrincipalDetails principalDetails) {
        imageService.사진업로드(imageUploadDto, principalDetails);

        return "redirect:/user/"+principalDetails.getUser().getId();
    }


}
