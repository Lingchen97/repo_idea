package com.lagou.controller;

import com.lagou.domain.Course;
import com.lagou.domain.CourseSection;
import com.lagou.domain.ResponseResult;
import com.lagou.service.CourseContentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/courseContent")
public class CourseContentController {

    @Autowired
    private CourseContentService contentService;

    @RequestMapping("/findSectionAndLesson")
    public ResponseResult findSectionAndLessonByCourseId(Integer courseId){

        List<CourseSection> list = contentService.findSectionAndLessonByCourseId(courseId);
        return new ResponseResult(true,200,"章节与课时内容查询成功",list);
    }

    @RequestMapping("/findCourseById")
    public ResponseResult findCourseById(Integer courseId){
        Course course = contentService.findCourseById(courseId);
        return new ResponseResult(true,200,"成功",course);
    }

    /*
        新增&更新章节信息
     */
    @RequestMapping("saveOrUpdateSection")
    public ResponseResult saveOrUpdateSection(@RequestBody CourseSection courseSection){

        //判断是否携带了章节id
        if (courseSection.getId() == null){
            //新增
            contentService.saveSection(courseSection);
            return new ResponseResult(true,200,"新增章节成功",null);
        }else {
            //更新
            contentService.updateSection(courseSection);
            return new ResponseResult(true,200,"更新章节成功",null);
        }

    }

    /*
        修改章节状态
     */
    @RequestMapping("/updateSectionStatus")
    public ResponseResult updateSectionStatus(int id,int status){
        contentService.updateSectionStatus(id,status);

        Map<Object,Object> map = new HashMap<>();
        map.put("status",status);

        return new ResponseResult(true,200,"修改章节状态成功",map);
    }

}
