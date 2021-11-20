package com.self.highperformance.job.staticjob;

import com.dangdang.ddframe.job.api.ShardingContext;
import com.dangdang.ddframe.job.api.simple.SimpleJob;
import com.dangdang.elasticjob.lite.annotation.ElasticSimpleJob;
import org.springframework.stereotype.Component;

/**
 * 1：执行周期
 * 2：分片
 * 3：指定Zookeeper中的命名空间
 */
@ElasticSimpleJob(
        cron = "0/5 * * * * ? *",
        shardingTotalCount = 1,
        jobName = "${elaticjob.zookeeper.namespace}"
)
@Component
public class StaticJobTask implements SimpleJob {

    // 执行的任务
    @Override
    public void execute(ShardingContext shardingContext) {
        System.out.println("StaticJobTask--execute");
    }
}