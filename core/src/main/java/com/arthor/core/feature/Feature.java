package com.arthor.core.feature;

import com.arthor.core.common.enumeration.RepositoryTypeEnum;

public interface Feature {

    Long getId();

    void setId(Long id);

    String getName();

    void setName(String name);

    Long getApplicationId();

    void setApplicationId(Long applicationId);

    String getApplicationName();

    void setApplicationName(String applicationName);

    RepositoryTypeEnum getRepositoryType();

    void setRepositoryType(RepositoryTypeEnum repositoryType);

    String getRepositoryUrl();

    void setRepositoryUrl(String repositoryUrl);

    String getBranch();

    void setBranch(String branch);

}
