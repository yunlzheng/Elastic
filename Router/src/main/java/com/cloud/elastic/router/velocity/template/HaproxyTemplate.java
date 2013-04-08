package com.cloud.elastic.router.velocity.template;

import java.util.List;
import com.cloud.elastic.commons.bean.RUnit;
import com.cloud.elastic.commons.bean.Runtime;

public class HaproxyTemplate {

	private Runtime runtime;
	private List<RUnit> runit;
	
	public Runtime getRuntime() {
		return runtime;
	}
	public void setRuntime(Runtime runtime) {
		this.runtime = runtime;
	}
	public List<RUnit> getRunit() {
		return runit;
	}
	public void setRunit(List<RUnit> runit) {
		this.runit = runit;
	}
	
	
	
}
