package com.kpleasing.wxss.dao.impl;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.orm.hibernate5.HibernateTemplate;

import com.kpleasing.wxss.dao.BaseDao;


/**
 * 
 * @author Howard.huang
 *
 * @param <T>
 * @param <ID>
 */
public abstract class BaseDaoImpl<T, PK extends Serializable> implements BaseDao<T, PK> {
	private Class<T> persistentClass;
	
	@SuppressWarnings("unchecked")
	public BaseDaoImpl() {
		this.persistentClass = (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
	}

	public Class<T> getPersistentClass() {
		return persistentClass;
	}
	
	@Autowired
	public HibernateTemplate hibernateTemplate;
	
	protected Session getSession() {
        return hibernateTemplate.getSessionFactory().getCurrentSession();
    }
	
	
	/**
	 * 通过id查找
	 * @param id
	 * @return
	 * @throws DataAccessException 
	 */
	public T findById(PK id){
		return (T) hibernateTemplate.get(getPersistentClass(), id);
	}

	
	/**
	 * 保存
	 */
	public void save(T entity) {
		hibernateTemplate.save(entity);
	}

	
	/**
	 * 更新
	 */
	public void update(T entity) {
		hibernateTemplate.update(entity);
	}
	
	/**
	 * 删除
	 */
	public void delete(T entity) {
		hibernateTemplate.delete(entity);
	}
	
	
	/**
	 * 根据hql查询 
	 * @param hql
	 * @param values
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<T> find(final String hql, final Object...values) {
		Query<T> query = this.getSession().createQuery(hql);
        for(int i = 0;i<values.length;i++){
            query.setParameter(i, values[i]);
        }  
        return query.list();
	 }

	
	/**
	 * 通过属性删除
	 */
	@SuppressWarnings("unchecked")
	public void deleteByProperty(String propertyName, Object value) {
		String queryString = "delete from " + getPersistentClass().getName() + " as model where model." + propertyName + "= ?";
		Query<T> query = this.getSession().createQuery(queryString);
		query.setParameter(0, value);
		query.executeUpdate();
	}

	/**
	 * saveOrUpdate
	 */
	public void saveOrUpdate(T entity) {
		hibernateTemplate.flush();
		hibernateTemplate.clear();
		hibernateTemplate.saveOrUpdate(entity);
	}
	
	/**
	 * 
	 * @param entity
	 */
	public void merge(T entity) {
		hibernateTemplate.flush();
		hibernateTemplate.clear();
		hibernateTemplate.merge(entity);
	}

	

	/**
	 * 分页查找所有的记录
	 * @param page 要返回的页数
	 * @param pageSize 没有记录数
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<T> findAll(int page, int pageSize) {
		String queryString = "from " + getPersistentClass().getName();
		Query<T> query = this.getSession().createQuery(queryString);
		int firstResult = (page - 1) * pageSize;
		query.setFirstResult(firstResult);
		query.setMaxResults(pageSize);
		return query.list();
	}

	/**
	 * 统计所有记录的总数
	 * @return 总数
	 */
	@SuppressWarnings("unchecked")
	public int countAll() {
		String queryString = "select count(*) from " + getPersistentClass().getName();
		Query<T> query = (Query<T>) this.getSession().createQuery(queryString);
		List<T> list = query.list();
		Long result = (Long) list.get(0);
		return result.intValue();
	}

	/**
	 * find By Example
	 * @param entity
	 * @return
	 */
	public List<T> findByExample(T entity) {
		return hibernateTemplate.findByExample(entity);
	}

	
	public List<T> findAll() {
		return (List<T>) find("from " + getPersistentClass().getName());
	}

	/**
	 * 通过属性查找
	 * @param propertyName 属性名称
	 * @param value 属性的值
	 * @return
	 */
	public List<T> findByProperty(String propertyName, Object value) {
		String queryString = "from " + getPersistentClass().getName() + " as model where model." + propertyName + "= ?";
		return (List<T>) find(queryString, value);

	}

	/**
	 * 通过多个属性组合查询
	 * @param propertyNames 属性名称数组
	 * @param values 对应于propertyNames的值 return 匹配的结果
	 */
	public List<T> findByPropertys(String[] propertyNames, Object[] values) {
		StringBuffer strBuffer = new StringBuffer();
		strBuffer.append("from " + getPersistentClass().getName());
		strBuffer.append(" as model where ");
		for (int i = 0; i < propertyNames.length; i++) {
			if (i != 0)
				strBuffer.append(" and");
			strBuffer.append(" model.");
			strBuffer.append(propertyNames[i]);
			strBuffer.append("=");
			strBuffer.append("? ");
		}
		String queryString = strBuffer.toString();
		return (List<T>) find(queryString, values);
	}

	/**
	 * 通过属性查找并分页
	 * @param propertyName 属性名称
	 * @param value 属性值
	 * @param page 页数
	 * @param pageSize 每页显示条数
	 */
	public List<T> findByProperty(String propertyName, Object value, int page, int pageSize) {
		return this.findByPropertys(new String[] { propertyName }, new Object[] { value }, page, pageSize);
	}

	/**
	 * 通过多个属性组合查询
	 * @param propertyNames 属性名称数组
	 * @param values 对应于propertyNames的值
	 * @param page 页数
	 * @param pageSize 每页显示数 return 匹配的结果 return 匹配的结果
	 */
	@SuppressWarnings("unchecked")
	public List<T> findByPropertys(String[] propertyNames, Object[] values,
			int page, int pageSize) {
		StringBuffer strBuffer = new StringBuffer();
		strBuffer.append("from " + getPersistentClass().getName());
		strBuffer.append(" as model where ");
		for (int i = 0; i < propertyNames.length; i++) {
			if (i != 0)
				strBuffer.append(" and");
			strBuffer.append(" model.");
			strBuffer.append(propertyNames[i]);
			strBuffer.append("=");
			strBuffer.append("? ");
		}
		String queryString = strBuffer.toString();

		int firstResult = (page - 1) * pageSize;

		Query<T> query = this.getSession().createQuery(queryString);
		query.setFirstResult(firstResult);
		query.setMaxResults(pageSize);
		for (int i = 0; i < values.length; i++) {
			query.setParameter(i, values[i]);
		}
		return query.list();
	}

	
	/**
	 * 通过属性统计数量
	 * @param propertyName 属性名称
	 * @param value 属性值
	 */
	public int countByProperty(String propertyName, Object value) {
		String[] propertyNames = new String[] { propertyName };
		Object[] values = new Object[] { value };
		return this.countByPropertys(propertyNames, values);
	}

	/**
	 * 通过多个属性统计数量
	 * @param propertyNames 属性名称数组
	 * @param values 对应的属性值数组 return
	 */
	@SuppressWarnings("unchecked")
	public int countByPropertys(String[] propertyNames, Object[] values) {
		StringBuffer strBuffer = new StringBuffer();
		strBuffer.append("select count(*) from "
				+ getPersistentClass().getName());
		strBuffer.append(" as model where ");
		for (int i = 0; i < propertyNames.length; i++) {
			if (i != 0)
				strBuffer.append(" and");
			strBuffer.append(" model.");
			strBuffer.append(propertyNames[i]);
			strBuffer.append("=");
			strBuffer.append("? ");
		}

		String queryString = strBuffer.toString();
		Query<T> query = this.getSession().createQuery(queryString);
		for (int i = 0; i < values.length; i++) {
			query.setParameter(i, values[i]);
		}

		List<T> list = query.list();
		Long result = (Long) list.get(0);
		return result.intValue();
	}

	/**
	 * 查找T并通过某一属性排序
	 * @param property 排序依据的顺序
	 * @param isSequence 是否顺序排序,false为倒序
	 */
	@SuppressWarnings("unchecked")
	public List<T> findAndOrderByProperty(int firstResult, int fetchSize,
			String propertyName, boolean isSequence) {
		String queryString = "from " + getPersistentClass().getName()
				+ " as model order by model." + propertyName;
		if (isSequence == false) {
			queryString = queryString + " DESC";
		}

		Query<T> queryObject = this.getSession().createQuery(queryString);
		queryObject.setFirstResult(firstResult);
		queryObject.setMaxResults(fetchSize);
		return queryObject.list();
	}

	
	/**
	 * 查找所有并通过某个属性排序
	 * @param propertyName 排序依据的属性名称
	 * @param isSequence 是否顺序排列
	 */
	@SuppressWarnings("unchecked")
	public List<T> findAllAndOrderByProperty(String propertyName,
			boolean isSequence) {
		String queryString = "from " + getPersistentClass().getName()
				+ " as model order by model." + propertyName;
		if (isSequence == false) {
			queryString = queryString + " DESC";
		}

		Query<T> queryObject = this.getSession().createQuery(queryString);
		return queryObject.list();
	}
}
