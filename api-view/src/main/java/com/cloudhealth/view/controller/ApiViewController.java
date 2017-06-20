package com.cloudhealth.view.controller;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.util.HashMap;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import com.cloudhealth.view.entity.AnnovarPoint;
import com.cloudhealth.view.entity.ClinvarPoint;
import com.cloudhealth.view.entity.EspPoint;
import com.cloudhealth.view.entity.ExacPoint;
import com.cloudhealth.view.entity.GnoExoPoint;
import com.cloudhealth.view.entity.GnoGenomePoint;
import com.cloudhealth.view.entity.HgmdPoint;
import com.cloudhealth.view.entity.HgmdVarAnnoPoint;
import com.cloudhealth.view.entity.OnekgPoint;
import com.cloudhealth.view.entity.VarAnnoPoint;
import com.cloudhealth.view.entity.VarAnnoPoint_history;
import com.cloudhealth.view.model.PointForm;
import com.cloudhealth.view.service.PointService;

@Controller
@RequestMapping(value={"/query"})
public class ApiViewController {
	
	@Autowired
	PointService pointService;
	
	@RequestMapping(value="/")
	public String Query(Model model) {
		
		model.addAttribute("sampleIds", pointService.listSampleId());
		model.addAttribute("trioDiffIds",pointService.listTrioDiffGroup());
		return "/queryTypeSelect";
	}
	
	@RequestMapping("/range")
	public @ResponseBody HashMap<String,Object> listAFPoints(@RequestParam("sample") String sample, @RequestParam(value="chr",defaultValue="1") String chr, @RequestParam(value="start", defaultValue="0") Integer start, @RequestParam(value="end",defaultValue="0") Integer end,@RequestParam("offset") Integer offset, @RequestParam("maxResults") Integer maxResult, @RequestParam(value="varAnno",defaultValue="ALL") String varAnnoGroupType, @RequestParam(value="hgmd",defaultValue="ALL") String hgmdType) {
		if("pl choose".equals(varAnnoGroupType))
			varAnnoGroupType = "ALL";

		if("pl choose".equals(hgmdType))
			hgmdType = "ALL";
		
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("list", pointService.listAFPoints(sample, chr, start, end,offset,maxResult,varAnnoGroupType,hgmdType));
		map.put("page", pointService.count(sample, chr, start, end,varAnnoGroupType,hgmdType));
		return map;
	}
	
	@RequestMapping("/symbol")
	public @ResponseBody HashMap<String, Object> listAFPoint(@RequestParam("sample") String sample,@RequestParam("symbol") String symbol,@RequestParam("offset") Integer offset, @RequestParam("maxResults") Integer maxResult, @RequestParam(value="varAnno", defaultValue="ALL") String varAnnoGroupType, @RequestParam(value="hgmd",defaultValue="ALL") String hgmdType) {
		if("pl choose".equals(varAnnoGroupType))
			varAnnoGroupType = "ALL";

		if("pl choose".equals(hgmdType))
			hgmdType = "ALL";
		
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("list", pointService.listAFPoints(sample, symbol,offset,maxResult,varAnnoGroupType,hgmdType));
		map.put("page", pointService.count(sample, symbol,varAnnoGroupType,hgmdType));
		return map;
	}
	
	@RequestMapping("/nm")
	public @ResponseBody HashMap<String, Object> listAFPointByNm(@RequestParam("sample") String sample, @RequestParam("nm") String nm,@RequestParam("offset") Integer offset, @RequestParam("maxResults") Integer maxResult, @RequestParam(value="varAnno", defaultValue="ALL") String varAnnoGroupType,@RequestParam(value="hgmd",defaultValue="ALL") String hgmdType) {
		if("pl choose".equals(varAnnoGroupType))
			varAnnoGroupType = "ALL";

		if("pl choose".equals(hgmdType))
			hgmdType = "ALL";
		
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("list", pointService.listAFPointsByNm(sample, nm,offset,maxResult,varAnnoGroupType,hgmdType));
		map.put("page", pointService.countByNm(sample, nm,varAnnoGroupType,hgmdType));
		return map;
	}
	
	@RequestMapping("/gno_gen/{chr}/{pos}/{ref}/{alt}")
	public String queryGnoGen(@PathVariable String chr, @PathVariable int pos, @PathVariable String ref, @PathVariable String alt, Model model) {
		
		GnoGenomePoint point = pointService.queryGnoGenPoint(chr, pos, ref, alt);
		model.addAttribute("gno_gen", point);
		return "/PointDetail/gno_gen_Form";
	}
	
	@RequestMapping("/gno_exo/{chr}/{pos}/{ref}/{alt}")
	public String queryGnoExo(@PathVariable String chr, @PathVariable int pos, @PathVariable String ref, @PathVariable String alt, Model model) {
		GnoExoPoint point = pointService.queryGnoExoPoint(chr, pos, ref, alt);
		model.addAttribute("gno_exo", point);
		return "/PointDetail/gno_exo_Form";
	}
	
	@RequestMapping("/onekg/{chr}/{pos}/{ref}/{obs}")
	public String query1kg(@PathVariable String chr, @PathVariable int pos, @PathVariable String ref, @PathVariable String obs, Model model) {
		OnekgPoint point = pointService.queryOnekgPoint(chr, pos, ref, obs);
		model.addAttribute("onekg", point);
		return "/PointDetail/onekg_Form";
	}
	
	@RequestMapping("/esp/{chr}/{start}/{ref}/{alt}")
	public String queryEsp(@PathVariable String chr, @PathVariable int start, @PathVariable("start") int end, @PathVariable String ref, @PathVariable String alt, Model model) {
		EspPoint point = pointService.queryEspPoint(chr, start, end,ref, alt);
		model.addAttribute("esp", point);
		return "/PointDetail/esp_Form";
	}
	
	@RequestMapping("/exac/{chr}/{start}/{ref}/{alt}")
	public String queryExac(@PathVariable String chr, @PathVariable int start, @PathVariable("start") int end, @PathVariable String ref, @PathVariable String alt, Model model) {
		ExacPoint point = pointService.queryExacPoint(chr, start, end, ref, alt);
		model.addAttribute("exac", point);
		return "/PointDetail/exac_Form";
	}
	
	@RequestMapping("/annovar/{chr}/{start}/{ref}/{alt}")
	public String queryAnnovar(@PathVariable String chr, @PathVariable int start, @PathVariable("start") int end, @PathVariable String ref, @PathVariable String alt, Model model) {
		AnnovarPoint point = pointService.queryAnnovarPoint(chr, start, end, ref, alt);
		model.addAttribute("annovar", point);
		return "/PointDetail/annovar_Form";
	}
	
	@RequestMapping("/clinvar/{chr}/{start}/{ref}/{alt}")
	public String queryClinvar(@PathVariable String chr, @PathVariable int start, @PathVariable("start") int end, @PathVariable String ref, @PathVariable String alt, Model model) {
		ClinvarPoint point =pointService.queryClinvarPoint(chr, start, end, ref, alt);
		model.addAttribute("clinvar", point);
		return "/PointDetail/clinvar_Form";
	}
	
	@RequestMapping("/history/{chr}/{pos}/{ref}/{alt}")
	public String queryHistory(@PathVariable String chr, @PathVariable int pos, @PathVariable String ref, @PathVariable String alt, Model model) {
		List<VarAnnoPoint_history> list = pointService.queryVarAnnoHistory(chr, pos, ref, alt);
		model.addAttribute("varAnnoHisList", list);
		return "/PointDetail/varAnnoHis_Form";
	}
	
	@RequestMapping("varAnnoDetail/{chr}/{pos}/{ref}/{alt}")
	public String queryVarAnno(@PathVariable String chr, @PathVariable int pos, @PathVariable String ref, @PathVariable String alt, Model model) {
		VarAnnoPoint point = pointService.queryVarAnnoPoint(chr, pos, ref, alt);
		model.addAttribute("varAnno", point);
		return "/PointDetail/varAnno_Form";
	}
	
	@RequestMapping("hgmd/{chr}/{pos}/{ref}/{alt}")
	public String queryHGMD(@PathVariable String chr, @PathVariable int pos, @PathVariable String ref, @PathVariable String alt, Model model) {
		HgmdPoint point = pointService.queryHgmdPoint(chr, pos, ref, alt);
		model.addAttribute("hgmd", point);
		return "/PointDetail/hgmd_Form";
	}
	
	@RequestMapping(value="/batchupdate/{sampleId}", method=RequestMethod.POST)
	public @ResponseBody String batchUpdate(PointForm pointForm, @PathVariable String sampleId) {

		String message = pointService.batchUpdate(pointForm.getVarAnnoPoints(), sampleId);
		if(("successUpdate").equals(message))
			return "Update Successfully";
		else
			return "Update Fail";
		
	}
	
	// function with react
	@RequestMapping("/singlesampleall")
	public String redirectUrl(){
		return "/queryAll"; // react
	}
	
	@RequestMapping("/singlesample")
	public String redirectUrl2() {
		return "/singleSelect"; //old jsp with ajax/jquery to organism html
	}
	
	@RequestMapping("/threesample")
	public String redirectUrl3() {
		return "/threeSelect";
	}
	
	// return DM/DM?/Path/Likely Path count and list
	@RequestMapping("/queryallpoints/{sampleID}")
	public @ResponseBody HashMap<String, Object> getAllPoints(@PathVariable("sampleID") String sampleID) {
		
		HashMap<String, Object> map = new HashMap<String, Object>();
		List<HgmdVarAnnoPoint> list = pointService.queryAll(sampleID);
		int dm = 0;
		int dmdoubt = 0;
		int path = 0;
		int likelyPath = 0;
		
		for(HgmdVarAnnoPoint point : list) {
			
			if("DM".equals(point.getCLASS())){
				dm++;
			}
			
			if("DM?".equals(point.getCLASS())){
				dmdoubt++;
			}
			
			if("PATH".equals(point.getCategory())){
				path++;
			}
			
			if("LiPATH".equals(point.getCategory())){
				likelyPath++;
			}
		}
		
		map.put("dm", dm);
		map.put("dmdoubt", dmdoubt);
		map.put("path", path);
		map.put("likelyPath", likelyPath);
		
		map.put("list", list);
		
		return map;
		
	}
	
	//handle request from three sample compare
	@RequestMapping("/queryrange")
	public @ResponseBody HashMap<String, Object> queryFromRange(@RequestParam("chr") String chr, @RequestParam("start") int start, @RequestParam("end") int end,
		@RequestParam("parentM") String parentM, @RequestParam("parentF") String parentF, @RequestParam("child") String child, @RequestParam("perpage") int perpage, 
		@RequestParam("offset") int offset, @RequestParam("hgmdSelect") String hgmdSelect, @RequestParam("clinvarSelect") String clinvarSelect) {
		
		return pointService.queryWithRange(parentM, parentF, child, chr, start, end, perpage, offset,hgmdSelect,clinvarSelect);
			
	}
	
	@RequestMapping("/querysymbol")
	public @ResponseBody HashMap<String, Object> queryFromSymbol(@RequestParam("symbol") String symbol, @RequestParam("parentM") String parentM, @RequestParam("parentF") String parentF, @RequestParam("child") String child, @RequestParam("perpage") int perpage, @RequestParam("offset") int offset,
			@RequestParam("hgmdSelect") String hgmdSelect, @RequestParam("clinvarSelect") String clinvarSelect) {
		
		return pointService.queryWithSymbol(parentF, parentM, child, symbol, perpage, offset,hgmdSelect,clinvarSelect);
	}
	
	@RequestMapping("/querynm")
	public @ResponseBody HashMap<String, Object> queryFromNm(@RequestParam("nm") String nm, @RequestParam("parentM") String parentM, @RequestParam("parentF") String parentF, @RequestParam("child") String child, @RequestParam("perpage") int perpage, @RequestParam("offset") int offset,
			@RequestParam("hgmdSelect") String hgmdSelect, @RequestParam("clinvarSelect") String clinvarSelect) {
		
		return pointService.queryWithNm(parentM, parentF, child, nm, perpage, offset,hgmdSelect,clinvarSelect);
	}
	
	//handle request from query all three sample diff
	@RequestMapping("/queryAllDiff")
	public @ResponseBody HashMap<String, Object> queryForAllTrioDiff(@RequestParam("parentM") String parentM, @RequestParam("parentF") String parentF, @RequestParam("child") String child, @RequestParam("perpage") int perpage, @RequestParam("offset") int offset,
			@RequestParam("hgmdSelect") String hgmdSelect, @RequestParam("clinvarSelect") String clinvarSelect) {
		
		return pointService.queryChildDiffParent(parentM, parentF, child, perpage, offset, hgmdSelect, clinvarSelect);
	}
	
	//handle request from trio diff analytics
	@RequestMapping(value="triodiffanalytics", method=RequestMethod.GET)
	public String returnPage() {
		return "/trioDiffAnalytics";
	}
	@RequestMapping(value="handleTrioDiff/{child}/{father}/{mother}", method=RequestMethod.GET)
	public @ResponseBody String handleTrioAnalyze(@PathVariable("child") String child, 
												  @PathVariable("father") String father, 
												  @PathVariable("mother") String mother) {
		
		return pointService.handleTrioDiffAnalyze(child, father, mother);
	}
	
	//upload vcf File 
	@RequestMapping(value="uploadvcf", method= RequestMethod.GET)
	public String returnUploadPage() {
		return "/uploadVCF";
	}
	
	//TODO
	@RequestMapping(value="uploadvcf", method=RequestMethod.POST)
	public @ResponseBody String uploadFile(@RequestParam("name") String name, @RequestParam("file") MultipartFile file) {
		if(! file.isEmpty()) {
			try {
				byte[] bytes= file.getBytes();
				
				//creating the directory to store file
				String rootPath = System.getProperty("catalina.home");
				File dir = new File(rootPath + File.separator + "tmpFiles");
				if(! dir.exists())
					dir.mkdirs();
				
				//create the file on server
				File serverFile = new File(dir.getAbsolutePath() + File.separator + name);
				BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(serverFile));
				stream.write(bytes);
				stream.flush();
				stream.close();
				
				System.out.println("Server File Location=" + serverFile.getAbsolutePath());
				
				//TODO
				return "You successfully upload file " + name;
			} catch (Exception e) {
				return "You failed to upload " + name + "=>" + e.getMessage();
			}
		} else {
			return "You failed to upload " + name + " because the file is empty";
		}
	}
	
}
