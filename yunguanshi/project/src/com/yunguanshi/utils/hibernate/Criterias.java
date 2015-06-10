package com.yunguanshi.utils.hibernate;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.criterion.Criterion;

public class Criterias {

	private List<Criterion> criterions=new ArrayList<Criterion>(0);

	public List<Criterion> getCriterions() {
		return criterions;
	}
	public void setCriterions(List<Criterion> criterions) {
		this.criterions = criterions;
	}
}
