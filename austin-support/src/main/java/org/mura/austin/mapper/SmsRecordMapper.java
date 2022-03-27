package org.mura.austin.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.mura.austin.domain.SmsRecord;

/**
 * @author Akutagawa Murasame
 * @date 2022/3/27 16:13
 */
@Mapper
public interface SmsRecordMapper extends BaseMapper<SmsRecord> {
}
