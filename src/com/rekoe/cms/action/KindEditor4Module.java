package com.rekoe.cms.action;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.ServletContext;
import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.log.Log;
import org.nutz.log.Logs;
import org.nutz.mvc.annotation.AdaptBy;
import org.nutz.mvc.annotation.At;
import org.nutz.mvc.annotation.Param;
import org.nutz.mvc.upload.UploadAdaptor;

/**
 * KindEditor在线编辑器文件上传,管理模块
 * @author Elkan(elkan1788@gmail.com)
 */
@At("/nutz/ke4plugin")
@AdaptBy(type = UploadAdaptor.class)
@IocBean
public class KindEditor4Module {
    // 日志输出对象
    private static Log log = Logs.get();
    // 文件目录名称
    private String fileDir;
    // 文件后缀名称
    private String fileExt;
    // 当前站点上下文
    private String pageCtx;
    // 站点真实路径
    private String relPath;
    // 上传文件保存路径
    private String savePath;
    // 允许上传文件后缀MAP数组
    private static final HashMap<String, String> extMap = new HashMap<String, String>();
    // 允许上传文件大小MAP数组
    private static final HashMap<String,Long> sizeMap = new HashMap<String, Long>();
    // 上传文件存放根目录
    private String filePath = "/attached/";    
    
    static {
        // 初始后缀名称MAP数组
        extMap.put("image", "gif,jpg,jpeg,png,bmp");
        extMap.put("flash", "swf,flv");
        extMap.put("media", "swf,flv,mp3,wav,wma,wmv,mid,avi,mpg,asf,rm,rmvb");
        extMap.put("file", "doc,docx,xls,xlsx,ppt,txt,zip,rar");
        // 初始文件大小MAP数组
        sizeMap.put("image", 100 * 1024l);
        sizeMap.put("flash", 10 * 1024 * 1024l);
        sizeMap.put("media", 10 * 1024 * 1024l);
        sizeMap.put("file", 10 * 1024 * 1024l);
    }

    @At
    public Map<String, Object> upload(@Param("imgFile") File tempFile, 
            @Param("dir") String dir, ServletContext context) {
        // 初始相关变量
        Map<String, Object> execute = new HashMap<String, Object>();
        pageCtx = context.getContextPath().concat(filePath);
        relPath = context.getRealPath(filePath);
        fileDir = dir;
        if (null == dir || dir.isEmpty()) {
            fileDir = "file";
        }
        // 检查是否有上传文件
        if (null == tempFile) {
            execute.put("error", 1);
            execute.put("message", "请选择上传文件.");
            return execute;
        }
        // 检查上传文件保存目录是否存在或可写
        if (!isExistOrRwFolder()) {
            execute.put("error", 1);
            execute.put("message", "上传文件保存目录不存在或\n是没有写入权限,请检查.");
            return execute;
        }
        // 检查目录名称是否正确
        if (!extMap.containsKey(fileDir)) {
            execute.put("error", 1);
            execute.put("message", "目录名不正确,请检查.");
            return execute;
        }
        // 计算出文件后缀名
        String tempFileName = tempFile.getName();
        fileExt = tempFileName.substring(tempFileName.lastIndexOf(".") + 1);
        // 检查上传文件类型
        if(!Arrays.<String>asList(extMap.get(fileDir).split(",")).contains(fileExt)){
            execute.put("error", 1);
            execute.put("message", "上传文件的格式被拒绝,\n只允许" + extMap.get(fileDir) + "格式的文件.");
            return execute;
        }
        // 检查上传文件的大小
        long maxSize = sizeMap.get(fileDir);
        if (tempFile.length() > maxSize) {
            execute.put("error", 1);
            String size = null;
            if(maxSize < 1024) {
                size = maxSize + "B";
            }
            if(maxSize > 1024 && maxSize < 1024 * 1024){
                size = maxSize/1024 + "KB";
            }
            if(maxSize > 1024 * 1024){
                size = maxSize/(1024 * 1024) + "MB";
            }
            execute.put("message", "上传文件大小超过限制,只允\n许上传小于 " + size + " 的文件.");
            return execute;
        }
        // 生成新的文件名,并按日期分类
        newSavePath();
        // 拷贝上传文件至指定存放目录
        copy(tempFile, savePath);
        // 计算出文件输出路径
        int point = savePath.lastIndexOf("/") - 8;
        StringBuilder url = new StringBuilder(pageCtx);
        url.append(fileDir).append("/");
        url.append(savePath.substring(point));
        // 返回上传文件的输出路径至前端
        execute.put("error", 0);
        execute.put("url", url.toString());
        return execute;
    }
    
    @At
    public Map<String, Object> manager(@Param("dir") String dir,
            @Param("path") String path, @Param("order") String order,
            ServletContext context) {
        // 初始相关变量
        Map<String, Object> execute = new HashMap<String, Object>();
        pageCtx = context.getContextPath().concat(filePath);
        relPath = context.getRealPath(filePath);
        fileDir = dir;
        if (null == dir || dir.isEmpty()) {
            fileDir = "file";
        }
        if (!extMap.containsKey(fileDir)) {
            execute.put("error", 1);
            execute.put("message", "目录名不正确,请检查.");
            return execute;
        }
        String tempPath = null == path ? fileDir.concat("/") : fileDir.concat("/"+path);
        String curPath = pageCtx.concat(tempPath);
        String curFileDir = relPath.concat("/"+tempPath);
        String curDir = path;
        String moveupDir = "";
        // 检查当前目录是否为根目录
        if (!"".equals(path)) {
            String str = curDir.substring(0, curDir.length() - 1);
            moveupDir = str.lastIndexOf("/") >= 0 ? str.substring(0, str.lastIndexOf("/") + 1) : "";
        }        
        // 检查..命令
        if(path.indexOf("..") >= 0){
            execute.put("error", 1);
            execute.put("message", "不允许使用..命令返回上一层.");
            return execute;
        }
        //最后一个字符不是/
        if (!"".equals(path) && !path.endsWith("/")) {
            execute.put("error", 1);
            execute.put("message", "文件路径不合法.");
            return execute;
        }
        // 检查当前目录
        File curPathFile = new File(curFileDir);
        if (!curPathFile.isDirectory()) {
            execute.put("error", 1);
            execute.put("message", "当前目录不存在.");
            return execute;
        }
        //遍历目录取的文件信息
        List<HashMap> fileList = new ArrayList<HashMap>();
        if (curPathFile.listFiles() != null) {
            for (File file : curPathFile.listFiles()) {
                HashMap<String, Object> hash = new HashMap<String, Object>();
                String fileName = file.getName();
                if (file.isDirectory()) {
                    hash.put("is_dir", true);
                    hash.put("has_file", (file.listFiles() != null));
                    hash.put("filesize", 0L);
                    hash.put("is_photo", false);
                    hash.put("filetype", "");
                } else if (file.isFile()) {
                    fileExt = fileName.substring(fileName.lastIndexOf(".") + 1).toLowerCase();
                    hash.put("is_dir", false);
                    hash.put("has_file", false);
                    hash.put("filesize", file.length());
                    hash.put("is_photo", Arrays.<String>asList(extMap.get("image").split(",")).contains(fileExt));
                    hash.put("filetype", fileExt);
                }
                hash.put("filename", fileName);
                hash.put("datetime", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(file.lastModified()));
                fileList.add(hash);
            }
        }
        // 文件排序方式
        String tempOrder = order != null ? order.toLowerCase() : "name";
        if ("size".equals(tempOrder)) {
            Collections.sort(fileList, new SizeComparator());
        } else if ("type".equals(tempOrder)) {
            Collections.sort(fileList, new TypeComparator());
        } else {
            Collections.sort(fileList, new NameComparator());
        }
        // 输出遍历后的文件信息数据
        execute.put("moveup_dir_path", moveupDir);
        execute.put("current_dir_path", curDir);
        execute.put("current_url", curPath);
        execute.put("total_count", fileList.size());
        execute.put("file_list", fileList);        
        return execute;
    }
    
    /**
     * 判断文件上传保存的文件夹是否存在或可写
     * @return 如果存在且可写返回"true",否则返回"false"
     */
    public boolean isExistOrRwFolder(){
        if(null == relPath || relPath.isEmpty()) {
            return false;
        }
        File folder = new File(relPath);
        if(!folder.exists())
            return false;
        if(!folder.isDirectory())
            return false;
        if(!folder.canWrite())
            return false;
        return true;
    }
    
    /**
     * 生成新的文件名,且按日期分类管理
     */
    public void newSavePath() {
        StringBuilder tempPath = new StringBuilder(relPath);
        tempPath.append("/").append(fileDir).append("/");
        SimpleDateFormat folderNameFormat = new SimpleDateFormat("yyyyMMdd");
        tempPath.append(folderNameFormat.format(new Date()));
        File temp = new File(tempPath.toString());
        if(!temp.exists())
            temp.mkdirs();
        SimpleDateFormat fileNameFormat = new SimpleDateFormat("yyyyMMddkkmmss_S");
        tempPath.append("/").append(fileNameFormat.format(new Date()));
        tempPath.append(".").append(fileExt);
        savePath = tempPath.toString().replaceAll("\\\\", "/");    
    }
    
    /**
     * 拷贝文件
     * @param src 源文件
     * @param tar 目标路径
     */
    public void copy(File src, String tar) {
        // 判断源文件或目标路径是否为空
        if (null == src 
                || null == tar 
                || tar.isEmpty()) {
            return;
        }
        InputStream srcIs = null;
        OutputStream tarOs = null;
        try {
            srcIs = new FileInputStream(src);
            File tarFile = new File(tar);
            tarOs = new FileOutputStream(tarFile);
            byte[] buffer = new byte[4096];
            int n = 0;
            while (-1 != (n = srcIs.read(buffer))) {
                tarOs.write(buffer, 0, n);
            }
        } catch (IOException e) {           
            log.error("Copy File is Fali, Because "+e);
        } finally {
            try {
                if (null != srcIs) {
                    srcIs.close();
                }
                if (null != tarOs) {
                    tarOs.close();
                }
            } catch (IOException e) {
                log.error("Close Stream is Fail, Because "+e);
            }
        }
    }
    
    /**
     * 根据文件名称排序
     */
    public class NameComparator implements Comparator {

        @Override
        public int compare(Object a, Object b) {
            HashMap hashA = (HashMap) a;
            HashMap hashB = (HashMap) b;
            if (((Boolean) hashA.get("is_dir")) && !((Boolean) hashB.get("is_dir"))) {
                return -1;
            } else if (!((Boolean) hashA.get("is_dir")) && ((Boolean) hashB.get("is_dir"))) {
                return 1;
            } else {
                return ((String) hashA.get("filename")).compareTo((String) hashB.get("filename"));
            }
        }
    }

    /**
     * 根据文件大小排序
     */
    public class SizeComparator implements Comparator {

        @Override
        public int compare(Object a, Object b) {
            HashMap hashA = (HashMap) a;
            HashMap hashB = (HashMap) b;
            if (((Boolean) hashA.get("is_dir")) && !((Boolean) hashB.get("is_dir"))) {
                return -1;
            } else if (!((Boolean) hashA.get("is_dir")) && ((Boolean) hashB.get("is_dir"))) {
                return 1;
            } else {
                if (((Long) hashA.get("filesize")) > ((Long) hashB.get("filesize"))) {
                    return 1;
                } else if (((Long) hashA.get("filesize")) < ((Long) hashB.get("filesize"))) {
                    return -1;
                } else {
                    return 0;
                }
            }
        }
    }

    /**
     * 根据文件类型排序
     */
    public class TypeComparator implements Comparator {

        @Override
        public int compare(Object a, Object b) {
            HashMap hashA = (HashMap) a;
            HashMap hashB = (HashMap) b;
            if (((Boolean) hashA.get("is_dir")) && !((Boolean) hashB.get("is_dir"))) {
                return -1;
            } else if (!((Boolean) hashA.get("is_dir")) && ((Boolean) hashB.get("is_dir"))) {
                return 1;
            } else {
                return ((String) hashA.get("filetype")).compareTo((String) hashB.get("filetype"));
            }
        }
    }
}