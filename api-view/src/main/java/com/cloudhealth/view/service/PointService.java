package com.cloudhealth.view.service;

import java.math.BigInteger;
import java.util.List;
import com.cloudhealth.view.model.AFPoint;
import com.cloudhealth.view.model.AnnovarPoint;
import com.cloudhealth.view.model.ClinvarPoint;
import com.cloudhealth.view.model.EspPoint;
import com.cloudhealth.view.model.ExacPoint;
import com.cloudhealth.view.model.GnoExoPoint;
import com.cloudhealth.view.model.GnoGenomePoint;
import com.cloudhealth.view.model.OnekgPoint;
import com.cloudhealth.view.model.VarAnnoPoint;
import com.cloudhealth.view.model.VarAnnoPoint_history;
import com.cloudhealth.view.model.VarAnnoReportPoint;

public interface PointService {
	
	public List<AFPoint> listAFPoints(String sampleId, String chr, int start, int end,Integer offset,Integer maxResults,String varAnnoGroupType);
	
	public BigInteger count(String sampleId, String chr, int start,int end,String vcfAnnoGroupType);
	
	public GnoGenomePoint queryGnoGenPoint(String chr,int pos,String ref,String alt);
	
	public GnoExoPoint queryGnoExoPoint(String chr,int pos,String ref,String alt);
	
	public OnekgPoint queryOnekgPoint(String chr,int pos, String ref, String obs);
	
	public EspPoint queryEspPoint(String chr, int start, int end, String ref, String alt);
	
	public ExacPoint queryExacPoint(String chr, int start, int end, String ref, String alt);
	
	public AnnovarPoint queryAnnovarPoint(String chr, int start, int end, String ref, String alt);
	
	public ClinvarPoint queryClinvarPoint(String chr, int start, int end, String ref, String alt);
	
	public List<AFPoint> listAFPoints(String sampleId,String symbol,Integer offset, Integer maxResults,String varAnnoGroupType);
	
	public BigInteger count(String sampleId, String symbol, String vcfAnnoGroupType); 
	
	public List<AFPoint> listAFPointsByNm(String sampleId,String nm,Integer offset, Integer maxResults,String varAnnoGroupType);

	public BigInteger countByNm(String sampleId, String nm,String vcfAnnoGroupType);
	
	public String batchUpdate(List<VarAnnoReportPoint> varAnnoPoints,String sampleId);
	

	//varAnnoDetail
	public VarAnnoPoint queryVarAnnoPoint(String chr, int pos, String ref, String alt);
	
	public List<VarAnnoPoint_history> queryVarAnnoHistory(String chr, int pos, String ref, String alt);
}
