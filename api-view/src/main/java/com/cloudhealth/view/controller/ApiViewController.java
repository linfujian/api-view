package com.cloudhealth.view.controller;

import java.util.HashMap;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import com.cloudhealth.view.model.AnnovarPoint;
import com.cloudhealth.view.model.ClinvarPoint;
import com.cloudhealth.view.model.EspPoint;
import com.cloudhealth.view.model.ExacPoint;
import com.cloudhealth.view.model.GnoExoPoint;
import com.cloudhealth.view.model.GnoGenomePoint;
import com.cloudhealth.view.model.OnekgPoint;
import com.cloudhealth.view.model.PointForm;
import com.cloudhealth.view.model.VarAnnoPoint;
import com.cloudhealth.view.model.VarAnnoPoint_history;
import com.cloudhealth.view.service.PointService;

@Controller
@RequestMapping(value={"/query"})
public class ApiViewController {
	
	@Autowired
	PointService pointService;
	
	@RequestMapping(value="/")
	public String Query() {
		return "/querySelect";
	}
	
	@RequestMapping("/range")
	public @ResponseBody HashMap<String,Object> listAFPoints(@RequestParam("sample") String sample, @RequestParam(value="chr",defaultValue="1") String chr, @RequestParam(value="start", defaultValue="0") Integer start, @RequestParam(value="end",defaultValue="0") Integer end,@RequestParam("offset") Integer offset, @RequestParam("maxResults") Integer maxResult) {
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("list", pointService.listAFPoints(sample, chr, start, end,offset,maxResult));
		map.put("page", pointService.count(sample, chr, start, end));
		return map;
	}
	
	@RequestMapping("/symbol")
	public @ResponseBody HashMap<String, Object> listAFPoint(@RequestParam("sample") String sample,@RequestParam("symbol") String symbol,@RequestParam("offset") Integer offset, @RequestParam("maxResults") Integer maxResult) {
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("list", pointService.listAFPoints(sample, symbol,offset,maxResult));
		map.put("page", pointService.count(sample, symbol));
		return map;
	}
	
	@RequestMapping("/nm")
	public @ResponseBody HashMap<String, Object> listAFPointByNm(@RequestParam("sample") String sample, @RequestParam("nm") String nm,@RequestParam("offset") Integer offset, @RequestParam("maxResults") Integer maxResult) {
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("list", pointService.listAFPointsByNm(sample, nm,offset,maxResult));
		map.put("page", pointService.countByNm(sample, nm));
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
	
	@RequestMapping(value="/batchupdate/{sampleId}", method=RequestMethod.POST)
	public @ResponseBody String batchUpdate(@ModelAttribute("pointForm") PointForm pointForm, @PathVariable String sampleId) {

		String message = pointService.batchUpdate(pointForm.getVarAnnoPoints(), sampleId);
		if(("successUpdate").equals(message))
			return "Update Successfully";
		else
			return "Update Fail";
		
	}
	
	
	
}
