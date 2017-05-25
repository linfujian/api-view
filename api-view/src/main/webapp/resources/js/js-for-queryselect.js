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
		
		$('#history_detail').dialog({
			autoOpen : false,
	        my: "center",
	        at: "center",
	        of: window,
			modal : true,
			resizable : false,
			width:900,
			height:300,
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
		$('#queryType').val('range_query');
		$.ajax({
			type:'GET',
			dataType:'json',
			url:'range',
			data: {
				"sample": $('#input_sample').val(),
				"chr": $('#range_chr').val(),
				"start": $('#range_start').val(),
				"end": $('#range_end').val(),
				"offset":0,
				"maxResults":20,
				"varAnno":$("#groupVarAnno").val(),
				"hgmd":$("#groupHGMD").val()
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
		$('#queryType').val('symbol_query');
		$.ajax({
			type:'GET',
			dataType:'json',
			url:'symbol',
			data:{
				"symbol":$('#symbol').val(),
				"sample":$('#input_sample').val(),
				"offset":0,
				"maxResults":20,
				"varAnno":$("#groupVarAnno").val(),
				"hgmd":$("#groupHGMD").val()
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
		$('#queryType').val('nm_query');
		$.ajax({
			type:'GET',
			dataType:'json',
			url:'nm',
			data:{
				"sample":$('#input_sample').val(),
				"nm":$('#nm').val(),
				"offset":0,
				"maxResults":20,
				"varAnno":$("#groupVarAnno").val(),
				"hgmd":$("#groupHGMD").val()
			},
			
			success: function(msg) {
				successCallBack(msg);
			},
			
			error: function(errorMsg) {
				errorCallBack(errorMsg);
			}
		});
	});
	
	function constructTable(msg) {
		var varAnnoGroupType = $("#groupVarAnno").val();
		var hgmdType = $("#groupHGMD").val();
		var content = '<table class=\"table table-condensed\"><thead><tr><th>#</th><th style=\"text-align:center;\" colspan=\"6\">Site Information</th><th style=\"text-align:center;\" colspan=\"5\">Frequency</th><th  style=\"text-align:center;\" colspan=\"2\">Clinical</th><th>HGMD</th><th style=\"text-align:center;\" colspan=\"4\">VarAnno</th><th style=\"text-align:center;\">Report?</th></tr><tr><th>N.O</th><th>CHROM</th><th>POS</th><th>REF</th><th>ALT</th><th>RS</th><th>Symbol</th><th>gno_genome</th><th>gno_exomes</th><th>1kg</th><th>esp</th><th>exac</th><th>SIFT_score/Polyphen2_HDIV_score</th><th>clinvar</th><th><select id=\"groupHGMD\"><option selected>' + hgmdType + '</option><option value=\"ALL\">ALL</option><option value=\"DM\">DM</option><option value=\"DM?\">DM?</option><option value=\"DP\">DP</option><option value=\"FP\">FP</option><option value=\"DFP\">DFP</option><option value=\"R\">R</option></select></th><th>Group by<select id=\"groupVarAnno\"><option selected>' + varAnnoGroupType + '</option><option value=\"ALL\">ALL</option><option value=\"Benign\">Benign</option><option value=\"Likely Benign\">Likely Benign</option><option value=\"Vus\">Vus</option><option value=\"Likely Path\">Likely Path</option><option value=\"Path\">Path</option><option value=\"Other\">Other</option></select></th><th>Comments</th><th>VarAnnoDetail</th><th>History</th><th></th></tr></thead><tbody>';
		
		$.each(msg.list, function(index,item){
			var param = '\'' + item.CHROM + '\',\'' + item.POS + '\',\'' + item.REF + '\',\'' + item.ALT + '\'';
			content += '<tr><td><a href=\"genomebrowse:/api/zoom?locus=' + item.CHROM + ':' + item.POS + '-' + item.POS + '\">' +  (index+1) + '</a></td><td><input type=\"hidden\" name=\"varAnnoPoints[' + index +'].CHROM\" value=\"' + item.CHROM + '\" />' + item.CHROM + '</td><td><input type=\"hidden\" name=\"varAnnoPoints[' + index +'].START\" value=\"' + item.POS + '\" /><input type=\"hidden\" name=\"varAnnoPoints[' + index + '].END\" value=\"' + item.POS + '\" />' + item.POS + '</td><td><input type=\"hidden\" name=\"varAnnoPoints[' + index +'].REF\" value=\"' + item.REF + '\" />' + item.REF + '</td><td><input type=\"hidden\" name=\"varAnnoPoints[' + index +'].ALT\" value=\"' + item.ALT + '\" />' + item.ALT + '</td><td>' + item.AF + '</td><td><input type=\"hidden\" name=\"varAnnoPoints[' + index +'].RS\" value=\"' + item.RS + '\" /><a style=\"color:blue;\">' + item.RS +'</a></td><td><input type=\"hidden\" name=\"varAnnoPoints[' + index +'].GeneSymbol\" value=\"' + item.Symbol + '\" /><a style=\"color:red;\">' + item.Symbol + '</a></td><td><a href=\"#\" onclick=\"sendAjax(\'gno_gen\',' + param + ')\">' + item.AF_gno_genome + '</a></td><td><a href=\"#\" onclick=\"sendAjax(\'gno_exo\',' + param + ')\">' +item.AF_gno_exome + '</a></td><td><a href=\"#\" onclick=\"sendAjax(\'onekg\',' + param + ')\">' + item.AF_EAS_1kg + '</a></td><td><a href=\"#\" onclick=\"sendAjax(\'esp\',' + param + ')\">' + item.AF_ALL_esp + '</a></td><td><a href=\"#\" onclick=\"sendAjax(\'exac\',' + param + ')\">' + item.AF_ALL_exac + '</a></td><td><a href=\"#\" onclick=\"sendAjax(\'annovar\',' + param + ')\">' + item.SIFT_score + '/' + item.Polyphen2_HDIV_score + '</a></td><td><a href=\"#\" onclick=\"sendAjax(\'clinvar\',' + param + ')\">' + item.CLNSIG_clinvar +
			'</a></td><td><a href=\"\#" onclick=\"sendAjax(\'hgmd\',' + param +')\">' + item.CLASS + '</td><td><select name=\"varAnnoPoints[' + index + '].Category\" class=\"selectpicker\"><option selected>' + item.Category + '</option><option>Benign</option><option>Likely Benign</option><option>Vus</option><option>Likely Path</option><option>Path</option><option>Other</option></select></td><td><input name=\"varAnnoPoints[' + index + '].Comments\" type=\"text\" maxlength=\"128\" value=\"' + item.Comments + '\" /></td><td><a href=\"#\" onclick=\"sendAjax(\'varAnnoDetail\',' + param + ')\">detail</a></td><td><button type=\"button\" class=\"btn btn-info\" onclick=\"sendAjax(\'history\',' + param + ')\">History</button></td><td><select name=\"varAnnoPoints[' + index + '].report\" class=\"selectpicker\"><option selected>' + item.REPORT + '</option><option>' + (item.REPORT=='no'? 'yes':'no') + '</option></select></td></tr>';
			
		});
		
		content += '</tbody></table><br/><button id=\"submit\" class=\"col-sm-offset-10 btn btn-danger\" >Save The Update</button>';
		
		$('#showresult').html(content);
		
		$('a').each(function(){
			 if($(this).text() == 'undefined' || $(this).text() == 'undefined/undefined') {
				 $(this).parent().html("");
			} 	
		});
		
		$('option').each(function(){
			 if($(this).text() == 'undefined') {
				 $(this).html("pl choose");
			} 	
		});
		
		$('input').each(function(){
			 if($(this).val() == 'undefined') {
				 $(this).val("");
			} 	
		});
	}


	function successCallBack(msg) {
		if(msg=='') {
			$('#showresult').html("no data from database returned");
		} else {
			constructTable(msg);
			$('#page').val(msg.page);
	
			$('#page-selection').empty();
			$('#page-selection').bootpag({
				total: Math.ceil($('#page').val()/20),
				page:1,
				maxVisible:10
			});
			
		}
	}
	
	$('#page-selection').bootpag({
		total: Math.ceil($('#page').val()/20),
		page:1,
		maxVisible:10
	}).on('page',function(event,num){
		var queryType = $('#queryType').val();
		switch(queryType) {
			case 'range_query':
				sendAjaxForPage('range',
						{
					"sample": $('#input_sample').val(),
					"chr": $('#range_chr').val(),
					"start": $('#range_start').val(),
					"end": $('#range_end').val(),
					"offset":(num-1)*20,
					"maxResults":20,
					"varAnno":$('#groupVarAnno').val(),
					"hgmd":$("#groupHGMD").val()
				});
			break;
			case 'symbol_query':
				sendAjaxForPage('symbol',
						{
					"symbol":$('#symbol').val(),
					"sample":$('#input_sample').val(),
					"offset":(num-1)*20,
					"maxResults":20,
					"varAnno":$('#groupVarAnno').val(),
					"hgmd":$("#groupHGMD").val()
						});
			break;
			case 'nm_query':
				sendAjaxForPage('nm',
						{
					"sample":$('#input_sample').val(),
					"nm":$('#nm').val(),
					"offset":(num-1)*20,
					"maxResults":20,
					"varAnno":$('#groupVarAnno').val(),
					"hgmd":$("#groupHGMD").val()
						});
			break;
		}
	});

	function errorCallBack(errorMsg) {
		var msg = '';
		if(errorMsg.status=== 500) {
			msg = 'Internal Server error';
		}
		
		$('#showresult').html(msg);
	}
	
	function sendAjaxForPage(type,dataArray) {
		$.ajax({
			type:'GET',
			dataType:'json',
			url: type,
			data:dataArray,

			success: function(msg) {
				constructTable(msg);
			},
			
			error: function(errorMsg) {
				errorCallBack(errorMsg);
			}
		});
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
			if(type=='varAnnoDetail') {
				$('#varAnno_detail').html(result);
				$('#varAnno_detail').dialog("option","title","VarAnno");
				$('#varAnno_detail').dialog('open');
			}
			//query history
			if(type=='history') {
				$('#history_detail').html(result);
				$('#history_detail').dialog("option","title","VarAnnoHistory");
				$('#history_detail').dialog('open');
			}
			
			//query hgmd
			if(type=='hgmd') {
				$('#hgmd_detail').html(result);
				$('#hgmd_detail').dialog("option","title","HGMD");
				$('#hgmd_detail').dialog('open');
			}
		});
	}
	
	$(document).on("click","#submit",function(e) {
		e.preventDefault();
		$.ajax({
			type:'POST',
			url:'batchupdate/'+ $('#input_sample').val(),
			data: $('form').serialize(),
			success: function(msg) {
				alert(msg);
			}
		});
	});
	/*function update() {
		
		$('#pointForm').submit(function(e) {
			e.preventDefault();
			$.ajax({
				type:'POST',
				url:'batchupdate',
				data: $('form').serialize(),
				success: function(msg) {
					alert(msg);
				}
			});
			
		});
	}*/
	$(document).on('change','#groupVarAnno',function(){
		
		if($('#queryType').val() == 'range_query'){
			$('#range_query').trigger('click');
		}
		if($('#queryType').val() == 'symbol_query'){
			$('#symbol_query').trigger('click');
		}
		if($('#queryType').val() == 'nm_query'){
			$('#nm_query').trigger('click');
		}
	});
	
	$(document).on('change','#groupHGMD',function(){
		if($('#queryType').val() == 'range_query'){
			$('#range_query').trigger('click');
		}
		if($('#queryType').val() == 'symbol_query'){
			$('#symbol_query').trigger('click');
		}
		if($('#queryType').val() == 'nm_query'){
			$('#nm_query').trigger('click');
		}
	});
