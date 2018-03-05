package sw.melody.modules.dictionary.controller;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import sw.melody.config.R;
import sw.melody.modules.dictionary.mapper.DictionaryMapper;

/***
 * Created by ping on 2018/3/1
 */
@RestController
@RequestMapping("/dictionary")
public class DictionaryController {

    @Autowired
    private DictionaryMapper dictionaryMapper;

    @ApiOperation(value="根据字典的key获取取值")
    @ApiImplicitParam(name = "dkey", value = "字典的key", required = true, dataType = "String")
    @RequestMapping(value="/{dkey}", method= RequestMethod.GET)
    public R getByDkey(@PathVariable String dkey) {
        if (StringUtils.isEmpty(dkey)) {
            return R.error("参数不能为空");
        }
        return R.ok(dictionaryMapper.queryByDkey(dkey));
    }

    @ApiOperation(value="根据字典的key更新取值")
    @ApiImplicitParam(name = "dkey", value = "字典的key", required = true, dataType = "String")
    @RequestMapping(value="/u", method= RequestMethod.POST)
    public R updateByDkey(String dkey, String dvalue) {
        if (StringUtils.isEmpty(dkey) || StringUtils.isEmpty(dvalue)) {
            return R.error("参数不能为空");
        }
        dictionaryMapper.update(dkey, dvalue);
        return R.ok();
    }
}
