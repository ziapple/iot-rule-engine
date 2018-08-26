package com.casic.iot;

import com.github.ltsopensource.core.commons.utils.WebUtils;
import org.junit.Test;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class JobPostTest {
    @Test
    public void testTerminateJob() throws IOException {
        String taskId = "122EFA71CCC444C3902F0134EE3E221A";
        Map<String, String> params = new HashMap<>();
        params.put("taskId", taskId);
        params.put("taskTrackerNodeGroup", "task-group-rule-engine");
        Map<String, String> headMap = new HashMap<>();
        headMap.put("Authorization", "Basic YWRtaW46YWRtaW4=");
        String res = WebUtils.doPost("http://localhost:8081/api/job-queue/executing-task-terminate", params, "utf-8",3000, 30000, headMap);
        System.out.print(res);
    }
}
