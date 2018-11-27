package com.ychp.code.builder;

import com.github.jknack.handlebars.Handlebars;
import com.github.jknack.handlebars.Template;
import com.google.common.collect.Lists;
import com.ychp.handlebars.HandlebarsFactory;
import org.apache.commons.lang3.StringUtils;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author yingchengpeng
 * @date 2018-08-08
 */
public abstract class Builder {

    public List<String> build(String templatePath, String outPath,
                              String baseName,
                              Map<String, Object> templateParamMap,
                              Boolean isPath, Boolean isWriteToLocal){
        List<String> templates = getTemplatePath(templatePath);
        List<String> contents = Lists.newArrayListWithCapacity(templates.size());
        templateParamMap.put("currentDate", new Date());
        try {
            outPath = StringUtils.isEmpty(outPath) ? getDefaultOutPath(): outPath;
            String context;
            String fileName;

            for(String template : templates){
                if(StringUtils.isEmpty(template)){
                    continue;
                }
                context = buildFile(template, templateParamMap, isPath);
                context = context.replace("{ ", "{").replace(" }", "}");
                contents.add(context);
                if(isWriteToLocal) {
                    fileName = getFileName(template, baseName);
                    new FileService().writeToLocal(outPath + File.separator + fileName, context);
                }
            }
        } catch (IOException e){
            e.printStackTrace();
        }
        return contents;
    }

    /**
     * 获取模板文件
     * @return 有模板文件
     */
    private List<String> getTemplatePath(String basePath) {
        if(basePath == null){
            basePath = getDefaultTemplate().trim();
        }
        File file = new File(basePath);
        if(file.isFile()) {
            return Lists.newArrayList(basePath);
        }
        List<File> files = getAllFiles(basePath);
        return files.stream()
                .map(item -> {
                    String path = item.getPath();
                    path = path.substring(path.indexOf("resources") + "resources".length() + 1);
                    path = path.replace(".hbs", "");
                    return path;
                })
                .collect(Collectors.toList());
    }

    private List<File> getAllFiles(String basePath) {
        File file = new File(basePath);
        if(file.isFile()) {
            return Lists.newArrayList(file);
        } else if(!file.isDirectory()) {
            return Lists.newArrayListWithCapacity(0);
        }
        File[] files = file.listFiles();
        if(files == null) {
            return Lists.newArrayListWithCapacity(0);
        }
        List<File> result = Lists.newArrayList();
        for(File item : files) {
            if(item.isFile()) {
                result.add(item);
            } else if(file.isDirectory()) {
                result.addAll(getAllFiles(file.getPath()));
            }
        }
        return result;
    }

    /**
     * 获取默认模板路径
     * @return 默认模板路径
     */
    protected String getDefaultTemplate() {
        return "";
    }

    /**
     * 默认输出路径
     * @return
     */
    protected String getDefaultOutPath(){
        String path = this.getClass().getProtectionDomain().getCodeSource().getLocation().getPath();
        int lastIndex = path.lastIndexOf(File.separator) + 1;
        return path.substring(0, lastIndex);
    }

    protected String getFileName(String template, String baseName) {
        return StringUtils.isEmpty(baseName) ?
                template.substring(template.indexOf(File.separator) + 1) :
                baseName + template.substring(template.indexOf('.'));
    }

    /**
     * 渲染模板内容数据
     * @param templateOrPath 模板或路径
     * @param params 变量
     * @param isPath 是否为文件路径
     * @return 内容
     * @throws IOException
     */
    protected String buildFile(String templateOrPath, Map<String, Object> params, Boolean isPath) throws IOException {
        Handlebars handlebars = HandlebarsFactory.getInstance();
        Template template;
        if(isPath) {
            template = handlebars.compile(templateOrPath);
        } else {
            template = handlebars.compileInline(templateOrPath);
        }
        return template.apply(params);
    }
}
