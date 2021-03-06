package org.mura.austin.support.pipeline;

/**
 * @author Akutagawa Murasame
 *
 * 业务执行接口
 */
public interface BusinessProcess {
    /**
     * 执行业务
     */
    void process(ProcessContext context);
}