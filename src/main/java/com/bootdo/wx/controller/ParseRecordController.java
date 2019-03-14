package com.bootdo.wx.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.bootdo.common.utils.FileUtils;
import com.wx.demo.tools.Constant;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import com.bootdo.wx.domain.ParseRecordDO;
import com.bootdo.wx.service.ParseRecordService;
import com.bootdo.common.utils.PageUtils;
import com.bootdo.common.utils.Query;
import com.bootdo.common.utils.R;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;

/**
 * 
 * 
 * @author zcg
 * @email 804188877@qq.com
 * @date 2019-03-11 10:30:23
 */
 
@Controller
@RequestMapping("/wx/parseRecord")
public class ParseRecordController {
	private static Logger logger = LoggerFactory.getLogger(ParseRecordController.class);
	@Autowired
	private ParseRecordService parseRecordService;
	
	@GetMapping()
	@RequiresPermissions("wx:parseRecord:parseRecord")
	String ParseRecord(){
	    return "wx/parseRecord/parseRecord";
	}
	
	@ResponseBody
	@GetMapping("/list")
	@RequiresPermissions("wx:parseRecord:parseRecord")
	public PageUtils list(@RequestParam Map<String, Object> params){
		//查询列表数据
        Query query = new Query(params);
		List<ParseRecordDO> parseRecordList = parseRecordService.list(query);
		int total = parseRecordService.count(query);
		PageUtils pageUtils = new PageUtils(parseRecordList, total);
		return pageUtils;
	}
	
	@GetMapping("/add")
	@RequiresPermissions("wx:parseRecord:add")
	String add(){
	    return "wx/parseRecord/add";
	}
	@GetMapping("/uploadAndParse")
	@RequiresPermissions("wx:parseRecord:uploadAndParse")
	String uploadAndParse(){
	    return "wx/parseRecord/uploadAndParse";
	}

	@GetMapping("/edit/{id}")
	@RequiresPermissions("wx:parseRecord:edit")
	String edit(@PathVariable("id") Long id,Model model){
		ParseRecordDO parseRecord = parseRecordService.get(id);
		model.addAttribute("parseRecord", parseRecord);
	    return "wx/parseRecord/edit";
	}
	
	/**
	 * 保存
	 */
	@ResponseBody
	@PostMapping("/save")
	@RequiresPermissions("wx:parseRecord:add")
	public R save( ParseRecordDO parseRecord){
		if(parseRecordService.save(parseRecord)>0){
			return R.ok();
		}
		return R.error();
	}

	/**
	 * 上传62数据文件-txt
	 *
	 * @return
	 */
	@SuppressWarnings("finally")
	@RequestMapping(value = "/upload62", method = RequestMethod.POST)
	@ResponseBody
	public String uploadPhoto(HttpServletRequest request, MultipartFile myfile) {
		String result = "";
		if (myfile!=null && myfile.getSize() > 0) {
			try {
				//String realPath = request.getSession().getServletContext().getRealPath("");
//                PropertiesUtils propertiesUtils = new PropertiesUtils();
//                String realPath = propertiesUtils.getConfig("", "commonFiles");
				result = FileUtils.uploadFile(myfile, "62data", "");
			} catch (Exception e) {
				logger.error("上传62数据失败:" + e.getMessage());
			}
		}
		return result;
	}

	/**
	 * 解析并处理62数据文件-txt
	 *
	 * @return
	 */
	@SuppressWarnings("finally")
	@RequestMapping(value = "/parse62Data", method = RequestMethod.POST)
	@ResponseBody
	public R parse62Data(HttpServletRequest request, String url) {
		R ret=new R();
		try {
			if (!StringUtils.isEmpty(url)) {
				byte[] b=FileUtils.downloadFile_NoRootPath(url);
				if (b.length != 0) {
					String data62 = new String(b, Constant.DEFAULT_DECODE);
					BufferedReader rdr = new BufferedReader(new StringReader(data62));
					List<String> lines = new ArrayList<String>();
					try {
						for (String line = rdr.readLine(); line != null; line = rdr.readLine()) {
							lines.add(line);
						}
						rdr.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
					if (lines.size()>0) {
						ret=parseRecordService.batch62DataBusi(lines,url);
					}
				}
			}
		} catch (Exception e) {
			logger.error("解析62数据失败:" + e.getMessage());
			return R.error();
		}
		return ret;
	}

	/**
	 * 修改
	 */
	@ResponseBody
	@RequestMapping("/update")
	@RequiresPermissions("wx:parseRecord:edit")
	public R update( ParseRecordDO parseRecord){
		parseRecordService.update(parseRecord);
		return R.ok();
	}
	
	/**
	 * 删除
	 */
	@PostMapping( "/remove")
	@ResponseBody
	@RequiresPermissions("wx:parseRecord:remove")
	public R remove( Long id){
		if(parseRecordService.remove(id)>0){
		return R.ok();
		}
		return R.error();
	}
	
	/**
	 * 删除
	 */
	@PostMapping( "/batchRemove")
	@ResponseBody
	@RequiresPermissions("wx:parseRecord:batchRemove")
	public R remove(@RequestParam("ids[]") Long[] ids){
		parseRecordService.batchRemove(ids);
		return R.ok();
	}
	
}
