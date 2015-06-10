package com.yunguanshi.dao;

import java.io.Serializable;
import java.util.List;

import javax.annotation.Resource;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.AggregateProjection;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.SimpleExpression;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.yunguanshi.utils.hibernate.Pagination;
import com.yunguanshi.utils.hibernate.QueryCondition;

/**
 * 
 * @author huanghuanlai
 *
 * @param <T>
 */
@SuppressWarnings("all")
@Repository
public class BaseDao<T>{
	
	@Resource
	private SessionFactory sessionFactory;

	public Session getSession() {
		return sessionFactory.getCurrentSession();
	}

	public T get(Class<T> t, Serializable id) {
		return (T) this.getSession().get(t, id);
	}

	public T get(String hql, Object param) {
		List<T> l = this.find(hql, param);
		if (l != null && l.size() > 0) {
			return l.get(0);
		} else {
			return null;
		}
	}
	
	public T findById(Class<T> t,Serializable id){
		return (T) getSession().load(t, id);
	}
	
	public T findByUUID(Class<T> t,String hql,String parem){
		hql = "from "+t.getSimpleName()+" u where "+hql;
		Query query =getSession().createQuery(hql); 
		query.setParameter(0, parem);
		List<T> list = query.list();
		
		if(list.size()>0){
			return (T) query.list().get(0);
		}else {
			return null;
		}
	}
	
	public List<T> findByClass(Class<T> t){
		Criteria criteria = getSession().createCriteria(t);
		return criteria.list();
	}
	
	public Pagination<T> findByClass(Class<T> t,Pagination<T> params){
		Criteria criteria = getSession().createCriteria(t);
		criteria.setFirstResult(params.getOffset());
		criteria.setMaxResults(params.getPageSize());
		params.setRecordList(criteria.list());
		return params;
	}
	
	public Pagination<T> findByClass(Class<T> t,Pagination<T> params,Order order){
		Criteria criteria = getSession().createCriteria(t);
		if(params.getCriterias()!=null){
			if(params.getCriterias().getCriterions().size()>0){
				for(Criterion cri : params.getCriterias().getCriterions()){
					criteria.add(cri);
				}
			}
		}
		if(null!=order)criteria.addOrder(order);//设置排序字段
		criteria.setFirstResult(params.getOffset());
		criteria.setMaxResults(params.getPageSize());
		params.setRecordList(criteria.list());
		return params;
	}
	public Pagination<T> findByClass(Class<T> t,Pagination<T> params,AggregateProjection projections){
		Criteria criteria = getSession().createCriteria(t);
		criteria.setFirstResult(params.getOffset());
		criteria.setMaxResults(params.getPageSize());
		params.setRecordList(criteria.list());
		return params;
	}
	
	public Pagination<T> findByClass(Class<T> t,Pagination<T> params,Order order,AggregateProjection projections){
		Criteria criteria = getSession().createCriteria(t);
		if(null!=order)criteria.addOrder(order);
		if(null!=projections)criteria.setProjection(projections);//设置查询参数
		criteria.setFirstResult(params.getOffset());
		criteria.setMaxResults(params.getPageSize());
		params.setRecordList(criteria.list());
		return params;
	}
	
	public Long getClassCount(Class<T> class1){
		Query query = this.getSession().createQuery("select count(*) from "+class1.getName());
		return (Long) query.uniqueResult();
	}
	
	public List<?> findBySql(String sql){
		Query query = this.getSession().createSQLQuery(sql);
		return  query.list();
	}
	
	public List<?> findByHql(String hql){
		Query query = this.getSession().createQuery(hql);
		return query.list();
	}
	
	public List<?> findByHql(String hql,List<Object> params){
		Query query = this.getSession().createQuery(hql);
		if (null != params && params.size() > 0) {
			for (int i = 0; i < params.size(); i++) {
				query.setParameter(i, params.get(i));
			}
		}
		return query.list();
	}
	
	public List<T> findByHql(String hql,String param){
		Query query = this.getSession().createQuery(hql);
		query.setParameter(0, param);
		return query.list();
	}
	
	public List<?> findBySql(String sql, List<Object> params){
		Query query = this.getSession().createQuery(sql);
		if (null != params && params.size() > 0) {
			for (int i = 0; i < params.size(); i++) {
				query.setParameter(i, params.get(i));
			}
		}
		return  query.list();
	}
	
	public Object findBySqlArray(String sql, List<Object> params){
		SQLQuery query = this.getSession().createSQLQuery(sql);
		if (null != params && params.size() > 0) {
			for (int i = 0; i < params.size(); i++) {
				query.setParameter(i, params.get(i));
			}
		}
		return  query.uniqueResult();
	}

	public long count(String hql) {
		return this.getSession().createQuery(hql).list().size();
	}

	public long count(String hql, List<Object> params) {
		Query query = this.getSession().createQuery(hql);
		if (null != params && params.size() > 0) {
			for (int i = 0; i < params.size(); i++) {
				query.setParameter(i, params.get(i));
			}
		}
		Long result = (Long) query.uniqueResult();
		return result;
	}

	public List<T> find(String hql) {
		return this.getSession().createQuery(hql).list();
	}

	public List<T> findByhql(String hql, List<Object> params) {
		Query query = this.getSession().createQuery(hql);
		if (null != params && params.size() > 0) {
			for (int i = 0; i < params.size(); i++) {
				query.setParameter(i, params.get(i));
			}
		}
		return query.list();
	}
	
	public List<T> find(String hql, Object params) {
		Query query = this.getSession().createQuery(hql);
		if(null!=params){
			query.setParameter(0, params);
		}
		return query.list();
	}

	public <T> void batchSave(List<T> entitys) {
		// TODO Auto-generated method stub
		
	}

	public int executeBySQL(String sql, Object... params) {
		// TODO Auto-generated method stub
		return 0;
	}

	public <T> void delete(Class<T> clazz, Object id) {
		// TODO Auto-generated method stub
		
	}
	
	public void deleteByObj(Object obj) {
		getSession().delete(obj);
	}

	public <T> void delete(Class<T> clazz, Object[] ids) {
		
	}

	public <T> T getById(Class<T> clazz, Object id) {
		// TODO Auto-generated method stub
		return null;
	}

	public <T> List<T> getByIds(Class<T> clazz, Object[] ids) {
		// TODO Auto-generated method stub
		return null;
	}

	public <T> List<T> getAll(Class<T> clazz) {
		// TODO Auto-generated method stub
		return null;
	}

	public <T> List<T> get(Class<T> clazz,
			List<QueryCondition> queryConditions, String orderBy,
			int currentPage, int pageSize) {
		// TODO Auto-generated method stub
		return null;
	}

	public <T> List<T> get(Class<T> clazz, List<QueryCondition> queryConditions) {
		// TODO Auto-generated method stub
		return null;
	}

	public <T> List<T> get(Class<T> clazz,
			List<QueryCondition> queryConditions, String orderBy) {
		// TODO Auto-generated method stub
		return null;
	}

	
	public Object getSingleResult(Class clazz,
			List<QueryCondition> queryConditions) {
		// TODO Auto-generated method stub
		return null;
	}

	
	public long getRecordCount(Class clazz, List<QueryCondition> queryConditions) {
		// TODO Auto-generated method stub
		return 0;
	}

	
	public <T> List<T> getByJpql(String jpql, Object... objects) {
		// TODO Auto-generated method stub
		return null;
	}

	
	public int executeJpql(String jpql, Object... objects) {
		// TODO Auto-generated method stub
		return 0;
	}

	
	public <T> Pagination<T> getPagination(Class<T> clazz,
			List<QueryCondition> queryConditions, String orderBy,
			int currentPage, int pageSize) {
		// TODO Auto-generated method stub
		return null;
	}

	
	public <T> List<T> getPageQuery(int currentPage, int pageSize, String jpql,
			Object... objects) {
		// TODO Auto-generated method stub
		return null;
	}

	
	public Object getUniqueResultByJpql(String jpql, Object... objects) {
		// TODO Auto-generated method stub
		return null;
	}

	public T update(T entity) {
		this.getSession().merge(entity);
		return entity;
	}
	
	public Object updateByObj(Object obj) {
		this.getSession().merge(obj);
		return obj;
	}
	
	public T save(T t) {
		this.getSession().save(t);
		return t;
	}
	
	public Object saveByObj(Object obj) {
		this.getSession().save(obj);
		return obj;
	}

	public void delete(T t) {
		this.getSession().delete(t);
	}

	public void saveOrUpdate(T t) {
		this.getSession().saveOrUpdate(t);
	}

	public void deleteByhql(String hql,List<String> mList) {
		Query query = getSession().createQuery(hql);
		for(int i =0;i<mList.size();i++){
			query.setParameter(i, mList.get(i));
		}
		query.executeUpdate();
	}
}
