package com.cloudhealth.view.service;

import java.util.List;
import com.cloudhealth.view.model.AFPoint;
import com.cloudhealth.view.model.AnnovarPoint;
import com.cloudhealth.view.model.ClinvarPoint;
import com.cloudhealth.view.model.EspPoint;
import com.cloudhealth.view.model.ExacPoint;
import com.cloudhealth.view.model.GnoExoPoint;
import com.cloudhealth.view.model.GnoGenomePoint;
import com.cloudhealth.view.model.OnekgPoint;

public interface PointService {
	
	public List<AFPoint> listAFPoints(String sampleId, String chr, int start, int end);
	
	public GnoGenomePoint queryGnoGenPoint(String chr,int pos,String ref,String alt);
	
	public GnoExoPoint queryGnoExoPoint(String chr,int pos,String ref,String alt);
	
	public OnekgPoint queryOnekgPoint(String chr,int pos, String ref, String obs);
	
	public EspPoint queryEspPoint(String chr, int start, int end, String ref, String alt);
	
	public ExacPoint queryExacPoint(String chr, int start, int end, String ref, String alt);
	
	public AnnovarPoint queryAnnovarPoint(String chr, int start, int end, String ref, String alt);
	
	public ClinvarPoint queryClinvarPoint(String chr, int start, int end, String ref, String alt);
	
	public List<AFPoint> listAFPoints(String sampleId,String symbol);
	
	public List<AFPoint> listAFPointsByNm(String sampleId,String nm);
}
