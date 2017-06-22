package com.cloudhealth.view.dao;

import java.io.BufferedReader;
import java.io.IOException;
import java.math.BigInteger;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.jdbc.Work;
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
import com.cloudhealth.view.entity.TrioDiffGroup;
import com.cloudhealth.view.entity.TrioDiffPoint;
import com.cloudhealth.view.entity.VarAnnoPoint;
import com.cloudhealth.view.entity.VarAnnoPoint_history;
import com.cloudhealth.view.model.VarAnnoReportPoint;

@Repository
public class PointDaoImpl implements PointDao {

	@Autowired
	private SessionFactory sessionFactory;
	
	//count
	@Transactional
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
	@Transactional
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
	@Transactional
	public RangePoint findRangeBySymbol(String symbol) {
		SQLQuery query = getSession().createSQLQuery("select ref.acc, ref.chr_name, ref.tx_start, ref.tx_end from refseqs ref where ref.entrez_id = ( select * from ( select homo1.entrez_id from homo_geninfor homo1 where homo1.symbol= ? union select  homo2.entrez_id from homo_geninfor homo2 where homo2.synonyms like ? or synonyms like ? or synonyms like ? or synonyms like ?) as s limit 1) order by exon_count desc limit 1").addEntity(RangePoint.class);
		List<RangePoint> pointList = query.setString(0, symbol).setString(1, "{" + symbol + ",%").setString(2, "%," + symbol + "}").setString(3, "%," + symbol + ",%").setString(4, "{" + symbol + "}").list();
		RangePoint point = pointList.isEmpty()? new RangePoint():pointList.get(0);
		return point;
	}
	
	//base on nm to find chr/start/end in sample
	@Transactional
	public RangePoint findRangeByNm(String nm) {
		
		if(nm.contains("."))
			nm = nm.substring(0, nm.indexOf("."));
		
		SQLQuery query = getSession().createSQLQuery("select acc, chr_name, tx_start, tx_end from refseqs where acc= ?").addEntity(RangePoint.class);
		List<RangePoint> pointList = query.setString(0, nm).list();
		RangePoint point = pointList.isEmpty()? new RangePoint() : pointList.get(0);
		return point;
	}
	
	//query singlePoint for gno_genome
	@Transactional
	public GnoGenomePoint queryGnoGen(String chr, int pos, String ref, String alt) {
		SQLQuery query = getSession().createSQLQuery("SELECT * FROM gno" + chr + " gno WHERE gno.CHROM=:chr AND gno.START=:pos AND gno.REF=:ref AND gno.ALT=:alt").addEntity(GnoGenomePoint.class);
		GnoGenomePoint point = (GnoGenomePoint) query.setString("chr", chr).setInteger("pos", pos).setString("ref", ref).setString("alt", alt).list().get(0);
		return point;
	}
	
	//query singlePoint for gno_exome
	@Transactional
	public GnoExoPoint queryGnoExo(String chr, int pos, String ref, String alt) {
		SQLQuery query = getSession().createSQLQuery("SELECT * FROM exo" + chr + " exo WHERE exo.CHROM=:chr AND exo.START=:pos AND exo.REF=:ref AND exo.ALT=:alt").addEntity(GnoExoPoint.class);
		GnoExoPoint point =  (GnoExoPoint) query.setString("chr", chr).setInteger("pos", pos).setString("ref", ref).setString("alt", alt).list().get(0);
		return point;
	}
	
	//query singlePoint for 1kg
	@Transactional
	public OnekgPoint queryOnekg(String chr, int pos, String ref, String obs) {
		SQLQuery query = getSession().createSQLQuery("SELECT * FROM hg38_1kg  1kg WHERE 1kg.CHROM=:chr AND 1kg.POS=:pos AND 1kg.REF=:ref AND 1kg.OBS=:obs").addEntity(OnekgPoint.class);
		OnekgPoint point = (OnekgPoint) query.setString("chr", chr).setInteger("pos", pos).setString("ref", ref).setString("obs", obs).list().get(0);
		return point;
	}
	
	//query singlePoint for esp
	@Transactional
	public EspPoint queryEsp(String chr, int start, int end, String ref, String alt) {
		SQLQuery query = getSession().createSQLQuery("SELECT * FROM hg38_esp esp WHERE esp.CHROM=:chr AND esp.START=:start AND esp.END=:end AND esp.REF=:ref AND esp.ALT=:alt").addEntity(EspPoint.class);
		EspPoint point = (EspPoint) query.setString("chr", chr).setInteger("start", start).setInteger("end", end).setString("ref", ref).setString("alt", alt).list().get(0);
		return point;
	}
	
	//query singlePoint for exac
	@Transactional
	public ExacPoint queryExac(String chr, int start, int end, String ref, String alt) {
		SQLQuery query = getSession().createSQLQuery("SELECT * FROM hg38_exac exac WHERE exac.CHROM=:chr AND exac.START=:start AND exac.END=:end AND exac.REF=:ref AND exac.ALT=:alt").addEntity(ExacPoint.class);
		ExacPoint point = (ExacPoint) query.setString("chr", chr).setInteger("start", start).setInteger("end", end).setString("ref", ref).setString("alt", alt).list().get(0);
		return point;
	}
	
	//query singlePoint for annovar
	@Transactional
	public AnnovarPoint queryAnnovar(String chr, int start, int end, String ref, String alt) {
		SQLQuery query = getSession().createSQLQuery("SELECT * FROM hg38_annovar annovar WHERE annovar.CHROM=:chr AND annovar.START=:start AND annovar.END=:end AND annovar.REF=:ref AND annovar.ALT=:alt").addEntity(AnnovarPoint.class);
		AnnovarPoint point = (AnnovarPoint) query.setString("chr", chr).setInteger("start", start).setInteger("end", end).setString("ref", ref).setString("alt", alt).list().get(0);
		return point;
	}
	
	//query singlePoint for clinvar
	@Transactional
	public ClinvarPoint queryClinvar(String chr, int start, int end, String ref, String alt) {
		SQLQuery query = getSession().createSQLQuery("SELECT * FROM clinvar WHERE clinvar.CHROM=:chr AND clinvar.START_POINT=:start AND clinvar.END_POINT=:end AND clinvar.REF=:ref AND clinvar.ALT=:alt").addEntity(ClinvarPoint.class);
		ClinvarPoint point = (ClinvarPoint) query.setString("chr", chr).setInteger("start", start).setInteger("end", end).setString("ref", ref).setString("alt", alt).list().get(0);
		return point;
	}
	//VarAnno/history detail
	@Transactional
	public VarAnnoPoint queryVarAnno(String chr, int start, String ref, String alt) {
		SQLQuery query = getSession().createSQLQuery("SELECT * FROM vcfanno WHERE CHROM=:chr AND START=:start AND REF=:ref AND ALT=:alt").addEntity(VarAnnoPoint.class);
		List<VarAnnoPoint> list = query.setString("chr", chr).setInteger("start", start).setString("ref", ref).setString("alt", alt).list();

		VarAnnoPoint point = list.isEmpty()? new VarAnnoPoint():list.get(0);
		return point;
	}
	@Transactional
	public List<VarAnnoPoint_history> queryHistory(String chr, int start, String ref, String alt) {
		SQLQuery query = getSession().createSQLQuery("SELECT his.revision,his.dt_datetime AS datetime,his.action,his.Func, his.ExonicFunc, his.Cdot, his.Pdot, his.Category,his.Comments,his.OperUser FROM vcfanno_history his WHERE CHROM=:chr AND START=:start AND REF=:ref AND ALT=:alt ORDER BY revision").addEntity(VarAnnoPoint_history.class);
		List<VarAnnoPoint_history> list = query.setString("chr", chr).setInteger("start", start).setString("ref", ref).setString("alt", alt).list();
		return list;
	}
	
	//hgmd detail query
	@Transactional
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
	@Transactional
	public String batchUpdate(List<VarAnnoReportPoint> varAnnoPoints,String sampleId) {

		Session session = getSession();
		for(Iterator<VarAnnoReportPoint> iterator = varAnnoPoints.iterator(); iterator.hasNext();) {
			VarAnnoReportPoint varAnnoPointReport = iterator.next();
	
			//将annovarPoint object merge into persistence a copy is persisted and this object is free
			try {
				VarAnnoPoint varAnnoPoint = varAnnoPointReport.getVarAnnoPoint();
				
				//set Func/ExonicFunc/Cdot/Pdot
				SQLQuery query = session.createSQLQuery("SELECT * FROM hg38_0base_FuncCPdot WHERE CHROM=:chr AND START=:start AND END=:end AND REF=:ref AND ALT=:alt").addEntity(CPdotPoint.class);
				CPdotPoint cPdotPoint = (CPdotPoint) query.setString("chr", varAnnoPoint.getCHROM()).setInteger("start", Integer.parseInt(varAnnoPoint.getSTART())).setInteger("end", Integer.parseInt(varAnnoPoint.getEND())).setString("ref", varAnnoPoint.getREF()).setString("alt", varAnnoPoint.getALT()).uniqueResult();
				if(cPdotPoint != null) {
					varAnnoPoint.setFunc(cPdotPoint.getFunc());
					varAnnoPoint.setExonicFunc(cPdotPoint.getExonicFunc());
					varAnnoPoint.setCdot(cPdotPoint.getCdot());
					varAnnoPoint.setPdot(cPdotPoint.getPdot());
				}
				session.merge(varAnnoPoint);
		
				//TODO
				//session.createSQLQuery("UPDATE sampleinfor SET REPORT=:report WHERE SAMPLE=:sample AND CHROM=:chr AND POS=:pos AND REF=:ref AND ALT=:alt").setString("sample", sampleId).setString("chr", varAnnoPoint.getCHROM()).setInteger("pos", Integer.parseInt(varAnnoPoint.getSTART())).setString("ref", varAnnoPoint.getREF()).setString("alt", varAnnoPoint.getALT()).setString("report", varAnnoPointReport.getReport()).executeUpdate();
				
			} catch (Exception e) {
				return null;
			}
			
		}
		
		//session.flush();
		//session.close();
		
		return "successUpdate";
	}
	//query sampleID all hgmdVarAnnoPoints
	@Transactional
	public List<HgmdVarAnnoPoint> queryAll(String sampleID) {
		String query = "select hgmdtable.CHROM, hgmdtable.POS, hgmdtable.REF, hgmdtable.ALT, hgmdtable.CLASS, vcftable.Category from  (select sample.CHROM,sample.POS,sample.REF,sample.ALT,hgmdtarget.CLASS from sampleinfor sample inner join (select * from hg38_hgmd  hgmd where hgmd.CLASS='DM' or hgmd.CLASS='DM?') hgmdtarget on (sample.CHROM=hgmdtarget.CHROM and sample.POS=hgmdtarget.POS and sample.REF=hgmdtarget.REF and sample.ALT=hgmdtarget.ALT) where sample.SAMPLE=:sampleID) as hgmdtable     left join     (select sample.CHROM,sample.POS,sample.REF,sample.ALT,vcftarget.Category from sampleinfor sample inner join (select * from vcfanno where vcfanno.Category='PATH' or vcfanno.Category='LiPATH') vcftarget on (sample.CHROM=vcftarget.CHROM and sample.POS=vcftarget.START and sample.REF=vcftarget.REF and sample.ALT=vcftarget.ALT) where sample.SAMPLE=:sampleID) as vcftable     on hgmdtable.CHROM=vcftable.CHROM and hgmdtable.POS=vcftable.POS and hgmdtable.REF=vcftable.REF and hgmdtable.ALT=vcftable.ALT union select vcftable.CHROM, vcftable.POS, vcftable.REF, vcftable.ALT, hgmdtable.CLASS, vcftable.Category from  (select sample.CHROM,sample.POS,sample.REF,sample.ALT,hgmdtarget.CLASS from sampleinfor sample inner join (select * from hg38_hgmd  hgmd where hgmd.CLASS='DM' or hgmd.CLASS='DM?') hgmdtarget on (sample.CHROM=hgmdtarget.CHROM and sample.POS=hgmdtarget.POS and sample.REF=hgmdtarget.REF and sample.ALT=hgmdtarget.ALT) where sample.SAMPLE=:sampleID) as hgmdtable     right join     (select sample.CHROM,sample.POS,sample.REF,sample.ALT,vcftarget.Category from sampleinfor sample inner join (select * from vcfanno where vcfanno.Category='Path' or vcfanno.Category='LiPATH') vcftarget on (sample.CHROM=vcftarget.CHROM and sample.POS=vcftarget.START and sample.REF=vcftarget.REF and sample.ALT=vcftarget.ALT) where sample.SAMPLE=:sampleID) as vcftable on hgmdtable.CHROM=vcftable.CHROM and hgmdtable.POS=vcftable.POS and hgmdtable.REF=vcftable.REF and hgmdtable.ALT=vcftable.ALT";
		SQLQuery query2 = getSession().createSQLQuery(query).addEntity(HgmdVarAnnoPoint.class);
		List<HgmdVarAnnoPoint> points = query2.setString("sampleID", sampleID).list();
		return points;
	}
	
	//count pageCount three sample
	@Transactional
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
	@Transactional
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
	
	//count the sampleId numbers in db
	@Transactional
	public List<String> countSampleId() {
			List<String> sampleList = getSession().createSQLQuery("SELECT SAMPLE FROM gene.sampleinfor group by SAMPLE").list();
			return sampleList;	
	}
	
	//list all triodiff group child/father/mother in triodiff table
	@Transactional
	public List<TrioDiffGroup> listTrioGroup() {
		List<TrioDiffGroup> trioList = getSession().createSQLQuery("select SampleId,FatherId,MotherId from trioDiff group by SampleId").addEntity(TrioDiffGroup.class).list();
		return trioList;
	}
	
	// query child diff both parent
	@Transactional
	public List<ThreeSamplePoint> queryChildDiffParent(String maleId, String femaleId, String child, int perpage, int offset, String hgmdSelect, String clinvarSelect) {
			
		if("All".equals(hgmdSelect) && "All".equals(clinvarSelect)) {
			
			String query = "SELECT diff.*, com.symbol AS Symbol, \"rs\" AS RS, gno.AF_GNOMAD AS AF_gno_genome,exo.AF_GNOMAD AS AF_gno_exome, 1kg.AF_EAS AS AF_EAS_1kg, esp.AF_ALL AS AF_ALL_esp, exac.EXAC_ALL AS AF_ALL_exac, annovar.SIFT_score AS SIFT_score, annovar.Polyphen2_HDIV_score as Polyphen2_HDIV_score, clinvar.CLNSIG AS CLNSIG_clinvar, hgmd.CLASS AS CLASS, vcfanno.Category,vcfanno.Comments, sample.REPORT from trioDiff diff LEFT JOIN (SELECT ref.chr_name,ref.tx_start,ref.tx_end,homo.symbol FROM refseqs ref,homo_geninfor homo WHERE ref.entrez_id = homo.entrez_id) AS com ON (diff.CHROM=com.chr_name and diff.POS > com.tx_start and diff.POS<com.tx_end)  LEFT JOIN gnochrall gno ON (diff.CHROM = gno.CHROM AND diff.POS = gno.START AND diff.REF = gno.REF AND diff.ALT = gno.ALT) LEFT JOIN exochrall exo ON (diff.CHROM = exo.CHROM AND diff.POS = exo.START AND diff.REF = exo.REF AND diff.ALT = exo.ALT) LEFT JOIN hg38_1kg 1kg ON (diff.CHROM = 1kg.CHROM AND diff.POS = 1kg.POS AND diff.REF = 1kg.REF AND diff.ALT = 1kg.OBS) LEFT JOIN hg38_esp esp ON (diff.CHROM = esp.CHROM AND diff.POS = esp.START AND diff.POS = esp.END AND diff.REF = esp.REF AND diff.ALT = esp.ALT) LEFT JOIN hg38_exac exac ON (diff.CHROM = exac.CHROM AND diff.POS = exac.START AND diff.POS = exac.END AND diff.REF = exac.REF AND diff.ALT = exac.ALT) LEFT JOIN hg38_annovar annovar ON (diff.CHROM = annovar.CHROM AND diff.POS = annovar.START AND diff.POS = annovar.END AND diff.REF = annovar.REF AND diff.ALT = annovar.ALT) LEFT JOIN clinvar ON (diff.CHROM = clinvar.CHROM AND diff.POS = clinvar.START_POINT AND diff.POS = clinvar.END_POINT AND diff.REF = clinvar.REF AND diff.ALT = clinvar.ALT) LEFT JOIN hg38_hgmd hgmd ON(diff.CHROM=hgmd.CHROM AND diff.POS = hgmd.POS AND diff.REF = hgmd.REF AND diff.ALT = hgmd.ALT) LEFT JOIN vcfanno on(diff.CHROM = vcfanno.CHROM and diff.POS = vcfanno.START and diff.REF = vcfanno.REF and diff.ALT= vcfanno.ALT) LEFT JOIN sampleinfor sample on(sample.SAMPLE=:child and diff.CHROM = sample.CHROM and diff.POS = sample.POS and diff.REF = sample.REF and diff.ALT = sample.ALT) WHERE diff.SampleId = :child AND diff.FatherId=:father AND diff.MotherId=:mother GROUP BY diff.CHROM,diff.POS";
			SQLQuery query1 = getSession().createSQLQuery(query).addEntity(ThreeSamplePoint.class);
			List<ThreeSamplePoint> points = query1.setString("child", child).setString("father", maleId).setString("mother", femaleId).setFirstResult(offset).setMaxResults(perpage).list();
			return points;
			
		} else if ("All".equals(hgmdSelect) && ! "All".equals(clinvarSelect)) {
			String query = "SELECT diff.*, com.symbol AS Symbol,\"rs\" AS RS, gno.AF_GNOMAD AS AF_gno_genome,exo.AF_GNOMAD AS AF_gno_exome, 1kg.AF_EAS AS AF_EAS_1kg, esp.AF_ALL AS AF_ALL_esp, exac.EXAC_ALL AS AF_ALL_exac, annovar.SIFT_score AS SIFT_score, annovar.Polyphen2_HDIV_score as Polyphen2_HDIV_score, clinvar.CLNSIG AS CLNSIG_clinvar, hgmd.CLASS AS CLASS, vcfanno.Category,vcfanno.Comments, sample.REPORT from trioDiff diff LEFT JOIN (SELECT ref.chr_name,ref.tx_start,ref.tx_end,homo.symbol FROM refseqs ref,homo_geninfor homo WHERE ref.entrez_id = homo.entrez_id) AS com ON (diff.CHROM=com.chr_name and diff.POS > com.tx_start and diff.POS<com.tx_end)  LEFT JOIN gnochrall gno ON (diff.CHROM = gno.CHROM AND diff.POS = gno.START AND diff.REF = gno.REF AND diff.ALT = gno.ALT) LEFT JOIN exochrall exo ON (diff.CHROM = exo.CHROM AND diff.POS = exo.START AND diff.REF = exo.REF AND diff.ALT = exo.ALT) LEFT JOIN hg38_1kg 1kg ON (diff.CHROM = 1kg.CHROM AND diff.POS = 1kg.POS AND diff.REF = 1kg.REF AND diff.ALT = 1kg.OBS) LEFT JOIN hg38_esp esp ON (diff.CHROM = esp.CHROM AND diff.POS = esp.START AND diff.POS = esp.END AND diff.REF = esp.REF AND diff.ALT = esp.ALT) LEFT JOIN hg38_exac exac ON (diff.CHROM = exac.CHROM AND diff.POS = exac.START AND diff.POS = exac.END AND diff.REF = exac.REF AND diff.ALT = exac.ALT) LEFT JOIN hg38_annovar annovar ON (diff.CHROM = annovar.CHROM AND diff.POS = annovar.START AND diff.POS = annovar.END AND diff.REF = annovar.REF AND diff.ALT = annovar.ALT) LEFT JOIN clinvar ON (diff.CHROM = clinvar.CHROM AND diff.POS = clinvar.START_POINT AND diff.POS = clinvar.END_POINT AND diff.REF = clinvar.REF AND diff.ALT = clinvar.ALT) LEFT JOIN hg38_hgmd hgmd ON(diff.CHROM=hgmd.CHROM AND diff.POS = hgmd.POS AND diff.REF = hgmd.REF AND diff.ALT = hgmd.ALT) LEFT JOIN vcfanno on(diff.CHROM = vcfanno.CHROM and diff.POS = vcfanno.START and diff.REF = vcfanno.REF and diff.ALT= vcfanno.ALT) LEFT JOIN sampleinfor sample on(sample.SAMPLE=:child and diff.CHROM = sample.CHROM and diff.POS = sample.POS and diff.REF = sample.REF and diff.ALT = sample.ALT) WHERE diff.SampleId = :child AND diff.FatherId=:father AND diff.MotherId=:mother AND vcfanno.Category=:category GROUP BY diff.CHROM,diff.POS";
			SQLQuery query1 = getSession().createSQLQuery(query).addEntity(ThreeSamplePoint.class);
			List<ThreeSamplePoint> points = query1.setString("child", child).setString("father", maleId).setString("mother", femaleId).setString("category", clinvarSelect).setFirstResult(offset).setMaxResults(perpage).list();
			return points;
			
		} else if (! "All".equals(hgmdSelect) && "All".equals(clinvarSelect)) {
			String query = "SELECT diff.*, com.symbol AS Symbol,\"rs\" AS RS, gno.AF_GNOMAD AS AF_gno_genome,exo.AF_GNOMAD AS AF_gno_exome, 1kg.AF_EAS AS AF_EAS_1kg, esp.AF_ALL AS AF_ALL_esp, exac.EXAC_ALL AS AF_ALL_exac, annovar.SIFT_score AS SIFT_score, annovar.Polyphen2_HDIV_score as Polyphen2_HDIV_score, clinvar.CLNSIG AS CLNSIG_clinvar, hgmd.CLASS AS CLASS, vcfanno.Category,vcfanno.Comments, sample.REPORT from trioDiff diff LEFT JOIN (SELECT ref.chr_name,ref.tx_start,ref.tx_end,homo.symbol FROM refseqs ref,homo_geninfor homo WHERE ref.entrez_id = homo.entrez_id) AS com ON (diff.CHROM=com.chr_name and diff.POS > com.tx_start and diff.POS<com.tx_end)  LEFT JOIN gnochrall gno ON (diff.CHROM = gno.CHROM AND diff.POS = gno.START AND diff.REF = gno.REF AND diff.ALT = gno.ALT) LEFT JOIN exochrall exo ON (diff.CHROM = exo.CHROM AND diff.POS = exo.START AND diff.REF = exo.REF AND diff.ALT = exo.ALT) LEFT JOIN hg38_1kg 1kg ON (diff.CHROM = 1kg.CHROM AND diff.POS = 1kg.POS AND diff.REF = 1kg.REF AND diff.ALT = 1kg.OBS) LEFT JOIN hg38_esp esp ON (diff.CHROM = esp.CHROM AND diff.POS = esp.START AND diff.POS = esp.END AND diff.REF = esp.REF AND diff.ALT = esp.ALT) LEFT JOIN hg38_exac exac ON (diff.CHROM = exac.CHROM AND diff.POS = exac.START AND diff.POS = exac.END AND diff.REF = exac.REF AND diff.ALT = exac.ALT) LEFT JOIN hg38_annovar annovar ON (diff.CHROM = annovar.CHROM AND diff.POS = annovar.START AND diff.POS = annovar.END AND diff.REF = annovar.REF AND diff.ALT = annovar.ALT) LEFT JOIN clinvar ON (diff.CHROM = clinvar.CHROM AND diff.POS = clinvar.START_POINT AND diff.POS = clinvar.END_POINT AND diff.REF = clinvar.REF AND diff.ALT = clinvar.ALT) LEFT JOIN hg38_hgmd hgmd ON(diff.CHROM=hgmd.CHROM AND diff.POS = hgmd.POS AND diff.REF = hgmd.REF AND diff.ALT = hgmd.ALT) LEFT JOIN vcfanno on(diff.CHROM = vcfanno.CHROM and diff.POS = vcfanno.START and diff.REF = vcfanno.REF and diff.ALT= vcfanno.ALT) LEFT JOIN sampleinfor sample on(sample.SAMPLE=:child and diff.CHROM = sample.CHROM and diff.POS = sample.POS and diff.REF = sample.REF and diff.ALT = sample.ALT) WHERE diff.SampleId = :child AND diff.FatherId=:father AND diff.MotherId=:mother AND hgmd.CLASS=:class GROUP BY diff.CHROM,diff.POS";
			SQLQuery query1 = getSession().createSQLQuery(query).addEntity(ThreeSamplePoint.class);
			List<ThreeSamplePoint> points = query1.setString("child", child).setString("father", maleId).setString("mother", femaleId).setString("class", hgmdSelect).setFirstResult(offset).setMaxResults(perpage).list();
			return points;
			
		} else {
			String query = "SELECT diff.*, com.symbol AS Symbol,\"rs\" AS RS, gno.AF_GNOMAD AS AF_gno_genome,exo.AF_GNOMAD AS AF_gno_exome, 1kg.AF_EAS AS AF_EAS_1kg, esp.AF_ALL AS AF_ALL_esp, exac.EXAC_ALL AS AF_ALL_exac, annovar.SIFT_score AS SIFT_score, annovar.Polyphen2_HDIV_score as Polyphen2_HDIV_score, clinvar.CLNSIG AS CLNSIG_clinvar, hgmd.CLASS AS CLASS, vcfanno.Category,vcfanno.Comments, sample.REPORT from trioDiff diff LEFT JOIN (SELECT ref.chr_name,ref.tx_start,ref.tx_end,homo.symbol FROM refseqs ref,homo_geninfor homo WHERE ref.entrez_id = homo.entrez_id) AS com ON (diff.CHROM=com.chr_name and diff.POS > com.tx_start and diff.POS<com.tx_end)  LEFT JOIN gnochrall gno ON (diff.CHROM = gno.CHROM AND diff.POS = gno.START AND diff.REF = gno.REF AND diff.ALT = gno.ALT) LEFT JOIN exochrall exo ON (diff.CHROM = exo.CHROM AND diff.POS = exo.START AND diff.REF = exo.REF AND diff.ALT = exo.ALT) LEFT JOIN hg38_1kg 1kg ON (diff.CHROM = 1kg.CHROM AND diff.POS = 1kg.POS AND diff.REF = 1kg.REF AND diff.ALT = 1kg.OBS) LEFT JOIN hg38_esp esp ON (diff.CHROM = esp.CHROM AND diff.POS = esp.START AND diff.POS = esp.END AND diff.REF = esp.REF AND diff.ALT = esp.ALT) LEFT JOIN hg38_exac exac ON (diff.CHROM = exac.CHROM AND diff.POS = exac.START AND diff.POS = exac.END AND diff.REF = exac.REF AND diff.ALT = exac.ALT) LEFT JOIN hg38_annovar annovar ON (diff.CHROM = annovar.CHROM AND diff.POS = annovar.START AND diff.POS = annovar.END AND diff.REF = annovar.REF AND diff.ALT = annovar.ALT) LEFT JOIN clinvar ON (diff.CHROM = clinvar.CHROM AND diff.POS = clinvar.START_POINT AND diff.POS = clinvar.END_POINT AND diff.REF = clinvar.REF AND diff.ALT = clinvar.ALT) LEFT JOIN hg38_hgmd hgmd ON(diff.CHROM=hgmd.CHROM AND diff.POS = hgmd.POS AND diff.REF = hgmd.REF AND diff.ALT = hgmd.ALT) LEFT JOIN vcfanno on(diff.CHROM = vcfanno.CHROM and diff.POS = vcfanno.START and diff.REF = vcfanno.REF and diff.ALT= vcfanno.ALT) LEFT JOIN sampleinfor sample on(sample.SAMPLE=:child and diff.CHROM = sample.CHROM and diff.POS = sample.POS and diff.REF = sample.REF and diff.ALT = sample.ALT) WHERE diff.SampleId = :child AND diff.FatherId=:father AND diff.MotherId=:mother AND hgmd.CLASS=:class AND vcfanno.Category=:category GROUP BY diff.CHROM,diff.POS";
			SQLQuery query1 = getSession().createSQLQuery(query).addEntity(ThreeSamplePoint.class);
			List<ThreeSamplePoint> points = query1.setString("child", child).setString("father", maleId).setString("mother", femaleId).setString("class", hgmdSelect).setString("category", clinvarSelect).setFirstResult(offset).setMaxResults(perpage).list();
			return points;
		}




	}
	@Transactional
	public BigInteger countChildDiff(String maleId, String femaleId, String child, int perpage, int offset,
			String hgmdSelect, String clinvarSelect) {
		
		SQLQuery sqlQuery;
		if("All".equals(hgmdSelect) && "All".equals(clinvarSelect)) {
			sqlQuery = getSession().createSQLQuery("select count(*) from trioDiff diff where diff.SampleId=:child and diff.FatherId=:father and diff.MotherId=:mother");
			return(BigInteger)sqlQuery.setString("child", child).setString("father", maleId).setString("mother", femaleId).uniqueResult();
		} else if("All".equals(hgmdSelect) && !"All".equals(clinvarSelect)) {
			sqlQuery = getSession().createSQLQuery("select count(*) from trioDiff diff LEFT JOIN vcfanno ON(diff.CHROM = vcfanno.CHROM and diff.POS = vcfanno.START and diff.REF = vcfanno.REF and diff.ALT= vcfanno.ALT) where diff.SampleId=:child and diff.FatherId=:father and diff.MotherId=:mother AND vcfanno.Category=:category");
			return (BigInteger)sqlQuery.setString("child", child).setString("father", maleId).setString("mother", femaleId).setString("category", clinvarSelect).uniqueResult();
		} else if (! "All".equals(hgmdSelect) && "All".equals(clinvarSelect)) {
			sqlQuery = getSession().createSQLQuery("select count(*) from trioDiff diff where diff.SampleId=:child and diff.FatherId=:father and diff.MotherId=:mother AND diff.CLASS=:class");
			return (BigInteger)sqlQuery.setString("child", child).setString("father", maleId).setString("mother", femaleId).setString("class", hgmdSelect).uniqueResult();
		} else {
			sqlQuery = getSession().createSQLQuery("select count(*) from trioDiff diff LEFT JOIN vcfanno ON(diff.CHROM = vcfanno.CHROM and diff.POS = vcfanno.START and diff.REF = vcfanno.REF and diff.ALT= vcfanno.ALT) where diff.SampleId=:child and diff.FatherId=:father and diff.MotherId=:mother AND vcfanno.Category=:category AND diff.CLASS=:class");
			return (BigInteger)sqlQuery.setString("child", child).setString("father", maleId).setString("mother", femaleId).setString("category", clinvarSelect).setString("class", hgmdSelect).uniqueResult();
		}
	}
	
	//handle TrioDiff Analyze old version
/*	@Transactional
	public String handleTrioAnalyze(String child, String father, String mother) {
		//judge if datas exist in table trio with query parameter childId/fatherId/motherId
		String existData = "SELECT count(*) FROM trioDiff WHERE SampleId=:child AND FatherId=:father AND MotherId=:mother LIMIT 1";
		Integer flag = ((BigInteger) getSession().createSQLQuery(existData).setString("child", child).setString("father", father).setString("mother", mother).uniqueResult()).intValue();
		
		if(flag == 0) { //if not exist to take time query and save to trioDiff table
			List<TrioDiff> result = new ArrayList<TrioDiff>();
			String query;
			String[] chrs = {"1","2","3","4","5","6","7","8","9","10","11","12","13","14","15","16","17","18","19","20","21","22","X","Y"};
			for(String chr : chrs) {
				query = "SELECT filterSampleF.CHROM,filterSampleF.POS,filterSampleF.REF,filterSampleF.ALT, round((filterSampleF.AO/(filterSampleF.AO+filterSampleF.RO)),3) AS ChildAF, filterSampleF.parentALT as FatherALT, filterSampleF.fatherAF AS FatherAF, filterSampleM.parentALT AS MotherALT, filterSampleM.motherAF AS MotherAF, com.symbol AS Symbol,\"rs\" AS RS, gno.AF_GNOMAD AS AF_gno_genome,exo.AF_GNOMAD AS AF_gno_exome, 1kg.AF_EAS AS AF_EAS_1kg, esp.AF_ALL AS AF_ALL_esp, exac.EXAC_ALL AS AF_ALL_exac, annovar.SIFT_score AS SIFT_score, annovar.Polyphen2_HDIV_score as Polyphen2_HDIV_score, clinvar.CLNSIG AS CLNSIG_clinvar, hgmd.CLASS AS CLASS, :child AS SampleId, :father AS FatherId, :mother AS MotherId from (select sample.*, father.ALT as parentALT, round((father.AO/(father.AO+father.RO)),3) as fatherAF from sampleinfor sample inner join (select * from sampleinfor where SAMPLE=:father) father on (sample.CHROM=father.CHROM and sample.POS=father.POS and sample.REF=father.REF and sample.ALT !=father.ALT) where sample.SAMPLE=:child AND LENGTH(sample.REF)=1 AND LENGTH(sample.ALT)=1) filterSampleF inner join (select sample.CHROM, sample.POS, sample.REF, sample.ALT, mother.ALT as parentALT,  round((mother.AO/(mother.AO+mother.RO)),3) as motherAF from sampleinfor sample inner join (select * from sampleinfor where SAMPLE=:mother) mother on (sample.CHROM=mother.CHROM and sample.POS=mother.POS and sample.REF=mother.REF and sample.ALT !=mother.ALT) where sample.SAMPLE=:child AND LENGTH(sample.REF)=1 AND LENGTH(sample.ALT)=1) filterSampleM on (filterSampleF.CHROM = filterSampleM.CHROM and filterSampleF.POS = filterSampleM.POS and filterSampleF.REF = filterSampleM.REF and filterSampleF.ALT = filterSampleM.ALT) LEFT JOIN (SELECT ref.chr_name,ref.tx_start,ref.tx_end,homo.symbol FROM refseqs ref,homo_geninfor homo WHERE ref.entrez_id = homo.entrez_id) AS com ON (filterSampleF.CHROM=com.chr_name and filterSampleF.POS > com.tx_start and filterSampleF.POS<com.tx_end)  LEFT JOIN gnochr" + chr + " gno ON (filterSampleF.CHROM = gno.CHROM AND filterSampleF.POS = gno.START AND filterSampleF.REF = gno.REF AND filterSampleF.ALT = gno.ALT) LEFT JOIN exochr" + chr + " exo ON (filterSampleF.CHROM = exo.CHROM AND filterSampleF.POS = exo.START AND filterSampleF.REF = exo.REF AND filterSampleF.ALT = exo.ALT) LEFT JOIN hg38_1kg 1kg ON (filterSampleF.CHROM = 1kg.CHROM AND filterSampleF.POS = 1kg.POS AND filterSampleF.REF = 1kg.REF AND filterSampleF.ALT = 1kg.OBS) LEFT JOIN hg38_esp esp ON (filterSampleF.CHROM = esp.CHROM AND filterSampleF.POS = esp.START AND filterSampleF.POS = esp.END AND filterSampleF.REF = esp.REF AND filterSampleF.ALT = esp.ALT) LEFT JOIN hg38_exac exac ON (filterSampleF.CHROM = exac.CHROM AND filterSampleF.POS = exac.START AND filterSampleF.POS = exac.END AND filterSampleF.REF = exac.REF AND filterSampleF.ALT = exac.ALT) LEFT JOIN hg38_annovar annovar ON (filterSampleF.CHROM = annovar.CHROM AND filterSampleF.POS = annovar.START AND filterSampleF.POS = annovar.END AND filterSampleF.REF = annovar.REF AND filterSampleF.ALT = annovar.ALT) LEFT JOIN clinvar ON (filterSampleF.CHROM = clinvar.CHROM AND filterSampleF.POS = clinvar.START_POINT AND filterSampleF.POS = clinvar.END_POINT AND filterSampleF.REF = clinvar.REF AND filterSampleF.ALT = clinvar.ALT) LEFT JOIN hg38_hgmd hgmd ON(filterSampleF.CHROM=hgmd.CHROM AND filterSampleF.POS = hgmd.POS AND filterSampleF.REF = hgmd.REF AND filterSampleF.ALT = hgmd.ALT) WHERE filterSampleF.CHROM=:chr AND filterSampleF.SAMPLE = :child  GROUP BY filterSampleF.POS";
				SQLQuery query1 = getSession().createSQLQuery(query).addEntity(TrioDiff.class);
				List<TrioDiff> points = query1.setString("chr", "chr"+chr).setString("child", child).setString("father", father).setString("mother", mother).list();
				result.addAll(points);
			}
			
			//save all diff result to trioDiff
			Session session = getSession();
			for(TrioDiff point : result) {
				session.saveOrUpdate(point);
			}
			//session.flush();
			//session.close();
			
			return "Trio Different Compare has been analyzed successfully, you can query in Trio Sample Query";
		} else {
			return "it has been analyzed before this analyze, you can query in Trio Sample Query";
		}
	}*/
	
	//handle trio diff analyze new version
	//TODO
	@Transactional
	public String handleTrioAnalyze(String child, String father, String mother) {
		
		Session session = getSession();
		//judge if datas exist in table trio with query parameter childId/fatherId/motherId
		String existData = "SELECT count(*) FROM trioDiff WHERE SampleId=:child AND FatherId=:father AND MotherId=:mother LIMIT 1";
		Integer flag = ((BigInteger) session.createSQLQuery(existData).setString("child", child).setString("father", father).setString("mother", mother).uniqueResult()).intValue();
		if(flag == 0) { //if not exist to take time query and save to trioDiff table
		String querySample;
		SQLQuery query;

		querySample ="create temporary table if not exists temp_table" + child +"(INDEX my_index_name(CHROM,POS,REF,ALT)) charset=latin1 as (select CHROM, POS, REF, ALT, round((AO/(AO+RO)),3) as AF from sampleinfor where SAMPLE=:sample and length(REF)=1 and length(ALT)=1)";
		query = session.createSQLQuery(querySample);
		query.setString("sample", child).executeUpdate();
		
		querySample ="create temporary table if not exists temp_table" + father +"(INDEX my_index_name(CHROM,POS,REF,ALT)) charset=latin1 as (select CHROM, POS, REF, ALT, round((AO/(AO+RO)),3) as AF from sampleinfor where SAMPLE=:sample and length(REF)=1 and length(ALT)=1)";
		query = session.createSQLQuery(querySample);
		query.setString("sample", father).executeUpdate();
		
		querySample ="create temporary table if not exists temp_table" + mother +"(INDEX my_index_name(CHROM,POS,REF,ALT)) charset=latin1 as (select CHROM, POS, REF, ALT, round((AO/(AO+RO)),3) as AF from sampleinfor where SAMPLE=:sample and length(REF)=1 and length(ALT)=1)";
		query = session.createSQLQuery(querySample);
		query.setString("sample", mother).executeUpdate();
		
		//List<Object> result = getSession().createSQLQuery("select * from temp_table" + child).list();
		
		String childFather = "create temporary table if not exists temp_fatherDiff" + father + "(PRIMARY KEY(CHROM,POS,REF(32),ALT(32))) charset=latin1 as (select child.CHROM,child.POS,child.REF,child.ALT,child.AF as ChildAF,father.ALT as FatherALT,father.AF as FatherAF, null as MotherALT, null as MotherAF,null as SampleId,null as FatherId,null as MotherId from temp_table" + child + " as child,temp_table" + father + " father where(child.CHROM=father.CHROM and child.POS=father.POS and child.REF=father.REF and child.ALT!=father.ALT))";
		session.createSQLQuery(childFather).executeUpdate();
		
		String childMother = "create temporary table if not exists temp_motherDiff" + mother +  "(PRIMARY KEY(CHROM,POS,REF(32),ALT(32))) charset=latin1 as (select child.CHROM,child.POS,child.REF,child.ALT,child.AF as ChildAF,null as FatherALT,null as FatherAF, mother.ALT as MotherALT, mother.AF as MotherAF,null as SampleId,null as FatherId,null as MotherId from temp_table" + child + " as child, temp_table" + mother + " mother where(child.CHROM=mother.CHROM and child.POS=mother.POS and child.REF=mother.REF and child.ALT!=mother.ALT))";
		session.createSQLQuery(childMother).executeUpdate();
		
		List<TrioDiffPoint> results = new ArrayList<TrioDiffPoint>();
		
		String finalQuery = "select father.CHROM,father.POS,father.REF,father.ALT,father.ChildAF,father.FatherALT,father.FatherAF,mother.MotherALT,mother.MotherAF, :child as SampleId, :father as FatherId, :mother as MotherId" + " from temp_fatherDiff" + father + " father inner join temp_motherDiff" + mother + " mother on(father.CHROM=mother.CHROM and father.POS=mother.POS and father.REF=mother.REF)";
		results = session.createSQLQuery(finalQuery).addEntity(TrioDiffPoint.class).setString("child", child).setString("father", father).setString("mother", mother).list();
		
		/*for(TrioDiffPoint fatherPoint : childFatherDiff) {
			fatherChr = fatherPoint.getCHROM();
			fatherPos = fatherPoint.getPOS();
			fatherREF = fatherPoint.getREF();
			
			for(TrioDiffPoint motherPoint : childMotherDiff) {
				if(fatherChr.equals(motherPoint.getCHROM()) &&
				   fatherPos.equals(motherPoint.getPOS()) &&
				   fatherREF.equals(motherPoint.getREF())) {
				
					TrioDiffPoint point = new TrioDiffPoint();
					point.setCHROM(fatherChr);
					point.setPOS(fatherPos);
					point.setREF(fatherREF);
					point.setALT(motherPoint.getALT());
					point.setChildAF(fatherPoint.getChildAF());
					point.setFatherALT(fatherPoint.getFatherALT());
					point.setFatherAF(fatherPoint.getFatherAF());
					point.setMotherALT(motherPoint.getMotherALT());
					point.setMotherAF(motherPoint.getMotherAF());
					point.setSampleId(child);
					point.setFatherId(father);
					point.setMotherId(mother);
					
					results.add(point);
				}
			
				
			}
		}*/
		
		
		/*String query1;
		String[] chrs = {"1","2","3","4","5","6","7","8","9","10","11","12","13","14","15","16","17","18","19","20","21","22","X","Y"};
		for(String chr : chrs) {
			for(TrioDiffPoint point : results) {
				query1 = "SELECT  com.symbol AS Symbol,\"rs\" AS RS, gno.AF_GNOMAD AS AF_gno_genome,exo.AF_GNOMAD AS AF_gno_exome, 1kg.AF_EAS AS AF_EAS_1kg, esp.AF_ALL AS AF_ALL_esp, exac.EXAC_ALL AS AF_ALL_exac, annovar.SIFT_score AS SIFT_score, annovar.Polyphen2_HDIV_score as Polyphen2_HDIV_score, clinvar.CLNSIG AS CLNSIG_clinvar, hgmd.CLASS AS CLASS, :child AS SampleId, :father AS FatherId, :mother AS MotherId from (SELECT ref.chr_name,ref.tx_start,ref.tx_end,homo.symbol FROM refseqs ref,homo_geninfor homo WHERE ref.entrez_id = homo.entrez_id) AS com ON (com.chr_name=:chr and :pos > com.tx_start and :pos < com.tx_end)  LEFT JOIN gnochr" + chr + " gno ON (gno.CHROM=:chr AND :pos = gno.START AND :ref = gno.REF AND :alt = gno.ALT) LEFT JOIN exochr" + chr + " exo ON (:chr = exo.CHROM AND :pos = exo.START AND :ref = exo.REF AND :alt = exo.ALT) LEFT JOIN hg38_1kg 1kg ON (:chr = 1kg.CHROM AND filterSampleF.POS = 1kg.POS AND filterSampleF.REF = 1kg.REF AND filterSampleF.ALT = 1kg.OBS) LEFT JOIN hg38_esp esp ON (filterSampleF.CHROM = esp.CHROM AND filterSampleF.POS = esp.START AND filterSampleF.POS = esp.END AND filterSampleF.REF = esp.REF AND filterSampleF.ALT = esp.ALT) LEFT JOIN hg38_exac exac ON (filterSampleF.CHROM = exac.CHROM AND filterSampleF.POS = exac.START AND filterSampleF.POS = exac.END AND filterSampleF.REF = exac.REF AND filterSampleF.ALT = exac.ALT) LEFT JOIN hg38_annovar annovar ON (filterSampleF.CHROM = annovar.CHROM AND filterSampleF.POS = annovar.START AND filterSampleF.POS = annovar.END AND filterSampleF.REF = annovar.REF AND filterSampleF.ALT = annovar.ALT) LEFT JOIN clinvar ON (filterSampleF.CHROM = clinvar.CHROM AND filterSampleF.POS = clinvar.START_POINT AND filterSampleF.POS = clinvar.END_POINT AND filterSampleF.REF = clinvar.REF AND filterSampleF.ALT = clinvar.ALT) LEFT JOIN hg38_hgmd hgmd ON(filterSampleF.CHROM=hgmd.CHROM AND filterSampleF.POS = hgmd.POS AND filterSampleF.REF = hgmd.REF AND filterSampleF.ALT = hgmd.ALT) WHERE filterSampleF.CHROM=:chr AND filterSampleF.SAMPLE = :child  GROUP BY filterSampleF.POS";
				SQLQuery query1 = getSession().createSQLQuery(query).addEntity(TrioDiff.class);
			}
			
			List<TrioDiff> points = query1.setString("chr", "chr"+chr).setString("child", child).setString("father", father).setString("mother", mother).list();
			result.addAll(points);
		}*/
		
		//save all diff result to trioDiff
		//session = getSessionFactory().openSession();
		session = getSessionFactory().openSession();
		for(TrioDiffPoint point : results) {
			session.save(point);
		}
		//TODO
		session.flush();
		
			return "Trio Different Compare has been analyzed successfully, you can query in Trio Sample Query";
		} else {
			return "it has been analyzed before this analyze, you can query in Trio Sample Query";
		}
		
	}
	
	public void doStore(final BufferedReader br) {
		
		final String sql = "INSERT INTO sampleinfor(CHROM ,POS ,ID ,REF  ,ALT  ,QUAL  ,FILTER ,AB  ,ABP  ,AC  ,AF  ,AN  ,CIGAR  ,DPB  ,DPRA  ,EPP  ,EPPR  ,GTI  ,LEN  ,MEANALT  ,MQM  ,MQMR  ,NS  ,NUMALT  ,ODDS  ,PAIRED  ,PAIREDR  ,PAO  ,PQA  ,PQR  ,PRO  ,RPL  ,RPP  ,RPPR  ,RPR  ,RUN  ,SAF  ,SAP  ,SAR  ,SRF  ,SRP  ,SRR  ,TYPE  ,GT  ,DP  ,RO  ,QR  ,AO  ,QA  ,GL  ,SAMPLE ) VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
			
			Session session = getSessionFactory().openSession();
			session.doWork(
					new Work() {
						
						public void execute(Connection conn) throws SQLException {
							PreparedStatement stm = null;
							
							try {
								ArrayList<String> single;
								
								stm = conn.prepareStatement(sql);
								
								conn.setAutoCommit(false);
								
								//去除不相关信息
								String line;
								while(!(line = br.readLine()).contains("#CHROM	POS	ID	REF	ALT	QUAL	FILTER	INFO	FORMAT"))
									continue;
								//对待第一行取SAMPLE
								String[] first = line.split("\t");
								String sample = first[first.length-1];
								int count = 0;
								while((line = br.readLine()) != null) {
									
									 single= separateData(line);
									 
									 stm.setString(1, single.get(0));
									 stm.setInt(2, Integer.valueOf(single.get(1)));
									 
									 for(int i=2;i<single.size();i++){
										 stm.setString(i+1, single.get(i));
									 }
 									 stm.setString(51, sample);//最后一列ID
									 
									 stm.addBatch();//加入批处理
									 if(count ++ > 5000) {
										 stm.executeBatch(); //每5000条执行一次批处理
										 conn.commit();
										 count = 0;
									 }
										 
								}
								stm.executeBatch(); //最后再执行一次批处理
								conn.commit();
								conn.close();
								
							} catch (IOException e) {
								e.printStackTrace();
							} finally {
								stm.close();
								conn.close();
								
							}
						}
						
						//对每一行进行处理
						private  ArrayList<String> separateData(String string) {
							ArrayList<String> result = new ArrayList<String>();
							String[] strings = string.split("\t");
							for(int i=0; i<7;i++){ //添加CHROM到FILTER
								result.add(strings[i]);
							}
							
							//对INFO行做特殊处理
							String seven = strings[7];
							String[] result1 = seven.split(";");
							HashMap<String, String> map = new HashMap<String,String>();
							for(String string2: result1){
								String key = string2.split("=")[0];
								String value = string2.split("=")[1];
								map.put(key, value);
							}
							String[] columns = {"AB","ABP","AC","AF","AN","CIGAR","DPB","DPRA","EPP","EPPR","GTI","LEN","MEANALT","MQM","MQMR","NS","NUMALT","ODDS","PAIRED","PAIREDR","PAO","PQA","PQR","PRO","RPL","RPP","RPPR","RPR","RUN","SAF","SAP","SAR","SRF","SRP","SRR","TYPE"};
							for(String key: columns)
								result.add(map.get(key));
							
							//对FORMAT行进行特殊处理
							String eight = strings[9];
							String[] result2 = eight.split(":");
							for(String string3: result2){
								result.add(string3);
							}
							
							return result;
							
						}
					});
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
