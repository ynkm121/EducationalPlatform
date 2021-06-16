package com.liu.service;

import com.liu.controller.student.vo.UncheckedVO;
import com.liu.util.PageQueryUtils;
import com.liu.util.PageResult;

import java.util.List;

public interface UncheckedService {

    PageResult getUncheckedPage(PageQueryUtils utils);
}
