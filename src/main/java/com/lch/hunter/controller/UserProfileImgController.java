package com.lch.hunter.controller;

import com.lch.hunter.entity.Img;
import com.lch.hunter.entity.UserProfileImg;
import com.lch.hunter.mapper.UserProfileImgMapper;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;

@RestController
public class UserProfileImgController {
    UserProfileImgMapper userProfileImgMapper;
    public UserProfileImgController(UserProfileImgMapper userProfileImgMapper) {
        this.userProfileImgMapper = userProfileImgMapper;
    }


    // 获取用户头像
    @GetMapping("/img/profilePic")
    public UserProfileImg getProfilePic(@RequestParam int userid){
        return userProfileImgMapper.selectById(userid % 9); // 头像暂时随机、固定
    }

    // 上传图片
    // 格式举例：localhost/defaultProfilePic/图片1.png
    @PostMapping("/img/profilePic")
    public boolean save(MultipartFile photo, HttpServletRequest request) throws IOException {
        UserProfileImg userProfileImg = new UserProfileImg();
        userProfileImg.setUserimgid(0);
        String path = request.getServletContext().getRealPath("/img_file/defaultProfilePic/");
        userProfileImg.setUserimgpath(photo.getOriginalFilename());
        userProfileImg.setUserimgrealpath(path);
        saveFile(photo, path);
        int indicator = userProfileImgMapper.insert(userProfileImg);
        return indicator > 0;
    }

    private void saveFile(MultipartFile photo, String path) throws IOException {
        File dir = new File(path);
        if (!dir.exists()) {
            dir.mkdirs();
        }
        File file = new File(path + photo.getOriginalFilename());
        photo.transferTo(file);
    }
}
