import Bean.Tasks;
import com.alibaba.fastjson.JSONObject;
import net.mamoe.mirai.Bot;
import net.mamoe.mirai.console.extension.PluginComponentStorage;
import net.mamoe.mirai.console.plugin.jvm.JavaPlugin;
import net.mamoe.mirai.console.plugin.jvm.JvmPluginDescriptionBuilder;
import net.mamoe.mirai.utils.MiraiLogger;
import org.jetbrains.annotations.NotNull;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public final class TaskPlugin extends JavaPlugin {
    public static final TaskPlugin INSTANCE = new TaskPlugin();

    public MiraiLogger logger = getLogger();

//    public String dataPath = PluginConfig.INSTANCE.getDataPath();

    private List<TaskChecker> checkers = new ArrayList<>();

    public File configPath;
    public File configFile;

    public Tasks tasks;

    private TaskPlugin() {
        super(new JvmPluginDescriptionBuilder("com.v6486449j.task-plugin", "1.0.0")
        .author("6486449j")
        .info("一个管理事务的mirai-console插件")
        .build());
    }

    @Override
    public void onLoad(@NotNull PluginComponentStorage $this$onLoad) {
        /*//加载数据
        try {

            //检测路径是否存在
            if(!configPath.exists()) {
                configPath.mkdirs();
                logger.info("创建路径");
            }

            //检测文件是否存在
            if(!configFile.exists()) {
                configFile.createNewFile();
                logger.info("创建文件");
            }

            FileInputStream fis = new FileInputStream(configFile);
            BufferedReader br = new BufferedReader(new InputStreamReader(fis));

            //读入文件
            String str;
            while((str = br.readLine()) != null) {
                logger.info("读入数据");
            }

            //判断文件是否为空
            if(str == "") {
                tasks = new Tasks();
                tasks.setTasks(new ArrayList<>());
                logger.info("文件为空，设置新数据");
            } else {
                //读取数据，将JSON转换成对象
                JSONObject jsonObject = JSONObject.parseObject(str);
                tasks = JSONObject.toJavaObject(jsonObject, Tasks.class);
                logger.info("文件不为空，读入数据");
            }

            fis.close();
        } catch(Exception e) {
            e.printStackTrace();
        }*/
    }

    @Override
    public void onEnable() {
        logger.info("TaskPlugin加载");

        //加载配置
        TaskPlugin.INSTANCE.reloadPluginConfig(PluginConfig.INSTANCE);
//        TaskPlugin.INSTANCE.reloadPluginData(PluginData.INSTANCE);

        configPath = getConfigFolder();
        configFile = new File(configPath, "test_config.json");
        //加载数据
        try {

            //检测路径是否存在
            if(!configPath.exists()) {
                configPath.mkdirs();
                logger.info("创建路径");
            }

            //检测文件是否存在
            if(!configFile.exists()) {
                configFile.createNewFile();
                logger.info("创建文件");
            }

            FileInputStream fis = new FileInputStream(configFile);
            BufferedReader br = new BufferedReader(new InputStreamReader(fis));

            //读入文件
            String str;
            while((str = br.readLine()) != null) {
                logger.info("读入数据");
            }

            //判断文件是否为空
            if(str == null) {
                tasks = new Tasks();
                tasks.setTasks(new ArrayList<>());

                writeConfig();

/*
                try {
                    FileOutputStream fos = new FileOutputStream(configFile);
                    BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(fos));
                    bw.write(JSONObject.toJSONString(tasks));
                } catch(Exception e) {
                    e.printStackTrace();
                }
*/

                logger.info("文件为空，设置新数据");
            } else {
                //读取数据，将JSON转换成对象
                JSONObject jsonObject = JSONObject.parseObject(str);
                tasks = JSONObject.toJavaObject(jsonObject, Tasks.class);
                logger.info("文件不为空，读入数据");
            }

            fis.close();
        } catch(Exception e) {
            e.printStackTrace();
        }

        INSTANCE.getScheduler().delayed(2000, () -> {
            //获取所有Bot实例
            for(Bot bot : Bot.getInstances()) {
                //注册监听器
                bot.getEventChannel().registerListenerHost(new MyEventsListener());

                //添加事务检查器
                checkers.add(new TaskChecker(bot));
            }

            //为所有检查器启动线程
            for(TaskChecker ch : checkers) {
                INSTANCE.getScheduler().repeating(10000, ch);
            }
        });
    }

    @Override
    public void onDisable() {
        writeConfig();
    }

    public boolean writeConfig() {
        try {
            FileOutputStream fos = new FileOutputStream(TaskPlugin.INSTANCE.configFile);
            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(fos));

            String jsonString = JSONObject.toJSONString(tasks);

            bw.write(jsonString);
            bw.flush();
            fos.close();
            TaskPlugin.INSTANCE.logger.info("写入文件");

            return true;
        } catch(Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
