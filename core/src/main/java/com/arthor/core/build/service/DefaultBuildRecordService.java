package com.arthor.core.build.service;

import com.arthor.core.build.BuildRecord;
import com.arthor.core.build.DefaultBuildRecord;
import com.arthor.core.build.model.CreateBuildRequest;
import com.arthor.core.build.model.ListBuildRecordRequest;
import com.arthor.core.build.store.BuildRecordDO;
import com.arthor.core.build.store.BuildRecordStore;
import com.arthor.core.common.utils.Assert;

import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 * 构建记录表 服务实现类
 * </p>
 *
 * @author 
 * @since 2022-11-24
 */
public class DefaultBuildRecordService implements BuildRecordService {

    private final BuildRecordStore buildRecordStore;

    public DefaultBuildRecordService(BuildRecordStore buildRecordStore) {
        this.buildRecordStore = buildRecordStore;
    }

    /**
     * 构建
     *
     * @param request
     * @return
     */
    @Override
    public Long createBuildRecord(CreateBuildRequest request) {
        BuildRecordDO entity = new BuildRecordDO();
        entity.setApplicationId(request.getApplicationId());
        entity.setApplicationName(request.getApplicationName());
        entity.setFeatureId(request.getFeatureId());
        entity.setFeatureName(request.getFeatureName());
        entity.setBuildNumber(request.getBuildNumber());
        entity.setJobName(request.getJobName());
        entity.setEnvId(request.getEnvId());
        entity.setStatus(request.getStatus());
        entity.setNumberOfCheck(request.getNumberOfCheck());
        entity.setCreateTime(request.getCreateTime());
        Assert.isTrue(buildRecordStore.create(entity), "保存构建记录失败");
        return entity.getId();
    }

    @Override
    public Boolean increaseNumberOfCheck(Long id) {
        return buildRecordStore.increaseNumberOfCheck(id);
    }

    @Override
    public Boolean updateBuildRecordBatch(List<BuildRecord> entityList) {
        entityList.forEach(e -> {
            BuildRecordDO entity = new BuildRecordDO();
            entity.setId(e.getId());
            entity.setStatus(e.getStatus());
            entity.setCommitId(e.getCommitId());
            entity.setImageId(e.getImageId());
            entity.setFinishTime(e.getFinishTime());
            Assert.isTrue(buildRecordStore.update(entity), "更新构建记录失败");
        });
        return Boolean.TRUE;
    }

    @Override
    public List<BuildRecord> listByCondition(ListBuildRecordRequest request) {
        return buildRecordStore.listByCondition(request)
                .stream().map(this::convert).collect(Collectors.toList());
    }

    @Override
    public BuildRecord findById(Long id) {
        BuildRecordDO entity = buildRecordStore.findById(id);
        Assert.notNull(entity, "无法获取部署记录信息");
        return convert(entity);
    }

    private BuildRecord convert(BuildRecordDO entity) {
        DefaultBuildRecord buildRecord = new DefaultBuildRecord();
        buildRecord.setId(entity.getId());
        buildRecord.setApplicationId(entity.getApplicationId());
        buildRecord.setApplicationName(entity.getApplicationName());
        buildRecord.setFeatureId(entity.getFeatureId());
        buildRecord.setFeatureName(entity.getFeatureName());
        buildRecord.setCommitId(entity.getCommitId());
        buildRecord.setImageId(entity.getImageId());
        buildRecord.setBuildNumber(entity.getBuildNumber());
        buildRecord.setEnvId(entity.getEnvId());
        buildRecord.setJobName(entity.getJobName());
        buildRecord.setStatus(entity.getStatus());
        buildRecord.setNumberOfCheck(entity.getNumberOfCheck());
        buildRecord.setCreateTime(entity.getCreateTime());
        buildRecord.setFinishTime(entity.getFinishTime());
        return buildRecord;
    }

}
