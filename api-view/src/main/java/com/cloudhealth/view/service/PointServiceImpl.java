package com.cloudhealth.view.service;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.cloudhealth.view.dao.PointDao;
import com.cloudhealth.view.entity.AFPoint;
import com.cloudhealth.view.entity.AnnovarPoint;
import com.cloudhealth.view.entity.ClinvarPoint;
import com.cloudhealth.view.entity.EspPoint;
import com.cloudhealth.view.entity.ExacPoint;
import com.cloudhealth.view.entity.GnoExoPoint;
import com.cloudhealth.view.entity.GnoGenomePoint;
import com.cloudhealth.view.entity.HgmdPoint;
import com.cloudhealth.view.entity.HgmdVarAnnoPoint;
import com.cloudhealth.view.entity.OnekgPoint;
import com.cloudhealth.view.entity.RangePoint;
import com.cloudhealth.view.entity.TrioDiffGroup;
import com.cloudhealth.view.entity.VarAnnoPoint;
import com.cloudhealth.view.entity.VarAnnoPoint_history;
import com.cloudhealth.view.model.VarAnnoReportPoint;

@Service
public class PointServiceImpl implements PointService {

	@Autowired
	PointDao pointDao;
	
	@Transactional(readOnly = true)
	public List<AFPoint> listAFPoints(String sampleId, String chr, int start, int end,Integer offset,Integer maxResults,String varAnnoGroupType, String hgmdType) {
		return pointDao.listAF(sampleId, chr, start, end,offset,maxResults,varAnnoGroupType,hgmdType);
	}
	
	public BigInteger count(String sampleId, String chr, int start, int end,String vcfAnnoGroupType, String hgmdType) {
		return pointDao.count(sampleId, chr, start, end,vcfAnnoGroupType,hgmdType);
	}

	public GnoGenomePoint queryGnoGenPoint(String chr, int pos, String ref, String alt) {
		return pointDao.queryGnoGen(chr, pos, ref, alt);
	}

	public GnoExoPoint queryGnoExoPoint(String chr, int pos, String ref, String alt) {
		return pointDao.queryGnoExo(chr, pos, ref, alt);
	}

	public OnekgPoint queryOnekgPoint(String chr, int pos, String ref, String obs) {
		return pointDao.queryOnekg(chr, pos, ref, obs);
	}

	public EspPoint queryEspPoint(String chr, int start, int end,String ref, String alt) {
		return pointDao.queryEsp(chr, start, end, ref, alt);		
	}
	
	public ExacPoint queryExacPoint(String chr, int start, int end, String ref, String alt) {
		return pointDao.queryExac(chr, start, end,ref, alt);
	}
	
	public AnnovarPoint queryAnnovarPoint(String chr, int start, int end, String ref, String alt) {
		return pointDao.queryAnnovar(chr, start, end, ref, alt);
	}
	
	public ClinvarPoint queryClinvarPoint(String chr, int start, int end, String ref, String alt) {
		return pointDao.queryClinvar(chr, start, end, ref, alt);
	}

	public List<AFPoint> listAFPoints(String sampleId, String symbol,Integer offset, Integer maxResults,String varAnnoGroupType,String hgmdType) {
		RangePoint range =  pointDao.findRangeBySymbol(symbol);
		
		if(range.getAcc() != null)
			return this.listAFPoints(sampleId, range.getChr_name(), Integer.valueOf(range.getTx_start()), Integer.valueOf(range.getTx_end()),offset,maxResults,varAnnoGroupType,hgmdType);
		return new ArrayList<AFPoint>();
	}
	
	public BigInteger count(String sampleId, String symbol,String vcfAnnoGroupType, String hgmdType) {
		RangePoint range =  pointDao.findRangeBySymbol(symbol);
		if(range.getAcc() != null)
			return this.count(sampleId, range.getChr_name(),Integer.valueOf(range.getTx_start()), Integer.valueOf(range.getTx_end()),vcfAnnoGroupType, hgmdType);
		return null;
	}

	public List<AFPoint> listAFPointsByNm(String sampleId, String nm,Integer offset, Integer maxResults,String varAnnoGroupType, String hgmdType) {
		RangePoint range = pointDao.findRangeByNm(nm);
		
		if(range.getAcc() != null)
			return this.listAFPoints(sampleId, range.getChr_name(), Integer.valueOf(range.getTx_start()), Integer.valueOf(range.getTx_end()),offset, maxResults, varAnnoGroupType,hgmdType);
		return new ArrayList<AFPoint>();
	}

	public BigInteger countByNm(String sampleId, String nm,String vcfAnnoGroupType, String hgmdType) {
		RangePoint range = pointDao.findRangeByNm(nm);
		if(range.getAcc() != null)
			return this.count(sampleId,  range.getChr_name(), Integer.valueOf(range.getTx_start()), Integer.valueOf(range.getTx_end()),vcfAnnoGroupType,hgmdType);
		return null;
	}

	public String batchUpdate(List<VarAnnoReportPoint> varAnnoPoints, String sampleId) {
		String message = pointDao.batchUpdate(varAnnoPoints,sampleId);
		if(message.equals("successUpdate"))
			return "successUpdate";
		else {
			return null;
		}
		
	}
	
	public VarAnnoPoint queryVarAnnoPoint(String chr, int pos, String ref, String alt) {
		return pointDao.queryVarAnno(chr, pos, ref, alt);
	}

	public List<VarAnnoPoint_history> queryVarAnnoHistory(String chr, int pos, String ref, String alt) {
		return pointDao.queryHistory(chr, pos, ref, alt);
	}

	//point query for hgmd
	public HgmdPoint queryHgmdPoint(String chr, int pos, String ref, String alt) {
		return pointDao.queryHgmd(chr, pos, ref, alt);
	}

	public List<HgmdVarAnnoPoint> queryAll(String sampleID) {
		return pointDao.queryAll(sampleID);
	}

	public HashMap<String, Object> queryWithRange(String maleId, String femaleId, String child, String chr, int start,
			int end, int perpage, int offset, String hgmdSelect, String clinvarSelect) {
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("count", pointDao.countThree(child, chr, start, end,hgmdSelect,clinvarSelect));
		map.put("list", pointDao.queryWithRange(maleId, femaleId, child, chr, start, end, perpage, offset,hgmdSelect,clinvarSelect));
		return map;
		
	}

	public HashMap<String, Object> queryWithSymbol(String maleId, String femaleId, String child, String symbol,
			int perpage, int offset, String hgmdSelect, String clinvarSelect) {
		RangePoint rangePoint = pointDao.findRangeBySymbol(symbol);
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("count", pointDao.countThree(child, rangePoint.getChr_name().substring(rangePoint.getChr_name().indexOf("chr")+3), Integer.parseInt(rangePoint.getTx_start()), Integer.parseInt(rangePoint.getTx_end()),hgmdSelect,clinvarSelect));
		map.put("list", pointDao.queryWithRange(maleId, femaleId, child, rangePoint.getChr_name().substring(rangePoint.getChr_name().indexOf("chr")+3), Integer.parseInt(rangePoint.getTx_start()), Integer.parseInt(rangePoint.getTx_end()), perpage, offset,hgmdSelect,clinvarSelect));
		return map;
	}

	public HashMap<String, Object> queryWithNm(String maleId, String femaleId, String child, String nm, int perpage,
			int offset,String hgmdSelect, String clinvarSelect) {
		RangePoint rangePoint = pointDao.findRangeByNm(nm);
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("count", pointDao.countThree(child, rangePoint.getChr_name().substring(rangePoint.getChr_name().indexOf("chr")+3), Integer.parseInt(rangePoint.getTx_start()), Integer.parseInt(rangePoint.getTx_end()),hgmdSelect,clinvarSelect));
		map.put("list", pointDao.queryWithRange(maleId, femaleId, child, rangePoint.getChr_name().substring(rangePoint.getChr_name().indexOf("chr")+3), Integer.parseInt(rangePoint.getTx_start()), Integer.parseInt(rangePoint.getTx_end()), perpage, offset,hgmdSelect,clinvarSelect));
		return map;
	}
	
	//query child both diff parent
	public HashMap<String, Object> queryChildDiffParent(String maleId, String femaleId, String child, int perpage, int offset, String hgmdSelect, String clinvarSelect) {
		
		HashMap<String, Object> map = new HashMap<String, Object>();
		
		map.put("list", pointDao.queryChildDiffParent(maleId, femaleId, child, perpage, offset, hgmdSelect, clinvarSelect));
		map.put("count", pointDao.countChildDiff(maleId, femaleId, child, perpage, offset, hgmdSelect, clinvarSelect));
		return map;
	}

	public List<String> listSampleId() {
		return pointDao.countSampleId();
	}

	public List<TrioDiffGroup> listTrioDiffGroup() {
		return pointDao.listTrioGroup();
	}
	
	public String handleTrioDiffAnalyze(String child,String father,String mother) {
		return pointDao.handleTrioAnalyze(child, father, mother);
	}

}
