package util;

import java.lang.reflect.Field;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.SQLQuery;
import org.hibernate.Session;

public class KeyUtil {

	public static synchronized Integer getNextKey(Class<?> entityClass,
			Session hSession) {
		String tableName = getTableNameByEntityClass(entityClass);
		String keyColumn = getNumericIdByEntityClass(entityClass);

		if (null != keyColumn && null != tableName) {
			String nextIdSQL = "SELECT MAX(" + keyColumn + ") AS ID " + "FROM "
					+ tableName;
			SQLQuery query = hSession.createSQLQuery(nextIdSQL);
			Integer maxKey = (Integer) query.list().get(0);

			return maxKey + 1;
		} else {
			throw new RuntimeException(
					"Can't find table props to fetch max ID.");
		}

	}

	public static String getTableNameByEntityClass(Class<?> entityClass) {
		Table entAnnotation = entityClass.getAnnotation(Table.class);
		if (null != entAnnotation)
			return entAnnotation.name();
		else {
			return entityClass.getSimpleName().toUpperCase();
		}
	}

	public static Field getIdFieldByEntityClass(Class<?> entityClass) {
		Field[] fields = entityClass.getDeclaredFields();
		for (Field field : fields) {
			Id id = field.getAnnotation(Id.class);
			EmbeddedId embeddedId = field.getAnnotation(EmbeddedId.class);
			if (null == id && null == embeddedId)
				continue;

			return field;
		}

		return null;
	}

	public static String getNumericIdByEntityClass(Class<?> entityClass) {
		Field field = getIdFieldByEntityClass(entityClass);

		if (null != field && field.getType().toString().equals("int")) {
			Column column = field.getAnnotation(Column.class);
			if (null != column)
				return column.name();
			else
				return field.getName().toUpperCase();
		}

		return null;
	}
}
