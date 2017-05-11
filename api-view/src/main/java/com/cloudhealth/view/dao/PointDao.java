package com.cloudhealth.view.dao;

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
import com.cloudhealth.view.model.RangePoint;
import com.cloudhealth.view.model.VarAnnoPoint;
import com.cloudhealth.view.model.VarAnnoPoint_history;
import com.cloudhealth.view.model.VarAnnoReportPoint;

public interface PointDao {

	//query based on sample ID,chr,postion
	public List<AFPoint> listAF(String sampleId, String chr, int start,int end,Integer offset, Integer maxResults, String varAnnoGroupType);
	public BigInteger count(String sampleId, String chr, int start,int end,String varAnnoGroupType);
	
	public GnoGenomePoint queryGnoGen(String chr,int pos, String ref, String alt);
	
	public GnoExoPoint queryGnoExo(String chr,int pos, String ref, String alt);
	
	public OnekgPoint queryOnekg(String chr, int pos, String ref, String obs);
	
	public EspPoint queryEsp(String chr, int start, int end, String ref, String alt);
	
	public ExacPoint queryExac(String chr, int start, int end, String ref, String alt);
	
	public AnnovarPoint queryAnnovar(String chr, int start, int end, String ref, String alt);
	
	public ClinvarPoint queryClinvar(String chr, int start, int end, String ref, String alt);
	
	//2015.05.08 add
	public VarAnnoPoint queryVarAnno(String chr,int pos, String ref, String alt);
	
	public List<VarAnnoPoint_history> queryHistory(String chr,int pos, String ref, String alt);
	
	//find chr start end from refseqs through entrez_id in homo_geninfor through symbol
	public RangePoint findRangeBySymbol(String symbol);

	public RangePoint findRangeByNm(String nm);
	
	public String batchUpdate(List<VarAnnoReportPoint> varAnnoPoints, String sampleId);
	
}
