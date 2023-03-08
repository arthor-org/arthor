package com.arthor.core.integration.pipeline.jenkins;

import java.io.*;
import java.util.Objects;

/**
 * 配置构建器
 */
public class ConfigBuilder {

    private volatile String configTemplateXML;

    private final static String FILE_PATH = "jenkins";
    private final static String FILE_NAME = "config-template.xml";
    private final static String COMPILE_COMMAND_REPLACE_KEY = "${COMPILE_COMMAND_REPLACE}";
    private final static String JOB_NAME_REPLACE_KEY = "${JOB_NAME_REPLACE}";
    private final static String IMAGE_WORK_DIRECTORY_REPLACE_KEY = "${IMAGE_WORK_DIRECTORY_REPLACE}";
    private final static String IMAGE_REPOSITORY_HOST_REPLACE_KEY = "${IMAGE_REPOSITORY_HOST_REPLACE}";
    private final static String IMAGE_REPOSITORY_ORG_REPLACE_KEY = "${IMAGE_REPOSITORY_ORG_REPLACE}";
    private final static String IMAGE_REPOSITORY_USERNAME_REPLACE_KEY = "${IMAGE_REPOSITORY_USERNAME_REPLACE}";
    private final static String IMAGE_REPOSITORY_PASSWORD_REPLACE_KEY = "${IMAGE_REPOSITORY_PASSWORD_REPLACE}";
    private final static String DEFAULT_COMPILE_COMMAND = "mvn clean package";

    public ConfigBuilder() {
        this.configTemplateXML = readTemplate();
    }

    public String createConfig(String jobName, String imageRepositoryHost, String imageRepositoryOrg,
                               String imageRepositoryUsername, String imageRepositoryPassword) {
        return configTemplateXML
                .replace(JOB_NAME_REPLACE_KEY, jobName)
                .replace(COMPILE_COMMAND_REPLACE_KEY, DEFAULT_COMPILE_COMMAND)
                .replace(IMAGE_WORK_DIRECTORY_REPLACE_KEY, "target")
                .replace(IMAGE_REPOSITORY_HOST_REPLACE_KEY, imageRepositoryHost)
                .replace(IMAGE_REPOSITORY_ORG_REPLACE_KEY, imageRepositoryOrg)
                .replace(IMAGE_REPOSITORY_USERNAME_REPLACE_KEY, imageRepositoryUsername)
                .replace(IMAGE_REPOSITORY_PASSWORD_REPLACE_KEY, imageRepositoryPassword);
    }

    private String readTemplate() {
        StringBuilder sb = new StringBuilder();
        BufferedReader br = null;
        try {
            br = new BufferedReader(new InputStreamReader(Objects.requireNonNull(
                    this.getClass().getClassLoader().getResourceAsStream(FILE_PATH + "/" + FILE_NAME))));
            String line;
            while ((line = br.readLine()) != null) {
                sb.append(line);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException ignore) {
                }
            }
        }
        return sb.toString();
    }


}
