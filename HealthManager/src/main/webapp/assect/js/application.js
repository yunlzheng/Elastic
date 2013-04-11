function getApplicationOperatStatusDesc(status){
	
	switch (status) {
	case 0:
		return "未进行任何操作";
	case 1:
		return "部署中";
	case 2:
		return "启动中";
	case 3:
		return "停止中";
	case 4:
		return "卸载中";
	case 5:
		return "扩展中";
	case 6:
		return "收缩中";
	default:
		return "未知状态";
	}
	
}

function getApplicationHealthDesc(health){
	
	switch (health) {
	case 100:
		
		return "已上传";
	case 101:
		return "未运行任何实例";

	case 102:
		return "运行中";
	case 103:
		return "已停止";
	default:
		return "未知状态";
	}
	
}