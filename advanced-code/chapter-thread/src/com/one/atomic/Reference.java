package com.one.atomic;

import java.util.Objects;

/**
 * @ClassName: Reference
 * @Description: 业务场景模拟: 序列需要自增并且时间需要更新为最新时间戳
 * @Author: one
 * @Date: 2022/02/13
 */
public class Reference {
    /**
     * 序列
     */
    private long sequence;

    /**
     * 时间戳
     */
    private long timestamp;

    public Reference(long sequence, long timestamp) {
        this.sequence = sequence;
        this.timestamp = timestamp;
    }

    public long getSequence() {
        return sequence;
    }

    public void setSequence(long sequence) {
        this.sequence = sequence;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    @Override
    public String toString() {
        return "Reference{" +
                "sequence=" + sequence +
                ", timestamp=" + timestamp +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Reference reference = (Reference) o;
        return sequence == reference.sequence &&
                timestamp == reference.timestamp;
    }

    @Override
    public int hashCode() {
        return Objects.hash(sequence, timestamp);
    }
}
