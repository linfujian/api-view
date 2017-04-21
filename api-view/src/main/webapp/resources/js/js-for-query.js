$('#range_query').click(function() {
	$.ajax({
		type:'GET',
		dataType:'json',
		url:'range',
		data: {
			"sample": $('#input_sample').val(),
			"chr": 'chr' + $('#range_chr').val(),
			"start": $('#range_start').val(),
			"end": $('#range_end').val()
		},
		
		success: function(msg) {
			successCallBack(msg);
		},
		
		error: function(errorMsg) {
			errorCallBack(errorMsg);
		}
	});
});	

$('#symbol_query').click(function() {
	$.ajax({
		type:'GET',
		dataType:'json',
		url:'symbol',
		data:{
			"symbol":$('#symbol').val(),
			"sample":$('#input_sample').val()
		},
		
		success: function(msg) {
			successCallBack(msg);
		},
		
		error: function(errorMsg) {
			errorCallBack(errorMsg);
		}
	});
});

$('#nm_query').click(function() {
	$.ajax({
		type:'GET',
		dataType:'json',
		url:'nm',
		data:{
			"sample":$('#input_sample').val(),
			"nm":$('#nm').val(),
		},
		
		success: function(msg) {
			successCallBack(msg);
		},
		
		error: function(errorMsg) {
			errorCallBack(errorMsg);
		}
	});
});


function successCallBack(msg) {
	if(msg=='') {
		$('#showResult').html("no data from database returned");
	} else {
		$('.queryPage').hide();
		
		var content = '<table class=\"table table-condensed\"><thead><tr><th>#</th><th style=\"text-align:center;\" colspan=\"4\">Site Information</th><th style=\"text-align:center;\" colspan=\"5\">Frequency</th><th  style=\"text-align:center;\" colspan=\"2\">Clinical</th></tr><tr><th>N.O</th><th>CHROM</th><th>POS</th><th>REF</th><th>ALT</th><th>gno_genome</th><th>gno_exomes</th><th>1kg</th><th>esp</th><th>exac</th><th>SIFT_score/Polyphen2_HDIV_score</th><th>clinvar</th></tr></thead><tbody>';
		
		$.each(msg, function(index,item){
			var param = '\'' + item.CHROM + '\',\'' + item.POS + '\',\'' + item.REF + '\',\'' + item.ALT + '\'';
			content += '<tr><td>' +  (index+1) + '</td><td>' + item.CHROM + '</td><td>' + item.POS + '</td><td>' + item.REF + '</td><td>' + item.ALT + '</td><td><a href=\"#\" onclick=\"sendAjax(\'gno_gen\',' + param + ')\">' + item.AF_gno_genome + '</a></td><td><a href=\"#\" onclick=\"sendAjax(\'gno_exo\',' + param + ')\">' +item.AF_gno_exome + '</a></td><td><a href=\"#\" onclick=\"sendAjax(\'onekg\',' + param + ')\">' + item.AF_EAS_1kg + '</a></td><td><a href=\"#\" onclick=\"sendAjax(\'esp\',' + param + ')\">' + item.AF_ALL_esp + '</a></td><td><a href=\"#\" onclick=\"sendAjax(\'exac\',' + param + ')\">' + item.AF_ALL_exac + '</a></td><td><a href=\"#\" onclick=\"sendAjax(\'annovar\',' + param + ')\">' + item.SIFT_score + '/' + item.Polyphen2_HDIV_score + '</a></td><td><a href=\"#\" onclick=\"sendAjax(\'clinvar\',' + param + ')\">' + item.CLNSIG_clinvar + '</a></td></tr>';
			
		});
		
		content += '</tbody></table>';
		
		$('#showResult').html(content);
		
		$('a').each(function(){
			 if($(this).text() == 'undefined' || $(this).text() == 'undefined/undefined') {
				 $(this).parent().html("");
			} 	
		});
	}
}

function errorCallBack(errorMsg) {
	var msg = '';
	if(errorMsg.status=== 500) {
		msg = 'Internal Server error';
	}
	
	$('#showResult').html(msg);
}
	
function sendAjax(type,chr,pos,ref,alt) {
	var url = type;
	$.get(type + '/' + chr + '/' + pos + '/' + ref + '/' + alt, function(result){
		if(type=='gno_gen') {
			$('#gno_gen_detail').html(result);
			$('#gno_gen_detail').dialog("option","title","Gno Genomes");
			$('#gno_gen_detail').dialog('open');
		}
		
		if(type=='gno_exo') {
			$('#gno_exo_detail').html(result);
			$('#gno_exo_detail').dialog("option","title","Gno Exomes");
			$('#gno_exo_detail').dialog('open');
		}
		
		if(type=='onekg') {
			$('#onekg_detail').html(result);
			$('#onekg_detail').dialog("option","title","1Kg");
			$('#onekg_detail').dialog('open');
		}
		
		if(type=='esp') {
			$('#esp_detail').html(result);
			$('#esp_detail').dialog("option","title","Esp");
			$('#esp_detail').dialog('open');
		}
		if(type=='exac') {
			$('#exac_detail').html(result);
			$('#exac_detail').dialog("option","title","Exac");
			$('#exac_detail').dialog('open');
		}
		if(type=='annovar') {
			$('#annovar_detail').html(result);
			$('#annovar_detail').dialog("option","title","Annovar");
			$('#annovar_detail').dialog('open');
		}
		if(type=='clinvar') {
			$('#clinvar_detail').html(result);
			$('#clinvar_detail').dialog("option","title","Clinvar");
			$('#clinvar_detail').dialog('open');
		}
	});
}

function sendRange() {
	if($('#input_sample').val() != '') {
		$('#query_select').hide();
		$('#queryPage_range').show();
	}
}

function sendSymbol() {
	if($('#input_sample').val() != '') {
		$('#query_select').hide();
		$('#queryPage_symbol').show();
	}
}

function sendNM() {
	if($('#input_sample').val() != '') {
		$('#query_select').hide();
		$('#queryPage_nm').show();
	}
}
	
$(document).ready(function(){
	
	$('#queryPage_range').hide();
	$('#queryPage_symbol').hide();
	$('#queryPage_nm').hide();
	
	$('.dialog').dialog({
		autoOpen : false,
        my: "center",
        at: "center",
        of: window,
		modal : true,
		resizable : false,
		width : 300,
		height:600,
		close : function() {
			//resetDialog($('#paperForm'));
			$(this).dialog('close');
		}
	});
});