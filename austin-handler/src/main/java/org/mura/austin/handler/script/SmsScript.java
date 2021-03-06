package org.mura.austin.handler.script;

import org.mura.austin.support.domain.SmsRecord;
import org.mura.austin.handler.domain.SmsParam;

import java.util.List;

/**
 * @author Akutagawa Murasame
 * @date 2022/3/27 12:19
 *
 * 消息脚本
 */
public interface SmsScript {
    /**
     * 发送短信
     *
     * @param smsParam 发送短信参数
     * @return 渠道商接口返回值
     */
    List<SmsRecord> send(SmsParam smsParam) throws Exception;
}
