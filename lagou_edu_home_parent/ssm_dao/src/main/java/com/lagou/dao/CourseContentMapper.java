package com.lagou.dao;

import com.lagou.domain.Course;
import com.lagou.domain.CourseSection;

import java.util.List;

public interface CourseContentMapper {

    /*
         查询课程下的章节与课时信息
     */
    public List<CourseSection> findSectionAndLessonByCourseId(int courseId);

    /*
        回显章节对应的课程信息
     */
    public Course findCourseById(Integer courseId);

    /*
        保存章节
     */
    public void saveSection(CourseSection courseSection);

    void updateSection(CourseSection courseSection);

    /*
        修改章节状态
     */
    public void updateSectionStatus(CourseSection courseSection);
}
