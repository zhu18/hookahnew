package com.jusfoun.hookah.console.server.api.sys;

import com.jusfoun.hookah.core.domain.Dict;
import com.jusfoun.hookah.core.utils.ReturnData;
import com.jusfoun.hookah.rpc.api.DictService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author huang lei
 * @date 2017/4/13 下午2:42
 * @desc
 */
@RestController
@RequestMapping(value = "/api/sys/dict")
public class DictApi {

    @Resource
    private DictService dictService;

    @RequestMapping(value = "/tree", method = RequestMethod.GET)
    public Object getListInPage() {

        List<Dict> dictTreeList = dictService.selectTree();
        return dictTreeList;
    }
}
