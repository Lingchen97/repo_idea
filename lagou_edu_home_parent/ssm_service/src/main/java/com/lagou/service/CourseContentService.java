package com.lagou.service;

import com.lagou.domain.Course;
import com.lagou.domain.CourseSection;

import java.util.List;

public interface CourseContentService {

    /*
    根据课程id查询对应章节和课时
     */
    public List<CourseSection> findSectionAndLessonByCourseId(Integer courseId);

    public Course findCourseById(Integer courseId);

    public void saveSection(CourseSection courseSection);

    void updateSection(CourseSection courseSection);

    /*
        修改章节状态
     */
    public void updateSectionStatus(int id,int status);
}
