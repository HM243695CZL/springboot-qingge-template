package com.hl.springboot.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hl.springboot.entity.Files;
import com.hl.springboot.mapper.FilesMapper;
import com.hl.springboot.service.IFilesService;
import org.springframework.stereotype.Service;

@Service
public class IFilesServiceImpl extends ServiceImpl<FilesMapper, Files> implements IFilesService {
}
