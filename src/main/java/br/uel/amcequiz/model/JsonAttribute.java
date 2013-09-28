package br.uel.amcequiz.model;

import java.io.Serializable;
import java.lang.reflect.Type;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.TreeMap;

import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SessionImplementor;
import org.hibernate.usertype.UserType;
import org.springframework.util.StringUtils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

public class JsonAttribute implements UserType {

	@Override
	public Object assemble(Serializable arg0, Object arg1)
			throws HibernateException {
		return arg0;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Object deepCopy(Object arg0) throws HibernateException {
		TreeMap<String, String> m = (TreeMap<String, String>) arg0;
		return new TreeMap<String, String>(m);
	}

	@Override
	public Serializable disassemble(Object arg0) throws HibernateException {
		return (Serializable) arg0;
	}

	@SuppressWarnings("unchecked")
	@Override
	public boolean equals(Object arg0, Object arg1) throws HibernateException {
		TreeMap<String, String> m1 = (TreeMap<String, String>) arg0;
		TreeMap<String, String> m2 = (TreeMap<String, String>) arg1;
		return m1.equals(m2);
	}

	@Override
	public int hashCode(Object arg0) throws HibernateException {
		return arg0.hashCode();
	}

	@Override
	public boolean isMutable() {
		return true;
	}

	@Override
	public Object nullSafeGet(ResultSet arg0, String[] arg1,
			SessionImplementor arg2, Object arg3) throws HibernateException,
			SQLException {
		String col = arg1[0];
		String val = arg0.getString(col);
		
		if (!StringUtils.hasText(val)) {
			return new TreeMap<String, String>();
		}
		
		Gson gson = new Gson();
		gson.toJson(val);
		
		Type stringStringMap = new TypeToken<TreeMap<String, String>>(){}.getType();
		TreeMap<String, String> m = gson.fromJson(val, stringStringMap);
		
		return m;
	}

	@SuppressWarnings("unchecked")
	@Override
	public void nullSafeSet(PreparedStatement ps, Object obj, int i,
			SessionImplementor arg3) throws HibernateException, SQLException {
		TreeMap<String, String> m = (TreeMap<String, String>) obj;
		
		String s = null;
		if (m.isEmpty()) {
			s = "";
		} else {	
			GsonBuilder builder = new GsonBuilder();
			Gson gson =
		            builder.enableComplexMapKeySerialization().setPrettyPrinting().create();
			Type type = new TypeToken<TreeMap<String,String>>(){}.getType();
			
			s = gson.toJson(m, type);
		}
		
		ps.setObject(i, s, Types.OTHER);
	}

	@Override
	public Object replace(Object arg0, Object arg1, Object arg2)
			throws HibernateException {
		return arg0;
	}

	@SuppressWarnings("rawtypes")
	@Override
	public Class returnedClass() {
		return TreeMap.class;
	}

	@Override
	public int[] sqlTypes() {
		return new int[] { Types.INTEGER };
	}

}
