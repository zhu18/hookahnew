package com.jusfoun.hookah.console.server.api.sys;

import com.jusfoun.hookah.core.domain.Dict;
import com.jusfoun.hookah.core.utils.ReturnData;
import com.jusfoun.hookah.rpc.api.DictService;
import org.springframework.web.bind.annotation.PathVariable;
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

    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public ReturnData save(Dict dict) {
        try {
            dictService.insert(dict);
        } catch (Exception e) {
            e.printStackTrace();
            return ReturnData.error("保存失败");
        }
        return ReturnData.success();
    }

    @RequestMapping(value = "/edit", method = RequestMethod.POST)
    public Object update(Dict dict) {
        if (dict.getDictId() != null) {
            int i = dictService.updateById(dict);
            return ReturnData.success(dict);
        } else {
            return ReturnData.error("传递参数错误");
        }
    }

    @RequestMapping(value = "/delete/{id}", method = RequestMethod.POST)
    public ReturnData delete(@PathVariable String id) {

        if (id != null) {
            int i = dictService.delete(Long.decode(id));
            return ReturnData.success(id);
        } else {
            return ReturnData.error("传递参数错误");
        }
    }
}
