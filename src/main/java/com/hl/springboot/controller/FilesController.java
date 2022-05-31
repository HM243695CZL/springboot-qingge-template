package com.hl.springboot.controller;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hl.springboot.common.Constants;
import com.hl.springboot.common.Result;
import com.hl.springboot.entity.Files;
import com.hl.springboot.exception.ServiceException;
import com.hl.springboot.mapper.FilesMapper;
import com.hl.springboot.service.IFilesService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.List;

/**
 * 文件上传接口
 */

@RestController
@RequestMapping("/file")
public class FilesController {

    @Resource
    private IFilesService filesService;

    @Value("${files.upload.path}")
    private String fileUploadPath;

    @Resource
    private FilesMapper filesMapper;

    @Value("${server.ip}")
    private String serverIp;

    @Value("${server.port}")
    private String serverPort;

    @GetMapping("/page")
    public Result page(
            @RequestParam Integer pageIndex,
            @RequestParam Integer pageSize) {
        QueryWrapper<Files> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderByDesc("id");
        Page<Files> page = filesService.page(new Page<>(pageIndex, pageSize), queryWrapper);
        return Result.success(page);
    }

    @PostMapping("/delete")
    public Result delete(@RequestBody List<Integer> ids) {
        Files file = filesService.getById(ids.get(0));
        String name = file.getName();
        File f = new File(fileUploadPath + name);
       if (f.delete()) {
           return Result.success(filesService.removeByIds(ids));
       } else {
           return Result.error(Constants.CODE_500, "文件删除失败");
       }
    }

    /**
     * 文件上传接口
     * @param file
     * @return
     * @throws IOException
     */
    @PostMapping("/upload")
    public String upload(@RequestParam MultipartFile file) throws IOException {
        String originalFilename = file.getOriginalFilename();
        String type = FileUtil.extName(originalFilename);
        long size = file.getSize();
        File uploadParentFile = new File(fileUploadPath);
        // 配置的文件目录不存在就创建
        if (!uploadParentFile.exists()) {
            uploadParentFile.mkdirs();
        }
        // 定义一个文件唯一的标识码
        String uuid = IdUtil.fastSimpleUUID();
        String fileUUID = uuid + StrUtil.DOT + type;
        File uploadFile = new File(fileUploadPath + fileUUID);
        file.transferTo(uploadFile);
        String url = "http://" + serverIp + ":" + serverPort + "/file/download/" + fileUUID;
        // 存储数据库
        Files saveFile = new Files();
        saveFile.setName(fileUUID);
        saveFile.setOriginName(originalFilename);
        saveFile.setType(type);
        saveFile.setSize(size / 1024 + "KB");
        saveFile.setDownloadUrl(url);
        filesMapper.insert(saveFile);
        return url;
    }

    /**
     * 文件下载接口 http://localhost:9090/file/download/{fileUUID}
     * @param fileUUID
     * @param response
     * @throws IOException
     */
    @GetMapping("/download/{fileUUID}")
    public void download(@PathVariable String fileUUID, HttpServletResponse response) throws IOException {
        // 根据文件的唯一标识码获取文件
        File uploadFile = new File(fileUploadPath + fileUUID);
        if (!uploadFile.exists()) {
            throw new ServiceException(Constants.CODE_404, "文件不存在");
        }
        // 设置输出流的格式
        ServletOutputStream os = response.getOutputStream();
        response.addHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(fileUUID, "UTF-8"));
        response.setContentType("application/octet-stream");
        // 读取文件的字节流
        os.write(FileUtil.readBytes(uploadFile));
        os.flush();
        os.close();
    }
}
