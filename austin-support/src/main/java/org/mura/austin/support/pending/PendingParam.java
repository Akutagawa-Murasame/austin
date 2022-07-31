package org.mura.austin.support.pending;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.concurrent.BlockingQueue;

/**
 * @author Akutagawa Murasame
 * @date 2022/7/27 21:40
 *
 * pending初始化参数类
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Accessors(chain = true)
public class PendingParam<T> {
    /**
     * 阻塞队列实现类【必填】
     */
    private BlockingQueue<T> queue;

    /**
     * batch 触发执行的数量阈值【必填】
     */
    private Integer numThreshold;

    /**
     * batch 触发执行的时间阈值，单位毫秒【必填】
     */
    private Long timeThreshold;

    /**
     * pending具体实现对象
     */
    private Pending pending;

    /**
     * 消费线程数【可选】
     */
    protected Integer threadNum;
}
