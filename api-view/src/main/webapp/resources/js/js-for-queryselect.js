$(document).ready(function() {
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
	
	function getRadio(select) {
		var content;
		if(select == 'RangeQuery') {
			
			content = "<div id=\"queryPage_range\" class=\"queryPage\"><form class=\"form-horizontal\" id=\"range_form\"><div class=\"form-group\"><label for=\"inputChr\" class=\"col-sm-2 control-label\">Chr</label><div class=\"col-sm-5\"><input type=\"text\" class=\"form-control\" id=\"range_chr\" placeholder=\"Chro\" /></div></div><div class=\"form-group\"><label for=\"inputStart\" class=\"col-sm-2 control-label\">Start</label><div class=\"col-sm-5\"><input type=\"text\" class=\"form-control\" id=\"range_start\" placeholder=\"Start point\" /></div></div><div class=\"form-group\"><label for=\"inputEnd\" class=\"col-sm-2 control-label\">End</label><div class=\"col-sm-5\"><input type=\"text\" class=\"form-control\" id=\"range_end\" placeholder=\"End point\" /></div></div></form><div class=\"col-sm-offset-2 col-sm-10\"><button class=\"btn btn-default\" id=\"range_query\">Query</button></div></div>";
			$('#queryInput').html(content);
			
		} else if(select == 'SymbolQuery') {
			
			content = "<div id=\"queryPage_symbol\" class=\"queryPage\"><form class=\"form-horizontal\" id=\"symbol_form\"><div class=\"form-group\"><label for=\"inputSymbol\" class=\"col-sm-2 control-label\">Symbol</label><div class=\"col-sm-5\"><input type=\"text\" class=\"form-control\" id=\"symbol\" placeholder=\"Symbol\" /></div></div></form><div class=\"col-sm-offset-2 col-sm-10\"><button class=\"btn btn-default\" id=\"symbol_query\">Query</button></div> </div>";
			$('#queryInput').html(content);
			
		} else if(select == 'NMQuery') {
			
			content = "<div id=\"queryPage_nm\" class=\"queryPage\"><form class=\"form-horizontal\" id=\"nm_form\"><div class=\"form-group\"><label for=\"inpuSymbol\" class=\"col-sm-2 control-label\">NM</label><div class=\"col-sm-5\"><input type=\"text\" class=\"form-control\" id=\"nm\" placeholder=\"NM\" /></div></div></form><div class=\"col-sm-offset-2 col-sm-10\"><button class=\"btn btn-default\" id=\"nm_query\">Query</button></div> </div>";
			$('#queryInput').html(content);
			
		}
	}
	
	$(document).on('click','#range_query',function() {
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

	$(document).on('click', '#symbol_query', function() {
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

	$(document).on('click', '#nm_query', function() {
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
			$('#showresult').html("no data from database returned");
		} else {
			
			var content = '<table class=\"table table-condensed\"><thead><tr><th>#</th><th style=\"text-align:center;\" colspan=\"4\">Site Information</th><th style=\"text-align:center;\" colspan=\"5\">Frequency</th><th  style=\"text-align:center;\" colspan=\"2\">Clinical</th></tr><tr><th>N.O</th><th>CHROM</th><th>POS</th><th>REF</th><th>ALT</th><th>gno_genome</th><th>gno_exomes</th><th>1kg</th><th>esp</th><th>exac</th><th>SIFT_score/Polyphen2_HDIV_score</th><th>clinvar</th></tr></thead><tbody>';
			
			$.each(msg, function(index,item){
				var param = '\'' + item.CHROM + '\',\'' + item.POS + '\',\'' + item.REF + '\',\'' + item.ALT + '\'';
				content += '<tr><td>' +  (index+1) + '</td><td>' + item.CHROM + '</td><td>' + item.POS + '</td><td>' + item.REF + '</td><td>' + item.ALT + '</td><td><a href=\"#\" onclick=\"sendAjax(\'gno_gen\',' + param + ')\">' + item.AF_gno_genome + '</a></td><td><a href=\"#\" onclick=\"sendAjax(\'gno_exo\',' + param + ')\">' +item.AF_gno_exome + '</a></td><td><a href=\"#\" onclick=\"sendAjax(\'onekg\',' + param + ')\">' + item.AF_EAS_1kg + '</a></td><td><a href=\"#\" onclick=\"sendAjax(\'esp\',' + param + ')\">' + item.AF_ALL_esp + '</a></td><td><a href=\"#\" onclick=\"sendAjax(\'exac\',' + param + ')\">' + item.AF_ALL_exac + '</a></td><td><a href=\"#\" onclick=\"sendAjax(\'annovar\',' + param + ')\">' + item.SIFT_score + '/' + item.Polyphen2_HDIV_score + '</a></td><td><a href=\"#\" onclick=\"sendAjax(\'clinvar\',' + param + ')\">' + item.CLNSIG_clinvar + '</a></td></tr>';
				
			});
			
			content += '</tbody></table>';
			
			$('#showresult').html(content);
			
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
		
		$('#showresult').html(msg);
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