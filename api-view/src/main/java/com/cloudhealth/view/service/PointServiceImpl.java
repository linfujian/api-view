package com.cloudhealth.view.service;

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

@Service
public class PointServiceImpl implements PointService {

	@Autowired
	PointDao pointDao;
	
	@Transactional(readOnly = true)
	public List<AFPoint> listAFPoints(String sampleId, String chr, int start, int end) {
		return pointDao.listAF(sampleId, chr, start, end);
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

	public List<AFPoint> listAFPoints(String sampleId, String symbol) {
		RangePoint range =  pointDao.findRangeBySymbol(symbol);
		
		if(range.getAcc() != null)
			return this.listAFPoints(sampleId, range.getChr_name(), Integer.valueOf(range.getTx_start()), Integer.valueOf(range.getTx_end()));
		return new ArrayList<AFPoint>();
	}

	public List<AFPoint> listAFPointsByNm(String sampleId, String nm) {
		RangePoint range = pointDao.findRangeByNm(nm);
		
		if(range.getAcc() != null)
			return this.listAFPoints(sampleId, range.getChr_name(), Integer.valueOf(range.getTx_start()), Integer.valueOf(range.getTx_end()));
		return new ArrayList<AFPoint>();
	}

}