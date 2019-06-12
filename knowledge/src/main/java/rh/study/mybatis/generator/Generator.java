package rh.study.mybatis.generator;

import org.mybatis.generator.api.MyBatisGenerator;
import org.mybatis.generator.config.Configuration;
import org.mybatis.generator.config.xml.ConfigurationParser;
import org.mybatis.generator.internal.DefaultShellCallback;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 *  读取 MBG配置生成代码
 */
public class Generator {

    public static void main(String[] args) throws Exception{
        // MBG 执行过程中的警告信息
        List<String> warning = new ArrayList<>();
        // 当生成的代码重复时，覆盖原代码
        boolean overwrite = true;
        // 读取 MBG 配置
        InputStream in = Generator.class.getResourceAsStream("/generator/generatorConfig.xml");
        ConfigurationParser cp = new ConfigurationParser(warning);
        Configuration configuration = cp.parseConfiguration(in);
        in.close();

        DefaultShellCallback callback = new DefaultShellCallback(overwrite);
        // 创建 MBG
        MyBatisGenerator generator = new MyBatisGenerator(configuration, callback, warning);
        // 执行生成代码
        generator.generate(null);
        // 输出警告信息
        for (String w : warning) {
            System.out.println(w);
        }
    }

}
