package com.arthor.core.integration.repository.gitlab;

import com.arthor.core.common.utils.OkHttpClientUtils;
import com.arthor.core.integration.repository.RepositoryProperties;
import com.arthor.core.integration.repository.model.BranchInfoDTO;
import com.arthor.core.integration.repository.model.ListBranchRequest;
import com.arthor.core.integration.repository.service.RepositoryOpenApiService;
import okhttp3.OkHttpClient;

import java.util.List;

public class GitlabOpenApiService implements RepositoryOpenApiService {

    private final RepositoryProperties repositoryProperties;
    private final OkHttpClient okHttpClient;

    public GitlabOpenApiService(RepositoryProperties repositoryProperties) {
        this.repositoryProperties = repositoryProperties;
        this.okHttpClient = OkHttpClientUtils.instance();
    }

    private final static String BRANCHES_URL = "%s/api/v4/projects/%s/repository/branches";
    private final static String ACCESS_TOKEN_HEADER_KEY = "PRIVATE-TOKEN";

    @Override
    public List<BranchInfoDTO> branch(ListBranchRequest request) {
//        String url = String.format(BRANCHES_URL, gitlabProperties.getGitlabHost(), projectId);
//        HttpHeaders httpHeaders = new HttpHeaders();
//        httpHeaders.add(ACCESS_TOKEN_HEADER_KEY, gitlabProperties.getAccessTokens());
//        ResponseEntity<String> responseEntity = gitlabRestTemplate.exchange(url, HttpMethod.GET, new HttpEntity<>(httpHeaders), String.class);
//        if (HttpStatus.OK == responseEntity.getStatusCode()) {
//            List<BranchInfo> branchInfos = JSON.parseArray(responseEntity.getBody(), BranchInfo.class);
//            if (CollectionUtils.isNotEmpty(branchInfos)) {
//                return branchInfos.stream().map(BranchInfo::getName).collect(Collectors.toList());
//            }
//            return Collections.emptyList();
//        }
//        throw new RepositoryOpenApiException(projectId, responseEntity.getStatusCodeValue(), responseEntity.getBody());
        return null;
    }
}
