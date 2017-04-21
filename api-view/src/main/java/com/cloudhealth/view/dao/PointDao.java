package com.cloudhealth.view.dao;

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

public interface PointDao {

	//query based on sample ID,chr,postion
	public List<AFPoint> listAF(String sampleId, String chr, int start,int end);
	
	public GnoGenomePoint queryGnoGen(String chr,int pos, String ref, String alt);
	
	public GnoExoPoint queryGnoExo(String chr,int pos, String ref, String alt);
	
	public OnekgPoint queryOnekg(String chr, int pos, String ref, String obs);
	
	public EspPoint queryEsp(String chr, int start, int end, String ref, String alt);
	
	public ExacPoint queryExac(String chr, int start, int end, String ref, String alt);
	
	public AnnovarPoint queryAnnovar(String chr, int start, int end, String ref, String alt);
	
	public ClinvarPoint queryClinvar(String chr, int start, int end, String ref, String alt);
	
	//find chr start end from refseqs through entrez_id in homo_geninfor through symbol
	public RangePoint findRangeBySymbol(String symbol);

	public RangePoint findRangeByNm(String nm);
}