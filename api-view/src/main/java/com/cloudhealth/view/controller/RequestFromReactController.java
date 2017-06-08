package com.cloudhealth.view.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import com.cloudhealth.view.entity.AnnovarPoint;
import com.cloudhealth.view.entity.ClinvarPoint;
import com.cloudhealth.view.entity.EspPoint;
import com.cloudhealth.view.entity.ExacPoint;
import com.cloudhealth.view.entity.GnoExoPoint;
import com.cloudhealth.view.entity.GnoGenomePoint;
import com.cloudhealth.view.entity.HgmdPoint;
import com.cloudhealth.view.entity.OnekgPoint;
import com.cloudhealth.view.entity.VarAnnoPoint;
import com.cloudhealth.view.entity.VarAnnoPoint_history;
import com.cloudhealth.view.service.PointService;

@Controller
@RequestMapping(value={"/query"})
public class RequestFromReactController {

	@Autowired
	PointService pointService;
	
	@RequestMapping("/reactgno_gen/{chr}/{pos}/{ref}/{alt}")
	public @ResponseBody GnoGenomePoint queryGnoGen(@PathVariable String chr, @PathVariable int pos, @PathVariable String ref, @PathVariable String alt, Model model) {
		
		GnoGenomePoint point = pointService.queryGnoGenPoint(chr, pos, ref, alt);

		return point;
	}
	
	@RequestMapping("/reactgno_exo/{chr}/{pos}/{ref}/{alt}")
	public @ResponseBody GnoExoPoint queryGnoExo(@PathVariable String chr, @PathVariable int pos, @PathVariable String ref, @PathVariable String alt, Model model) {
		GnoExoPoint point = pointService.queryGnoExoPoint(chr, pos, ref, alt);

		return point;
	}
	
	@RequestMapping("/reactonekg/{chr}/{pos}/{ref}/{obs}")
	public @ResponseBody OnekgPoint query1kg(@PathVariable String chr, @PathVariable int pos, @PathVariable String ref, @PathVariable String obs, Model model) {
		OnekgPoint point = pointService.queryOnekgPoint(chr, pos, ref, obs);

		return point;
	}
	
	@RequestMapping("/reactesp/{chr}/{start}/{ref}/{alt}")
	public @ResponseBody EspPoint queryEsp(@PathVariable String chr, @PathVariable int start, @PathVariable("start") int end, @PathVariable String ref, @PathVariable String alt, Model model) {
		EspPoint point = pointService.queryEspPoint(chr, start, end,ref, alt);

		return point;
	}
	
	@RequestMapping("/reactexac/{chr}/{start}/{ref}/{alt}")
	public @ResponseBody ExacPoint queryExac(@PathVariable String chr, @PathVariable int start, @PathVariable("start") int end, @PathVariable String ref, @PathVariable String alt, Model model) {
		ExacPoint point = pointService.queryExacPoint(chr, start, end, ref, alt);

		return point;
	}
	
	@RequestMapping("/reactannovar/{chr}/{start}/{ref}/{alt}")
	public @ResponseBody AnnovarPoint queryAnnovar(@PathVariable String chr, @PathVariable int start, @PathVariable("start") int end, @PathVariable String ref, @PathVariable String alt, Model model) {
		AnnovarPoint point = pointService.queryAnnovarPoint(chr, start, end, ref, alt);

		return point;
	}
	
	@RequestMapping("/reactclinvar/{chr}/{start}/{ref}/{alt}")
	public @ResponseBody ClinvarPoint queryClinvar(@PathVariable String chr, @PathVariable int start, @PathVariable("start") int end, @PathVariable String ref, @PathVariable String alt, Model model) {
		ClinvarPoint point =pointService.queryClinvarPoint(chr, start, end, ref, alt);
		
		return point;
	}
	
	@RequestMapping("/reacthistory/{chr}/{pos}/{ref}/{alt}")
	public @ResponseBody List<VarAnnoPoint_history> queryHistory(@PathVariable String chr, @PathVariable int pos, @PathVariable String ref, @PathVariable String alt, Model model) {
		List<VarAnnoPoint_history> list = pointService.queryVarAnnoHistory(chr, pos, ref, alt);
	
		return list;
	}
	
	@RequestMapping("reactvarAnnoDetail/{chr}/{pos}/{ref}/{alt}")
	public @ResponseBody VarAnnoPoint queryVarAnno(@PathVariable String chr, @PathVariable int pos, @PathVariable String ref, @PathVariable String alt, Model model) {
		VarAnnoPoint point = pointService.queryVarAnnoPoint(chr, pos, ref, alt);
	
		return point;
	}
	
	@RequestMapping("reacthgmd/{chr}/{pos}/{ref}/{alt}")
	public @ResponseBody HgmdPoint queryHGMD(@PathVariable String chr, @PathVariable int pos, @PathVariable String ref, @PathVariable String alt, Model model) {
		HgmdPoint point = pointService.queryHgmdPoint(chr, pos, ref, alt);
	
		return point;
	}
}
