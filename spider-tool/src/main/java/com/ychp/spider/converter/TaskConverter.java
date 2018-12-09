package com.ychp.spider.converter;

import com.ychp.spider.bean.response.TaskDetailInfo;
import com.ychp.spider.bean.response.TaskInfo;
import com.ychp.spider.enums.TaskStatus;
import com.ychp.spider.model.Parser;
import com.ychp.spider.model.Task;
import org.springframework.cglib.beans.BeanCopier;

/**
 * @author yingchengpeng
 * @date 2018/12/9
 */
public class TaskConverter {

    private static final BeanCopier TASK_COPIER = BeanCopier.create(Task.class, TaskInfo.class, false);
    private static final BeanCopier TASK_DETAIL_COPIER = BeanCopier.create(Task.class, TaskDetailInfo.class, false);

    public static TaskInfo convert(Task task, Parser parser) {
        TaskInfo taskInfo = new TaskInfo();
        TASK_COPIER.copy(task, taskInfo, null);
        taskInfo.setName(parser.getName());
        taskInfo.setStatusStr(TaskStatus.fromValue(task.getStatus()).toString());
        return taskInfo;
    }

    public static TaskDetailInfo convertDetail(Task task, Parser parser) {
        TaskDetailInfo taskDetailInfo = new TaskDetailInfo();
        TASK_DETAIL_COPIER.copy(task, taskDetailInfo, null);
        taskDetailInfo.setName(parser.getName());
        taskDetailInfo.setStatusStr(TaskStatus.fromValue(task.getStatus()).toString());
        return taskDetailInfo;
    }

}
