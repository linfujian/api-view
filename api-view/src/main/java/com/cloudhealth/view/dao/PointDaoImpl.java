package com.cloudhealth.view.dao;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import com.cloudhealth.view.model.AFPoint;
import com.cloudhealth.view.model.AnnovarPoint;
import com.cloudhealth.view.model.ClinvarPoint;
import com.cloudhealth.view.model.EspPoint;
import com.cloudhealth.view.model.ExacPoint;
import com.cloudhealth.view.model.GnoExoPoint;
import com.cloudhealth.view.model.GnoGenomePoint;
import com.cloudhealth.view.model.OnekgPoint;
import com.cloudhealth.view.model.RangePoint;
import com.cloudhealth.view.model.SampleInfo;

@Repository
@Transactional
public class PointDaoImpl implements PointDao {

	@Autowired
	private SessionFactory sessionFactory;
	
	//return pointInfors satisified with conditions
	public List<AFPoint> listAF(String sampleId, String chr, int start, int end) {
		
		SQLQuery query = getSession().createSQLQuery("SELECT sample.CHROM,sample.POS,sample.REF,sample.ALT,gno.AF_GNOMAD AS AF_gno_genome,exo.AF_GNOMAD AS AF_gno_exome, 1kg.AF_EAS AS AF_EAS_1kg, esp.AF_ALL AS AF_ALL_esp, exac.EXAC_ALL AS AF_ALL_exac, annovar.SIFT_score AS SIFT_score, annovar.Polyphen2_HDIV_score as Polyphen2_HDIV_score, clinvar.CLNSIG AS CLNSIG_clinvar FROM sampleinfor sample LEFT JOIN gno" + chr + " gno ON (sample.CHROM = gno.CHROM AND sample.POS = gno.POS AND sample.REF = gno.REF AND sample.ALT = gno.ALT) LEFT JOIN exo" + chr + " exo ON (sample.CHROM = exo.CHROM AND sample.POS = exo.POS AND sample.REF = exo.REF AND sample.ALT = exo.ALT) LEFT JOIN hg38_1kg 1kg ON (sample.CHROM = 1kg.CHROM AND sample.POS = 1kg.POS AND sample.REF = 1kg.REF AND sample.ALT = 1kg.OBS) LEFT JOIN hg38_esp esp ON (sample.CHROM = esp.CHROM AND sample.POS = esp.START AND sample.POS = esp.END AND sample.REF = esp.REF AND sample.ALT = esp.ALT) LEFT JOIN hg38_exac exac ON (sample.CHROM = exac.CHROM AND sample.POS = exac.START AND sample.POS = exac.END AND sample.REF = exac.REF AND sample.ALT = exac.ALT) LEFT JOIN hg38_annovar annovar ON (sample.CHROM = annovar.CHROM AND sample.POS = annovar.START AND sample.POS = annovar.END AND sample.REF = annovar.REF AND sample.ALT = annovar.ALT) LEFT JOIN clinvar ON (sample.CHROM = clinvar.CHROM AND sample.POS = clinvar.START_POINT AND sample.POS = clinvar.END_POINT AND sample.REF = clinvar.REF AND sample.ALT = clinvar.ALT) WHERE sample.SAMPLE = :sample AND sample.CHROM = :chr AND sample.POS BETWEEN :start AND :end").addEntity(AFPoint.class);
		List<AFPoint> afPointInfors = query.setString("sample", sampleId).setString("chr", chr).setInteger("start", start).setInteger("end", end).list();
		
		for(Iterator<AFPoint> iterator = afPointInfors.iterator(); iterator.hasNext();) {
			AFPoint afPoint = iterator.next();
			if((afPoint.getREF().length() != 1) && (afPoint.getALT().length() != 1))
				iterator.remove();
		}
		
		return afPointInfors;
	}
	
	//base on symbol to find entrez_id in homo_geninfor  then base on entrez_id to find chr/start/end in sample
	public RangePoint findRangeBySymbol(String symbol) {
		SQLQuery query = getSession().createSQLQuery("select ref.acc, ref.chr_name, ref.tx_start, ref.tx_end from refseqs ref where ref.entrez_id = ( select * from ( select homo1.entrez_id from homo_geninfor homo1 where homo1.symbol= ? union select  homo2.entrez_id from homo_geninfor homo2 where homo2.synonyms like ? or synonyms like ? or synonyms like ? or synonyms like ?) as s limit 1) order by exon_count desc limit 1").addEntity(RangePoint.class);
		List<RangePoint> pointList = query.setString(0, symbol).setString(1, "{" + symbol + ",%").setString(2, "%," + symbol + "}").setString(3, "%," + symbol + ",%").setString(4, "{" + symbol + "}").list();
		RangePoint point = pointList.isEmpty()? new RangePoint():pointList.get(0);
		return point;
	}
	
	//base on nm to find chr/start/end in sample
	public RangePoint findRangeByNm(String nm) {
		
		if(nm.contains("."))
			nm = nm.substring(0, nm.indexOf("."));
		
		SQLQuery query = getSession().createSQLQuery("select acc, chr_name, tx_start, tx_end from refseqs where acc= ?").addEntity(RangePoint.class);
		List<RangePoint> pointList = query.setString(0, nm).list();
		RangePoint point = pointList.isEmpty()? new RangePoint() : pointList.get(0);
		return point;
	}
	
	//query singlePoint for gno_genome
	public GnoGenomePoint queryGnoGen(String chr, int pos, String ref, String alt) {
		SQLQuery query = getSession().createSQLQuery("SELECT * FROM gno" + chr + " gno WHERE gno.CHROM=:chr AND gno.POS=:pos AND gno.REF=:ref AND gno.ALT=:alt").addEntity(GnoGenomePoint.class);
		GnoGenomePoint point = (GnoGenomePoint) query.setString("chr", chr).setInteger("pos", pos).setString("ref", ref).setString("alt", alt).list().get(0);
		return point;
	}
	
	//query singlePoint for gno_exome
	public GnoExoPoint queryGnoExo(String chr, int pos, String ref, String alt) {
		SQLQuery query = getSession().createSQLQuery("SELECT * FROM exo" + chr + " exo WHERE exo.CHROM=:chr AND exo.POS=:pos AND exo.REF=:ref AND exo.ALT=:alt").addEntity(GnoExoPoint.class);
		GnoExoPoint point =  (GnoExoPoint) query.setString("chr", chr).setInteger("pos", pos).setString("ref", ref).setString("alt", alt).list().get(0);
		return point;
	}
	
	//query singlePoint for 1kg
	public OnekgPoint queryOnekg(String chr, int pos, String ref, String obs) {
		SQLQuery query = getSession().createSQLQuery("SELECT * FROM hg38_1kg  1kg WHERE 1kg.CHROM=:chr AND 1kg.POS=:pos AND 1kg.REF=:ref AND 1kg.OBS=:obs").addEntity(OnekgPoint.class);
		OnekgPoint point = (OnekgPoint) query.setString("chr", chr).setInteger("pos", pos).setString("ref", ref).setString("obs", obs).list().get(0);
		return point;
	}
	
	//query singlePoint for esp
	public EspPoint queryEsp(String chr, int start, int end, String ref, String alt) {
		SQLQuery query = getSession().createSQLQuery("SELECT * FROM hg38_esp esp WHERE esp.CHROM=:chr AND esp.START=:start AND esp.END=:end AND esp.REF=:ref AND esp.ALT=:alt").addEntity(EspPoint.class);
		EspPoint point = (EspPoint) query.setString("chr", chr).setInteger("start", start).setInteger("end", end).setString("ref", ref).setString("alt", alt).list().get(0);
		return point;
	}
	
	//query singlePoint for exac
	public ExacPoint queryExac(String chr, int start, int end, String ref, String alt) {
		SQLQuery query = getSession().createSQLQuery("SELECT * FROM hg38_exac exac WHERE exac.CHROM=:chr AND exac.START=:start AND exac.END=:end AND exac.REF=:ref AND exac.ALT=:alt").addEntity(ExacPoint.class);
		ExacPoint point = (ExacPoint) query.setString("chr", chr).setInteger("start", start).setInteger("end", end).setString("ref", ref).setString("alt", alt).list().get(0);
		return point;
	}
	
	//query singlePoint for annovar
	public AnnovarPoint queryAnnovar(String chr, int start, int end, String ref, String alt) {
		SQLQuery query = getSession().createSQLQuery("SELECT * FROM hg38_annovar annovar WHERE annovar.CHROM=:chr AND annovar.START=:start AND annovar.END=:end AND annovar.REF=:ref AND annovar.ALT=:alt").addEntity(AnnovarPoint.class);
		AnnovarPoint point = (AnnovarPoint) query.setString("chr", chr).setInteger("start", start).setInteger("end", end).setString("ref", ref).setString("alt", alt).list().get(0);
		return point;
	}
	
	//query singlePoint for clinvar
	public ClinvarPoint queryClinvar(String chr, int start, int end, String ref, String alt) {
		SQLQuery query = getSession().createSQLQuery("SELECT * FROM clinvar WHERE clinvar.CHROM=:chr AND clinvar.START_POINT=:start AND clinvar.END_POINT=:end AND clinvar.REF=:ref AND clinvar.ALT=:alt").addEntity(ClinvarPoint.class);
		ClinvarPoint point = (ClinvarPoint) query.setString("chr", chr).setInteger("start", start).setInteger("end", end).setString("ref", ref).setString("alt", alt).list().get(0);
		return point;
	}
	
	//handle for sampleInfo to change pos to start/end
	//TODO
	/*public List<SampleInfoStandard> handleForStandard(String sample, String chr, int start, int end) {
		SQLQuery query = getSession().createSQLQuery("select CHROM, POS, REF, ALT from sampleinfor where SAMPLE = :sample and CHROM = :chr and POS between :start and :end").addEntity(SampleInfo.class);
		List<SampleInfo> sampleInfos = query.setString("sample", sample).setString("chr", chr).setInteger("start", start).setInteger("end", end).list();
		
		List<SampleInfoStandard> standards = new ArrayList<SampleInfoStandard>();
		
		for(SampleInfo sampleInfo : sampleInfos) {
			
			//for snv
			if((sampleInfo.getREF().length() == 1) && (sampleInfo.getALT().length()==1)) {
				SampleInfoStandard standard = new SampleInfoStandard();
				standard.setCHROM(sampleInfo.getCHROM());
				standard.setStart(sampleInfo.getPOS());
				standard.setEnd(sampleInfo.getPOS());
				standard.setREF(sampleInfo.getREF());
				standard.setALT(sampleInfo.getALT());
				
				standards.add(standard);
				
			}
			//other situations
		}
		
		return standards;
		
	}*/
	
	
	
	private Session getSession() {
		Session session = getSessionFactory().getCurrentSession();
		if(session == null)
			session = getSessionFactory().openSession();
		return session;
	}
	
	private SessionFactory getSessionFactory() {
		return sessionFactory;
	}

}
