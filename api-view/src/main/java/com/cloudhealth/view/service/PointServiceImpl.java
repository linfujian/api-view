package com.cloudhealth.view.service;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.cloudhealth.view.dao.PointDao;
import com.cloudhealth.view.model.AFPoint;
import com.cloudhealth.view.model.AnnovarPoint;
import com.cloudhealth.view.model.ClinvarPoint;
import com.cloudhealth.view.model.EspPoint;
import com.cloudhealth.view.model.ExacPoint;
import com.cloudhealth.view.model.GnoExoPoint;
import com.cloudhealth.view.model.GnoGenomePoint;
import com.cloudhealth.view.model.OnekgPoint;
import com.cloudhealth.view.model.RangePoint;
import com.cloudhealth.view.model.VarAnnoPoint;
import com.cloudhealth.view.model.VarAnnoPoint_history;
import com.cloudhealth.view.model.VarAnnoReportPoint;

@Service
public class PointServiceImpl implements PointService {

	@Autowired
	PointDao pointDao;
	
	@Transactional(readOnly = true)
	public List<AFPoint> listAFPoints(String sampleId, String chr, int start, int end,Integer offset,Integer maxResults,String varAnnoGroupType) {
		return pointDao.listAF(sampleId, chr, start, end,offset,maxResults,varAnnoGroupType);
	}
	
	public BigInteger count(String sampleId, String chr, int start, int end,String vcfAnnoGroupType) {
		return pointDao.count(sampleId, chr, start, end,vcfAnnoGroupType);
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

	public List<AFPoint> listAFPoints(String sampleId, String symbol,Integer offset, Integer maxResults,String varAnnoGroupType) {
		RangePoint range =  pointDao.findRangeBySymbol(symbol);
		
		if(range.getAcc() != null)
			return this.listAFPoints(sampleId, range.getChr_name(), Integer.valueOf(range.getTx_start()), Integer.valueOf(range.getTx_end()),offset,maxResults,varAnnoGroupType);
		return new ArrayList<AFPoint>();
	}
	
	public BigInteger count(String sampleId, String symbol,String vcfAnnoGroupType) {
		RangePoint range =  pointDao.findRangeBySymbol(symbol);
		if(range.getAcc() != null)
			return this.count(sampleId, range.getChr_name(),Integer.valueOf(range.getTx_start()), Integer.valueOf(range.getTx_end()),vcfAnnoGroupType);
		return null;
	}

	public List<AFPoint> listAFPointsByNm(String sampleId, String nm,Integer offset, Integer maxResults,String varAnnoGroupType) {
		RangePoint range = pointDao.findRangeByNm(nm);
		
		if(range.getAcc() != null)
			return this.listAFPoints(sampleId, range.getChr_name(), Integer.valueOf(range.getTx_start()), Integer.valueOf(range.getTx_end()),offset, maxResults, varAnnoGroupType);
		return new ArrayList<AFPoint>();
	}

	public BigInteger countByNm(String sampleId, String nm,String vcfAnnoGroupType) {
		RangePoint range = pointDao.findRangeByNm(nm);
		if(range.getAcc() != null)
			return this.count(sampleId,  range.getChr_name(), Integer.valueOf(range.getTx_start()), Integer.valueOf(range.getTx_end()),vcfAnnoGroupType);
		return null;
	}

	public String batchUpdate(List<VarAnnoReportPoint> varAnnoPoints, String sampleId) {
		String message = pointDao.batchUpdate(varAnnoPoints,sampleId);
		if(message.equals("successUpdate"))
			return "successUpdate";
		return null;
	}
	
	public VarAnnoPoint queryVarAnnoPoint(String chr, int pos, String ref, String alt) {
		return pointDao.queryVarAnno(chr, pos, ref, alt);
	}

	public List<VarAnnoPoint_history> queryVarAnnoHistory(String chr, int pos, String ref, String alt) {
		return pointDao.queryHistory(chr, pos, ref, alt);
	}
	
}
