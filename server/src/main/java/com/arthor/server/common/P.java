package com.arthor.server.common;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Collections;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class P<T> {

    /**
     * 查询数据列表
     */
    @Builder.Default
    private List<T> records = Collections.emptyList();
    /**
     * 总数
     */
    @Builder.Default
    private long total = 0;
    /**
     * 每页显示条数，默认 20
     */
    @Builder.Default
    private long size = 20;
    /**
     * 当前页
     */
    @Builder.Default
    private long current = 1;

    /**
     * 是否存在上一页
     *
     * @return true / false
     */
    public boolean hasPrevious() {
        return this.current > 1;
    }

    /**
     * 是否存在下一页
     *
     * @return true / false
     */
    public boolean hasNext() {
        return this.current < this.getPages();
    }

    @JsonIgnore
    public long getPages() {
        if (getSize() == 0) {
            return 0L;
        }
        long pages = getTotal() / getSize();
        if (getTotal() % getSize() != 0) {
            pages++;
        }
        return pages;
    }

}
