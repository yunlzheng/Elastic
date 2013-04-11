var seriousCount = 0;
var generalCount = 0;
var seriousFaultCount = 0;
var generalFaultCount = 0;

$.ajaxSetup({
	async : false
});

var options = {
	offset:0,
	length:10
}

var data_result = new Array();

var sendAjaxRequest = function(success, error) {
	/**
	 * 如果size为空则获取所有的记录
	 */
	var url = "../rest/exception/"+options.offset+"/"+options.length;
	
	$.ajax({

		type : "get",
		url : url,
		success : success,
		error : error,
		timeOut : 3000

	});

}

function successRequest(data) {

	options.offset = options.offset+options.length;
	
	data_result = data_result.concat(data);
	
	//data = toArray(data);

	$("#tbody").empty();
	for ( var i = 0; i < data_result.length; i++) {

		/**
		 * 添加表格
		 */
		var keepTime = (Date.parse(data_result[i]["endTime"]) - Date
				.parse(data_result[i]["startTime"]))
				/ (3600 * 1000 * 24);

		var tr_1 = "<tr><td>"+(i+1)+"</td><td>" + data_result[i]["name"] + "</td><td>"
				+ data_result[i]["startTime"] + "</td><td>" + data_result[i]["endTime"]
				+ "</td><td>" + keepTime + "*24h</td><td>" + data_result[i]["message"];
		var tr_3 = "<td></td></tr>";
		var tr_2 = "";
		var tr = "";

		if (data_result[i]["level"] == 1) {
			seriousCount++;
			tr_2 = "</td><td>严重异常</td>";

		} else if (data_result[i]["level"] == 2) {
			generalCount++;
			tr_2 = "</td><td>一般异常</td>";
		} else if (data_result[i]["level"] == 3) {
			seriousFaultCount++;
			tr_2 = "</td><td>严重错误</td>";
		} else if (data_result[i]["level"] == 4) {
			generalFaultCount++;
			tr_2 = "</td><td>一般错误</td>";
		}

		tr = tr_1 + tr_2 + tr_3;
		$("#tbody").append(tr);

	}

	$("#select_type")
			.change(
					function() {

						var $select_level = $("#select_level");
						$select_level.attr("attrib", "display");
						var $select_server = $("#select_server");
						$select_server.attr("attrib", "display");
						var $select_app = $("#select_app");
						$select_app.attr("attrib", "display");

						var mode = $("#select_type").find("option:selected")
								.attr("mode");

						if (mode == "select_app_exc") {

							$select_app.find("option:gt(0)").remove();
							$select_app.removeAttr("attrib");

							appendSelect(data_result, "select_app");

							$select_app
									.change(function() {

										$mode1 = $select_app.find(
												"option:selected").attr("mode");
										var name = ($select_app
												.find("option:selected"))
												.attr("name");

										/**
										 * 选择服务器异常等级
										 */
										$select_level.removeAttr("attrib");

										if ($select_app.find("option:selected")
												.attr("check") == "allapp") {

											$select_level.attr("attrib",
													"display");
											checkAllServer(data_result, "allapp");
										}

										$select_level
												.change(function() {
													if ($select_level.find(
															"option:selected")
															.attr("check") == "allexc") {
														var serverName = $select_app
																.find(
																		"option:selected")
																.attr("name");

														checkAllServer(data_result,
																serverName,
																"app");
													} else {
														appendToBody(data_result, "",
																"", name,
																"application")
													}
												});

									});
						}

						if (mode == "select_server_exc") {

							$select_server.find("option:gt(0)").remove();
							$select_server.removeAttr("attrib");
							appendSelect(data_result, "select_server");
							$select_server
									.change(function() {

										$mode1 = $select_server.find(
												"option:selected").attr("mode");
										var name = ($select_server
												.find("option:selected"))
												.attr("name");

										/**
										 * 选择服务器异常等级
										 */
										$select_level.removeAttr("attrib");

										if ($select_server.find(
												"option:selected")
												.attr("check") == "allserver") {
											$select_level.attr("attrib",
													"display");

											checkAllServer(data_result, "servers");
										}
										$select_level
												.change(function() {

													if ($select_level.find(
															"option:selected")
															.attr("check") == "allexc") {
														var serverName = $select_server
																.find(
																		"option:selected")
																.attr("name");
														checkAllServer(data_result,
																serverName,
																"server");
													} else {
														appendToBody(data_result, "",
																"", name,
																"server")
													}

												});

									});

						}

						if (mode == "select_all") {
							appendToBody(data_result, mode, "");
							$select_level.change(function() {

								var mode1 = $select_level.find(
										"option:selected").attr("mode");
								appendToBody(data_result, mode, mode1);

							});
						}

					});

	// 日期选择
	var date = new Date();
	var start_date = date.getMonth() + "/" + date.getDay() + "/"
			+ date.getFullYear();

	var start = new choosedate("time_start", start_date, data_result);
	start.init();
	start.datepicker();

	var end = new choosedate("time_end", start_date, data_result);
	end.init();
	end.datepicker();

	// 日期选择结束
	
	 $("html,body").animate({scrollTop:$("body").height()},2000);
	
}

/**
 * 将数据进行封装
 */
function appendToBody(data, mode, mode1, name, part) {
	$("#tbody").empty();

	if (mode == "select_all") {

		/**
		 * 在点击 select_all 的时候讲所有的信息都显示到table
		 */
		$("#select_level").removeAttr("attrib");
		for ( var i = 0; i < data.length; i++) {

			/**
			 * 添加表格
			 */
			var keepTime = (Date.parse(data[i]["endTime"]) - Date
					.parse(data[i]["startTime"]))
					/ (3600 * 1000 * 24);

			var tr_1 = "<tr><td>"+(i+1)+"</td><td>" + data[i]["name"] + "</td><td>"
					+ data[i]["startTime"] + "</td><td>" + data[i]["endTime"]
					+ "</td><td>" + keepTime + "*24h</td><td>"
					+ data[i]["message"];
			var tr_3 = "<td></td></tr>";
			var tr_2 = "";
			var tr = "";

			if (data[i]["level"] == 1) {
				tr_2 = "</td><td>严重异常</td>";
			} else if (data[i]["level"] == 2) {
				tr_2 = "</td><td>一般异常</td>";
			} else if (data[i]["level"] == 3) {
				tr_2 = "</td><td>严重错误</td>";
			} else if (data[i]["level"] == 4) {
				tr_2 = "</td><td>一般错误</td>";
			}

			tr = tr_1 + tr_2 + tr_3;
			$("#tbody").append(tr);
		}

		if (mode1 == "seriousExc") {
			$("#tbody").empty();
			for ( var i = 0; i < data.length; i++) {
				/**
				 * 添加表格
				 */
				var keepTime = (Date.parse(data[i]["endTime"]) - Date
						.parse(data[i]["startTime"]))
						/ (3600 * 1000 * 24);

				var tr_1 = "<tr><td>"+(i+1)+"</td><td>" + data[i]["name"] + "</td><td>"
						+ data[i]["startTime"] + "</td><td>"
						+ data[i]["endTime"] + "</td><td>" + keepTime
						+ "*24h</td><td>" + data[i]["message"];
				var tr_3 = "<td></td></tr>";
				var _tr_2 = "";
				var tr = "";

				if (data[i]["level"] == 1) {
					_tr_2 = "</td><td>严重异常</td>";
					tr = tr_1 + _tr_2 + tr_3;
					$("#tbody").append(tr);
				}

			}
		} else if (mode1 == "generalExc") {
			$("#tbody").empty();
			for ( var i = 0; i < data.length; i++) {
				/**
				 * 添加表格
				 */
				var keepTime = (Date.parse(data[i]["endTime"]) - Date
						.parse(data[i]["startTime"]))
						/ (3600 * 1000 * 24);

				var tr_1 = "<tr><td>"+(i+1)+"</td><td>" + data[i]["name"] + "</td><td>"
						+ data[i]["startTime"] + "</td><td>"
						+ data[i]["endTime"] + "</td><td>" + keepTime
						+ "*24h</td><td>" + data[i]["message"];
				var tr_3 = "<td></td></tr>";
				var _tr_2 = "";
				var tr = "";

				if (data[i]["level"] == 2) {
					_tr_2 = "</td><td>一般异常</td>";
					tr = tr_1 + _tr_2 + tr_3;
					$("#tbody").append(tr);
				}

			}
		} else if (mode1 == "seriousFau") {
			$("#tbody").empty();
			for ( var i = 0; i < data.length; i++) {
				/**
				 * 添加表格
				 */
				var keepTime = (Date.parse(data[i]["endTime"]) - Date
						.parse(data[i]["startTime"]))
						/ (3600 * 1000 * 24);

				var tr_1 = "<tr><td>"+(i+1)+"</td><td>" + data[i]["name"] + "</td><td>"
						+ data[i]["startTime"] + "</td><td>"
						+ data[i]["endTime"] + "</td><td>" + keepTime
						+ "*24h</td><td>" + data[i]["message"];
				var tr_3 = "<td></td></tr>";
				var _tr_2 = "";
				var tr = "";

				if (data[i]["level"] == 3) {
					_tr_2 = "</td><td>严重错误</td>";
					tr = tr_1 + _tr_2 + tr_3;
					$("#tbody").append(tr);
				}

			}
		} else if (mode1 == "generalFau") {
			$("#tbody").empty();
			for ( var i = 0; i < data.length; i++) {
				/**
				 * 添加表格
				 */
				var keepTime = (Date.parse(data[i]["endTime"]) - Date
						.parse(data[i]["startTime"]))
						/ (3600 * 1000 * 24);

				var tr_1 = "<tr><td>"+(i+1)+"</td><td>" + data[i]["name"] + "</td><td>"
						+ data[i]["startTime"] + "</td><td>"
						+ data[i]["endTime"] + "</td><td>" + keepTime
						+ "*24h</td><td>" + data[i]["message"];
				var tr_3 = "<td></td></tr>";
				var _tr_2 = "";
				var tr = "";

				if (data[i]["level"] == 4) {
					_tr_2 = "</td><td>一般错误</td>";
					tr = tr_1 + _tr_2 + tr_3;
					$("#tbody").append(tr);
				}

			}
		}
	}

	if (mode == "select_server_exc") {
		/**
		 * 在点击 select_server_exc 的时候将所有的 server异常 显示出来
		 */
		$("#select_server").removeAttr("attrib");
		// 添加"select_server"结束
		for ( var i = 0; i < data.length; i++) {
			/**
			 * 添加表格
			 */
			var keepTime = (Date.parse(data[i]["endTime"]) - Date
					.parse(data[i]["startTime"]))
					/ (3600 * 1000 * 24);

			var tr_1 = "<tr><td>"+(i+1)+"</td><td>" + data[i]["name"] + "</td><td>"
					+ data[i]["startTime"] + "</td><td>" + data[i]["endTime"]
					+ "</td><td>" + keepTime + "*24h</td><td>"
					+ data[i]["message"];
			var tr_3 = "<td></td></tr>";
			var tr_2 = "";
			var tr = "";

			if (data[i]["type"] == 1) {
				if (data[i]["level"] == 1) {
					tr_2 = "</td><td>严重异常</td>";
				} else if (data[i]["level"] == 2) {
					tr_2 = "</td><td>一般异常</td>";
				} else if (data[i]["level"] == 3) {
					seriousFaultCount++;
					tr_2 = "</td><td>严重错误</td>";
				} else if (data[i]["level"] == 4) {
					generalFaultCount++;
					tr_2 = "</td><td>一般错误</td>";
				}

				var $option = $("<option id='" + data[i]["id"] + "'>"
						+ data[i]["name"] + " </option>")
				$("#select_server").append($option);
				tr = tr_1 + tr_2 + tr_3;
				$("#tbody").append(tr);
			}
		}
	}

	/**
	 * 服务器三级下拉
	 */

	if (name != "" && name != undefined && part == "server") {
		var level_type = $("#select_level").find("option:selected")
				.attr("mode");
		for ( var i = 0; i < data.length; i++) {
			/**
			 * 添加表格
			 */
			var keepTime = (Date.parse(data[i]["endTime"]) - Date
					.parse(data[i]["startTime"]))
					/ (3600 * 1000 * 24);

			var tr_1 = "<tr><td>"+(i+1)+"</td><td>" + data[i]["name"] + "</td><td>"
					+ data[i]["startTime"] + "</td><td>" + data[i]["endTime"]
					+ "</td><td>" + keepTime + "*24h</td><td>"
					+ data[i]["message"];
			var tr_3 = "<td></td></tr>";
			var tr_2 = "";
			var tr = "";
			/**
			 * 添加表格
			 */
			if (level_type == "seriousExc" && data[i]["name"] == name
					&& data[i]["level"] == 1) {
				tr_2 = "</td><td>严重异常</td>";
				tr = tr_1 + tr_2 + tr_3;
				$("#tbody").append(tr);
			} else if (level_type == "generalExc" && data[i]["level"] == 2
					&& data[i]["name"] == name) {
				tr_2 = "</td><td>一般异常</td>";
				tr = tr_1 + tr_2 + tr_3;
				$("#tbody").append(tr);
			} else if (level_type == "seriousFau" && data[i]["level"] == 3
					&& data[i]["name"] == name) {
				tr_2 = "</td><td>严重错误</td>";
				tr = tr_1 + tr_2 + tr_3;
				$("#tbody").append(tr);
			} else if (level_type == "generalFau" && data[i]["level"] == 4
					&& data[i]["name"] == name) {
				tr_2 = "</td><td>一般错误</td>";
				tr = tr_1 + tr_2 + tr_3;
				$("#tbody").append(tr);
			}

		}

	}

	/**
	 * 应用三级下拉
	 */

	if (name != "" && name != undefined && part == "application") {

		var level_type = $("#select_level").find("option:selected").attr("mode");
		for ( var i = 0; i < data.length; i++) {
			/**
			 * 添加表格
			 */
			var keepTime = (Date.parse(data[i]["endTime"]) - Date.parse(data[i]["startTime"]))/ (3600 * 1000 * 24);

			var tr_1 = "<tr><td>"+(i+1)+"</td><td>" + data[i]["name"] + "</td><td>"
					+ data[i]["startTime"] + "</td><td>" + data[i]["endTime"]
					+ "</td><td>" + keepTime + "*24h</td><td>"
					+ data[i]["message"];
			var tr_3 = "<td></td></tr>";
			var tr_2 = "";
			var tr = "";
			/**
			 * 添加表格
			 */
			if (level_type == "seriousExc" && data[i]["name"] == name
					&& data[i]["level"] == 1) {
				tr_2 = "</td><td>严重异常</td>";
				tr = tr_1 + tr_2 + tr_3;
				$("#tbody").append(tr);
			} else if (level_type == "generalExc" && data[i]["level"] == 2
					&& data[i]["name"] == name) {
				tr_2 = "</td><td>一般异常</td>";
				tr = tr_1 + tr_2 + tr_3;
				$("#tbody").append(tr);
			} else if (level_type == "seriousFau" && data[i]["level"] == 3
					&& data[i]["name"] == name) {
				tr_2 = "</td><td>严重错误</td>";
				tr = tr_1 + tr_2 + tr_3;
				$("#tbody").append(tr);
			} else if (level_type == "generalFau" && data[i]["level"] == 4
					&& data[i]["name"] == name) {
				tr_2 = "</td><td>一般错误</td>";
				tr = tr_1 + tr_2 + tr_3;
				$("#tbody").append(tr);
			}

		}

	}

}

function checkAllServer(data, serverName, type) {
	$("#tbody").empty();

	if (serverName == "servers") { // 查看所有的server记录
		for ( var i = 0; i < data.length; i++) {

			/**
			 * 添加表格
			 */
			var keepTime = (Date.parse(data[i]["endTime"]) - Date
					.parse(data[i]["startTime"]))
					/ (3600 * 1000 * 24);

			var tr_1 = "<tr><td>"+(i+1)+"</td><td>" + data[i]["name"] + "</td><td>"
					+ data[i]["startTime"] + "</td><td>" + data[i]["endTime"]
					+ "</td><td>" + keepTime + "*24h</td><td>"
					+ data[i]["message"];
			var tr_3 = "<td></td></tr>";
			var tr_2 = "";
			var tr = "";

			if (data[i]["type"] == 1) {
				if (data[i]["level"] == 1) {
					tr_2 = "</td><td>严重异常</td>";
					tr = tr_1 + tr_2 + tr_3;

				} else if (data[i]["level"] == 2) {
					tr_2 = "</td><td>一般异常</td>";
					tr = tr_1 + tr_2 + tr_3;

				} else if (data[i]["level"] == 3) {
					tr_2 = "</td><td>严重错误</td>";
					tr = tr_1 + tr_2 + tr_3;

				} else if (data[i]["level"] == 4) {
					tr_2 = "</td><td>一般错误</td>";
					tr = tr_1 + tr_2 + tr_3;

				}

				$("#tbody").append(tr);
			}

		}
	} else if (serverName == "allapp") {
		for ( var i = 0; i < data.length; i++) {

			/**
			 * 添加表格
			 */
			var keepTime = (Date.parse(data[i]["endTime"]) - Date
					.parse(data[i]["startTime"]))
					/ (3600 * 1000 * 24);

			var tr_1 = "<tr><td>"+(i+1)+"</td><td>" + data[i]["name"] + "</td><td>"
					+ data[i]["startTime"] + "</td><td>" + data[i]["endTime"]
					+ "</td><td>" + keepTime + "*24h</td><td>"
					+ data[i]["message"];
			var tr_3 = "<td></td></tr>";
			var tr_2 = "";
			var tr = "";

			if (data[i]["type"] == 2) {
				if (data[i]["level"] == 1) {
					tr_2 = "</td><td>严重异常</td>";
					tr = tr_1 + tr_2 + tr_3;

				} else if (data[i]["level"] == 2) {
					tr_2 = "</td><td>一般异常</td>";
					tr = tr_1 + tr_2 + tr_3;

				} else if (data[i]["level"] == 3) {
					tr_2 = "</td><td>严重错误</td>";
					tr = tr_1 + tr_2 + tr_3;

				} else if (data[i]["level"] == 4) {
					tr_2 = "</td><td>一般错误</td>";
					tr = tr_1 + tr_2 + tr_3;

				}

				$("#tbody").append(tr);
			}

		}
	} else if (type == "app") {
		for ( var i = 0; i < data.length; i++) {

			/**
			 * 添加表格
			 */
			var keepTime = (Date.parse(data[i]["endTime"]) - Date
					.parse(data[i]["startTime"]))
					/ (3600 * 1000 * 24);

			var tr_1 = "<tr><td>"+(i+1)+"</td><td>" + data[i]["name"] + "</td><td>"
					+ data[i]["startTime"] + "</td><td>" + data[i]["endTime"]
					+ "</td><td>" + keepTime + "*24h</td><td>"
					+ data[i]["message"];
			var tr_3 = "<td></td></tr>";
			var tr_2 = "";
			var tr = "";

			if (data[i]["name"] == serverName) {
				if (data[i]["type"] == 2) {
					if (data[i]["level"] == 1) {
						tr_2 = "</td><td>严重异常</td>";
						tr = tr_1 + tr_2 + tr_3;

					} else if (data[i]["level"] == 2) {
						tr_2 = "</td><td>一般异常</td>";
						tr = tr_1 + tr_2 + tr_3;

					} else if (data[i]["level"] == 3) {
						tr_2 = "</td><td>严重错误</td>";
						tr = tr_1 + tr_2 + tr_3;

					} else if (data[i]["level"] == 4) {
						tr_2 = "</td><td>一般错误</td>";
						tr = tr_1 + tr_2 + tr_3;

					}

					$("#tbody").append(tr);
				}
			}

		}
	} else if (type == "server") {
		for ( var i = 0; i < data.length; i++) {

			/**
			 * 添加表格
			 */
			var keepTime = (Date.parse(data[i]["endTime"]) - Date
					.parse(data[i]["startTime"]))
					/ (3600 * 1000 * 24);

			var tr_1 = "<tr><td>"+(i+1)+"</td><td>" + data[i]["name"] + "</td><td>"
					+ data[i]["startTime"] + "</td><td>" + data[i]["endTime"]
					+ "</td><td>" + keepTime + "*24h</td><td>"
					+ data[i]["message"];
			var tr_3 = "<td></td></tr>";
			var tr_2 = "";
			var tr = "";

			if (data[i]["name"] == serverName) {
				if (data[i]["type"] == 1) {
					if (data[i]["level"] == 1) {
						tr_2 = "</td><td>严重异常</td>";
						tr = tr_1 + tr_2 + tr_3;

					} else if (data[i]["level"] == 2) {
						tr_2 = "</td><td>一般异常</td>";
						tr = tr_1 + tr_2 + tr_3;

					} else if (data[i]["level"] == 3) {
						tr_2 = "</td><td>严重错误</td>";
						tr = tr_1 + tr_2 + tr_3;

					} else if (data[i]["level"] == 4) {
						tr_2 = "</td><td>一般错误</td>";
						tr = tr_1 + tr_2 + tr_3;

					}

					$("#tbody").append(tr);
				}
			}

		}
	}
}

function appendSelect(data, selector) {
	
	var arrays = new Array();
	var array;
	$("#tbody").empty();
	if (selector == "select_server") {
		for ( var i = 0; i < data.length; i++) {

			/**
			 * 添加表格
			 */
			var keepTime = (Date.parse(data[i]["endTime"]) - Date
					.parse(data[i]["startTime"]))
					/ (3600 * 1000 * 24);

			var tr_1 = "<tr><td>"+(i+1)+"</td><td>" + data[i]["name"] + "</td><td>"
					+ data[i]["startTime"] + "</td><td>" + data[i]["endTime"]
					+ "</td><td>" + keepTime + "*24h</td><td>"
					+ data[i]["message"];
			var tr_3 = "<td></td></tr>";
			var tr_2 = "";
			var tr = "";

			if (data[i]["type"] == 1) {
				arrays.push(data[i]["name"]);
				if (data[i]["level"] == 1) {
					tr_2 = "</td><td>严重异常</td>";
				} else if (data[i]["level"] == 2) {
					tr_2 = "</td><td>一般异常</td>";
				} else if (data[i]["level"] == 3) {
					tr_2 = "</td><td>严重错误</td>";
				} else if (data[i]["level"] == 4) {
					tr_2 = "</td><td>一般错误</td>";
				}

				tr = tr_1 + tr_2 + tr_3;
				$("#tbody").append(tr);
			}
		}
	} else if (selector == "select_app") {
		for ( var i = 0; i < data.length; i++) {

			/**
			 * 添加表格
			 */
			var keepTime = (Date.parse(data[i]["endTime"]) - Date
					.parse(data[i]["startTime"]))
					/ (3600 * 1000 * 24);

			var tr_1 = "<tr><td>"+(i+1)+"</td><td>" + data[i]["name"] + "</td><td>"
					+ data[i]["startTime"] + "</td><td>" + data[i]["endTime"]
					+ "</td><td>" + keepTime + "*24h</td><td>"
					+ data[i]["message"];
			var tr_3 = "<td></td></tr>";
			var tr_2 = "";
			var tr = "";

			if (data[i]["type"] == 2) {
				arrays.push(data[i]["name"]);
				if (data[i]["level"] == 1) {
					tr_2 = "</td><td>严重异常</td>";
				} else if (data[i]["level"] == 2) {
					tr_2 = "</td><td>一般异常</td>";
				} else if (data[i]["level"] == 3) {
					tr_2 = "</td><td>严重错误</td>";
				} else if (data[i]["level"] == 4) {
					tr_2 = "</td><td>一般错误</td>";
				}

				tr = tr_1 + tr_2 + tr_3;
				$("#tbody").append(tr);
			}
		}
	}

	array = arrays.unique();
	for ( var j = 0; j < array.length; j++) {
		var $option = $("<option name='" + array[j] + "'>" + array[j]
				+ " </option>")
		$("#" + selector).append($option);
	}
}

/**
 * 日历
 */
// 日期选择
function choosedate(object, date, data) {

	this.object = object;
	this.date = date;
	this.init = function() {
		this.object = "." + this.object;
		$(this.object).val(this.date);
	};

	this.datepicker = function() {
		var object = this.object;
		var date = this.date;
		var array = new Array();

		$(object).DatePicker(
				{
					format : 'm/d/Y',
					date : [ '2008-07-28', '2008-07-31' ],
					current : '2008-07-31',
					starts : 1,
					position : 'r',
					onBeforeShow : function() {
						$(object).DatePickerSetDate($(object).val(), true);
					},
					onChange : function(formated, dates) {

						$(object).val(formated);
						$(object).DatePickerHide();

						var start = $(".time_start").val();
						var end = $(".time_end").val();

						// 如果输入的为结束日期那么判断一下 开始日期和结束日期的大小

						if (Date.parse(start) - Date.parse(end) > 0) {
							if (object == ".time_start") {
								$(object).val(date);
							}
							alert("对不起  输入的结束时间应该在起始时间之后");
						} else {
							// 如果符合要求 就从后台传送过来的数据中查询符合条件的数据

							for ( var i = 0; i < data.length; i++) {
								var newdate = data[i]["startTime"];

								if (Date.parse(newdate) - Date.parse(start) > 0
										&& Date.parse(newdate)
												- Date.parse(end) < 0) {
									array.push(data[i]);
								}
							}

							addrecord(array);

						}

					}
				});
	}

}
// 日历结束

function addrecord(data) {
	$("#tbody").empty();
	for ( var i = 0; i < data.length; i++) {
		for ( var i = 0; i < data.length; i++) {

			/**
			 * 添加表格
			 */
			var keepTime = (Date.parse(data[i]["endTime"]) - Date
					.parse(data[i]["startTime"]))
					/ (3600 * 1000 * 24);

			var tr_1 = "<tr><td>"+(i+1)+"</td><td>" + data[i]["name"] + "</td><td>"
					+ data[i]["startTime"] + "</td><td>" + data[i]["endTime"]
					+ "</td><td>" + keepTime + "*24h</td><td>"
					+ data[i]["message"];
			var tr_3 = "<td></td></tr>";
			var tr_2 = "";
			var tr = "";

			if (data[i]["level"] == 1) {
				seriousCount++;
				tr_2 = "</td><td>严重异常</td>";

			} else if (data[i]["level"] == 2) {
				generalCount++;
				tr_2 = "</td><td>一般异常</td>";
			} else if (data[i]["level"] == 3) {
				seriousFaultCount++;
				tr_2 = "</td><td>严重错误</td>";
			} else if (data[i]["level"] == 4) {
				generalFaultCount++;
				tr_2 = "</td><td>一般错误</td>";
			}

			tr = tr_1 + tr_2 + tr_3;
			$("#tbody").append(tr);

		}
	}
}

function error() {
	//console.log("error");
}

/**
 * 获取严重异常的数量
 */
function getSeriousCount() {
	return seriousCount;
}

/**
 * 获取一般异常的数量
 */
function getGeneralCount() {
	return generalCount;
}

/**
 * 获取严重错误的数量
 */
function getSeriousFaultCount() {
	return seriousFaultCount;
}

/**
 * 获取一般错误的数量
 */
function getGeneralFaultCount() {
	return generalFaultCount;
}

/**
 * 去掉重复的服务器名称
 */

Array.prototype.unique = function() {
	var n = {};
	var arr = [];

	for ( var i = 0; i < this.length; i++) {
		if (!n[this[i]]) {
			n[this[i]] = true;
			arr.push(this[i]);
		}
	}

	return arr;
}

function toArray(data) {

	var arr = new Array();

	if (!$.isArray(data))
		arr.push(data);
	arr = data;

	return arr;
}
