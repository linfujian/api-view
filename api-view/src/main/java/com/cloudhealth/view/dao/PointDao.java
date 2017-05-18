package com.cloudhealth.view.dao;

import java.math.BigInteger;
import java.util.List;

import com.cloudhealth.view.entity.AFPoint;
import com.cloudhealth.view.entity.AnnovarPoint;
import com.cloudhealth.view.entity.ClinvarPoint;
import com.cloudhealth.view.entity.EspPoint;
import com.cloudhealth.view.entity.ExacPoint;
import com.cloudhealth.view.entity.GnoExoPoint;
import com.cloudhealth.view.entity.GnoGenomePoint;
import com.cloudhealth.view.entity.HgmdPoint;
import com.cloudhealth.view.entity.OnekgPoint;
import com.cloudhealth.view.entity.RangePoint;
import com.cloudhealth.view.entity.VarAnnoPoint;
import com.cloudhealth.view.entity.VarAnnoPoint_history;
import com.cloudhealth.view.model.VarAnnoReportPoint;

public interface PointDao {

	//query based on sample ID,chr,postion
	public List<AFPoint> listAF(String sampleId, String chr, int start,int end,Integer offset, Integer maxResults, String varAnnoGroupType, String hgmdType);
	public BigInteger count(String sampleId, String chr, int start,int end,String varAnnoGroupType, String hgmdType);
	
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
	
	//hgmd point query
	public HgmdPoint queryHgmd(String chr,int pos, String ref, String alt);
	
	//find chr start end from refseqs through entrez_id in homo_geninfor through symbol
	public RangePoint findRangeBySymbol(String symbol);

	public RangePoint findRangeByNm(String nm);
	
	public String batchUpdate(List<VarAnnoReportPoint> varAnnoPoints, String sampleId);
	
}
