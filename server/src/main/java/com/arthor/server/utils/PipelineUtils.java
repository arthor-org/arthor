package com.arthor.server.utils;

import com.arthor.core.pipeline.model.ImageIdParseResult;
import com.google.common.base.Splitter;
import com.google.common.collect.Lists;
import org.apache.commons.lang3.StringUtils;
import com.arthor.core.common.utils.Assert;

import java.util.List;

public abstract class PipelineUtils {

    private final static String JOB_NAME_FORMAT = "%s_%s_%s_%s";

    /**
     * 拼接流水线名称
     *
     * @param applicationName
     * @param applicationId
     * @param featureId
     * @return
     */
    public static String buildJobName(String applicationName, String featureName, Long applicationId, Long featureId) {
        return String.format(JOB_NAME_FORMAT, applicationName, featureName, applicationId, featureId);
    }

    public static ImageIdParseResult parseImageId(String imageId) {
        Assert.isTrue(StringUtils.isNotBlank(imageId), "无效镜像ID");
        List<String> commitIdList = Lists.newArrayList(Splitter.on(":").split(imageId));
        Assert.isTrue(commitIdList.size() == 2, "无法从imageId中解析commitId");
        String imageString = commitIdList.get(0);
        String commitId = commitIdList.get(1);
        List<String> imageList = Lists.newArrayList(Splitter.on("/")
                .split(imageString));
        Assert.isTrue(imageList.size() == 3, "无法从imageId中解析镜像信息");
        String jobName = imageList.get(2);
        return new ImageIdParseResult(jobName, commitId);
    }

}
