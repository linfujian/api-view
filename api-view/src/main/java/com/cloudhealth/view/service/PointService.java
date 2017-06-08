package com.cloudhealth.view.service;

import java.math.BigInteger;
import java.util.HashMap;
import java.util.List;

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
import com.cloudhealth.view.entity.VarAnnoPoint;
import com.cloudhealth.view.entity.VarAnnoPoint_history;
import com.cloudhealth.view.model.VarAnnoReportPoint;

public interface PointService {
	
	public List<AFPoint> listAFPoints(String sampleId, String chr, int start, int end,Integer offset,Integer maxResults,String varAnnoGroupType, String hgmdType);
	
	public BigInteger count(String sampleId, String chr, int start,int end,String vcfAnnoGroupType, String hgmdType);
	
	public GnoGenomePoint queryGnoGenPoint(String chr,int pos,String ref,String alt);
	
	public GnoExoPoint queryGnoExoPoint(String chr,int pos,String ref,String alt);
	
	public OnekgPoint queryOnekgPoint(String chr,int pos, String ref, String obs);
	
	public EspPoint queryEspPoint(String chr, int start, int end, String ref, String alt);
	
	public ExacPoint queryExacPoint(String chr, int start, int end, String ref, String alt);
	
	public AnnovarPoint queryAnnovarPoint(String chr, int start, int end, String ref, String alt);
	
	public ClinvarPoint queryClinvarPoint(String chr, int start, int end, String ref, String alt);
	
	public List<AFPoint> listAFPoints(String sampleId,String symbol,Integer offset, Integer maxResults,String varAnnoGroupType, String hgmdType);
	
	public BigInteger count(String sampleId, String symbol, String vcfAnnoGroupType, String hgmdType); 
	
	public List<AFPoint> listAFPointsByNm(String sampleId,String nm,Integer offset, Integer maxResults,String varAnnoGroupType, String hgmdType);

	public BigInteger countByNm(String sampleId, String nm,String vcfAnnoGroupType, String hgmdType);
	
	public String batchUpdate(List<VarAnnoReportPoint> varAnnoPoints,String sampleId);
	

	//varAnnoDetail
	public VarAnnoPoint queryVarAnnoPoint(String chr, int pos, String ref, String alt);
	
	public List<VarAnnoPoint_history> queryVarAnnoHistory(String chr, int pos, String ref, String alt);
	
	//hgmdDetail
	public HgmdPoint queryHgmdPoint(String chr, int pos, String ref, String alt);
	
	//query all hgmdVarAnnoPoints
	public List<HgmdVarAnnoPoint> queryAll(String sampleID);
	
	//query threesample with range
	public HashMap<String, Object> queryWithRange(String maleId,String femaleId, String child, String chr, int start, int end, int perpage, int offset, String hgmdSelect, String clinvarSelect);
	
	//query threeSample with symbol
	public HashMap<String, Object> queryWithSymbol(String maleId,String femaleId, String child, String symbol, int perpage, int offset, String hgmdSelect, String clinvarSelect);
	
	//query for threesample with nm
	public HashMap<String, Object> queryWithNm(String maleId,String femaleId, String child, String nm, int perpage, int offset, String hgmdSelect, String clinvarSelect);
	
}
