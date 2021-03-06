package org.mura.austin.service.api.impl.service;

import cn.monitor4all.logRecord.annotation.OperationLog;
import org.mura.austin.service.api.domain.BatchSendRequest;
import org.mura.austin.service.api.domain.SendRequest;
import org.mura.austin.service.api.domain.SendResponse;
import org.mura.austin.service.api.impl.domain.SendTaskModel;
import org.mura.austin.support.pipeline.ProcessContext;
import org.mura.austin.support.pipeline.ProcessController;
import org.mura.austin.common.vo.BasicResultVo;
import org.mura.austin.service.api.service.SendService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;

/**
 * @author Akutagawa Murasame
 *
 * 发送接口实现
 */
@Service
public class SendServiceImpl implements SendService {
    private ProcessController processController;

    @Autowired
    public void setProcessController(ProcessController processController) {
        this.processController = processController;
    }

    @Override
    @OperationLog(bizType = "SendService#send", bizId = "#sendRequest.messageTemplateId", msg = "#sendRequest")
    public SendResponse send(SendRequest sendRequest) {
        SendTaskModel sendTaskModel = SendTaskModel.builder()
                .messageTemplateId(sendRequest.getMessageTemplateId())
                .messageParamList(Collections.singletonList(sendRequest.getMessageParam()))
                .build();

        ProcessContext context = ProcessContext.builder()
                .code(sendRequest.getCode())
                .processModel(sendTaskModel)
                .needBreak(false)
                .response(BasicResultVo.success())
                .build();

        ProcessContext process = processController.process(context);

        return new SendResponse(process.getResponse().getStatus(), process.getResponse().getMsg());
    }

    @Override
    @OperationLog(bizType = "SendService#batchSend", bizId = "#batchSendRequest.messageTemplateId", msg = "#batchSendRequest")
    public SendResponse batchSend(BatchSendRequest batchSendRequest) {
        SendTaskModel sendTaskModel = SendTaskModel.builder()
                .messageTemplateId(batchSendRequest.getMessageTemplateId())
                .messageParamList(batchSendRequest.getMessageParamList())
                .build();

        ProcessContext context = ProcessContext.builder()
                .code(batchSendRequest.getCode())
                .processModel(sendTaskModel)
                .needBreak(false)
                .response(BasicResultVo.success()).build();

        ProcessContext process = processController.process(context);

        return new SendResponse(process.getResponse().getStatus(), process.getResponse().getMsg());
    }
}