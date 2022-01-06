
		var ajaxi18n = (url ,  formData, step2  ) => {
		    ajaxPromise({
			     "type":"post",
			      "url": url,
			      "data": formData
			    }).then(res => {
			        console.log("第一个请求正确返回==>"+res); 
			        var obj = JSON.parse(res);
			        var value = obj.respData.data;
			        step2(value); 
			        
			    }).catch(err => {
			        console.log("第一个请求失败");  
			    })
		};
		const ajaxPromise=  param => {
				  return new Promise((resovle, reject) => {
				    $.ajax({
				      "type":param.type || "get",
				      "async":param.async || true,
				      "url":param.url,
				      "data":param.data || "",
				      "success": res => {
				        resovle(res);
				      },
				      "error": err => {
				        reject(err);
				      }
				    })
				  }
			)};

		function retrivalLan(){
			let url = i18nMsg.ctx  +"/langList";
			$.ajax({
				type : "GET",
				url : url,
				async: true,
				cache:false,
				success : function(data) {
					var obj = JSON.parse(data);
			        var value = obj.respData.langList;
			        //从后端捞出语系选单，塞入select里面
			        value.forEach(function(element){
			        	var option = new Option(element.paramValue, element.paramId); 
			        	$('#lang-selector').append($(option));
			        });
			        //去COOKIE捞，让选单显示现正使用语系
			        var cookieLan = "";
					var cookie =document.cookie;
					
				 	if(cookie.indexOf('lan')!= -1){
						cookieLan = cookie.substring(cookie.indexOf('lan')+4,cookie.indexOf('lan')+9);
						var items = cookieLan.split("_");
						cookieLan = items[0] + "_" + items[1].toUpperCase();
						$('#lang-selector').val(cookieLan);
						//console.log("都会进来!!从COOKIES捞的语系= " + cookieLan); 
					}
				 	//原本是从request捞，但登出后会捞不到，改从用COOKIES捞 
//			        if(i18nMsg.usedLan.length != 0){
//			   		  $('#lang-selector').val(i18nMsg.usedLan);
//			        }
				 	
				},
				error:function(){
			    	console.log("系统异常，请联系管理员！");
			    	return  false;
			    }
			});
		}
		
		$(function() {
			// initLanSelector
			retrivalLan();
			//换语系的话，触发的相关功能
			$( "#lang-selector" ).change(function() {
				let langSelect = $(this).val();
			    let ur = window.location.href.split("?")[0]+"?lan="+langSelect;
			    var formData = {lan:langSelect}; //Array 
			    
			  	let url = i18nMsg.ctx  +"/updateI18n";
				
			    ajaxi18n(url , formData , function(value){
			    	//如果网址含有i18 (使用网址选择语系)，又要使用选单变更的话
			    	//更改回原网址，才不会一直吃到网址中的语系
			    	if(window.location.href.indexOf('i18n') != -1){
			    		var url = window.location.href.split("/i18n")[0];
			    		window.location = url;
			    	}else{
			    		window.location.reload(); 
			    	}
			  	 });
			   
			});
		});