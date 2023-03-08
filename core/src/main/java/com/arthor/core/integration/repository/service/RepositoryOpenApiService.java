package com.arthor.core.integration.repository.service;

import com.arthor.core.integration.repository.model.BranchInfoDTO;
import com.arthor.core.integration.repository.model.ListBranchRequest;

import java.util.List;

public interface RepositoryOpenApiService {

    List<BranchInfoDTO> branch(ListBranchRequest request);

}
