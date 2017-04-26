package com.cloudhealth.view.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import com.cloudhealth.view.model.AFPoint;
import com.cloudhealth.view.model.AnnovarPoint;
import com.cloudhealth.view.model.ClinvarPoint;
import com.cloudhealth.view.model.EspPoint;
import com.cloudhealth.view.model.ExacPoint;
import com.cloudhealth.view.model.GnoExoPoint;
import com.cloudhealth.view.model.GnoGenomePoint;
import com.cloudhealth.view.model.OnekgPoint;
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
	public @ResponseBody List<AFPoint> listAFPoints(@RequestParam("sample") String sample, @RequestParam("chr") String chr, @RequestParam("start") int start, @RequestParam("end") int end) {
		return pointService.listAFPoints(sample, chr, start, end);
	}
	
	@RequestMapping("/symbol")
	public @ResponseBody List<AFPoint> listAFPoint(@RequestParam("sample") String sample,@RequestParam("symbol") String symbol) {
		return pointService.listAFPoints(sample, symbol);
	}
	
	@RequestMapping("/nm")
	public @ResponseBody List<AFPoint> listAFPointByNm(@RequestParam("sample") String sample, @RequestParam("nm") String nm) {
		return pointService.listAFPointsByNm(sample, nm);
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
	
	
}
