import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.lang.reflect.Field;
import java.util.Scanner;
import java.util.Set;

import javax.persistence.Entity;

import org.reflections.Reflections;

import util.KeyUtil;

public class DaoGenerator {
	public static void main(String[] args) throws FileNotFoundException {
		generateDaos();
	}

	public static void generateDaos() throws FileNotFoundException {
		Reflections reflections = new Reflections(
				"ru.ecom4u.web.domain.db.entities");

		Set<Class<? extends Object>> allClasses = reflections
				.getTypesAnnotatedWith(Entity.class);

		System.out.println(allClasses.size());

		String templ = readTpl("d:/DEV/JAVA/_LABS_/1/dao.java.tpl");
		File dir = new File("d:/DEV/JAVA/_LABS_/1/out");

		for (Class<? extends Object> clazz : allClasses) {
			String tpl = new String(templ);

			String simpleName = clazz.getSimpleName();
			String varName = simpleName.substring(0, 1).toLowerCase()
					+ simpleName.substring(1);
			Field keyField = KeyUtil.getIdFieldByEntityClass(clazz);
			System.out.println(simpleName + "  " + varName + "  (" + keyField
					+ ")  ");
			String idType = keyField.getType().getSimpleName();

			tpl = tpl.replaceAll("__EntClass__", simpleName);
			tpl = tpl.replaceAll("__VarName__", varName);
			tpl = tpl.replaceAll("__IdType__", idType);

			// System.out.println(tpl + "\n\n\n");

			File javaFile = new File(dir, simpleName + "Dao.java");
			writeResult(javaFile, tpl);
		}
	}

	public static String readTpl(String path) throws FileNotFoundException {
		Scanner scanner = new Scanner(new File(path));
		StringBuffer sb = new StringBuffer();
		while (scanner.hasNextLine()) {
			sb.append(scanner.nextLine());
			sb.append('\n');
		}
		scanner.close();

		return sb.toString();
	}

	public static void writeResult(File file, String txt)
			throws FileNotFoundException {
		PrintWriter pw = new PrintWriter(file);
		pw.write(txt);
		pw.close();
	}
}
