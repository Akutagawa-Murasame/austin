package org.mura.austin.support.pipeline;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.mura.austin.common.enums.ResponseStatusEnum;
import org.mura.austin.common.vo.BasicResultVo;

import java.util.List;
import java.util.Map;

/**
 * @author Akutagawa Murasame
 */
@Slf4j
@Data
public class ProcessController {
    /**
     * 模板映射
     *
     * 根据业务编号businessCode映射不同的责任链
     */
    private Map<String, ProcessTemplate> templateConfig = null;

    /**
     * 执行责任链
     */
    public ProcessContext process(ProcessContext context) {
//        前置检查
        if (!preCheck(context)) {
            return context;
        }

//        遍历流程节点
        List<BusinessProcess> processList = templateConfig.get(context.getCode()).getProcessList();
        for (BusinessProcess businessProcess : processList) {
            businessProcess.process(context);
            if (context.getNeedBreak()) {
                break;
            }
        }

        return context;
    }

    private Boolean preCheck(ProcessContext context) {
        // 上下文
        if (context == null) {
            // #TODO 此处无法正确返回CONTEXT_IS_NULL信息，以后可能会改
            return false;
        }

        //业务代码
        String businessCode = context.getCode();
        if (StrUtil.isBlank(businessCode)) {
            context.setResponse(BasicResultVo.fail(ResponseStatusEnum.BUSINESS_CODE_IS_NULL));

            return false;
        }

        // 执行模板
        ProcessTemplate processTemplate = templateConfig.get(businessCode);
        if (processTemplate == null) {
            context.setResponse(BasicResultVo.fail(ResponseStatusEnum.PROCESS_TEMPLATE_IS_NULL));

            return false;
        }

        // 执行模板列表
        List<BusinessProcess> processList = processTemplate.getProcessList();
        if (CollUtil.isEmpty(processList)) {
            context.setResponse(BasicResultVo.fail(ResponseStatusEnum.PROCESS_LIST_IS_NULL));

            return false;
        }

        return true;
    }
}
