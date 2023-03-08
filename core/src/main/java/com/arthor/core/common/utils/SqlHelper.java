package com.arthor.core.common.utils;

import java.util.Objects;

public abstract class SqlHelper {

    public static boolean retBool(Integer row) {
        return Objects.nonNull(row) && row >= 1;
    }

}
