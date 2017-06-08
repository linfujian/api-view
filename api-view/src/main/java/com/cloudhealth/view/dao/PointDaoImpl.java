package com.cloudhealth.view.dao;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import com.cloudhealth.view.entity.AFPoint;
import com.cloudhealth.view.entity.AnnovarPoint;
import com.cloudhealth.view.entity.CPdotPoint;
import com.cloudhealth.view.entity.ClinvarPoint;
import com.cloudhealth.view.entity.EspPoint;
import com.cloudhealth.view.entity.ExacPoint;
import com.cloudhealth.view.entity.GnoExoPoint;
import com.cloudhealth.view.entity.GnoGenomePoint;
import com.cloudhealth.view.entity.HgmdPoint;
import com.cloudhealth.view.entity.HgmdVarAnnoPoint;
import com.cloudhealth.view.entity.OnekgPoint;
import com.cloudhealth.view.entity.RangePoint;
import com.cloudhealth.view.entity.ThreeSamplePoint;
import com.cloudhealth.view.entity.VarAnnoPoint;
import com.cloudhealth.view.entity.VarAnnoPoint_history;
import com.cloudhealth.view.model.VarAnnoReportPoint;

@Repository
@Transactional
public class PointDaoImpl implements PointDao {

	@Autowired
	private SessionFactory sessionFactory;
	
	//count
	public BigInteger count(String sampleId, String chr, int start, int end,String varAnnoGroupType,String hgmdType) {
		if(chr.contains("chr"))
			chr = chr.substring(3);
		SQLQuery query;
		if("ALL".equals(varAnnoGroupType) && "ALL".equals(hgmdType)) {
			query = getSession().createSQLQuery("SELECT count(*) FROM sampleinfor sample WHERE sample.SAMPLE = :sample AND sample.CHROM = :chr AND sample.POS BETWEEN :start AND :end AND LENGTH(sample.REF)=1 AND LENGTH(sample.ALT)=1");
			return (BigInteger)query.setString("sample", sampleId).setString("chr", "chr" + chr).setInteger("start", start).setInteger("end", end).uniqueResult();
		} else if("ALL".equals(hgmdType) && !"ALL".equals(varAnnoGroupType)) {
			query = getSession().createSQLQuery("SELECT count(*) FROM sampleinfor sample INNER JOIN vcfanno ON (sample.CHROM=vcfanno.CHROM AND sample.POS=vcfanno.START AND sample.REF=vcfanno.REF AND sample.ALT=vcfanno.ALT AND vcfanno.Category = :category) WHERE sample.SAMPLE = :sample AND sample.CHROM = :chr AND sample.POS BETWEEN :start AND :end AND LENGTH(sample.REF)=1 AND LENGTH(sample.ALT)=1");
			return (BigInteger)query.setString("sample", sampleId).setString("chr", "chr" + chr).setInteger("start", start).setInteger("end", end).setString("category", varAnnoGroupType).uniqueResult();
		} else if (! "ALL".equals(hgmdType) && "ALL".equals(varAnnoGroupType)) {
			query = getSession().createSQLQuery("SELECT count(*) FROM sampleinfor sample INNER JOIN hg38_hgmd hgmd ON (sample.CHROM=hgmd.CHROM AND sample.POS=hgmd.POS AND sample.REF=hgmd.REF AND sample.ALT=hgmd.ALT AND hgmd.CLASS = :class) WHERE sample.SAMPLE = :sample AND sample.CHROM = :chr AND sample.POS BETWEEN :start AND :end AND LENGTH(sample.REF)=1 AND LENGTH(sample.ALT)=1");
			return (BigInteger)query.setString("sample", sampleId).setString("chr", "chr" + chr).setInteger("start", start).setInteger("end", end).setString("class", hgmdType).uniqueResult();
		} else {
			query = getSession().createSQLQuery("SELECT count(*) FROM sampleinfor sample INNER JOIN vcfanno ON (sample.CHROM=vcfanno.CHROM AND sample.POS=vcfanno.START AND sample.REF=vcfanno.REF AND sample.ALT=vcfanno.ALT AND vcfanno.Category = :category) INNER JOIN hg38_hgmd hgmd ON (sample.CHROM=hgmd.CHROM AND sample.POS=hgmd.POS AND sample.REF=hgmd.REF AND sample.ALT=hgmd.ALT AND hgmd.CLASS = :class) WHERE sample.SAMPLE = :sample AND sample.CHROM = :chr AND sample.POS BETWEEN :start AND :end AND LENGTH(sample.REF)=1 AND LENGTH(sample.ALT)=1");
			return (BigInteger)query.setString("sample", sampleId).setString("chr", "chr" + chr).setInteger("start", start).setInteger("end", end).setString("category", varAnnoGroupType).setString("class", hgmdType).uniqueResult();
		}
	}
	//return pointInfors satisified with conditions
	public List<AFPoint> listAF(String sampleId, String chr, int start, int end,Integer offset, Integer maxResults,String varAnnoGroupType, String hgmdType) {
		if(chr.contains("chr"))
			chr = chr.substring(3);
		//SQLQuery query = getSession().createSQLQuery("SELECT sample.CHROM,sample.POS,sample.REF,sample.ALT,com.symbol AS Symbol,pr.RS AS RS, gno.AF_GNOMAD AS AF_gno_genome,exo.AF_GNOMAD AS AF_gno_exome, 1kg.AF_EAS AS AF_EAS_1kg, esp.AF_ALL AS AF_ALL_esp, exac.EXAC_ALL AS AF_ALL_exac, annovar.SIFT_score AS SIFT_score, annovar.Polyphen2_HDIV_score as Polyphen2_HDIV_score, clinvar.CLNSIG AS CLNSIG_clinvar FROM sampleinfor sample LEFT JOIN (SELECT ref.chr_name,ref.tx_start,ref.tx_end,homo.symbol FROM refseqs ref,homo_geninfor homo WHERE ref.entrez_id = homo.entrez_id) AS com ON (sample.CHROM=com.chr_name and sample.POS > com.tx_start and sample.POS<com.tx_end) LEFT JOIN point_rs pr ON(sample.CHROM = pr.CHROM and sample.POS = pr.START) LEFT JOIN gnochr" + chr + " gno ON (sample.CHROM = gno.CHROM AND sample.POS = gno.POS AND sample.REF = gno.REF AND sample.ALT = gno.ALT) LEFT JOIN exochr" + chr + " exo ON (sample.CHROM = exo.CHROM AND sample.POS = exo.POS AND sample.REF = exo.REF AND sample.ALT = exo.ALT) LEFT JOIN hg38_1kg 1kg ON (sample.CHROM = 1kg.CHROM AND sample.POS = 1kg.POS AND sample.REF = 1kg.REF AND sample.ALT = 1kg.OBS) LEFT JOIN hg38_esp esp ON (sample.CHROM = esp.CHROM AND sample.POS = esp.START AND sample.POS = esp.END AND sample.REF = esp.REF AND sample.ALT = esp.ALT) LEFT JOIN hg38_exac exac ON (sample.CHROM = exac.CHROM AND sample.POS = exac.START AND sample.POS = exac.END AND sample.REF = exac.REF AND sample.ALT = exac.ALT) LEFT JOIN hg38_annovar annovar ON (sample.CHROM = annovar.CHROM AND sample.POS = annovar.START AND sample.POS = annovar.END AND sample.REF = annovar.REF AND sample.ALT = annovar.ALT) LEFT JOIN clinvar ON (sample.CHROM = clinvar.CHROM AND sample.POS = clinvar.START_POINT AND sample.POS = clinvar.END_POINT AND sample.REF = clinvar.REF AND sample.ALT = clinvar.ALT) WHERE sample.SAMPLE = :sample AND sample.CHROM = :chr AND sample.POS BETWEEN :start AND :end AND LENGTH(sample.REF)=1 AND LENGTH(sample.ALT)=1 GROUP BY sample.POS").addEntity(AFPoint.class);
		String querySQL;
		SQLQuery query;
		List<AFPoint> afPointInfors;
		if("ALL".equals(varAnnoGroupType) && "ALL".equals(hgmdType)) {
			querySQL = "SELECT sample.CHROM,sample.POS,sample.REF,sample.ALT,com.symbol AS Symbol,\"rs\" AS RS, gno.AF_GNOMAD AS AF_gno_genome,exo.AF_GNOMAD AS AF_gno_exome, 1kg.AF_EAS AS AF_EAS_1kg, esp.AF_ALL AS AF_ALL_esp, exac.EXAC_ALL AS AF_ALL_exac, annovar.SIFT_score AS SIFT_score, annovar.Polyphen2_HDIV_score as Polyphen2_HDIV_score, clinvar.CLNSIG AS CLNSIG_clinvar, hgmd.CLASS AS CLASS, vcfanno.Category, vcfanno.Comments, sample.REPORT FROM sampleinfor sample LEFT JOIN (SELECT ref.chr_name,ref.tx_start,ref.tx_end,homo.symbol FROM refseqs ref,homo_geninfor homo WHERE ref.entrez_id = homo.entrez_id) AS com ON (sample.CHROM=com.chr_name and sample.POS > com.tx_start and sample.POS<com.tx_end)  LEFT JOIN gnochr" + chr + " gno ON (sample.CHROM = gno.CHROM AND sample.POS = gno.START AND sample.REF = gno.REF AND sample.ALT = gno.ALT) LEFT JOIN exochr" + chr + " exo ON (sample.CHROM = exo.CHROM AND sample.POS = exo.START AND sample.REF = exo.REF AND sample.ALT = exo.ALT) LEFT JOIN hg38_1kg 1kg ON (sample.CHROM = 1kg.CHROM AND sample.POS = 1kg.POS AND sample.REF = 1kg.REF AND sample.ALT = 1kg.OBS) LEFT JOIN hg38_esp esp ON (sample.CHROM = esp.CHROM AND sample.POS = esp.START AND sample.POS = esp.END AND sample.REF = esp.REF AND sample.ALT = esp.ALT) LEFT JOIN hg38_exac exac ON (sample.CHROM = exac.CHROM AND sample.POS = exac.START AND sample.POS = exac.END AND sample.REF = exac.REF AND sample.ALT = exac.ALT) LEFT JOIN hg38_annovar annovar ON (sample.CHROM = annovar.CHROM AND sample.POS = annovar.START AND sample.POS = annovar.END AND sample.REF = annovar.REF AND sample.ALT = annovar.ALT) LEFT JOIN clinvar ON (sample.CHROM = clinvar.CHROM AND sample.POS = clinvar.START_POINT AND sample.POS = clinvar.END_POINT AND sample.REF = clinvar.REF AND sample.ALT = clinvar.ALT) LEFT JOIN hg38_hgmd hgmd ON(sample.CHROM=hgmd.CHROM AND sample.POS = hgmd.POS AND sample.REF = hgmd.REF AND sample.ALT = hgmd.ALT) LEFT JOIN vcfanno ON(sample.CHROM=vcfanno.CHROM AND sample.POS=vcfanno.START AND sample.REF=vcfanno.REF AND sample.ALT=vcfanno.ALT) WHERE sample.SAMPLE = :sample AND sample.CHROM = :chr AND sample.POS BETWEEN :start AND :end AND LENGTH(sample.REF)=1 AND LENGTH(sample.ALT)=1 GROUP BY sample.POS";
			query = getSession().createSQLQuery(querySQL).addEntity(AFPoint.class);
			afPointInfors= query.setString("sample", sampleId).setString("chr", "chr" + chr).setInteger("start", start).setInteger("end", end).setFirstResult(offset!=null?offset:0).setMaxResults(maxResults!=null?maxResults:10).list();
		} else if("ALL".equals(hgmdType) && !"ALL".equals(varAnnoGroupType)) {
			querySQL = "SELECT sample.CHROM,sample.POS,sample.REF,sample.ALT,com.symbol AS Symbol,\"rs\" AS RS, gno.AF_GNOMAD AS AF_gno_genome,exo.AF_GNOMAD AS AF_gno_exome, 1kg.AF_EAS AS AF_EAS_1kg, esp.AF_ALL AS AF_ALL_esp, exac.EXAC_ALL AS AF_ALL_exac, annovar.SIFT_score AS SIFT_score, annovar.Polyphen2_HDIV_score as Polyphen2_HDIV_score, clinvar.CLNSIG AS CLNSIG_clinvar, hgmd.CLASS AS CLASS, vcfanno.Category, vcfanno.Comments, sample.REPORT FROM sampleinfor sample LEFT JOIN (SELECT ref.chr_name,ref.tx_start,ref.tx_end,homo.symbol FROM refseqs ref,homo_geninfor homo WHERE ref.entrez_id = homo.entrez_id) AS com ON (sample.CHROM=com.chr_name and sample.POS > com.tx_start and sample.POS<com.tx_end)  LEFT JOIN gnochr" + chr + " gno ON (sample.CHROM = gno.CHROM AND sample.POS = gno.START AND sample.REF = gno.REF AND sample.ALT = gno.ALT) LEFT JOIN exochr" + chr + " exo ON (sample.CHROM = exo.CHROM AND sample.POS = exo.START AND sample.REF = exo.REF AND sample.ALT = exo.ALT) LEFT JOIN hg38_1kg 1kg ON (sample.CHROM = 1kg.CHROM AND sample.POS = 1kg.POS AND sample.REF = 1kg.REF AND sample.ALT = 1kg.OBS) LEFT JOIN hg38_esp esp ON (sample.CHROM = esp.CHROM AND sample.POS = esp.START AND sample.POS = esp.END AND sample.REF = esp.REF AND sample.ALT = esp.ALT) LEFT JOIN hg38_exac exac ON (sample.CHROM = exac.CHROM AND sample.POS = exac.START AND sample.POS = exac.END AND sample.REF = exac.REF AND sample.ALT = exac.ALT) LEFT JOIN hg38_annovar annovar ON (sample.CHROM = annovar.CHROM AND sample.POS = annovar.START AND sample.POS = annovar.END AND sample.REF = annovar.REF AND sample.ALT = annovar.ALT) LEFT JOIN clinvar ON (sample.CHROM = clinvar.CHROM AND sample.POS = clinvar.START_POINT AND sample.POS = clinvar.END_POINT AND sample.REF = clinvar.REF AND sample.ALT = clinvar.ALT) LEFT JOIN hg38_hgmd hgmd ON(sample.CHROM=hgmd.CHROM AND sample.POS = hgmd.POS AND sample.REF = hgmd.REF AND sample.ALT = hgmd.ALT) LEFT JOIN vcfanno ON(sample.CHROM=vcfanno.CHROM AND sample.POS=vcfanno.START AND sample.REF=vcfanno.REF AND sample.ALT=vcfanno.ALT) WHERE vcfanno.Category = :category AND sample.SAMPLE = :sample AND sample.CHROM = :chr AND sample.POS BETWEEN :start AND :end AND LENGTH(sample.REF)=1 AND LENGTH(sample.ALT)=1 GROUP BY sample.POS"; 
			query = getSession().createSQLQuery(querySQL).addEntity(AFPoint.class);
			afPointInfors = query.setString("sample", sampleId).setString("chr", "chr" + chr).setInteger("start", start).setInteger("end", end).setFirstResult(offset!=null?offset:0).setMaxResults(maxResults!=null?maxResults:10).setString("category", varAnnoGroupType).list();
		} else if (! "ALL".equals(hgmdType) && "ALL".equals(varAnnoGroupType)) {
			querySQL = "SELECT sample.CHROM,sample.POS,sample.REF,sample.ALT,com.symbol AS Symbol,\"rs\" AS RS, gno.AF_GNOMAD AS AF_gno_genome,exo.AF_GNOMAD AS AF_gno_exome, 1kg.AF_EAS AS AF_EAS_1kg, esp.AF_ALL AS AF_ALL_esp, exac.EXAC_ALL AS AF_ALL_exac, annovar.SIFT_score AS SIFT_score, annovar.Polyphen2_HDIV_score as Polyphen2_HDIV_score, clinvar.CLNSIG AS CLNSIG_clinvar, hgmd.CLASS AS CLASS, vcfanno.Category, vcfanno.Comments, sample.REPORT FROM sampleinfor sample LEFT JOIN (SELECT ref.chr_name,ref.tx_start,ref.tx_end,homo.symbol FROM refseqs ref,homo_geninfor homo WHERE ref.entrez_id = homo.entrez_id) AS com ON (sample.CHROM=com.chr_name and sample.POS > com.tx_start and sample.POS<com.tx_end)  LEFT JOIN gnochr" + chr + " gno ON (sample.CHROM = gno.CHROM AND sample.POS = gno.START AND sample.REF = gno.REF AND sample.ALT = gno.ALT) LEFT JOIN exochr" + chr + " exo ON (sample.CHROM = exo.CHROM AND sample.POS = exo.START AND sample.REF = exo.REF AND sample.ALT = exo.ALT) LEFT JOIN hg38_1kg 1kg ON (sample.CHROM = 1kg.CHROM AND sample.POS = 1kg.POS AND sample.REF = 1kg.REF AND sample.ALT = 1kg.OBS) LEFT JOIN hg38_esp esp ON (sample.CHROM = esp.CHROM AND sample.POS = esp.START AND sample.POS = esp.END AND sample.REF = esp.REF AND sample.ALT = esp.ALT) LEFT JOIN hg38_exac exac ON (sample.CHROM = exac.CHROM AND sample.POS = exac.START AND sample.POS = exac.END AND sample.REF = exac.REF AND sample.ALT = exac.ALT) LEFT JOIN hg38_annovar annovar ON (sample.CHROM = annovar.CHROM AND sample.POS = annovar.START AND sample.POS = annovar.END AND sample.REF = annovar.REF AND sample.ALT = annovar.ALT) LEFT JOIN clinvar ON (sample.CHROM = clinvar.CHROM AND sample.POS = clinvar.START_POINT AND sample.POS = clinvar.END_POINT AND sample.REF = clinvar.REF AND sample.ALT = clinvar.ALT) LEFT JOIN hg38_hgmd hgmd ON(sample.CHROM=hgmd.CHROM AND sample.POS = hgmd.POS AND sample.REF = hgmd.REF AND sample.ALT = hgmd.ALT) LEFT JOIN vcfanno ON(sample.CHROM=vcfanno.CHROM AND sample.POS=vcfanno.START AND sample.REF=vcfanno.REF AND sample.ALT=vcfanno.ALT) WHERE hgmd.CLASS = :class AND sample.SAMPLE = :sample AND sample.CHROM = :chr AND sample.POS BETWEEN :start AND :end AND LENGTH(sample.REF)=1 AND LENGTH(sample.ALT)=1 GROUP BY sample.POS"; 
			query = getSession().createSQLQuery(querySQL).addEntity(AFPoint.class);
			afPointInfors = query.setString("sample", sampleId).setString("chr", "chr" + chr).setInteger("start", start).setInteger("end", end).setFirstResult(offset!=null?offset:0).setMaxResults(maxResults!=null?maxResults:10).setString("class", hgmdType).list();
		} else {
			querySQL = "SELECT sample.CHROM,sample.POS,sample.REF,sample.ALT,com.symbol AS Symbol,\"rs\" AS RS, gno.AF_GNOMAD AS AF_gno_genome,exo.AF_GNOMAD AS AF_gno_exome, 1kg.AF_EAS AS AF_EAS_1kg, esp.AF_ALL AS AF_ALL_esp, exac.EXAC_ALL AS AF_ALL_exac, annovar.SIFT_score AS SIFT_score, annovar.Polyphen2_HDIV_score as Polyphen2_HDIV_score, clinvar.CLNSIG AS CLNSIG_clinvar, hgmd.CLASS AS CLASS, vcfanno.Category, vcfanno.Comments, sample.REPORT FROM sampleinfor sample LEFT JOIN (SELECT ref.chr_name,ref.tx_start,ref.tx_end,homo.symbol FROM refseqs ref,homo_geninfor homo WHERE ref.entrez_id = homo.entrez_id) AS com ON (sample.CHROM=com.chr_name and sample.POS > com.tx_start and sample.POS<com.tx_end)  LEFT JOIN gnochr" + chr + " gno ON (sample.CHROM = gno.CHROM AND sample.POS = gno.START AND sample.REF = gno.REF AND sample.ALT = gno.ALT) LEFT JOIN exochr" + chr + " exo ON (sample.CHROM = exo.CHROM AND sample.POS = exo.START AND sample.REF = exo.REF AND sample.ALT = exo.ALT) LEFT JOIN hg38_1kg 1kg ON (sample.CHROM = 1kg.CHROM AND sample.POS = 1kg.POS AND sample.REF = 1kg.REF AND sample.ALT = 1kg.OBS) LEFT JOIN hg38_esp esp ON (sample.CHROM = esp.CHROM AND sample.POS = esp.START AND sample.POS = esp.END AND sample.REF = esp.REF AND sample.ALT = esp.ALT) LEFT JOIN hg38_exac exac ON (sample.CHROM = exac.CHROM AND sample.POS = exac.START AND sample.POS = exac.END AND sample.REF = exac.REF AND sample.ALT = exac.ALT) LEFT JOIN hg38_annovar annovar ON (sample.CHROM = annovar.CHROM AND sample.POS = annovar.START AND sample.POS = annovar.END AND sample.REF = annovar.REF AND sample.ALT = annovar.ALT) LEFT JOIN clinvar ON (sample.CHROM = clinvar.CHROM AND sample.POS = clinvar.START_POINT AND sample.POS = clinvar.END_POINT AND sample.REF = clinvar.REF AND sample.ALT = clinvar.ALT) LEFT JOIN hg38_hgmd hgmd ON(sample.CHROM=hgmd.CHROM AND sample.POS = hgmd.POS AND sample.REF = hgmd.REF AND sample.ALT = hgmd.ALT) LEFT JOIN vcfanno ON(sample.CHROM=vcfanno.CHROM AND sample.POS=vcfanno.START AND sample.REF=vcfanno.REF AND sample.ALT=vcfanno.ALT) WHERE vcfanno.Category = :category AND hgmd.CLASS = :class AND sample.SAMPLE = :sample AND sample.CHROM = :chr AND sample.POS BETWEEN :start AND :end AND LENGTH(sample.REF)=1 AND LENGTH(sample.ALT)=1 GROUP BY sample.POS"; 
			query = getSession().createSQLQuery(querySQL).addEntity(AFPoint.class);
			afPointInfors = query.setString("sample", sampleId).setString("chr", "chr" + chr).setInteger("start", start).setInteger("end", end).setFirstResult(offset!=null?offset:0).setMaxResults(maxResults!=null?maxResults:10).setString("category", varAnnoGroupType).setString("class", hgmdType).list();
		}
		
		afPointInfors = (List<AFPoint>) (afPointInfors.isEmpty()? new ArrayList() : afPointInfors); 
		
		/*for(Iterator<AFPoint> iterator = afPointInfors.iterator(); iterator.hasNext();) {
			AFPoint afPoint = iterator.next();
			if((afPoint.getREF().length() != 1) && (afPoint.getALT().length() != 1))
				iterator.remove();
		}*/
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
		SQLQuery query = getSession().createSQLQuery("SELECT * FROM gno" + chr + " gno WHERE gno.CHROM=:chr AND gno.START=:pos AND gno.REF=:ref AND gno.ALT=:alt").addEntity(GnoGenomePoint.class);
		GnoGenomePoint point = (GnoGenomePoint) query.setString("chr", chr).setInteger("pos", pos).setString("ref", ref).setString("alt", alt).list().get(0);
		return point;
	}
	
	//query singlePoint for gno_exome
	public GnoExoPoint queryGnoExo(String chr, int pos, String ref, String alt) {
		SQLQuery query = getSession().createSQLQuery("SELECT * FROM exo" + chr + " exo WHERE exo.CHROM=:chr AND exo.START=:pos AND exo.REF=:ref AND exo.ALT=:alt").addEntity(GnoExoPoint.class);
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
	//VarAnno/history detail
	public VarAnnoPoint queryVarAnno(String chr, int start, String ref, String alt) {
		SQLQuery query = getSession().createSQLQuery("SELECT * FROM vcfanno WHERE CHROM=:chr AND START=:start AND REF=:ref AND ALT=:alt").addEntity(VarAnnoPoint.class);
		List<VarAnnoPoint> list = query.setString("chr", chr).setInteger("start", start).setString("ref", ref).setString("alt", alt).list();

		VarAnnoPoint point = list.isEmpty()? new VarAnnoPoint():list.get(0);
		return point;
	}
	public List<VarAnnoPoint_history> queryHistory(String chr, int start, String ref, String alt) {
		SQLQuery query = getSession().createSQLQuery("SELECT his.revision,his.dt_datetime AS datetime,his.action,his.Func, his.ExonicFunc, his.Cdot, his.Pdot, his.Category,his.Comments,his.OperUser FROM vcfanno_history his WHERE CHROM=:chr AND START=:start AND REF=:ref AND ALT=:alt ORDER BY revision").addEntity(VarAnnoPoint_history.class);
		List<VarAnnoPoint_history> list = query.setString("chr", chr).setInteger("start", start).setString("ref", ref).setString("alt", alt).list();
		return list;
	}
	
	//hgmd detail query
	public HgmdPoint queryHgmd(String chr, int pos, String ref, String alt) {
		SQLQuery query = getSession().createSQLQuery("SELECT * FROM hg38_hgmd WHERE CHROM=:chr AND POS=:pos AND REF=:ref AND ALT=:alt").addEntity(HgmdPoint.class);
		List<HgmdPoint> list = query.setString("chr", chr).setInteger("pos", pos).setString("ref", ref).setString("alt", alt).list();

		HgmdPoint point = list.isEmpty()? new HgmdPoint():list.get(0);
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
	
	public String batchUpdate(List<VarAnnoReportPoint> varAnnoPoints,String sampleId) {

		for(Iterator<VarAnnoReportPoint> iterator = varAnnoPoints.iterator(); iterator.hasNext();) {
			VarAnnoReportPoint varAnnoPointReport = iterator.next();
	
			//½«annovarPoint object merge into persistence a copy is persisted and this object is free
			try {
				VarAnnoPoint varAnnoPoint = varAnnoPointReport.getVarAnnoPoint();
				
				//set Func/ExonicFunc/Cdot/Pdot
				SQLQuery query = getSession().createSQLQuery("SELECT * FROM hg38_0base_FuncCPdot WHERE CHROM=:chr AND START=:start AND END=:end AND REF=:ref AND ALT=:alt").addEntity(CPdotPoint.class);
				CPdotPoint cPdotPoint = (CPdotPoint) query.setString("chr", varAnnoPoint.getCHROM()).setInteger("start", Integer.parseInt(varAnnoPoint.getSTART())).setInteger("end", Integer.parseInt(varAnnoPoint.getEND())).setString("ref", varAnnoPoint.getREF()).setString("alt", varAnnoPoint.getALT()).uniqueResult();
				if(cPdotPoint != null) {
					varAnnoPoint.setFunc(cPdotPoint.getFunc());
					varAnnoPoint.setExonicFunc(cPdotPoint.getExonicFunc());
					varAnnoPoint.setCdot(cPdotPoint.getCdot());
					varAnnoPoint.setPdot(cPdotPoint.getPdot());
				}
				getSession().merge(varAnnoPoint);
				getSession().createSQLQuery("UPDATE sampleinfor SET REPORT=:report WHERE SAMPLE=:sample AND CHROM=:chr AND POS=:pos AND REF=:ref AND ALT=:alt").setString("sample", sampleId).setString("chr", varAnnoPoint.getCHROM()).setInteger("pos", Integer.parseInt(varAnnoPoint.getSTART())).setString("ref", varAnnoPoint.getREF()).setString("alt", varAnnoPoint.getALT()).setString("report", varAnnoPointReport.getReport()).executeUpdate();
			} catch (Exception e) {
				return null;
			}
			
		}
		
		return "successUpdate";
	}
	//query sampleID all hgmdVarAnnoPoints
	public List<HgmdVarAnnoPoint> queryAll(String sampleID) {
		String query = "select hgmdtable.CHROM, hgmdtable.POS, hgmdtable.REF, hgmdtable.ALT, hgmdtable.CLASS, vcftable.Category from  (select sample.CHROM,sample.POS,sample.REF,sample.ALT,hgmdtarget.CLASS from sampleinfor sample inner join (select * from hg38_hgmd  hgmd where hgmd.CLASS='DM' or hgmd.CLASS='DM?') hgmdtarget on (sample.CHROM=hgmdtarget.CHROM and sample.POS=hgmdtarget.POS and sample.REF=hgmdtarget.REF and sample.ALT=hgmdtarget.ALT) where sample.SAMPLE=:sampleID) as hgmdtable     left join     (select sample.CHROM,sample.POS,sample.REF,sample.ALT,vcftarget.Category from sampleinfor sample inner join (select * from vcfanno where vcfanno.Category='PATH' or vcfanno.Category='LiPATH') vcftarget on (sample.CHROM=vcftarget.CHROM and sample.POS=vcftarget.START and sample.REF=vcftarget.REF and sample.ALT=vcftarget.ALT) where sample.SAMPLE=:sampleID) as vcftable     on hgmdtable.CHROM=vcftable.CHROM and hgmdtable.POS=vcftable.POS and hgmdtable.REF=vcftable.REF and hgmdtable.ALT=vcftable.ALT union select vcftable.CHROM, vcftable.POS, vcftable.REF, vcftable.ALT, hgmdtable.CLASS, vcftable.Category from  (select sample.CHROM,sample.POS,sample.REF,sample.ALT,hgmdtarget.CLASS from sampleinfor sample inner join (select * from hg38_hgmd  hgmd where hgmd.CLASS='DM' or hgmd.CLASS='DM?') hgmdtarget on (sample.CHROM=hgmdtarget.CHROM and sample.POS=hgmdtarget.POS and sample.REF=hgmdtarget.REF and sample.ALT=hgmdtarget.ALT) where sample.SAMPLE=:sampleID) as hgmdtable     right join     (select sample.CHROM,sample.POS,sample.REF,sample.ALT,vcftarget.Category from sampleinfor sample inner join (select * from vcfanno where vcfanno.Category='Path' or vcfanno.Category='LiPATH') vcftarget on (sample.CHROM=vcftarget.CHROM and sample.POS=vcftarget.START and sample.REF=vcftarget.REF and sample.ALT=vcftarget.ALT) where sample.SAMPLE=:sampleID) as vcftable on hgmdtable.CHROM=vcftable.CHROM and hgmdtable.POS=vcftable.POS and hgmdtable.REF=vcftable.REF and hgmdtable.ALT=vcftable.ALT";
		SQLQuery query2 = getSession().createSQLQuery(query).addEntity(HgmdVarAnnoPoint.class);
		List<HgmdVarAnnoPoint> points = query2.setString("sampleID", sampleID).list();
		return points;
	}
	
	//count pageCount three sample
	public BigInteger countThree(String child, String chr, int start, int end, String hgmdSelect, String clinvarSelect) {
		SQLQuery sqlQuery;
		if("All".equals(hgmdSelect) && "All".equals(clinvarSelect)) {
			sqlQuery = getSession().createSQLQuery("SELECT count(*) FROM sampleinfor sample WHERE sample.SAMPLE = :sample AND sample.CHROM = :chr AND sample.POS BETWEEN :start AND :end AND LENGTH(sample.REF)=1 AND LENGTH(sample.ALT)=1");
			return(BigInteger)sqlQuery.setString("sample", child).setString("chr", "chr"+chr).setInteger("start", start).setInteger("end", end).uniqueResult();
		} else if("All".equals(hgmdSelect) && !"All".equals(clinvarSelect)) {
			sqlQuery = getSession().createSQLQuery("SELECT count(*) FROM sampleinfor sample INNER JOIN vcfanno ON (sample.CHROM=vcfanno.CHROM AND sample.POS=vcfanno.START AND sample.REF=vcfanno.REF AND sample.ALT=vcfanno.ALT AND vcfanno.Category = :category) WHERE sample.SAMPLE = :sample AND sample.CHROM = :chr AND sample.POS BETWEEN :start AND :end AND LENGTH(sample.REF)=1 AND LENGTH(sample.ALT)=1");
			return (BigInteger)sqlQuery.setString("sample", child).setString("chr", "chr" + chr).setInteger("start", start).setInteger("end", end).setString("category", clinvarSelect).uniqueResult();
		} else if (! "All".equals(hgmdSelect) && "All".equals(clinvarSelect)) {
			sqlQuery = getSession().createSQLQuery("SELECT count(*) FROM sampleinfor sample INNER JOIN hg38_hgmd hgmd ON (sample.CHROM=hgmd.CHROM AND sample.POS=hgmd.POS AND sample.REF=hgmd.REF AND sample.ALT=hgmd.ALT AND hgmd.CLASS = :class) WHERE sample.SAMPLE = :sample AND sample.CHROM = :chr AND sample.POS BETWEEN :start AND :end AND LENGTH(sample.REF)=1 AND LENGTH(sample.ALT)=1");
			return (BigInteger)sqlQuery.setString("sample", child).setString("chr", "chr" + chr).setInteger("start", start).setInteger("end", end).setString("class", hgmdSelect).uniqueResult();
		} else {
			sqlQuery = getSession().createSQLQuery("SELECT count(*) FROM sampleinfor sample INNER JOIN vcfanno ON (sample.CHROM=vcfanno.CHROM AND sample.POS=vcfanno.START AND sample.REF=vcfanno.REF AND sample.ALT=vcfanno.ALT AND vcfanno.Category = :category) INNER JOIN hg38_hgmd hgmd ON (sample.CHROM=hgmd.CHROM AND sample.POS=hgmd.POS AND sample.REF=hgmd.REF AND sample.ALT=hgmd.ALT AND hgmd.CLASS = :class) WHERE sample.SAMPLE = :sample AND sample.CHROM = :chr AND sample.POS BETWEEN :start AND :end AND LENGTH(sample.REF)=1 AND LENGTH(sample.ALT)=1");
			return (BigInteger)sqlQuery.setString("sample", child).setString("chr", "chr" + chr).setInteger("start", start).setInteger("end", end).setString("category", clinvarSelect).setString("class", hgmdSelect).uniqueResult();
		}
	}
	
	//query three sample with three patterns
	public List<ThreeSamplePoint> queryWithRange(String maleId, String femaleId, String child, String chr, int start,
			int end, int perpage, int offset, String hgmdSelect, String clinvarSelect) {
		String query;
		if("All".equals(hgmdSelect) && "All".equals(clinvarSelect)) {
			query = "SELECT sample.CHROM,sample.POS,sample.REF,sample.ALT, round((sample.AO/(sample.AO+sample.RO)),3) AS ChildAF, father.ALT AS FatherALT, round((father.AO/(father.AO+father.RO)),3) AS FatherAF, mother.ALT AS MotherALT, round((mother.AO/(mother.AO+mother.RO)),3) AS motherAF, com.symbol AS Symbol,\"rs\" AS RS, gno.AF_GNOMAD AS AF_gno_genome,exo.AF_GNOMAD AS AF_gno_exome, 1kg.AF_EAS AS AF_EAS_1kg, esp.AF_ALL AS AF_ALL_esp, exac.EXAC_ALL AS AF_ALL_exac, annovar.SIFT_score AS SIFT_score, annovar.Polyphen2_HDIV_score as Polyphen2_HDIV_score, clinvar.CLNSIG AS CLNSIG_clinvar, hgmd.CLASS AS CLASS, vcfanno.Category, vcfanno.Comments, sample.REPORT FROM sampleinfor sample LEFT JOIN (SELECT ref.chr_name,ref.tx_start,ref.tx_end,homo.symbol FROM refseqs ref,homo_geninfor homo WHERE ref.entrez_id = homo.entrez_id) AS com ON (sample.CHROM=com.chr_name and sample.POS > com.tx_start and sample.POS<com.tx_end)  LEFT JOIN gnochr" + chr + " gno ON (sample.CHROM = gno.CHROM AND sample.POS = gno.START AND sample.REF = gno.REF AND sample.ALT = gno.ALT) LEFT JOIN exochr" + chr + " exo ON (sample.CHROM = exo.CHROM AND sample.POS = exo.START AND sample.REF = exo.REF AND sample.ALT = exo.ALT) LEFT JOIN hg38_1kg 1kg ON (sample.CHROM = 1kg.CHROM AND sample.POS = 1kg.POS AND sample.REF = 1kg.REF AND sample.ALT = 1kg.OBS) LEFT JOIN hg38_esp esp ON (sample.CHROM = esp.CHROM AND sample.POS = esp.START AND sample.POS = esp.END AND sample.REF = esp.REF AND sample.ALT = esp.ALT) LEFT JOIN hg38_exac exac ON (sample.CHROM = exac.CHROM AND sample.POS = exac.START AND sample.POS = exac.END AND sample.REF = exac.REF AND sample.ALT = exac.ALT) LEFT JOIN hg38_annovar annovar ON (sample.CHROM = annovar.CHROM AND sample.POS = annovar.START AND sample.POS = annovar.END AND sample.REF = annovar.REF AND sample.ALT = annovar.ALT) LEFT JOIN clinvar ON (sample.CHROM = clinvar.CHROM AND sample.POS = clinvar.START_POINT AND sample.POS = clinvar.END_POINT AND sample.REF = clinvar.REF AND sample.ALT = clinvar.ALT) LEFT JOIN hg38_hgmd hgmd ON(sample.CHROM=hgmd.CHROM AND sample.POS = hgmd.POS AND sample.REF = hgmd.REF AND sample.ALT = hgmd.ALT) LEFT JOIN vcfanno ON(sample.CHROM=vcfanno.CHROM AND sample.POS=vcfanno.START AND sample.REF=vcfanno.REF AND sample.ALT=vcfanno.ALT) LEFT JOIN sampleinfor father ON(father.SAMPLE=:father and father.CHROM=sample.CHROM and father.POS=sample.POS and father.REF= sample.REF) LEFT JOIN sampleinfor mother ON(mother.SAMPLE=:mother and mother.CHROM=sample.CHROM and mother.POS=sample.POS and mother.REF=sample.REF) WHERE sample.SAMPLE = :sample AND sample.CHROM = :chr AND sample.POS BETWEEN :start AND :end AND LENGTH(sample.REF)=1 AND LENGTH(sample.ALT)=1 GROUP BY sample.POS";
			SQLQuery query1 = getSession().createSQLQuery(query).addEntity(ThreeSamplePoint.class);
			List<ThreeSamplePoint> points = query1.setString("sample", child).setString("father", maleId).setString("mother", femaleId).setString("chr", "chr"+chr).setInteger("start", start).setInteger("end", end).setFirstResult(offset).setMaxResults(perpage).list();
			return points;
		} else if ("All".equals(hgmdSelect) && ! "All".equals(clinvarSelect)) {
			query = "SELECT sample.CHROM,sample.POS,sample.REF,sample.ALT, round((sample.AO/(sample.AO+sample.RO)),3) AS ChildAF, father.ALT AS FatherALT, round((father.AO/(father.AO+father.RO)),3) AS FatherAF, mother.ALT AS MotherALT, round((mother.AO/(mother.AO+mother.RO)),3) AS motherAF, com.symbol AS Symbol,\"rs\" AS RS, gno.AF_GNOMAD AS AF_gno_genome,exo.AF_GNOMAD AS AF_gno_exome, 1kg.AF_EAS AS AF_EAS_1kg, esp.AF_ALL AS AF_ALL_esp, exac.EXAC_ALL AS AF_ALL_exac, annovar.SIFT_score AS SIFT_score, annovar.Polyphen2_HDIV_score as Polyphen2_HDIV_score, clinvar.CLNSIG AS CLNSIG_clinvar, hgmd.CLASS AS CLASS, vcfanno.Category, vcfanno.Comments, sample.REPORT FROM sampleinfor sample LEFT JOIN (SELECT ref.chr_name,ref.tx_start,ref.tx_end,homo.symbol FROM refseqs ref,homo_geninfor homo WHERE ref.entrez_id = homo.entrez_id) AS com ON (sample.CHROM=com.chr_name and sample.POS > com.tx_start and sample.POS<com.tx_end)  LEFT JOIN gnochr" + chr + " gno ON (sample.CHROM = gno.CHROM AND sample.POS = gno.START AND sample.REF = gno.REF AND sample.ALT = gno.ALT) LEFT JOIN exochr" + chr + " exo ON (sample.CHROM = exo.CHROM AND sample.POS = exo.START AND sample.REF = exo.REF AND sample.ALT = exo.ALT) LEFT JOIN hg38_1kg 1kg ON (sample.CHROM = 1kg.CHROM AND sample.POS = 1kg.POS AND sample.REF = 1kg.REF AND sample.ALT = 1kg.OBS) LEFT JOIN hg38_esp esp ON (sample.CHROM = esp.CHROM AND sample.POS = esp.START AND sample.POS = esp.END AND sample.REF = esp.REF AND sample.ALT = esp.ALT) LEFT JOIN hg38_exac exac ON (sample.CHROM = exac.CHROM AND sample.POS = exac.START AND sample.POS = exac.END AND sample.REF = exac.REF AND sample.ALT = exac.ALT) LEFT JOIN hg38_annovar annovar ON (sample.CHROM = annovar.CHROM AND sample.POS = annovar.START AND sample.POS = annovar.END AND sample.REF = annovar.REF AND sample.ALT = annovar.ALT) LEFT JOIN clinvar ON (sample.CHROM = clinvar.CHROM AND sample.POS = clinvar.START_POINT AND sample.POS = clinvar.END_POINT AND sample.REF = clinvar.REF AND sample.ALT = clinvar.ALT) LEFT JOIN hg38_hgmd hgmd ON(sample.CHROM=hgmd.CHROM AND sample.POS = hgmd.POS AND sample.REF = hgmd.REF AND sample.ALT = hgmd.ALT) LEFT JOIN vcfanno ON(sample.CHROM=vcfanno.CHROM AND sample.POS=vcfanno.START AND sample.REF=vcfanno.REF AND sample.ALT=vcfanno.ALT) LEFT JOIN sampleinfor father ON(father.SAMPLE=:father and father.CHROM=sample.CHROM and father.POS=sample.POS and father.REF= sample.REF) LEFT JOIN sampleinfor mother ON(mother.SAMPLE=:mother and mother.CHROM=sample.CHROM and mother.POS=sample.POS and mother.REF=sample.REF) WHERE vcfanno.Category=:category AND sample.SAMPLE = :sample AND sample.CHROM = :chr AND sample.POS BETWEEN :start AND :end AND LENGTH(sample.REF)=1 AND LENGTH(sample.ALT)=1 GROUP BY sample.POS";
			SQLQuery query1 = getSession().createSQLQuery(query).addEntity(ThreeSamplePoint.class);
			List<ThreeSamplePoint> points = query1.setString("sample", child).setString("father", maleId).setString("mother", femaleId).setString("chr", "chr"+chr).setInteger("start", start).setInteger("end", end).setString("category", clinvarSelect).setFirstResult(offset).setMaxResults(perpage).list();
			return points;
		} else if (! "All".equals(hgmdSelect) && "All".equals(clinvarSelect)) {
			query = "SELECT sample.CHROM,sample.POS,sample.REF,sample.ALT, round((sample.AO/(sample.AO+sample.RO)),3) AS ChildAF, father.ALT AS FatherALT, round((father.AO/(father.AO+father.RO)),3) AS FatherAF, mother.ALT AS MotherALT, round((mother.AO/(mother.AO+mother.RO)),3) AS motherAF, com.symbol AS Symbol,\"rs\" AS RS, gno.AF_GNOMAD AS AF_gno_genome,exo.AF_GNOMAD AS AF_gno_exome, 1kg.AF_EAS AS AF_EAS_1kg, esp.AF_ALL AS AF_ALL_esp, exac.EXAC_ALL AS AF_ALL_exac, annovar.SIFT_score AS SIFT_score, annovar.Polyphen2_HDIV_score as Polyphen2_HDIV_score, clinvar.CLNSIG AS CLNSIG_clinvar, hgmd.CLASS AS CLASS, vcfanno.Category, vcfanno.Comments, sample.REPORT FROM sampleinfor sample LEFT JOIN (SELECT ref.chr_name,ref.tx_start,ref.tx_end,homo.symbol FROM refseqs ref,homo_geninfor homo WHERE ref.entrez_id = homo.entrez_id) AS com ON (sample.CHROM=com.chr_name and sample.POS > com.tx_start and sample.POS<com.tx_end)  LEFT JOIN gnochr" + chr + " gno ON (sample.CHROM = gno.CHROM AND sample.POS = gno.START AND sample.REF = gno.REF AND sample.ALT = gno.ALT) LEFT JOIN exochr" + chr + " exo ON (sample.CHROM = exo.CHROM AND sample.POS = exo.START AND sample.REF = exo.REF AND sample.ALT = exo.ALT) LEFT JOIN hg38_1kg 1kg ON (sample.CHROM = 1kg.CHROM AND sample.POS = 1kg.POS AND sample.REF = 1kg.REF AND sample.ALT = 1kg.OBS) LEFT JOIN hg38_esp esp ON (sample.CHROM = esp.CHROM AND sample.POS = esp.START AND sample.POS = esp.END AND sample.REF = esp.REF AND sample.ALT = esp.ALT) LEFT JOIN hg38_exac exac ON (sample.CHROM = exac.CHROM AND sample.POS = exac.START AND sample.POS = exac.END AND sample.REF = exac.REF AND sample.ALT = exac.ALT) LEFT JOIN hg38_annovar annovar ON (sample.CHROM = annovar.CHROM AND sample.POS = annovar.START AND sample.POS = annovar.END AND sample.REF = annovar.REF AND sample.ALT = annovar.ALT) LEFT JOIN clinvar ON (sample.CHROM = clinvar.CHROM AND sample.POS = clinvar.START_POINT AND sample.POS = clinvar.END_POINT AND sample.REF = clinvar.REF AND sample.ALT = clinvar.ALT) LEFT JOIN hg38_hgmd hgmd ON(sample.CHROM=hgmd.CHROM AND sample.POS = hgmd.POS AND sample.REF = hgmd.REF AND sample.ALT = hgmd.ALT) LEFT JOIN vcfanno ON(sample.CHROM=vcfanno.CHROM AND sample.POS=vcfanno.START AND sample.REF=vcfanno.REF AND sample.ALT=vcfanno.ALT) LEFT JOIN sampleinfor father ON(father.SAMPLE=:father and father.CHROM=sample.CHROM and father.POS=sample.POS and father.REF= sample.REF) LEFT JOIN sampleinfor mother ON(mother.SAMPLE=:mother and mother.CHROM=sample.CHROM and mother.POS=sample.POS and mother.REF=sample.REF) WHERE hgmd.CLASS=:class AND sample.SAMPLE = :sample AND sample.CHROM = :chr AND sample.POS BETWEEN :start AND :end AND LENGTH(sample.REF)=1 AND LENGTH(sample.ALT)=1 GROUP BY sample.POS";
			SQLQuery query1 = getSession().createSQLQuery(query).addEntity(ThreeSamplePoint.class);
			List<ThreeSamplePoint> points = query1.setString("sample", child).setString("father", maleId).setString("mother", femaleId).setString("chr", "chr"+chr).setInteger("start", start).setInteger("end", end).setString("class", hgmdSelect).setFirstResult(offset).setMaxResults(perpage).list();
			return points;
		} else {
			query = "SELECT sample.CHROM,sample.POS,sample.REF,sample.ALT, round((sample.AO/(sample.AO+sample.RO)),3) AS ChildAF, father.ALT AS FatherALT, round((father.AO/(father.AO+father.RO)),3) AS FatherAF, mother.ALT AS MotherALT, round((mother.AO/(mother.AO+mother.RO)),3) AS motherAF, com.symbol AS Symbol,\"rs\" AS RS, gno.AF_GNOMAD AS AF_gno_genome,exo.AF_GNOMAD AS AF_gno_exome, 1kg.AF_EAS AS AF_EAS_1kg, esp.AF_ALL AS AF_ALL_esp, exac.EXAC_ALL AS AF_ALL_exac, annovar.SIFT_score AS SIFT_score, annovar.Polyphen2_HDIV_score as Polyphen2_HDIV_score, clinvar.CLNSIG AS CLNSIG_clinvar, hgmd.CLASS AS CLASS, vcfanno.Category, vcfanno.Comments, sample.REPORT FROM sampleinfor sample LEFT JOIN (SELECT ref.chr_name,ref.tx_start,ref.tx_end,homo.symbol FROM refseqs ref,homo_geninfor homo WHERE ref.entrez_id = homo.entrez_id) AS com ON (sample.CHROM=com.chr_name and sample.POS > com.tx_start and sample.POS<com.tx_end)  LEFT JOIN gnochr" + chr + " gno ON (sample.CHROM = gno.CHROM AND sample.POS = gno.START AND sample.REF = gno.REF AND sample.ALT = gno.ALT) LEFT JOIN exochr" + chr + " exo ON (sample.CHROM = exo.CHROM AND sample.POS = exo.START AND sample.REF = exo.REF AND sample.ALT = exo.ALT) LEFT JOIN hg38_1kg 1kg ON (sample.CHROM = 1kg.CHROM AND sample.POS = 1kg.POS AND sample.REF = 1kg.REF AND sample.ALT = 1kg.OBS) LEFT JOIN hg38_esp esp ON (sample.CHROM = esp.CHROM AND sample.POS = esp.START AND sample.POS = esp.END AND sample.REF = esp.REF AND sample.ALT = esp.ALT) LEFT JOIN hg38_exac exac ON (sample.CHROM = exac.CHROM AND sample.POS = exac.START AND sample.POS = exac.END AND sample.REF = exac.REF AND sample.ALT = exac.ALT) LEFT JOIN hg38_annovar annovar ON (sample.CHROM = annovar.CHROM AND sample.POS = annovar.START AND sample.POS = annovar.END AND sample.REF = annovar.REF AND sample.ALT = annovar.ALT) LEFT JOIN clinvar ON (sample.CHROM = clinvar.CHROM AND sample.POS = clinvar.START_POINT AND sample.POS = clinvar.END_POINT AND sample.REF = clinvar.REF AND sample.ALT = clinvar.ALT) LEFT JOIN hg38_hgmd hgmd ON(sample.CHROM=hgmd.CHROM AND sample.POS = hgmd.POS AND sample.REF = hgmd.REF AND sample.ALT = hgmd.ALT) LEFT JOIN vcfanno ON(sample.CHROM=vcfanno.CHROM AND sample.POS=vcfanno.START AND sample.REF=vcfanno.REF AND sample.ALT=vcfanno.ALT) LEFT JOIN sampleinfor father ON(father.SAMPLE=:father and father.CHROM=sample.CHROM and father.POS=sample.POS and father.REF= sample.REF) LEFT JOIN sampleinfor mother ON(mother.SAMPLE=:mother and mother.CHROM=sample.CHROM and mother.POS=sample.POS and mother.REF=sample.REF) WHERE hgmd.CLASS=:class AND vcfanno.Category=:category AND sample.SAMPLE = :sample AND sample.CHROM = :chr AND sample.POS BETWEEN :start AND :end AND LENGTH(sample.REF)=1 AND LENGTH(sample.ALT)=1 GROUP BY sample.POS";
			SQLQuery query1 = getSession().createSQLQuery(query).addEntity(ThreeSamplePoint.class);
			List<ThreeSamplePoint> points = query1.setString("sample", child).setString("father", maleId).setString("mother", femaleId).setString("chr", "chr"+chr).setInteger("start", start).setInteger("end", end).setString("class", hgmdSelect).setString("category", clinvarSelect).setFirstResult(offset).setMaxResults(perpage).list();
			return points;
		}
		
	}
	
	
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
