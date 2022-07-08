package org.mura.austin.cron.utils;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.map.MapUtil;
import cn.hutool.core.text.csv.CsvData;
import cn.hutool.core.text.csv.CsvRow;
import cn.hutool.core.text.csv.CsvUtil;
import com.google.common.base.Throwables;
import lombok.extern.slf4j.Slf4j;
import org.mura.austin.cron.domain.CrowdInfoVo;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author Akutagawa Murasame
 * @date 2022/7/8 14:23
 *
 * 读取人群文件工具类
 */
@Slf4j
public class ReadFileUtils {
    /**
     * 读取csv文件
     * 1. 获取一行信息(id,param1,param2...)，第一列默认为接收者Id
     * 2. 把文件信息塞进对象内
     * 3. 把对象返回
     */
    public static List<CrowdInfoVo> getCsvRowList(String path) {
        List<CrowdInfoVo> result = new ArrayList<>();
        try {
            CsvData data = CsvUtil.getReader().read(FileUtil.file(path));

            // 第一行为默认为列名
            if (data == null || data.getRow(0) == null || data.getRow(1) == null) {
                log.error("read csv file empty!,path:{}", path);
            }

            // 第一行为默认为列名,所以遍历从第二行开始,第一列默认为接收者Id(不处理)
            CsvRow headerInfo = data.getRow(0);
            for (int i = 1; i < data.getRowCount(); i++) {
                CsvRow row = data.getRow(i);
                Map<String, String> param = MapUtil.newHashMap();

                for (int j = 1; j < headerInfo.size(); j++) {
                    param.put(headerInfo.get(j), row.get(j));
                }

                result.add(CrowdInfoVo.builder().id(row.get(0)).params(param).build());
            }
        } catch (Exception e) {
            log.error("TaskHandler#getCsvRowList fail!{}", Throwables.getStackTraceAsString(e));
        }

        return result;
    }
}
