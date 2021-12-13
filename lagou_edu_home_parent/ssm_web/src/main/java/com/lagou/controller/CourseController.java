package com.lagou.controller;

import com.lagou.domain.Course;
import com.lagou.domain.CourseVO;
import com.lagou.domain.ResponseResult;
import com.lagou.service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/course")
public class CourseController {

    @Autowired
    private CourseService courseService;

    /**
     * 根据条件查询课程
     */
    @RequestMapping("/findCourseByCondition")
    public ResponseResult findCourseByCondition(@RequestBody CourseVO courseVO){

        List<Course> list = courseService.findCourseByCondition(courseVO);

        return new ResponseResult(true,200,"响应成功",list);
    }

    /*
     * 课程图片上传
     */
    @RequestMapping("/courseUpload")
    public ResponseResult fileUpload(@RequestParam("file") MultipartFile file, HttpServletRequest request) throws IOException {

        //1.判断接收到的上传文件是否为空
        if (file.isEmpty()){
            throw new RuntimeException();
        }
        //2.获取项目部署路径
        // realPath  = D:\apache-tomcat-8.5.56\webapps\ssm_web\
        String realPath = request.getServletContext().getRealPath("/");
        // D:\apache-tomcat-8.5.56\webapps\
        String webappPath = realPath.substring(0, realPath.indexOf("ssm_web"));

        //3.获取原文件名
        String originalFilename = file.getOriginalFilename();

        //4.生成新的文件名
        String newFileName = System.currentTimeMillis() + originalFilename.substring(originalFilename.lastIndexOf("."));

        //5.文件上传
        String uploadPath = webappPath + "upload\\";
        File filePath = new File(uploadPath, newFileName);

        //如果目录不存在就创建目录
        if (filePath.getParentFile().exists()){
            boolean mkdirs = filePath.getParentFile().mkdirs();
            System.out.println("创建目录：" + filePath);
        }

        //图片进行真正的上传
        file.transferTo(filePath);

        //6.将文件名和路径返回
        Map<String,String> map = new HashMap<>();
        map.put("fileName",newFileName);
        map.put("filePath","http://localhost:8080/upload/" + newFileName);

        return new ResponseResult(true,200,"上传成功",map);
    }

    /*
    *   新增课程和讲师信息
    *   新增课程和修改课程放在一个方法中
    * */
    @RequestMapping("/saveOrUpdateCourse")
    public ResponseResult saveOrUpdateCourse(@RequestBody CourseVO courseVO) throws InvocationTargetException, IllegalAccessException {

        courseService.saveCourseOrTeacher(courseVO);

        return new ResponseResult(true,200,"相应成功",null);
    }

    /*
    *   根据id查询
    * */
    @RequestMapping("/findCourseById")
    public ResponseResult findCourseById(Integer id){
        CourseVO courseById = courseService.findCourseById(id);
        return new ResponseResult(true,200,"相应成功",courseById);
    }

    /*
        课程状态管理
     */
    @RequestMapping("/updateCourseStatus")
    public ResponseResult updateCourseStatus(Integer id, Integer status){

        courseService.updateCourseStatus(id,status);

        //响应数据
        Map<String,Object> map = new HashMap<>();
        map.put("status",status);

        return new ResponseResult(true,200,"课程状态修改成功",map);
    }

}
