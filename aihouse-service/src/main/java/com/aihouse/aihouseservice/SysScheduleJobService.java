package com.aihouse.aihouseservice;

import com.aihouse.aihousedao.bean.SysScheduleJob;
import com.aihouse.aihousedao.dao.SysScheduleJobDao;

public interface SysScheduleJobService extends BaseService<SysScheduleJob,SysScheduleJobDao> {

        /**
         * 新增定时任务
         *
         * @author lanjerry
         * @date 2019/1/28 15:37
         * @param job 任务
         */
        void add(SysScheduleJob job);

        void addJob(SysScheduleJob job);
        /**
         * 启动定时任务
         *
         * @author lanjerry
         * @date 2019/1/28 16:49
         * @param id 任务id
         */
        void start(int id);

        /**
         * 暂停定时任务
         *
         * @author lanjerry
         * @date 2019/1/28 16:49
         * @param id 任务id
         */
        void pause(int id);

        /**
         * 删除定时任务
         *
         * @author lanjerry
         * @date 2019/1/28 16:49
         * @param id 任务id
         */
        void delete(int id);

        /**
         * 启动所有定时任务
         *
         * @author lanjerry
         * @date 2019/1/28 16:49
         */

        void deleteOnlyJob(int id);
}
