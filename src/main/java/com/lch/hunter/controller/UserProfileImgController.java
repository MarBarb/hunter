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
    // 获取图片格式：localhost/defaultProfilePic/userimgpath
    // 获取图片格式举例：localhost/defaultProfilePic/图片1.png
    @GetMapping("/img/profilePic")
    public UserProfileImg getProfilePic(@RequestParam int userid){
        // 头像暂时随机、固定
        return userProfileImgMapper.selectById((userid % 20) + 1); // 返回值是头像图片的文件名
    }

    // 上传头像
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
