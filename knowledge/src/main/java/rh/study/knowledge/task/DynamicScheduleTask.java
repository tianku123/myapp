package rh.study.knowledge.task;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.Trigger;
import org.springframework.scheduling.TriggerContext;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.SchedulingConfigurer;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;
import org.springframework.scheduling.support.CronTrigger;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import rh.study.knowledge.dao.cron.CronMapper;
import rh.study.knowledge.entity.cron.Cron;
import rh.study.knowledge.service.redis.RedisService;
import rh.study.knowledge.util.DateUtil;

import java.util.Date;

@Component
@Configuration      //1.主要用于标记配置类，兼备Component的效果。
@EnableScheduling   // 2.开启定时任务
public class DynamicScheduleTask implements SchedulingConfigurer {

    @Autowired      //注入mapper
    @SuppressWarnings("all")
    CronMapper cronMapper;

    @Autowired
    private RedisService redisService;

    /**
     * 执行定时任务.
     */
    @Override
    public void configureTasks(ScheduledTaskRegistrar taskRegistrar) {

        taskRegistrar.addTriggerTask(
                //1.添加任务内容(Runnable)
                new Runnable() {
                    @Override
                    public void run() {
                        System.out.println("执行动态定时任务开始: " + DateUtil.now());
                        redisService.saveRedisMonitor();
                        System.out.println("执行动态定时任务结束: " + DateUtil.now());
                    }
                },
                //2.设置执行周期(Trigger)
                new Trigger() {
                    @Override
                    public Date nextExecutionTime(TriggerContext triggerContext) {
                        //2.1 从数据库获取执行周期
                        Cron cron = cronMapper.selectByPrimaryKey(1);
                        //2.2 合法性校验.
                        if (StringUtils.isEmpty(cron.getCron())) {
                            // Omitted Code ..
                        }
                        //2.3 返回执行周期(Date)
                        return new CronTrigger(cron.getCron()).nextExecutionTime(triggerContext);
                    }
                }
        );
    }

}
